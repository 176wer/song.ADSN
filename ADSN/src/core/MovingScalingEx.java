/**
 * Project Name:ADSN
 * File Name:MovingScalingEx.java
 * Package Name:core
 * Date:2016年3月3日上午8:45:17
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package core;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * ClassName:MovingScalingEx <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月3日 上午8:45:17 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@SuppressWarnings("serial")
public class MovingScalingEx extends JFrame {

    public MovingScalingEx() {

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());


    	DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode(new ZEllipse("0000"));

    	DefaultMutableTreeNode rootNode1=new DefaultMutableTreeNode(new ZEllipse("5CA7"));
    	rootNode.add(rootNode1);
//        DefaultMutableTreeNode rootNode2=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode2);
//        DefaultMutableTreeNode rootNode3=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode3);
//        DefaultMutableTreeNode rootNode4=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode4);
//        DefaultMutableTreeNode rootNode5=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode5);
//        DefaultMutableTreeNode rootNode6=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode6);
//
//
//        DefaultMutableTreeNode rootNode7=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode7);
//        DefaultMutableTreeNode rootNode8=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode8);
//        DefaultMutableTreeNode rootNode9=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode9);
//        DefaultMutableTreeNode rootNode10=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode10);
//        DefaultMutableTreeNode rootNode11=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode11);
//        DefaultMutableTreeNode rootNode12=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode.add(rootNode12);
//
//        DefaultMutableTreeNode rootNode13=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode13);
//        DefaultMutableTreeNode rootNode14=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode14);
//        DefaultMutableTreeNode rootNode15=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode15);
//        DefaultMutableTreeNode rootNode16=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode16);
//        DefaultMutableTreeNode rootNode17=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode17);
//        DefaultMutableTreeNode rootNode18=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode18);
//
//        DefaultMutableTreeNode rootNode19=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode13);
//        DefaultMutableTreeNode  rootNode20=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode14);
//        DefaultMutableTreeNode rootNode21=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode6.add(rootNode15);
//        DefaultMutableTreeNode rootNode22=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode16);
//        DefaultMutableTreeNode rootNode23=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode17);
//        DefaultMutableTreeNode rootNode24=new DefaultMutableTreeNode(new ZEllipse("第二个圆"));
//        rootNode7.add(rootNode18);

       // Surface surface = new Surface(rootNode);
      //  add(surface, BorderLayout.CENTER);


      //  add(new Surface(rootNode));
        
        setTitle("Moving and scaling");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                MovingScalingEx ex = new MovingScalingEx();
                ex.setVisible(true);
            }
        });
    }
}
