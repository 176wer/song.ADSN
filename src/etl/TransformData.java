/**
 * Project Name:ADSN
 * File Name:TransformData.java
 * Package Name:etl
 * Date:2016年1月5日下午2:29:29
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package etl;

import java.sql.Connection;

import pool.ConnectionPoolManager;
import pool.IConnectionPool;

/**
 * ClassName:TransformData <br/>
 * Function: 把temp里的数据导入primeval
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月5日 下午2:29:29 <br/>
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

