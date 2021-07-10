package model;
import controller.Json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserModel implements Serializable {


    private final String username;
    private String password;
    private String nickname;
    private int userScore;
    private int userCoin;
    private String imageUrl;
    private String activeDeck;
    public HashMap<String, Integer> userAllCards = new HashMap<>();
    public HashMap<String, DeckModel> userAllDecks = new HashMap<>();
    public static HashMap<String, UserModel> allUsersInfo = new HashMap<>();
    public static ArrayList<String> allUsernames = new ArrayList<>();
    public static ArrayList<String> allUsersNicknames = new ArrayList<>();
    public static ArrayList <String> importedCards;

    public UserModel(String username, String password, String nickname, String imageUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userScore = 0;
        this.userCoin = 100000;
        this.imageUrl = imageUrl;
        this.activeDeck="";
        allUsernames.add(username);
        allUsersNicknames.add(nickname);
        allUsersInfo.put(username, this);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }
public static void setObject(UserModel userModel){
        allUsersInfo.put(userModel.getUsername(),userModel);
}
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getUserScore() {
        return userScore;
    }

    public int getUserCoin() {
        return userCoin;
    }

    public HashMap<String, Integer> getUserAllCards() {
        return userAllCards;
    }

    public void changePassword(String password) {
        this.password = password;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void changeNickname(String nickname) {
        allUsersNicknames.remove(this.nickname);
        allUsersNicknames.add(nickname);
        this.nickname = nickname;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void changeUserScore(int userScore) {
        this.userScore += userScore;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void changeUserCoin(int amount) {
        this.userCoin += amount;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setActiveDeck(String deckName) {
        this.activeDeck = deckName;
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void addDeck(DeckModel deckModel) {
        userAllDecks.put(deckModel.getDeckName(), deckModel);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void deleteDeck(String deckName) {
        userAllDecks.remove(deckName);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public void addCardToUserAllCards(String cardName) {
        if (null == this.userAllCards.get(cardName)) {
            userAllCards.put(cardName, 1);
        } else {
            int cardNumbers = userAllCards.get(cardName) + 1;
            userAllCards.replace(cardName, cardNumbers);
        }
        Json.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public void removeCardFromUserAllCards(String cardName) {
        if (isUserHaveCard(cardName)) {
            int i = userAllCards.get(cardName) - 1;
            if (i == 0) {
                userAllCards.remove(cardName);
            } else {
                userAllCards.replace(cardName, i);
            }
        }
        Json.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public boolean isUserHaveCard(String cardName) {
        return null != this.userAllCards.get(cardName);
    }

    public String getActiveDeck() {
        return activeDeck;
    }

    public boolean isUserHaveThisDeck(String deckName) {
        return userAllDecks.containsKey(deckName);
    }

    public static UserModel getUserByUsername(String username) {
        return allUsersInfo.get(username);
    }

    public static boolean isRepeatedUsername(String username) {
        for (Map.Entry<String, UserModel> eachUser : allUsersInfo.entrySet())
            if (eachUser.getKey().equals(username))
                return true;
        return false;
    }

    public static boolean isRepeatedNickname(String nickname) {
        for (Map.Entry<String, UserModel> eachUser : allUsersInfo.entrySet())
            if (eachUser.getValue().getNickname().equals(nickname))
                return true;
        return false;
    }

    public static UserModel getUserByNickname(String nickname){
        for (Map.Entry<String, UserModel> eachUser : allUsersInfo.entrySet())
            if(eachUser.getValue().getNickname().equals(nickname))
                return eachUser.getValue();
        return null;
    }

}