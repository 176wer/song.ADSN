/**
 * Project Name:ADSN
 * File Name:TransformData.java
 * Package Name:etl
 * Date:2016��1��5������2:29:29
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package etl;

import java.sql.Connection;

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

/**
 * ClassName:TransformData <br/>
 * Function: ��temp������ݵ���primeval
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016��1��5�� ����2:29:29 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TransformData {
     public static void main(String[] agrs){
    	 IConnectionPool pool=ConnectionPoolManager.getInstance().getPool("mysql");
         Connection conn=pool.getConnection();
     }
}

