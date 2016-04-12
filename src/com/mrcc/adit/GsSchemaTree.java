/*
 * Copyright:
 *   This program is free software; you can redistribute it and/or modify  
 *   it under the terms of the GNU General Public License as published by  
 *   the Free Software Foundation; either version 2 of the License, or     
 *   (at your option) any later version.
 *
 * $Id: GsSchemaTree.java,v 1.21 2004/11/17 16:14:25 gsherman Exp $
 */
package com.mrcc.adit;
import javax.swing.JTree;
import javax.swing.JTextArea;

import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;


import java.util.Hashtable;
import java.util.Vector;
import java.util.HashMap;

import java.awt.GridBagConstraints;
import java.awt.Cursor;
import java.awt.Font;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.TreeSet;
import java.util.logging.*;
/**
 * Tree control to handle display of database objects (tables, indexes, etc)
 *@author     gsherman
 */
public class GsSchemaTree extends javax.swing.JPanel implements TreeExpansionListener {
	private static Logger logger = Logger.getLogger("com.mrcc.adit.GsSchemaTree");
	//{{{ private members
	private Connection connection;
	private boolean expanding;
	private JPopupMenu jPopTreeMenu;
	private JPopupMenu jPopTreeFieldMenu;
	private String selectedTable;
	private String selectedField;
	private GsTableSchemaCatalog currentTsc;
	private int popupType;
	private JTextArea sqlField;
	private Hashtable typeMap;
	public static final int ACTION_BROWSE=0, ACTION_REPORT =1, ACTION_COUNT=2,
				 ACTION_FIELD_ORDERBY_ASCENDING=3, ACTION_FIELD_ORDERBY_DESCENDING=4,
				 ACTION_FIELD_SELECT_DISTINCT=5, ACTION_FIELD_CREATE_INDEX=6, ACTION_SCHEMA=7,
				 ACTION_DROP_TABLE=8;
	public static final int TABLE=0, INDEXES=1, INDEX=2, FIELD=3;
	String nodeTypes[] = {"table", "indexes", "index", "field"};
	JTree schemaTree;
	JToolBar jToolBar;
	JButton jButton1;
	GridBagConstraints gridBagConstraints;
	DefaultMutableTreeNode rootNode;
	DefaultTreeModel treeModel;
	private static String[] deleteRules;
	private static String[] updateRules;
	//}}}

