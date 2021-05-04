package model;
import controller.GameMatController;
import java.util.*;


public class MonsterZoneCard {

    private final String playerNickname;
    private final String monsterName;
    private int address;
    private String mode;
    private int attack;
    private int defend;
    private boolean isSelected;
    private boolean isScanner;
    private boolean isDead;
    private boolean haveChangedPositionThisTurn;
    private boolean haveAttackThisTurn;
    private int numberOfFullHouse = 0;
    private static final Map<Integer,MonsterZoneCard> eachMonsterCard = new HashMap<>();
    private static final Map<String, Map<Integer,MonsterZoneCard>> allMonsterCards = new HashMap<>();


    public MonsterZoneCard(String playerNickname, String monsterName, String mode, boolean isScanner) {
        this.playerNickname = playerNickname;
        this.monsterName = monsterName;
        this.address = ++numberOfFullHouse;
        this.mode = mode;
        this.isScanner = isScanner;
        this.haveChangedPositionThisTurn = true;
        eachMonsterCard.put(address,this);
        allMonsterCards.put(playerNickname,eachMonsterCard);
    }

    public String getMonsterName() {
        return monsterName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void changeAttack(int attack) {
        this.attack += attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public void changeDefend(int defend) {
        this.defend += defend;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getHaveAttackThisTurn() {
        return haveAttackThisTurn;
    }

    public void setHaveAttackThisTurn(boolean haveAttackThisTurn) {
        this.haveAttackThisTurn = haveAttackThisTurn;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
        GameMatModel.getGameMatByNickname(GameMatController.onlineUser).addToGraveyard(this.monsterName);
        allMonsterCards.get(this.playerNickname).remove(this.address);
    }

    public boolean getHaveChangedPositionThisTurn() {
        return haveChangedPositionThisTurn;
    }

    public void setHaveChangedPositionThisTurn(boolean haveChangedPositionThisTurn) {
        this.haveChangedPositionThisTurn = haveChangedPositionThisTurn;
    }

    public void changeNumberOfFullHouse(int amount) {
        numberOfFullHouse += amount;
    }

    @Override
    public String toString() {
        return "Name: " + monsterName + "\n" +
                "Level: " + MonsterCard.getMonsterByName(monsterName).getLevel() + "\n" +
                "Type: " + MonsterCard.getMonsterByName(monsterName).getMonsterType() + "\n" +
                "ATK: " + attack + "\n" +
                "DEF: " + defend + "\n" +
                "Description: " + MonsterCard.getMonsterByName(monsterName).getDescription() ;
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allMonsterCards.get(playerNickname).size();
    }

    public static MonsterZoneCard getMonsterCardByAddress(int address, String playerNickname) {
        return allMonsterCards.get(playerNickname).get(address);
    }

    public static Map<Integer,MonsterZoneCard> getAllObjectsByPlayerName(String playerNickname) {
        return allMonsterCards.get(playerNickname);
    }

}
