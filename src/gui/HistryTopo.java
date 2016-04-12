/**
 * Project Name:ADSN
 * File Name:HistryTopo.java
 * Package Name:gui
 * Date:2016年3月8日下午9:13:04
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package gui;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import core.Surface;
import core.ZEllipse;
import lib.Lib;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JSplitPane;

/**
 * ClassName:HistryTopo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年3月8日 下午9:13:04 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class HistryTopo implements ChangeListener {

	private JDialog dialog;
	private int MAX = 20;// 最大刻度数
	private int MIN = 0;// 最小刻度数
	private int Current = 0;// 当前刻度数
	private JSplitPane splitPane;
	private JSlider slider;
	private Connection conn;
	private ArrayList<ZEllipse> zels = new ArrayList<ZEllipse>();
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(new ZEllipse("0000"));
	private Surface panel;
	 
 
	/**
	 * Create the application.
	 */
	public HistryTopo() {
		Lib lib = new Lib();
		IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		conn = pool.getConnection();
		MAX = lib.getTopoMaxID();
		initialize();

		 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dialog = new JDialog();
		dialog.setVisible(true);
		dialog.setTitle("节点历史拓扑图");
		dialog.setBounds(100, 100, 1000, 690);
	 

		splitPane = new JSplitPane();
	    splitPane.setDividerLocation(20);
	    splitPane.setDividerSize(5);
		dialog.getContentPane().add(splitPane, BorderLayout.CENTER);

		slider = new JSlider(JSlider.VERTICAL, MIN, MAX, 0);
		slider.addChangeListener(this);
		splitPane.setLeftComponent(slider);
		
		panel = new Surface();
		splitPane.setRightComponent(panel);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
      
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			rootNode.removeAllChildren();
			int fps = (int) source.getValue();
			String sql = "select * from topo where number=" + fps;
			Statement stm = null;
			ResultSet rs = null;

			try {
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				rs.next();
				while (rs.next()) {
					ZEllipse zel = new ZEllipse(rs.getString(3));
					zel.setTime(rs.getString(1));
					zel.setAddr(rs.getString(3));
					zel.setRoute(rs.getString(4));
					zel.setTemp(rs.getString(5));
					zel.setVoltage(rs.getString(6));
					zel.setLight(rs.getString(7));
					zel.setHumidity(rs.getString(8));
					zel.setVibration(rs.getString(9));
					zel.setStatus(rs.getInt(10));
		
					addObject(zel);
				}

			} catch (SQLException e1) {

				// TODO Auto-generated catch block
				e1.printStackTrace();

			} finally {
				try {
					rs.close();
					stm.close();
				} catch (SQLException e1) {

					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

			}
			panel.refresh(rootNode);

		}

	}

	public  void  addObject(ZEllipse child) {
		 
		String route = child.getRoute();


		@SuppressWarnings("rawtypes")
		Enumeration enumeration = rootNode.breadthFirstEnumeration();
		while (enumeration.hasMoreElements()) {
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) enumeration.nextElement();
			String addr = parentNode.getUserObject().toString();

			
			if (addr.equals(route)) {
                  parentNode.add(new DefaultMutableTreeNode(child));
                 
				 
			}

		}

		 
	}
	

}
