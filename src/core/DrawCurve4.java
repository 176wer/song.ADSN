package core;/**
 * Created by zgs on 2016/4/23.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date:  2016/4/23 <br/>
 *
 * @author 20:52
 * @since JDK 1.8
 */
public class DrawCurve4 extends JPanel {
    private DrawCurve3 temp;
    private DrawCurve3 humdity;
    private DrawCurve3 light;
    private DrawCurve3 vibration;



    public DrawCurve4(){
        setLayout(new GridLayout(2,2));
        temp = new DrawCurve3("温度");
        humdity = new DrawCurve3("湿度");
        light = new DrawCurve3("光强");
        vibration = new DrawCurve3("振动");
        add(temp);
        add(humdity);
        add(light);
        add(vibration);

    }

    /**
     *
     * @return
     */
    public DrawCurve3 getTemp(){
        return temp;
    }
    public DrawCurve3 getHumdity(){
        return humdity;
    }
    public DrawCurve3 getLight(){
        return  light;

    }
    public DrawCurve3 getVibration(){
        return vibration;
    }
}
