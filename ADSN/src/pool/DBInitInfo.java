package pool;

 
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * ��ʼ����ģ��������е������ļ�
 * @author Ran
 *
 */
public class DBInitInfo {
    public  static List<DBbean>  beans = null;



    static{
        beans = new ArrayList<DBbean>();
        // �������� ���Դ�xml �������ļ����л�ȡ
        // Ϊ�˲��ԣ�������ֱ��д��
        DBbean beanOracle = new DBbean();
        beanOracle.setDriverName("com.mysql.jdbc.Driver");
        beanOracle.setUrl("jdbc:mysql://127.0.0.1:3306/adsn");
        beanOracle.setUserName("root");
        beanOracle.setPassword("123");

        beanOracle.setMinConnections(5);
        beanOracle.setMaxConnections(500);

        beanOracle.setPoolName("mysql");
        beans.add(beanOracle);
    }

}
