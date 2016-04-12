/*
* GsMessageDialog.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
 package com.mrcc.adit;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *  Description of the Class
 *
 *@author     gsherman
 *@created    October 29, 2002
 */
class GsMessageDialog extends javax.swing.JDialog {
	private JTextArea jtxtMsg;
	private JScrollPane jscrMsgPane;
	private JLabel jlblMsgTitle;
	private JButton btnClose;
	private JPopupMenu jpopSqlTxtMenu;
	private JMenuItem sqlTxtMenuCut;
	private JMenuItem sqlTxtMenuCopy;
	private JMenuItem sqlTxtMenuPaste;


	/**
	 *  Constructor for the GsMessageDialog object
	 *
	 *@param  owner  Description of the Parameter
	 *@param  title  Description of the Parameter
	 *@param  modal  Description of the Parameter
	 */
	public GsMessageDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);

		// set up the popup menu
		jpopSqlTxtMenu = new JPopupMenu();
		sqlTxtMenuCut = new JMenuItem();
		sqlTxtMenuCopy = new JMenuItem();
		sqlTxtMenuPaste = new JMenuItem();

		sqlTxtMenuCut.setText("Cut");
		sqlTxtMenuCut.setToolTipText("Cut text and add to clipboard");
		sqlTxtMenuCut.addMouseListener(
			new java.awt.event.MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					sqlTxtMenuCutMouseReleased(evt);
				}
			});

		//jpopSqlTxtMenu.add(sqlTxtMenuCut);
		sqlTxtMenuCopy.setText("Copy");
		sqlTxtMenuCopy.setToolTipText("Copy selected text to clipboard");
		sqlTxtMenuCopy.addMouseListener(
			new java.awt.event.MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					sqlTxtMenuCopyMouseReleased(evt);
				}
			});

		jpopSqlTxtMenu.add(sqlTxtMenuCopy);
		sqlTxtMenuPaste.setText("Paste");
		sqlTxtMenuPaste.setToolTipText("Paste text from clipboard");
		sqlTxtMenuPaste.addMouseListener(
			new java.awt.event.MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					sqlTxtMenuPasteMouseReleased(evt);
				}
			});

		//jpopSqlTxtMenu.add(sqlTxtMenuPaste);

		// set up grigbaglayout for visible components
		getContentPane().setLayout(new java.awt.GridBagLayout());
		//GridBagLayout gridbag = new GridBagLayout();


		jtxtMsg = new JTextArea();
		jtxtMsg.setEditable(false);
		jtxtMsg.setLineWrap(true);
		jtxtMsg.setFont(new Font("Dialog", Font.PLAIN, 11));
		jtxtMsg.setWrapStyleWord(true);
		jtxtMsg.addMouseListener(
			new java.awt.event.MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					jtxtMsgPopupMenu(evt);
				}
			});

		jscrMsgPane = new JScrollPane(jtxtMsg);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0.0;
		gridBagConstraints.weighty = 0.0;
		gridBagConstraints.insets = new Insets(2, 4, 2, 4);
		jlblMsgTitle = new JLabel("Message title");
		getContentPane().add(jlblMsgTitle, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new Insets(2, 4, 2, 4);
		getContentPane().add(jscrMsgPane, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.weightx = 0.0;
		gridBagConstraints.weighty = 0.0;
		gridBagConstraints.insets = new Insets(2, 0, 2, 0);
		btnClose = new JButton("Close");
		getContentPane().add(btnClose, gridBagConstraints);
		btnClose.addMouseListener(
			new java.awt.event.MouseAdapter() {
				public void mouseReleased(MouseEvent evt) {
					btnCloseMouseReleased(evt);
				}
			});
	}


	// -- Constructor

	/**
	 *  Sets the message attribute of the GsMessageDialog object
	 *
	 *@param  msg  The new message value
	 */
	void setMessage(String msg) {
		jtxtMsg.setText(msg);

	}


	// -- addMessage()

	/**
	 *  Sets the customLabel attribute of the GsMessageDialog object
	 *
	 *@param  text  The new customLabel value
	 */
	void setCustomLabel(String text) {
		jlblMsgTitle.setText(text);

	}


	// -- setCustomLabel()

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void btnCloseMouseReleased(MouseEvent evt) {
		dispose();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	private void sqlTxtMenuCutMouseReleased(MouseEvent evt) {

		//GEN-FIRST:event_sqlTxtMenuPasteMouseReleased
		// Add your handling code here:
		jtxtMsg.cut();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	private void sqlTxtMenuCopyMouseReleased(MouseEvent evt) {

		//GEN-FIRST:event_sqlTxtMenuPasteMouseReleased
		// Add your handling code here:
		jtxtMsg.copy();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	private void sqlTxtMenuPasteMouseReleased(MouseEvent evt) {

		//GEN-FIRST:event_sqlTxtMenuPasteMouseReleased
		// Add your handling code here:
		jtxtMsg.paste();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	private void jtxtMsgPopupMenu(MouseEvent evt) {
		int btn = evt.getButton();
		if((btn == MouseEvent.BUTTON3)) {
			jpopSqlTxtMenu.show(jtxtMsg, evt.getX(), evt.getY());

			//jtabResults.removeTabAt(jtabResults.getSelectedIndex());
		}
	}
}
// -- end class GsMessageDialog

