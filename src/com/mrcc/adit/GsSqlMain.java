/*
 *
 * Copyright:
 *   This program is free software; you can redistribute it and/or modify  
 *   it under the terms of the GNU General Public License as published by  
 *   the Free Software Foundation; either version 2 of the License, or     
 *   (at your option) any later version.
 * $Id: GsSqlMain.java,v 1.34 2004/12/07 00:44:51 gsherman Exp $
 */
package com.mrcc.adit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.table.TableModel;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.event.ActionListener;

/**
 * Adit main class 
 *@author     gsherman
 */
public class GsSqlMain
  extends JDialog implements ClipboardOwner{

  private static Logger logger = Logger.getLogger("com.mrcc.adit.GsSqlMain");
  protected Action pasteSqlAction;
  protected Action copySqlAction;
  protected Action sqlExecuteAgainAction;
  protected Action sqlExecuteAction;
  protected Action saveAsDelimitedAction;
  protected Action copyToClipboardAction;
  protected Action resultsClose;
  protected Action resultsCloseAll;
  protected Action viewReportAction;
  private ImageIcon imgQuery;
  //{{{ GsSqlMain()
  /**
   *  Adit constructor
   */
  public GsSqlMain() {


    about(false);
    
    initComponents();
    queryAction = CREATE_RESULTS;
    URL url = getClass().getResource("images/query.gif");
    imgQuery = new ImageIcon(url);
		btnQuery.setIcon(imgQuery);

    // init icons
    url = getClass().getResource("images/connect.gif");
    btnConnect.setIcon(new ImageIcon(url));
    btnUseConnection.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/new.gif");
    btnNew.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/disconnect.gif");
    btnDisconnect.setIcon(new ImageIcon(url));

    url = getClass().getResource("images/tables.gif");
    btnTables.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/open.gif");
    btnOpen.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/save.gif");
    btnSave.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/fit.gif");
    btnFit.setIcon(new ImageIcon(url));
    url = getClass().getResource("images/newdatabaseconnection.gif");
    btnNewConnection.setIcon(new ImageIcon(url));
    setSize(795, 550);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(screenSize.width / 2 - (getWidth() / 2), screenSize.height / 2 - (getHeight() / 2));
    setTitle("À¶±¦ " + version + " - disconnected");
    url = getClass().getResource("images/adit.png");
    setIconImage(new ImageIcon(url).getImage());
    currentSaveDirectory = null;
    // init connection to null
    con = null;
    loadConnections();
    // fire off the thread to update memory status
    Thread memStatusThread = new Thread(
        new Runnable() {
          public void run() {
            try{
              while(true){
                updateMemoryStats();
                Thread.sleep(1500);

              }
            }catch(InterruptedException ie){
            }
          }
        });
    memStatusThread.start();
    updateMemoryStats();

  }
  //}}}

  //{{{ initComponents()

  /**
   *  This method is called from within the constructor to initialize the form.
   *  WARNING: Do NOT modify this code. The content of this method is always
   *  regenerated by the Form Editor.
   */

  private void initComponents() {
    //GEN-BEGIN:initComponents
    GridBagConstraints gridBagConstraints;


    btnConnect = new JButton();
    btnDisconnect = new JButton();
    btnFit = new JToggleButton();
    btnNew = new JButton();
    btnNewConnection = new JButton();
    btnOpen = new JButton();
    btnQuery = new JButton(sqlExecuteAction);
    btnSave = new JButton();
    btnTables = new JButton();
    btnUseConnection = new JButton();
    cmbConnections = new JComboBox();
    copySqlAction = new CopySqlAction("Copy Sql");
    jcmbSqlStatements = new JComboBox();
    jMenu1 = new JMenu();
    jMenu2 = new JMenu();
    jMenuBar1 = new JMenuBar();
    jmnuFileExit = new JMenuItem();
    jmnuFileNew = new JMenuItem();
    jmnuFileOpen = new JMenuItem();
    jmnuFileSaveAs = new JMenuItem();
    jmnuHelpAbout = new JMenuItem();
    jmnuTools = new JMenu();
    jpopSqlTxtMenu = new JPopupMenu();
    jpopTabMenu = new JPopupMenu();
    jScrollPane1 = new JScrollPane();
    jSeparator1 = new JSeparator();
    jSeparator2 = new JSeparator();
    jSeparator3 = new JSeparator();
    jSplitPane1 = new JSplitPane();
    jtabResults = new JTabbedPane();
    jToolBar1 = new JToolBar();
    jToolBar2 = new JToolBar();
    jToolBar3 = new JToolBar();
    jToolBar4 = new JToolBar();
    jtxtSql = new JTextArea();
    pasteSqlAction = new SqlPasteAction("Paste Sql");
    resultsClose = new CloseResultAction("Close result table");
    resultsCloseAll = new CloseResultAllAction("Close all result tables");
    saveAsDelimitedAction = new SaveAsDelimitedAction("Save as delimited text");
    copyToClipboardAction = new CopyToClipboardAction("Copy results to clipboard");
    sqlExecuteAction = new SqlExecuteAction("",imgQuery);
    sqlExecuteAgainAction = new SqlExecuteAgainAction("Execute query again");
    viewReportAction = new ViewReportAction("View Report");
    sqlTxtMenuCopy = new JMenuItem();
    sqlTxtMenuCut = new JMenuItem();
    sqlTxtMenuPaste = new JMenuItem();
    tabClose = new JMenuItem(resultsClose);
    tabCloseAll = new JMenuItem(resultsCloseAll);
    tabCopySql = new JMenuItem(copySqlAction);
    tabHtmlReport = new JMenuItem(viewReportAction);
    tableTree = new GsSchemaTree(null);
    tabPasteSql = new JMenuItem(pasteSqlAction);
    tabProperties = new JMenuItem();
    tabRefresh = new JMenuItem(sqlExecuteAgainAction);
    tabSaveAs = new JMenuItem(saveAsDelimitedAction);
    tabSaveToClipboard = new JMenuItem(copyToClipboardAction);
    toolsHistoryClear = new JMenuItem();

    // add listener for table tree
    tableTree.addSchemaTreeListener(new GsSchemaTreeListener(){
      public void treeAction(GsSchemaTreeEvent e){
        int action = e.getAction();
        switch(action){
          case GsSchemaTree.ACTION_BROWSE:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "select * from " + makeTableQualifier(e.getTsc());
            //submitQuery(currentSqlStatement);
            browse();
            break;
          case GsSchemaTree.ACTION_REPORT:
            queryAction = VIEW_REPORT;
            currentSqlStatement = "select * from " + makeTableQualifier(e.getTsc());
            submitQuery(currentSqlStatement);
            break;
          case GsSchemaTree.ACTION_COUNT:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "select count(*) from " + makeTableQualifier(e.getTsc());
            browse();
            break;
          case GsSchemaTree.ACTION_SCHEMA:

            break;
          case GsSchemaTree.ACTION_FIELD_ORDERBY_ASCENDING:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "select * from " + makeTableQualifier(e.getTsc()) + " order by " + e.getField();
            browse();
            break;
          case GsSchemaTree.ACTION_FIELD_ORDERBY_DESCENDING:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "select * from " 
              + makeTableQualifier(e.getTsc()) 
              + " order by " + e.getField() + " desc";
            browse();
            break;
          case GsSchemaTree.ACTION_FIELD_SELECT_DISTINCT:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "select distinct " + e.getField() 
              + " from " +  makeTableQualifier(e.getTsc()) 
              + " order by " + e.getField();
            browse();
            break;
          case GsSchemaTree.ACTION_FIELD_CREATE_INDEX:
            queryAction = CREATE_RESULTS;
            currentSqlStatement = "create index " + e.getTable() 
              + "_" + e.getField() 
              + " on " 
              + makeTableQualifier(e.getTsc()) 
              + "(" + e.getField() + ")";
            createIndex(currentSqlStatement);
            break;
          case GsSchemaTree.ACTION_DROP_TABLE:
            dropTable(e.getTable());
            break;
        }
        /* if(action == "browse"){
           submitQuery("select * from " + e.getTable());
           }else
           } */
    }
  });


  mainPane = new JSplitPane();

  jpopTabMenu.add(tabRefresh);

  jpopTabMenu.add(tabSaveAs);

  jpopTabMenu.add(tabSaveToClipboard);
  
  jpopTabMenu.add(tabCopySql);


  jpopTabMenu.add(tabPasteSql);

  jpopTabMenu.add(tabHtmlReport);

  jpopTabMenu.add(jSeparator1);
  tabProperties.setText("Properties");
  tabProperties.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          tabPropertiesMouseReleased(evt);
        }
      });

  jpopTabMenu.add(tabProperties);
  jpopTabMenu.add(jSeparator2);

  jpopTabMenu.add(tabClose);

  jpopTabMenu.add(tabCloseAll);

  sqlTxtMenuCut.setText("Cut");
  sqlTxtMenuCut.setToolTipText("Cut text and add to clipboard");
  sqlTxtMenuCut.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          sqlTxtMenuCutMouseReleased(evt);
        }
      });

  jpopSqlTxtMenu.add(sqlTxtMenuCut);
  sqlTxtMenuCopy.setText("Copy");
  sqlTxtMenuCopy.setToolTipText("Copy selected text to clipboard");
  sqlTxtMenuCopy.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          sqlTxtMenuCopyMouseReleased(evt);
        }
      });

  jpopSqlTxtMenu.add(sqlTxtMenuCopy);
  sqlTxtMenuPaste.setText("Paste");
  sqlTxtMenuPaste.setToolTipText("Paste text from clipboard");
  sqlTxtMenuPaste.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          sqlTxtMenuPasteMouseReleased(evt);
        }
      });

  jpopSqlTxtMenu.add(sqlTxtMenuPaste);

  getContentPane().setLayout(new java.awt.GridBagLayout());

  addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent evt) {
          exitForm(evt);
        }
      });

  btnConnect.setFont(new java.awt.Font("Dialog", 1, 10));
  btnConnect.setIcon(new ImageIcon(""));
  btnConnect.setToolTipText("Connect by specifying driver, url, and user");
  btnConnect.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnConnectMouseReleased(evt);
        }
      });

  jToolBar2.add(btnConnect);

  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 0;
  gridBagConstraints.gridy = 0;
  gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
  getContentPane().add(jToolBar2, gridBagConstraints);

  btnNew.setFont(new java.awt.Font("Dialog", 1, 10));
  btnNew.setToolTipText("New query");
  btnNew.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnNewMouseReleased(evt);
        }
      });

  jToolBar1.add(btnNew);

  btnOpen.setToolTipText("Open SQL file");
  btnOpen.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnOpenMouseReleased(evt);
        }
      });

  jToolBar1.add(btnOpen);

  btnSave.setToolTipText("Save SQL file");
  btnSave.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnSaveMouseReleased(evt);
        }
      });

  jToolBar1.add(btnSave);

  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 1;
  gridBagConstraints.gridy = 0;
  gridBagConstraints.anchor = GridBagConstraints.WEST;
  getContentPane().add(jToolBar1, gridBagConstraints);

  btnQuery.setFont(new java.awt.Font("Dialog", 1, 10));
	btnQuery.setToolTipText("Execute query");
  btnQuery.addMouseListener(
			new java.awt.event.MouseAdapter(){
				public void mouseReleased(MouseEvent evt){
					btnQueryMouseReleased(evt);
				}
			});


  jToolBar3.add(btnQuery);

  btnTables.setFont(new java.awt.Font("Dialog", 1, 10));
  btnTables.setIcon(new ImageIcon(""));
  btnTables.setToolTipText("Show tables");
  btnTables.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnTablesMouseReleased(evt);
        }
      });

  //jToolBar3.add(btnTables);


  btnReloadTables = new JButton();
  URL url = getClass().getResource("images/reload.gif");
  btnReloadTables.setIcon(new ImageIcon(url));
  btnReloadTables.setToolTipText("Refresh table list");
  btnReloadTables.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          reloadMouseReleased(evt);
        }
      });
  jToolBar3.add(btnReloadTables);



  btnFit.setToolTipText("Expand/shrink result table");
  btnFit.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnFitMouseReleased(evt);
        }
      });



  jToolBar3.add(btnFit);

  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 2;
  gridBagConstraints.gridy = 0;
  gridBagConstraints.anchor = GridBagConstraints.WEST;
  getContentPane().add(jToolBar3, gridBagConstraints);

  jSplitPane1.setDividerLocation(80);
  jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
  jtabResults.setFont(new java.awt.Font("Dialog", 0, 11));
  jtabResults.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseClicked(MouseEvent evt) {
          jtabResultsMouseClicked(evt);
        }
        public void mousePressed(MouseEvent evt) {
          jtabResultsMouseClicked(evt);
        }
        public void mouseReleased(MouseEvent evt) {
          jtabResultsMouseClicked(evt);
        }

      });


  jSplitPane1.setRightComponent(jtabResults);

  jtxtSql.setLineWrap(true);
  jtxtSql.setMaximumSize(new Dimension(100, 100));
  jtxtSql.addKeyListener(
      new java.awt.event.KeyAdapter() {
        public void keyPressed(KeyEvent evt) {
          jtxtSqlKeyPressed(evt);
        }
      });

  jtxtSql.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jtxtSqlMouseReleased(evt);
        }
      });

  jScrollPane1.setViewportView(jtxtSql);

  jSplitPane1.setLeftComponent(jScrollPane1);

  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 0;
  gridBagConstraints.gridy = 3;
  gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
  gridBagConstraints.fill = GridBagConstraints.BOTH;
  gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
  gridBagConstraints.weightx = 1.0;
  gridBagConstraints.weighty = 1.0;

  mainPane.setDividerLocation(300);
  mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
  mainPane.setLeftComponent(tableTree);
  mainPane.setRightComponent(jSplitPane1);

  getContentPane().add(mainPane, gridBagConstraints);

  statusBar = new JLabel("A really, really really really long long long text label");
  //statusBar.setPreferredSize(new java.awt.Dimension(80, 20));
  statusBar.setMaximumSize(new Dimension(1280,20));
  statusBar.setMaximumSize(new Dimension(60,20));
  GridBagConstraints gbag = new GridBagConstraints();
  gbag.gridx = 0;
  gbag.gridy = 4;
  gbag.gridwidth=4;
  gbag.weightx = .50;
  gbag.weighty = 0;
  gbag.fill = GridBagConstraints.HORIZONTAL;
  getContentPane().add(statusBar, gbag);
  jcmbSqlStatements.setFont(new java.awt.Font("Dialog", 0, 11));
  jcmbSqlStatements.addItemListener(
      new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
          jcmbSqlStatementsItemStateChanged(evt);
        }
      });

  jcmbSqlStatements.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jcmbSqlStatementsMouseRelease(evt);
        }
      });

  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 0;
  gridBagConstraints.gridy = 2;
  gridBagConstraints.gridwidth = 5;
  //idBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
  gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
  getContentPane().add(jcmbSqlStatements, gridBagConstraints);

  cmbConnections.setFont(new java.awt.Font("Dialog", 0, 11));
  cmbConnections.setMinimumSize(new Dimension(80, 20));
  cmbConnections.setPreferredSize(new Dimension(80, 20));
  jToolBar4.add(cmbConnections);

  btnUseConnection.setToolTipText("Connect using the stored connection ");
  btnUseConnection.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnUseConnectionMouseReleased(evt);
        }
      });

  jToolBar4.add(btnUseConnection);

  btnNewConnection.setToolTipText("Create or manage connection definitions");
  btnNewConnection.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnNewConnectionMouseReleased(evt);
        }
      });

  jToolBar4.add(btnNewConnection);

  btnDisconnect.setToolTipText("Disconnect from the current database");
  btnDisconnect.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          btnDisconnectMouseReleased(evt);
        }
      });

  jToolBar4.add(btnDisconnect);


  gridBagConstraints = new GridBagConstraints();
  gridBagConstraints.gridx = 3;
  gridBagConstraints.gridy = 0;
  gridBagConstraints.gridwidth = 2;
  gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
  gridBagConstraints.anchor = GridBagConstraints.WEST;
  getContentPane().add(jToolBar4, gridBagConstraints);

  jMenu1.setText("File");
  jMenu1.setFont(new java.awt.Font("Dialog", 0, 11));




  jmnuFileNew.setFont(new java.awt.Font("Dialog", 0, 11));
  jmnuFileNew.setText("New Adit Window");
  jmnuFileNew.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuFileNewMouseReleased(evt);
        }
      });

  jMenu1.add(jmnuFileNew);




  jmnuFileOpen.setFont(new java.awt.Font("Dialog", 0, 11));
  jmnuFileOpen.setText("Open SQL...");
  jmnuFileOpen.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuFileOpenMouseReleased(evt);
        }
      });

  jMenu1.add(jmnuFileOpen);
  jmnuFileSaveAs.setFont(new java.awt.Font("Dialog", 0, 11));
  jmnuFileSaveAs.setText("Save SQL As...");
  jmnuFileSaveAs.setToolTipText("Save SQL text to a file");
  jmnuFileSaveAs.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuFileSaveAsMouseReleased(evt);
        }
      });

  jMenu1.add(jmnuFileSaveAs);
  jMenu1.add(jSeparator3);
  jmnuFileExit.setFont(new java.awt.Font("Dialog", 1, 10));
  jmnuFileExit.setText("Exit");
  jmnuFileExit.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuFileExitMouseReleased(evt);
        }
      });

  jMenu1.add(jmnuFileExit);
  jMenuBar1.add(jMenu1);

  //		set up the tools menu
  jmnuTools.setText("Tools");
  jmnuTools.setFont(new java.awt.Font("Dialog", 0, 11));
  toolsHistoryClear.setFont(new java.awt.Font("Dialog", 0, 11));
  toolsHistoryClear.setText("Clear History");
  toolsHistoryClear.setToolTipText("Clear the SQL statement history list");
  toolsHistoryClear.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuToolsClearHistoryMouseReleased(evt);
        }
      });

  jmnuTools.add(toolsHistoryClear);
  jMenuBar1.add(jmnuTools);
  
  menuItem = new JMenuItem("½ÚµãÐÅÏ¢Â¼Èë");
  menuItem.addActionListener(new ActionListener() {
  	public void actionPerformed(ActionEvent arg0) {
  		GatherNodeInfor dialog = new GatherNodeInfor();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
  	}
  });
  jmnuTools.add(menuItem);

  jMenu2.setText("Help");
  jMenu2.setFont(new java.awt.Font("Dialog", 0, 11));
  jmnuHelpAbout.setFont(new java.awt.Font("Dialog", 0, 11));
  jmnuHelpAbout.setText("About");
  jmnuHelpAbout.setToolTipText("About Adit");
  jmnuHelpAbout.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          jmnuHelpAboutMouseReleased(evt);
        }
      });

  jMenu2.add(jmnuHelpAbout);
  jMenuBar1.add(jMenu2);


  setJMenuBar(jMenuBar1);

  pack();
  // set up key mapping
  setKeyMappings();
  // load the history list
  loadHistory();
  // blank the sql area at load
  jtxtSql.setText("");
  }
  //GEN-END:initComponents
  //}}}
  //{{{ Mouse event handlers

  //{{{ btnNewConnectionMouseReleased()

