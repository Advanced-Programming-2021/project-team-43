package controller;

import model.ShopModel;
import model.UserModel;
import view.ShopView;

import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static String onlineUser = MainMenuController.username;

    public static void findMatcher() {
        String command = ShopView.getCommand();

        while (true) {
            if (getMatcher(command,"shop\\s+buy\\s+(\\w+)").find()) {
                String cardName = getMatcher(command,"shop\\s+buy\\s+(\\w+)").group(1);
                if (ShopModel.getCardPriceByName(cardName) == 0)
                    ShopView.showInput("there is no card with this name");
                else if (UserModel.getUserByUsername(onlineUser).getUserCoin() < ShopModel.getCardPriceByName(cardName))
                    ShopView.showInput("not enough money");
                else
                    shopBuy(cardName);
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
                    ShopView.showInput("menu navigation is not possible");
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
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        UserModel.getUserByUsername(onlineUser).changeUserCoin(cardPrice * -1);
        UserModel.getUserByUsername(onlineUser).addCardToUserAllCards(cardName);
    }

}
