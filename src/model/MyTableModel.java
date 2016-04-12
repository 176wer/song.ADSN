/**
 * Project Name:ADSN
 * File Name:TableModel.java
 * Package Name:model
 * Date:2016年1月2日上午9:49:12
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package model;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import bean.Experiment;


/**
 * ClassName:TableModel <br/>
 * Function: use a custom TableModel
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年1月2日 上午9:49:12 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
 
 
   public class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"实验次数",
                                        "实验目的",
                                        "实验日期",
                                         };
        private List data;
       
 
        public MyTableModel(List list){
        	data=list;
        }
         
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
            return data.size();
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
        	Experiment experiment=(Experiment) data.get(row);
        	 
            return experiment.getTabelData(col);
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
          
                return false;
           
        }
 
        
        
    }
 
   