/**
 *  New connection button handler
 *  Opens the dialog to add a new or edit existing connections
 *
 *@param  evt  MouseEvent
 */
private void btnNewConnectionMouseReleased(MouseEvent evt) {
  //GEN-FIRST:event_btnNewConnectionMouseReleased
  // Add your handling code here:
  GsStoredConnection gsc = new GsStoredConnection(null, true);
  // set the selected connection in the connection maintenance dialog
  // to the same connection currently selected in the main window
  gsc.setSelectedConnection((String)cmbConnections.getSelectedItem());
  gsc.setVisible(true);
  loadConnections();
  // set the selected connection to the one active in the 
  // GsStoredConnection dialog
  cmbConnections.setSelectedItem(gsc.getSelectedConnection());
}
//GEN-LAST:event_btnNewConnectionMouseReleased //}}}

//{{{ btnUseConnectionMouseReleased()
/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnUseConnectionMouseReleased(MouseEvent evt) {
  //GEN-FIRST:event_btnUseConnectionMouseReleased
  // Add your handling code here:
  connect((String) cmbConnections.getSelectedItem());
}
//GEN-LAST:event_btnUseConnectionMouseReleased
//}}}

//{{{ btnDisconnectMouseReleased()
/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnDisconnectMouseReleased(MouseEvent evt) {
  disconnect();
}
//GEN-LAST:event_btnDisconnectMouseReleased
//}}}
//{{{ btnFitMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnFitMouseReleased(MouseEvent evt) {
  //GEN-FIRST:event_btnFitMouseReleased
  // Add your handling code here:
  fitResults();
}
//GEN-LAST:event_btnFitMouseReleased
//}}}

