package test;/**
 * Created by zgs on 2016/4/17.
 */

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 2016/4/17 16:45 <br/>
 *
 * @author 赵广松
 * @since JDK 1.8
 */
public class TreeLibPanel extends JFrame implements TreeModelListener,ActionListener{
    private CustomTree tree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    public TreeLibPanel() {

        setLayout(new BorderLayout());
        setSize(new Dimension(500, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



        rootNode = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(new NodeData("node1"));
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(new NodeData("node2"));
        rootNode.add(node1);
        rootNode.add(node2);
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(this);
        tree = new CustomTree(treeModel);

        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("添加节点");
        item.setActionCommand("add");
        item.addActionListener(this);

        JMenuItem item2 = new JMenuItem("删除节点");
        menu.add(item);
        item2.setActionCommand("remove");

        menu.add(item2);


        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    TreePath path = tree.getPathForLocation ( e.getX (), e.getY () );
                    Rectangle pathBounds = tree.getUI ().getPathBounds ( tree, path );
                    if ( pathBounds != null && pathBounds.contains ( e.getX (), e.getY () ) )
                    {
                        menu.show ( tree, pathBounds.x, pathBounds.y + pathBounds.height );
                    }
                }
            }
        });




        add(tree, BorderLayout.CENTER);
        tree.setCellRenderer(new CustomCellRender());
        tree.setCellEditor(new CustomCellEditor());
        tree.setEditable(true);

    }








    //remove the currently selected node
    public void removeCurrentNode(){
        TreePath currentSelection=tree.getSelectionPath();
        if(currentSelection!=null){
            DefaultMutableTreeNode currentNode=(DefaultMutableTreeNode)(currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
            if(parent!=null){

            }
        }
    }

    /**
     *
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addObject(Object child){
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                    (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true);

    }

    /**
     *
     * @param parent
     * @param child
     * @return
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

    /**
     *
     * @param parent
     * @param child
     * @param shouldBeVisible
     * @return
     */
    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child,
                                            boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode =
                new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent,
                parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }


    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

        int index = e.getChildIndices()[0];
        node = (DefaultMutableTreeNode)(node.getChildAt(index));
        JOptionPane.showMessageDialog(this,"你确认值编辑为:"+node.getUserObject());
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {

    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {

    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath currentSelection=tree.getSelectionPath();
        if(currentSelection!=null){
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
            NodeData data = (NodeData) currentNode.getUserObject();
            System.out.println(data);
        }
               if(e.getActionCommand().equals("add")){

               }else if(e.getActionCommand().equals("remove")){

               }

    }


    public static void main(String[] args) {
        new TreeLibPanel();
    }
}
