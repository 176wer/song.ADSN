/**
 * Project Name:ADSN
 * File Name:ToboToLib.java
 * Package Name:lib
 * Date:2016年3月8日下午9:29:13
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package lib;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import core.ZEllipse;

/**
 * ClassName:ToboToLib <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月8日 下午9:29:13 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ToboToLib extends Thread{
	private DefaultMutableTreeNode rootNode;
	public ToboToLib(DefaultMutableTreeNode rootNode){
		this.rootNode=rootNode;
	}
	public void run(){
	Enumeration enumeration=rootNode.breadthFirstEnumeration();
	while(enumeration.hasMoreElements()){
		 DefaultMutableTreeNode child=(DefaultMutableTreeNode) enumeration.nextElement();
		 ZEllipse zel=(ZEllipse)child.getUserObject();
		 
	}
	}
	

}

