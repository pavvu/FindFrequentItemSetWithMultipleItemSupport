package pkotha6_sanilk2_CS583_Project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetFrequentItemSets {
    
    public static void readInput() {
	try {
	    BufferedReader br = new BufferedReader(new FileReader("input-data.txt"));
	    String currentLine = "";
	    while ((currentLine = br.readLine()) != null) {
		
		System.out.println(currentLine);
		currentLine = currentLine.replace("{", "");
		currentLine = currentLine.replace("}", "");
		currentLine = currentLine.replace(",", "");
		
		for (String str : currentLine.split(" ")) {
		    //System.out.println(Integer.parseInt(str));
		    
		}
		
	   }
	    
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args) {
	readInput();
    }

}
