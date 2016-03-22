package control;

 
 

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

 
public class NodeLog  {
   //定义日子
    private Logger log=null;

    
    //日期格式化
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    //日志文件夹名称
    private static final String LOG_FOLDER_NAME = "Log/NodeLog";

    private static final String LOG_FILE_SUFFIX = ".log";
    public NodeLog(){
        ConfLog();
    }
    

    //获取或创建日志路径
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
     //配置日资
    public void  ConfLog(  ){
         log = Logger.getLogger("Nodelog");
        log.setLevel(Level.ALL);
        FileHandler fileHandler = null;
        try {
            //定义日志为追加
            fileHandler = new FileHandler(getLogFilePath(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new LogFormatter());
        log.addHandler(fileHandler);
    }
    //自定义日志格式
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
