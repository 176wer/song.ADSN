package control;

 

/**
 * Created by Administrator on 2015/9/17.
 */
//字节转换为16进制数
public class StringToHex {
    public static String[] Hex(byte[] b) {
        String[] str=new String[19];

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;

            }
                //  System.out.println(hex.toUpperCase());

           str[i]=hex.toUpperCase();


        }


        return  str;
    }

	 
}
