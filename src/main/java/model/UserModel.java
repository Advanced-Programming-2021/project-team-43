package model;


import controller.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {

    private final String username;
    private String password;
    private String nickname;
    private int userScore;
    private int userCoin;
    public HashMap<String, Integer> userAllCards = new HashMap<>();
    public HashMap<String, DeckModel> userAllDecks = new HashMap<>();
    private String activeDeck;
    public static HashMap<String, UserModel> allUsersInfo = new HashMap<>();
    public static ArrayList<String> allUsernames = new ArrayList<>();
    public static ArrayList<String> allUsersNicknames = new ArrayList<>();
    public static ArrayList <String> importedCards = new ArrayList<>();
    public UserModel(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userScore = 0;
        this.userCoin = 100000;
        this.activeDeck="";
        allUsersInfo.put(username,this);
        allUsernames.add(username);
        allUsersNicknames.add(nickname);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);

    }


    public int getUserCoin() {
        return userCoin;
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

    public void changePassword(String password) {
        this.password = password;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }


    public void changeNickname(String nickname) {

        allUsersNicknames.remove(this.nickname);
        allUsersNicknames.add(nickname);
        this.nickname = nickname;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);

    }


    public void setUserScore(int userScore) {
        this.userScore = userScore;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);

    }


    public void changeUserScore(int userScore) {
        this.userScore = userScore + this.userScore;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }


    public void changeUserCoin(int amount) {

        this.userCoin = this.userCoin + amount;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }


    public void setActiveDeck(String deckName) {
        this.activeDeck = deckName;
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }


    public void addDeck(DeckModel deckModel) {
        userAllDecks.put(deckModel.getDeckName(), deckModel);
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public void deleteDeck(String deckName) {
        userAllDecks.remove(deckName);
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }


    public static UserModel getUserByUsername(String username) {
        return allUsersInfo.get(username);
    }


    public static boolean isRepeatedUsername(String username) {

        for (String allUsername : allUsernames) {
            if (allUsername.equals(username)) {

                return true;
            }
        }

        return false;

    }

    public static boolean isRepeatedNickname(String nickname) {
        for (String allUsersNickname : allUsersNicknames) {
            if (allUsersNickname.equals(nickname)) {
                return true;
            }
        }
        return false;

    }


    public void addCardToUserAllCards(String cardName) {
        if (null == this.userAllCards.get(cardName)) {
            userAllCards.put(cardName, 1);
        } else {
            int cardNumbers = userAllCards.get(cardName) + 1;
            userAllCards.replace(cardName, cardNumbers);
        }
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
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
        allUsersInfo.replace(username, this);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public boolean isUserHaveCard(String cardName) {
        return null != this.userAllCards.get(cardName);
    }

    public String getActiveDeck() {
        return activeDeck;
    }

    public boolean isUserHaveThisDeck(String deckName) {
        return userAllDecks.get(deckName) != null;
    }
}