//{{{ btnSaveMouseReleased()
/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnSaveMouseReleased(MouseEvent evt) {

  saveSQL();
}
//}}}

//{{{ btnOpenMouseReleased()
/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnOpenMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_btnOpenMouseReleased
  // Add your handling code here:
  openSQL();
}
//}}}

//{{{ jmnuHelpAboutMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jmnuHelpAboutMouseReleased(MouseEvent evt) {

  about(true);
}
//}}}



//{{{ jmnuFileNewMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jmnuFileNewMouseReleased(MouseEvent evt) {

  // Add your handling code here:
  Runtime rt = Runtime.getRuntime();
  //rt.exec("adit");
}
//}}}


//{{{ jmnuFileOpenMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jmnuFileOpenMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_jmnuFileOpenMouseReleased
  // Add your handling code here:
  openSQL();
}
//}}}

//{{{ jmnuFileSaveAsMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jmnuFileSaveAsMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_jmnuFileSaveAsMouseReleased
  // Add your handling code here:
  saveSQL();
}
//}}}

private void jmnuToolsClearHistoryMouseReleased(MouseEvent evt){
  jcmbSqlStatements.removeAllItems();
}

//{{{ sqlTxtMenuPasteMouseReleased()


/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void sqlTxtMenuPasteMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_sqlTxtMenuPasteMouseReleased
  // Add your handling code here:
  jtxtSql.paste();
}

//}}}
//{{{ sqlTxtMenuCopyMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void sqlTxtMenuCopyMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_sqlTxtMenuCopyMouseReleased
  // Add your handling code here:
  jtxtSql.copy();
}
//}}}

//{{{ sqlTxtMenuCutMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void sqlTxtMenuCutMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_sqlTxtMenuCutMouseReleased
  jtxtSql.cut();
}

//}}}

//{{{ jtxtSqlMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jtxtSqlMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_jtxtSqlMouseReleased
  // Add your handling code here:
  int btn = evt.getButton();
  if((btn == MouseEvent.BUTTON3)) {
    jpopSqlTxtMenu.show(jtxtSql, evt.getX(), evt.getY());
  }
}
//}}}

//{{{ jcmbSqlStatementsMouseRelease()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jcmbSqlStatementsMouseRelease(MouseEvent evt) {

  //GEN-FIRST:event_jcmbSqlStatementsMouseRelease
  // Add your handling code here:
  //  jtxtSql.setText((String) jcmbSqlStatements.getSelectedItem());
}
//}}}


/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void tabPropertiesMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_tabPropertiesMouseReleased
  // Add your handling code here:
  showProperties();
}

//}}}

//{{{ tabCloseMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void tabCloseMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_tabCloseMouseReleased
  // Add your handling code here:
  jtabResults.removeTabAt(jtabResults.getSelectedIndex());
}
//}}}

//{{{ btnNewMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnNewMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_btnNewMouseReleased
  // Add your handling code here:
  jtxtSql.setText("");
  jtxtSql.requestFocus();
}

//}}}

//{{{ jtxtSqlKeyPressed()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jtxtSqlKeyPressed(KeyEvent evt) {

  //GEN-FIRST:event_jtxtSqlKeyPressed
  // Add your handling code here:

  int k = evt.getKeyCode();
  //System.out.println("KeyCode: " + k);
  switch (k) {
    //		case KeyEvent.VK_F5:	// f5 - execute query
    //			doQuery();
    //			break;
    case KeyEvent.VK_N:	// ctrl+n - clear sql text

      if(evt.isControlDown()){
        jtxtSql.setText("");
        jtxtSql.requestFocus();
      }
      break;
      //		case KeyEvent.VK_S:
      //			if(evt.isControlDown()){
      //				// save query
      //				saveSQL();
      //			}
      //			break;
      //		case KeyEvent.VK_V:
      //			if(evt.isControlDown() && evt.isShiftDown()){
      //				pasteSql();
      //			}

  }
}
//}}}

