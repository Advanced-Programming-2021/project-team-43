package main.java.controller;
import main.java.model.*;



public class ShopController {

    public static String shopBuy(String cardName) {
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        if (cardPrice == -1)
            return "There is no card with this name";
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.getUserCoin() < cardPrice)
            return "Not enough money";
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
        return "Card added successfully!";
    }

}