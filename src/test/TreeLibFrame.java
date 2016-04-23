package test;/**
 * Created by zgs on 2016/4/18.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(¿ÉÑ¡). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
public class TreeLibFrame extends JFrame {
    public TreeLibFrame(){
        setVisible(true);
        setSize(new Dimension(600,700));
        setLayout(new BorderLayout());
        add(new TreeLibPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        TreeLibFrame f = new TreeLibFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
