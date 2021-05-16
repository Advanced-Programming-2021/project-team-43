package main.java.controller;

import main.java.controller.MainMenuController;
import main.java.model.DeckModel;
import main.java.model.UserModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainMenuControllerTest {


    @Test
    public void findMatcher() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        MainMenuController.findMatcher("menu enter (.+?)");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.findMatcher("menu show-current");
        Assert.assertEquals("Main Menu", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.findMatcher("invalid command");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));


    }


    @Test
    public void duelFindMatcher() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        MainMenuController.duelFindMatcher("invalid command");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.duelFindMatcher("menu show-current");
        Assert.assertEquals("Duel Menu", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.duelFindMatcher("menu enter (.+?)");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.duelFindMatcher("menu enter deck");
        Assert.assertEquals("menu navigation is not possible", outContentWithOutEnter(outContent));


    }

    @Test
    public void duelMenu() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        UserModel userModel = new UserModel("ali", "p", "n");
        UserModel userModel1 = new UserModel("reza", "p", "n2");
        MainMenuController.username = "ali";
        MainMenuController.duelMenu("reza",12);
        Assert.assertEquals("ali has no active deck", outContentWithOutEnter(outContent));

        outContent.reset();
        userModel.setActiveDeck("a");
        MainMenuController.duelMenu("reza",12);
        Assert.assertEquals("reza has no active deck", outContentWithOutEnter(outContent));

        outContent.reset();
        userModel1.setActiveDeck("a");
        MainMenuController.duelMenu("invalid username",12);
        Assert.assertEquals("there is no player with this username", outContentWithOutEnter(outContent));

        outContent.reset();
        userModel1.setActiveDeck("a");
        DeckModel deckModel = new DeckModel("a");
        DeckModel deckModel1 = new DeckModel("a");
        userModel.addDeck(deckModel);
        userModel1.addDeck(deckModel1);

        MainMenuController.duelMenu("reza",12);
        Assert.assertEquals("ali’s deck is invalid", outContentWithOutEnter(outContent));
        for (int i = 0; i < 50; i++) {
            deckModel.addCardToMain("a");
        }


        outContent.reset();
        MainMenuController.duelMenu("reza",12);
        Assert.assertEquals("reza’s deck is invalid", outContentWithOutEnter(outContent));


        for (int i = 0; i < 50; i++) {
            deckModel1.addCardToMain("a");
        }
        outContent.reset();
        MainMenuController.duelMenu("reza",12);
        Assert.assertEquals("number of rounds is not supported", outContentWithOutEnter(outContent));






    }

    @Test
    public void profile() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        new UserModel("ali", "p", "n");
        new UserModel("alis", "p", "nd");
        MainMenuController.username = "ali";
        MainMenuController.profile("menu show-current");
        Assert.assertEquals("Profile Menu",outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("menu enter (.+?)");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("menu enter deck");
        Assert.assertEquals("menu navigation is not possible", outContentWithOutEnter(outContent));


        outContent.reset();
        MainMenuController.profile("profile change --nickname bb");
        Assert.assertEquals("nickname changed successfully!", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("profile change --nickname nd");
        Assert.assertEquals("user with nickname nd already exists", outContentWithOutEnter(outContent));



        outContent.reset();
        MainMenuController.profile("profile change --password --current p --new p");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("profile change --password --current pp --new p");
        Assert.assertEquals("current password is invalid", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("profile change --password --current p --new pp");
        Assert.assertEquals("password changed successfully!", outContentWithOutEnter(outContent));


        outContent.reset();
        MainMenuController.profile("profile change --current (.+?) --password --new (.+?)");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("profile change --current (.+?) --new (.+?) --password");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));
        outContent.reset();
        MainMenuController.profile("profile change --password --new (.+?) --current (.+?)");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));
        outContent.reset();
        MainMenuController.profile("profile change --new (.+?) --password --current (.+?)");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("profile change --new (.+?) --current (.+?) --password");
        Assert.assertEquals("please enter a new password", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.profile("invalid command");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));


    }



    @Test
    public void scoreboard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        UserModel userModel = new UserModel("ali", "p", "n");


        outContent.reset();
        MainMenuController.scoreboard("mensdfdsfsdfds");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));
        outContent.reset();
        MainMenuController.scoreboard("menu show-current");
        Assert.assertEquals("Score board Menu", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.scoreboard("menu enter (.+?)");
        Assert.assertEquals("invalid command", outContentWithOutEnter(outContent));

        outContent.reset();
        MainMenuController.scoreboard("menu enter deck");
        Assert.assertEquals("menu navigation is not possible", outContentWithOutEnter(outContent));


        outContent.reset();

        Assert.assertEquals(1, MainMenuController.scoreboard("scoreboard show"));

        UserModel userModel1 = new UserModel("aliii", "p", "nn");


        UserModel userModel2 = new UserModel("reza1", "p", "bb");
        UserModel userModel3 = new UserModel("reza2", "p", "bbb");
        userModel2.setUserScore(123);
        userModel3.setUserScore(1232);

        outContent.reset();
        Assert.assertEquals(1, MainMenuController.scoreboard("scoreboard show"));

    }

    @Test
    public void showScoreboard() {
    }



    public String outContentWithOutEnter(ByteArrayOutputStream outContent) {
        return outContent.toString().substring(0, outContent.toString().length() - 2);
    }
}