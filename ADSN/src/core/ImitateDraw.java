package core;

import java.util.Random;

public class ImitateDraw extends Thread {
	private DrawCurve1 draw;
	private boolean flag=true;
	
	public ImitateDraw(DrawCurve1 draw){
		this.draw=draw;
	}
	public void run(){
		Random r=new Random();
		while(flag){
			try {
				Thread.sleep(500);
				int d1=r.nextInt(10);
				int d2=r.nextInt(8);
				int d3=r.nextInt(5);
				int d4=r.nextInt(9);
				draw.add(d1,d2,d3,d4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	

}
