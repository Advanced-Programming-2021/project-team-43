package model;

import controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class GameMatModelTest {

    GameMatModel gameMatModel1;


    @Before
    public void beforeAll() {
        gameMatModel1 = new GameMatModel("n1");
    }


    @Test
    public void startNewGame() {
        //Assert.assertEquals(2, GameMatModel.getPlayerGameMat().size());
    }

    @Test
    public void getPhase() {
        Assert.assertEquals(Phase.Draw_Phase, gameMatModel1.getPhase());
    }

    @Test
    public void setPhase() {
        gameMatModel1.setPhase(Phase.End_Phase);
        Assert.assertEquals(Phase.End_Phase, gameMatModel1.getPhase());
    }

    @Test
    public void addToGraveyard() {
        gameMatModel1.addToGraveyard("yami");
        Assert.assertEquals("yami", gameMatModel1.getGraveyard().get(0));
    }

    @Test
    public void getDeadCardNameByAddress() {
        gameMatModel1.addToGraveyard("Monster Reborn");
        assertNull(gameMatModel1.getDeadCardNameByAddress(1));
        Assert.assertEquals("Monster Reborn", gameMatModel1.getDeadCardNameByAddress(0));
    }

    @Test
    public void removeFromGraveyardByAddress() {
        gameMatModel1.addToGraveyard("Monster Reborn");
        gameMatModel1.removeFromGraveyardByAddress(0);
        Assert.assertEquals(0, gameMatModel1.getGraveyard().size());
    }

    @Test
    public void getKindOfDeadCardByAddress() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel1.addToGraveyard("Monster Reborn");
        Assert.assertEquals("Spell", gameMatModel1.getKindOfDeadCardByAddress(0));
        assertNull(gameMatModel1.getKindOfDeadCardByAddress(-1));
    }

    @Test
    public void getNameOfDeadCardByAddress() {
        gameMatModel1.addToGraveyard("Monster Reborn");
        Assert.assertEquals("Monster Reborn", gameMatModel1.getDeadCardNameByAddress(0));
        assertNull(gameMatModel1.getDeadCardNameByAddress(-2));
    }

    @Test
    public void getNumberOfDeadCards() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel1.addToGraveyard("Monster Reborn");
        Assert.assertEquals(1, gameMatModel1.getNumberOfDeadCards());
    }

    @Test
    public void getNumberOfDeadCardByModel() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel1.addToGraveyard("Monster Reborn");
        Assert.assertEquals(1, gameMatModel1.getNumberOfDeadCardByModel("Spell"));
    }

    @Test
    public void doesThisModelAndTypeExist() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        Assert.assertFalse(gameMatModel1.doesThisModelAndTypeExist("Spell", "Normal"));
        Assert.assertFalse(gameMatModel1.doesThisModelAndTypeExist("Monster", "Warrior"));
        gameMatModel1.addToGraveyard("Yami");
        gameMatModel1.addToGraveyard("Axe Raider");
        Assert.assertFalse(gameMatModel1.doesThisModelAndTypeExist("Spell", "Normal"));
        Assert.assertFalse(gameMatModel1.doesThisModelAndTypeExist("Spell", "Effect"));
        Assert.assertTrue(gameMatModel1.doesThisModelAndTypeExist("Monster", "Warrior"));
        gameMatModel1.addToGraveyard("Monster Reborn");
        Assert.assertTrue(gameMatModel1.doesThisModelAndTypeExist("Spell", "Normal"));
    }

    @Test
    public void doesAddressAndTypeMatch() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel1.addToGraveyard("Axe Raider");
        Assert.assertFalse(gameMatModel1.doesAddressAndTypeMatch(0, "Spell", "Normal"));
        Assert.assertTrue(gameMatModel1.doesAddressAndTypeMatch(0, "Monster", "Warrior"));
    }

    @Test
    public void isAnySevenLevelMonsterInGraveyard() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel1.addToGraveyard("Axe Raider");
        Assert.assertFalse(gameMatModel1.isAnySevenLevelMonsterInGraveyard());
        gameMatModel1.addToGraveyard("Slot Machine");
        Assert.assertTrue(gameMatModel1.isAnySevenLevelMonsterInGraveyard());
    }

    @Test
    public void showGraveyard() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        gameMatModel1.showGraveyard();
        Assert.assertEquals("Graveyard Empty", outContentWithOutEnter(outContent));
        gameMatModel1.addToGraveyard("Axe Raider");
        outContent.reset();
        gameMatModel1.showGraveyard();
        Assert.assertEquals("1. Axe Raider : An axe-wielding monster of tremendous strength and agility.", outContentWithOutEnter(outContent));
    }

    @Test
    public void getFieldZone() {
        Assert.assertEquals("",gameMatModel1.getFieldZone());
    }

    @Test
    public void addToFieldZone() {
        gameMatModel1.addToFieldZone("card","H");
        Assert.assertEquals("card/H",gameMatModel1.getFieldZone());
    }

    @Test
    public void changeModeOfFieldCard() {
        gameMatModel1.addToFieldZone("card","H");
        gameMatModel1.changeModeOfFieldCard("O");
        Assert.assertEquals("card/O",gameMatModel1.getFieldZone());
    }

    @Test
    public void removeFromFieldZone() {
        gameMatModel1.addToFieldZone("card","H");
        gameMatModel1.removeFromFieldZone();
        Assert.assertEquals("",gameMatModel1.getFieldZone());
    }

    @Test
    public void getNumberOfDeadMonsterThisTurn() {
        Assert.assertEquals(0,gameMatModel1.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void changeNumberOfDeadMonsterThisTurn() {
        gameMatModel1.changeNumberOfDeadMonsterThisTurn();
        Assert.assertEquals(1,gameMatModel1.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void resetNumberOfDeadMonsterThisTurn() {
        gameMatModel1.changeNumberOfDeadMonsterThisTurn();
        gameMatModel1.resetNumberOfDeadMonsterThisTurn();
        Assert.assertEquals(0,gameMatModel1.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void getGameMatByNickname() {
        Assert.assertEquals(gameMatModel1,GameMatModel.getGameMatByNickname("n1"));
    }

    public String outContentWithOutEnter(ByteArrayOutputStream outContent) {
        return outContent.toString().substring(0, outContent.toString().length() - 2);
    }

    @Test
    public void doesThisMonsterExistInGraveyard() {

//        gameMatModel1.addToGraveyard("Exploder Dragon");
//        System.out.println(gameMatModel1.graveyard.get(0));
//        assertTrue(gameMatModel1.doesThisMonsterExistInGraveyard("Exploder Dragon"));
    }

}