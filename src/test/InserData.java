package test;

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by zgs on 2016/3/29.
 */
public class InserData {
    public static void main(String[] agrs){
      
        try {
        	 Class.forName("com.mysql.jdbc.Driver");
             String url="jdbc:mysql://localhost:3306/test";
             Connection conn=DriverManager.getConnection(url,"root","123");
             Statement stm=null;
        
            Random r = new Random();
            long time=System.currentTimeMillis();
            for( int i=0;i<10000;i++){
            	stm=conn.createStatement();
                int temp=r.nextInt(10);
                int hum = r.nextInt(10) + 10;
                int light=r.nextInt(10)+100;
                String sql="insert into ntime values('2016-03-30 19:11:19','05AE',"+temp+","+hum+","+light+",0)";
                stm.execute(sql);
                stm.close();
            }
            System.out.println(System.currentTimeMillis()-time);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
