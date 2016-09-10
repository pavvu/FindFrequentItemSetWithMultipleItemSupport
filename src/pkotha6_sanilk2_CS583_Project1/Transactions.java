package pkotha6_sanilk2_CS583_Project1;

import java.util.ArrayList;
import java.util.List;

public class Transactions {
    private List<ItemSet> transaction;

    public Transactions(List<ItemSet> currentTransaction) {
        this.transaction = new ArrayList<ItemSet>(currentTransaction);
    }

}
