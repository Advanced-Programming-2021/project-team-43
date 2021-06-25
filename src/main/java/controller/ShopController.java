package controller;
import model.*;
import view.*;
import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static void run() {
        String command;
        do {
            command = ShopView.getCommand().trim();
        } while (findMatcher(command) != 5);
    }

    public static int findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^shop\\s+buy\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^s\\s+b\\s+(.+?)$")).find()) {
            shopBuy(matcher.group(1));
            return 1;
        }
        if (getMatcher(command, "^shop\\s+show\\s+--all$").find() || getMatcher(command, "^s\\s+s\\s+-a$").find()) {
            shopShow();
            return 2;
        }
        if ((matcher = getMatcher(command, "^menu\\s+enter\\s+(\\w*)$")).find() || (matcher = getMatcher(command, "^m\\s+en\\s+(.+?)$")).find()) {
            if (matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Scoreboard") || matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Shop"))
                ShopView.showInput("menu navigation is not possible");
            else
                ShopView.showInput("invalid command");
            return 3;
        }
        if (getMatcher(command, "^menu\\s+show-current$").find() || getMatcher(command, "^m\\s+s-c$").find()) {
            ShopView.showInput("Shop");
            return 4;
        }
        if (getMatcher(command, "^menu\\s+exit$").find() || getMatcher(command, "^m\\s+ex$").find())
            return 5;
        if ((matcher = getMatcher(command, "^increase\\s* --money\\s* (\\d+)$")).find() || (matcher = getMatcher(command, "^increase\\s* -m\\s* (\\d+)$")).find()) {
            MainMenuController.increaseMoney(Integer.parseInt(matcher.group(1)));
            return 6;
        }
        ShopView.showInput("invalid command");
        return 7;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void shopShow() {
        TreeMap<String, Integer> sortedMap = new TreeMap<>(ShopModel.getCardInfo());
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
            ShopView.showInput(entry.getKey() + " : " + entry.getValue());
    }

    public static void shopBuy(String cardName) {
        int cardPrice = ShopModel.getCardPriceByName(cardName);
        if (cardPrice == 0) {
            ShopView.showInput("there is no card with this name");
            return;
        }
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.getUserCoin() < cardPrice) {
            ShopView.showInput("not enough money");
            return;
        }
        user.changeUserCoin(-1 * cardPrice);
        user.addCardToUserAllCards(cardName);
        ShopView.showInput("your shopping was successful!");
    }

}