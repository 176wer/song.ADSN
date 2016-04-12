/**
 * Project Name:ADSN
 * File Name:NodeLEList.java
 * Package Name:control
 * Date:2015年12月26日下午12:07:07
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import bean.NodeMark;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

/**
 * ClassName:NodeLEList <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年12月26日 下午12:07:07 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class NodeLEList {
	
	private HashMap<String,Integer> energy;
	private HashMap<String,NodeLife> life;
	IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
    Connection conn=pool.getConnection();
  
	public NodeLEList(HashMap<String, Integer> energy,HashMap<String, NodeLife> life){
		this.energy=energy;
		this.life=life;
		InList();
	}
	public void InList(){
	    Set<String> keys=life.keySet();
	    try {
			Statement stm=conn.createStatement();
			for(String key:keys){
				
		    	int ener=energy.get(key);
		    	 
		    	NodeLife lif=life.get(key);
		    	String life1=Long.toString(lif.getLife());
		    	 String time=new Date().toString();
		    	   String sql="insert into sumle values ('"+key+"',"+NodeMark.mark+",'"+life1+"','"+ener+"','"+time+"')";
		    	
		    	   stm.executeUpdate(sql);
		    }
			stm.close();
		
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			try {
				pool.releaseConn(conn);
			} catch (SQLException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	    
	}
	

}

