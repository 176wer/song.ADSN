package test;/**
 * Created by zgs on 2016/4/18.
 */

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
//对JTree自定义包含图形图标和文本的节点
class CustomCellRender extends DefaultTreeCellRenderer {
    protected JCheckBox checkBoxRenderer = new JCheckBox();
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            if (userObject instanceof NodeData) {
                NodeData question = (NodeData) userObject;
                prepareQuestionRenderer(question, selected);
                return checkBoxRenderer;
            }
        }
        return super.getTreeCellRendererComponent(tree, value, selected, expanded,
                leaf, row, hasFocus);
    }

    protected void prepareQuestionRenderer(NodeData tfq, boolean selected) {
        checkBoxRenderer.setText(tfq.getQuestion());
        checkBoxRenderer.setSelected(tfq.getAnswer());
        if (selected) {
            checkBoxRenderer.setForeground(Color.RED);
            checkBoxRenderer.setBackground(getBackgroundSelectionColor());
        } else {
            checkBoxRenderer.setForeground(getTextNonSelectionColor());
            checkBoxRenderer.setBackground(getBackgroundNonSelectionColor());
        }
    }
}
