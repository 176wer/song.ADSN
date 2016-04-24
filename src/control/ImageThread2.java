package control;/**
 * Created by zgs on 2016/4/24.
 */

import java.io.File;

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(¿ÉÑ¡). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
public class ImageThread2 {
    public ImageThread2(){
        String filepath = ImageThread2.class.getResource("/gui").getFile();
        File file = new File(filepath);
       String a= file.getAbsolutePath();
        System.out.println(a);
    }

    public static void main(String[] args) {
        new ImageThread2();
    }
}
