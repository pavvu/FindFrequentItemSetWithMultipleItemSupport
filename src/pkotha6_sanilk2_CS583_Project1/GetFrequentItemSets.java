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
    public static Set<SingleItem> M = new TreeSet<>(new MISComparator());
    public static Set<SingleItem> F1 = new TreeSet<>(new MISComparator());
    public static Set<SingleItem> L = new TreeSet<>(new MISComparator());
    public static List<ItemSet> Fk = new LinkedList<>();
    public static List<ItemSet> previousFk = new LinkedList<>();
    public static double noOfTransactions = 0d;
    public static double SDC = 0d;
    public static Map<Integer, SingleItem> singleItemMap = new HashMap<>();
    public static List<Integer> mustHaveList = new ArrayList<>();
    public static List<Integer> cannotHaveList = new ArrayList<>();
    public static List<ItemSet> cannotBeTogetherItemSets = new ArrayList<ItemSet>();
    public static boolean endOfCompare = false;
    //End of initialisations

    public static void readInput() throws IOException {
        BufferedReader  br = new BufferedReader(new FileReader("input-data.txt"));
        String currentLine = "";

        // building transactions and singleItems
        List<HashSet<Integer>> transactions = new ArrayList<HashSet<Integer>>();
        Map<Integer, Integer> singleItemCount = new TreeMap<>();
        while ((currentLine = br.readLine()) != null) {
            noOfTransactions++;
            //System.out.println(currentLine);
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
        Map<Integer, Double> itemMisMap = getParameters();
        int count = 0;
        SingleItem currSingleItem = null;
        for(Integer itemId : singleItemCount.keySet()) {
            currSingleItem = new SingleItem();
            count = singleItemCount.get(itemId);
            currSingleItem.setCount(count);
            currSingleItem.setItemID(itemId);
            if(itemMisMap.containsKey(itemId)) {
                currSingleItem.setMIS(itemMisMap.get(itemId));
            }
            else {
                System.out.println("The MIS for the item " + itemId + " is not defined!");
                throw new java.util.NoSuchElementException();
            }
            currSingleItem.setSupport(count/noOfTransactions);
            M.add(currSingleItem);
            if(!singleItemMap.containsKey(itemId)) {
                singleItemMap.put(itemId, currSingleItem);
            }
        }

        // printing the built data structure
        /*for(SingleItem s : M) {
            System.out.println(s);
        }*/
    }

    public static Map<Integer, Double> getParameters() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("parameter-file.txt"));
        String currLine = "";
        int tempInt = 0;
        double temp = 0d;
        String tempStr = "";
        String tempParts[];
        Map<Integer, Double> itemMisMap = new HashMap<>();
        while((currLine = br.readLine()) != null) {
            if(currLine.contains("MIS")) {
                tempInt = Integer.parseInt( currLine.substring(4, currLine.indexOf(')')));
                temp = Double.parseDouble(currLine.substring(currLine.indexOf('=') + 2));
                itemMisMap.put(tempInt, temp);	
            }
            else if(currLine.contains("SDC")){
                SDC = Double.parseDouble(currLine.substring(currLine.indexOf("=") + 2));
            }
            else if(currLine.contains("cannot")) {
                int index = currLine.indexOf(":") + 2;
                if(index < currLine.length()) {
                    tempStr = currLine.substring(currLine.indexOf(":") + 2);
                    tempStr = tempStr.replaceAll("[^0-9]", " ").trim();
                    tempParts = tempStr.split("\\s+");
                    int length = 0;
                    boolean numberCheck = false;
                    boolean nullCheck = false;
                    for(String s : tempParts) {
                        numberCheck = true;
                        nullCheck = true;
                        length = s.length();
                        for(int i = 0; i < length; ++i) {
                            nullCheck = false;
                            if(!Character.isDigit((s.charAt(i)))) {
                                numberCheck = false;
                                break;
                            }
                        }
                        if(numberCheck && !nullCheck) {
                            cannotHaveList.add(Integer.parseInt(s.trim()));
                        }
                    }
                }
            }
            else if(currLine.contains("must")) {
                int index = currLine.indexOf(":") + 2;
                if(index < currLine.length()) {
                    tempStr = currLine.substring(currLine.indexOf(":") + 2);
                    tempParts = tempStr.replaceAll("[^0-9]", " ").split("\\s+");
                    int length = 0;
                    boolean numberCheck = false;
                    boolean nullCheck = false;
                    for(String s : tempParts) {
                        numberCheck = true;
                        nullCheck = true;
                        length = s.length();
                        for(int i = 0; i < length; ++i) {
                            nullCheck = false;
                            if(!Character.isDigit((s.charAt(i)))) {
                                numberCheck = false;
                                break;
                            }
                        }
                        if(numberCheck && !nullCheck) {
                            mustHaveList.add(Integer.parseInt(s.trim()));
                        }
                    }
                }
            }
            else {
                break;
            }
        }
        br.close();
        return itemMisMap;
    }


    public static void initialPass() {
        if(M == null) {
            return;
        }

        SingleItem firstItem = null;
        SingleItem curItem = null; 
        for(Iterator<SingleItem> i = M.iterator(); i.hasNext();) {
            curItem = i.next();
            if(firstItem == null) {
                if(curItem.getSupport() >= curItem.getMIS()) {
                    firstItem = curItem;
                    L.add(curItem);
                }
                else {
                    //M.remove(curItem);
                }
            }
            else {
                if(curItem.getSupport() >= firstItem.getMIS()) {
                    L.add(curItem);
                }
            }
        }

        /*for (SingleItem item : L) {
            System.out.println(item);
        }*/
    }

    public static void generateF1() {
        if(L == null) {
            return;
        }
        for(SingleItem item : L) {
            if(item.getSupport() >= item.getMIS()) {
                F1.add(item);
            }
        }

        if(F1 != null && F1.size() > 0) {
            int count = 0;
            if (mustHaveList != null && mustHaveList.size() > 0) {
                System.out.println("Frequent 1-itemsets\n");
                for(SingleItem item : F1) {
                    if(mustHaveList.contains(item.getItemID())) {
                        count++;
                        System.out.println("\t" + item.getCount() + " : {" + item.getItemID() + "}");
                    }
                }
            }
            else {
                System.out.println("Frequent 1-itemsets\n");
                for(SingleItem item : F1) {
                    count++;
                    System.out.println("\t" + item.getCount() + " : {" + item.getItemID() + "}");
                }
            }
            System.out.println("Total number of frequent 1-itemsets = " + count);
        }
    }

    public static void AprioriAlgorithm() {
        int k = 2;
        List<ItemSet> Ck = new ArrayList<>();
        while(k == 2 || (previousFk != null && previousFk.size() > 0)) {
            if(k == 2) {
                Ck = generateLevel2Candidate(SDC);
            }
            else {
                Ck = generateMSCandidate(SDC);
            }

            Fk = new LinkedList<>();
            for(ItemSet i : Ck) {
                i.setCount(Transactions.getItemSetCount(i));

                List<Integer> curItemList = new ArrayList<>(i.getItemsSet());
                if(i.getCount()/noOfTransactions >= singleItemMap.get(curItemList.get(0)).getMIS()) {
                    Fk.add(i);
                }
            }
            k++;

            int count = 0;
            List<Integer> currItemsList = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            ItemSet currTailItemSet;
            //Updated condition during DEMO!
            //if((k-1) % 2 != 0) {
            for (ItemSet i : Fk) {
                if (!containCannotHave(i) && containMustHave(i)) {
                    currItemsList = new LinkedList<>(i.getItemsSet());
                    currItemsList.remove(0);
                    currTailItemSet = new ItemSet();
                    count++;
                    sb.append("\t" + i.getCount() + " : {" + i + "}\n");
                    for(int item : currItemsList) {
                        currTailItemSet.add(item);
                    }
                    sb.append("Tailcount = " + Transactions.getItemSetCount(currTailItemSet) + "\n");
                }
            }
            if(count > 0) {
                System.out.println("\nFrequent " + (k-1) + "-itemsets\n");
                System.out.println(sb.toString());
                System.out.println("Total number of frequent " +(k-1) + "-itemsets = " + count);
            }
            //}
            previousFk = Fk;
        }
        return;
    }

    public static List<ItemSet> generateLevel2Candidate(double p) {
        List<ItemSet> c2 = new ArrayList<>();
        List<SingleItem> listL = new ArrayList<SingleItem>(L);
        SingleItem firstItem = null;
        for(int index = 0; index < listL.size(); index++) {
            SingleItem currItem = listL.get(index);

            if(currItem.getSupport() >= currItem.getMIS()){
                firstItem = currItem;
                for(int h = index+1; h < listL.size(); h++) {
                    SingleItem secondItem = listL.get(h);

                    if ((secondItem.getSupport() >= firstItem.getMIS()) && 
                            (Math.abs(currItem.getSupport() - secondItem.getSupport())<=p)) {
                        ItemSet currItemSet = new ItemSet();
                        currItemSet.add(currItem.getItemID());
                        currItemSet.add(secondItem.getItemID());
                        c2.add(currItemSet);
                    }
                }
            }
        }

        /*System.out.println("printing c2");
        for(ItemSet itemSet : c2) {
            System.out.println(itemSet.getItemsSet());
        }*/
        return c2;
    }

    public static List<ItemSet> generateMSCandidate(double p) {
        List<ItemSet> Ck = new ArrayList<>();
        ItemSet currC;
        int bound = Fk.size();
        boolean addCandidateToCk = true;
        for(int i =0; i<bound-1; i++) {
            endOfCompare = false;
            for (int j=i+1; j<bound ; j++ ) {
                List<Integer> c = combinef1f2(Fk.get(i), Fk.get(j), p);
                if(c!= null) {
                    // checking if the k-1 size subsets are present in Fk-1 or not
                    addCandidateToCk = sPresentInK(c);
                    if(addCandidateToCk) {
                        currC = new ItemSet();
                        for(int cItem : c) {
                            currC.add(cItem);
                        }
                        Ck.add(currC);
                    }
                }
                if(endOfCompare) {
                    break;
                }
            }
        }
        return Ck;
    }


    public static boolean sPresentInK (List<Integer> c) {
        boolean present = true;
        boolean MIScondition = MISCheck(c);
        for (int index=0; index < c.size(); index++) {
            List<Integer> currSubSet = new ArrayList<>(c);
            currSubSet.remove(index);
            if(currSubSet.contains(c.get(0)) || MIScondition) {
                ItemSet currSubSetItemSet = new ItemSet();
                for(int currSubSetItem : currSubSet) {
                    currSubSetItemSet.add(currSubSetItem);
                }
                if(!Fk.contains(currSubSetItemSet)) {
                    present = false;
                    break;
                }
            }
            else {
                break;
            }
        }
        return present;
    }

    public static boolean MISCheck(List<Integer> c) {
        if(c == null || c.size() < 2) {
            return false;
        }
        return singleItemMap.get(c.get(0)).getMIS() == singleItemMap.get(c.get(1)).getMIS();
    }


    public static List<Integer> combinef1f2(ItemSet f1, ItemSet f2, double SDC) {
        if (f1 == null || f2 == null) return null;
        if(f1.getItemsSet().size() != f2.getItemsSet().size()) return null;

        int bound = f1.getItemsSet().size();

        List<Integer> f1List = new LinkedList<Integer> (f1.getItemsSet());
        List<Integer> f2List = new LinkedList<Integer> (f2.getItemsSet());

        int f1Last = f1List.remove(bound-1);
        int f2Last = f2List.remove(bound-1);

        if(!f1List.equals(f2List)) {
            endOfCompare = true;
            return null;
        }

        SingleItem f1LastItem = singleItemMap.get(f1Last);
        SingleItem f2LastItem = singleItemMap.get(f2Last);

        double f1LastSup = f1LastItem.getSupport();
        double f2LastSup = f2LastItem.getSupport();

        double f1LastMIS = f1LastItem.getMIS();
        double f2LastMIS = f2LastItem.getMIS();

        if(f2LastMIS >= f1LastMIS && Math.abs(f1LastSup - f2LastSup) <= SDC) {
            f1List.add(f1Last);
            f1List.add(f2Last);
            return f1List;
        }

        return null;
    }

    public static boolean containMustHave(ItemSet currItemSet) {
        if(mustHaveList == null || mustHaveList.size() == 0) {
            return true;
        }
        if(currItemSet == null || currItemSet.getItemsSet().size() == 0) {
            return false;
        }
        for (Integer item : mustHaveList) {
            if(currItemSet.getItemsSet().contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containCannotHave(ItemSet currItemSet) {
        if(cannotHaveList == null || cannotHaveList.size() == 0) {
            return false;
        }
        if(currItemSet == null || currItemSet.getItemsSet().size() == 0) {
            return false;
        }
        for (ItemSet currCannotBeTogetherItemSet : cannotBeTogetherItemSets) {
            if (currItemSet.getItemsSet().containsAll(currCannotBeTogetherItemSet.getItemsSet())) {
                return true;
            }
        }
        return false;
    }

    public static void populateCannotHaveList () {
        if (cannotHaveList == null || cannotHaveList.size() == 0 ) {
            System.out.println("ERROR! cannot-have-together-list is empty");
            return ; 
        }

        if (cannotHaveList.size() == 1) {
            return;
        }

        for (int i = 0; i < cannotHaveList.size(); i++) {
            Integer firstItem = cannotHaveList.get(i);
            for (int j = i+1; j < cannotHaveList.size(); j++) {
                Integer secondItem = cannotHaveList.get(j);
                ItemSet tempItemSet = new ItemSet();
                tempItemSet.add(firstItem);
                tempItemSet.add(secondItem);
                cannotBeTogetherItemSets.add(tempItemSet);		
            }
        }
    }

    public static void main(String[] args) throws IOException {
        readInput();  
        //System.out.println("generating L");
        populateCannotHaveList();
        initialPass();
        //System.out.println("generating F1");
        generateF1();
        //System.out.println("Implementing Algorithm");
        AprioriAlgorithm();
        TestSuites();
    }

    public static void TestSuites() throws IOException {
        ItemSet i = new ItemSet();
        i.add(20);
        i.add(30);
        i.add(50);
        //System.out.println(Transactions.getItemSetCount(i) == 2);
        //0 because it is not present.

        ItemSet j = new ItemSet();
        j.add(20);
        j.add(30);
        j.add(80);

        //System.out.println(Transactions.getItemSetCount(j) == 3);
        //2 because it is present in first and last.

        //System.out.println(combinef1f2(i, j, 0.1) == null);

        //System.out.println("combining items test case");
        i = new ItemSet();
        i.add(100);
        i.add(50);

        j = new ItemSet();
        j.add(140);
        j.add(50);
        combinef1f2(i,j,0.2);

        /*        populateCannotHaveList();
        for(ItemSet s : cannotBeTogetherItemSets) {
            System.out.println(s);
        }*/
    }
}
