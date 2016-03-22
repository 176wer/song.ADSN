package control;

 
 

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

 
public class NodeLog  {
   //��������
    private Logger log=null;

    
    //���ڸ�ʽ��
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    //��־�ļ�������
    private static final String LOG_FOLDER_NAME = "Log/NodeLog";

    private static final String LOG_FILE_SUFFIX = ".log";
    public NodeLog(){
        ConfLog();
    }
    

    //��ȡ�򴴽���־·��
    private synchronized  String getLogFilePath() {
        StringBuffer logFilePath = new StringBuffer();
          
        String logPath= NodeLog.class.getResource("/").getPath();;
        System.out.println(logPath);
        logFilePath.append(logPath);
        logFilePath.append(File.separatorChar);
        logFilePath.append(LOG_FOLDER_NAME);

        File file = new File(logFilePath.toString());
        if (!file.exists())
            file.mkdir();

        logFilePath.append(File.separatorChar);
        logFilePath.append(sdf.format(new Date()));
        logFilePath.append(LOG_FILE_SUFFIX);

        return logFilePath.toString();
    }
     //��������
    public void  ConfLog(  ){
         log = Logger.getLogger("Nodelog");
        log.setLevel(Level.ALL);
        FileHandler fileHandler = null;
        try {
            //������־Ϊ׷��
            fileHandler = new FileHandler(getLogFilePath(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new LogFormatter());
        log.addHandler(fileHandler);
    }
    //�Զ�����־��ʽ
    class LogFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            Date date = new Date();
            String sDate = date.toString();
            return "[" + sDate + "]" + record.getMessage() + "\r\n";
        }

    }
    public void addLog(String msg){
    	log.info(msg);
    }
}
