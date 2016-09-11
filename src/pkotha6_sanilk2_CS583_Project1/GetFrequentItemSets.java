package pkotha6_sanilk2_CS583_Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class GetFrequentItemSets {
    public  static TreeSet<SingleItem> singleItemSet = new TreeSet<>(new MISComparator());

    public static void readInput() throws IOException {
        BufferedReader  br = new BufferedReader(new FileReader("input-data.txt"));
        String currentLine = "";

        // building transactions add singleItems
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
            singleItemSet.add(currSingleItem);
        }

        // printing the built datastructure
        for(SingleItem s : singleItemSet) {
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
                itemMisMap.put(itemId, itemMis);	
            }
            else {
                break;
            }
        }
        br.close();
        return itemMisMap;
    }


    public static void generateL() {
        if(singleItemSet == null) {
            return;
        }

        SingleItem firstItem = null;
        SingleItem curItem = null; 
        for(Iterator<SingleItem> i = singleItemSet.iterator(); i.hasNext();) {
            curItem = i.next();
            if(firstItem == null) {
                if(curItem.getSupport() >= curItem.getMIS()) {
                    firstItem = curItem;
                }
                else {
                    singleItemSet.remove(curItem);
                }
            }
            else {
                if(curItem.getSupport() < firstItem.getMIS()) {
                    singleItemSet.remove(curItem);
                }
            }
        }
    }


    
    public static TreeSet<SingleItem> generateF1() {
	TreeSet<SingleItem> F1 = new TreeSet<SingleItem>(new );
	for(SingleItem item : SingleItemsSet) {
	    if(item.getSupport() > item.getMIS()) {
		F1.add(item);
	    }
	}
	
	for(SingleItem item : F1) {
	    System.out.println(item);
	}
	return F1;
    }
    
    public static void main(String[] args) throws IOException {
        readInput();
        System.out.println("generating F1");
        generateL();
        generateF1();
	TestSuites();
        
    }
    
    public static void TestSuites() throws IOException {
	ItemSet i = new ItemSet();
	i.add(20);
	i.add(30);
	i.add(40);
	System.out.println(Transactions.getItemSetCount(i)==0);
	//0 because it is not present.

	i = new ItemSet();
	i.add(20);
	i.add(30);
	i.add(50);

	System.out.println(Transactions.getItemSetCount(i)==2);
	//2 because it is present in first and last.
    }

}
