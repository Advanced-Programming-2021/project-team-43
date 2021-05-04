package model;
import java.util.*;


public class SpellTrapZoneCard {

    private String playerNickname;
    private String spellName;
    private int address;
    private String mode;
    private boolean isSelected;
    private int numberOfFullHouse = 0;
    private static final Map<Integer,SpellTrapZoneCard> eachSpellCard = new HashMap<>();
    private static final Map<String, Map<Integer,SpellTrapZoneCard>> allSpellCards = new HashMap<>();


    public SpellTrapZoneCard(String playerNickname, String spellName, String mode) {
        this.playerNickname = playerNickname;
        this.spellName = spellName;
        this.address = ++numberOfFullHouse;
        this.mode = mode;
        eachSpellCard.put(address, this);
        allSpellCards.put(playerNickname, eachSpellCard);
    }

    public String getSpellName() {
        return spellName;
    }

    public int getAddress() {
        return address;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "Name: " + spellName + "\n" +
                "Spell" +  "\n" +
                "Type: " + SpellCard.getSpellCardByName(spellName).getCardModel() + "\n" +
                "Description: " + SpellCard.getSpellCardByName(spellName).getDescription();
    }


    public static int getNumberOfFullHouse(String playerNickname) {
        return allSpellCards.get(playerNickname).size();
    }

    public static SpellTrapZoneCard getSpellCardByAddress(int address, String playerNickname) {
        return allSpellCards.get(playerNickname).get(address);
    }

    public static Map<Integer,SpellTrapZoneCard> getAllObjectsByPlayerName(String playerNickname) {
        return allSpellCards.get(playerNickname);
    }

}
