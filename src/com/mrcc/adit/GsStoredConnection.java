/*
* GsStoredConnection.java 0.1 12/20/2002
*
* Copyright:
*   This program is free software; you can redistribute it and/or modify  
*   it under the terms of the GNU General Public License as published by  
*   the Free Software Foundation; either version 2 of the License, or     
*   (at your option) any later version.
*/

package com.mrcc.adit;
import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;
/**
 *
 * @author  gsherman 
 */
public class GsStoredConnection extends javax.swing.JDialog {
    
    /** Creates new form GsStoredConnection */
    public GsStoredConnection(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // populate the stored connection combo

        // get all connections
        try{
	
            Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections");
            String [] cons = dbpref.childrenNames();
            if(cons.length > 0){
                for(int i = 0; i < cons.length; i++){
		    
                    jcmbStoredConnections.addItem(cons[i]);
                    System.out.println("Adding connection named " + cons[i]);
                }
            }
            
        }catch(BackingStoreException bse){
            System.out.println(bse.getMessage());
        }
        loadParameters();
        setSize(500,getHeight());
        setLocationRelativeTo(parent);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jcmbStoredConnections = new javax.swing.JComboBox();
        jlblDriver = new javax.swing.JLabel();
        jtxtDriver = new javax.swing.JTextField();
        jlblUrl = new javax.swing.JLabel();
        jtxtUrl = new javax.swing.JTextField();
        jlblUsername = new javax.swing.JLabel();
        jtxtUsername = new javax.swing.JTextField();
        jlblPassword = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JPasswordField();
        jbtnSave = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jchkRequirePassword = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jbtnDelete = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());
        getRootPane().setDefaultButton(jbtnSave);

        setTitle("Manage Stored Connections");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel1.setText("Select a connection to edit or fill in fields to create a new one");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 2);
        getContentPane().add(jLabel1, gridBagConstraints);

        jcmbStoredConnections.setEditable(true);
        jcmbStoredConnections.setFont(new java.awt.Font("Dialog", 0, 11));
        jcmbStoredConnections.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcmbStoredConnectionsItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 2);
        getContentPane().add(jcmbStoredConnections, gridBagConstraints);

        jlblDriver.setFont(new java.awt.Font("Dialog", 0, 11));
        jlblDriver.setText("JDBC Driver");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        getContentPane().add(jlblDriver, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        getContentPane().add(jtxtDriver, gridBagConstraints);

        jlblUrl.setFont(new java.awt.Font("Dialog", 0, 11));
        jlblUrl.setText("JDBC Url");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        getContentPane().add(jlblUrl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        getContentPane().add(jtxtUrl, gridBagConstraints);

        jlblUsername.setFont(new java.awt.Font("Dialog", 0, 11));
        jlblUsername.setText("User name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        getContentPane().add(jlblUsername, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        getContentPane().add(jtxtUsername, gridBagConstraints);

        jlblPassword.setFont(new java.awt.Font("Dialog", 0, 11));
        jlblPassword.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        getContentPane().add(jlblPassword, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 2);
        getContentPane().add(jtxtPassword, gridBagConstraints);

        jbtnSave.setFont(new java.awt.Font("Dialog", 0, 11));
        jbtnSave.setText("Save");
        jbtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbtnSaveMouseReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 4);
        getContentPane().add(jbtnSave, gridBagConstraints);

        jbtnCancel.setFont(new java.awt.Font("Dialog", 0, 11));
        jbtnCancel.setText("Cancel");
        jbtnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbtnCancelMouseReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 4);
        getContentPane().add(jbtnCancel, gridBagConstraints);

        jchkRequirePassword.setFont(new java.awt.Font("Dialog", 0, 11));
        jchkRequirePassword.setText("Require password");
        jchkRequirePassword.setToolTipText("Check this box if you want to be prompted for the password rather than saving it with the connection information");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        getContentPane().add(jchkRequirePassword, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 11));
        jLabel2.setText("Connection name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        getContentPane().add(jLabel2, gridBagConstraints);

        jbtnDelete.setFont(new java.awt.Font("Dialog", 0, 11));
        jbtnDelete.setText("Delete");
        jbtnDelete.setToolTipText("Delete the selected connection");
        jbtnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbtnDeleteMouseReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 4);
        getContentPane().add(jbtnDelete, gridBagConstraints);

        pack();
    }//GEN-END:initComponents

    private void jbtnDeleteMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnDeleteMouseReleased
        // Add your handling code here:
         Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections/" + (String)jcmbStoredConnections.getSelectedItem());
         try{
            dbpref.removeNode();
            jcmbStoredConnections.removeItemAt(jcmbStoredConnections.getSelectedIndex());
         }catch(BackingStoreException bse){
            System.out.println(bse.getMessage());
        }
    }//GEN-LAST:event_jbtnDeleteMouseReleased

    private void jcmbStoredConnectionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcmbStoredConnectionsItemStateChanged
        // Add your handling code here:
        loadParameters();
    }//GEN-LAST:event_jcmbStoredConnectionsItemStateChanged

    private void jbtnCancelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnCancelMouseReleased
        // Add your handling code here:
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jbtnCancelMouseReleased

    private void jbtnSaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseReleased
        // Add your handling code here:
        saveConnection();
    }//GEN-LAST:event_jbtnSaveMouseReleased
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new GsStoredConnection(new javax.swing.JFrame(), true).show();
    }
    
    private void saveConnection() {
        // save the connection
        Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections/" + (String)jcmbStoredConnections.getSelectedItem());
        String conName = (String)jcmbStoredConnections.getSelectedItem();
        dbpref.put("driver", jtxtDriver.getText());
        dbpref.put("url", jtxtUrl.getText());
        dbpref.put("username", jtxtUsername.getText());
        dbpref.put("password", new String(jtxtPassword.getPassword()));
        dbpref.putBoolean("requirepassword", jchkRequirePassword.isSelected());
        setVisible(false);
        dispose();
        
    }    
    private void loadParameters(){
        // load the parameters for the currently selected connection
        String conName = (String)jcmbStoredConnections.getSelectedItem();
	if(conName != null){
        Preferences parentNode = Preferences.userRoot().node("mrcc/adit/connections");
	if(parentNode != null){
        try{
        if(parentNode.nodeExists(conName)){
         Preferences dbpref = Preferences.userRoot().node("mrcc/adit/connections/" + conName);
         
         jtxtDriver.setText(dbpref.get("driver",""));
         jtxtUrl.setText(dbpref.get("url",""));
         jtxtUsername.setText(dbpref.get("username",""));
         jtxtPassword.setText(dbpref.get("password",""));
         jchkRequirePassword.setSelected(dbpref.getBoolean("requirepassword",false));
        }
        }catch(BackingStoreException bse){
            System.out.println(bse.getMessage());
        }
	}
    }
    }
    public void setSelectedConnection(String selCon){
    	jcmbStoredConnections.setSelectedItem(selCon);
    }
    public String getSelectedConnection(){
    	return (String)jcmbStoredConnections.getSelectedItem();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlblPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox jchkRequirePassword;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jlblDriver;
    private javax.swing.JComboBox jcmbStoredConnections;
    private javax.swing.JLabel jlblUrl;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JTextField jtxtUsername;
    private javax.swing.JPasswordField jtxtPassword;
    private javax.swing.JTextField jtxtDriver;
    private javax.swing.JTextField jtxtUrl;
    private javax.swing.JLabel jlblUsername;
    // End of variables declaration//GEN-END:variables
    
}
