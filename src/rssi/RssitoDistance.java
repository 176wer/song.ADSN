/**
 * Project Name:ADSN
 * File Name:RssitoDistance.java
 * Package Name:rssi
 * Date:2016��1��5������8:55:02
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package rssi;
/**
 * ClassName:RssitoDistance <br/>
 * Function: ���ݽڵ�RSSIֵ������ڵ�����
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016��1��5�� ����8:55:02 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class RssitoDistance {
	double rssitoDistance(double rssi,int a, double n)
    {
        double ra = Math.abs(rssi);  //���rssi�ľ���ֵ
        double ka = (ra - a) / (10 * n);
        double distance = Math.pow(10, ka);//���10��ka�η�
        return distance;
    }
	public static void main(String[] agrs){
		 RssitoDistance a=new RssitoDistance();
		 System.out.println(a.rssitoDistance(205, 211, 422));
	}
}

