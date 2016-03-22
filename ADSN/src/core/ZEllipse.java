/**
 * Project Name:ADSN
 * File Name:ZEllipse.java
 * Package Name:core
 * Date:2016年3月3日上午8:41:54
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package core;

import java.awt.geom.Ellipse2D;

/**
 * ClassName:ZEllipse <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月3日 上午8:41:54 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ZEllipse extends Ellipse2D.Float {



    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 1L;




	public ZEllipse(float x, float y, float width, float height) {

        setFrame(x, y, width, height);

    }



    public boolean isHit(float x, float y) {

        return getBounds2D().contains(x, y);
    }

    public void addX(float x) {

        this.x += x;
    }

    public void addY(float y) {

        this.y += y;
    }

    public void addWidth(float w) {

        this.width += w;

    }

    public void addHeight(float h) {

        this.height += h;
    }
    public void setCoordinate(float x, float y, float width, float height){
        setFrame(x, y, width, height);
    }

    private String zhentou;
    private String length;
    private String Type;
    private String addr="0000";
    private String  word;
    private String datalength;
    private String temp;
    private String voltage;
    private String route;
    private String light;
    private String humidity;
    private String check;
    private String time;
    private int status=1;//是否为路由节点,1为协调器，2为路由，3为节点
    private String vibration="1";//振动
    private int rssi=0;//用于测距
    //判断是否为路由节点，




    /**
     * @return  the rssi
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * rssi.
     * @param   rssi    the rssi to set
     */
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    /**
     * @return  the vibration
     */
    public String getVibration() {
        return vibration;
    }

    /**
     * vibration.
     *
     * @param   vibration    the vibration to set
     * @since   JDK 1.6
     */
    public void setVibration(String vibration) {
        this.vibration = vibration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ZEllipse(String s) {
        addr=s;
    }
    public ZEllipse(String[] str){
       
        	 setZhentou(str[0]);
             setLength(str[1]);
             setType(str[3] + str[2]);
             setAddr(str[5] + str[4]);
             setWord(str[7] + str[6]);
             setDatalength(str[9] + str[8]);
             int s=Integer.valueOf(str[10],16);
             String s1=String.valueOf(s);
             setTemp(s1);

             //判断是否为路由
             if (str[10].equals("FF")) {
                 setStatus(2);
             } else {
                 setStatus(3);
             }
            
            try{
            	 double vol = Integer.valueOf(str[11],16);
                 double vol1=vol/10;
                 String vol2 = String.valueOf(vol1);
                 setVoltage(vol2);
                 setRoute(str[13]+str[12]);
                 setCheck(str[14]);
            	   int l1=Integer.valueOf(str[15],16);
                   String l2 = String.valueOf(l1);
                   setLight(l2);

                   int h1=Integer.valueOf(str[16],16);
                   String h2 = String.valueOf(h1);
                   setHumidity(h2);

                   int v1=Integer.valueOf(str[17],16);
                   String v2 = String.valueOf(v1);

                   setVibration(v2);
                   setRssi(Integer.valueOf(str[18], 16));
            }catch(Exception e){
            	System.out.println("这里出错了");
            	e.printStackTrace();
            }
          
             //   node.setLight(str[14]);
             //  node.setHumidity(str[15]);
         
       


    }
    public String toString(){

        return addr;
    }
    public String infor(){
        return "<html>温度:"+temp+"<br/>湿度:"+humidity+"<br/>光强:"+light+"<br/>震动:"+vibration+"<br/>RSSI:"+rssi+"</html>";
    }
    public String getZhentou() {
        return zhentou;
    }


    public int  getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status=status;
    }





    public void setZhentou(String zhentou) {
        this.zhentou = zhentou;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDatalength() {
        return datalength;
    }

    public void setDatalength(String datalength) {
        this.datalength = datalength;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getVoltage() {
        return voltage;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }


}
