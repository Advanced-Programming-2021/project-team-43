package model;
import java.util.HashMap;

public class SpellCard extends Card {

    private String icon;
    String status;
    private static HashMap<String, SpellCard> spellCards = new HashMap<>();

    public SpellCard(String cardName, String cardModel,  String icon,String description,int price, String status) {
        super(cardName, cardModel,description,price);
        this.cardName = cardName;
        this.icon = icon;
        this.status=status;
        spellCards.put(cardName, this);

    }

    public static HashMap<String, SpellCard> getSpellCards() {
        return spellCards;
    }

    public String getStatus() {
        return status;
    }

    public String getCardName() {
        return cardName;
    }

    public String getIcon() {
        return icon;
    }

    public static SpellCard getSpellCardByName(String cardName) {
        return spellCards.get(cardName);
    }
}
