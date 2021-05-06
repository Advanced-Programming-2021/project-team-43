package model;
import java.util.*;
import controller.GameMatController;

public class SpellTrapZoneCard {

    private final String playerNickname;
    private final String spellTrapName;
    private String mode;
    private final int address;
    private boolean isSelected;
    private int numberOfFullHouse = 0;
    private static final Map<Integer,SpellTrapZoneCard> eachSpellTrapCard = new HashMap<>();
    private static final Map<String, Map<Integer,SpellTrapZoneCard>> allSpellTrapCards = new HashMap<>();


    public SpellTrapZoneCard(String playerNickname, String spellTrapName, String mode) {
        this.playerNickname = playerNickname;
        this.spellTrapName = spellTrapName;
        this.mode = mode;
        this.address = ++numberOfFullHouse;
        eachSpellTrapCard.put(address, this);
        allSpellTrapCards.put(playerNickname, eachSpellTrapCard);
    }

    public String getSpellTrapName() {
        return spellTrapName;
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

    public static int getNumberOfFullHouse(String playerNickname) {
        return allSpellTrapCards.get(playerNickname).size();
    }

    public void changeNumberOfFullHouse(int amount) {
        numberOfFullHouse += amount;
    }

    public void removeSpellTrapFromZone() {
        GameMatModel.getGameMatByNickname(GameMatController.onlineUser).addToGraveyard(this.spellTrapName);
        allSpellTrapCards.get(this.playerNickname).remove(this.address);
        changeNumberOfFullHouse(-1);
    }

    @Override
    public String toString() {
        return "Name: " + spellTrapName + "\n" +
                "Spell" +  "\n" +
                "Type: " + SpellCard.getSpellCardByName(spellTrapName).getCardModel() + "\n" +
                "Description: " + SpellCard.getSpellCardByName(spellTrapName).getDescription();
    }

    public static String[] getAllSpellTrapMode(String playerNickname) {
        String[] allSpellsMode = new String[5];
        if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(5))
            allSpellsMode[0] = "E";
        else
            allSpellsMode[0] = getAllSpellTrapByPlayerName(playerNickname).get(5).getMode();
        if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(3))
            allSpellsMode[1] = "E";
        else
            allSpellsMode[1] = getAllSpellTrapByPlayerName(playerNickname).get(3).getMode();
        if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(1))
            allSpellsMode[2] = "E";
        else
            allSpellsMode[2] = getAllSpellTrapByPlayerName(playerNickname).get(1).getMode();
        if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(2))
            allSpellsMode[3] = "E";
        else
            allSpellsMode[3] = getAllSpellTrapByPlayerName(playerNickname).get(2).getMode();
        if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(4))
            allSpellsMode[4] = "E";
        else
            allSpellsMode[4] = getAllSpellTrapByPlayerName(playerNickname).get(4).getMode();
        return allSpellsMode;
    }

    public static SpellTrapZoneCard getSpellCardByAddress(int address, String playerNickname) {
        return allSpellTrapCards.get(playerNickname).get(address);
    }

    public static Map<Integer,SpellTrapZoneCard> getAllSpellTrapByPlayerName(String playerNickname) {
        return allSpellTrapCards.get(playerNickname);
    }

}
