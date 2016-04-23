package test;

/**
 * Created by zgs on 2016/4/11.
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestPath extends JFrame{

    public static void main(String[] agrs){
        String f=TestPath.class.getResource("/config/sqlhistory.bin").getFile();
       TestPath a= new TestPath();
        a.setSize(new Dimension(500,600));
        a.setVisible(false);
        try {
            FileInputStream fi = new FileInputStream(new File(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
