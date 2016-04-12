package control;

public class Param {
    //刷新时间
    public static int time=2;
   //监控的温度
    public static int temp=40;
    /**
	 * @return the temp
	 */
	public static int getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public static void setTemp(int temp) {
		Param.temp = temp;
	}

	public static int getTime() {
        return time;
    }

    public static void setTime(int time) {
        Param.time = time;
    }
}