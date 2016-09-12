package pkotha6_sanilk2_CS583_Project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GetFrequentItemSets {
    public static Set<SingleItem> singleItemSet = new TreeSet<>(new MISComparator());
    public static Set<SingleItem> F1 = new TreeSet<>(new IDComparator());
    public static List<ItemSet> Fk = new LinkedList<>();
    public static int noOfTransactions = 0;
    public static Map<Integer, SingleItem> singleItemMap = new HashMap<>();

    public static void readInput() throws IOException {
        BufferedReader  br = new BufferedReader(new FileReader("input-data.txt"));
        String currentLine = "";

        // building transactions add singleItems
        List<HashSet<Integer>> transactions = new ArrayList<HashSet<Integer>>();
        Map<Integer, Integer> singleItemCount = new TreeMap<>();
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
            if(!singleItemMap.containsKey(itemId)) {
                singleItemMap.put(itemId, currSingleItem);
            }
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

        for (SingleItem item : singleItemSet) {
            System.out.println(item);
        }
    }

    public static void generateF1() {
        for(SingleItem item : singleItemSet) {
            if(item.getSupport() > item.getMIS()) {
                F1.add(item);
            }
        }
        for(SingleItem item : F1) {
            System.out.println(item);
        }
    }

    public static void AprioriAlgorithm() {
        int k = 2;
        List<ItemSet> Ck = new ArrayList<>();
        int[] curItems = new int[]{};
        while(k == 2 || Fk != null) {
            if(k == 2) {
                generateLevel2Candidate(0.1);
            }
            else {
                //generateMSCandidate();
            }

            Fk = new LinkedList<>();
            for(ItemSet i : Ck) {
                i.setCount(Transactions.getItemSetCount(i));
            }

            for(ItemSet i : Ck) {
                curItems = new int[i.getItemsSet().size()];
                int index = 0;
                for(int item : i.getItemsSet()) {
                    curItems[index++] = item;
                }

                if(i.getCount() >= singleItemMap.get(curItems[0]).getMIS()) {
                    Fk.add(i);
                }
            }
        }
        //Union;
        return;
    }

    public static void generateLevel2Candidate(double p) {
        ArrayList<ItemSet> c2 = new ArrayList<>();
        ArrayList<SingleItem> listL = new ArrayList<SingleItem>(singleItemSet);
        for(int index =0 ; index < listL.size(); index++) {
            SingleItem currItem = listL.get(index);
            if(currItem.getSupport() >= currItem.getMIS()){
                for(int h = index+1; h < listL.size(); h++) {
                    SingleItem secondItem = listL.get(h);
                    if ( (secondItem.getSupport() >= secondItem.getMIS()) &&
                            (Math.abs(currItem.getSupport() - secondItem.getSupport())<=p)
                            ) {
                        ItemSet currItemSet = new ItemSet();
                        currItemSet.add(currItem.getItemID());
                        currItemSet.add(secondItem.getItemID());
                        c2.add(currItemSet);
                    }
                }
            }
        }

        for(ItemSet itemSet : c2) {

            System.out.println(itemSet.getItemsSet());

        }

    }

    public static void main(String[] args) throws IOException {
        readInput();  
        System.out.println("prininting L");
        generateL();
        System.out.println("generating F1");
        generateF1();
        System.out.println("prining c2");
        generateLevel2Candidate(0.1);
        System.out.println("Implementing Algorithm");
        AprioriAlgorithm();
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
