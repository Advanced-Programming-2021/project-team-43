package model;

import controller.MainMenuController;
import controller.SetCards;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MonsterZoneCardTest {


    @Test
    public void getMonsterName() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        String nn = ee.getMonsterName();
        assertEquals("Suijin", nn);
    }

    @Test
    public void getAddress() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals(1, ee.getAddress());
    }

    @Test
    public void getMode() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals("DH", ee.getMode());
    }

    @Test
    public void setMode() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setMode("DH");
        assertEquals("DH", ee.getMode());
    }

    @Test
    public void getAttack() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals(2500, ee.getAttack());
    }

    @Test
    public void setAttack() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setAttack(2333);
        assertEquals(2333, ee.getAttack());
    }

    @Test
    public void changeAttack() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.changeAttack(300);
        assertEquals(2800, ee.getAttack());
    }

    @Test
    public void getDefend() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals(2400, ee.getDefend());

    }

    @Test
    public void changeDefend() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.changeDefend(-200);
        assertEquals(2200, ee.getDefend());
    }

    @Test
    public void getLevel() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals(7, ee.getLevel());
    }

    @Test
    public void getMonsterType() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals("Aqua", ee.getMonsterType());
    }

    @Test
    public void getHaveChangedPositionThisTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertFalse(ee.getHaveAttackThisTurn());
    }

    @Test
    public void setHaveChangedPositionThisTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setHaveChangedPositionThisTurn(true);
        assertTrue(ee.getHaveChangedPositionThisTurn());
    }

    @Test
    public void getHaveAttackThisTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertFalse(ee.getHaveAttackThisTurn());
    }

    @Test
    public void setHaveAttackThisTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setHaveAttackThisTurn(true);
        assertTrue(ee.getHaveAttackThisTurn());
    }

    @Test
    public void getCanAttack() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertTrue(ee.getCanAttack());
    }

    @Test
    public void setCanAttack() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setCanAttack(false);
        assertFalse(ee.getCanAttack());
    }

    @Test
    public void getCanAttackToThisMonster() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertTrue(ee.getCanAttackToThisMonster());
    }

    @Test
    public void setCanAttackToThisMonster() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setCanAttackToThisMonster(true);
        assertTrue(ee.getCanAttackToThisMonster());
    }

    @Test
    public void getIsEffectUsed() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertFalse(ee.getIsEffectUsed());
    }

    @Test
    public void setIsEffectUsed() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        ee.setIsEffectUsed(true);
        assertTrue(ee.getIsEffectUsed());
    }

    @Test
    public void getIsForOneTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertFalse(ee.getIsForOneTurn());
    }

    @Test
    public void getNumberOfFullHouse() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        assertEquals(1, MonsterZoneCard.getNumberOfFullHouse("roya"));
        assertEquals(0, MonsterZoneCard.getNumberOfFullHouse("roooya"));
    }

    @Test
    public void getAllEffectedMonster() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
//        List<Integer> ce ;
        assertEquals(0, ee.getAllEffectedMonster("roya").size());
    }

    @Test
    public void setAllEffectedMonster() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        List<Integer> ce = new ArrayList<>();
        ce.add(2);
        ee.setAllEffectedMonster("roya", ce);
        assertEquals(ce, ee.getAllEffectedMonster("roya"));
    }


    @Test
    public void removeMonsterFromZone() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Suijin");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Suijin", "DH", false, false);
        MonsterZoneCard.getMonsterCardByAddress(1,"roya").removeMonsterFromZone();
        assertNull(MonsterZoneCard.getAllMonstersByPlayerName("roya").get(1));
    }

    @Test
    public void changeOneTurnMonstersIsEffectUsed() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        MonsterZoneCard.changeOneTurnMonstersIsEffectUsed("roya");
        assertFalse(ee.getIsEffectUsed());
    }

    @Test
    public void getAddressByMonsterName() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(1, MonsterZoneCard.getAddressByMonsterName("roya", "Texchanger"));
    }

    @Test
    public void testToString() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(234, se.toString().length());

    }

    @Test
    public void getAllMonstersMode() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        Assert.assertEquals(6, MonsterZoneCard.getAllMonstersMode("roya").length);
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        Assert.assertEquals(6, MonsterZoneCard.getAllMonstersMode("roya").length);
    }

    @Test
    public void isThisMonsterTypeExisted() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertFalse(MonsterZoneCard.isThisMonsterTypeExisted("Aqua", "roya"));
    }

    @Test
    public void removeUselessMonster() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        MonsterZoneCard.removeUselessMonster("roya");
        assertEquals(234, MonsterZoneCard.getAllMonstersByPlayerName("roya").get(se.getAddress()).toString().length());
    }

    @Test
    public void getMonsterCardByAddress() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(se, MonsterZoneCard.getMonsterCardByAddress(se.getAddress(), "roya"));
    }

    @Test
    public void getAllMonstersByPlayerName() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        HashMap<Integer, MonsterZoneCard> allCard = new HashMap<>();
        allCard.put(1, se);
        assertEquals(se, MonsterZoneCard.getMonsterCardByAddress(se.getAddress(), "roya"));
    }

    @Test
    public void getSumOfMonstersLevel() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);

        assertEquals(1, MonsterZoneCard.getSumOfMonstersLevel("roya"));
    }

    @Test
    public void changeTurn() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        MonsterZoneCard.changeTurn("roya");
        assertFalse(se.getHaveChangedPositionThisTurn());
        assertFalse(se.getHaveAttackThisTurn());
    }


    @Test
    public void getIsScanner() {
    }








    @Test
    public void changeTheMonsterFace() {
    }

    @Test
    public void getAddressOfScanner() {
    }

    @Test
    public void getNewMonsterAddress() {
    }

}