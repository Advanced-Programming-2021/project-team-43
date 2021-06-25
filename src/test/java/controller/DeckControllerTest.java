package controller;

import model.DeckModel;
import model.UserModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class DeckControllerTest {

    @Before
    public void before() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("me", "p", "me");
        MainMenuController.username = "me";
    }


    @Test
    public void addLimitedCard() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        UserModel userModel = new UserModel("alie", "n", "p");
        MainMenuController.username = "alie";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userModel.addCardToUserAllCards("Terraforming");
        userModel.addCardToUserAllCards("Terraforming");
        DeckController.addCardToSideDeck("Terraforming", "deck");
        outContent.reset();
        DeckController.addCardToSideDeck("Terraforming", "deck");
        assertEquals("there are already one cards with name Terraforming in deck deck", outContentWithOutEnter(outContent));
    }

    @Test
    public void testForAddCard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        UserModel userModel = new UserModel("user12", "p", "n");
        MainMenuController.username = "user12";
        DeckModel deckModel = new DeckModel("deck1221");
        userModel.addDeck(deckModel);
        for (int i = 0; i < 10; i++) {
            userModel.addCardToUserAllCards("Axe Raider");
            userModel.addCardToUserAllCards("Mind Crush");
            userModel.addCardToUserAllCards("Monster Reborn");
            userModel.addCardToUserAllCards("Change of Heart");
            userModel.addCardToUserAllCards("Pot of Greed");
        }
        DeckController.addCardToMainDeck("Axe Raider", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Monster Reborn", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Mind Crush", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToSideDeck("Axe Raider", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToSideDeck("Mind Crush", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToSideDeck("Change of Heart", "deck1221");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        for (int i = 0; i < 2; i++) {
            DeckController.addCardToMainDeck("Axe Raider", "deck1221");
            DeckController.addCardToMainDeck("Mind Crush", "deck1221");
        }
        outContent.reset();
        DeckController.addCardToMainDeck("Axe Raider", "deck1221");
        Assert.assertEquals("there are already three cards with name Axe Raider in deck deck1221", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Mind Crush", "deck1221");
        Assert.assertEquals("there are already three cards with name Mind Crush in deck deck1221", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Monster Reborn", "deck1221");
        Assert.assertEquals("there are already one cards with name Monster Reborn in deck deck1221", outContentWithOutEnter(outContent));
    }


    @Test
    public void run() {
        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(0, DeckController.run());
    }

    @Test
    public void findMatcher() {
        new UserModel("user11", "p", "n");
        MainMenuController.username = "user11";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.findMatcher("menu enter (.+?)");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        DeckController.findMatcher("menu show-current");
        Assert.assertEquals("Deck", outContentWithOutEnter(outContent));

        outContent.reset();
        DeckController.findMatcher("invalid command");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        outContent.reset();
        DeckController.findMatcher("deck create (.+?)");
        Assert.assertEquals("deck created successfully!", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --card (.+?) --deck (.+?) --side");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --card (.+?) --side --deck (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --side --card (.+?) --deck (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --side --deck (.+?) --card (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --deck (.+?) --side --card (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --deck (.+?) --card (.+?) --side");
        Assert.assertEquals("card with name (.+?) does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --card (.+?) --deck (.+?) --side");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --card (.+?) --side --deck (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --side --card (.+?) --deck (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --side --deck (.+?) --card (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --deck (.+?) --side --card (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --deck (.+?) --card (.+?) --side");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck show --side --deck-name (.+?)1");
        Assert.assertEquals("deck with name (.+?)1 does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck show --deck-name (.+?)1 --side");
        Assert.assertEquals("deck with name (.+?)1 does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck delete (.+?)");
        Assert.assertEquals("deck deleted successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck set-activate (.+?)");
        Assert.assertEquals("deck with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --card (.+?) --deck (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck add-card --deck (.+?) --card (.+?)");
        Assert.assertEquals("card with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --card (.+?) --deck (.+?)");
        Assert.assertEquals("deck with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck rm-card --deck (.+?) --card (.+?)");
        Assert.assertEquals("deck with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck show --all");
        Assert.assertEquals(34, outContentWithOutEnter(outContent).length());
        outContent.reset();
        DeckController.findMatcher("deck show --deck-name (.+?)");
        Assert.assertEquals("deck with name (.+?) does not exist", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.findMatcher("deck show --cards");
        Assert.assertEquals("", outContent.toString());
    }

    @Test
    public void setActivate() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.setActivate("deck");
        String actual = outContentWithOutEnter(outContent);
        Assert.assertEquals("deck activated successfully", actual);
        outContent.reset();
        DeckController.setActivate("deck2");
        actual = outContentWithOutEnter(outContent);
        Assert.assertEquals("deck with name deck2 does not exist", actual);
    }

    @Test
    public void deleteDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.deleteDeck("deck");
        Assert.assertEquals("deck deleted successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.deleteDeck("deck12");
        Assert.assertEquals("deck with name deck12 does not exist", outContentWithOutEnter(outContent));
    }

    @Test
    public void createDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.createDeck("deck2");
        Assert.assertEquals("deck created successfully!", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.createDeck("deck");
        Assert.assertEquals("deck with name deck already exists", outContentWithOutEnter(outContent));
    }

    @Test
    public void addCardToMainDeck() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.addCardToMainDeck("ads", "deck21212123");
        Assert.assertEquals("card with name ads does not exist", outContentWithOutEnter(outContent));
        outContent.reset();

        DeckController.addCardToMainDeck("ads", "deck");
        Assert.assertEquals("card with name ads does not exist", outContentWithOutEnter(outContent));

        outContent.reset();
        for (int i = 0; i < 4; i++) {
            userModel.addCardToUserAllCards("Yami");
        }

        DeckController.addCardToMainDeck("Yami", "deck");

        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        DeckController.addCardToMainDeck("Yami", "deck");
        DeckController.addCardToMainDeck("Yami", "deck");
        outContent.reset();
        DeckController.addCardToMainDeck("Yami", "deck");
        Assert.assertEquals("there are already three cards with name Yami in deck deck", outContentWithOutEnter(outContent));
        DeckModel deckModel1 = new DeckModel("fullDeck");
        for (int i = 0; i < 60; i++) {
            deckModel1.addCardToMain("card12");
        }
        userModel.addDeck(deckModel1);
        outContent.reset();
        DeckController.addCardToMainDeck("Yami", "fullDeck");
        Assert.assertEquals("main deck is full", outContentWithOutEnter(outContent));
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        outContent.reset();
        DeckController.addCardToMainDeck("Trap Hole", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        DeckController.addCardToMainDeck("Trap Hole", "deck");
        DeckController.addCardToMainDeck("Trap Hole", "deck");
        outContent.reset();
        DeckController.addCardToMainDeck("Trap Hole", "deck");
        Assert.assertEquals("there are already three cards with name Trap Hole in deck deck", outContentWithOutEnter(outContent));


        userModel.addCardToUserAllCards("Time Seal");
        userModel.addCardToUserAllCards("Time Seal");
        outContent.reset();
        DeckController.addCardToMainDeck("Time Seal", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Time Seal", "deck");
        Assert.assertEquals("there are already one cards with name Time Seal in deck deck", outContentWithOutEnter(outContent));


        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        outContent.reset();
        DeckController.addCardToMainDeck("United We Stand", "deck");

        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));

        userModel.addCardToUserAllCards("Monster Reborn");
        userModel.addCardToUserAllCards("Monster Reborn");
        outContent.reset();
        DeckController.addCardToMainDeck("Monster Reborn", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToMainDeck("Monster Reborn", "deck");
        Assert.assertEquals("there are already one cards with name Monster Reborn in deck deck", outContentWithOutEnter(outContent));
    }

    @Test
    public void addCardToSideDeck() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        UserModel userModel = new UserModel("mamad", "n", "p");
        MainMenuController.username = "mamad";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.addCardToSideDeck("ads", "deck21212123");
        Assert.assertEquals("card with name ads does not exist", outContentWithOutEnter(outContent));
        outContent.reset();

        DeckController.addCardToSideDeck("ads", "deck");
        Assert.assertEquals("card with name ads does not exist", outContentWithOutEnter(outContent));

        outContent.reset();
        for (int i = 0; i < 4; i++) {
            userModel.addCardToUserAllCards("Yami");
        }

        DeckController.addCardToSideDeck("Yami", "deck");

        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        DeckController.addCardToSideDeck("Yami", "deck");
        DeckController.addCardToSideDeck("Yami", "deck");
        outContent.reset();
        DeckController.addCardToSideDeck("Yami", "deck");
        Assert.assertEquals("there are already three cards with name Yami in deck deck", outContentWithOutEnter(outContent));///////////////
        DeckModel deckModel1 = new DeckModel("fullDeck");
        for (int i = 0; i < 60; i++) {
            deckModel1.addCardToSide("card12");
        }
        userModel.addDeck(deckModel1);
        outContent.reset();
        DeckController.addCardToSideDeck("Yami", "fullDeck");
        Assert.assertEquals("side deck is full", outContentWithOutEnter(outContent));
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        userModel.addCardToUserAllCards("Trap Hole");
        outContent.reset();
        DeckController.addCardToSideDeck("Trap Hole", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        DeckController.addCardToSideDeck("Trap Hole", "deck");
        DeckController.addCardToSideDeck("Trap Hole", "deck");
        outContent.reset();
        DeckController.addCardToSideDeck("Trap Hole", "deck");
        Assert.assertEquals("there are already three cards with name Trap Hole in deck deck", outContentWithOutEnter(outContent));


        userModel.addCardToUserAllCards("Time Seal");
        userModel.addCardToUserAllCards("Time Seal");
        outContent.reset();
        DeckController.addCardToSideDeck("Time Seal", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToSideDeck("Time Seal", "deck");
        Assert.assertEquals("there are already one cards with name Time Seal in deck deck", outContentWithOutEnter(outContent));

        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        userModel.addCardToUserAllCards("United We Stand");
        outContent.reset();
        DeckController.addCardToSideDeck("United We Stand", "deck");

        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));

        userModel.addCardToUserAllCards("Monster Reborn");
        userModel.addCardToUserAllCards("Monster Reborn");
        outContent.reset();
        DeckController.addCardToSideDeck("Monster Reborn", "deck");
        Assert.assertEquals("card added to deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.addCardToSideDeck("Monster Reborn", "deck");
        Assert.assertEquals("there are already one cards with name Monster Reborn in deck deck", outContentWithOutEnter(outContent));
    }

    @Test
    public void removeCardFromMainDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        deckModel.addCardToMain("cardTest");
        DeckController.removeCardFromMainDeck("cardTest", "deck");
        Assert.assertEquals("card removed form deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.removeCardFromMainDeck("cardTest", "deck");
        Assert.assertEquals("card with name cardTest does not exist in main deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.removeCardFromMainDeck("cardTest", "deck1221423523");
        Assert.assertEquals("deck with name deck1221423523 does not exist", outContentWithOutEnter(outContent));
    }

    @Test
    public void removeCardFromSideDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        deckModel.addCardToSide("cardTest");
        DeckController.removeCardFromSideDeck("cardTest", "deck");
        Assert.assertEquals("card removed form deck successfully", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.removeCardFromSideDeck("cardTest", "deck");
        Assert.assertEquals("card with name cardTest does not exist in side deck", outContentWithOutEnter(outContent));
        outContent.reset();
        DeckController.removeCardFromSideDeck("cardTest", "deck1221423523");
        Assert.assertEquals("deck with name deck1221423523 does not exist", outContentWithOutEnter(outContent));
    }

    @Test
    public void showAllDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.showAllDeck();
        Assert.assertEquals(36, outContent.toString().length());
        outContent.reset();
        DeckModel deckModel = new DeckModel("deck");
        userModel.addDeck(deckModel);
        DeckController.showAllDeck();
        Assert.assertEquals(77, outContent.toString().length());
        outContent.reset();
        DeckModel deckModel1 = new DeckModel("deck2");
        userModel.addDeck(deckModel1);
        userModel.setActiveDeck("deck2");
        DeckController.showAllDeck();
        Assert.assertEquals(119, outContent.toString().length());
    }

    @Test
    public void showMainDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.showMainDeck("deck");
        Assert.assertEquals("deck with name deck does not exist", outContentWithOutEnter(outContent));
        DeckModel deckModel1 = new DeckModel("deck2");
        userModel.addDeck(deckModel1);
        outContent.reset();
        DeckController.showMainDeck("deck2");
        Assert.assertEquals(52, outContentWithOutEnter(outContent).length());

        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        DeckModel deckModel2 = new DeckModel("didi");
        deckModel2.addCardToMain("Axe Raider");
        deckModel2.addCardToMain("Mind Crush");
        deckModel2.addCardToMain("Monster Reborn");
        deckModel2.addCardToMain("Change of Heart");
        deckModel2.addCardToMain("Pot of Greed");
        userModel.addDeck(deckModel2);
        outContent.reset();
        DeckController.showMainDeck("didi");
        Assert.assertEquals(510, outContentWithOutEnter(outContent).length());

    }

    @Test
    public void showSideDeck() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.showSideDeck("deck");
        Assert.assertEquals("deck with name deck does not exist", outContentWithOutEnter(outContent));
        DeckModel deckModel1 = new DeckModel("deck2");
        userModel.addDeck(deckModel1);
        outContent.reset();
        DeckController.showSideDeck("deck2");
        Assert.assertEquals(52, outContentWithOutEnter(outContent).length());


        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        DeckModel deckModel2 = new DeckModel("didi");
        deckModel2.addCardToSide("Axe Raider");
        deckModel2.addCardToSide("Mind Crush");
        deckModel2.addCardToSide("Monster Reborn");
        deckModel2.addCardToSide("Change of Heart");
        deckModel2.addCardToSide("Pot of Greed");
        userModel.addDeck(deckModel2);
        outContent.reset();
        DeckController.showSideDeck("didi");
        Assert.assertEquals(510, outContentWithOutEnter(outContent).length());
    }

    @Test
    public void showCards() {
        UserModel userModel = new UserModel("ali", "n", "p");
        MainMenuController.username = "ali";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckController.showCards();
        Assert.assertEquals("", outContent.toString());

    }

    public String outContentWithOutEnter(ByteArrayOutputStream outContent) {
        return outContent.toString().substring(0, outContent.toString().length() - 2);
    }

}