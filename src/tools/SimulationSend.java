package tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/** 
 * Function: 模拟往串口发送数据
 * Reason: 做数据处理
 * date: 2016年4月11日 下午3:12:33 <br/> 
 * @author zgs 
 * @since JDK 1.8 
 */  
public class SimulationSend extends Thread{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		long start=System.nanoTime();
		Random rand=new Random();
        Statement stm= null;
        try {
            stm = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i=1;i<10000;i++){

			int temp=rand.nextInt(3)+13;
			int voletage=20;
			String rssi="E"+rand.nextInt(2);
			String light="0"+rand.nextInt(3);
			int humdity=rand.nextInt(3)+20;
			String zhen="0"+rand.nextInt(1);
			String  addr="6F7"+rand.nextInt(9);
			String data="FE0E4687"+addr+"02000800"+temp+voletage+"0000"+rssi+light+humdity+zhen+"2C";
			Date date = new Date();
			String time = format.format(date);

			try {

                String sql="insert into raw values('"+data+"','"+time+"')";
				stm.execute(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
 
	System.out.println(System.nanoTime()-start);
	}
   public static void main(String[] agrs){

	   new SimulationSend().start();
   }
}
