package model;

import controller.MainMenuController;
import controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {


    UserModel user;
    DeckModel deckModel;
    Player player;
    ArrayList<String> mainDeck;
    ArrayList<String> sideDeck = new ArrayList<>();

    @Before
    public void beforeAllClassTest() {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        user = new UserModel("Guy","123","me");
        MainMenuController.username = "Guy";
        deckModel = new DeckModel("myDeck");
        for (int i = 0; i < 10; i++) {
            deckModel.addCardToMain("Yami");
        }
        for (int i = 0; i < 5; i++) {
            deckModel.addCardToSide("Battle Ox");
        }
        user.addDeck(deckModel);
        player = new Player("me", deckModel, true, 3);
        mainDeck = new ArrayList<>(deckModel.getArrayMain());
        String[] cardName2 = deckModel.cardsInSideDeck.keySet().toArray(new String[0]);
        for (String eachCard : cardName2) {
            for (int i = 0; i < deckModel.cardsInSideDeck.get(eachCard); i++)
                sideDeck.add(eachCard);
        }
    }

    @Test
    public void startNewGame() {
        player.startNewGame(deckModel, true);
        Assert.assertEquals(8000, player.getLifePoint());
        assertTrue(player.getIsYourTurn());
        Assert.assertEquals(2, player.getNumberOfRound());
        assertFalse(player.getCanBattle());
        assertFalse(player.getCanDrawCard());
    }

    @Test
    public void fillTheGameDecks() {
        ArrayList<String> expectedMainDeck = new ArrayList<>();
        ArrayList<String> expectedSideDeck = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedMainDeck.add("Yami");
        for (int i = 0; i < 5; i++)
            expectedSideDeck.add("Battle Ox");
        Assert.assertEquals(expectedMainDeck, player.getPlayerDeck("main"));
        Assert.assertEquals(expectedSideDeck, player.getPlayerDeck("side"));

    }

    @Test
    public void firstDrawCard() {
        ArrayList<String> expectedDeck = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedDeck.add("Yami");
        expectedDeck.remove(0);
        player.drawCard(false);
        Assert.assertEquals(expectedDeck, player.getPlayerDeck("main"));

    }

    @Test
    public void drawCard() {
        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedList.add("Yami");
        Assert.assertEquals(expectedList, player.getPlayerDeck("main"));
    }

    @Test
    public void exchangeCard() {
        /////////////////////
    }

    @Test
    public void getLifePoint() {
        Assert.assertEquals(8000, player.getLifePoint());
    }

    @Test
    public void changeLifePoint() {
        player.changeLifePoint(-1000);
        Assert.assertEquals(7000, player.getLifePoint());
    }

    @Test
    public void getIsYourTurn() {
        assertTrue(player.getIsYourTurn());
    }

    @Test
    public void setIsYourTurn() {
        player.setIsYourTurn(false);
        assertFalse(player.getIsYourTurn());
    }

    @Test
    public void getNumberOfRound() {
        Assert.assertEquals(3, player.getNumberOfRound());
    }

    @Test
    public void getCounterOfTurn() {
        Assert.assertEquals(1, player.getCounterOfTurn());
    }

    @Test
    public void changeCounterOfTurn() {
        player.changeCounterOfTurn();
        Assert.assertEquals(2, player.getCounterOfTurn());
    }

    @Test
    public void getIsYourMoveFinished() {
        assertFalse(player.getIsYourMoveFinished());
    }

    @Test
    public void setIsYourMoveFinished() {
        player.setIsYourMoveFinished(true);
        assertTrue(player.getIsYourMoveFinished());
    }

    @Test
    public void getCanUseTrap() {
        assertTrue(player.getCanUseTrap());
    }

    @Test
    public void setCanUseTrap() {
        player.setCanUseTrap(false);
        assertFalse(player.getCanUseTrap());
    }

    @Test
    public void getCanSetSummonMonster() {
        assertTrue(player.getCanSetSummonMonster());
    }

    @Test
    public void setCanSetSummonMonster() {
        player.setCanSetSummonMonster(false);
        assertFalse(player.getCanSetSummonMonster());
    }

    @Test
    public void getNumberOfWin() {
        Assert.assertEquals(0, player.getNumberOfWin());
    }

    @Test
    public void changeNumberOfWin() {
        player.changeNumberOfWin();
        Assert.assertEquals(1, player.getNumberOfWin());
    }

    @Test
    public void getCanDrawCard() {
        assertFalse(player.getCanDrawCard());
    }

    @Test
    public void setCanDrawCard() {
        player.setCanDrawCard(true);
        assertTrue(player.getCanDrawCard());
    }

    @Test
    public void getCanBattle() {
        assertFalse(player.getCanBattle());
    }

    @Test
    public void setCanBattle() {
        player.setCanBattle(true);
        assertTrue(player.getCanBattle());
    }

    @Test
    public void getCardNameByAddress() {
        Assert.assertEquals("Yami", player.getCardNameByAddress(0));
    }

    @Test
    public void getNumberOfMainDeckCards() {
        Assert.assertEquals(5, player.getNumberOfMainDeckCards());
    }

    @Test
    public void showMainDeck() {
        ///////////////////
    }

    @Test
    public void getNumberOfSideDeckCards() {
        Assert.assertEquals(5, player.getNumberOfSideDeckCards());
    }

    @Test
    public void showSideDeck() {
        ////////////////
    }

    @Test
    public void changeTurn() {
        player.changeTurn();
        assertFalse(player.getIsYourMoveFinished());
        assertTrue(player.getCanBattle());
        assertTrue(player.getCanSetSummonMonster());
        assertTrue(player.getCanUseTrap());
    }

    @Test
    public void addToMainDeck() {
        ArrayList<String> expectedDeck = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedDeck.add("Yami");
        expectedDeck.add("Axe Raider");
        player.addToMainDeck("Axe Raider");
        Assert.assertEquals(expectedDeck, player.getPlayerDeck("main"));
    }

    @Test
    public void removeFromMainDeck() {
        ArrayList<String> expectedDeck = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedDeck.add("Yami");
        expectedDeck.remove(0);
        player.removeFromMainDeck();
        Assert.assertEquals(expectedDeck, player.getPlayerDeck("main"));
    }

    @Test
    public void removeFromMainDeckByAddress() {
        ArrayList<String> expectedDeck = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            expectedDeck.add("Yami");
        player.removeFromMainDeckByAddress(4);
        Assert.assertEquals(expectedDeck, player.getPlayerDeck("main"));
    }

    @Test
    public void addToSideDeck() {
        ////////
    }

    @Test
    public void removeFromSideDeckByAddress() {
        ArrayList<String> expectedDeck = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedDeck.add("Battle Ox");
        expectedDeck.remove(3);
        player.removeFromSideDeckByAddress(3);
        Assert.assertEquals(expectedDeck, player.getPlayerDeck("side"));
    }

    @Test
    public void getMaxLifePoints() {
        //////////////
    }

    @Test
    public void doesThisModelAndTypeExist() {
        assertTrue(player.doesThisModelAndTypeExist("Spell", "Field"));
    }

    @Test
    public void doesAddressTypeMatchInMainDeck() {
        assertTrue(player.doesAddressTypeMatchInMainDeck(1, "Spell", "Field"));
    }

    @Test
    public void getPlayerDeck() {
        mainDeck.subList(0, 5).clear();
        Assert.assertEquals(5 , player.getPlayerDeck("main").size());
    }

    @Test
    public void getPlayerByName() {
        Assert.assertEquals(player, Player.getPlayerByName("me"));
    }


    @Test
    public void getNickname() {
    }

    @Test
    public void setLifePoint() {
    }



}