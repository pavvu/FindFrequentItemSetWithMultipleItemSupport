package pkotha6_sanilk2_CS583_Project1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Transactions {
    public static List<HashSet<Integer>> transactions;

    public Transactions(List<HashSet<Integer>> transactions) {
        transactions = new ArrayList<HashSet<Integer>>(transactions);
    }

    public static int getItemSetCount(ItemSet items) {
        if(items == null) {
            return 0;
        }

        int count = 0;
        for(Set<Integer> t : transactions) {
            if(t.containsAll(items.getItemsSet())) {
                count++;
            }
        }
        return count;
    }
}
