/*
* GsPassword.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
package com.mrcc.adit;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *  Description of the Class
 *
 *@author     gsherman
 *@created    November 13, 2002
 */
class GsPassword extends JDialog {
	JPasswordField pf;
	String password;
	/**
	 *  Constructor for the GsPassword object
	 *
	 *@param  owner  Description of the Parameter
	 *@param  title  Description of the Parameter
	 *@param  modal  Description of the Parameter
	 */
	public GsPassword(Frame owner, String title, boolean modal) {
		super(owner, title, modal);



		JLabel prompt = new JLabel(title);
		pf = new JPasswordField(15);
		pf.addKeyListener(
		    new java.awt.event.KeyAdapter() {
			    public void keyPressed(KeyEvent evt) {
				    passwordKeyPressed(evt);
			    }
		    });
		password = null;
		getContentPane().setLayout(new FlowLayout());

		getContentPane().add(prompt);
		getContentPane().add(pf);

		pack();

		setLocationRelativeTo(owner);
		//pass.show();
	}
	private void passwordKeyPressed(KeyEvent evt) {

		//GEN-FIRST:event_jtxtSqlKeyPressed
		// Add your handling code here:

		int k = evt.getKeyCode();
		//System.out.println("KeyCode: " + k);
		if( k == KeyEvent.VK_ENTER){
			password = new String(pf.getPassword());
			hide();
		}

	}
	public String getPassword(){
		return password;
	}
}

