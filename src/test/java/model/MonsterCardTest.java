package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class MonsterCardTest {


    @Test
    public void getAttribute() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        String actualAttribute = MonsterCard.getMonsterByName("wild").getAttribute();
        assertEquals("EARTH", actualAttribute);
    }

    @Test
    public void getCardType() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        String actualType = MonsterCard.getMonsterByName("wild").getCardType();
        assertEquals("Normal", actualType);
    }


    @Test
    public void getLevel() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        int actualLevel = MonsterCard.getMonsterByName("wild").getLevel();
        assertEquals(4, actualLevel);
    }

    @Test
    public void getMonsterType() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        String actualMonsterType = MonsterCard.getMonsterByName("wild").getMonsterType();
        assertEquals("Fiend", actualMonsterType);
    }

    @Test
    public void getAttack() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        int actualAttack = MonsterCard.getMonsterByName("wild").getAttack();
        assertEquals(3002, actualAttack);
    }

    @Test
    public void getDefend() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        int actualDefend = MonsterCard.getMonsterByName("wild").getDefend();
        assertEquals(3100, actualDefend);
    }

    @Test
    public void isEpic() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        boolean actualEpic = MonsterCard.getMonsterByName("wild").isEpic();
        assertFalse(actualEpic);
    }

    @Test
    public void isRitual() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        boolean actualRitual = MonsterCard.getMonsterByName("wild").isRitual();
        assertFalse(actualRitual);
    }

    @Test
    public void isScanner() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        boolean actualIsScanner = MonsterCard.getMonsterByName("wild").isScanner();
        assertFalse(actualIsScanner);
    }

    @Test
    public void getMonsterByName() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        MonsterCard actualMonsterCard = MonsterCard.getMonsterByName("wild");
        assertEquals(monsterCard, actualMonsterCard);
    }

    @Test
    public void getAllMonsters() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        HashMap<String, MonsterCard> expectedMonsters = new HashMap<>();
        expectedMonsters.put("wild", monsterCard);
        HashMap<String, MonsterCard> actualMonsterCard = MonsterCard.getAllMonsters();
        assertEquals(expectedMonsters, actualMonsterCard);
    }

    @Test
    public void setAttack() {
        MonsterCard monsterCard = new MonsterCard("EARTH", "wild", 4, "Fiend", 3002, 3100, "Monster"
                , "Normal", false, "The ultimate wizard in terms of attack and defense.", 900);
        MonsterCard.getMonsterByName("wild").setAttack(200);
        int actualAttack = MonsterCard.getMonsterByName("wild").getAttack();
        assertEquals(3202, actualAttack);
    }

}