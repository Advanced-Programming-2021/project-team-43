package controller;
import model.*;
import view.ShopView;
import java.util.*;
import java.util.regex.*;


public class ShopController {

    public  static String run(String string) {
        return findMatcher(string);
    }

    public static String findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^S (.+?)shop buy (.+?)$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            return shopBuy(matcher.group(2),matcher.group(1));
        }

        if ((matcher = getMatcher(command, "^S c (.+?) m (.+)$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            return taghirMojoodi(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        if ((matcher = getMatcher(command, "^S c (.+?) setAvailable$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            return setAvailable(matcher.group(1));
        }
        if ((matcher = getMatcher(command, "^S c (.+?) setUnAvailable$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            return setAvailable(matcher.group(1));
        }
        return null;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static String taghirMojoodi(String cardName ,int mojoodi)  {
        Card.getCardsByName(cardName).mojoodi=mojoodi;
        return "chang is successful";
    }
    public static String setAvailable(String cardName){
        Card.getCardsByName(cardName).isAvailable=true;
        return "set available is successful";
    }
    public static String setUnAvailable(String cardName){
        Card.getCardsByName(cardName).isAvailable=true;
        return "set unavailable is successful";
    }

    public static String shopBuy(String cardName,String token) {
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if(Card.getCardsByName(cardName).mojoodi==0){
            return ("card is sold out ");
        }
        if (!Card.getCardsByName(cardName).isAvailable){
            return ("card is not available");
        }
        if (user.getUserCoin() < cardPrice) {
            return ("not enough money");
        }
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
        Card.getCardsByName(cardName).mojoodi=Card.getCardsByName(cardName).mojoodi-1;
        return ("your shopping was successful!");
    }


}