package model;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class ShopModel {

    private static final Map<String, Integer> cardInfo= new HashMap<>();

    public ShopModel(@NotNull HashMap<String, Card> allCards) {
        for (Map.Entry<String, Card> eachCard : allCards.entrySet()) {
            cardInfo.put(eachCard.getKey(), Card.getCardsByName(eachCard.getKey()).getPrice());
        }
    }

    public static int getCardPriceByName(String cardName) {
        if (cardInfo.containsKey(cardName))
            return cardInfo.get(cardName);
        return 0;
    }

    public static HashMap<String, Integer> getCardInfo() {
        return (HashMap<String, Integer>) cardInfo;
    }

}