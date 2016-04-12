/*
* GsTable.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
package com.mrcc.adit;

/**
 *
 * @author  gsherman 
 */
public class GsTable extends javax.swing.JTable {
    
    private int sqlRowCount = 0;
      private boolean fitState;
    private String sqlStatement;
    
    /** Creates a new instance of GsTable */
    public GsTable() {
    }
    public GsTable(java.util.Vector v, java.util.Vector c){
        super(v,c);
    }
    
    public int getSqlRowCount() {
        return sqlRowCount;
    }
    
    public String getSqlStatement() {
        return sqlStatement;
    }
    
    public void setSqlRowCount(int rc){
        sqlRowCount = rc;
    }
    public void setSqlStatement(String sql){
        sqlStatement = sql;
    }
      public boolean getFitState(){
	    return fitState;
    }
    public void setFitState(boolean state){
	    fitState = state;
    }
}
