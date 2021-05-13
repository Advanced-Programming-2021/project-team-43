package model;
import view.GameMatView;

import javax.swing.plaf.PanelUI;
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
        for (int i = 0; i < allHandCards.get(playerNickname).size(); i++)
            GameMatView.showInput(i + 1 + ". " + allHandCards.get(playerNickname).get(i));
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