	//{{{ GsSchemaTree()
	/**
	 *  Tree control for displaying the objects in a database
	 *
	 *@param  con  Connection 
	 */
	public GsSchemaTree(Connection con) {
		connection = con;
		initMap();
		// set up the rules array
		deleteRules = new String[5];
		deleteRules[DatabaseMetaData.importedKeyNoAction] = "No action";
		deleteRules[DatabaseMetaData.importedKeyCascade] = "Cascade delete";
		deleteRules[DatabaseMetaData.importedKeySetNull] = "Set to null";
		deleteRules[DatabaseMetaData.importedKeyRestrict] = "No action";
		deleteRules[DatabaseMetaData.importedKeySetDefault] = "Set to default value";
		updateRules = new String[5];
		updateRules[DatabaseMetaData.importedKeyNoAction] = "No action";
		updateRules[DatabaseMetaData.importedKeyCascade] = "Change to agree with primary key";
		updateRules[DatabaseMetaData.importedKeySetNull] = "Set to null";
		updateRules[DatabaseMetaData.importedKeyRestrict] = "No action";
		updateRules[DatabaseMetaData.importedKeySetDefault] = "Set to default value";
		setLayout(new java.awt.GridBagLayout());
		jToolBar = new JToolBar();
		jButton1 = new JButton();
		URL url = getClass().getResource("images/reload.gif");
		jButton1.setIcon(new javax.swing.ImageIcon(url));
		jButton1.addMouseListener(
				new java.awt.event.MouseAdapter() {
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						reloadMouseReleased(evt);
					}
				});
		jToolBar.add(jButton1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		//getContentPane().add(jToolBar, gridBagConstraints);


		// add the tree
		rootNode = new DefaultMutableTreeNode("Tables");
		treeModel = new DefaultTreeModel(rootNode);

		schemaTree = new JTree(treeModel);
		schemaTree.setShowsRootHandles(true);
		schemaTree.getSelectionModel().setSelectionMode
			(TreeSelectionModel.SINGLE_TREE_SELECTION);
		schemaTree.addTreeExpansionListener(this);
		schemaTree.addMouseListener(
				new java.awt.event.MouseAdapter() {
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						schemaTreeMouseReleased(evt);
					}
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						schemaTreeMouseReleased(evt);
					}
					public void mousePressed(java.awt.event.MouseEvent evt) {
						schemaTreeMouseReleased(evt);
					}
				});
		Font font = schemaTree.getFont();
		float newSize = font.getSize() -1;
		schemaTree.setFont(font.deriveFont(newSize));
		schemaTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			schemaTree.getLastSelectedPathComponent();

		if (node == null) return;

		Object nodeInfo = node.getUserObject();
		logger.info("Selected node is of type: " + nodeInfo.getClass().getName());
		if(nodeInfo.getClass().getName().equals("com.mrcc.adit.GsTableSchemaCatalog")){
			popupType = TABLE;
			GsTableSchemaCatalog tsc = (GsTableSchemaCatalog) nodeInfo;
			logger.info("Currently selected table is " + tsc.getTable());
			selectedTable = tsc.getTable();
			currentTsc = tsc;
		}else{
			if(nodeInfo.getClass().getName().equals("com.mrcc.adit.GsField")){
				popupType = FIELD;
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
				Object parentNodeInfo = parent.getUserObject();
				GsField fld = (GsField)nodeInfo;
				selectedField = fld.getName();
				// set the table name too
				GsTableSchemaCatalog ftsc = (GsTableSchemaCatalog) parentNodeInfo;
				logger.info("Currently selected table associated with " + selectedField +" is " + ftsc.getTable());
				selectedTable = ftsc.getTable();
				currentTsc = ftsc;
			}else{
				if(nodeInfo.getClass().getName().equals("java.lang.String")){
					// if the node has no children it is either a field name
					// or an index item node
					if(node.getChildCount() == 0){
						// check parentage
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
						Object parentNodeInfo = parent.getUserObject();
						logger.info("Parent of Node with no children is of type: " + parentNodeInfo.getClass().getName());
						if(parentNodeInfo.getClass().getName().equals("com.mrcc.adit.GsTableSchemaCatalog")){
							popupType = FIELD;
							GsField fld = (GsField)nodeInfo;
							selectedField = fld.getName();
							// set the table name too
							GsTableSchemaCatalog ftsc = (GsTableSchemaCatalog) parentNodeInfo;
							logger.info("Currently selected table associated with " + selectedField +" is " + ftsc.getTable());
							selectedTable = ftsc.getTable();
						}else{
							popupType = INDEX;
						}
					}else{
						// if the node has children it can be either the
						// top-level indexes folder or an index
						String nodeText = (String)nodeInfo;
						if(nodeText.equals("Indexes")){
							popupType = INDEXES;
						}else{
							popupType = INDEX;
						}
						//logger.info("Selected node is a " + nodeInfo.getClass().getName());
					}
				}
			}
		}
		logger.info("Node type is " + nodeTypes[popupType]);
			}
		});



		JScrollPane scroller = new JScrollPane(schemaTree);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		// gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		// scroller.add(schemaTree);
		//scroller.setSize(300,220);
		add(scroller, gridBagConstraints);

		//setTitle("Table List");

		initComponents();
		//setSize(getWidth() * 2, getHeight());
		// init node types


	} //}}}

	//{{{ initComponents()

	private void initComponents() {
		// create the table popup menu

		javax.swing.JMenuItem jPopBrowseMenuItem;
		javax.swing.JMenuItem jPopCountMenuItem;
		javax.swing.JMenuItem jPopViewReportMenuItem;
		javax.swing.JMenuItem jPopCreateSchemaMenuItem;
		javax.swing.JMenuItem jPopDropTableMenuItem;
		javax.swing.JMenuItem jPopCreateSelectMenuItem;

		jPopTreeMenu = new JPopupMenu();
		jPopBrowseMenuItem = new javax.swing.JMenuItem();
		jPopBrowseMenuItem.setText("Browse");
		jPopBrowseMenuItem.setToolTipText("Browse table");
		jPopBrowseMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						browseMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopBrowseMenuItem);

		jPopCountMenuItem = new javax.swing.JMenuItem();
		jPopCountMenuItem.setText("Count");
		jPopCountMenuItem.setToolTipText("Count records in the table");
		jPopCountMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						countMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopCountMenuItem);

		jPopViewReportMenuItem = new javax.swing.JMenuItem();
		jPopViewReportMenuItem.setText("View Report");
		jPopViewReportMenuItem.setToolTipText("View in report format");
		jPopViewReportMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						viewReportMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopViewReportMenuItem);


		jPopCreateSchemaMenuItem = new javax.swing.JMenuItem();
		jPopCreateSchemaMenuItem.setText("Schema");
		jPopCreateSchemaMenuItem.setToolTipText("Paste schema for this table into the SQL editor");
		jPopCreateSchemaMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						createSchemaMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopCreateSchemaMenuItem);

		jPopCreateSelectMenuItem = new javax.swing.JMenuItem();
		jPopCreateSelectMenuItem.setText("Create select statement");
		jPopCreateSelectMenuItem.setToolTipText("Create a select statement for this table and insert it into the SQL editor");
		jPopCreateSelectMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						createSelectMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopCreateSelectMenuItem);


		jPopDropTableMenuItem = new javax.swing.JMenuItem();
		jPopDropTableMenuItem.setText("Drop table...");
		jPopDropTableMenuItem.setToolTipText("Delete the table from the database");
		jPopDropTableMenuItem.addMouseListener(new java.awt.event.MouseAdapter()
				{
					public void mouseReleased(java.awt.event.MouseEvent evt) {
						dropTableMouseReleased(evt);
					}
				});
		jPopTreeMenu.add(jPopDropTableMenuItem);


		// create field popup menu
		jPopTreeFieldMenu = new JPopupMenu();
		javax.swing.JMenuItem jPopOrderByMenuItem = new javax.swing.JMenuItem ();
		jPopOrderByMenuItem.setText("Order by ascending");
		jPopOrderByMenuItem.setToolTipText("Browse the table and sort in ascending order by this field");
		jPopOrderByMenuItem.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				orderByMouseReleased(evt);
			}
		});
		jPopTreeFieldMenu.add(jPopOrderByMenuItem);

		javax.swing.JMenuItem jPopOrderByMenuDescItem = new javax.swing.JMenuItem ();
		jPopOrderByMenuDescItem.setText("Order by descending");
		jPopOrderByMenuDescItem.setToolTipText("Browse the table and sort in descending order by this field");
		jPopOrderByMenuDescItem.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				orderByDescMouseReleased(evt);
			}
		});
		jPopTreeFieldMenu.add(jPopOrderByMenuDescItem);

		javax.swing.JMenuItem jPopSelectDistinctItem = new javax.swing.JMenuItem ();
		jPopSelectDistinctItem.setText("Select distinct");
		jPopSelectDistinctItem.setToolTipText("Select distinct (unique) items in the selected field");
		jPopSelectDistinctItem.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				selectDistinctMouseReleased(evt);
			}
		});
		jPopTreeFieldMenu.add(jPopSelectDistinctItem);

		javax.swing.JMenuItem jPopCreateIndexItem = new javax.swing.JMenuItem ();
		jPopCreateIndexItem.setText("Create index");
		jPopCreateIndexItem.setToolTipText("Create index on selected field");
		jPopCreateIndexItem.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				createIndexMouseReleased(evt);
			}
		});
		jPopTreeFieldMenu.add(jPopCreateIndexItem);
		selectedTable = null;

		//  pack();
	} //}}}

	//{{{ addTable()
	/**
	 *  Adds a table to the tree
	 *@param  t       Name of the table
	 *@param  fields  Vector containing the fields
	 */
	public void addTable(String t, Vector fields) {
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
		rootNode.add(child);
		for(int i = 0; i < fields.size(); i++) {
			DefaultMutableTreeNode newField = new DefaultMutableTreeNode(fields.elementAt(i));
			child.add(newField);
		}


	} //}}}

	//{{{ addTable()
	/**
	 *  Adds a table to the tree 
	 *
	 *@param  t       The feature to be added to the Table attribute
	 *@param  fields  The feature to be added to the Table attribute
	 */
	public void addTable(GsTableSchemaCatalog t, Vector fields) {
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(t);
		rootNode.add(child);
		for(int i = 0; i < fields.size(); i++) {
			DefaultMutableTreeNode newField = new DefaultMutableTreeNode(fields.elementAt(i));
			child.add(newField);
		}


	} //}}}


	/**
	 *  Sets the connection attribute of the GsSchemaTree object
	 *
	 *@param  con  The new connection value
	 */
	public void setConnection(Connection con) {
		connection = con;
	}

	public void setSqlTextField(JTextArea sqlField){
		this.sqlField = sqlField;
	}

	public void addSchemaTreeListener(GsSchemaTreeListener listener){
		listenerList.add(GsSchemaTreeListener.class, listener);
	}

	public void removeSchemaTreeListener(GsSchemaTreeListener listener){
		listenerList.remove(GsSchemaTreeListener.class, listener);
	}

	void fireEvent(int action, GsTableSchemaCatalog tsc, String field){
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i += 2){
			if(listeners[i] == GsSchemaTreeListener.class){
				((GsSchemaTreeListener)listeners[i+1]).treeAction(new GsSchemaTreeEvent(this, action, tsc, field));
			}
		}
	}
	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void reloadMouseReleased(java.awt.event.MouseEvent evt) {
		loadTables();
	}

	public void schemaTreeMouseReleased(java.awt.event.MouseEvent evt){
		int btn = evt.getButton();
		if(evt.isPopupTrigger()) {
			java.awt.Point p = evt.getPoint();

			TreePath path = schemaTree.getPathForLocation(evt.getX(), evt.getY());
			schemaTree.setSelectionPath(path);

			// popup the appropriate menu
			switch(popupType){
				case TABLE:
					jPopTreeMenu.show(schemaTree, evt.getX(), evt.getY());
					break;
				case FIELD:
					jPopTreeFieldMenu.show(schemaTree,evt.getX(), evt.getY());
					break;
			}
			// get the active node
			//JOptionPane.showMessageDialog(this, "Mouse Event in schemaTreeMouseReleased processed");
		}
	}

	public void browseMouseReleased(java.awt.event.MouseEvent evt){
		//JOptionPane.showMessageDialog(this, "Browse clicked");
		if(selectedTable != null){
			//sqlField.setText("select * from " + selectedTable);
			fireEvent(ACTION_BROWSE, currentTsc, null);
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table before browsing");
		}
		// get the name of the table
	}

	public void viewReportMouseReleased(java.awt.event.MouseEvent evt){
		//JOptionPane.showMessageDialog(this, "Browse clicked");
		if(selectedTable != null){
			//sqlField.setText("select * from " + selectedTable);
			fireEvent(ACTION_REPORT, currentTsc, null);
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table to view");
		}
		// get the name of the table
	}
	public void countMouseReleased(java.awt.event.MouseEvent evt){
		//JOptionPane.showMessageDialog(this, "Browse clicked");
		if(selectedTable != null){
			//sqlField.setText("select * from " + selectedTable);
			fireEvent(ACTION_COUNT, currentTsc, null);
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table to query");
		}
		// get the name of the table
	}
	public void createSchemaMouseReleased(java.awt.event.MouseEvent evt){
		//JOptionPane.showMessageDialog(this, "Browse clicked");
		if(selectedTable != null){
			sqlField.setText(createSchema());
			//fireEvent(ACTION_SCHEMA, selectedTable, null);
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table to generate schema");
		}
		// get the name of the table
	}
	public void dropTableMouseReleased(java.awt.event.MouseEvent evt){
		if(selectedTable != null){
			fireEvent(ACTION_DROP_TABLE, currentTsc, null);
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table to drop");
		}
		// get the name of the table
	}

	public void createSelectMouseReleased(java.awt.event.MouseEvent evt){
		if(selectedTable != null){
			sqlField.setText(createSelectStatement());
		}else{
			JOptionPane.showMessageDialog(this, "Please select a table to generate select statement");
		}
		// get the name of the table
	}
	public void orderByMouseReleased(java.awt.event.MouseEvent evt){
		fireEvent(ACTION_FIELD_ORDERBY_ASCENDING, currentTsc, selectedField);
	}
	public void orderByDescMouseReleased(java.awt.event.MouseEvent evt){
		fireEvent(ACTION_FIELD_ORDERBY_DESCENDING, currentTsc, selectedField);
	}
	public void selectDistinctMouseReleased(java.awt.event.MouseEvent evt){
		fireEvent(ACTION_FIELD_SELECT_DISTINCT, currentTsc, selectedField);
	}
	public void createIndexMouseReleased(java.awt.event.MouseEvent evt){
		fireEvent(ACTION_FIELD_CREATE_INDEX, currentTsc, selectedField);
	}
	public String getSelectedTable(){
		return selectedTable;
	}
  /**
   * Clear the table tree//{{{
   */
  public void clearTables(){
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
    logger.info("Disconnect - Removing all children from the tree");
		rootNode.removeAllChildren();
		treeModel.reload();
		repaint();
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }
//}}}
	/**
	 *  Load the tables into the table tree //{{{
	 */
	public void loadTables() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		rootNode.removeAllChildren();
		//   if(treeModel.getChildCount(rootNode) > 0){
		//	    treeModel.removeNodeFromParent(rootNode);
		//  }

		try {

			DatabaseMetaData dmd = connection.getMetaData();
			String[] types = {"TABLE", "VIEW"};
			ResultSet ds = dmd.getTables(null, null, "%", types);
			// get the table names into a sorted set
			TreeSet ss = new TreeSet();
			// ignore schemas we don't want to see
			//TODO Create a mechanism to specify schemas we aren't interested in
			while(ds.next()) {

				ss.add(new GsTableSchemaCatalog(ds.getString(3), ds.getString(2), ds.getString(1)));
			}
			// ds.beforeFirst();
			java.util.Iterator it = ss.iterator();
			while(it.hasNext()) {
				//old: while (ds.next())

				GsTableSchemaCatalog tsc = (GsTableSchemaCatalog) it.next();
				logger.info(tsc.toString());
				//old: String tableName = ds.getString(3);
				String tableName = tsc.getTable();
				try {
					Vector fields = new Vector();
					//old: ResultSet colInfo = dmd.getColumns(ds.getString(1), ds.getString(2), tableName, "%");
					/*
					 *  ResultSet colInfo = dmd.getColumns(tsc.getCatalog(), tsc.getSchema(), tableName, "%");
					 *  while (colInfo.next())
					 *  {
					 *  String fieldinfo = colInfo.getString(4) + " : " + colInfo.getString(6) + "(" + colInfo.getString(7) + ")";
					 *  fields.add(fieldinfo);
					 *  /  logger.info(colInfo.getString(4) + " : " + colInfo.getString(6));
					 *  }
					 */
					fields.add("placeholder");
					String treeEntry = null;
					//old: String schema = ds.getString(2);
					String schema = tsc.getSchema();
					if(schema != null) {

						// some JDBC drivers return null, others
						// a blank string so we need to check for
						// both
						if(schema.length() > 0) {
							treeEntry = schema + "." + tableName;
						} else {
							treeEntry = tableName;
						}
					} else {
						treeEntry = tableName;
					}

					//addTable(treeEntry, fields);
					logger.info("Added " + tsc + " to tree");
					addTable(tsc, fields);
				} catch(StringIndexOutOfBoundsException iob) {
					logger.info("String out of bounds");
				}
			}

			// show table info for users

			/*
			 *  ResultSet colInfo = dmd.getColumns(null,null,"alaska","%");
			 *  while(colInfo.next()){
			 *  logger.info(colInfo.getString(3) + " : " + colInfo.getString(4) + " : " + colInfo.getString(6));
			 *  }
			 */
			ds.close();
		} catch(SQLException se) {
			logger.info("Sql exception: " + se.getMessage());
			JOptionPane.showMessageDialog(this, "Sql exception: " + se.getMessage());

		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		treeModel.reload();
		repaint();
	}
