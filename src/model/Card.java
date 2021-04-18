package model;

import java.util.HashMap;

public class Card {
    protected String cardName;
    protected String cardLocation;
    protected String cardSide;
    protected String backAndForth;
    protected String description;
    protected String cardModel;
    protected String attribute;
    protected String cardOwner;
    protected int cardNumber;
    protected HashMap<String, Card> cards = new HashMap<>();

    public Card(String cardName, String cardSide, String cardLocation, String backAndForth,
                String description, String attribute, String cardModel, int cardNumber, String cardOwner) {
        this.cardName = cardName;
        this.cardSide = cardSide;
        this.backAndForth = backAndForth;
        this.cardLocation = cardLocation;
        this.description = description;
        this.cardNumber = cardNumber;
        this.cardModel = cardModel;
        this.attribute = attribute;
        this.cardOwner = cardOwner;
        cards.put(cardName, this);
    }

    public Card() {

    }

    public String getCardName() {
        return cardName;
    }

    public String getCardLocation() {
        return cardLocation;
    }

    public String getCardSide() {
        return cardSide;
    }

    public String getDescription() {
        return description;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getCardModel() {
        return cardModel;
    }

    public String getBackAndForth() {
        return backAndForth;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }
}
