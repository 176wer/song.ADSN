package control;

/**
 * Created by Administrator on 2015/11/5.
 */

import core.DrawCurve;
import core.DrawCurve1;
import core.DrawCurve4;
import core.Surface;
import org.jfree.chart.JFreeChart;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ImageThread extends Thread
{
  //�߳�����ʱ��
    private int sleepTime;
    //����ͼ��
    private DrawCurve4 drawCurve4;
    //��ǰʱ��
    private String time;
    //�������
    private Surface topoPanel;
    //����ļ���·��
    private String  filePath;

    public ImageThread(int sleepTime, DrawCurve4 wenpane, Surface topoPanel){
        this.sleepTime=sleepTime;

        drawCurve4 = wenpane;
        this.topoPanel=topoPanel;
        filePath = ImageThread.class.getResource("/curveImage").getPath();
        System.out.println("filePath: " + filePath);
    }


    public void run(){
         while(true){
             try {
                 sleep(sleepTime);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println(drawCurve4);
             Dimension imageSize = drawCurve4.getSize();
             BufferedImage image = new BufferedImage(imageSize.width,
                     imageSize.height, BufferedImage.TYPE_INT_ARGB);
             Graphics2D g = image.createGraphics();
             drawCurve4.paint(g);
             g.dispose();
             try {
                 ImageIO.write(image, "png", new File(filePath));
             } catch (IOException e) {
                 e.printStackTrace();

             }
         }
    }
    //��Jfreechartͼ����Ϊͼ��
    public   void save( BufferedImage bi )
    {
        try
        {
           //����ͼƬ��ʽ����ȡwriter
            Iterator<?> iter = ImageIO.getImageWritersByFormatName( "JPG" );
            if( iter.hasNext() )
            {
                ImageWriter writer = (ImageWriter)iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
                iwp.setCompressionQuality( 0.95f );
                //�ļ���
                String destFilename= filePath+"image/wen/"+time+".jpg";
                MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( new FileOutputStream( destFilename ) );
                writer.setOutput( mos );
                IIOImage image = new IIOImage( bi, null, null);
                writer.write(null, image, iwp);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void constrain( String srcFilename, String destFilename, int boxSize )
    {
        try
        {
            FileInputStream fis = new FileInputStream( srcFilename );

            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( new FileOutputStream( destFilename ) );
            constrain( fis, mos, boxSize );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static byte[] constrain( String srcFilename, int boxSize )
    {
        try
        {
            FileInputStream fis = new FileInputStream( srcFilename );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( baos );
            constrain( fis, mos, boxSize );
            return baos.toByteArray();

        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static byte[] constrain( InputStream is, int boxSize )
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( baos );
            constrain( is, mos, boxSize );
            return baos.toByteArray();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static void constrain( String srcFilename, OutputStream os, int boxSize )
    {
        try
        {
            FileInputStream fis = new FileInputStream( srcFilename );
            MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( os );
            constrain( fis, mos, boxSize );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void constrain( InputStream is, ImageOutputStream os, int boxSize )
    {
        try
        {
            // ��ȡ sourcefile
            BufferedImage input = ImageIO.read( is );

            // �õ�ͼ���ԭʼ�ߴ�
            int srcHeight = input.getHeight();
            int srcWidth = input.getWidth();

            // Լ���ߴ�
            int height = boxSize;
            int width = boxSize;
            if( srcHeight > srcWidth )
            {
                width = ( int )( ( ( float )height / ( float )srcHeight ) * ( float )srcWidth );
            }
            else if( srcWidth > srcHeight )
            {
                height = ( int )( ( ( float )width / ( float )srcWidth ) * ( float )srcHeight );
            }

            // �����µ� thumbnail BufferedImage
            BufferedImage thumb = new BufferedImage( width, height, BufferedImage.TYPE_USHORT_565_RGB );
            Graphics g = thumb.getGraphics();
            g.drawImage( input, 0, 0, width, height, null );

            // ��ȡwriter�����ø�ʽ
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "JPG" );
            if( iter.hasNext() )
            {
                ImageWriter writer = (ImageWriter)iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );

                iwp.setCompressionQuality( 0.95f );
                writer.setOutput( os );
                IIOImage image = new IIOImage(thumb, null, null);
                writer.write(null, image, iwp);
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
    //������ͼ����ΪͼƬ
    private void saveImage(JPanel topoPanel) {
        BufferedImage img = new BufferedImage(topoPanel.getWidth(), topoPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        topoPanel.paint(img.getGraphics());
        try {
            ImageIO.write(img, "jpg", new File(filePath+"image/topo/"+time+".jpg"));
        } catch (Exception e) {
            System.out.println("panel not saved" + e.getMessage());
        }
    }
}
