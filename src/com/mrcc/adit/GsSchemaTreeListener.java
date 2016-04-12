/*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*
* $Id: GsSchemaTreeListener.java,v 1.2 2003/03/14 05:46:18 gsherman Exp $
*/

package com.mrcc.adit;
public interface GsSchemaTreeListener extends java.util.EventListener{
	void treeAction(GsSchemaTreeEvent e);
}
