/*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*
* $Id: GsIndexEntry.java,v 1.2 2003/04/01 03:32:17 gsherman Exp $
*/
package com.mrcc.adit;
/**
* Metadata about an index in a database table.
*@author gsherman
*/
public class GsIndexEntry{
	//{{{ GsIndexEntry()
	/**
	* Constructor
	* @param name Index name
	* @param columns Column or columns that participate in the index
	* @param unique True if the index is unique
	* @param cardinality Cardinality of the index
	*
	*/
	public GsIndexEntry(String name, String columns, boolean unique, int cardinality){
		this.name = name;
		this.columns = columns;
		this.unique = unique;
		this.cardinality = cardinality;
		direction = null;
		columnCount = 1;
	} //}}}

	//{{{ addColumn()
	/**
	* Adds a column to the string of columns participating in the index
	* @param column Name of the column
	*/
	public void addColumn(String column){
		columns += ", " + column;
		columnCount++;
	} //}}}

	//{{{ getDirection()
	/**
	* Gets the direction (ascending or descending) of the index
	*@return Direction 
	*/
	public String getDirection(){
		return direction;
	} //}}}

	//{{{ setDirection()
	/**
	* Set the direction of the index
	*@param direction Direction of the index (ascending or descending)
	*/
	public void setDirection(String direction){
		this.direction = direction;
	} //}}}

	//{{{ getName()
	/**
	* Get the name of the index
	* @return Name of the index
	*/
	public String getName(){
		return name;
	} //}}}

	//{{{ getColumns()
	/**
	* Get the comma delimited list of columns that participate in the index
	* @return String containing the column names
	*/
	public String getColumns(){
		return columns;
	} //}}}

	//{{{ getColumnCount()
	/**
	* Get the number of columns in this index
	*@return Number of columns that participate in the index
	*/
	public int getColumnCount(){
		return columnCount;
	} //}}}

	//{{{ getUnique()
	/**
	* Get the uniqueness of the index
	*@return True if the index is unique
	*/
	public boolean getUnique(){
		return unique;
	} //}}}

	//{{{ getCardinality()
	/**
	* Gets the cardinality of this index entry. Cardinality is typically the
	* number of rows in the index (see JDBC specification)
	*@return Cardinality (typically number of rows in the index)
	*/
	public int getCardinality(){
		return cardinality;
	}
	//}}}

	//{{{ private members
	private String name;
	private String columns;
	private boolean unique;
	private int cardinality;
	private String direction;
	private int columnCount;
	//}}}
}
