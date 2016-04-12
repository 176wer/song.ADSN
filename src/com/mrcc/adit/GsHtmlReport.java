/*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*
* $Id: GsHtmlReport.java,v 1.3 2003/04/01 03:32:17 gsherman Exp $
*/
package com.mrcc.adit;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.text.html.HTMLDocument;


import java.awt.Dimension;
import java.awt.Color;

import java.io.FileInputStream;

/**
* GsHtmlReport is a simple html view window
*/
public class GsHtmlReport extends javax.swing.JFrame{
	//{{{ private members
	private JEditorPane editorPane;
	private JTextArea sqlText; //}}}

	//{{{ GsHtmlReport()
	/**
	* Constructor
	*/
	public GsHtmlReport(){
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		sqlText = new JTextArea();
		sqlText.setEditable(false);
		sqlText.setLineWrap(true);
		sqlText.setWrapStyleWord(true);
		sqlText.setBackground(new Color(255,255,153));
		//sqlText.setPreferredSize(new Dimension(600, 100));
		JScrollPane sqlPane = new JScrollPane(sqlText);
		sqlPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getContentPane().add(sqlPane);
		editorPane = new JEditorPane();
		editorPane.setEditable(false);

		editorPane.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(600, 500));
		getContentPane().add(scrollPane);
		setTitle("Report View");
		pack();
	} //}}}

	//{{{ setText()
	/**
	* Set the text displayed in the view window
	*@param text Text to display
	*/
	public void setText(String text){
		editorPane.setText(text);
		editorPane.setCaretPosition(0);
	} //}}}

	//{{{ setSqlText()
	/**
	* Sets the SQL statement that was used to retrieve the results from  the database
	*/
	public void setSqlText(String text){
		sqlText.setText(text);
	} //}}}

	//{{{ setContent()
	/**
	* Reads a file from disk and display it in the view
	*@param file Path to the file
	*/
	public void setContent(String file){
		try{
			FileInputStream fio = new FileInputStream(file);
			editorPane.read(fio, new HTMLDocument());
		}catch(java.io.IOException ioe){
			System.out.println("Unable to display temporary html file: " + file);
		}
	} //}}}

}
