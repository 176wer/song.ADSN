package core;

import java.util.Random;

import gui.DynamicTree;
/*
 * 模拟串口发送数据
 */
public class ImitateSend extends Thread{
	private boolean flag=true;
	 DynamicTree treePane;
	public ImitateSend(DynamicTree dynamicTree){
	   treePane=dynamicTree;	 
	}
	public void run(){
		Random r=new Random();
		while(flag){
			try {
			
				ZEllipse  zellipse;
				for(int i=0;i<20;i++){
					String addr="0A"+i;
					  zellipse=new ZEllipse(addr);
					zellipse.setRoute("0000");
				    int s=r.nextInt(2)+2;
				    zellipse.setStatus(s);
				    
				    treePane.addObject(zellipse);
					
				}
				for(int i=0;i<10;i++){
					String addr="0E"+i;
					  zellipse=new ZEllipse(addr);
					  int rr=r.nextInt(20);
					  String route="0A"+rr;
					 
					  zellipse.setRoute(route);
					  
					  int s=r.nextInt(2)+2;
					    zellipse.setStatus(s);
					    treePane.addObject(zellipse);
					
					
				}
				
				for(int i=0;i<17;i++){
					String addr="0F"+i;
					  zellipse=new ZEllipse(addr);
					  int rr=r.nextInt(10);
					  String route="0E"+rr;
					  System.out.println(route);
					  zellipse.setRoute(route);
					  
					  int s=r.nextInt(2)+2;
					    zellipse.setStatus(s);
					    treePane.addObject(zellipse);
					
					
				}
				
				 
				
				
				Thread.sleep(10000);
				treePane.clear();
				treePane.getSet().clear();
			 
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
