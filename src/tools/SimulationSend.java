package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

/** 
 * Function: ģ�������ڷ�������
 * Reason: �����ݴ���
 * date: 2016��4��11�� ����3:12:33 <br/> 
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
		//������ɲ���ֵ
		Random rand=new Random();
		while(true){
			String data="FE0E46876F7902000800";
		}
 
	
	}

}
