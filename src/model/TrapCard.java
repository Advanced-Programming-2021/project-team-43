package model;

import java.util.HashMap;

public class TrapCard extends Card {
    // private String cardName;
    // private String type;
    private String icon;
    private String status;
    private static HashMap<String, TrapCard> trapCards = new HashMap<>();

    public TrapCard(String cardName, String cardModel, String icon, String description, int price, String status) {
        super(cardName, cardModel, description, price);
        this.cardName = cardName;
        //  this.type = type;
        this.icon = icon;
        this.status = status;
        trapCards.put(cardName, this);
    }

    public String getCardName() {
        return cardName;
    }

//    public String getType() {
//        return type;
//    }

    public String getIcon() {
        return icon;
    }

    public static TrapCard getTrapCardByName(String cardName) {
        return trapCards.get(cardName);
    }
}
