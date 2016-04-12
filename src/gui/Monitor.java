package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Param;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Monitor extends JDialog  implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	 
	/**
	 * Create the dialog.
	 */
	public Monitor() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		 
		textField.setBounds(221, 43, 66, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("监控的温度");
		label.setBounds(73, 46, 72, 15);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(this);
				getRootPane().setDefaultButton(okButton);
			}
			
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="OK"){
			String data=textField.getText().trim();
			try{
				int data2=Integer.valueOf(data);
				Param.setTemp(data2);
			}catch(Exception e2){
				JOptionPane.showMessageDialog(null, "请输入正确的数值", "错误提示", JOptionPane.ERROR_MESSAGE);
			}
			this.setVisible(false);
			this.dispose();
			
		}else{
			this.setVisible(false);
			this.dispose();
		}
		
	}
}
