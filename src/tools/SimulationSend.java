package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

/** 
 * Function: 模拟往串口发送数据
 * Reason: 做数据处理
 * date: 2016年4月11日 下午3:12:33 <br/> 
 * @author zgs 
 * @since JDK 1.8 
 */  
public class SimulationSend extends Thread{
	private Connection conn=null;
	public SimulationSend(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/adsn";
			conn=DriverManager.getConnection(url,"root","123");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(){
		//随机生成参数值
		Random rand=new Random();
		while(true){
			String data="FE0E46876F7902000800";
		}
 
	
	}

}