//{{{ btnTablesMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnTablesMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_btnTablesMouseReleased
  // Add your handling code here:
  if(isConnected) {
    showTables();
  }
}
//}}}


//{{{ jtabResultsMouseClicked()

/**
 *  Displays the result table popup menu if the
 *  popup trigger is clicked
 *
 *@param  evt  Description of the Parameter
 */
private void jtabResultsMouseClicked(MouseEvent evt) {

  if(jtabResults.getTabCount() > 0) {
    //update the fit button state
    GsTable tab = (GsTable) getCurrentTable();
    btnFit.setSelected(tab.getFitState());
    int btn = evt.getButton();
    if((evt.isPopupTrigger()) && (jtabResults.getTabCount() > 0)) {
      jpopTabMenu.show(jtabResults, evt.getX(), evt.getY());

    }
  }
}
//}}}

//{{{ jmnuFileExitMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void jmnuFileExitMouseReleased(MouseEvent evt) {
  storeHistory();
  //GEN-FIRST:event_jmnuFileExitMouseReleased
  // Add your handling code here:
  System.exit(0);
}
//}}}

//{{{ btnConnectMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnConnectMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_btnConnectMouseReleased
  // Add your handling code here:
  connect();
}

//}}}

//{{{ btnQueryMouseReleased()

/**
 *  Description of the Method
 *
 *@param  evt  Description of the Parameter
 */
private void btnQueryMouseReleased(MouseEvent evt) {

  //GEN-FIRST:event_btnQueryMouseReleased
  // Add your handling code here:
  doQuery();
}
//}}}

//{{{ reloadMouseReleased()
public void reloadMouseReleased(MouseEvent evt) {
  tableTree.loadTables();
}
//}}}

//{{{ jcmbSqlStatementsItemStateChanged()

/**
 *  Sets the sql text area to the contents of the selected
 *  statement in the sql statements combo box
 *
 */
private void jcmbSqlStatementsItemStateChanged(java.awt.event.ItemEvent evt) {

  // Add your handling code here:
  jtxtSql.setText((String) jcmbSqlStatements.getSelectedItem());
}

//}}}


//{{{ exitForm()

/**
 *  Exit the Application
 *
 *@param  evt  Description of the Parameter
 */
private void exitForm(java.awt.event.WindowEvent evt) {
  storeHistory();
  //GEN-FIRST:event_exitForm
  System.exit(0);
}

//}}}

//{{{ main()

/**
 *@param  args  the command line arguments
 */
 



//}}}

//{{{ about()
/**
 *  Display the Adit splash screen
 *
 *@param  centerInParent  If true, the splash screen will be centered in the Adit form
 */
public void about(boolean centerInParent) {
  aboutWindow = new JWindow(this);
  //System.out.println("Showing abt");
  aboutWindow.setSize(200, 107);
  if(centerInParent) {

    Point pt = getLocationOnScreen();
    aboutWindow.setLocation((int) (pt.getX() + (getWidth() - aboutWindow.getWidth()) / 2), (int) pt.getY() + (getHeight() - aboutWindow.getHeight()) / 2);
  } else {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    aboutWindow.setLocation(screenSize.width / 2 - (aboutWindow.getWidth() / 2), screenSize.height / 2 - (aboutWindow.getHeight() / 2));
  }

  //a
  aboutWindow.getContentPane().setBackground(new Color(255, 255, 255));

  // get the image
  URL url = getClass().getResource("images/adit.png");
  JLabel msg = new JLabel(new ImageIcon(url));
  aboutWindow.getContentPane().add(msg);
  aboutWindow.addMouseListener(
      new java.awt.event.MouseAdapter() {
        public void mouseReleased(MouseEvent evt) {
          aboutWindow.dispose();

          //jmnuFileSaveAsMouseReleased(evt);
        }
      });
  aboutWindow.setVisible(true);
}
//}}}

//{{{ newWindow()
/**
 *  Creates a new instance of adit
 */
public void newWindow() {

  GsSqlMain newWindow = new GsSqlMain();
}
//}}}

//{{{ connect() stored connection
/**
 *  Connect using named connection (stored)
 *
 *@param  storedConnection  Name of the stored connection to use 
 */
public void connect(String storedConnection) {
  setWaitCursor(true);
  // get the parameters for the connection
  //  Preferences temppref = Preferences.userRoot().node("mrcc/adit/connections/testconnection");
  if(storedConnection.length() > 0) {
    // temppref.put("driver","driverfoo");
    Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections/" + storedConnection);
    String driver = dbpref.get("driver", null);
    String url = dbpref.get("url", null);
    String username = dbpref.get("username", null);
    String password = dbpref.get("password", null);
    boolean requiresPassword = dbpref.getBoolean("requirepassword", false);
    if(driver != null && url != null) {
      // some connections don't require a username/password
      if(username != null) {
        if(requiresPassword && (password == null || password.length() == 0)) {
          // prompt for password
          GsPassword pass = new GsPassword(null,"Password for " + storedConnection, true);

          pass.show();
          password = pass.getPassword();
          if(password == null)
            password = "";

        }
      }

      try {
        System.out.println("Attempting to load the driver: " + driver);
        Class.forName(driver);

      } catch(ClassNotFoundException nc) {
        JOptionPane.showMessageDialog(this, "Driver not found", "Error Loading Driver", JOptionPane.ERROR_MESSAGE);
        System.out.println("Driver not found");
        setWaitCursor(false);

      }

      try {
        if(con != null){
          con.close();
          setTitle("À¶±¦ " + version);
        }
        con = DriverManager.getConnection(url, username, password);
        System.out.println("Connected to the database");
        isConnected = true;
        setTitle("À¶±¦ " + version + " - Connected using " + storedConnection);
        tableTitle = storedConnection;
        showTables();

      } catch(SQLException se) {
        System.out.println("SQL Exception: " + se.getMessage());
        String msg = se.getMessage();

        JOptionPane.showMessageDialog(this, se.getMessage());
        //getMessage());
        setWaitCursor(false);
      }
    }
  }
} //}}}
//{{{ disconnect
public void disconnect(){ 
  try{
    if(con != null){
      con.close();
      setTitle("À¶±¦ - disconnected");
      tableTree.clearTables();
    }
  }catch(SQLException se){
  }
}

//}}}

//{{{ connect() quick connect
/**
 *  Connect using driver and url entered by user. This method opens a 
 * dialog for entering the url, driver, username, and password
 */
public void connect() {
  setWaitCursor(true);
  // close the existing connection
  try{
    if(con != null){
      con.close();
      setTitle("À¶±¦ " + version);
    }
  }catch(SQLException se){
  }
  GsConnection conParms = new GsConnection(null, true);

  //  conParms.setDriver(driver);
  conParms.setUser(user);
  conParms.setPasswd(passwd);
  conParms.setLocationRelativeTo(this);
  conParms.setVisible(true);
  if(conParms.doConnect()) {

    con = conParms.getConnection();
    isConnected = (con != null);
    if(isConnected) {

      // get the host and database name from the url
      databaseUrl = conParms.getUrl();

      // sanitfize the url if it contains a password
      if(databaseUrl.indexOf("password") > 0) {
        databaseUrl = databaseUrl.substring(0, databaseUrl.indexOf("password"));
      }

      // String host = url.substring(url.indexOf("//") + 2, url.indexOf("/", url.indexOf("//") + 2));
      // String database = url.substring(url.indexOf("/", url.indexOf("//") + 2)+1, url.indexOf();
      setTitle("À¶±¦ " + version + " - Connected using " + databaseUrl);
      tableTitle = databaseUrl;
      showTables();
    }

  }
  setWaitCursor(false);
} //}}}
//{{{ showTables()
/**
 *  Description of the Method
 */
public void showTables() {

  Thread tableThread = new Thread(
      new Runnable() {
        public void run() {
          showTableTree();
        }
      });
  tableThread.start();
} //}}}


//{{{ htmlReportFromQuery()
public void htmlReportFromQuery(final Vector rows, final Vector cols) {

  Thread reportThread = new Thread(
      new Runnable() {
        public void run() {
          viewHtmlReport(rows, cols);
        }
      });
  reportThread.start();
} //}}}

