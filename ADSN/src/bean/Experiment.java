/**
 * Project Name:ADSN
 * File Name:Experiment.java
 * Package Name:bean
 * Date:2016年1月1日下午4:32:41
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * Project Name:ADSN
 * File Name:Experiment.java
 * Package Name:bean
 * Date:2016年1月1日下午4:32:41
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
 */

package bean;
/**
 * ClassName:Experiment <br/>
 * Function: simple bean for recorder information
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月1日 下午4:32:41 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
 
public class Experiment {
 
 /**
 * MaxId:目前为止进行的实验次数.
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
 * @Description:ZigBee实验的次数
 */
private String id;

/**
* @Fields:directions;
* @Description:对实验目的进行说明
*/
private String directions;

/**
* @Fields:date
* @Description:实验的开始日期
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
* @Description: 为了方便使用自定义TableModel  
* @return String    返回类型  
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

