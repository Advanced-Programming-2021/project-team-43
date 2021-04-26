package model;

import java.util.*;

public class HandCardZone {

    private String playerNickname;
    private String cardName;
    private int address;
    private String kind;//monster, spell, trap
    private boolean isSelected;
    private static List<HandCardZone> allHandCard = new ArrayList<>();
    private static int addressCounter = 1;

    public HandCardZone(String playerNickname, String cardName, String kind) {
        this.playerNickname = playerNickname;
        this.cardName = cardName;
        this.address = addressCounter;
        this.kind = kind;
        addressCounter++;
        if (addressCounter == 7)
            addressCounter = 1;
        allHandCard.add(this);
    }

    public String getCardName() {
        return cardName;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
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

    public static int getNumberOfHandCard() {
        return addressCounter - 1;
    }

    public static HandCardZone getHandCardByName(String cardName, String playerNickname) {
        for (HandCardZone eachCard: allHandCard)
            if (eachCard.cardName.equals(cardName) && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

    public static HandCardZone getHandCardByAddress(int address, String playerNickname) {
        for (HandCardZone eachCard: allHandCard)
            if (eachCard.address == address && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

}
