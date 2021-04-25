import java.util.*;

public class GameMatModel {

    private String playerNickname;
    private Phase phase;
    //public String action;
    private List<String> handCard = new ArrayList<>();
    private Map<Integer,String> monstersZone = new HashMap<>();
    private Map<Integer,String> spellsZone = new HashMap<>();
    private List<String> graveyard = new ArrayList<>();
    private Map<Integer,String> deckZone = new HashMap<>();
    private String fieldZone;
    private int ownMonsterZoneSelected;
    private int ownSpellZoneSelected;
    private boolean isOwnFieldZoneSelected;
    private int rivalMonsterZoneSelected;
    private int rivalSpellZoneSelected;
    private int rivalFieldZoneSelected;
    private int handCardSelected;
    private static List<GameMatModel> playerGameMat = new ArrayList<>();

    public GameMatModel (String playerNickname) {
        this.playerNickname = playerNickname;
        this.phase = Phase.Draw_Phase;
        for (int i = 1; i < 6; i++) {
            this.monstersZone.put(i, "");
            this.spellsZone.put(i, "");
        }
        playerGameMat.add(this);
    }

    public String getMonsterNameByAddress(int address) {
        for (Map.Entry eachMonsterZone : monstersZone.entrySet()) {
            if ((int)eachMonsterZone.getKey() == address)
                return (String) eachMonsterZone.getValue();
        }
        return null;
    }

    public String getSpellNameByAddress(int address) {
        for (Map.Entry eachSpellZone : spellsZone.entrySet()) {
            if ((int)eachSpellZone.getKey() == address)
                return (String) eachSpellZone.getValue();
        }
        return null;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void addToHandCard(String cardName) {
        handCard.add(cardName);
    }

    public void removeFromHandCard(String cardName) {
        Iterator<String> iteratorOfCards = handCard.iterator();
        while (iteratorOfCards.hasNext()) {
            String eachCard = iteratorOfCards.next();
            if (eachCard.equals(cardName)) iteratorOfCards.remove();
        }
    }

    public void addToMonsterZone(String cardName, int zone) {
        monstersZone.put(zone, cardName);
    }

    public void removeFromMonsterZone(int zone) {
        monstersZone.remove(zone);
    }

    public void addToSpellZone(String cardName, int zone) {
        spellsZone.put(zone, cardName);
    }

    public void removeFromSpellZone(int zone) {
        spellsZone.remove(zone);//null
    }

    public void addToFieldZone(String cardName) {
        fieldZone = cardName;
    }

    public void removeFromFieldZone(String cardName) {
        fieldZone = "";
    }

    public void removeCardFromDeck() {
        deckZone.remove(1);
    }

    public int getOwnMonsterZoneSelected() {
        return ownMonsterZoneSelected;
    }

    public void setOwnMonsterZoneSelected(int ownMonsterZoneSelected) {
        this.ownMonsterZoneSelected = ownMonsterZoneSelected;
    }

    public int getOwnSpellZoneSelected() {
        return ownSpellZoneSelected;
    }

    public void setOwnSpellZoneSelected(int ownSpellZoneSelected) {
        this.ownSpellZoneSelected = ownSpellZoneSelected;
    }

    public boolean getOwnFieldZoneSelected() {
        return isOwnFieldZoneSelected;
    }

    public void setOwnFieldZoneSelected() {
        this.isOwnFieldZoneSelected = true;
    }

    public int getHandCardSelected() {
        return handCardSelected;
    }

    public void setHandCardSelected(int handCardSelected) {
        this.handCardSelected = handCardSelected;
    }

    public int getRivalFieldZoneSelected() {
        return rivalFieldZoneSelected;
    }

    public void setRivalFieldZoneSelected(int rivalFieldZoneSelected) {
        this.rivalFieldZoneSelected = rivalFieldZoneSelected;
    }

    public int getRivalMonsterZoneSelected() {
        return rivalMonsterZoneSelected;
    }

    public void setRivalMonsterZoneSelected(int rivalMonsterZoneSelected) {
        this.rivalMonsterZoneSelected = rivalMonsterZoneSelected;
    }

    public int getRivalSpellZoneSelected() {
        return rivalSpellZoneSelected;
    }

    public void setRivalSpellZoneSelected(int rivalSpellZoneSelected) {
        this.rivalSpellZoneSelected = rivalSpellZoneSelected;
    }

    public int getNumberOfHandCard() {
        return handCard.size();
    }

    public boolean isAnyMonsterCardHere(int address) {
        return !monstersZone.get(address).equals("");
    }

    public boolean isAnySpellCardHere(int address) {
        return !spellsZone.get(address).equals("");
    }

    public String getFieldZone() {
        return fieldZone;
    }

    private void attack(int locOfPlayer, int locOfRival) {

    }

    private void setCard() {

    }

    private void flipSummon(int address) {

    }

    private void attackDirect(int locOfPlayer, int locOfRival) {

    }

    private void activateEffect() {

    }

    private void showGraveyard() {

    }

    private void showCard() {

    }

    private void endGame() {

    }

    private void selectRivalCard(int address) {

    }

    private void changeCardMode(String mode) {

    }

    private void setRivalName(String rivalName) {

    }

    public static GameMatModel getGameMatByNickname(String nickname) {
        for (GameMatModel eachGameMat: playerGameMat)
            if (eachGameMat.playerNickname.equals(nickname))
                return eachGameMat;

        return null;
    }

}
