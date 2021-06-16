package model;

import controller.SetCards;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class GameMatModelTest {

    @Test
    public void startNewGame() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        Assert.assertEquals(1, GameMatModel.getPlayerGameMat().size());
    }

    @Test
    public void getPhase() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        Assert.assertEquals(Phase.Draw_Phase, gameMatModel.getPhase());
    }

    @Test
    public void setPhase() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.setPhase(Phase.End_Phase);
        Assert.assertEquals(Phase.End_Phase, gameMatModel.getPhase());
    }

    @Test
    public void addToGraveyard() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToGraveyard("yami");
        Assert.assertEquals("yami", gameMatModel.getGraveyard().get(0));
    }

    @Test
    public void getDeadCardNameByAddress() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToGraveyard("yami");
        assertNull(gameMatModel.getDeadCardNameByAddress(12));
        Assert.assertEquals("yami", gameMatModel.getDeadCardNameByAddress(0));
    }


    @Test
    public void removeFromGraveyardByAddress() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToGraveyard("yami");
        gameMatModel.removeFromGraveyardByAddress(0);
        Assert.assertEquals(0, gameMatModel.getGraveyard().size());


    }

    @Test
    public void getKindOfDeadCardByAddress() {

        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel.addToGraveyard("Yami");
        Assert.assertEquals("Spell", gameMatModel.getKindOfDeadCardByAddress(0));
        assertNull(gameMatModel.getKindOfDeadCardByAddress(-231));

    }

    @Test
    public void getNameOfDeadCardByAddress() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToGraveyard("Yami");
        Assert.assertEquals("Yami", gameMatModel.getDeadCardNameByAddress(0));
        assertNull(gameMatModel.getDeadCardNameByAddress(-231));
    }

    @Test
    public void getNumberOfDeadCards() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel.addToGraveyard("Yami");
        Assert.assertEquals(1, gameMatModel.getNumberOfDeadCards());


    }

    @Test
    public void getNumberOfDeadCardByModel() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel.addToGraveyard("Yami");
        Assert.assertEquals(1, gameMatModel.getNumberOfDeadCardByModel("Spell"));
        Assert.assertEquals(0, gameMatModel.getNumberOfDeadCardByModel("Trap"));

    }

    @Test
    public void doesThisModelAndTypeExist() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        Assert.assertFalse(gameMatModel.doesThisModelAndTypeExist("Spell", "Normal"));
        Assert.assertFalse(gameMatModel.doesThisModelAndTypeExist("Monster", "Warrior"));
        gameMatModel.addToGraveyard("Yami");
        gameMatModel.addToGraveyard("Axe Raider");


        Assert.assertFalse(gameMatModel.doesThisModelAndTypeExist("Spell", "Normal"));
        Assert.assertFalse(gameMatModel.doesThisModelAndTypeExist("Spell", "Effect"));
        Assert.assertTrue(gameMatModel.doesThisModelAndTypeExist("Monster", "Warrior"));
        gameMatModel.addToGraveyard("Monster Reborn");
        Assert.assertTrue(gameMatModel.doesThisModelAndTypeExist("Spell", "Normal"));
    }

    @Test
    public void doesAddressAndTypeMatch() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel.addToGraveyard("Axe Raider");
        Assert.assertFalse(gameMatModel.doesAddressAndTypeMatch(0, "Spell", "Normal"));
        Assert.assertTrue(gameMatModel.doesAddressAndTypeMatch(0, "Monster", "Warrior"));

    }

    @Test
    public void isAnySevenLevelMonsterInGraveyard() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        gameMatModel.addToGraveyard("Axe Raider");
        Assert.assertFalse(gameMatModel.isAnySevenLevelMonsterInGraveyard());
        gameMatModel.addToGraveyard("Slot Machine");
        Assert.assertTrue(gameMatModel.isAnySevenLevelMonsterInGraveyard());
    }

    @Test
    public void showGraveyard() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        gameMatModel.showGraveyard();
        Assert.assertEquals("Graveyard Empty", outContentWithOutEnter(outContent));
        gameMatModel.addToGraveyard("Axe Raider");
        outContent.reset();
        gameMatModel.showGraveyard();
        Assert.assertEquals("1. Axe Raider : An axe-wielding monster of tremendous strength and agility.", outContentWithOutEnter(outContent));
    }

    @Test
    public void getFieldZone() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        Assert.assertEquals("",gameMatModel.getFieldZone());
    }

    @Test
    public void addToFieldZone() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToFieldZone("card","H");
        Assert.assertEquals("card/H",gameMatModel.getFieldZone());
    }

    @Test
    public void changeModeOfFieldCard() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToFieldZone("card","H");
        gameMatModel.changeModeOfFieldCard("O");
        Assert.assertEquals("card/O",gameMatModel.getFieldZone());
    }

    @Test
    public void removeFromFieldZone() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.addToFieldZone("card","H");
        gameMatModel.removeFromFieldZone();
        Assert.assertEquals("",gameMatModel.getFieldZone());

    }

    @Test
    public void getNumberOfDeadMonsterThisTurn() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        Assert.assertEquals(0,gameMatModel.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void changeNumberOfDeadMonsterThisTurn() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.changeNumberOfDeadMonsterThisTurn();
        Assert.assertEquals(1,gameMatModel.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void resetNumberOfDeadMonsterThisTurn() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        gameMatModel.startNewGame();
        gameMatModel.changeNumberOfDeadMonsterThisTurn();
        gameMatModel.resetNumberOfDeadMonsterThisTurn();
        Assert.assertEquals(0,gameMatModel.getNumberOfDeadMonsterThisTurn());
    }

    @Test
    public void getGameMatByNickname() {
        GameMatModel gameMatModel = new GameMatModel("n1");
        Assert.assertEquals(gameMatModel,GameMatModel.getGameMatByNickname("n1"));
    }

    public String outContentWithOutEnter(ByteArrayOutputStream outContent) {
        return outContent.toString().substring(0, outContent.toString().length() - 2);
    }
    @Test
    public void getGraveyard() {
    }

    @Test
    public void getPlayerGameMat() {
    }

    @Test
    public void isAnyMonsterInGraveyard() {
    }

    @Test
    public void removeDeadCardByName() {
    }


    @Test
    public void doesThisMonsterExistInGraveyard() {
    }

}