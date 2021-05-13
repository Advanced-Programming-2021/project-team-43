package main.java.controller;

import main.java.model.*;
import main.java.view.ShopView;

import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static String onlineUser = MainMenuController.username;

    public static void findMatcher() {
        String command;
        while (true) {
            command = ShopView.getCommand();
            command = command.trim();
            Pattern pattern = Pattern.compile("shop\\s* buy\\s* (\\w*)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                shopBuy(matcher.group(1));
                continue;
            }
            Pattern pattern1 = Pattern.compile("shop \\s*show\\s* --all");
            Matcher matcher1 = pattern1.matcher(command);
            if (matcher1.find()) {
                shopShow();
                continue;
            }
            Pattern pattern2 = Pattern.compile("menu \\s*enter \\s*(\\w*)");
            Matcher matcher2 = pattern2.matcher(command);
            if (matcher2.find()) {
                String menuName = matcher2.group(1);
                if (menuName.equals("Main Menu"))
                    MainMenuController.findMatcher();
                else if (menuName.equals("Deck") || menuName.equals("Profile") || menuName.equals("Scoreboard") || menuName.equals("Duel") || menuName.equals("Import/Export"))
                    ShopView.showInput("menu navigation is not possible");
                else
                    ShopView.showInput("invalid command");
                continue;
            }
            Pattern pattern3=Pattern.compile("menu \\s*show-current");
            Matcher matcher3=pattern3.matcher(command);
            if (matcher3.find()) {
                ShopView.showInput("shop");
                continue;
            }
            Pattern pattern4=Pattern.compile("menu \\s*exit");
            Matcher matcher4=pattern4.matcher(command);
            if (matcher4.find()) {
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
        if (cardPrice == 0) {
            ShopView.showInput("there is no card with this name");
            return;
        }
        UserModel user = UserModel.getUserByUsername(onlineUser);
        if (user.getUserCoin() < cardPrice)
            ShopView.showInput("not enough money");
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
        ShopView.showInput("your shopping was successful!");
    }

}