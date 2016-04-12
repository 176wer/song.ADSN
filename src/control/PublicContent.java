package control;

public class PublicContent {
private static int freshtime=60000;//定义刷新时间
public static int getfreshtime(){
	return freshtime;
}
public static void setFreshTime(int time){
	freshtime=time;
}
}
