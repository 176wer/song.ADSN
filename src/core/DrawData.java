package core;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Created by zgs on 2016/3/29.
 */
public class DrawData extends JPanel  {

    //保存曲线1
    private ArrayList<Long> time = new ArrayList<Long>();
    private ArrayList<Integer> val = new ArrayList<Integer>();
    //保存曲线2
    private ArrayList<Long> time2 = new ArrayList<Long>();
    private ArrayList<Integer> val2 = new ArrayList<Integer>();
    private Image image;
    private Color LineColor = new Color(151, 151, 151);
    ImageIcon icon = new ImageIcon("image/a.jpg");
    DateFormat fmt = DateFormat.getDateTimeInstance();
    private boolean flag = false;

    public DrawData() {
        // String path=DrawData.class.getResource("/image/a.jpg").getFile();
        // System.out.println(path);


        //image=Toolkit.getDefaultToolkit().getImage("/image/a.jpg");
    }

    public void addData(long t, int v, long t2, int v2) {
        time.add(t);
        val.add(v);
        time2.add(t);
        val2.add(v);

        if (time.size() > 500) {

            time.remove(0);
            val.remove(0);
            time2.remove(0);
            val2.remove(0);
        }
        repaint();
    }

    // Graph the sensor values in the dataPanel JPanel
    public void paintComponent(Graphics g0) {

        super.paintComponent(g0);

        Graphics2D g = (Graphics2D) g0;
        // image=icon.getImage();
        //g.drawImage(image,0,0,600,900,this);
        //  System.out.println(image.getWidth(this));
        //   System.out.println("ddddddd");
        int x0 = this.getX() ;       // get size of pane
        int y0 = this.getHeight() ;

        int xn = this.getWidth() - 20;
        int yn = this.getX() + 20;
        //虚线点坐标
        int yy = this.getHeight() / 4;
        int xx = this.getHeight() - yy;

        g.setColor(new Color(255, 204, 102));
        // draw flat A

        for (int j = x0; j < xx; j++) {
            g.drawLine(j, this.getHeight() - j, j, 0);
        }
        //画横线网格
        g.setColor(LineColor);
        int wy0 = y0 - 40;
        for (int w = wy0; w > 0; w = w - 40) {
            g.drawLine(x0, w, xx, yy - y0 + w);
        }
        //画竖线网格
        for (int z = 0; z < xx; z = z + 40) {
            g.drawLine(z, y0 - z + x0, z, 0);
        }

        //画 底部平面
        g.setColor(new Color(247, 197, 104));
        for (int h = x0; h < xn; h++) {
            g.drawLine(h, y0, xx + h - x0, yy);

        }
//        // 画竖线网格A
        g.setColor(LineColor);
        for (int h = x0; h < xn; h = h + 40) {
            g.drawLine(h, y0, xx + h - x0, yy);

        }
        double vscale = (yn - y0) / 12.0;      // light values range from 0 to 800
        double tscale = 450.0 / 2000.0;           // 1 pixel = 2 seconds = 2000 milliseconds
        g.setColor(Color.black);
        // draw X axis = time

        drawAL(x0, y0, x0, yn, g);
        //draw Y axis=value
        drawAL(x0, y0, xn, y0, g);

        //draw Z axis
        Stroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL, 0,
                new float[]{16, 4}, 0);
        g.setStroke(bs);
        drawAL(x0, y0, xx, yy, g);

        // graph sensor values
        int xp = -1;
        int vp = -1;

        int xp1 = -1;
        int vp1 = -1;
        for (int i = 0; i < time.size(); i++) {

            int x = x0 + (int) ((time.get(i) - time.get(0)) * tscale) + 20;
            int v = y0 + (int) (val.get(i) * vscale) - 40;


            int x1 = x + 150;
            int v1 = y0 + (int) (val.get(i) * vscale) - 240;
            if (xp > 0) {
                g.drawLine(xp, vp, x, v);//draw Line A
                g.drawLine(xp1, vp1, x1, v1);//draw Line B
            }

            xp = x;
            vp = v;
            xp1 = x1;
            vp1 = v1;

        }


        if (flag) {
            for (int i = 0; i < time.size(); i = i + 100) {

                int x = x0 + (int) ((time.get(i) - time.get(0)) * tscale) + 20;
                int v = y0 + (int) (val.get(i) * vscale) - 40;


                int x1 = x + 150;
                int v1 = y0 + (int) (val.get(i) * vscale) - 240;
                System.out.println("执行了");
                Stroke bs2 = new BasicStroke(1, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_BEVEL, 0,
                        new float[]{16, 4}, 0);
                g.setStroke(bs2);

                g.drawLine(x, v, x1, v1);

            }

        }


    }

    public static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2) {

        double H = 10; // 箭头高度
        double L = 4; // 底边的一半
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        double awrad = Math.atan(L / H); // 箭头角度
        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
        double y_3 = ey - arrXY_1[1];
        double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
        double y_4 = ey - arrXY_2[1];

        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        // 画线
        g2.drawLine(sx, sy, ex, ey);
        //
        GeneralPath triangle = new GeneralPath();
        triangle.moveTo(ex, ey);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.closePath();
        //实心箭头
        g2.fill(triangle);
        //非实心箭头
        //g2.draw(triangle);

    }

    public static double[] rotateVec(int px, int py, double ang,
                                     boolean isChLen, double newLen) {

        double mathstr[] = new double[2];
        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }

    public void Duo() {
        flag = true;
        repaint();
    }


}

