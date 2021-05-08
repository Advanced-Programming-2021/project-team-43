package model;
import java.util.*;


public class HandCardZone {

    private final String playerNickname;
    private final String cardName;
    private final int address;
    private final String kind;
    private static final List<HandCardZone> eachHandCard = new ArrayList<>();
    private static final Map<String, List<HandCardZone>> allHandCards = new HashMap<>();

    public HandCardZone(String playerNickname, String cardName) {
        this.playerNickname = playerNickname;
        this.cardName = cardName;
        this.address = eachHandCard.size();
        this.kind = Card.getCardsByName(cardName).getCardModel();
        eachHandCard.add(eachHandCard.size(), this);
        allHandCards.put(playerNickname,eachHandCard);
    }

    public String getCardName() {
        return cardName;
    }

    public int getAddress() {
        return address + 1;
    }

    public String getKind() {
        return kind;
    }

    public void removeFromHandCard() {
        allHandCards.get(playerNickname).remove(address);
        eachHandCard.remove(address);
        //add to graveyard
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allHandCards.get(playerNickname).size();
    }

    public static HandCardZone getHandCardByAddress(int address, String playerNickname) {
        return allHandCards.get(playerNickname).get(address - 1);
    }


    public static int doIHaveAnyRitualMonster(String playerNickname) {
        for (HandCardZone eachCard : allHandCards.get(playerNickname))
            if (eachCard.getCardName().equals("Crab Turtle") || eachCard.getCardName().equals("Skull Guardian"))
                return eachCard.getAddress();
        return 0;
    }

}
