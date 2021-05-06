package model;
import controller.GameMatController;
import java.util.*;


public class MonsterZoneCard {

    private final String playerNickname;
    private final String monsterName;
    private String mode;
    private final int address;
    private int attack;
    private int defend;
    private final int level;
    private boolean isScanner;
    private boolean isSelected;
    private boolean haveChangedPositionThisTurn;
    private boolean haveAttackThisTurn;
    private boolean canAttack;
    private boolean canAttackToThisMonster;
    private boolean isEffectUsed;//change turn false
    private int numberOfFullHouse = 0;
    private List<Integer> allRelatedMonsters = new ArrayList<>();
    private static final Map<Integer,MonsterZoneCard> eachMonsterCard = new HashMap<>();
    private static final Map<String, Map<Integer,MonsterZoneCard>> allMonsterCards = new HashMap<>();


    public MonsterZoneCard(String playerNickname, String monsterName, String mode, boolean isScanner) {
        this.playerNickname = playerNickname;
        this.monsterName = monsterName;
        this.mode = mode;
        this.address = ++numberOfFullHouse;
        this.attack = MonsterCard.getMonsterByName(monsterName).getAttack();
        this.defend = MonsterCard.getMonsterByName(monsterName).getDefend();
        this.level = MonsterCard.getMonsterByName(monsterName).getLevel();
        this.isScanner = isScanner;
        this.haveChangedPositionThisTurn = true;
        this.canAttack = true;
        this.canAttackToThisMonster = true;
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

    public int getLevel() {
        return level;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getHaveChangedPositionThisTurn() {
        return haveChangedPositionThisTurn;
    }

    public void setHaveChangedPositionThisTurn(boolean haveChangedPositionThisTurn) {
        this.haveChangedPositionThisTurn = haveChangedPositionThisTurn;
    }

    public boolean getHaveAttackThisTurn() {
        return haveAttackThisTurn;
    }

    public void setHaveAttackThisTurn(boolean haveAttackThisTurn) {
        this.haveAttackThisTurn = haveAttackThisTurn;
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean getCanAttackToThisMonster() {
        return canAttackToThisMonster;
    }

    public void setCanAttackToThisMonster(boolean canAttackToThisMonster) {
        this.canAttackToThisMonster = canAttackToThisMonster;
    }

    public boolean getIsEffectUsed() {
        return isEffectUsed;
    }

    public void setIsEffectUsed(boolean isEffectUsed) {
        this.isEffectUsed = isEffectUsed;
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        return allMonsterCards.get(playerNickname).size();
    }

    public void changeNumberOfFullHouse(int amount) {
        numberOfFullHouse += amount;
    }

    public void removeMonsterFromZone() {
        GameMatModel.getGameMatByNickname(GameMatController.onlineUser).addToGraveyard(this.monsterName);
        allMonsterCards.get(this.playerNickname).remove(this.address);
        changeNumberOfFullHouse(-1);
    }

    public void setRelatedMonsters(ArrayList<Integer> allRelatedMonsters) {
        this.allRelatedMonsters.addAll(allRelatedMonsters);
    }

    public ArrayList<Integer> getRelatedMonsters() {
        return (ArrayList<Integer>) allRelatedMonsters;
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

    public static String[] getAllMonstersMode(String playerNickname) {
        String[] allMonstersMode = new String[5];
        if (!getAllMonstersByPlayerName(playerNickname).containsKey(5))
            allMonstersMode[0] = "E";
        else
            allMonstersMode[0] = getAllMonstersByPlayerName(playerNickname).get(5).getMode();
        if (!getAllMonstersByPlayerName(playerNickname).containsKey(3))
            allMonstersMode[1] = "E";
        else
            allMonstersMode[1] = getAllMonstersByPlayerName(playerNickname).get(3).getMode();
        if (!getAllMonstersByPlayerName(playerNickname).containsKey(1))
            allMonstersMode[2] = "E";
        else
            allMonstersMode[2] = getAllMonstersByPlayerName(playerNickname).get(1).getMode();
        if (!getAllMonstersByPlayerName(playerNickname).containsKey(2))
            allMonstersMode[3] = "E";
        else
            allMonstersMode[3] = getAllMonstersByPlayerName(playerNickname).get(2).getMode();
        if (!getAllMonstersByPlayerName(playerNickname).containsKey(4))
            allMonstersMode[4] = "E";
        else
            allMonstersMode[4] = getAllMonstersByPlayerName(playerNickname).get(4).getMode();
        return allMonstersMode;
    }

    public static MonsterZoneCard getMonsterCardByAddress(int address, String playerNickname) {
        return allMonsterCards.get(playerNickname).get(address);
    }

    public static Map<Integer,MonsterZoneCard> getAllMonstersByPlayerName(String playerNickname) {
        return allMonsterCards.get(playerNickname);
    }

    public static int getSumOfMonstersLevel(String playerNickname) {
        int sumOfLevels = 0;
        for (MonsterZoneCard eachCard : allMonsterCards.get(playerNickname).values())
            sumOfLevels += eachCard.getLevel();
        return sumOfLevels;
    }
}
