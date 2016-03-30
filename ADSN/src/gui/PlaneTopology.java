package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PlaneTopology extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PlaneTopology dialog = new PlaneTopology();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		splitPane.setDividerLocation(50);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		BasicButton btnNewButton = new BasicButton("平面拓扑");
		panel_1.add(btnNewButton);
		
		BasicButton btnd = new BasicButton("3D拓扑");
		panel_1.add(btnd);
		
		BasicButton btnNewButton_1 = new BasicButton("修改");
		panel_1.add(btnNewButton_1);
		
		BasicButton btnNewButton_2 = new BasicButton("保存");
		panel_1.add(btnNewButton_2);
	}

}
