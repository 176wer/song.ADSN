/**
 * Project Name:ADSN
 * File Name:NodeLife.java
 * Package Name:control
 * Date:2015年12月23日下午9:38:59
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package control;
/**
 * ClassName:NodeLife  
 * Function:  
 * Date:     2015年12月23日 下午9:38:59 <br/>
 * @author   赵广松
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class NodeLife {
//节点是否死亡
private boolean isDead;
//节点加入网络时的时间
private long start=System.nanoTime();
//节点寿命
private long life=0;
//节点上不在线总时间
private long idle=0;
//节点上一次不在线时间
private long NowTime=0;
/**
 * @return  the life
 */
public long getLife() {
	if(!isDead){
		life=System.nanoTime()-start-idle;
		return life;	
	}else{
		return life;
	}
	
}

/**
 * @param   life    the life to set
 */
public void setLife(int life) {
	this.life = life;
}

/**
 * @return  the isDead 节点状态
 */
public boolean isDead() {
	return isDead;
}

/**
 * isDead
 * @param   isDead 设置节点状态
 */
public void setDead(boolean isDead) {
	this.isDead = isDead;
}

public void setNowTime(){
	NowTime=System.nanoTime();
}
//获取总的不在线时间
public void setSumIdleTime(){
   idle=idle+System.nanoTime()-NowTime;
}

}

