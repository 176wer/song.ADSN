/**
 * Project Name:ADSN
 * File Name:Experiment.java
 * Package Name:bean
 * Date:2016��1��1������4:32:41
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * Project Name:ADSN
 * File Name:Experiment.java
 * Package Name:bean
 * Date:2016��1��1������4:32:41
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
 */

package bean;
/**
 * ClassName:Experiment <br/>
 * Function: simple bean for recorder information
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016��1��1�� ����4:32:41 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
 
public class Experiment {
 
 /**
 * MaxId:ĿǰΪֹ���е�ʵ�����.
 * 
 */
private String MaxId;
 
/**
 * @return  the maxId
 */
public String getMaxId() {
	return MaxId;
}

/**
 * maxId.
 * @param   maxId    the maxId to set
 */
public void setMaxId(String maxId) {
	MaxId = maxId;
}

/**
 * @Fields:id
 * @Description:ZigBeeʵ��Ĵ���
 */
private String id;

/**
* @Fields:directions;
* @Description:��ʵ��Ŀ�Ľ���˵��
*/
private String directions;

/**
* @Fields:date
* @Description:ʵ��Ŀ�ʼ����
*/
private String date;

/**
 * @return  the id
 */
public String getId() {
	return id;
}

/**
 * id.
 * @param   id    the id to set
 */
public void setId(String id) {
	this.id = id;
}

/**
 * @return  the directions
 */
public String getDirections() {
	return directions;
}

/**
 * directions.
 * @param   directions    the directions to set
 */
public void setDirections(String directions) {
	this.directions = directions;
}

/**
 * @return  the date
 */
public String getDate() {
	return date;
}

/**
 * date.
 * @param   date    the date to set
 */
public void setDate(String date) {
	this.date = date;
}


/**  
* @Title: getTabelData  
* @Description: Ϊ�˷���ʹ���Զ���TableModel  
* @return String    ��������  
* @throws  
*/
public String getTabelData(int col){
	if(col==0){
		return id;
	}else if(col==1){
		return directions;
	}else{
		return date;
	}
}

public String toString(){
	return id;
}

}

