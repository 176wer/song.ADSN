/**
 * Project Name:ADSN
 * File Name:Sort.java
 * Package Name:etl
 * Date:2016��1��4������3:51:50
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package etl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

/**
 * ClassName:Sort <br/>
 * Function: ��ԭʼ���ݽ��г�������
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016��1��4�� ����3:51:50 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Sort {
	
	//�����ӳػ�ȡ����
	 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
     Connection conn=pool.getConnection();
     Connection conn1=pool.getConnection();
     public void deal(){
    	 String sql="select * from temp";
    	 String insertTemp;
    	    
    	 try{
    		 Statement stm=conn.createStatement();
 		    Statement stm1=conn1.createStatement();
 			ResultSet rs=stm.executeQuery(sql);
 			while(rs.next()){
 			int number=rs.getInt(2);
 			String addr=rs.getString(6);
 			String time=rs.getString(13);
 			String temperature=rs.getString(9);
 			
 			
 			 //ԭʼ��ѹ����Ҫת��Ϊ10���ƣ��ڳ���10
 			try{
 				Integer voltag=Integer.valueOf(rs.getString(10).trim(),16);
 				double voltag1=(double)voltag/10;
 				String voltage=String.valueOf(voltag1);
 				
 				
 				 
 				Integer light=Integer.valueOf(rs.getString(14), 16);
 				
 				Integer humdity=Integer.valueOf(rs.getString(15), 16);
 			    //String humdity=String.valueOf(humdit);
 				
 				String vibration=rs.getString(16);
 				
 				//��·���¶�ȥ��
 				if(!temperature.equals("255")){
 					insertTemp="insert into temperature values("+number+",'"+addr+"','"+temperature+"','"+time+"')";
 					 stm1.execute(insertTemp);
 				}
 				//ʪ�����ݳ��ִ�����������ж�һ��
 				if(humdity<100){
 					insertTemp="insert into humidity values("+number+",'"+addr+"','"+humdity+"','"+time+"')";
 					 stm1.execute(insertTemp);
 				}
 				
 				
 				 insertTemp="insert into light values("+number+",'"+addr+"','"+light+"','"+time+"')";
 				 stm1.execute(insertTemp);
 				 insertTemp="insert into voltage values("+number+",'"+addr+"','"+voltage+"','"+time+"')";
 				 stm1.execute(insertTemp);
 				 insertTemp="insert into vibration values("+number+",'"+addr+"','"+vibration+"','"+time+"')";
 				 stm1.execute(insertTemp);
 			}catch(Exception e){
 				e.printStackTrace();
 			}
 			
 			 
 			
 			
 			}
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
			
		 
     }
   public static void main(String[]agrs){
	   Sort sort=new Sort();
	   sort.deal();
   }
}

