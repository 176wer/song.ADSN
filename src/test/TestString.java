package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TestString {
	
public static void main(String[] agrs){
	
	try {
		BufferedReader reader=new BufferedReader(new FileReader("D:\\1.java"));
		int i=0;
		String s=null;
		while((s=reader.readLine())!=null){
			System.out.println(s+"    "+i);
			i++;
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
