package main.java.model;

import main.java.view.*;


import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {
    private String username;
    private String password;
    private String nickname;
    private int userScore;
    private int userCoin;
    private HashMap<String, Integer> userAllCards;
    private ArrayList<DeckModel> userAllDecks;
    private String activeDeck;
    private String currentMenu;
    private String onlineUser;
    public static HashMap<String, UserModel> allUsersInfo=new HashMap<>();
    public static ArrayList<String> allUsernames=new ArrayList<>();
    public static ArrayList<String> allUsersNicknames=new ArrayList<>();


    public UserModel(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

//    public  HashMap<String,Integer> getUserAllCards()
//    {
//
//    }


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

    public String getCurrentMenu() {
        return currentMenu;
    }

    public String getOnlineUser() {
        return onlineUser;
    }
    //    public  void setUserState(String state)
//    {
//
//    }


//    public  String getUserState()
//    {
//
//    }


    public void setUsername(String username) {
        this.username = username;

    }


    public void setPassword(String password) {
        this.password = password;

    }


    public void setNickname(String nickname) {
        this.nickname = nickname;

    }


    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }


    public void changeUserScore(int userScore) {
        this.userScore = userScore + this.userScore;
    }




    public void decreaseUserCard(int amount) {

    }


    public void increaseUserCard(int amount) {

    }


    public void changeUserCoin(int amount) {
        this.userCoin=this.userCoin+amount;
    }


    public void setActiveDeck(String deckName) {

    }


    public void deleteDeck(String deckName) {
        for (int i = 0; i <userAllDecks.size() ; i++) {
            if(userAllDecks.get(i).equals(deckName)){
                userAllDecks.remove(i);
            }

        }




    }

    public static void main(String[] args) {
//        DeckModel a =new DeckModel();
//        userAllDecks.add("ad",a);
    }

    public static UserModel getUserByUsername(String username) {
     return allUsersInfo.get(username);
    }


    public static boolean isRepeatedUsername(String username) {

        for (int i = 0; i < allUsernames.size(); i++) {
            if (allUsernames.get(i).equals(username)){
                System.out.println("true");
                return true;
            }
        }
        System.out.println("false");
        return false;

    }


    public static boolean isRepeatedNickname(String nickname) {
        for (int i = 0; i < allUsersNicknames.size(); i++) {
            if (allUsersNicknames.get(i).equals(nickname)){
                return true;
            }
        }
        return false;

    }
}