//{{{ htmlReportFromResultTable()

public void htmlReportFromResultTable() {

  Thread tableReportThread = new Thread(
      new Runnable() {
        public void run() {
          viewHtmlReport();
        }
      });
  tableReportThread.start();
}
//}}}

//{{{ browse()
public void browse(){
  Thread browseThread = new Thread(
      new Runnable() {
        public void run() {
          submitQuery(currentSqlStatement);
        }
      });
  browseThread.start();
} //}}}

//{{{ showTableTree()
/**
 *  Description of the Method
 */
public void showTableTree() {
  setWaitCursor(true);
  //GsSchemaTree sTree = new GsSchemaTree(con);
  tableTree.setConnection(con);
  tableTree.setSqlTextField(jtxtSql);
  //this, false);
  tableTree.setVisible(true);
  //sTree.setTitle("Tables - " + tableTitle);

  URL url = getClass().getResource("images/adit.png");
  //sTree.setIconImage(new ImageIcon(url).getImage());
  tableTree.loadTables();
  setWaitCursor(false);
} //}}}


//{{{ showProperties()
/**
 *  Description of the Method
 */
private void showProperties() {

  GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
  String msg = currentTable.getSqlStatement();
  String rowCnt = "Rows returned by the query: " + currentTable.getSqlRowCount();
  GsMessageDialog gsm = new GsMessageDialog(null,"Query Properties", true);
  gsm.setSize(500, 150);
  gsm.setMessage(msg);
  gsm.setCustomLabel(rowCnt);

  Point pt = getLocationOnScreen();
  gsm.setLocation((int) (pt.getX() + (getWidth() - gsm.getWidth()) / 2), (int) pt.getY() + (getHeight() - gsm.getHeight()) / 2);
  gsm.setVisible(true);

  // JOptionPane.showMessageDialog(this,msg,"Query Properties",JOptionPane.INFORMATION_MESSAGE);
}
//}}}

//{{{ doQuery()
/**
 *  Description of the Method
 */
private void doQuery() {
  setWaitCursor(true);
  String sql = "";
  if(queryAction == REFRESH_RESULTS){
    GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
    sql = currentTable.getSqlStatement();
  }else{
    sql = jtxtSql.getText();
  }

  sql = sql.toLowerCase();
  if(sql.indexOf("select") == 0) {
    query();
  } else {
    updateQuery();
  }

  setWaitCursor(false);
} //}}}


//{{{ updateQuery()
/**
 *  Description of the Method
 *
 *@return    Description of the Return Value
 */
