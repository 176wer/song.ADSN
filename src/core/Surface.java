/**
 * Project Name:ADSN
 * File Name:Surface.java
 * Package Name:core
 * Date:2016年3月3日上午8:43:29
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * ClassName:Surface <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月3日 上午8:43:29 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@SuppressWarnings("serial")
public class Surface extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

    private ZEllipse zell;
    private ArrayList<ZEllipse> zEllipses = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private int x;
    private int y;
    private String toolTip="";
     
    private AffineTransform transform = new AffineTransform();
    private float zoom=0.3f;//字体放大倍数
    




    public Surface() {
     
    	 setToolTipText("");
    	  addMouseListener(this);
          addMouseWheelListener(this);
          addMouseMotionListener(this);
       
    }
   
    


      
      

           


    
    private void initLocation(DefaultMutableTreeNode rootNode){
        LinkedList<DefaultMutableTreeNode> linkedList = new LinkedList<DefaultMutableTreeNode>();

        linkedList.add(rootNode);
        ZEllipse n0 = (ZEllipse) rootNode.getUserObject();
        n0.setCoordinate(400, 50, 40, 40);

        ArrayList<ZEllipse> zelList=new ArrayList<ZEllipse>();
        zEllipses.add(n0);

        while (linkedList.isEmpty() == false) {
            DefaultMutableTreeNode parentNode = linkedList.remove();
            ZEllipse zel1 = (ZEllipse) parentNode.getUserObject();

            int px = (int) zel1.getX();
            int py = (int) zel1.getY();

            Enumeration num = parentNode.children();
            int Counts = parentNode.getChildCount();
            while (num.hasMoreElements()) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) num.nextElement();
                linkedList.add(childNode);
                ZEllipse zel2=(ZEllipse)childNode.getUserObject();
                zelList.add(zel2);

            }
            //垂直线左边应画的圆的个数

            int rCounts = Counts / 2;


            for(int i=0;i<rCounts;i++) {
                ZEllipse zel2=zelList.remove(0);
                zel2.setCoordinate(px - i * 100, py + 70, 40, 40);
                zEllipses.add(zel2);
                Edge edge = new Edge(zel1, zel2);
                edges.add(edge);
            }
            //垂直线画右边圆的个数
            int lCounts = Counts - rCounts;

            for(int i=1;i<=lCounts;i++) {

                ZEllipse zel3=zelList.remove(0);
                zel3.setCoordinate(px+i * 100, py + 70, 40, 40);

                zEllipses.add(zel3);
                Edge edge = new Edge(zel1, zel3);
                edges.add(edge);
            }




        }
    }

    private void doDrawing(Graphics g) {
       
    
        Graphics2D g2d = (Graphics2D) g;

        Font font = new Font("Serif", Font.BOLD, 40);
        g2d.setFont(font);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setPaint(new Color(0, 0, 200));

        g2d.setPaint(new Color(0, 200, 0));
        try{
            for (ZEllipse z : zEllipses) {
                if(z.getStatus()==3){
                    g2d.setPaint(new Color(194, 178, 98, 254));
                } else if (z.getStatus() == 2) {
                    g2d.setPaint(new Color(77, 81, 198));
                }else {
                    g2d.setPaint(new Color(177, 37, 47));
                }
                g2d.fill(z);
                g2d.setPaint(new Color(0,0,0));
                Font font1=g2d.getFont();
                if (zoom > 0) {
                    transform.setToScale(zoom,zoom);
                    font1=font1.deriveFont(transform);
                    g2d.setFont(font1);
                }else{
                    transform.setToScale(0.1,0.1);
                    font1=font1.deriveFont(transform);
                    g2d.setFont(font1);
                }

                g2d.drawString(z.getAddr(),(int)(z.getX()+z.getWidth()/3),(int)(z.getY()+z.getHeight()/2));

            }
            g2d.setPaint(new Color(0, 0, 0));

            Line2D lin;
            for (Edge edge : edges) {

                lin = new Line2D.Float(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY());
                g2d.draw(lin);
            }
        }catch (ConcurrentModificationException e){

            ;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        doScale(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        doMove(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int xM = e.getX();
        int yM = e.getY();
        //true为显示JToolTip
        boolean flag=true;
        for (ZEllipse z : zEllipses) {
            if (z.isHit(xM, yM)) {

               toolTip=z.infor();
                flag=false;
                break;
            }
        }
        if(flag){
            toolTip="";
        }

    }

    private void doScale(MouseWheelEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            //flag is true 代表焦点落在园内，flag ia false 代表落在圆外
            boolean flag = false;
            for (ZEllipse z : zEllipses) {
                if (z.isHit(x, y)) {

                    float amount = e.getWheelRotation() * 5f;
                    zoom = zoom + amount/200;
                    z.addWidth(amount);
                    z.addHeight(amount);
                    repaint();
                    flag = true;
                    break;
                }

            }
            if (!flag) {
                for (ZEllipse z : zEllipses) {

                    float amount = e.getWheelRotation() * 5f;
                    zoom = zoom + amount/200;
                    z.addWidth(amount);
                    z.addHeight(amount);
                    repaint();


                }
            }

        }
    }

    private void doMove(MouseEvent e) {

        int dx = e.getX() - x;
        int dy = e.getY() - y;
        //flag is true代表焦点落在园内，is false 落在圆外
        boolean flag = false;
        for (ZEllipse z : zEllipses) {
            if (z.isHit(x, y)) {

                z.addX(dx);
                z.addY(dy);
                repaint();
                flag = true;
                break;
            }
        }
        if (!flag) {
            for (ZEllipse z : zEllipses) {


                z.addX(dx);
                z.addY(dy);
                repaint();

            }
        }


        x += dx;
        y += dy;
    }

    public Point getToolTipLocation(MouseEvent e)
    {
        Point p = e.getPoint();
        p.y += 15;
        return p;
//      return super.getToolTipLocation(e);
    }

    public String getToolTipText(MouseEvent e)
    {
      return toolTip;
    }
     
    public void refresh(DefaultMutableTreeNode rootNode){
        zoom = 0.3f;
        zEllipses.clear();
        edges.clear();
    	initLocation(rootNode);
    	repaint();
    }
}

