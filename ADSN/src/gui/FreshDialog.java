package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import control.PublicContent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
/*
 *����ˢ��ʱ������
 */
public class FreshDialog extends JDialog  implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JButton btnNewButton;
    private SerialConnection con;
	 
		
	 

	/**
	 * Create the dialog.
	 */
	public FreshDialog(SerialConnection con) {
		this.con=con;
		setTitle("�ڵ�ˢ��");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "Seting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("�Զ�ˢ��");
		buttonGroup.add(chckbxNewCheckBox);
		chckbxNewCheckBox.setBounds(69, 29, 85, 23);
		contentPanel.add(chckbxNewCheckBox);
		chckbxNewCheckBox.setSelected(true);
		
		textField = new JTextField();
		textField.setBounds(156, 30, 66, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("��");
		label.setBounds(232, 33, 23, 15);
		contentPanel.add(label);
		
		btnNewButton = new JButton("ˢ��");
		btnNewButton.setActionCommand("refresh");
		btnNewButton.addActionListener(this);
		 
		btnNewButton.setBounds(69, 101, 93, 23);
		contentPanel.add(btnNewButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("OK")){
			try{
				String time1=textField.getText().trim();
				int time2=Integer.valueOf(time1);
				PublicContent.setFreshTime(time2*1000);
				this.dispose();
				 
			}catch(Exception e2){
				JOptionPane.showMessageDialog(contentPanel, "��������ȷ��ֵ", "ˢ��", JOptionPane.ERROR_MESSAGE);
			}
		}else if(e.getActionCommand().equals("refresh")){
			con.interrupt();
			System.out.println("djjj");
		}
		else{
			this.dispose();
		}
		
	}
}
