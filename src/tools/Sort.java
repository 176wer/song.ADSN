package tools;
/**
 * Created by zgs on 2016/4/12.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Function: ��ԭʼ��16�������ݽ������ݷ��� <br/>
 * Reason:  ADD REASON(��ѡ). <br/>
 * date:
 *
 * @author
 * @since JDK 1.8
 */
public class Sort  extends  Thread{
    private Connection conn;
    private int i=0;
    public Sort(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/adsn";
            conn= DriverManager.getConnection(url,"root","123");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void run(){
        while(true){
            String sql="select * from raw limit "+i+",1000 ";

        }
    }
}
