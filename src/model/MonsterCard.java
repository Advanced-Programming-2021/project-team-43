package model;

import java.util.HashMap;

public class MonsterCard {
    private  String name;
    private int level;
    private String monsterType;
    private int attack;
    private int defend;
    private int shield;
    private boolean isEpic;
    private boolean isRitual;
    private boolean isScanner;
    private String description;
    private static HashMap<String,MonsterCard> allMonsters=new HashMap<>();


    public MonsterCard (String name, int level, String type, int attack, int defend, String description,
                        int shield,boolean isEpic,boolean isRitual, boolean isScanner)
    {
        this.attack=attack;
        this.name=name;
        this.level=level;
        this.defend=defend;
        this.description=description;
        this.monsterType=type;
        this.shield=shield;
        this.isEpic=isEpic;
        this.isRitual=isRitual;
        this.isScanner=isScanner;
        allMonsters.put(name,this);
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
}
