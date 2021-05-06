package main.java.controller;
import main.java.model.*;
import main.java.view.*;
import java.util.*;
import java.util.regex.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ShopController {

    public static String onlineUser = MainMenuController.username;

    public static void findMatcher() {
        String command;
        while (true) {
            command = ShopView.getCommand();
            if (getMatcher(command,"shop\\s+buy\\s+(\\w+)").find()) {
                shopBuy(getMatcher(command,"shop\\s+buy\\s+(\\w+)").group(1));
                continue;
            }
            if (getMatcher(command,"shop\\s+show\\s+--all").find()) {
                shopShow();
                continue;
            }
            if (getMatcher(command,"menu\\s+enter\\s+(\\w+)").find()) {
                String menuName = getMatcher(command,"menu\\s+enter\\s+(\\w+)").group(1);
                if (menuName.equals("Main Menu"))
                    MainMenuController.findMatcher();
                else if (menuName.equals("Deck") || menuName.equals("Profile") || menuName.equals("Scoreboard") || menuName.equals("Duel") || menuName.equals("Import/Export"))
                    ShopView.showInput("Menu navigation is not possible");
                else
                    ShopView.showInput("invalid command");
                continue;
            }
            if (getMatcher(command,"menu\\s+show-current").find()) {
                ShopView.showInput("shop");
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
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
            ShopView.showInput(entry.getKey() + " : " + entry.getValue());
    }

    private static void shopBuy(String cardName) {
        Integer cardPrice = ShopModel.getCardPriceByName(cardName);
        if (cardPrice == null) {
            ShopView.showInput("There is no card with this name");
            return;
        }
        UserModel user = UserModel.getUserByUsername(onlineUser);
        if (user.getUserCoin() < cardPrice)
            ShopView.showInput("Not enough money");
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
    }

}
