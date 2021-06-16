package controller;

import model.Card;
import model.ShopModel;
import model.UserModel;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ShopControllerTest {

    @Test
    public void findMatcher1() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String command = "shop buy Yaami";
        ShopController.findMatcher(command);
        assertEquals("there is no card with this name", show.toString().substring(0, show.toString().length() - 2));
    }

    @Test
    public void findMatcher3() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String command3 = "menu enter Deck";
        ShopController.findMatcher(command3);
        assertEquals("menu navigation is not possible", show.toString().substring(0, show.toString().length() - 2));
    }

    @Test
    public void findMatcher2() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String cammand2 = "shop show --all";
        ShopController.findMatcher(cammand2);
        assertEquals(1658, show.toString().length());
    }

    @Test
    public void findMatcher4() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String command = "menu show-current";
        ShopController.findMatcher(command);
        assertEquals("shop", show.toString().substring(0, show.toString().length() - 2));
    }
    @Test
    public void findMatcher5() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String command = "eeeeee";
        ShopController.findMatcher(command);
        assertEquals("invalid command", show.toString().substring(0, show.toString().length() - 2));
    }
    @Test
    public void findMatcher6() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        String command3 = "menu enter Deckaak";
        ShopController.findMatcher(command3);
        assertEquals("invalid command", show.toString().substring(0, show.toString().length() - 2));
    }
    @Test
    public void shopShow() {
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        ShopController.shopShow();
        assertEquals(0, show.toString().length());
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        ShopController.shopShow();
        assertEquals(1658, show.toString().length());
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
    @Test
    public void run() {
    }

    @Test
    public void findMatcher() {
    }
}