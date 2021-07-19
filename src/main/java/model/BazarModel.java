package model;


import java.util.ArrayList;

public class BazarModel {
    public static ArrayList<BazarModel> all = new ArrayList<>();
    public String cardName;
    public String seller;
    public String bestCustomer;
    public int bestPrice;

    public BazarModel(String cardName, String seller, int firstPrice) {
        this.cardName = cardName;
        this.seller = seller;
        bestPrice = firstPrice;
        bestCustomer = seller;
        UserModel.getUserByUsername(seller).removeCardFromUserAllCards(cardName);
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
        if (!bestCustomer.equals(seller)) {
            UserModel.getUserByUsername(bestCustomer).changeUserCoin(bestPrice);
        }
        bestCustomer = customer;
        bestPrice = price;
        UserModel.getUserByUsername(bestCustomer).changeUserCoin(bestPrice * -1);
    }


}
