package model;

import java.util.HashMap;

public class SpellCard extends Card{

    private String cardName;
    private String type;
    private int cardNumber;
    private String icon;
    private String cardDescription;
    private String cardOwner;
    private static HashMap<String, SpellCard> spellCards = new HashMap<>();

    public SpellCard(String cardName, String type, int cardNumber, String icon,
                     String cardDescription, String cardOwner) {
        super();
        this.cardName = cardName;
        this.type = type;
        this.cardNumber = cardNumber;
        this.icon = icon;
        this.cardDescription = cardDescription;
        this.cardOwner = cardOwner;
        spellCards.put(cardName,this);
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

    public static  SpellCard getSpellCardByName(String cardName) {
        return spellCards.get(cardName);
    }
}
