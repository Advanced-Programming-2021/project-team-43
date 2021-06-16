package model;

import controller.MainMenuController;
import controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpellTrapZoneCardTest {

    @Before
    public void before() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        DeckModel deckModel = new DeckModel("deck");
        new UserModel("u", "p", "n");
        MainMenuController.username = "u";
        for (int i = 0; i < 5; i++) {
            deckModel.addCardToMain("Yami");
            deckModel.addCardToMain("Trap Hole");
            deckModel.addCardToMain("Suijin");
            deckModel.addCardToMain("Magic Jamamer");

        }
        new Player("n", deckModel, true, 1);


        DeckModel deckModel1 = new DeckModel("deck");
        new UserModel("u1", "p", "n1");
        MainMenuController.username = "u1";
        for (int i = 0; i < 5; i++) {
            deckModel1.addCardToMain("Yami");
            deckModel1.addCardToMain("Trap Hole");
            deckModel1.addCardToMain("Twin Twisters");

        }
        new Player("n1", deckModel1, false, 1);

    }


    @Test
    public void getSpellTrapName() {

        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        SpellTrapZoneCard spellTrapZoneCard1 = new SpellTrapZoneCard("n", "Trap Hole", "H");

        assertEquals("Yami", spellTrapZoneCard.getSpellTrapName());
        assertEquals("Trap Hole", spellTrapZoneCard1.getSpellTrapName());

    }

    @Test
    public void getMode() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals("H", spellTrapZoneCard.getMode());

    }

    @Test
    public void setMode() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        spellTrapZoneCard.setMode("O");
        assertEquals("O", spellTrapZoneCard.getMode());
    }

    @Test
    public void getKind() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals("Spell", spellTrapZoneCard.getKind());
    }

    @Test
    public void getIcon() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals("Field", spellTrapZoneCard.getIcon());
    }

    @Test
    public void getAddress() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals(1, spellTrapZoneCard.getAddress());
    }

    @Test
    public void getTurnCounter() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals(0, spellTrapZoneCard.getTurnCounter());
    }

    @Test
    public void setTurnCounter() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        spellTrapZoneCard.setTurnCounter(5);
        assertEquals(5, spellTrapZoneCard.getTurnCounter());
    }

    @Test
    public void changeTurnCounter() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        spellTrapZoneCard.setTurnCounter(5);
        spellTrapZoneCard.changeTurnCounter();
        assertEquals(6, spellTrapZoneCard.getTurnCounter());
    }

    @Test
    public void getIsSetInThisTurn() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertFalse(spellTrapZoneCard.getIsSetInThisTurn());
    }

    @Test
    public void setIsSetInThisTurn() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        spellTrapZoneCard.setIsSetInThisTurn(true);
        assertTrue(spellTrapZoneCard.getIsSetInThisTurn());
    }

    @Test
    public void getNumberOfFullHouse() {
        assertEquals(0, SpellTrapZoneCard.getNumberOfFullHouse("n"));
        new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals(1, SpellTrapZoneCard.getNumberOfFullHouse("n"));

    }

    @Test
    public void getRelatedMonsterAddress() {
        assertEquals(0, SpellTrapZoneCard.getNumberOfFullHouse("n"));
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals(0, spellTrapZoneCard.getRelatedMonsterAddress().size());
    }

    @Test
    public void setRelatedMonsterAddress() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        Assert.assertEquals(0, spellTrapZoneCard.getRelatedMonsterAddress().size());
        spellTrapZoneCard.setRelatedMonsterAddress("Suijin", 1);
        Assert.assertEquals(1, spellTrapZoneCard.getRelatedMonsterAddress().size());


    }

    @Test
    public void removeSpellTrapFromZone() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");
        spellTrapZoneCard.removeSpellTrapFromZone();
        assertEquals(0, SpellTrapZoneCard.getNumberOfFullHouse("n"));
    }



    @Test
    public void doesThisCardNameExist() {
        Assert.assertEquals(-1, SpellTrapZoneCard.doesThisCardNameExist("n", "Magic Cylinder"));
        new SpellTrapZoneCard("n", "Magic Cylinder", "H");
        Assert.assertEquals(1, SpellTrapZoneCard.doesThisCardNameExist("n", "Magic Cylinder"));

    }

    @Test
    public void getAddressOfSpellByIcon() {
        Assert.assertEquals(0, SpellTrapZoneCard.getAddressOfSpellByIcon("n", "Normal", "Magic Cylinder"));
        new SpellTrapZoneCard("n", "Magic Cylinder", "H");
        Assert.assertEquals(1, SpellTrapZoneCard.getAddressOfSpellByIcon("n", "Normal", "Magic Cylinder"));
    }

    @Test
    public void isThisTrapActivated() {

        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Magic Jamamer", "H");

        assertEquals(-1,SpellTrapZoneCard.isThisTrapActivated("n", "Magic Jamamer"));
        spellTrapZoneCard.setMode("O");
        assertEquals(1,SpellTrapZoneCard.isThisTrapActivated("n", "Magic Jamamer"));
    }

    @Test
    public void isThisSpellActivated() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Yami", "H");

        assertEquals(-1,SpellTrapZoneCard.isThisSpellActivated("n", "Yami"));
        spellTrapZoneCard.setMode("O");
        assertEquals(1,SpellTrapZoneCard.isThisSpellActivated("n", "Yami"));

    }

    @Test
    public void getAllSpellTrapMode() {
        Assert.assertEquals(6,SpellTrapZoneCard.getAllSpellTrapMode("n").length);

    }

    @Test
    public void getSpellCardByAddress() {
        assertNull(SpellTrapZoneCard.getSpellCardByAddress(1, "n"));
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n","Yami","O");
        Assert.assertEquals(spellTrapZoneCard,SpellTrapZoneCard.getSpellCardByAddress(1,"n"));
    }


    @Test
    public void getEffectStack() {
        Assert.assertEquals(0,SpellTrapZoneCard.getEffectStack().size());
    }



    @Test
    public void getNewSpellAddress() {
    }

    @Test
    public void getAddressOfQuickSpellByName() {
    }

    @Test
    public void getAddressOfSetTrap() {
    }

    @Test
    public void doesAddressAndTrapNameMatch() {
    }


    @Test
    public void getAllSpellTrapByPlayerName() {
    }

    @Test
    public void changeTurn() {
    }
}