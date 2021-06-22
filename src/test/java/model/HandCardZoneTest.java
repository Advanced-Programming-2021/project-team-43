package model;

import controller.MainMenuController;
import controller.SetCards;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class HandCardZoneTest {

    UserModel user;
    DeckModel deckModel;
    Player player;
    ArrayList<String> mainDeck;
    HandCardZone card;

    @Before
    public void beforeAllClassTest() {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        user = new UserModel("Guy", "123", "me");
        MainMenuController.username = "Guy";
        deckModel = new DeckModel("myDeck");
        for (int i = 0; i < 10; i++)
            deckModel.addCardToMain("Yami");
        for (int i = 0; i < 5; i++)
            deckModel.addCardToSide("Battle Ox");
        user.addDeck(deckModel);
        player = new Player("me", deckModel, true, 3);
        mainDeck = new ArrayList<>(deckModel.getArrayMain());
        card = new HandCardZone("me", "The Tricky");
    }

    @Test
    public void getCardName() {
        Assert.assertEquals("The Tricky", card.getCardName());
        Assert.assertEquals("Negate Attack", new HandCardZone("me", "Negate Attack").getCardName());
    }

    @Test
    public void getAddress() {
        Assert.assertEquals(5, card.getAddress());
        Assert.assertEquals(6, new HandCardZone("me", "Solemn Warning").getAddress());
    }

    @Test
    public void getKind() {
        Assert.assertEquals("Monster", card.getKind());
        Assert.assertEquals("Trap", new HandCardZone("me", "Solemn Warning").getKind());
    }

    @Test
    public void removeFromHandCard() {
        HandCardZone.getHandCardByAddress(card.getAddress(),"me").removeFromHandCard();
        assertEquals(5,HandCardZone.getNumberOfFullHouse("me"));
    }

    @Test
    public void getNumberOfFullHouse() {
        new HandCardZone("me", "Twin Twisters");
        Assert.assertEquals(7, HandCardZone.getNumberOfFullHouse("me"));
    }

    @Test
    public void doesAnyLevelFourMonsterExisted() {
        assertFalse(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
        HandCardZone hand = new HandCardZone(player.getNickname(), "Flame manipulator");
        assertTrue(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
        hand.removeFromHandCard();
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
        HandCardZone.showHandCard("me");
        assertEquals(60, show.size());
    }

    @Test
    public void getHandCardByAddress() {
        assertNull(HandCardZone.getHandCardByAddress(-1, player.getNickname()));
        assertEquals("Yami", HandCardZone.getHandCardByAddress(3, player.getNickname()).getCardName());
    }

    @Test
    public void doIHaveAnyRitualMonster() {
        Assert.assertEquals(-1, HandCardZone.doIHaveAnyRitualMonster("me"));
        HandCardZone hand = new HandCardZone("me", "Crab Turtle");
        Assert.assertEquals(7, HandCardZone.doIHaveAnyRitualMonster("me"));/////////////////
        hand.removeFromHandCard();
    }

    @Test
    public void removeAllTypeCard() {
        HandCardZone.removeAllTypeCard("me", "Yami");
        Assert.assertEquals(3, HandCardZone.getNumberOfFullHouse("me"));
    }

    @Test
    public void getAddressOfCybreseMonster() {
        HandCardZone hand = new HandCardZone("me","Texchanger");
        Assert.assertEquals(6, HandCardZone.getAddressOfCybreseMonster("me"));
        hand.removeFromHandCard();
        Assert.assertEquals(-1, HandCardZone.getAddressOfCybreseMonster("me"));
    }

}