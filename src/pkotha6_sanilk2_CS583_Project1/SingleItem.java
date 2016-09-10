package pkotha6_sanilk2_CS583_Project1;

public class SingleItem {
    private int itemID;
    private double MIS;
    private int count;
    private double support;
    private double confidence;
    private int check;

    public SingleItem(int itemID, double MIS, int count, double support, double confidence) {
        this.itemID = itemID;
        this.MIS = MIS;
        this.count = count;
        this.support = support;
        this.confidence = confidence;
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

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
