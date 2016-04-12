package control;

/**
 * Created by Administrator on 2015/11/28.
 */
/*
* 把字符数组转换为String
* */
public class ArrayToString {
    public static String ToString(String[] stres){
        String ToStr="";
        for(int i=0;i<stres.length;i++){
          ToStr=ToStr+stres[i];
        }
        return ToStr;
    }
}