//}}}

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void treeExpanded(TreeExpansionEvent evt) {
		populateFields(evt);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void treeCollapsed(TreeExpansionEvent evt) {

	}

	//{{{ populateFields

	/**
	 *  Description of the Method
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void populateFields(TreeExpansionEvent evt) {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if(!expanding){
			TreePath src = evt.getPath();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) src.getLastPathComponent();
			logger.info("Expanded Node Type:" + node.getClass().getName());
			Object otsc = node.getUserObject();
			// only expand table nodes, not index entry nodes
			logger.info("Node class is: " + otsc.getClass().getName());
			if(otsc.getClass().getName().equals("com.mrcc.adit.GsTableSchemaCatalog")){
				GsTableSchemaCatalog tsc = (GsTableSchemaCatalog) otsc;
				logger.info("Expanded " + tsc.getTable());
				// get the fields for the node
				if(node.getChildCount() == 1) {
					node.remove(0);
					DefaultMutableTreeNode indexNode = null;
					DefaultMutableTreeNode referencesNode = null;
					DefaultMutableTreeNode referencedNode = null;
					String tableName = tsc.getTable();

					try {
						DatabaseMetaData dmd = connection.getMetaData();
						//old: ResultSet colInfo = dmd.getColumns(ds.getString(1), ds.getString(2), tableName, "%");
						String context = "Get columns";
						logger.info("Getting columns for " + tsc.toString());
						ResultSet colInfo = dmd.getColumns(tsc.getCatalog(), tsc.getSchema(), tableName, "%");
						logger.info("Getting references (exported keys) for " + tsc.toString());
						ResultSet referencesInfo = dmd.getExportedKeys(tsc.getCatalog(), tsc.getSchema(), tableName);
						logger.info("Database product: " + dmd.getDatabaseProductName());
						logger.info("Database driver: " + dmd.getDriverName());
						boolean getRefs = false;
						if(getRefs){
						// add index info for this table
						logger.info("Getting references");
						while (referencesInfo.next()){
							if(referencesNode == null){
								referencesNode = new DefaultMutableTreeNode("References (exported keys)");
								node.add(referencesNode);
							}
							// get the primary key
							DefaultMutableTreeNode referencesChildNode = 
								new DefaultMutableTreeNode("" + referencesInfo.getString(4) + " to " + referencesInfo.getString(7));
							referencesNode.add(referencesChildNode);
							referencesChildNode.add(new DefaultMutableTreeNode("PK Table: " + referencesInfo.getString(3)));
							referencesChildNode.add(new DefaultMutableTreeNode("PK Column: " + referencesInfo.getString(4)));
							referencesChildNode.add(new DefaultMutableTreeNode("FK Schema: " + referencesInfo.getString(6)));
							referencesChildNode.add(new DefaultMutableTreeNode("FK Table: " + referencesInfo.getString(7)));
							referencesChildNode.add(new DefaultMutableTreeNode("FK Column: " + referencesInfo.getString(8)));
							referencesChildNode.add(new DefaultMutableTreeNode("Update Rule: " + updateRules[referencesInfo.getShort(10)]));
							referencesChildNode.add(new DefaultMutableTreeNode("Delete Rule: " + deleteRules[referencesInfo.getShort(11)]));
							referencesChildNode.add(new DefaultMutableTreeNode("FK Name: " + referencesInfo.getString(12)));
							referencesChildNode.add(new DefaultMutableTreeNode("PK Name: " + referencesInfo.getString(13)));


						}
						logger.info("Getting referenced");
						// getting imported keys for informix is terribly slow -- skip this if 
						// connected to an informix database
						String driver = dmd.getDriverName();
						if(driver.indexOf("Informix") == -1){
							logger.info("Getting referenced (imported keys) for " + tsc.toString());
							ResultSet referencedInfo = dmd.getImportedKeys(tsc.getCatalog(), tsc.getSchema(), tableName);

							while(referencedInfo.next()){
								if(referencedNode == null){
									referencedNode = new DefaultMutableTreeNode("Referenced (imported keys)");
									node.add(referencedNode);
								}
								DefaultMutableTreeNode referencedChildNode = 
									new DefaultMutableTreeNode("" + referencedInfo.getString(4) + " from " + referencedInfo.getString(3));
								referencedNode.add(referencedChildNode);
								referencedChildNode.add(new DefaultMutableTreeNode("PK Table: " + referencedInfo.getString(3)));
								referencedChildNode.add(new DefaultMutableTreeNode("PK Column: " + referencedInfo.getString(4)));
								referencedChildNode.add(new DefaultMutableTreeNode("FK Schema: " + referencedInfo.getString(6)));
								referencedChildNode.add(new DefaultMutableTreeNode("FK Table: " + referencedInfo.getString(7)));
								referencedChildNode.add(new DefaultMutableTreeNode("FK Column: " + referencedInfo.getString(8)));
							referencedChildNode.add(new DefaultMutableTreeNode("Update Rule: " + updateRules[referencedInfo.getShort(10)]));
							referencedChildNode.add(new DefaultMutableTreeNode("Delete Rule: " + deleteRules[referencedInfo.getShort(11)]));
								referencedChildNode.add(new DefaultMutableTreeNode("FK Name: " + referencedInfo.getString(12)));
								referencedChildNode.add(new DefaultMutableTreeNode("PK Name: " + referencedInfo.getString(13)));


							}
						}	
					}
						try{
							context = "Get indexes";
							logger.info("Getting indexes for " + tsc.toString());
							ResultSet idxInfo = dmd.getIndexInfo(tsc.getCatalog(), tsc.getSchema(), tableName, false, false);
							String lastIdxName = "";
							HashMap indexes = new HashMap();
							// populate the index hashtable

							while(idxInfo.next()){
								// create the index node if it doesn't exist
								if(indexNode == null){
									indexNode = new DefaultMutableTreeNode("Indexes");
									node.add(indexNode);
								}

								// get the index name
								String idxName = idxInfo.getString(6);
								// check to see if the index already has and index entry object
								GsIndexEntry idxEntry = (GsIndexEntry)indexes.get(idxName);
								if(idxEntry == null){
									// create a new entry
									idxEntry = new GsIndexEntry(idxName, idxInfo.getString(9), idxInfo.getBoolean(4), idxInfo.getInt(11));
									if(idxInfo.getString(10) != null){
										idxEntry.setDirection(idxInfo.getString(10));
									}
									// add the index entry to the collection

									indexes.put(idxName, idxEntry);
								}else{
									// this index entry exists so must be a multi-column index.
									// add the column to it
									idxEntry.addColumn(idxInfo.getString(9));
								}
							}
							// create the index nodes and populate them
							java.util.Iterator iCol =indexes.values().iterator();
							//java.util.Iterator iCol = col.iterator();
							while(iCol.hasNext()){
								GsIndexEntry indexEntry = (GsIndexEntry)iCol.next();
								DefaultMutableTreeNode indexChildNode = new DefaultMutableTreeNode(indexEntry.getName());
								indexNode.add(indexChildNode);
								String unique = "Unique: " ;
								if(indexEntry.getUnique())
									unique += "No";
								else
									unique += "Yes";

								indexChildNode.add( new DefaultMutableTreeNode(unique));
								String columnLabel = "Column";
								if(indexEntry.getColumnCount() > 1){
									columnLabel += "s";
								}
								indexChildNode.add(new DefaultMutableTreeNode(columnLabel + ": " + indexEntry.getColumns()));
								//	indexChildNode.add(new DefaultMutableTreeNode("Type: " + indexEntry.getType()));
								//	indexChildNode.add(new DefaultMutableTreeNode("Position: " + idxInfo.getShort(8)));
								//	logger.info("Direction: " + idxInfo.getString(10));
								if(indexEntry.getDirection() != null){
									if(indexEntry.getDirection().equals("A")){
										indexChildNode.add(new DefaultMutableTreeNode("Direction: Ascending"));
									}else{
										indexChildNode.add(new DefaultMutableTreeNode("Direction: Descending"));
									}
								}
								indexChildNode.add(new DefaultMutableTreeNode("Cardinality: " + indexEntry.getCardinality()));

							}

						}catch(SQLException idxEx){
							logger.info("SQL Exception getting indexes for " + tableName + " - " + idxEx.getMessage());
							if(indexNode == null){
								indexNode = new DefaultMutableTreeNode("Indexes");
								node.add(indexNode);
							}
							indexNode.add(new DefaultMutableTreeNode("Error: " + idxEx.getMessage()));
						}
						Vector fields = new Vector();
						while(colInfo.next()) {

							//String fieldinfo = colInfo.getString(4) + " : " + colInfo.getString(6) + "(" + colInfo.getString(7) + ")";
							GsField fieldInfo = new GsField(colInfo.getString(4), colInfo.getString(6),
									colInfo.getString(7), colInfo.getInt(11));
							String nulls = colInfo.getString(18);
							//logger.info("Nullable string is: " + nulls);
							logger.info("FIELD INFO: " + colInfo.getString(4) + " Java type:" + colInfo.getString(5));
							fields.add(fieldInfo);

							//  logger.info(colInfo.getString(4) + " : " + colInfo.getString(6));
						}
						colInfo.close();
						String treeEntry = null;
						//old: String schema = ds.getString(2);
						String schema = tsc.getSchema();
						if(schema != null) {

							// some JDBC drivers return null, others
							// a blank string so we need to check for
							// both
							if(schema.length() > 0) {
								treeEntry = schema + "." + tableName;
							} else {
								treeEntry = tableName;
							}
						} else {
							treeEntry = tableName;
						}

						//addTable(treeEntry, fields);
						for(int i = 0; i < fields.size(); i++) {
							GsField fld = (GsField)fields.elementAt(i);
							//DefaultMutableTreeNode newField = new DefaultMutableTreeNode(fields.elementAt(i).getName());
							DefaultMutableTreeNode newField = new DefaultMutableTreeNode(fld);
							node.add(newField);
						}
					} catch(StringIndexOutOfBoundsException iob) {
						logger.info("String out of bounds");
						JOptionPane.showMessageDialog(this,"String out of bounds while populating fields");
					} catch(SQLException se) {
						logger.info("Sql exception: " + se.getMessage());
						JOptionPane.showMessageDialog(this,"Sql exception: " + se.getMessage());
					}

				}


				treeModel.reload(node);
				expanding = true;
				schemaTree.expandPath(src);
			}
			expanding = false;
		}
		//repaint();



		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	} //}}}

	//{{{ createSelectStatement()
	public String createSelectStatement(){
		String sql = null;
		if(currentTsc != null){
			sql = "select ";
			try {
				// get the fields for the table and complete the sql statement
				DatabaseMetaData dmd = connection.getMetaData();
				//old: ResultSet colInfo = dmd.getColumns(ds.getString(1), ds.getString(2), tableName, "%");
				ResultSet colInfo =
					dmd.getColumns(
							currentTsc.getCatalog(),
							currentTsc.getSchema(),
							currentTsc.getTable(),
							"%");
				int count = 0;
				while(colInfo.next()) {

					if(count++ > 0){
						sql += ", ";
					}
					sql += colInfo.getString(4);
				}
				sql += " from ";
				//			 if(currentTsc.getCatalog() != null){
				//				sql += currentTsc.getCatalog() + ".";
				//			 }
				if(currentTsc.getSchema() != null){
					sql += currentTsc.getSchema() + ".";
				}
				sql +=currentTsc.getTable() ;

				colInfo.close();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
		return sql;

	}
	//}}}

	//{{{ createSchema()
	/**
	 * Generate the SQL to create the table
	 */
	public String createSchema(){
		String sql = null;
		if(currentTsc != null){
			sql = "CREATE TABLE " + currentTsc.getTable() +
				" (";
			try {
				// get the fields for the table and complete the sql statement
				DatabaseMetaData dmd = connection.getMetaData();
				//old: ResultSet colInfo = dmd.getColumns(ds.getString(1), ds.getString(2), tableName, "%");
				ResultSet colInfo =
					dmd.getColumns(
							currentTsc.getCatalog(),
							currentTsc.getSchema(),
							currentTsc.getTable(),
							"%");
				int count = 0;
				while(colInfo.next()) {
					int javaType = colInfo.getInt(5);

					if(count++ > 0){
						sql += ", ";
					}
					//String fieldinfo = colInfo.getString(4) + " : " + colInfo.getString(6) + "(" + colInfo.getString(7) + ")";
					sql += colInfo.getString(4) + " " + colInfo.getString(6);
					// determine if this field type requires a length parameter
					Boolean needLength = (Boolean)typeMap.get(new Integer(javaType));
					if(needLength != null){
						if(needLength.booleanValue()){
							if(colInfo.getString(7).length() > 0){
								sql += "(" + colInfo.getString(7) + ")";
							}
						}
					}
					//  logger.info(colInfo.getString(4) + " : " + colInfo.getString(6));
				}
				sql += ")";

				colInfo.close();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}
		}
		return sql;
	} //}}}
	/**
	 * Initialize the type map used for creating schema sql
	 * Only types that require a length parameter are included
	 * in the map. When creating the schema statement, if a type is
	 * not found in the map, no length qualifiers will be added to the
	 * SQL.
	 *
	 */
	private void initMap(){
		typeMap = new Hashtable();
		Boolean bool = new Boolean(true);

		typeMap.put(new Integer(Types.CHAR), bool);
		typeMap.put(new Integer(Types.DECIMAL), bool);
		typeMap.put(new Integer(Types.DOUBLE), bool);
		typeMap.put(new Integer(Types.FLOAT), bool);
		typeMap.put(new Integer(Types.NUMERIC), bool);
		typeMap.put(new Integer(Types.REAL), bool);
		typeMap.put(new Integer(Types.VARCHAR), bool);



	}
}

