package main.java.controller;
import main.java.model.*;
import main.java.view.ShopView;
import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static void run() {
        String command;
        while (true) {
            command = ShopView.getCommand();
            if (findMatcher(command) == 1) {
                continue;
            }
            break;
        }
    }

    public static int findMatcher(String command) {
        command = command.trim();
        Pattern pattern = Pattern.compile("^shop\\s* buy\\s* (.+?)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            shopBuy(matcher.group(1));
            return 1;
        }
        Pattern pattern1 = Pattern.compile("^shop \\s*show\\s* --all$");
        Matcher matcher1 = pattern1.matcher(command);
        if (matcher1.find()) {
            shopShow();
            return 1;
        }
        Pattern pattern2 = Pattern.compile("^menu \\s*enter \\s*(\\w*)$");
        Matcher matcher2 = pattern2.matcher(command);
        if (matcher2.find()) {
            String menuName = matcher2.group(1);
            if (menuName.equals("Main Menu"))
                return 0;
            else if (menuName.equals("Deck") || menuName.equals("Profile") || menuName.equals("Scoreboard") || menuName.equals("Duel") || menuName.equals("Import/Export"))
                ShopView.showInput("menu navigation is not possible");
            else
                ShopView.showInput("invalid command");
            return 1;
        }
        Pattern pattern3 = Pattern.compile("^menu \\s*show-current$");
        Matcher matcher3 = pattern3.matcher(command);
        if (matcher3.find()) {
            ShopView.showInput("shop");
            return 1;
        }
        Pattern pattern4 = Pattern.compile("^menu \\s*exit$");
        Matcher matcher4 = pattern4.matcher(command);
        if (matcher4.find()) {
            return 0;
        }
        ShopView.showInput("invalid command");
        return 1;
    }

    public static void shopShow() {
        TreeMap<String, Integer> sortedMap = new TreeMap<>(ShopModel.getCardInfo());
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
            ShopView.showInput(entry.getKey() + " : " + entry.getValue());
    }

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