/**
 * Project Name:ADSN
 * File Name:ViewExper.java
 * Package Name:gui.toolbar
 * Date:2016年1月2日下午3:02:26
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package gui.toolbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import control.XMLReader;
import model.MyTableModel;

/**
 * ClassName:ViewExper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月2日 下午3:02:26 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ViewExper extends JDialog {
	private JPanel panel;
	private JButton btnNewButton;

	 

	/**
	 * Create the dialog.
	 */
	public ViewExper() {
		setBounds(100, 100, 661, 406);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 10, 625, 325);
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);
		
		btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(570, 345, 65, 23);
		getContentPane().add(btnNewButton);
		
		List reader=XMLReader.getXMLReader().getExperiments();
	    JTable table = new JTable(new MyTableModel(reader));
	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);
	    //Create the scroll pane and add the table to it.
	    JScrollPane scrollPane = new JScrollPane(table);
	    //Add the scroll pane to this panel.
	    panel.add(scrollPane,BorderLayout.CENTER);
		 
	}
}

