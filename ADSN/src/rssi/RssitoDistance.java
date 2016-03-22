/**
 * Project Name:ADSN
 * File Name:RssitoDistance.java
 * Package Name:rssi
 * Date:2016年1月5日下午8:55:02
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package rssi;
/**
 * ClassName:RssitoDistance <br/>
 * Function: 根据节点RSSI值获算出节点间距离
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月5日 下午8:55:02 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class RssitoDistance {
	double rssitoDistance(double rssi,int a, double n)
    {
        double ra = Math.abs(rssi);  //求出rssi的绝对值
        double ka = (ra - a) / (10 * n);
        double distance = Math.pow(10, ka);//求出10的ka次方
        return distance;
    }
	public static void main(String[] agrs){
		 RssitoDistance a=new RssitoDistance();
		 System.out.println(a.rssitoDistance(205, 211, 422));
	}
}

