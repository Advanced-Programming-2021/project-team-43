package model;

import controller.MainMenuController;
import controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpellTrapZoneCardTest {

    SpellTrapZoneCard spell1;
    SpellTrapZoneCard spell2;

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
            deckModel.addCardToMain("Magic Jammer");
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
        spell1 = new SpellTrapZoneCard("n", "Yami", "H");
        spell2 = new SpellTrapZoneCard("n", "Trap Hole", "H");;
    }


    @Test
    public void getSpellTrapName() {
        assertEquals("Yami", spell1.getSpellTrapName());
        assertEquals("Trap Hole", spell2.getSpellTrapName());
    }

    @Test
    public void getMode() {
        assertEquals("H", spell1.getMode());
    }

    @Test
    public void setMode() {
        spell1.setMode("O");
        assertEquals("O", spell1.getMode());
    }

    @Test
    public void getKind() {
        assertEquals("Spell", spell1.getKind());
    }

    @Test
    public void getIcon() {
        assertEquals("Field", spell1.getIcon());
    }

    @Test
    public void getAddress() {
        assertEquals(1, spell1.getAddress());
    }

    @Test
    public void getTurnCounter() {
        assertEquals(0, spell1.getTurnCounter());
    }

    @Test
    public void setTurnCounter() {
        spell1.setTurnCounter(5);
        assertEquals(5, spell1.getTurnCounter());
    }

    @Test
    public void changeTurnCounter() {
        spell1.setTurnCounter(5);
        spell1.changeTurnCounter();
        assertEquals(6, spell1.getTurnCounter());
    }

    @Test
    public void getIsSetInThisTurn() {
        assertFalse(spell1.getIsSetInThisTurn());
    }

    @Test
    public void setIsSetInThisTurn() {
        spell1.setIsSetInThisTurn(true);
        assertTrue(spell1.getIsSetInThisTurn());
    }

    @Test
    public void getNumberOfFullHouse() {
        assertEquals(2, SpellTrapZoneCard.getNumberOfFullHouse("n"));
        new SpellTrapZoneCard("n", "Yami", "H");
        assertEquals(3, SpellTrapZoneCard.getNumberOfFullHouse("n"));
    }

    @Test
    public void getRelatedMonsterAddress() {
        assertEquals(0, spell1.getRelatedMonsterAddress().size());
    }

    @Test
    public void setRelatedMonsterAddress() {
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Black Pendant", "H");
        Assert.assertEquals(0, spellTrapZoneCard.getRelatedMonsterAddress().size());
        spellTrapZoneCard.setRelatedMonsterAddress("Suijin", 1);
        Assert.assertEquals(1, spellTrapZoneCard.getRelatedMonsterAddress().size());
    }

    @Test
    public void removeSpellTrapFromZone() {
        spell1.removeSpellTrapFromZone();
        assertEquals(1, SpellTrapZoneCard.getNumberOfFullHouse("n"));
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
        SpellTrapZoneCard spellTrapZoneCard = new SpellTrapZoneCard("n", "Magic Jammer", "H");
        assertEquals(-1,SpellTrapZoneCard.isThisTrapActivated("n", "Magic Jammer"));
        spellTrapZoneCard.setMode("O");
        assertEquals(1,SpellTrapZoneCard.isThisTrapActivated("n", "Magic Jammer"));
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
        Assert.assertEquals("Yami",SpellTrapZoneCard.getSpellCardByAddress(1,"n").getSpellTrapName());
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