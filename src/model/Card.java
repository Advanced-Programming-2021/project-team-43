package model;

import java.util.HashMap;

public class Card {
    protected String cardName;
    protected String cardLocation;
    protected String cardSide;
    protected String backAndForth;
    protected String cardModel;
  //  protected String attribute;
   // protected String cardNumber;
    protected String description;
    protected int price;
    protected static HashMap<String, Card> cards = new HashMap<>();

    public Card(String cardName, String cardModel,String description,int price) {
        this.cardName = cardName;
       // this.cardNumber = cardNumber;
        this.cardModel = cardModel;
     //   this.attribute = attribute;
        this.description=description;
        cards.put(cardName, this);
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
//    public String getAttribute() {
//        return attribute;
//    }

    public String getCardModel() {
        return cardModel;
    }

    public String getBackAndForth() {
        return backAndForth;
    }


    public static Card getCardsByName(String name) {
        return cards.get(name);
    }
}
