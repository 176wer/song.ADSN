/*
* TxtFilter.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
package com.mrcc.adit;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *  Description of the Class
 *
 *@author     gsherman
 *@created    December 20, 2002
 */
public class TxtFilter extends FileFilter {

	// Accept all directories and .txt/.sql files
	/**
	 *  Description of the Method
	 *
	 *@param  f  Description of the Parameter
	 *@return    Description of the Return Value
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String name = f.getName();
		int i = name.lastIndexOf('.');
		String extension = null;
		if (i > 0 && i < name.length() - 1) {
			extension = name.substring(i + 1).toLowerCase();
		}

		if (extension != null) {
			if (extension.equals("txt")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}


	// The description of this filter
	/**
	 *  Gets the description attribute of the TxtFilter object
	 *
	 *@return    The description value
	 */
	public String getDescription() {
		return "Text files";
	}
}

