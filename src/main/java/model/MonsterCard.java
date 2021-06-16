package model;
import java.util.HashMap;


public class MonsterCard extends Card {
    private int level;
    private String monsterType;
    private int attack;
    private int defend;
    private String cardType;
    private boolean isScanner;
    private String attribute;
    private static HashMap<String, MonsterCard> allMonsters = new HashMap<>();


    public MonsterCard(String attribute, String name, int level, String MonsterType, int attack, int defend,
                       String cardModel, String cardType, boolean isScanner, String description, int price) {
        super(name, cardModel, description, price);
        this.attack = attack;
        this.level = level;
        this.defend = defend;
        this.monsterType = MonsterType;
        this.cardType = cardType;
        this.isScanner = isScanner;
        this.attribute = attribute;
        allMonsters.put(name, this);
    }


    public String getAttribute() {
        return attribute;
    }

    public String getCardType() {
        return cardType;
    }//Normal

//    //public void addScanner() {
//        allMonsters.put("Scanner", this);
//    }

    public int getLevel() {
        return level;
    }

    public String getMonsterType() {
        return monsterType;
    }//Fair

    public int getAttack() {
        return attack;
    }

    public int getDefend() {
        return defend;
    }

    public boolean isEpic() {
        return cardName.equals("Command knight");
    }

    public boolean isRitual() {
        return cardType.equals("Ritual");
    }

    public boolean isScanner() {
        return isScanner;
    }

    public static MonsterCard getMonsterByName(String name) {
        return allMonsters.get(name);
    }

    public static HashMap<String, MonsterCard> getAllMonsters() {
        return allMonsters;
    }

    public void setAttack(int change) {
        this.attack += change;
    }


}
