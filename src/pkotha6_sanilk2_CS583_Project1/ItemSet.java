package pkotha6_sanilk2_CS583_Project1;

import java.util.LinkedHashSet;

public class ItemSet {
    private LinkedHashSet<Integer> itemsSet;
    private int count;

    public ItemSet() {
        itemsSet = new LinkedHashSet<>();
    }

    public LinkedHashSet<Integer> getItemsSet() {
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
        return this.itemsSet.toString().replace("[", "").replace("]", "");
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ItemSet) {
            return this.getItemsSet().containsAll(((ItemSet) o).getItemsSet());
        }
        return false;
    }

}