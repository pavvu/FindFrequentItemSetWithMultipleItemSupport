package pkotha6_sanilk2_CS583_Project1;

import java.util.HashSet;
import java.util.Set;

public class ItemSet {
    private Set<Integer> itemsSet;
    private int count;

    public ItemSet() {
        itemsSet = new HashSet<>();
    }

    public Set<Integer> getItemsSet() {
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
}
