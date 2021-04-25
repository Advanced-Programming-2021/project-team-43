import java.util.*;

public class SpellZoneCard {

    private String playerNickname;
    private String spellName;
    private int address;
    private String mode; //spell : set , activate
    private boolean isSelected;
    private static List<SpellZoneCard> allSpellZoneCard = new ArrayList<>();

    public SpellZoneCard(String playerNickname, String spellName, int address, String mode) {
        this.playerNickname = playerNickname;
        this.spellName = spellName;
        this.address = address;
        this.mode = mode;
        allSpellZoneCard.add(this);
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



    public static boolean isAnySpellSelected() {
        for (SpellZoneCard eachCard: allSpellZoneCard)
            if (eachCard.isSelected)
                return true;
        return false;
    }

    public static SpellZoneCard getSpellCardByName(String spellName, String playerNickname) {
        for (SpellZoneCard eachCard: allSpellZoneCard)
            if (eachCard.spellName.equals(spellName) && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

    public static SpellZoneCard getSpellCardByAddress(int address, String playerNickname) {
        for (SpellZoneCard eachCard: allSpellZoneCard)
            if (eachCard.address == address && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

}