public int updateQuery() {
  if(isConnected) {
    jcmbSqlStatements.addItem(jtxtSql.getText());
    jcmbSqlStatements.setSelectedIndex(jcmbSqlStatements.getItemCount() - 1);
    try {

      Statement stmt = con.createStatement();
      int rows = stmt.executeUpdate(jtxtSql.getText());
      JOptionPane.showMessageDialog(this, "" + rows + " rows affected by query", "Query Result", JOptionPane.INFORMATION_MESSAGE);
    } catch(SQLException se) {
      System.out.println(se.getMessage());
      JOptionPane.showMessageDialog(this, "SQL Exception: " + se.getMessage() + "(" + se.getErrorCode() + ")", "Query Result", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  return 0;
} //}}}

//{{{ createIndex(String sql)
public void createIndex(String sql){
  Statement stmt = null;
  try {

    stmt = con.createStatement();
    int rows = stmt.executeUpdate(sql);
    JOptionPane.showMessageDialog(this,"Created Index","Query Result", JOptionPane.INFORMATION_MESSAGE);
    stmt.close();
  } catch(SQLException se) {
    System.out.println(se.getMessage());
    JOptionPane.showMessageDialog(this, "SQL Execption: " + se.getMessage() + "(" + se.getErrorCode() + ")", "Query Result", JOptionPane.INFORMATION_MESSAGE);

  }
} //}}}

//{{{ dropTable
public void dropTable(String table){
  int res = JOptionPane.showConfirmDialog(this, 
      "Are you sure you want to drop table " + table + "?", "Drop Table " + table, JOptionPane.YES_NO_OPTION);
  if(res == JOptionPane.YES_OPTION){
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("drop table " + table);
      stmt.close();
      tableTree.loadTables();
      JOptionPane.showMessageDialog(this,"Dropped table " + table, "Drop Table", JOptionPane.INFORMATION_MESSAGE);
    } catch(SQLException se) {
      System.out.println(se.getMessage());
      JOptionPane.showMessageDialog(this, "SQL Execption: " + se.getMessage() + "(" + se.getErrorCode() + ")", "Drop Table Result", JOptionPane.INFORMATION_MESSAGE);
    }
  }

}
//}}}

//{{{ submitQuery()
public int submitQuery(String sql){
  try {
    setWaitCursor(true);
    currentSqlStatement = sql;
    System.out.println("SQL is: " + sql);
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    if(rs != null) {

      ResultSetMetaData rsmd = rs.getMetaData();

      // create a new tab
      int numberOfColumns = rsmd.getColumnCount();
      Vector cols = new Vector();
      for(int column = 0; column < numberOfColumns; column++) {
        cols.addElement(rsmd.getColumnLabel(column + 1));
      }

      // Get all rows.
      Vector rows = new Vector();
      rowCount = 0;
      while(rs.next()) {
        rowCount++;

        Vector newRow = new Vector();
        String s = null;
        for(int i = 1; i <= rsmd.getColumnCount(); i++) {
          switch (rsmd.getColumnType(i)) {

            case java.sql.Types.CLOB:

              s = rs.getString(i);
              if(rs.wasNull()) {
                s = "";
              }

              // String clbText = clb.getSubString(start,len);
              newRow.addElement(s);

              break;
            case java.sql.Types.LONGVARCHAR:
              s = rs.getString(i);
              if(rs.wasNull()) {
                s = "";
              }
              //System.out.println("Longvarchar");
              // String clbText = clb.getSubString(start,len);
              newRow.addElement(s);
              break;
            case java.sql.Types.OTHER:

              String msg = rsmd.getColumnTypeName(i);
              newRow.addElement("[? " + msg + " ?]");

              break;
            case java.sql.Types.JAVA_OBJECT:
              msg = rsmd.getColumnTypeName(i);
              System.out.println("Class for this object: " + rsmd.getColumnClassName(i));
              System.out.println("Column type: " + msg);
              // deal with geometry types
              if(msg.toLowerCase().equals("st_geometry")){

                /* try{
                   java.util.Map udtMap = con.getTypeMap();
                   udtMap.put("st_geometry", Class.forName("com.esri.sde.sdk.geom.SePoint"));							
                   Object o = rs.getObject(i);//, udtMap);
                //newRow.addElement(o.getClass().getName());
                }catch(ClassNotFoundException cnfe){
                System.out.println("ERROR: " + cnfe.getMessage());
                }  */


                // java.io.InputStream in = rs.getAsciiStream(i);
                //	try{
                //	int size = in.available();
                //	System.out.println("Size of st_point is " + size);
                //	byte ary[] = new byte[size];
                //	in.read(ary,0,size);
                byte ary[] = rs.getBytes(i);
                newRow.addElement(ary);
                /* 	}catch(java.io.IOException ioe){
                    System.out.println("IO exception on st_point read");
                    newRow.addElement("ERROR!");
                    }  */

              }else{
                newRow.addElement("[? " + msg + " ?]");
              }

              break;
            default:
              newRow.addElement(rs.getObject(i));

              break;
          }
        }

        rows.addElement(newRow);
      }
      switch(queryAction){
        case REFRESH_RESULTS:
          createResultTable(rows, cols);
          break;
        case VIEW_REPORT:
          htmlReportFromQuery(rows, cols);
          break;
        case CREATE_RESULTS:
          createResultTable(rows, cols);
          break;
      }
      // resture default action
      queryAction = CREATE_RESULTS;
      /* if(refreshResults){
         createResultTable(rows, cols);//replaceResultTable(rows,cols);
         refreshResults = false;
         }else{
         createResultTable(rows, cols);
         } */

    } else {
      JOptionPane.showMessageDialog(this, "Query returned no rows", "No rows returned", JOptionPane.INFORMATION_MESSAGE);
    }
  } catch(SQLException se) {
    System.out.println(se.getMessage());
    JOptionPane.showMessageDialog(this, "Query Result: " + se.getMessage(), "Query Result", JOptionPane.INFORMATION_MESSAGE);
  }
  setWaitCursor(false);
  return 0;
} //}}}

//{{{ query()
/**
 *  Description of the Method
 *
 *@return    Description of the Return Value
 */
public int query() {
  if(isConnected) {
    String sql = "";
    if(queryAction == REFRESH_RESULTS){
      GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
      sql = currentTable.getSqlStatement();
      logger.info("Refreshing results");
    }else{
      sql = jtxtSql.getText();

      // remove the statment if its already in list and add to the end
      for(int i=0; i < jcmbSqlStatements.getItemCount(); i++){
        String item = (String)jcmbSqlStatements.getItemAt(i);
        if(item.equals(sql)){
          jcmbSqlStatements.removeItemAt(i);
          break;
        }
      }
      jcmbSqlStatements.addItem(sql);
      jcmbSqlStatements.setSelectedIndex(jcmbSqlStatements.getItemCount() - 1);

    }
    submitQuery(sql);

  } else {
    JOptionPane.showMessageDialog(this, "You must be connected to submit SQL statements", "Not Connected", JOptionPane.ERROR_MESSAGE);
  }

  return 0;
} //}}}


//{{{ openSQL()
/**
 *  Description of the Method
 */
public void openSQL() {

  JFileChooser openFile = new JFileChooser();
  openFile.addChoosableFileFilter(new TxtFilter());
  openFile.addChoosableFileFilter(new SqlFilter());
  openFile.setDialogType(JFileChooser.OPEN_DIALOG);
  openFile.setCurrentDirectory(currentSqlDirectory);
  openFile.setDialogTitle("Open SQL File");

  int returnVal = openFile.showOpenDialog(this);
  if(returnVal == JFileChooser.APPROVE_OPTION) {
    currentSqlDirectory = openFile.getCurrentDirectory();
    try {

      FileReader sqlFile = new FileReader(openFile.getSelectedFile());
      char[] buf = new char[(int) openFile.getSelectedFile().length()];
      sqlFile.read(buf, 0, (int) openFile.getSelectedFile().length());

      String sBuf = new String(buf);
      jtxtSql.setText(new String(buf));
      sqlFile.close();
    } catch(IOException ioe) {
      JOptionPane.showMessageDialog(this, "There was a problem opening the SQL file:  " + ioe.getMessage(), "Open SQL File", JOptionPane.WARNING_MESSAGE);
    }
  }

  jtxtSql.requestFocus();
} //}}}


//{{{ saveSQL()
/**
 *  Description of the Method
 */
public void saveSQL() {

  JFileChooser saveFile = new JFileChooser();
  saveFile.addChoosableFileFilter(new TxtFilter());
  saveFile.addChoosableFileFilter(new SqlFilter());
  saveFile.setDialogType(JFileChooser.SAVE_DIALOG);
  saveFile.setCurrentDirectory(currentSqlDirectory);
  saveFile.setDialogTitle("Save SQL");
  int returnVal = saveFile.showSaveDialog(this);
  if(returnVal == JFileChooser.APPROVE_OPTION) {
    currentSqlDirectory = saveFile.getCurrentDirectory();
    try {

      FileWriter sqlFile = new FileWriter(saveFile.getSelectedFile());
      sqlFile.write(jtxtSql.getText() + "\n");
      sqlFile.close();
    } catch(IOException ioe) {
      JOptionPane.showMessageDialog(this, "There was a problem saving the SQL to disk:  " + ioe.getMessage(), "Save SQL File", JOptionPane.WARNING_MESSAGE);
    }
  }

  jtxtSql.requestFocus();
} //}}}


//{{{ fitResults()
/**
 *  Description of the Method
 */
public void fitResults() {
  GsTable tab = (GsTable) getCurrentTable();
  if(tab != null) {
    fitToWidth = !tab.getFitState();
    tab.setFitState(fitToWidth);
    if(fitToWidth) {
      tab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    } else {
      tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    doLayout();
  } else {
    btnFit.setSelected(fitToWidth);
  }
} //}}}

//{{{ viewHtmlReport()
// create an html report from row, col data
public void viewHtmlReport(Vector rows, Vector cols){
  setWaitCursor(true);
  GsHtmlReport rpt = new GsHtmlReport();
  // display the result set in an html report format
  String sql = tableTitle + ": select * from " + tableTree.getSelectedTable();
  rpt.setSqlText(sql);
  // create a temporary file
  try{
    File rptFile = File.createTempFile("adit_",".html");
    FileWriter htmlFile = new FileWriter(rptFile);
    htmlFile.write("<html><body><table border=0 width=100%>");
    //htmlFile.write("<html><body>");
    for(int ir = 0; ir < rows.size(); ir++) {

      for(int cr = 0; cr < cols.size(); cr++) {
        htmlFile.write("<tr>");
        htmlFile.write("<td valign=top><b>");
        htmlFile.write("" + cols.get(cr));
        htmlFile.write("</b></td><td valign=top>");
        Vector thisRow = (Vector)rows.get(ir);
        htmlFile.write("" + thisRow.get(cr));

        htmlFile.write("</td>");

      }
      htmlFile.write("</tr>");

      htmlFile.write("<tr><td colspan=2><hr></td></tr>"); // separator row
    }

    htmlFile.write("</table></body></html>");
    htmlFile.close();

    rpt.setContent(rptFile.getAbsolutePath());
    rpt.show();
    rptFile.delete();
  }catch(IOException ioe){
    System.out.println("Unable to create temporary file");
  }
  setWaitCursor(false);
} //}}}

//{{{ viewHtmlReport()
// create html report from existing table
public void viewHtmlReport(){
  GsScrollPane gsScrollPane = (GsScrollPane) jtabResults.getSelectedComponent();
  if(gsScrollPane != null){
    setWaitCursor(true);
    GsHtmlReport rpt = new GsHtmlReport();
    // display the result set in an html report format
    String sql = tableTitle + ": " + gsScrollPane.getSqlStatement();
    rpt.setSqlText(sql);
    JTable currentTable = getCurrentTable();
    TableModel tm = currentTable.getModel();
    try{
      File rptFile = File.createTempFile("adit_",".html");
      FileWriter htmlFile = new FileWriter(rptFile);
      htmlFile.write("<html><body><table border=0 width=100%>");

      /* for(int it = 0; it < tm.getColumnCount(); it++) {
         delimFile.write(currentTable.getColumnName(it) + "|");
         } */

      int rows = tm.getRowCount();
      int cols = tm.getColumnCount();
      for(int ir = 0; ir < rows; ir++) {

        for(int cr = 0; cr < cols; cr++) {
          htmlFile.write("<tr>");
          //System.out.println("Column: " + cr);
          htmlFile.write("<td valign=top><b>");
          htmlFile.write(currentTable.getColumnName(cr));
          htmlFile.write("</b></td><td valign=top>");
          if(tm.getValueAt(ir, cr) != null){
            //System.out.println(tm.getValueAt(ir, cr).getClass().getName());
            //	if(tm.getValueAt(ir, cr).getClass().getName().equals("String")){
            htmlFile.write(tm.getValueAt(ir, cr).toString());
            htmlFile.write("</td>");

          }else{
            htmlFile.write("</td>");
          }
          htmlFile.write("</tr>");
          }

          htmlFile.write("<tr><td colspan=2><hr></td></tr>"); // separator row
        }


        htmlFile.write("</table></body></html>");
        htmlFile.close();

        rpt.setContent(rptFile.getAbsolutePath());
        rpt.show();
        rptFile.delete();
      }catch(IOException ioe){
        System.out.println("Unable to create temporary file");
      }
      setWaitCursor(false);
    }
  } //}}}

//{{{ saveResults()
/**
 *  Description of the Method
 */
public void saveResults() {

  // save current result table to a text file
  JFileChooser saveFile = new JFileChooser();
  saveFile.setDialogType(JFileChooser.SAVE_DIALOG);
  saveFile.setCurrentDirectory(currentSaveDirectory);
  saveFile.setDialogTitle("Save as Delimited Text");
  int returnVal = saveFile.showSaveDialog(this);
  if(returnVal == JFileChooser.APPROVE_OPTION) {
    setWaitCursor(true);
    currentSaveDirectory = saveFile.getCurrentDirectory();
    //System.out.println("Creating FileWriter");
    try {

      FileWriter delimFile = new FileWriter(saveFile.getSelectedFile());
      /*
       *  GsScrollPane scrollpane = (GsScrollPane)jtabResults.getSelectedComponent();
       *  JViewport vp = scrollpane.getViewport();
       *  Component child = null;
       *  for (int i = 0; i < vp.getComponentCount(); i++)
       *  {
       *  child = vp.getComponent(i);
       *  System.out.println(child.getClass().getName());
       *  if (child.getClass().getName().equals("JTable"))
       *  {
       *  System.out.println("Found the JTable");
       *  break;
       *  }
       *  }
       */
      // get the table model
      //System.out.println("Geting current table");
      JTable currentTable = getCurrentTable();
      //System.out.println("Getting table model");
      TableModel tm = currentTable.getModel();

      // write out column names
      //System.out.println("Writing column names");
      for(int it = 0; it < tm.getColumnCount(); it++) {
        delimFile.write(currentTable.getColumnName(it) + "|");
      }

      delimFile.write("\n");
      //System.out.println("Writing table contents");
      // write out the table contents
      int rows = tm.getRowCount();
      int cols = tm.getColumnCount();
      for(int ir = 0; ir < rows; ir++) {
        //System.out.println("Row: " + ir);
        for(int cr = 0; cr < cols; cr++) {
          //System.out.println("Column: " + cr);
          if(tm.getValueAt(ir, cr) != null){
            if(tm.getValueAt(ir, cr).getClass().getName().equals("String")){
              String value = (String)tm.getValueAt(ir, cr);
              if(value == null){
                value = "";
              }
              value = value.trim();
              delimFile.write(value + "|");
            }else{
              delimFile.write(tm.getValueAt(ir, cr) + "|");
            }
          }else{
            delimFile.write("|");
          }
        }

        delimFile.write("\n");
      }

      delimFile.close();
    } catch(IOException ioe) {
    }
  }
  setWaitCursor(false);
  jtxtSql.requestFocus();
} //}}}


public void saveToClipboard() {

	  // save current result table to the clipboard as delimited text
	   
	      // get the table model
	      //System.out.println("Geting current table");
	      JTable currentTable = getCurrentTable();
	      //System.out.println("Getting table model");
	      TableModel tm = currentTable.getModel();

	      // write out column names
	      String data = "";
	      //System.out.println("Writing column names");
	      for(int it = 0; it < tm.getColumnCount(); it++) {
	        data += currentTable.getColumnName(it) + "|";
	      }

	      data += "\n";
	      
	      // write out the table contents
	      int rows = tm.getRowCount();
	      int cols = tm.getColumnCount();
	      for(int ir = 0; ir < rows; ir++) {
	        //System.out.println("Row: " + ir);
	        for(int cr = 0; cr < cols; cr++) {
	          //System.out.println("Column: " + cr);
	          if(tm.getValueAt(ir, cr) != null){
	            if(tm.getValueAt(ir, cr).getClass().getName().equals("String")){
	              String value = (String)tm.getValueAt(ir, cr);
	              if(value == null){
	                value = "";
	              }
	              value = value.trim();
	              data += value + "|";
	            }else{
	              data += tm.getValueAt(ir, cr) + "|";
	            }
	          }else{
	            data += "|";
	          }
	        }

	        data += "\n";
	      }

	  // push it to the clipboard
	      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
          clip.setContents(new StringSelection(data),this );
	  setWaitCursor(false);
	  jtxtSql.requestFocus();
	} //}}}




//{{{ getCurrentTable()
/**
 *  Gets the currentTable attribute of the GsSqlMain object
 *
 *@return    The currentTable value
 */
public JTable getCurrentTable() {
  GsScrollPane scrollpane = (GsScrollPane) jtabResults.getSelectedComponent();
  Component child = null;
  if(scrollpane != null) {
    JViewport vp = scrollpane.getViewport();
    for(int i = 0; i < vp.getComponentCount(); i++) {
      child = vp.getComponent(i);
      // System.out.println(child.getClass().getName());
      if(child.getClass().getName().equals("GesSQL.GsTable")) {
        break;
      }
    }
  }
  return (JTable) child;
} //}}}

//{{{ getCurrentTableIndex()
public int getCurrentTableIndex(){
  int currentIndex = -1;
  GsScrollPane scrollpane = (GsScrollPane) jtabResults.getSelectedComponent();
  Component child = null;
  if(scrollpane != null) {
    JViewport vp = scrollpane.getViewport();
    for(int i = 0; i < vp.getComponentCount(); i++) {
      child = vp.getComponent(i);
      System.out.println(child.getClass().getName());
      if(child.getClass().getName().equals("GesSQL.GsTable")) {
        currentIndex = i;
        break;
      }
    }
  }
  return currentIndex;
} //}}}

//{{{ setKeyMappings()
/**
 * Setup key mappings
 *
 */
private void setKeyMappings(){
  // mappings for the result window
  // Paste sql from result table into the sql editor
  //	SqlPasteAction sqlPasteAction = new SqlPasteAction("pasteSql");
  InputMap jtabMap = jtabResults.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
  ActionMap jtabAction = jtabResults.getActionMap();
  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
      "pasteSql");
  jtabAction.put("pasteSql",pasteSqlAction);

  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
      "executeAgain");
  jtabAction.put("executeAgain", sqlExecuteAgainAction);

  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK ), "closeResult");
  jtabAction.put("closeResult", resultsClose);

  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK), "closeAllResults");
  jtabAction.put("closeAllResults", resultsCloseAll);

  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
      "copySql");
  jtabAction.put("copySql",copySqlAction);
  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
      "saveDelimited");
  jtabAction.put("saveDelimited",saveAsDelimitedAction);
  jtabMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK),
      "viewReport");
  jtabAction.put("viewReport",viewReportAction);

  // query mapping
  btnQuery.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),"executeSql");
  btnQuery.getActionMap().put("executeSql", sqlExecuteAction);


}
//}}}

