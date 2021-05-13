package controller;
import model.*;
import view.ShopView;
import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static String onlineUser = MainMenuController.username;

    public static void commandController() {
        Matcher matcher;
        String command;
        while (true) {
            command = ShopView.getCommand();
            if ((matcher = getMatcher(command,"shop\\s+buy\\s+(\\w+)")).find()) {
                shopBuy(matcher);
                continue;
            }
            if (getMatcher(command,"shop\\s+show\\s+--all").find()) {
                shopShow();
                continue;
            }
            if ((matcher = getMatcher(command,"menu\\s+enter\\s+(\\w+)")).find()) {
                String menuName = matcher.group(1);
                if (menuName.equals("Main")) {
                    MainMenuController.findMatcher();
                    break;
                }
                else if (menuName.equals("Shop") || menuName.equals("Deck") || menuName.equals("Profile") || menuName.equals("Scoreboard") || menuName.equals("Duel") || menuName.equals("Import/Export"))
                    ShopView.showInput("menu navigation is not possible");
                else
                    ShopView.showInput("invalid command");
                continue;
            }
            if (getMatcher(command,"menu\\s+show-current").find()) {
                ShopView.showInput("Shop");
                continue;
            }
            if (getMatcher(command,"menu\\s+exit").find()) {
                MainMenuController.findMatcher();
                break;
            }
            ShopView.showInput("invalid command");
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    private static void shopShow() {
        TreeMap<String, Integer> sortedMap = new TreeMap<>(ShopModel.getCardInfo());
        for (Map.Entry<String, Integer> eachCard : sortedMap.entrySet())
            ShopView.showInput(eachCard.getKey() + " : " + eachCard.getValue());
    }

    private static void shopBuy(Matcher matcher) {
        String cardName = matcher.group(1);
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        if (cardPrice == 0)
            ShopView.showInput("there is no card with this name");
        else {
            UserModel user = UserModel.getUserByUsername(onlineUser);
            if (user.getUserCoin() < cardPrice)
                ShopView.showInput("not enough money");
            else {
                user.changeUserCoin(-1 * cardPrice);
                user.addCardToUserAllCards(cardName);
            }
        }
    }
}

