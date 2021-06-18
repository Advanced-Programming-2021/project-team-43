package model;
import view.GameMatView;
import java.util.*;


public class HandCardZone {


    private final String playerNickname;
    private final String cardName;
    private int address;
    private final String kind;
    public static  Map<String, List<HandCardZone>> allHandCards = new HashMap<>();

    public HandCardZone(String playerNickname, String cardName) {
        this.playerNickname = playerNickname;
        this.cardName = cardName;
        this.address = allHandCards.get(playerNickname).size();
        this.kind = Card.getCardsByName(cardName).getCardModel();
        allHandCards.get(playerNickname).add(address, this);
    }

    public String getCardName() {
        return cardName;
    }

    public int getAddress() {
        return address;
    }

    public void changeAddress() {
        address--;
    }

    public String getKind() {
        return kind;
    }

    public void removeFromHandCard() {
        allHandCards.get(playerNickname).remove(address);
        for (int i = address; i < allHandCards.get(playerNickname).size(); i++) {
            allHandCards.get(playerNickname).get(i).changeAddress();
        }
    }

    public static void removeAllTypeCard(String playerNickname, String cardName) {
        HandCardZone handCard;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            handCard = allHandCards.get(playerNickname).get(i);
            if (handCard.cardName.equals(cardName)) {
                handCard.removeFromHandCard();
            }
        }
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allHandCards.get(playerNickname).size();
    }

    public static int getAddressOfCybreseMonster(String playerNickname) {
        HandCardZone handCard;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            handCard = allHandCards.get(playerNickname).get(i);
            if (handCard != null && handCard.getKind().equals("Monster") && MonsterCard.getMonsterByName(handCard.getCardName()).getMonsterType().equals("Cyberse"))
                return i;
        }
        return -1;
    }

    public static boolean doesAnyLevelFourMonsterExisted(String playerNickname) {
        HandCardZone handCard;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            handCard = allHandCards.get(playerNickname).get(i);
            if (handCard.getKind().equals("Monster"))
                if (MonsterCard.getMonsterByName(handCard.getCardName()).getLevel() < 5)
                    return true;
        }
        return false;
    }

    public static boolean doesThisCardNameExist(String playerNickname, String cardName) {
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            if (allHandCards.get(playerNickname).get(i).getCardName().equals(cardName))
                return true;
        }
        return false;
    }

    public static void showHandCard(String playerNickname) {
        List<HandCardZone> handCardOwn=allHandCards.get(playerNickname);
        for (int i = 0; i < handCardOwn.size(); i++)
            GameMatView.showInput(i + 1 + ". " + handCardOwn.get(i).getCardName());
    }

    public static HandCardZone getHandCardByAddress(int address, String playerNickname) {
        if (address >= allHandCards.get(playerNickname).size() || address < 0)
            return null;
        return allHandCards.get(playerNickname).get(address);
    }

    public static int doIHaveAnyRitualMonster(String playerNickname) {
        for (HandCardZone eachCard : allHandCards.get(playerNickname))
            if (eachCard.getCardName().equals("Crab Turtle") || eachCard.getCardName().equals("Skull Guardian"))
                return eachCard.getAddress() + 1;
        return -1;
    }

}