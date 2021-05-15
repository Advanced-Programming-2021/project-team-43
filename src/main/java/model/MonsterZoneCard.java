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
    private final String monsterType;
    private boolean isScanner;
    private boolean haveChangedPositionThisTurn;//call it change turn
    private boolean haveAttackThisTurn;//call it change turn
    private boolean canAttack;
    private boolean canAttackToThisMonster;
    private boolean isEffectUsed;//change turn false
    private int numberOfFullHouse = 1;
    private boolean isForOneTurn;
    private final Map<String, List<Integer>> allEffectiveSpell = new HashMap<>();
    public static final Map<String, Map<Integer, MonsterZoneCard>> allMonsterCards = new HashMap<>();


    public MonsterZoneCard(String playerNickname, String monsterName, String mode, boolean isScanner, boolean isForOneTurn) {
        this.playerNickname = playerNickname;
        this.monsterName = monsterName;
        this.mode = mode;
        this.address = allMonsterCards.get(playerNickname).size() + 1;
        MonsterCard monster = MonsterCard.getMonsterByName(monsterName);
        this.attack = monster.getAttack();
        this.defend = monster.getDefend();
        this.level = monster.getLevel();
        this.monsterType = monster.getMonsterType();
        this.isScanner = isScanner;
        this.haveChangedPositionThisTurn = true;
        this.canAttack = true;
        this.canAttackToThisMonster = true;
        this.isForOneTurn = isForOneTurn;
        allMonsterCards.get(playerNickname).put(address, this);
    }

    public String getMonsterName() {
        return monsterName;
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

    public void changeDefend(int defend) {
        this.defend += defend;
    }

    public int getLevel() {
        return level;
    }

    public String getMonsterType() {
        return monsterType;
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

    public boolean getIsForOneTurn() {
        return isForOneTurn;
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        if (allMonsterCards.get(playerNickname) != null) {
            return allMonsterCards.get(playerNickname).size();
        }
        else
            return 0;
    }

    public List<Integer> getAllEffectedMonster(String playerNickname) {
        return allEffectiveSpell.get(playerNickname);
    }

    public void setAllEffectedMonster(String playerNickname, List<Integer> eachMonster) {
        allEffectiveSpell.put(playerNickname, eachMonster);
    }

    public void changeNumberOfFullHouse(int amount) {
        numberOfFullHouse += amount;
    }

    public static void removeMonsterFromZone(String playerNickname, int address) {
        GameMatModel.getGameMatByNickname(playerNickname).addToGraveyard(allMonsterCards.get(playerNickname).get(address).getMonsterName());
        allMonsterCards.get(playerNickname).remove(address);
        //changeNumberOfFullHouse(-1);
    }

    public static void changeOneTurnMonstersIsEffectUsed(String playerNickname) {
        String cardName;
        for (int i = 1; i < 6; i++) {
            if (allMonsterCards.get(playerNickname).get(i) != null) {
                cardName = allMonsterCards.get(playerNickname).get(i).getMonsterName();
                if (cardName.equals("Texchanger") || cardName.equals("Herald of Creation"))
                    allMonsterCards.get(playerNickname).get(i).setIsEffectUsed(false);
            }
        }
    }


    public static int getAddressByMonsterName(String playerNickname, String monsterName) {
        for (int i = 1; i < 6; i++)
            if (allMonsterCards.get(playerNickname).get(i) != null)
                if (allMonsterCards.get(playerNickname).get(i).getMonsterName().equals(monsterName))
                    return allMonsterCards.get(playerNickname).get(i).getAddress();
        return 0;
    }

    @Override
    public String toString() {
        return "Name: " + monsterName + "\n" +
                "Level: " + MonsterCard.getMonsterByName(monsterName).getLevel() + "\n" +
                "Type: " + MonsterCard.getMonsterByName(monsterName).getMonsterType() + "\n" +
                "ATK: " + attack + "\n" +
                "DEF: " + defend + "\n" +
                "Description: " + MonsterCard.getMonsterByName(monsterName).getDescription();
    }

    public static String[] getAllMonstersMode(String playerNickname) {
        String[] allMonstersMode = new String[6];
        if (getAllMonstersByPlayerName(playerNickname) == null)
            for (int i = 1; i < 6; i++)
                allMonstersMode[i] = "E";
        else {
            for (int i = 1; i < 6; i++) {
                if (!getAllMonstersByPlayerName(playerNickname).containsKey(i))
                    allMonstersMode[i] = "E";
                else
                    allMonstersMode[i] = getAllMonstersByPlayerName(playerNickname).get(i).getMode();
            }
        }
        return allMonstersMode;
    }

    public static boolean isThisMonsterTypeExisted(String monsterType, String playerNickname) {
        for (int i = 1; i < 6; i++)
            if (allMonsterCards.get(playerNickname).get(i) != null)
                if (allMonsterCards.get(playerNickname).get(i).getMonsterType().equals(monsterType))
                    return true;
        return false;

    }

    public static void removeUselessMonster(String playerNickname) {
        for (int i = 1; i < 6; i++)
            if (allMonsterCards.get(playerNickname).get(i) != null && allMonsterCards.get(playerNickname).get(i).getIsForOneTurn())
                removeMonsterFromZone(playerNickname, i);
    }

    public static MonsterZoneCard getMonsterCardByAddress(int address, String playerNickname) {
        return allMonsterCards.get(playerNickname).get(address);
    }

    public static Map<Integer, MonsterZoneCard> getAllMonstersByPlayerName(String playerNickname) {
        return allMonsterCards.get(playerNickname);
    }

    public static int getSumOfMonstersLevel(String playerNickname) {
        int sumOfLevels = 0;
        for (MonsterZoneCard eachCard : allMonsterCards.get(playerNickname).values())
            sumOfLevels += eachCard.getLevel();
        return sumOfLevels;
    }

    public static void changeTurn(String playerNickname) {
        Map<Integer, MonsterZoneCard> allMonsters = new HashMap<>(allMonsterCards.get(playerNickname));
        for (int i = 1; i < 6; i++) {
            if (allMonsters.get(i) != null) {
                allMonsters.get(i).setHaveChangedPositionThisTurn(false);
                allMonsters.get(i).setHaveAttackThisTurn(false);
            }
        }
    }

}
