package test;

/**
 * Created by zgs on 2016/4/11.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestPath {
    public static void main(String[] agrs){
        String f=TestPath.class.getResource("/config/sqlhistory.bin").getFile();
        try {
            FileInputStream fi = new FileInputStream(new File(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
