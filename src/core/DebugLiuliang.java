package core;

import java.util.Random;

import gui.LiuLiang;

public class DebugLiuliang extends Thread {
	
	private LiuLiang liuliang;
	private boolean flag=true;
	public DebugLiuliang(LiuLiang liuliang){
		this.liuliang=liuliang;
	}
	public void run(){
		Random random=new Random();
		while(flag){
		try {
			int r=random.nextInt(5)+10;
			Thread.sleep(50);
			liuliang.addData(r);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	
	
}
