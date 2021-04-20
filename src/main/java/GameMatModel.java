import java.util.*;

public class GameMatModel {
    private HashMap<Integer,String> monstersZone;
    private HashMap<Integer,String> spellsZone;
    private ArrayList<String> handCard;
    private String fieldZone;
    private int phase;
    public int selectedOwnCardAddress;
    public String rivalName;
    public String ownName;
    public String action;


    public GameMatModel (ArrayList<String> handCard, String ownName) {

    }


    public void addToMonsterZone(String cardName, int zone) {

    }


    public void addToSpellZone(String cardName, int zone) {

    }


    public void addToFieldCard(String cardName) {

    }


    public void removeFromMonsterZone(String cardName, int zone) {

    }


    public void removeFromSpellCardZone(String cardName, int zone) {

    }


    public void removeFromFieldCard(String cardName) {

    }


    public void removeFromHand(String cardName) {

    }


    public void addToHand(String cardName) {

    }


    public void changePhase(int phase) {

    }


    public void getPhase() {///////////////////////

    }


    public void selectOwnCard(int address) {

    }


    public void attack(int locOfPlayer, int locOfRival) {

    }


    public void setCard() {

    }


    public void flipSummon(int address) {

    }


    public void attackDirect(int locOfPlayer, int locOfRival) {

    }


    public void activateEffect() {

    }


    public void showGraveyard() {

    }


    public void showCard() {

    }


    public void endGame() {

    }


    public void selectRivalCard(int address) {

    }


    public void changeCardMode(String mode) {

    }


    public void setRivalName(String rivalName) {

    }
}
