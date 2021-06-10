package main.java.model;
import java.util.HashMap;


public class TrapCard extends Card {

    private String icon;
    private String status;
    private static HashMap<String, TrapCard> trapCards = new HashMap<>();

    public TrapCard(String cardName, String cardModel, String icon, String description, int price, String status,String secondName) {
        super(cardName, cardModel, description, price,secondName);
        this.cardName = cardName;
        this.icon = icon;
        this.status = status;
        trapCards.put(cardName, this);
    }

    public String getCardName() {
        return cardName;
    }

    public String getIcon() {
        return icon;
    }

    public String getStatus() {
        return status;
    }

    public static HashMap<String, TrapCard> getTrapCards() {
        return trapCards;
    }

    public static TrapCard getTrapCardByName(String cardName) {
        return trapCards.get(cardName);
    }

}
