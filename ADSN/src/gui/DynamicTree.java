package gui;
import core.DrawCurve;
import core.Surface;
import core.ZEllipse;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashSet;
 
/**
 * Created by Administrator on 2015/9/19.
 */
public class DynamicTree extends JPanel implements TreeSelectionListener {
    protected DefaultMutableTreeNode rootNode;
    protected JTabbedPane pane;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private HashSet<String> set;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private ParameterPanel paramPane;
    private int nodeCounts=1;//对节点数进行计数
    private String SelecName;//选中节点名称
    private HashSet<String>  hashSet=new HashSet<String>();//在添加TabPan时避免重复
    private int i=1;//设置t添加TabPane的序号
    private Surface topo;
    public DynamicTree(ParameterPanel paramPane,Surface topo) {
        super(new GridLayout(1,0));
        this.paramPane=paramPane;
        this.topo=topo;
        initialize();


    }
    public void initialize(){

        set=new HashSet<String>();
        ZEllipse kaiBean=new ZEllipse("0000");
        rootNode = new DefaultMutableTreeNode(kaiBean);
        topo.refresh(rootNode);
        treeModel = new DefaultTreeModel(rootNode);
        //    treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(this);
        tree.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
   public DynamicTree(){
       super(new GridLayout(1,0));
       initialize();
   }

    /** Remove all nodes except the root node. */
    public synchronized void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
        topo.refresh(rootNode);
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                    (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        }

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }

    /** Add child to the currently selected node. */
    /*
    public DefaultMutableTreeNode addObject(Object child) {
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
    */

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, false);
    }

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
            //tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        topo.refresh(rootNode);
        nodeCounts++;
        return childNode;
    }



    public DefaultMutableTreeNode addObject(Object child){
    	ZEllipse b=(ZEllipse)child;
     String addr1=b.getAddr();
     boolean isTrue=set.add(addr1);

     if(!isTrue){
         for(String str:set)

         return  null;
     }


     Enumeration enumeration= rootNode.breadthFirstEnumeration();
     while (enumeration.hasMoreElements()) {
         DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) enumeration
                 .nextElement();
                 String addr=parentNode.getUserObject().toString();

               String  route=b.getRoute();


         if(addr.equals(route)){

             return addObject(parentNode, child, true);

         }

     }
       // return addObject(rootNode, child, true);
     set.remove(addr1);

     return rootNode;


 }

    
  
    

    class MyTreeModelListener implements TreeModelListener {
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

            System.out.println("The user has finished editing the node.");
            System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {

        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }
    }
     
    public void setTable(JTabbedPane pane){
        this.pane=pane;
    }
    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public DefaultMutableTreeNode getRootNode(){
        return rootNode;
    }
    public HashSet<String>   getSet(){
        return set;
    }
    public HashSet<String> getHashSet(){
        return  hashSet;
    }
    public String getSelecName(){
        return  SelecName;
    }
    public void setI(){
        i--;
    }
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		  DefaultMutableTreeNode selectNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		  ZEllipse NodeObject=null;
		     try{
		    	    NodeObject=(ZEllipse)selectNode.getUserObject();
		    	  paramPane.getLabel().setText(NodeObject.getTemp());
		    	  paramPane.getLabel_1().setText(NodeObject.getHumidity());
		    	  paramPane.getLabel_2().setText(NodeObject.getLight());
		    	  paramPane.getLabel_3().setText(NodeObject.getVibration());
		    	  paramPane.getLabel_4().setText(String.valueOf(NodeObject.getRssi()));
		    	  SelecName=NodeObject.getAddr();
		    	  DrawCurve wenpane=new DrawCurve();
			        PublicNode.setWenpane(wenpane);
			        PublicNode.setAddr(SelecName);
			        if(i>=2){
			        	 hashSet.remove(pane.getTitleAt(i));
			            pane.remove(i);  
			            i--;
			        }
			       
			        if(hashSet.add(SelecName)){
			           i++;
			            pane.add(SelecName, wenpane);
			            pane.setTabComponentAt(i,
			                    new ButtonTabComponent(pane,this));
			           

			        }   
		    	

		     }catch(Exception en){
		    	 System.out.println("树结构发生改变时，有程序试图外部修改jtree"+NodeObject);
		     }
	        
		    
		    	  
  
	       
	   
		
	}
	public void setNodeCounts(){
		nodeCounts=1;
	}
	public int getNodeCounts(){
		return nodeCounts;
	}
}
