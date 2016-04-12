/*
* GsTableSchemaCatalog.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/

package com.mrcc.adit;


/**
 *  Description of the Class
 *
 *@author     gsherman
 *@created    November 3, 2002
 */
class GsTableSchemaCatalog extends Object implements Comparable {
	private String catalog;
	private String schema;
	private String table;


	/**
	 *  Constructor for the GsTableSchemaCatalog object
	 *
	 *@param  table    Description of the Parameter
	 *@param  schema   Description of the Parameter
	 *@param  catalog  Description of the Parameter
	 */
	public GsTableSchemaCatalog(String table, String schema, String catalog) {
		this.table = table;
		this.schema = schema;
		this.catalog = catalog;
	}
	// -- Constructor


	/**
	 *  Description of the Method
	 *
	 *@param  tsc  Description of the Parameter
	 *@return      Description of the Return Value
	 */
	public int compareTo(Object tsc) {
		String s = toString();
		return s.compareTo(tsc.toString());
	}


	/**
	 *  Gets the catalog attribute of the GsTableSchemaCatalog object
	 *
	 *@return    The catalog value
	 */
	public String getCatalog() {
		return catalog;
	}


	/**
	 *  Gets the schema attribute of the GsTableSchemaCatalog object
	 *
	 *@return    The schema value
	 */
	public String getSchema() {
		return schema;
	}


	/**
	 *  Gets the table attribute of the GsTableSchemaCatalog object
	 *
	 *@return    The table value
	 */
	public String getTable() {
		return table;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public String toString() {
		String spec = table;
		if(schema != null) {
			if(schema.length() > 0) {
				spec = schema + "." + spec;
				if(catalog != null && schema != null) {
					if(catalog.length() > 0) {
						spec = catalog + "." + spec;
			}
			}
		}
		
		}
		
		
		//System.out.println("spec is : " + spec);
		return spec;
	}
}
// -- end class GsTableSchemaCatalog

