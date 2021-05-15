package model;
import view.GameMatView;
import java.util.*;


public class HandCardZone {

    private final String cardName;
    private final int address;
    private final String kind;
    public static  Map<String, List<HandCardZone>> allHandCards = new HashMap<>();

    public HandCardZone(String playerNickname, String cardName) {
        this.cardName = cardName;
        this.address = allHandCards.get(playerNickname).size();
        this.kind = Card.getCardsByName(cardName).getCardModel();
        allHandCards.get(playerNickname).add(address, this);
    }

    public String getCardName() {
        return cardName;
    }

    public int getAddress() {
        return address;//based on list index
    }

    public String getKind() {
        return kind;
    }

    public static void removeFromHandCard(String playerNickname, int address) {
        allHandCards.get(playerNickname).remove(address);//based of list index
    }

    public static int[] getAddressByName(String playerNickname, String cardName) {
        int[] allAddresses = new int[6];
        int counter = -1;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            if (allHandCards.get(playerNickname).get(i).getCardName().equals(cardName))
                allAddresses[++counter] = allHandCards.get(playerNickname).get(i).getAddress();
        }
        return allAddresses;
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allHandCards.get(playerNickname).size();
    }

    public static boolean doesThisModelAndTypeExist(String playerNickname, String model, String type) {
        String cardName;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            cardName = allHandCards.get(playerNickname).get(i).getCardName();
            if (model.equals("Monster"))
                return MonsterCard.getMonsterByName(cardName).getMonsterType().equals(type);
            if (model.equals("Spell"))
                return SpellCard.getSpellCardByName(cardName).getIcon().equals(type);
        }
        return false;
    }

    public static boolean doesAnyLevelFourMonsterExisted(String playerNickname) {
        String cardName;
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++) {
            cardName = allHandCards.get(playerNickname).get(i).getCardName();
            if (allHandCards.get(playerNickname).get(i).getKind().equals("Monster"))
                if (MonsterCard.getMonsterByName(cardName).getLevel() < 5)
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
        return allHandCards.get(playerNickname).get(address);
    }

    public static int doIHaveAnyRitualMonster(String playerNickname) {
        for (HandCardZone eachCard : allHandCards.get(playerNickname))
            if (eachCard.getCardName().equals("Crab Turtle") || eachCard.getCardName().equals("Skull Guardian"))
                return eachCard.getAddress();
        return 0;
    }

}