//{{{ loadHistory()
/**
 * Load history from disk
 *
 */
private void loadHistory(){
  try {
    String file= GsSqlMain.class.getResource("/config/sqlhistory.bin").getFile();
    FileInputStream fi = new FileInputStream(new File(file));
    ObjectInputStream si = new ObjectInputStream(fi);  
    Vector history = (Vector) si.readObject();
    si.close();
    // add the vector contents to the sql history drop-down
    for(int i = 0; i < history.size(); i++){
      jcmbSqlStatements.addItem(history.get(i));
    }
  } catch (Exception e) {
    e.printStackTrace();

  }

} //}}}

//{{{ storeHistory()
/**
 * Store the history (contents of the sql drop-down) to
 * disk by serializing a vector 
 *
 */
private void storeHistory(){
  try {
    Vector history = new Vector();
    logger.info("Serializing the history list");
    for(int i = 0; i < jcmbSqlStatements.getItemCount(); i++){
      if(((String)jcmbSqlStatements.getItemAt(i)).length() > 0){
        history.add(jcmbSqlStatements.getItemAt(i));
      }
    }
    FileOutputStream fo = new FileOutputStream("sqlhistory.bin");
    ObjectOutputStream so = new ObjectOutputStream(fo);
    so.writeObject(history);
    so.flush();
    so.close();
  } catch (Exception e) {
    e.printStackTrace();

  }

} //}}}

//{{{ loadConnections()
/**
 *  Description of the Method
 */
private void loadConnections() {
  // populate the stored connections dropdown box
  cmbConnections.removeAllItems();
  try {

    Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections");
    String[] cons = dbpref.childrenNames();
    if(cons != null) {
      if(cons.length > 0) {
        for(int i = 0; i < cons.length; i++) {

          cmbConnections.addItem(cons[i]);
          System.out.println("Adding connection named " + cons[i]);
        }
      }
    }
  } catch(BackingStoreException bse) {
    System.out.println(bse.getMessage());
  }
}
//}}}

