/*
* GsScrollPane.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/
package com.mrcc.adit;

import java.awt.Component;
import java.util.Vector;
/**
 *
 * @author  gsherman
 */
public class GsScrollPane extends javax.swing.JScrollPane {
    private int sqlRowCount = 0;
    private String sqlStatement;
    private Vector rows;
    private Vector cols;
  
    
    
    /** Creates a new instance of GsScrollPane */
    public GsScrollPane() {
    }
    public GsScrollPane(Component c){
        super(c);
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
    public Vector getRows(){
        return rows;
    }
    public Vector getCols(){
        return cols;
    }
    public void storeRows(Vector rows){
        this.rows = rows;
    }
    public void storeCols(Vector cols){
        this.cols = cols;
    }
  
}
