import java.util.*;
import java.util.regex.*;


public class ShopController {

    public static String onlineUser = MainMenuController.username;

    public static void findMatcher() {
        String shopBuy = "shop\\s+buy\\s+(\\w+)";
        String shopShow = "shop\\s+show\s+--all";
        String enterMenu = "menu\\s+enter\\s+(\\w+)";
        String currentMenu = "menu\\s+show-current";
        String back = "menu\\s+exit";
        String command = ShopView.getCommand();
        Pattern pattern = Pattern.compile(command);
        Matcher matcher;

        while (true) {
            matcher = pattern.matcher(shopBuy);
            if (matcher.find()) {
                String cardName = matcher.group(1);
                if (ShopModel.getCardPriceByName(cardName) == 0)
                    ShopView.showInput("there is no card with this name");
                else if (UserModel.getUserByUsername(onlineUser).getUserCoin() < ShopModel.getCardPriceByName(cardName))
                    ShopView.showInput("not enough money");
                else
                    shopBuy(cardName);
                continue;
            }
            matcher = pattern.matcher(shopShow);
            if (matcher.find()) {
                shopShow();
                continue;
            }
            matcher = pattern.matcher(enterMenu);
            if (matcher.find()) {
                String menuName = matcher.group(1);
                if (menuName.equals("Main Menu"))
                    MainMenuController.findMatcher();
                else if (menuName.equals("deck"))
                    DeckController.findMatcher();
                else
                    ShopView.showInput("invalid command");
                continue;
            }
            matcher = pattern.matcher(currentMenu);
            if (matcher.find()) {
                ShopView.showInput("shop");
                continue;
            }
            matcher = pattern.matcher(back);
            if (matcher.find()) {
                MainMenuController.findMatcher();
                break;
            }
            ShopView.showInput("invalid command");
        }
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
