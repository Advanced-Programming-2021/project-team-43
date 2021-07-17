package controller;
import model.*;
import view.RegisterAndLoginView;
import view.ShopView;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static String shopBuy(String cardName) {
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        if (cardPrice == -1)
            return "There is no card with this name";
        try {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            RegisterAndLoginView.dataOutputStream.writeUTF("S " + MainMenuController.token + "shop buy " + cardName);
            RegisterAndLoginView.dataOutputStream.flush();
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                user.changeUserCoin(-1 * cardPrice);
                user.addCardToUserAllCards(cardName);
           }

            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}