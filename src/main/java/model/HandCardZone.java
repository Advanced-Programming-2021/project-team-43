package model;
import java.util.*;


public class HandCardZone {

    private final String playerNickname;
    private final String cardName;
    private int address;
    private final String kind;
    private boolean isSelected;
    private boolean isRemoved;
    private int addressCounter = 0;
    private static final Map<Integer,HandCardZone> eachHandCard = new HashMap<>();
    private static final Map<String, Map<Integer,HandCardZone>> allHandCards = new HashMap<>();

    public HandCardZone(String playerNickname, String cardName) {
        this.playerNickname = playerNickname;
        this.cardName = cardName;
        this.address = ++addressCounter;
        this.kind = Card.getCardsByName(cardName).getCardModel();
        eachHandCard.put(address,this);
        allHandCards.put(playerNickname,eachHandCard);
    }

    public String getCardName() {
        return cardName;
    }

    public int getAddress() {
        return address;
    }

    public String getKind() {
        return kind;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public void removeFromHandCard() {///////?????????
        allHandCards.get(playerNickname).remove(address);
        eachHandCard.remove(address);
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allHandCards.get(playerNickname).size();
    }

    public static HandCardZone getHandCardByAddress(int address, String playerNickname) {
        return allHandCards.get(playerNickname).get(address);
    }


    public static int doIHaveAnyRitualMonster(String playerNickname) {
        for (HandCardZone eachCard : allHandCards.get(playerNickname).values())
            if (eachCard.getCardName().equals("Crab Turtle") || eachCard.getCardName().equals("Skull Guardian"))
                return eachCard.getAddress();
        return 0;
    }

}
