package control;

public class PublicContent {
private static int freshtime=60000;//����ˢ��ʱ��
public static int getfreshtime(){
	return freshtime;
}
public static void setFreshTime(int time){
	freshtime=time;
}
}
