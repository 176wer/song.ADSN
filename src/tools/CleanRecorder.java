package tools;/**
 * Created by zgs on 2016/4/16.
 */

/**
 * Function:  对一条数据进行数据清洗 <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 2016.4.16 16.41 <br/>
 *
 * @author Mr.zgs
 * @since JDK 1.8
 */
public class CleanRecorder {
    /**
     * 根据帧头和校检位判断数据是否有效
     * 这里以后用XML文档进行处理
     */
    private static String zhentou="FE";
    private static String check = "2C";
    public static String[] getDeal(String ag){
        String[] str = new String[15];
        char[] chars = ag.toCharArray();
        str[0]=String.valueOf(chars[0])+String.valueOf(chars[1]);
        str[14] = String.valueOf(chars[chars.length - 1]) + String.valueOf(chars[chars.length - 2]);
        if(str[0].equals(zhentou)&&str[1].equals(check)){
            str[1] = String.valueOf(chars[2]) + String.valueOf(chars[3]);
            str[2] = String.valueOf(chars[4]) + String.valueOf(chars[5]) + String.valueOf(chars[6]) + String.valueOf(chars[7]);
            str[3] = String.valueOf(chars[8]) + String.valueOf(chars[9]) + String.valueOf(chars[10]) + String.valueOf(chars[11]);
            str[4] = String.valueOf(chars[12]) + String.valueOf(chars[13]) + String.valueOf(chars[14]) + String.valueOf(chars[15]);
            str[5] = String.valueOf(chars[16]) + String.valueOf(chars[17]) + String.valueOf(chars[18]) + String.valueOf(chars[19]);
            str[6] = String.valueOf(chars[20]) + String.valueOf(chars[21]);
            str[7] = String.valueOf(chars[22]) + String.valueOf(chars[23]);
            str[8] = String.valueOf(chars[24]) + String.valueOf(chars[25]) + String.valueOf(chars[26]) + String.valueOf(chars[27]);
            str[9] = String.valueOf(chars[28]) + String.valueOf(chars[29]);
            str[10] = String.valueOf(chars[30]) + String.valueOf(chars[31]);
            str[11] = String.valueOf(chars[32]) + String.valueOf(chars[33]);
            str[12] = String.valueOf(chars[34]) + String.valueOf(chars[35]);
            str[13] = String.valueOf(chars[36]) + String.valueOf(chars[37]);
            return  str;

        }else{
            return  null;
        }

    }
}
