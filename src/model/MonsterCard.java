package model;

import java.util.HashMap;

public class MonsterCard extends Card {
    private String name;
    private int level;
    private String monsterType;
    private int attack;
    private int defend;
    private int shield;
    private static boolean isEpic;
    private static boolean isRitual;
    private boolean isScanner;
    private String description;
    private static HashMap<String, MonsterCard> allMonsters = new HashMap<>();


    public MonsterCard(String cardSide, String cardLocation, String backAndForth, String attribute, String name, int level, String type, int attack, int defend, String description,
                       String cardModel, int cardNumber, String cardOwner, int shield, boolean isEpic, boolean isRitual, boolean isScanner) {
        super(name, cardSide, cardLocation, backAndForth, description, attribute, cardModel, cardNumber, cardOwner);
        this.attack = attack;
        this.name = name;
        this.level = level;
        this.defend = defend;
        this.description = description;
        this.monsterType = type;
        this.shield = shield;
        MonsterCard.isEpic = isEpic;
        MonsterCard.isRitual = isRitual;
        this.isScanner = isScanner;
        allMonsters.put(name, this);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefend() {
        return defend;
    }

    public int getShield() {
        return shield;
    }

    public boolean isEpic() {
        return isEpic;
    }

    public boolean isRitual() {
        return isRitual;
    }

    public boolean isScanner() {
        return isScanner;
    }

    public String getDescription() {
        return description;
    }

    public static MonsterCard getMonsterByName(String name) {
        return allMonsters.get(name);
    }

    public static void setEpic(boolean epic) {
        isEpic = epic;
    }

    public static void setRitual(boolean ritual) {
        isRitual = ritual;
    }

    public static HashMap<String, MonsterCard> getAllMonsters() {
        return allMonsters;
    }
    public void changeAttack(int change){
        this.attack+=change;
    }
}
