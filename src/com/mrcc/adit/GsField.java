/*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*
* $Id: GsField.java,v 1.4 2004/10/26 04:22:40 gsherman Exp $
*/
package com.mrcc.adit;
import java.sql.DatabaseMetaData;
public class GsField
{
	private String name;
	private String type;
	private String length;
	private int nullable;
	
	public GsField(String name, String type, String length, int nullable){
		this.name = name;
		this.type = type;
		this.length = length;
		this.nullable = nullable;
	}
	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public String getLength(){
		return length;
	}
	public boolean equals(Object object_1)
	{
		return true;
	}

	public String toString()
	{
		String str = name + " := " + type + "(" + length + ")";
		String nullStr = "";
		switch(nullable){
		case DatabaseMetaData.typeNoNulls:
			nullStr = " not null";
			break;
		case DatabaseMetaData.typeNullable:
			nullStr = " null";
			break;
		case DatabaseMetaData.typeNullableUnknown:
			nullStr = " null unknown";
			break;
		}
		str += nullStr;
		return str; 
	}
	/**
	 * @return
	 */
	public int getNullable() {
		return nullable;
	}

	/**
	 * @param string
	 */
	public void setNullable(int n) {
		nullable = n;
	}

}

