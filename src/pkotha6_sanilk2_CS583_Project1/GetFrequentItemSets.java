package pkotha6_sanilk2_CS583_Project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GetFrequentItemSets {

    private static final List SingleItemSet = null;
    public  static TreeSet<SingleItem> SingleItemsSet = new TreeSet<>(new MISComparator());

    public static void readInput() throws IOException {
	BufferedReader  br = new BufferedReader(new FileReader("input-data.txt"));
	String currentLine = "";

	// building transactions adn singleItems
	List<HashSet<Integer>> transactions = new ArrayList<HashSet<Integer>>();
	Map<Integer, Integer> singleItemCount = new TreeMap<>();
	int noOfTransactions = 0;
	while ((currentLine = br.readLine()) != null) {
	    noOfTransactions++;
	    System.out.println(currentLine);
	    currentLine = currentLine.replace("{", "");
	    currentLine = currentLine.replace("}", "");
	    currentLine = currentLine.replace(",", "");

	    HashSet<Integer> currTransaction = new HashSet<>();
	    for (String itemID : currentLine.split(" ")) {
		//System.out.println(Integer.parseInt(str));
		int item = Integer.parseInt(itemID);
		if(singleItemCount.containsKey(item)) {
		    singleItemCount.put(item, singleItemCount.get(item)+1);
		}
		else {
		    singleItemCount.put(item, 1);
		}
		currTransaction.add(item);	    
	    }
	    transactions.add(currTransaction);

	}
	Transactions.transactions = transactions;
	buildListofSingleItems(singleItemCount, noOfTransactions);
	br.close();

    }

    public static void buildListofSingleItems(Map<Integer, Integer> singleItemCount, double noOfTransactions) throws IOException {
	Map<Integer, Double> itemMisMap = getMIS();
	int count = 0;
	SingleItem currSingleItem = null;
	for(Integer itemId : singleItemCount.keySet()) {
	    currSingleItem = new SingleItem();
	    count = singleItemCount.get(itemId);
	    currSingleItem.setCount(count);
	    currSingleItem.setItemID(itemId);
	    currSingleItem.setMIS(itemMisMap.get(itemId));
	    currSingleItem.setSupport(count/noOfTransactions);
	    SingleItemsSet.add(currSingleItem);
	}
	
	// printing the built datastrcture
	for(SingleItem s : SingleItemsSet) {
	    System.out.println(s);
	}
    }

    public static Map<Integer, Double> getMIS() throws IOException {
	BufferedReader br = new BufferedReader(new FileReader("parameter-file.txt"));
	String currLine = "";
	int itemId = 0;
	double itemMis = 0d;
	Map<Integer, Double> itemMisMap = new HashMap<>();
	while((currLine = br.readLine()) != null) {
	    if(currLine.contains("MIS")) {
		itemId = Integer.parseInt( currLine.substring(4, currLine.indexOf(')')));
		itemMis = Double.parseDouble(currLine.substring(currLine.indexOf('=')+2));
		//System.out.println(itemId + " " + itemMis);
		itemMisMap.put(itemId, itemMis);	
	    }
	    else {
		break;
	    }
	}
	br.close();
	return itemMisMap;
    }

    public static void main(String[] args) {
	//readInput();
	try {
	    readInput();
	    //getMIS();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
