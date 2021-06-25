package controller;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ShopControllerTest {

    @Before
    public void before() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("me", "p", "me");
        MainMenuController.username = "me";
    }

    @Test
    public void findMatcher() {
        MainMenuController.username = "me";
        Assert.assertEquals(1, ShopController.findMatcher("shop buy Mirage Dragon"));
        Assert.assertEquals(2, ShopController.findMatcher("shop show --all"));
        Assert.assertEquals(3, ShopController.findMatcher("menu enter Deck"));
        Assert.assertEquals(3, ShopController.findMatcher("menu enter Dck"));
        Assert.assertEquals(4, ShopController.findMatcher("menu show-current"));
        Assert.assertEquals(5, ShopController.findMatcher("menu exit"));
        Assert.assertEquals(6, ShopController.findMatcher("increase --money 1000"));
        Assert.assertEquals(7, ShopController.findMatcher("what"));
    }


    @Test
    public void shopShow() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards()); ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        ShopController.shopShow();
        assertEquals(1655, show.toString().length());

        ShopController.shopShow();
        assertEquals(3310, show.toString().length());
    }

    @Test
    public void shopBuy() {
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        ShopController.shopBuy("Yawwi");
        assertEquals("there is no card with this name", show.toString().substring(0, show.toString().length() - 2));
        UserModel aa = new UserModel("a", "b", "c");
        show.reset();
        MainMenuController.username = "a";
        ShopController.shopBuy("Yami");
        assertEquals("your shopping was successful!", show.toString().substring(0, show.toString().length() - 2));
        show.reset();
        aa.changeUserCoin(-99990);
        ShopController.shopBuy("Yami");
        assertEquals("not enough money", show.toString().substring(0, show.toString().length() - 2));
    }

}