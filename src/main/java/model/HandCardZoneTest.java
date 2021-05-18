package main.java.model;

import main.java.controller.MainMenuController;
import main.java.controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class HandCardZoneTest {
    UserModel user;
    DeckModel deckModel;
    Player player;
    ArrayList<String> mainDeck;
    ArrayList<String> sideDeck = new ArrayList<>();
    HandCardZone card;

    @Before
    public void beforeAllClassTest() {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        user = new UserModel("Guy", "123", "me");
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
        card = new HandCardZone("me", "Forest");
    }

    @Test
    public void getCardName() {
        Assert.assertEquals("Forest", card.getCardName());
        Assert.assertEquals("Negate Attack", new HandCardZone("me", "Negate Attack").getCardName());
    }

    @Test
    public void getAddress() {
        Assert.assertEquals(5, card.getAddress());
        Assert.assertEquals(6, new HandCardZone("me", "Solemn Warning").getAddress());
    }

    @Test
    public void getKind() {
        Assert.assertEquals("Spell", card.getKind());
        Assert.assertEquals("Trap", new HandCardZone("me", "Solemn Warning").getKind());
    }

    @Test
    public void removeFromHandCard() {
        HandCardZone.removeFromHandCard("me", card.getAddress());
        assertNull(HandCardZone.getHandCardByAddress(5, "me"));
    }

    @Test
    public void getAddressByName() {
        int[] addresses = HandCardZone.getAddressByName("me", "Yami");
        int[] expectedAddresses = new int[5];
        for (int i = 0; i < 5; i++)
            expectedAddresses[i] = i;
        Assert.assertEquals(expectedAddresses[0], addresses[0]);
        Assert.assertEquals(expectedAddresses[1], addresses[1]);
        Assert.assertEquals(expectedAddresses[2], addresses[2]);
        Assert.assertEquals(expectedAddresses[3], addresses[3]);
        Assert.assertEquals(expectedAddresses[4], addresses[4]);
    }

    @Test
    public void getNumberOfFullHouse() {
        new HandCardZone("me", "Twin Twisters");
        Assert.assertEquals(7, HandCardZone.getNumberOfFullHouse("me"));
    }



    @Test
    public void doesThisModelAndTypeExist() {
        new HandCardZone(player.getNickname(), "Battle warrior");
        assertTrue(HandCardZone.doesThisModelAndTypeExist("me", "Monster", "Warrior"));
        new HandCardZone(player.getNickname(), "Twin Twisters");
        assertFalse(HandCardZone.doesThisModelAndTypeExist("me", "Spell", "Quick-play"));
    }





    @Test
    public void doesAnyLevelFourMonsterExisted() {
        assertFalse(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
        new HandCardZone(player.getNickname(), "Flame manipulator");
        assertTrue(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
    }

    @Test
    public void doesThisCardNameExist() {
        assertTrue(HandCardZone.doesThisCardNameExist(player.getNickname(), "Yami"));
        assertFalse(HandCardZone.doesThisCardNameExist(player.getNickname(), "Magic jammer"));
    }

    @Test
    public void showHandCard() {
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        HandCardZone.showHandCard(player.getNickname());
        assertEquals(56, show.size());
    }

    @Test
    public void getHandCardByAddress() {
        assertNull(HandCardZone.getHandCardByAddress(-1, player.getNickname()));
        assertNotNull(HandCardZone.getHandCardByAddress(3, player.getNickname()));
    }

    @Test
    public void doIHaveAnyRitualMonster() {
        Assert.assertEquals(-1, HandCardZone.doIHaveAnyRitualMonster("me"));
        HandCardZone hand = new HandCardZone("me", "Crab Turtle");
        Assert.assertEquals(6, HandCardZone.doIHaveAnyRitualMonster("me"));
    }

}