//{{{ createResultTable()
public void createResultTable(Vector rows, Vector cols){
  GsTable qResult = new GsTable(rows, cols);
  qResult.setFitState(false);
  btnFit.setSelected(false);
  fitToWidth = false;

  GsScrollPane sp = new GsScrollPane(qResult);
  sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  sp.setSqlRowCount(rowCount);

  String tabTitle = "";
  if(queryAction == REFRESH_RESULTS){
    GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
    tabTitle = currentTable.getSqlStatement();
  }else{
    tabTitle = currentSqlStatement;
  }
  sp.setSqlStatement(tabTitle);

  jtabResults.addTab(tabTitle, sp);
  jtabResults.setSelectedIndex(jtabResults.getTabCount() - 1);
  jtabResults.setToolTipTextAt(jtabResults.getTabCount() - 1, rowCount + " rows");

  qResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


} //}}}

//{{{ replaceResultTable()
public void replaceResultTable(Vector rows, Vector cols){
  JTable currentTable = getCurrentTable();

  GsScrollPane scrollpane = (GsScrollPane) jtabResults.getSelectedComponent();
  //scrollpane.remove(getCurrentTableIndex());
  currentTable = new GsTable(rows,cols);
  //scrollpane.add(currentTable);
  scrollpane.setSqlRowCount(rowCount);
  jtabResults.setToolTipTextAt(jtabResults.getSelectedIndex(), rowCount + " rows");
} //}}}

//{{{ lostOwnership()
public void lostOwnership(Clipboard clipboard,
    Transferable contents){
} //}}}

//{{{ private void pasteSql(){
private void pasteSql(){
  GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
  if(currentTable != null){
    String sql = currentTable.getSqlStatement();
    jtxtSql.setText(sql);
    jtxtSql.requestFocus();
  }
}
//}}}
private void copySql(){
  GsScrollPane currentTable = (GsScrollPane) jtabResults.getSelectedComponent();
  if(currentTable != null){
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

    String sql = currentTable.getSqlStatement();
    clip.setContents(new StringSelection(sql),this );
  }
}
//{{{ updateMemoryStats()
private void updateMemoryStats(){
  Runtime rt = Runtime.getRuntime();
  long free = rt.freeMemory();
  long total = rt.maxMemory();
  double dfree = free/Math.pow(2,20);
  double dtotal = total/Math.pow(2,20);
  statusBar.setText("" + (long)dfree + "/" + (long)dtotal + "Mb");
} //}}}

//{{{ setWaitCursor()
private void setWaitCursor(boolean wait){
  if(wait){
    Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
    setCursor(waitCursor);
    tableTree.setCursor(waitCursor);
    jtxtSql.setCursor(waitCursor);
  }else{
    Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    setCursor(defaultCursor);
    tableTree.setCursor(defaultCursor);
    jtxtSql.setCursor(defaultCursor);
  }
} //}}}
private String makeTableQualifier(GsTableSchemaCatalog tsc){
  // make a qualified name for the table using 
  // schema.table notation
  String tq = "";
  // make a qualified name for the table using 
  // schema.table notation
  if(tsc.getSchema() != null){
    tq = tsc.getSchema() + ".";
  }
  tq += tsc.getTable();
  return tq;
}
//{{{ private members

String url;
String driver;
String user;
String passwd;
Connection con;
String databaseUrl;
boolean isConnected = false;
boolean refreshResults = false;
int rowCount;
File currentSaveDirectory;
File currentSqlDirectory;
JWindow aboutWindow;
boolean fitToWidth = false;
static String version = "0.94";
String currentSqlStatement;

private String tableTitle;

// Variables declaration - do not modify//GEN-BEGIN:variables
private JToolBar jToolBar2;
private JMenuItem jmnuFileOpen;
private JSeparator jSeparator2;
private JTabbedPane jtabResults;
private JScrollPane jScrollPane1;
private JToolBar jToolBar3;
private JMenu jMenu2;
private JMenu jmnuTools;
private JMenuItem sqlTxtMenuPaste;
private JMenuItem tabCloseAll;
private JMenuItem sqlTxtMenuCopy;
private JSplitPane jSplitPane1;
private JSplitPane mainPane;
private JButton btnSave;
private JMenuItem jmnuHelpAbout;
private JToggleButton btnFit;
private JMenuItem tabProperties;
private JMenuItem tabClose;
private JPopupMenu jpopTabMenu;
private JButton btnOpen;
private JPopupMenu jpopSqlTxtMenu;
private JButton btnTables;
private JButton btnReloadTables;
private JComboBox cmbConnections;
private JToolBar jToolBar1;
private JButton btnNewConnection;
private JButton btnUseConnection;
private JSeparator jSeparator1;
private JButton btnNew;
private JMenuItem jmnuFileSaveAs;
private JComboBox jcmbSqlStatements;
private JToolBar jToolBar4;
private JMenuItem sqlTxtMenuCut;
private JButton btnQuery;
private JButton btnConnect;
private JButton btnDisconnect;
private JMenu jMenu1;
private JTextArea jtxtSql;
private JSeparator jSeparator3;
private JMenuItem jmnuFileExit;
private JMenuItem jmnuFileNew;
private JMenuBar jMenuBar1;
private JMenuItem tabSaveAs;
private JMenuItem tabSaveToClipboard;
private JMenuItem tabRefresh;
private JMenuItem tabHtmlReport;
private JMenuItem tabCopySql;
private JMenuItem tabPasteSql;
private JMenuItem toolsHistoryClear;
private JMenuItem toolsHistoryEdit;
private JLabel statusBar;
private GsSchemaTree tableTree;
private int queryAction;
public static final int REFRESH_RESULTS = 0, VIEW_REPORT = 1, CREATE_RESULTS = 2;
private JMenuItem menuItem;
//}}}
// action classes
public class SqlPasteAction extends AbstractAction {
  public SqlPasteAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_V | KeyEvent.CTRL_MASK ));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Paste SQL statement into editor");

  }
  public void actionPerformed(ActionEvent e) { 
    pasteSql();
  }
}
public class SqlExecuteAgainAction extends AbstractAction {
  public SqlExecuteAgainAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_E | KeyEvent.CTRL_MASK ));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Execute query again");

  }
  public void actionPerformed(ActionEvent e) { 
    queryAction = REFRESH_RESULTS;
    doQuery();
  }
}

public class SqlExecuteAction extends AbstractAction {
  public SqlExecuteAction(String text, ImageIcon  icon) {
    super(text, icon);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F4));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Execute Query");

  }
  public void actionPerformed(ActionEvent e) { 
    queryAction = CREATE_RESULTS;
    doQuery();
  }
}
public class CloseResultAction extends AbstractAction {
  public CloseResultAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F4 | KeyEvent.CTRL_MASK));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Close result table");

  }
  public void actionPerformed(ActionEvent e) { 

    jtabResults.removeTabAt(jtabResults.getSelectedIndex());
  }
}
public class CloseResultAllAction extends AbstractAction {
  public CloseResultAllAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F4 | KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Close all result tables");

  }
  public void actionPerformed(ActionEvent e) { 

    int sel = 0;
    while((sel = jtabResults.getSelectedIndex()) > -1) {
      jtabResults.removeTabAt(sel);
    }
  }
}
public class CopySqlAction extends AbstractAction {
  public CopySqlAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_C | KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Copy SQL to clipboard");

  }
  public void actionPerformed(ActionEvent e) { 
    copySql();
  }

}
public class SaveAsDelimitedAction extends AbstractAction {
  public SaveAsDelimitedAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S | KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "Save as delimited text");

  }
  public void actionPerformed(ActionEvent e) { 
    saveResults();
  }

}
public class CopyToClipboardAction extends AbstractAction {
	  public CopyToClipboardAction(String text) {
	    super(text);
	    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

	    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_K | KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));//| KeyEvent.SHIFT_MASK));
	    putValue(SHORT_DESCRIPTION, "Copy result table to the clipboard as delimited text");

	  }
	  public void actionPerformed(ActionEvent e) { 
	    saveToClipboard();
	  }

	}

public class ViewReportAction extends AbstractAction {
  public ViewReportAction(String text) {
    super(text);
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

    putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R | KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));//| KeyEvent.SHIFT_MASK));
    putValue(SHORT_DESCRIPTION, "View report");

  }
  public void actionPerformed(ActionEvent e) { 
    htmlReportFromResultTable();
  }

}
}

