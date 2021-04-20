import java.util.*;

public class ShopModel {
    private String cardName;
    private int cardPrice;
    private static Map<String,Integer> cardInfo= new HashMap<>();

    public int getCardPriceByName(String cardName) {
        //return the card price by iterating over the cardInfo HashMap
        for (Map.Entry<String,Integer> entry : cardInfo.entrySet())
            if (entry.getKey().equals(cardName))
                return entry.getValue();
        //return 0 if the cardName dont exist
        return 0;
    }
}