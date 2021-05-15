package test.java;
import main.java.model.DeckModel;
import main.java.model.UserModel;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.regex.Pattern;

public class UserTest {

    @Test
    public void getPasswordTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        Assert.assertEquals("pass", userModel.getPassword());
    }

    @Test
    public void getNicknameTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        Assert.assertEquals("nick", userModel.getNickname());
    }

    @Test
    public void getUsernameTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        Assert.assertEquals("username", userModel.getUsername());
    }

    @Test
    public void getUserCoinTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        Assert.assertEquals(100000, userModel.getUserCoin());
    }

    @Test
    public void getUserScoreTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        Assert.assertEquals(0, userModel.getUserScore());
    }

    @Test
    public void changePasswordTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.changePassword("newPass");
        Assert.assertEquals("newPass", userModel.getPassword());
    }

    @Test
    public void changeNicknameTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.changeNickname("newNick");
        Assert.assertEquals("newNick", userModel.getNickname());

    }

    @Test
    public void setUserScore() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.setUserScore(123);
        Assert.assertEquals(123, userModel.getUserScore());
    }

    @Test
    public void changeUserCoinTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        int userCoin = userModel.getUserCoin();
        userModel.changeUserCoin(-5);
        Assert.assertEquals(userCoin - 5, userModel.getUserCoin());
    }

    @Test
    public void setActiveDeckTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.setActiveDeck("deck");
        Assert.assertEquals("deck", userModel.getActiveDeck());
    }

    @Test
    public void addDeckTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        DeckModel deckModel = new DeckModel("name");
        userModel.addDeck(deckModel);
        HashMap<String, DeckModel> hashMap = new HashMap<>();
        hashMap.put("name", deckModel);
        Assert.assertEquals(hashMap, userModel.userAllDecks);

    }

    @Test
    public void deleteDeckTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");

        DeckModel deckModel1 = new DeckModel("name1");
        DeckModel deckModel2 = new DeckModel("name2");
        DeckModel deckModel3 = new DeckModel("name3");

        userModel.addDeck(deckModel1);
        userModel.addDeck(deckModel2);
        userModel.addDeck(deckModel3);

        HashMap<String, DeckModel> hashMap = new HashMap<>();
        hashMap.put("name1", deckModel1);
        hashMap.put("name3", deckModel3);

        userModel.deleteDeck("name2");

        Assert.assertEquals(hashMap, userModel.userAllDecks);
    }

    @Test
    public void getUserByUsernameTest() {
        UserModel userModel1 = new UserModel("username1", "pass", "nick1");
        UserModel userModel2 = new UserModel("username2", "pass", "nick2");
        UserModel userModel3 = new UserModel("username3", "pass", "nick3");
        Assert.assertEquals(userModel2, UserModel.getUserByUsername("username2"));

    }

    @Test
    public void isRepeatedUsername() {
        new UserModel("username1", "pass", "nick1");
        new UserModel("username2", "pass", "nick2");
        new UserModel("username3", "pass", "nick3");
        Assert.assertTrue(UserModel.isRepeatedUsername("username1"));
        Assert.assertFalse(UserModel.isRepeatedUsername("username66"));
    }

    @Test
    public void isRepeatedNickname() {
        new UserModel("username1", "pass", "nick1");
        new UserModel("username2", "pass", "nick2");
        new UserModel("username3", "pass", "nick3");
        Assert.assertTrue(UserModel.isRepeatedNickname("nick2"));
        Assert.assertFalse(UserModel.isRepeatedNickname("nick66"));
    }

    @Test
    public void addCardToUserAllCardsTest() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card2");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("card1", 4);
        hashMap.put("card2", 1);
        Assert.assertEquals(hashMap, userModel.userAllCards);

    }

    @Test
    public void removeCardFromUserAllCards() {
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card2");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("card1", 3);
        userModel.removeCardFromUserAllCards("card1");
        userModel.removeCardFromUserAllCards("card2");
        Assert.assertEquals(hashMap, userModel.userAllCards);
    }

    @Test
    public void isUserHaveCard(){
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card1");
        userModel.addCardToUserAllCards("card2");
        Assert.assertTrue(userModel.isUserHaveCard("card1"));
        Assert.assertFalse(userModel.isUserHaveCard("card34"));
    }

    @Test
    public void isUserHaveThisDeckTest(){
        UserModel userModel = new UserModel("username", "pass", "nick");
        DeckModel deckModel = new DeckModel("name");
        userModel.addDeck(deckModel);
        Assert.assertTrue(userModel.isUserHaveThisDeck("name"));
        Assert.assertFalse(userModel.isUserHaveThisDeck("card34"));

    }

    @Test
    public void changeUserScoreTest(){
        UserModel userModel = new UserModel("username", "pass", "nick");
        userModel.changeUserScore(5);
        Assert.assertEquals(5,userModel.getUserScore());
    }
}
