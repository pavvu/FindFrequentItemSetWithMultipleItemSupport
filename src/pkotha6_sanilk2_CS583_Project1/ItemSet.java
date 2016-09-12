package pkotha6_sanilk2_CS583_Project1;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ItemSet {
    private TreeSet<Integer> itemsSet;
    private int count;

    public ItemSet() {
        itemsSet = new TreeSet<>();
    }

    public TreeSet<Integer> getItemsSet() {
        return itemsSet;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void add(int item) {
        itemsSet.add(item);
    }

    @Override
    public String toString() {
        if(this.itemsSet == null) {
            return "[]";
        }
        return this.itemsSet.toString();
    }
}