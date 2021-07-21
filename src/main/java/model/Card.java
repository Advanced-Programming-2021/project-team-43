package model;
import java.util.HashMap;


public class Card {

    protected String cardName;
    protected String cardModel;
    protected String description;
    protected int price;
    protected String secondName;
    protected static HashMap<String, Card> cards = new HashMap<>();
    public boolean isAvailable;
    public int mojoodi;

    public Card(String cardName, String cardModel, String description, int price, String secondName) {
        this.cardName = cardName;
        this.cardModel = cardModel;
        this.description = description;
        this.price = price;
        this.secondName=secondName;
        cards.put(cardName, this);
        isAvailable=true;
        mojoodi=10;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getDescription() {
        return description;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardModel() {
        return cardModel;
    }

    public int getPrice() {
        return price;
    }

    public static HashMap<String, Card> getCards() {
        return cards;
    }

    public static Card getCardsByName(String name) {
        return cards.get(name);
    }

}