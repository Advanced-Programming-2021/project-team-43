package model;

import controller.MainMenuController;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckModelTest {

    @Test
    public void getDeckName() {
        DeckModel deckModel= new DeckModel("a");
        Assert.assertEquals("a",deckModel.getDeckName());
    }

    @Test
    public void addCardToMain() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        String[] strings =deckModel.cardsInMainDeck.keySet().toArray(new String[0]);
        String[] strings1={"card","asa"};
        Assert.assertEquals(strings1,strings);
    }

    @Test
    public void removeCardFromMain() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        String[] strings1={"card"};
        deckModel.removeCardFromMain("asa");
        String[] strings =deckModel.cardsInMainDeck.keySet().toArray(new String[0]);
        Assert.assertEquals(strings1,strings);
    }

    @Test
    public void addCardToSide() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToSide("card");
        deckModel.addCardToSide("asa");
        String[] strings =deckModel.cardsInSideDeck.keySet().toArray(new String[0]);
        String[] strings1={"card","asa"};
        Assert.assertEquals(strings1,strings);
    }

    @Test
    public void removeCardFromSide() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToSide("card");
        deckModel.addCardToSide("asa");
        deckModel.removeCardFromSide("asa");
        String[] strings =deckModel.cardsInSideDeck.keySet().toArray(new String[0]);
        String[] strings1={"card"};
        Assert.assertEquals(strings1,strings);
    }

    @Test
    public void getNumberOfCardInMainDeck() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        Assert.assertEquals(2,deckModel.getNumberOfCardInMainDeck("card"));
    }

    @Test
    public void getNumberOfCardInSideDeck() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToSide("card");
        deckModel.addCardToSide("asa");
        deckModel.addCardToSide("card");
        Assert.assertEquals(2,deckModel.getNumberOfCardInSideDeck("card"));
    }

    @Test
    public void getMainAllCardNumber() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        Assert.assertEquals(3,deckModel.getMainAllCardNumber());
    }

    @Test
    public void getSideAllCardNumber() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToSide("card");
        deckModel.addCardToSide("asa");
        deckModel.addCardToSide("card");
        Assert.assertEquals(3,deckModel.getSideAllCardNumber());
    }

    @Test
    public void isMainDeckHaveThisCard() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        Assert.assertTrue(deckModel.isMainDeckHaveThisCard("card"));
        Assert.assertFalse(deckModel.isMainDeckHaveThisCard("cardsd"));
    }

    @Test
    public void isSideDeckHaveThisCard() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToSide("card");
        deckModel.addCardToSide("asa");
        deckModel.addCardToSide("card");
        Assert.assertTrue(deckModel.isSideDeckHaveThisCard("card"));
        Assert.assertFalse(deckModel.isSideDeckHaveThisCard("cardsd"));
    }

    @Test
    public void validOrInvalid() {
        new UserModel("ali","p","n");
        MainMenuController.username="ali";
        DeckModel deckModel= new DeckModel("a");
        deckModel.addCardToMain("card");
        deckModel.addCardToMain("asa");
        deckModel.addCardToMain("card");
        Assert.assertEquals("invalid",deckModel.validOrInvalid());
        DeckModel deckModel1=new DeckModel("s");
        for (int i = 0; i <100 ; i++)
            deckModel1.addCardToMain("asa");
        Assert.assertEquals("valid",deckModel1.validOrInvalid());
    }

    @Test
    public void getArrayMain() {
    }
}