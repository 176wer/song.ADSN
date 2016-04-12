/*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*
* $Id: GsSchemaTreeEvent.java,v 1.6 2004/11/17 16:14:25 gsherman Exp $
*/

package com.mrcc.adit;
public class GsSchemaTreeEvent extends java.util.EventObject{
	int action;
	GsTableSchemaCatalog tsc;
	String field;
	GsSchemaTreeEvent(GsSchemaTree source, int action, GsTableSchemaCatalog tsc, String field){
		super(source);
		this.action = action;
		this.tsc = tsc;
		this.field = field;
	}
	public int getAction(){
		return action;
	}
	public String getTable(){
		return tsc.getTable();
	}
	public String getSchema(){
		return tsc.getSchema();
	}
	public String getField(){
		return field;
	}
	/** 
	 * Gets the GsTableSchemaCatalog associated with this object
	 * @return GsTableSchemaCatalog object
	 */
	public GsTableSchemaCatalog getTsc(){
		return tsc;
	}

}
