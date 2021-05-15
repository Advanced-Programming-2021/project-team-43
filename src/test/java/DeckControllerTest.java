package test.java;

import main.java.controller.DeckController;
import main.java.controller.MainMenuController;
import main.java.controller.SetCards;
import main.java.model.DeckModel;
import main.java.model.UserModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class DeckControllerTest {
    @Test
    public void findMatcher1() {
        new UserModel("ll", "p", "mahin");
        String command = "deck create deck";
        MainMenuController.username = "ll";
        Assert.assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher2() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --card rr --deck vv --side";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher3() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --card jj --side --deck pp";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher4() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --side --card ww --deck pp";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher5() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --side --deck hh --card jj";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher6() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --deck cc --side --card rr";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher7() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --deck mm--card jjj --side";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher8() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --card nn --deck gg --side";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher9() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --card hh --side --deck ww";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher10() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --side --card ii --deck ll";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher11() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --side --deck hh --card h";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher12() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --deck o --side --card k";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher13() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --deck ll --card kk --side";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher14() {
        new UserModel("ll", "p", "mahin");
        String command = "deck show --side --deck-name m";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher15() {
        new UserModel("ll", "p", "mahin");
        String command = "deck show --deck-name nn --side";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher16() {
        new UserModel("ll", "p", "mahin");
        String command = "deck delete c";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher17() {
        new UserModel("ll", "p", "mahin");
        String command = "deck set-activate xx";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher18() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --card n --deck j";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher19() {
        new UserModel("ll", "p", "mahin");
        String command = "deck add-card --deck b --card mj";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher20() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --card m --deck j";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher21() {
        new UserModel("ll", "p", "mahin");
        String command = "deck rm-card --deck hjj --card pp";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher22() {
        new UserModel("ll", "p", "mahin");
        String command = "deck show --all";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher23() {
        new UserModel("ll", "p", "mahin");
        String command = "deck show --deck-name gh";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher24() {
        new UserModel("ll", "p", "mahin");
        String command = "deck show --cards";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher25() {
        new UserModel("ll", "p", "mahin");
        String command = "menu exit";
        MainMenuController.username = "ll";
        assertEquals(0, DeckController.findMatcher(command));
    }
    @Test
    public void findMatcher26() {
        new UserModel("ll", "p", "mahin");
        String command = "menu enter fd";
        MainMenuController.username = "ll";
        assertEquals(1, DeckController.findMatcher(command));
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
        Assert.assertEquals("deck with name deck21212123 does not exist", outContentWithOutEnter(outContent));
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
        Assert.assertEquals("deck with name deck21212123 does not exist", outContentWithOutEnter(outContent));
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
        Assert.assertEquals("there are already three cards with name Yami in deck deck", outContentWithOutEnter(outContent));
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
        Assert.assertEquals(76, outContent.toString().length());
        outContent.reset();
        DeckModel deckModel1 = new DeckModel("deck2");
        userModel.addDeck(deckModel1);
        userModel.setActiveDeck("deck2");
        DeckController.showAllDeck();
        Assert.assertEquals(117, outContent.toString().length());


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