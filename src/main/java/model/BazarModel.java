package model;

import java.util.ArrayList;
import java.util.Date;

public class BazarModel {

    public static ArrayList<BazarModel> all = new ArrayList<>();
    public String cardName;
    public String seller;
    public String bestCustomer;
    public int bestPrice;
    public Date date = new Date();

    public BazarModel(String cardName, String seller, int firstPrice) {
        this.cardName=cardName;
        this.seller=seller;
        bestPrice=firstPrice;
        bestCustomer=seller;
    }

}
