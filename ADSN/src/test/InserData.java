package test;

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by zgs on 2016/3/29.
 */
public class InserData {
    public static void main(String[] agrs){
        IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
        Connection conn = pool.getConnection();
        try {
            Statement stm=conn.createStatement();
            Random r = new Random();
            for( int i=0;i<1000;i++){
                int temp=r.nextInt(10);
                int hum = r.nextInt(10) + 10;
                int light=r.nextInt(10)+100;
                String sql="insert into ntime values('2016-03-30 19:11:19','05AE',"+temp+","+hum+","+light+",0)";
                stm.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
