package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import core.Surface1;
import core.ZEllipse;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class PlaneTopology extends JDialog   {

	private final JPanel contentPanel = new JPanel();
	private int id=0;
	private DefaultMutableTreeNode rootNode ;
	private Surface1 surface;

	
	 

	/**
	 * Create the dialog.
	 */
	public PlaneTopology() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(20,20, (int)screenSize.getWidth()-40, (int)screenSize.getHeight()-80);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPanel.add(splitPane, BorderLayout.CENTER);
		
		surface = new Surface1();
		surface.setDoubleBuffered(true);
		splitPane.setRightComponent(surface);
		splitPane.setDividerLocation(50);
	 
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		BasicButton btnNewButton = new BasicButton("平面拓扑");
		panel_1.add(btnNewButton);
		
		BasicButton btnd = new BasicButton("3D拓扑");
		panel_1.add(btnd);
		
		BasicButton btnNewButton_2 = new BasicButton("保存");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		        Connection conn = pool.getConnection();
				Enumeration enu=rootNode.breadthFirstEnumeration();
				try{
					while(enu.hasMoreElements()){
						DefaultMutableTreeNode dft=(DefaultMutableTreeNode)enu.nextElement();
						ZEllipse z=(ZEllipse)dft.getUserObject();
						String nodeName=z.getAddr();
						int local_x=(int)z.getX();
						int local_y=(int)z.getY();
						String sql="update localtopo set local_x="+local_x+",local_y="+local_y+" where sensor_addr='"+nodeName+"'";
						Statement stm=conn.createStatement();
						stm.execute(sql);
						stm.close();
					}
					pool.releaseConn(conn);
				}catch(Exception e){
					
				}
				
				
			}
		});
		panel_1.add(btnNewButton_2);
		
		BasicButton btnNewButton_3 = new BasicButton("上一个");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				id++;
				IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
		        Connection conn = pool.getConnection();
		        String sql="select * from localtopo where id="+id+"";
		        ResultSet rs=null;
		        try {
					Statement stm=conn.createStatement();
					rs=stm.executeQuery(sql);
					
					rs.next();
					rs.getString(3);
					ZEllipse z = new ZEllipse(rs.getString(2));
					z.setAddr(rs.getString(2));
					z.setStatus(1);
					z.setCoordinate((float)rs.getInt(5), (float)rs.getInt(6), 40, 40);
					rootNode=new DefaultMutableTreeNode(z);
					 
					while(rs.next()){
						ZEllipse zel = new ZEllipse(rs.getString(2));
						
						zel.setRoute(rs.getString(3));
					    zel.setStatus(2);
						zel.setCoordinate((float)rs.getInt(5), (float)rs.getInt(6), 40, 40);
						 
			
						addObject(zel);
					}
					
				
					
					stm.close();
					rs.close();
					pool.releaseConn(conn);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,"拓扑已到最后一张");
				}
		        
		        surface.refresh(rootNode);
			}
		});
		panel_1.add(btnNewButton_3);
		
		BasicButton btnNewButton_4 = new BasicButton("下一个");
		panel_1.add(btnNewButton_4);
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
	class A extends JPanel{
		 protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon images=new ImageIcon(getClass().getResource("/image/big.png"));
		images.setImage(images.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST));
		images.paintIcon(this,g,0,0);
		
		 }
		 }
	 
	}


