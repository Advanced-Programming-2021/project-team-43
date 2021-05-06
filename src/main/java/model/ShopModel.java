package main.java.model;
import java.util.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ShopModel {

    private static final Map<String,Integer> cardInfo= new HashMap<>();

    public static Integer getCardPriceByName(String cardName) {
        return cardInfo.get(cardName);
    }

    public static HashMap<String,Integer> getCardInfo() {
        return (HashMap<String,Integer>) cardInfo;
    }

}