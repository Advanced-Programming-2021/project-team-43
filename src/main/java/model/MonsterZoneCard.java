package main.java.model;

import java.util.*;

public class MonsterZoneCard {

    private String playerNickname;
    private String monsterName;
    private int address;
    private String mode; //monsters : summon , set , defend
    private int level;
    private int attack;
    private int defend;
    private boolean isSelected;
    private boolean haveChangedPositionThisTurn;
    private static int numberOfFullHouse = 0;
    private static List<MonsterZoneCard> allMonsterZoneCard = new ArrayList<>();

    public MonsterZoneCard(String playerNickname, String monsterName, int address, String mode) {
        this.playerNickname = playerNickname;
        this.monsterName = monsterName;
        this.address = address;
        this.mode = mode;
        numberOfFullHouse++;
        allMonsterZoneCard.add(this);
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

    public int getLevel() {
        return level;
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

    public boolean getHaveChangedPositionThisTurn() {
        return haveChangedPositionThisTurn;
    }

    public void setHaveChangedPositionThisTurn(boolean haveChangedPositionThisTurn) {
        this.haveChangedPositionThisTurn = haveChangedPositionThisTurn;
    }


    public static int getNumberOfFullHouse() {
        return numberOfFullHouse;
    }

    public static void changeNumberOfFullHouse(int amount) {
        numberOfFullHouse += amount;
    }

    public static boolean isAnyMonsterSelected() {
        for (MonsterZoneCard eachCard: allMonsterZoneCard)
            if (eachCard.isSelected)
                return true;
        return false;
    }

    public static MonsterZoneCard getMonsterCardByName(String monsterName, String playerNickname) {
        for (MonsterZoneCard eachCard: allMonsterZoneCard)
            if (eachCard.monsterName.equals(monsterName) && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

    public static MonsterZoneCard getMonsterCardByAddress(int address, String playerNickname) {
        for (MonsterZoneCard eachCard: allMonsterZoneCard)
            if (eachCard.address == address && eachCard.playerNickname.equals(playerNickname))
                return eachCard;
        return null;
    }

}
