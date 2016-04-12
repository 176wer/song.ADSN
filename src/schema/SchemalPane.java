package schema;

/**
 * Created by San on 2015/12/29.
 * name:
 * description:
 * remark:
 * question:
 */


import core.MysqlServer;
import core.ServerManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;



/**
 * Created by Administrator on 2015/12/26.
 */

public class SchemalPane extends JPanel implements Runnable{
    MysqlServer[] servers=null;


    private String host;
    private int port;
    private String username;
    private String password;
    private String db;
    private DefaultTableModel model;
    private Connection con=null;
    public SchemalPane(String group){
        setLayout(new BorderLayout());
        servers=  ServerManager.getInstance().getServersByGroup(group);
        System.out.println(servers.length);
        //Object[] columnName={"表名","记录数"};
        Object[] columnName={"表名","记录数"};
        model=new DefaultTableModel();
        model.setDataVector(null,columnName);
        MysqlServer server=servers[0];
        host=server.getHost();
        port=server.getPort();
        username=server.getUsername();
        password=server.getPassword();
        db=server.getDb();
        initDb();


        JTable table=new JTable(model);
        JScrollPane scrollPane=new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);
    }
    public void initDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://"+host+":"+port+"/"+db;
            con= DriverManager.getConnection(url,username,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(){
        /**
         *
         */
        PreparedStatement schemaPs=null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        ResultSet rs1=null;
        ArrayList<String> list=new ArrayList<String>();

        try{

            schemaPs = con.prepareStatement("show tables;");
            rs = schemaPs.executeQuery();

            while(rs.next()){
                list.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }

            for(String str:list){
                ps=con.prepareStatement(" Select COUNT(*) from  "+ str );
                String a="Select COUNT(*) from "+str+";" ;
                System.out.println(a);
                rs1=ps.executeQuery();
                while(rs1.next()){
                    System.out.println(rs1.getString(1));
                    Object[] data={str,rs1.getString(1)};
                    model.addRow(data);

                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{

            try {
                schemaPs.close();
                ps.close ();
                rs.close();
                rs1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void run() {
        while(true){
            model.setRowCount(0);
            update();

            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
