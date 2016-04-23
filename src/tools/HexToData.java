package tools;/**
 * Created by zgs on 2016/4/16.
 */

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 2016/4/16  <br/>
 *
 * @author Mr.Zhao
 * @since JDK 1.8
 */
public class HexToData extends Thread {
    private Connection conn;
    private String S_table = "super_raw";//初始清洗的A表名
    private int i=0;

    public HexToData() {
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        conn = pool.getConnection();
    }

    public void run() {
        try {
            Statement stm=conn.createStatement();
            Statement stm1=conn.createStatement();
            while (true) {
                String sql = "select * from " + S_table+" limit "+i+",1000";
                ResultSet rs=stm.executeQuery(sql);
                while(rs.next()){
                    String hex = rs.getString(1);
                    String date = rs.getString(2);
                    String[] datas = CleanRecorder.getDeal(hex);
                    String sql2="insert into ";

                }
                i=i+1000;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
