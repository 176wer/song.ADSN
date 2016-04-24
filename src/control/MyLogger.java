package control;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import lib.Lib;
public class MyLogger extends Thread {
   //��������
    private Logger log=null;

    //��ȡ��ʼʱ��
    long startTime=System.currentTimeMillis();
    private Lib lib=new Lib();
    //���ڸ�ʽ��
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    //��־�ļ�������
    private static final String LOG_FOLDER_NAME = "Log";

    private static final String LOG_FILE_SUFFIX = ".log";
    public MyLogger(){
        ConfLog();
    }
    public void run(){
      while(true){
          try {
              Thread.sleep(10000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          long endTime=System.currentTimeMillis()-startTime; //��ȡϵͳ����ʱ��
          log.info("ϵͳ�����У� "+endTime+"ms    �Ѽ�¼: "+lib.getCount());
      }
    }

    //��ȡ�򴴽���־·��
    private synchronized  String getLogFilePath() {
        StringBuffer logFilePath = new StringBuffer();
          
        String logPath1= MyLogger.class.getResource("/Log/NodeLog").getPath();;
        String logPath= new File(logPath1).getAbsolutePath();


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
         log = Logger.getLogger("ADSNlog");
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

}