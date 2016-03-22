

/**
 * Created by Administrator on 2015/12/25.
 */
package core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import bean.ProcessList;
import bean.ProcessListItem;
import bean.ServerStatus;
import bean.ServerVariables;


public class MysqlServer extends TimerTask {

    //BEAN STUFF
    private int id;
    private String name;
    private String host;
    private int port;
    private String username;
    private String password;
    private String db;

    //BUSINESS LOGIC STUFF
    private Timer timer = new Timer(true);
    private Map<String, String> statusVariables = new HashMap<String, String>();
    private Map<String, String> serverVariables = new HashMap<String, String>();
    private Map<String,String>   schemaVariables=new HashMap<String,String>();
    private ServerStatus status = ServerStatus.DISCONNECTED;
    private String errorMessage = null;

    //EVENT STUFF
    private Set<ProcessListListener> processListListeners = new HashSet<ProcessListListener>();
    private Set<ServerVariablesListener> serverVariablesListeners = new HashSet<ServerVariablesListener>();
    private Set<ServerStatusListener> serverStatusListeners = new HashSet<ServerStatusListener>();
    private Set<SchemaListener>   schemaListeners=new HashSet<SchemaListener>();

    //SQL STUFF
    private Connection con;
    private PreparedStatement processListPs;
    private PreparedStatement statusVariablesPs;
    private PreparedStatement serverVariablesPs;
    private PreparedStatement schemaPs;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public MysqlServer(int id, String name, String host, int port, String username,
                       String password, String db) {
        super();
        this.setId(id);
        this.name = name;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.db=db;
        timer.schedule(this, 0, 1000);
    }

    //BEAN GETTERS AND SETTERS
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username ;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public ServerStatus getStatus() {
        return status;
    }
    public String getErrorMessage(){
        return errorMessage;
    }


    //EVENT STUFF
    public void addProcessListListener(ProcessListListener listener){
        processListListeners.add(listener);
    }

    public void removeProcessListListener(ProcessListListener listener){
        processListListeners.remove(listener);
    }

    public void addServerVariablesListener(ServerVariablesListener listener){
        serverVariablesListeners.add(listener);
    }

    public void removeServerVariablesListener(ServerVariablesListener listener){
        serverVariablesListeners.remove(listener);
    }

    public void addServerStatusListener(ServerStatusListener listener){
        serverStatusListeners.add(listener);
    }

    public void removeServerStatusListener(ServerStatusListener listener){
        serverStatusListeners.remove(listener);
    }
    public void addSchemaListener(SchemaListener listener){schemaListeners.add(listener);}
    public void  removeSchemaListener(SchemaListener listener){schemaListeners.remove(listener);}

    private void onError(Exception exc){
        if(serverVariablesPs!=null){
            try {
                serverVariablesPs.close();
            } catch (SQLException e) {}
            finally{
                serverVariablesPs = null;
            }
        }

        if(statusVariablesPs!=null){
            try {
                statusVariablesPs.close();
            } catch (SQLException e) {}
            finally{
                statusVariablesPs = null;
            }
        }

        if(processListPs!=null){
            try {
                processListPs.close();
            } catch (SQLException e) {}
            finally{
                processListPs = null;
            }
        }

        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {}
            finally{
                con = null;
            }
        }

        errorMessage = exc.getMessage();

