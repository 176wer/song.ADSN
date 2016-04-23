package test;/**
 * Created by zgs on 2016/4/18.
 */

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(¿ÉÑ¡). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
class CustomCellEditor extends DefaultCellEditor {
    protected NodeData nodeData;

    public CustomCellEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        nodeData = (NodeData) node.getUserObject();
        JTextField editor = (JTextField) super.getComponent();
        editor.setText(nodeData.getQuestion());
        return editor;

    }


    public Object getCellEditorValue() {
        JTextField editor = (JTextField) (super.getComponent());
        nodeData.setValue(editor.getText());
        return nodeData;
    }
}