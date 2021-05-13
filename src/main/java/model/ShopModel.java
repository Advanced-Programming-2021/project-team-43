package model;
import java.util.*;


public class ShopModel {

    private static final Map<String,Integer> cardInfo= new HashMap<>();

    public ShopModel(HashMap<String, Card> allCards) {
        for (Map.Entry<String,Card> eachCard : allCards.entrySet()) {
            cardInfo.put(eachCard.getKey(), Card.getCardsByName(eachCard.getKey()).getPrice());
        }
    }

    public static int getCardPriceByName(String cardName) {
        for (Map.Entry<String,Integer> entry : cardInfo.entrySet())
            if (entry.getKey().equals(cardName))
                return entry.getValue();
        return 0;
    }

    public static HashMap<String,Integer> getCardInfo() {
        return (HashMap<String, Integer>) cardInfo;
    }
}