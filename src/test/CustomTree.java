package test;/**
 * Created by zgs on 2016/4/18.
 */

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(¿ÉÑ¡). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
public class CustomTree extends JTree {
    public CustomTree(TreeModel model){
        super(model);
    }
    public boolean isPathEditable(TreePath path) {
        Object comp = path.getLastPathComponent();
        if (comp instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) comp;
            Object userObject = node.getUserObject();
            if (userObject instanceof NodeData) {
                return true;
            }
        }
        return false;
    }
}
