/*
* GsSqlAbout 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
 package com.mrcc.adit;

import javax.swing.JLabel;
import java.awt.Window;
class GsSqlAbout extends javax.swing.JWindow
{
	
	
	public GsSqlAbout(Window owner) { 
		super(owner);
		JLabel about = new JLabel();
		about.setText("<b>About GsSQL</b>");
		//this.getContentPane.add(about);
		
	} // -- Constructor
	
} // -- end class GsSqlAbout

