/**
 * Project Name:ADSN
 * File Name:NodeTime.java
 * Package Name:bean
 * Date:2016年1月3日下午9:24:12
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package bean;
/**
 * ClassName:NodeTime <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月3日 下午9:24:12 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class NodeTime {
	private int year;
	private int mouth;
	private int day;
	private int hours;
	private int minute;
	private int second;
	/**
	 * @return  the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * year.
	 * @param   year    the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return  the mouth
	 * 
	 */
	public int getMouth() {
		return mouth;
	}
	/**
	 * mouth.
	 * @param   mouth    the mouth to set
	 */
	public void setMouth(int mouth) {
		this.mouth = mouth;
	}
	/**
	 * @return  the date
	 */
	public int getDay() {
		return day;
	}
	/**
	 * date.
	 * @param   date    the date to set
	 */
	public void setDay(int date) {
		this.day = date;
	}
	/**
	 * @return  the hours
	 */
	public int getHours() {
		return hours;
	}
	/**
	 * hours.
	 * @param   hours    the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
	/**
	 * @return  the minute
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * minute.
	 * @param   minute    the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	/**
	 * @return  the second
	 */
	public int getSecond() {
		return second;
	}
	/**
	 * second.
	 * @param   second    the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
	}
	public void EdsTime(String time){
		String[] strs=time.split(" ");
		String str1=strs[0];
		String str2=strs[1];
		String[] strs1=str1.split("-");
		String[] strs2=str2.split(":");
		this.setYear(Integer.valueOf(strs1[0]));
		this.setMouth(Integer.valueOf(strs1[1]));
		this.setDay(Integer.valueOf(strs1[2]));
		this.setHours(Integer.valueOf(strs2[0]));
		this.setMinute(Integer.valueOf(strs2[1]));
		this.setSecond(Integer.valueOf(strs2[2]));
	}
       
}

