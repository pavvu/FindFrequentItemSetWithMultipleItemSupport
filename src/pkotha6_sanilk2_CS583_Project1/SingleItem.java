package pkotha6_sanilk2_CS583_Project1;

import java.util.Comparator;

class MISComparator implements Comparator<SingleItem> {
    @Override
    public int compare(SingleItem o1, SingleItem o2) {
        return (o1.getMIS() > o2.getMIS() ? 1 : (o1.getMIS() < o2.getMIS() ? -1 : 
            ((o1.getItemID() > o2.getItemID() ? 1 : (o1.getItemID() < o2.getItemID() ? -1 : 0)))) );
    }
}

class IDComparator implements Comparator<SingleItem> {
    @Override
    public int compare(SingleItem o1, SingleItem o2) {
        return (o1.getItemID() > o2.getItemID() ? 1 : (o1.getItemID() < o2.getItemID() ? -1 : 0));
    }
}

public class SingleItem {
    private int itemID;
    private double MIS;
    private int count;
    private double support;

    public SingleItem() {
    }

    public SingleItem(int itemID, double MIS, int count, double support, double confidence) {
        this.itemID = itemID;
        this.MIS = MIS;
        this.count = count;
        this.support = support;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public double getMIS() {
        return MIS;
    }

    public void setMIS(double mIS) {
        MIS = mIS;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport(double support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return new String("item = " + this.itemID + ", count = " + this.count + ", MIS = " + this.MIS + ", support = " + this.support);
    }
}
