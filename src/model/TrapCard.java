package model;

import java.util.HashMap;

public class TrapCard extends Card {
    private String cardName;
    private String type;
    private int cardNumber;
    private String icon;
    private String cardDescription;
    private String cardOwner;
    private static HashMap<String, TrapCard> trapCards = new HashMap<>();

    public TrapCard(String cardName, String type, int cardNumber, String icon, String cardDescription, String cardOwner
            , String cardSide, String cardLocation, String backAndForth, String description, String attribute, String cardModel) {
        super(cardName, cardSide, cardLocation, backAndForth, description, attribute, cardModel, cardNumber, cardOwner);
        this.cardName = cardName;
        this.type = type;
        this.cardNumber = cardNumber;
        this.icon = icon;
        this.cardDescription = cardDescription;
        this.cardOwner = cardOwner;
        trapCards.put(cardName, this);
    }

    public String getCardName() {
        return cardName;
    }

    public String getType() {
        return type;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getIcon() {
        return icon;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public static TrapCard getTrapCardByName(String cardName) {
        return trapCards.get(cardName);
    }
}
