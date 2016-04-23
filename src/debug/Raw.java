package debug;

import java.util.Random;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.ArrayToString;

public class Raw extends Thread{
	private boolean flag=true;
	private DefaultTableModel model;
	public Raw(JTable table){
		this.model=(DefaultTableModel) table.getModel();
	}
	public void run(){
		Random rand=new Random();
		while(flag){
			try {
				Thread.sleep(200);
				int temp=rand.nextInt(3)+13;
				int voletage=20;
				String rssi="E"+rand.nextInt(2);
				String light="0"+rand.nextInt(3);
				int humdity=rand.nextInt(3)+20;
				String zhen="0"+rand.nextInt(1);
				String  addr="6F7"+rand.nextInt(9);
				String data="FE0E4687"+addr+"02000800"+temp+voletage+"0000"+rssi+light+humdity+zhen+"2C";
				Object[] tdata = {data+ "\n"};
				model.addRow(tdata);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void setFlag(boolean flag){
		this.flag=flag;
	}

}