        setStatus(ServerStatus.ERROR);
    }

   public synchronized void setupConnection(){
        if(status == ServerStatus.CONNECTED) return;
        try{
            String url = "jdbc:mysql://"+host+":"+port;
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection (url, username, password);
            errorMessage = null;
            setStatus(ServerStatus.CONNECTED);
        }
        catch(Exception e){
            onError(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        synchronized (con) {
            if(con!=null)
                con.close();
        }
    }

    private void setStatus(ServerStatus newStatus){
        if(status != newStatus){
            status = newStatus;

            //notify all
            Set<ServerStatusListener> tempSet = new HashSet<ServerStatusListener>(serverStatusListeners);
            for (ServerStatusListener listener : tempSet) {
                listener.onServerStatusChange(this);
            }
        }
    }

    private void generateProcessList(){
        if(status != ServerStatus.CONNECTED)
            return;

        if(processListListeners.size() == 0) //no one interested
            return;

        try{
            List<ProcessListItem> items = new ArrayList<ProcessListItem>();

            synchronized (con) {
                if(processListPs==null)
                    //processListPs = con.prepareStatement("SELECT id, user, host, db, command, time, state, info FROM information_schema.PROCESSLIST;");
                    processListPs = con.prepareStatement("SHOW FULL PROCESSLIST;");

                ResultSet rs = null;
                try{
                    rs = processListPs.executeQuery();

                    while(rs.next()){
                        ProcessListItem item = new ProcessListItem();
						/*item.setPid(rs.getLong(1));
						item.setUser(rs.getString(2));
						item.setHost(rs.getString(3));
						item.setDb(rs.getString(4));
						item.setCommand(rs.getString(5));
						item.setTime(rs.getLong(6));
						item.setState(rs.getString(7));
						item.setInfo(rs.getString(8));*/
                        item.setPid(rs.getLong("id"));
                        item.setUser(rs.getString("user"));
                        item.setHost(rs.getString("host"));
                        item.setDb(rs.getString("db"));
                        item.setCommand(rs.getString("command"));
                        item.setTime(rs.getLong("time"));
                        item.setState(rs.getString("state"));
                        item.setInfo(rs.getString("info"));

                        items.add(item);
                    }
                }
                finally{
                    if(rs!=null) rs.close();
                }
            }

            ProcessList processList = new ProcessList(items, this);
            //notify all

            Set<ProcessListListener> tempSet = new HashSet<ProcessListListener>(processListListeners);
            for(ProcessListListener listener : tempSet)
                listener.onProcessListGenerated(processList);
        }
        catch (SQLException e) {
            onError(e);
        }
    }


     public Map<String,String> generateSchema(){

        if(status != ServerStatus.CONNECTED)
            return null;
         System.out.println("dfhsjdfsd");
       Map<String,String> newSchema=new HashMap<String,String>();
        List<String>  list=new ArrayList<String>();
        synchronized (con) {
            ResultSet rs = null;
            ResultSet rs1=null;
            try{
            if(schemaPs==null)
                schemaPs = con.prepareStatement("show tables;");
                rs = schemaPs.executeQuery();

                while(rs.next()){
                     list.add(rs.getString(1));
                    System.out.println(rs.getString(1));
                }
                for(String str:list){
                    PreparedStatement ps=con.prepareStatement("Select COUNT(*) from '"+str+"'");
                     rs1=ps.executeQuery();
                    while(rs1.next()){
                        newSchema.put(str,rs.getString(1));
                        System.out.println(rs1.getString(1));
                    }
                    ps.close();
                    schemaVariables = Collections.unmodifiableMap(newSchema);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                try {
                    rs.close();
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return  schemaVariables;
    }

    private boolean downloadStatusVariables(){
        if(status != ServerStatus.CONNECTED)
            return false;

		/*if(serverVariablesListeners.size()==0)
			return false;*/

        try{
            Map<String, String> newStatusVariables = new HashMap<String, String>();
            synchronized (con) {
                if(statusVariablesPs==null)
                    statusVariablesPs = con.prepareStatement("show global status;");

                ResultSet rs = null;
                try{
                    rs = statusVariablesPs.executeQuery();

                    while(rs.next()){
                        newStatusVariables.put(rs.getString(1), rs.getString(2));
                    }
                }
                finally{
                    if(rs!=null) rs.close();
                }
            }
            statusVariables = Collections.unmodifiableMap(newStatusVariables);
            return true;
        }
        catch (SQLException e) {
            onError(e);
            return false;
        }
    }

    private boolean downloadServerVariables(){
        if(status != ServerStatus.CONNECTED)
            return false;

		/*if(serverVariablesListeners.size()==0)
			return false;*/

        try{
            Map<String, String> newServerVariables = new HashMap<String, String>();
            synchronized (con) {
                if(serverVariablesPs==null)
                    serverVariablesPs = con.prepareStatement("show global variables;");

                ResultSet rs = null;
                try{
                    rs = serverVariablesPs.executeQuery();

                    while(rs.next()){
                        newServerVariables.put(rs.getString(1), rs.getString(2));
                    }
                }
                finally{
                    if(rs!=null) rs.close();
                }
            }

            serverVariables = Collections.unmodifiableMap(newServerVariables);
            return true;
        }
        catch (SQLException e) {
            onError(e);
            return false;
        }
    }

    public void killQuery(long pid){
        if(status != ServerStatus.CONNECTED)
            return;

        try{
            synchronized (con) {
                Statement stmt = null;
                try{
                    stmt = con.createStatement();
                    stmt.execute("KILL QUERY "+pid);
                }
                finally{
                    if(stmt != null) stmt.close();
                }
            }
        }
        catch (SQLException e) {
            onError(e);
        }
    }

    private void notifyServerVariables(){
        ServerVariables sv = new ServerVariables(statusVariables, serverVariables, this);

        Set<ServerVariablesListener> tempSet = new HashSet<ServerVariablesListener>(serverVariablesListeners);
        for (ServerVariablesListener listener : tempSet) {
            listener.onServerVariablesDownloaded(sv);
        }
    }


    @Override
    public void run() {
        setupConnection();
        generateProcessList();
        boolean statusVar = downloadStatusVariables();
        boolean serverVar = downloadServerVariables();
        if(statusVar && serverVar)
            notifyServerVariables();
    }

    @Override
    public String toString() {
        return name;
    }
}

