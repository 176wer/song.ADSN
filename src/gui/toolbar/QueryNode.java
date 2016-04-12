/**
 * Project Name:ADSN
 * File Name:QueryNode.java
 * Package Name:gui.toolbar
 * Date:2016年1月2日下午8:14:54
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package gui.toolbar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bean.Experiment;
import control.XMLReader;
import gui.NodeHistroy;
import pool.ConnectionPoolManager;
import pool.IConnectionPool;
import javax.swing.DefaultComboBoxModel;

/**
 * ClassName:QueryNode <br/>
 * Function: 输入节点短地址，显示相应的数据 Reason: TODO ADD REASON. <br/>
 * Date: 2016年1月2日 下午8:14:54 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class QueryNode extends JDialog {

	private final JPanel contentPanel = new JPanel();
	IConnectionPool pool = ConnectionPoolManager.getInstance().getPool("mysql");
	Connection conn = pool.getConnection();
	private JComboBox comboBox;
	private JTable table;
	private JScrollPane scrollPane;
	private String selectItem;
	private JComboBox comboBox_1;

	/**
	 * Create the dialog.
	 */
	public QueryNode() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(217, 27, 108, 192);
		contentPanel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "节点地址" }));

		List<Experiment> reader = XMLReader.getXMLReader().getExperiments();
		comboBox = new JComboBox();
		for (Experiment x : reader) {
			comboBox.addItem(x);
		}
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);
				JComboBox cb = (JComboBox) e.getSource();
				Experiment ex = (Experiment) cb.getSelectedItem();
				String Name = ex.getId().trim();
				

				String sql = "select * from na where number=" + Name;
				try {
					Statement stm = conn.createStatement();
					ResultSet rs = stm.executeQuery(sql);

					while (rs.next()) {
						String value = rs.getString(2);
						Object[] a = { value };
						model.addRow(a);
					}
				} catch (SQLException e1) {

					// TODO Auto-generated catch block
					e1.printStackTrace();

				}

			}
		});
		comboBox.setBounds(79, 38, 59, 21);
		contentPanel.add(comboBox);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"\u6E29\u5EA6", "\u6E7F\u5EA6", "\u5149\u7167", "\u632F\u52A8", "\u5149\u5F3A"}));
		comboBox_1.setBounds(79, 96, 59, 21);
		contentPanel.add(comboBox_1);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int col = table.getSelectedColumn();
				int row = table.getSelectedRow();
				try {
					String selectVaule = (String) model.getValueAt(row, col);
					Experiment ex = (Experiment) comboBox.getSelectedItem();
					String Name = ex.getId();

					// String sql="select * from temp where number="+Name+" and
					// addr='"+selectVaule+"' ";
					//
					// Statement stm=conn.createStatement();
					// ResultSet rs=stm.executeQuery(sql);
					NodeHistroy window = new NodeHistroy(selectVaule, Name);
					window.frame.setVisible(true);

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "请从表格中选择数据", "错误提示", JOptionPane.WARNING_MESSAGE);

				} finally {

				}

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
	public static void main(String[] agrs){
		QueryNode q=new QueryNode();
	}
}
