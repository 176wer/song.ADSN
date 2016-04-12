/**
 * Project Name:ADSN
 * File Name:NodeLife.java
 * Package Name:control
 * Date:2015��12��23������9:38:59
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package control;
/**
 * ClassName:NodeLife  
 * Function:  
 * Date:     2015��12��23�� ����9:38:59 <br/>
 * @author   �Թ���
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class NodeLife {
//�ڵ��Ƿ�����
private boolean isDead;
//�ڵ��������ʱ��ʱ��
private long start=System.nanoTime();
//�ڵ�����
private long life=0;
//�ڵ��ϲ�������ʱ��
private long idle=0;
//�ڵ���һ�β�����ʱ��
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
 * @return  the isDead �ڵ�״̬
 */
public boolean isDead() {
	return isDead;
}

/**
 * isDead
 * @param   isDead ���ýڵ�״̬
 */
public void setDead(boolean isDead) {
	this.isDead = isDead;
}

public void setNowTime(){
	NowTime=System.nanoTime();
}
//��ȡ�ܵĲ�����ʱ��
public void setSumIdleTime(){
   idle=idle+System.nanoTime()-NowTime;
}

}

