//package main.java.model;
//import main.java.controller.MainMenuController;
//import main.java.controller.SetCards;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.*;
//
//public class HandCardZoneTest {
//
//    UserModel user;
//    DeckModel deckModel;
//    Player player;
//    ArrayList<String> mainDeck;
//    ArrayList<String> sideDeck = new ArrayList<>();
//    HandCardZone card;
//
//    @Before
//    public void beforeAllClassTest() {
//        SetCards.readingCSVFileMonster();
//        SetCards.readingCSVFileTrapSpell();
//        user = new UserModel("Guy","123","me");
//        MainMenuController.username = "Guy";
//        deckModel = new DeckModel("myDeck");
//        for (int i = 0; i < 10; i++) {
//            deckModel.addCardToMain("Yami");
//        }
//        for (int i = 0; i < 5; i++) {
//            deckModel.addCardToSide("Battle Ox");
//        }
//        user.addDeck(deckModel);
//        player = new Player("me", deckModel, true, 3);
//        mainDeck = new ArrayList<>(deckModel.getArrayMain());
//        card = new HandCardZone("me", "Forest");
//    }
//
//    @Test
//    public void getCardName() {
//        Assert.assertEquals("Forest", card.getCardName());
//        Assert.assertEquals("Negate Attack", new HandCardZone("me", "Negate Attack").getCardName());
//    }
//
//    @Test
//    public void getAddress() {
//        Assert.assertEquals(5, card.getAddress());
//        Assert.assertEquals(6, new HandCardZone("me", "Solemn Warning").getAddress());
//    }
//
//    @Test
//    public void getKind() {
//        Assert.assertEquals("Spell", card.getKind());
//        Assert.assertEquals("Trap", new HandCardZone("me", "Solemn Warning").getKind());
//    }
//
//    @Test
//    public void removeFromHandCard() {
//        assertNull(HandCardZone.getHandCardByAddress(5, "me"));
//    }
//
//    @Test
//    public void getAddressByName() {
//    }
//
//    @Test
//    public void getNumberOfFullHouse() {
//        new HandCardZone("me", "Twin Twisters");
//        Assert.assertEquals(7, HandCardZone.getNumberOfFullHouse("me"));
//    }
//
//
//    @Test
//    public void doesAnyLevelFourMonsterExisted() {
//
//    }
//
//    @Test
//    public void doesThisCardNameExist() {
//
//    }
//
//    @Test
//    public void showHandCard() {
//
//    }
//
//    @Test
//    public void getHandCardByAddress() {
//
//    }
//
//    @Test
//    public void doIHaveAnyRitualMonster() {
//        Assert.assertEquals(-1, HandCardZone.doIHaveAnyRitualMonster("me"));
//    }
//}