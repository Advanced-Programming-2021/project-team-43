package model;


import java.io.Serializable;
import java.util.ArrayList;

public class BazarModel implements Serializable {
    public String cardName;
    public String seller;
    public String bestCustomer;
    public int bestPrice;
    public int bazarCode;
    public int firstPrice;
    public BazarModel(String cardName, String seller, int firstPrice) {
        this.cardName = cardName;
        this.seller = seller;
        bestPrice = firstPrice;
        bestCustomer = seller;
        bazarCode = UserModel.bazarCounter;
        UserModel.bazarCounter++;
        this.firstPrice=firstPrice;
        UserModel.getUserByUsername(seller).removeCardFromUserAllCards(cardName);
        UserModel.all.add(this);
    }

    public void end() {
        if (bestCustomer.equals(seller)) {
            UserModel.getUserByUsername(seller).addCardToUserAllCards(cardName);
            return;
        }
        UserModel.getUserByUsername(bestCustomer).addCardToUserAllCards(cardName);
        UserModel.getUserByUsername(seller).changeUserCoin(bestPrice);
    }

    public void changeCustomer(String customer, int price) {
        if (firstPrice!=bestPrice) {
            UserModel.getUserByUsername(bestCustomer).changeUserCoin(bestPrice);
        }
        bestCustomer = customer;
        bestPrice = price;
        UserModel.getUserByUsername(bestCustomer).changeUserCoin(bestPrice * -1);
        
    }


}
