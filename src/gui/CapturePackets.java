package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

@SuppressWarnings("serial")
public class CapturePackets extends JDialog  implements TreeSelectionListener{
	private JTable table;
	private JTabbedPane jtable;
	private  JTree tree;
	private int index=0;

	 

	/**
	 * Create the dialog.
	 */
	public CapturePackets() {
		setBounds(100, 100, 905, 723);
		getContentPane().setLayout(new BorderLayout());
		setTitle("数据包的捕获");
        //splitPane1位0，左右布局
		JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		getContentPane().add(splitPane1, BorderLayout.CENTER);
		splitPane1.setDividerLocation(150);

		JSplitPane splitPane = new JSplitPane();
		
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane1.setRightComponent(splitPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"4", "A7", "0xD", "0x8841", "0x2010", "0xBBBB", "0xAAAA", "FFF", "2"},
			},
			new String[] {
				"Preamble", "SFD", "Frane_Control", "Seq.Num", "Dest.PAN", "Dest.Address", "Source.Address", "Frame PayLoad", "FCS"
			}
		));
		
		jtable=new JTabbedPane();
		splitPane.setLeftComponent(jtable);
		
		

		JScrollPane sroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	 
		jtable.addTab(null, sroll);
		jtable.setTabComponentAt(0, new JLabel("数据包捕获"));

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(null);

		JLabel lblSentPacket = new JLabel("Sent Packet");
		lblSentPacket.setBounds(38, 26, 79, 15);
		panel.add(lblSentPacket);

		JLabel lblNewLabel = new JLabel("Channel");
		lblNewLabel.setBounds(38, 62, 60, 15);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Output power");
		lblNewLabel_1.setBounds(38, 99, 79, 15);
		panel.add(lblNewLabel_1);

		JLabel label = new JLabel("0");
		label.setBounds(158, 26, 54, 15);
		panel.add(label);

		JLabel lblNewLabel_2 = new JLabel("0x0B");
		lblNewLabel_2.setBounds(158, 62, 54, 15);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("1 dBm");
		lblNewLabel_3.setBounds(158, 99, 54, 15);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Average RSSI");
		lblNewLabel_4.setBounds(397, 26, 79, 15);
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("--");
		lblNewLabel_5.setBounds(511, 26, 54, 15);
		panel.add(lblNewLabel_5);

		JLabel lblPacket = new JLabel("Packet erro rate");
		lblPacket.setBounds(397, 62, 107, 15);
		panel.add(lblPacket);

		JLabel label_1 = new JLabel("--");
		label_1.setBounds(511, 62, 54, 15);
		panel.add(label_1);

		JButton btnNewButton = new JButton("Start Capture");
		btnNewButton.setBounds(38, 153, 118, 23);
		panel.add(btnNewButton);

		JButton btnStop = new JButton("Stop Cature");
		btnStop.setBounds(236, 153, 128, 23);
		panel.add(btnStop);
		
		
      
        tree=new JTree();
        tree.addTreeSelectionListener(this);
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("数据包分析");
        DefaultMutableTreeNode r1=new DefaultMutableTreeNode("数据包比例图");
        DefaultMutableTreeNode r2=new DefaultMutableTreeNode("历史数据显示");
        DefaultMutableTreeNode r3=new DefaultMutableTreeNode("多播数据显示");
        
        root.add(r2);
        root.add(r1);
        root.add(r3);
        
        DefaultTreeModel model=new DefaultTreeModel(root);
        tree.setModel(model);
        JScrollPane scroll=new JScrollPane(tree);
        splitPane1.setLeftComponent(scroll);
	}
	public void reIndex(){
		index--;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
    	String select=(String)node.getUserObject();
        if(select.equals("数据包比例图")){
        	index++;
        	 
        	jtable.add(select, new PacketProportion());
       jtable.setTabComponentAt(index, new TabComponent(jtable,this));
        	
        }else if(select.equals("历史数据显示")){
        	index++;
        	jtable.add(select, new JPanel());
       jtable.setTabComponentAt(index, new TabComponent(jtable,this));
        	
        }
	}
}
