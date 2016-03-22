/**
 * Project Name:ADSN
 * File Name:ConfigFrame.java
 * Package Name:control
 * Date:2016年1月2日上午10:43:04
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import model.MyTableModel;

/**
 * ClassName:ConfigFrame <br/>
 * Function: 选择ZigBee实验次数，并进行实验说明
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月2日 上午10:43:04 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ConfigFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigFrame frame = new ConfigFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		 List<?> reader=XMLReader.getXMLReader().getExperiments();
		 JTable table = new JTable(new MyTableModel(reader));
	     table.setFillsViewportHeight(true);
	    //Create the scroll pane and add the table to it.
	     JScrollPane scrollPane = new JScrollPane(table);
	     panel.add(scrollPane,BorderLayout.CENTER);
	 
		panel.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(51, 153, 255)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "\u5DF2\u8FDB\u884C\u7684\u5B9E\u9A8C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 403, 291);
		contentPane.add(panel);
	}
	 
}

