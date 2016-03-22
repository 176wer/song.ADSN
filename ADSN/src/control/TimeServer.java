package control;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeServer {
public static String getTime(){
	Date date=new Date();
	 DateFormat cnDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
             DateFormat.FULL, Locale.CHINA);
    String  time = cnDate.format(date);
    return time;
}
}
