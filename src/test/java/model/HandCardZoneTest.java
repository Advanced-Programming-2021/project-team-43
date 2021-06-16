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
        HandCardZone.getHandCardByAddress(card.getAddress(),"me").removeFromHandCard();
//        HandCardZone.removeFromHandCard("me", card.getAddress());
        assertNull(HandCardZone.getHandCardByAddress(5, "me"));
    }

//    @Test
//    public void getAddressByName() {
//        int[] addresses = HandCardZone.getAddressByName("me", "Yami");
//        int[] expectedAddresses = new int[5];
//        for (int i = 0; i < 5; i++)
//            expectedAddresses[i] = i;
//        Assert.assertEquals(expectedAddresses[0], addresses[0]);
//        Assert.assertEquals(expectedAddresses[1], addresses[1]);
//        Assert.assertEquals(expectedAddresses[2], addresses[2]);
//        Assert.assertEquals(expectedAddresses[3], addresses[3]);
//        Assert.assertEquals(expectedAddresses[4], addresses[4]);
//    }

    @Test
    public void getNumberOfFullHouse() {
        new HandCardZone("me", "Twin Twisters");
        Assert.assertEquals(7, HandCardZone.getNumberOfFullHouse("me"));
    }





    @Test
    public void doesAnyLevelFourMonsterExisted() {
        assertFalse(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
        new HandCardZone(player.getNickname(), "Flame manipulator");
        assertTrue(HandCardZone.doesAnyLevelFourMonsterExisted(player.getNickname()));
    }

    @Test
    public void doesThisCardNameExist() {
        assertEquals(true,HandCardZone.doesThisCardNameExist(player.getNickname(), "Yami"));
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
        assertEquals("Yami",HandCardZone.getHandCardByAddress(3, player.getNickname()).getCardName());
    }

    @Test
    public void doIHaveAnyRitualMonster() {
        Assert.assertEquals(-1, HandCardZone.doIHaveAnyRitualMonster("me"));
        HandCardZone hand = new HandCardZone("me", "Crab Turtle");
        Assert.assertEquals(7, HandCardZone.doIHaveAnyRitualMonster("me"));
    }

    @Test
    public void changeAddress() {
    }

    @Test
    public void removeAllTypeCard() {
    }

    @Test
    public void getAddressOfCybreseMonster() {
    }

}