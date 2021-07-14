package controller;

import model.Card;
import model.ShopModel;
import model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ShopController {

    public static String run(String string) {
        return findMatcher(string);
    }

    public static String findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^S (.+?)shop buy (.+?)$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            return shopBuy(matcher.group(2),matcher.group(1));

        }
        return null;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }


    public static String shopBuy(String cardName,String token) {
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (!Card.getCardsByName(cardName).isAvailable){
            return ("card is not available");
        }
        if (user.getUserCoin() < cardPrice) {
            return ("not enough money");
        }
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
        return ("your shopping was successful!");
    }

}