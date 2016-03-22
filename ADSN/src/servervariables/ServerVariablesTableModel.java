package servervariables;

/**
 * Created by Administrator on 2015/12/26.
 */
import bean.ServerStatus;
import bean.ServerVariables;
import core.MysqlServer;
import core.ServerVariablesListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;



public class ServerVariablesTableModel extends AbstractTableModel implements ServerVariablesListener {

    public static final int SERVER_VARIABLES = 0;
    public static final int STATUS_VARIABLES = 1;

    private MysqlServer[] servers;

    private List<MysqlServer> activeServers = new ArrayList<MysqlServer>();
    private List<String> activeVariables = new ArrayList<String>();
    private Map<MysqlServer, Map<String, String>> activeData = new HashMap<MysqlServer, Map<String,String>>();

    private List<MysqlServer> tempServers = new ArrayList<MysqlServer>();
    private List<String> tempVariables = new ArrayList<String>();
    private Map<MysqlServer, Map<String, String>> tempData = new HashMap<MysqlServer, Map<String,String>>();


    private int type;

    public ServerVariablesTableModel(MysqlServer[] servers, int type) throws Exception{
        this.servers = servers;
        this.type = type;
        loadData();
    }

    private static final long serialVersionUID = 1L;

    public int getColumnCount() {
        return activeServers.size()+1;
    }

    public int getRowCount() {
        return activeVariables.size();
    }

    public Object getValueAt(int row, int col) {
        String k = activeVariables.get(row);
        if(col==0) return k;

        MysqlServer server = activeServers.get(col-1);
        Map<String,String> serverData = activeData.get(server);
        if(serverData == null)
            return null;

        return serverData.get(k);
    }

    public String getColumnName(int col){
        if(col==0) return "Variable";
        return activeServers.get(col-1).getName();
    }


    public void loadData(){
        tempVariables.clear();
        tempData.clear();
        tempServers.clear();

        for (MysqlServer server : servers) {
            if(server.getStatus() == ServerStatus.CONNECTED){
                tempServers.add(server);
                server.addServerVariablesListener(this);
            }
        }
    }

    public synchronized void onServerVariablesDownloaded(ServerVariables vars) {
        vars.getServer().removeServerVariablesListener(this);

        Map<String, String> serverData = null;
        if(type==STATUS_VARIABLES)
            serverData = vars.getStatusVariables();
        else if(type==SERVER_VARIABLES)
            serverData = vars.getServerVariables();

        for (String variable : serverData.keySet()) {
            if(!tempVariables.contains(variable))
                tempVariables.add(variable);
        }

        tempData.put(vars.getServer(), serverData);

        if(tempData.size() == tempServers.size()){
            activeData = new HashMap<MysqlServer, Map<String,String>>(tempData);
            activeServers = new ArrayList<MysqlServer>(tempServers);
            activeVariables = new ArrayList<String>(tempVariables);
            fireTableStructureChanged();
        }
    }

}
