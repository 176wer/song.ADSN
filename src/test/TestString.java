package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestString {
	
public static void main(String[] agrs){
	String a="A";
	System.out.println(a.hashCode()%4);
    int[] b=new int[10000000];
    try {
        Thread.sleep(500000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    for (int i=0;i<100000000;i++){
        b[i]=3;
    }


}
}
