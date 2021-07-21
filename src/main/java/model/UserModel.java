package model;
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
    private String rivalToken;
    private boolean isOnline;
    private int chatRecords = 0;
    private int sequentialWin = 0;
    private int sequentialLost = 0;
    private final HashMap<String, Integer> myAchievements = new HashMap<>();
    private final HashMap<Integer, String> myInvitations = new HashMap<>();
    public HashMap<String, Integer> userAllCards = new HashMap<>();
    public HashMap<String, DeckModel> userAllDecks = new HashMap<>();
    public static HashMap<String, UserModel> allUsersInfo = new HashMap<>();
    public static ArrayList<String> allUsernames = new ArrayList<>();
    public static ArrayList<String> allUsersNicknames = new ArrayList<>();
    public static ArrayList <String> importedCards;
    private static final long serialVersionUID = 4778925288210528972L;
    public static ArrayList<BazarModel> all = new ArrayList<>();
    public static int bazarCounter;


    public UserModel(String username, String password, String nickname, String imageUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userScore = 0;
        this.userCoin = 100000;
        this.imageUrl = imageUrl;
        this.activeDeck="";
        this.rivalToken = "";
        myAchievements.put("chatCup", 0);
        myAchievements.put("sequentialWin", 0);
        myAchievements.put("sequentialLost", 0);
        allUsernames.add(username);
        allUsersNicknames.add(nickname);
        allUsersInfo.put(username, this);
    }

    public static void setObject(UserModel userModel){
        allUsersInfo.put(userModel.getUsername(), userModel);
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

    public void setRivalToken(String rivalToken) {
        this.rivalToken = rivalToken;
    }

    public String getRivalToken() {
        return rivalToken;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public HashMap<String, Integer> getUserAllCards() {
        return userAllCards;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        allUsersNicknames.remove(this.nickname);
        allUsersNicknames.add(nickname);
        this.nickname = nickname;
    }

    public void changeUserScore(int userScore) {
        this.userScore += userScore;
    }

    public void changeUserCoin(int amount) {
        this.userCoin += amount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setActiveDeck(String deckName) {
        this.activeDeck = deckName;
    }

    public void addDeck(DeckModel deckModel) {
        userAllDecks.put(deckModel.getDeckName(), deckModel);
    }

    public void deleteDeck(String deckName) {
        userAllDecks.remove(deckName);
    }

    public void addCardToUserAllCards(String cardName) {
        if (null == this.userAllCards.get(cardName)) {
            userAllCards.put(cardName, 1);
        } else {
            int cardNumbers = userAllCards.get(cardName) + 1;
            userAllCards.replace(cardName, cardNumbers);
        }
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
    }

    public void addAchievement(String name) {
        int x = myAchievements.get(name);
        myAchievements.replace(name, x, x + 1);
    }

    public void addSequentialWin() {
        sequentialLost++;
    }

    public void resetSequentialWin() {
        sequentialWin = 0;
    }

    public int getSequentialWin() {
        return sequentialWin;
    }

    public void addSequentialLost() {
        sequentialWin++;
    }

    public void resetSequentialLost() {
        sequentialWin = 0;
    }

    public int getSequentialLost() {
        return sequentialWin;
    }

    public int getChatRecords() {
        return chatRecords;
    }

    public void setChatRecords(int chatRecords) {
        this.chatRecords = chatRecords;
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

    public void addInvitation(int id, String username) {
        myInvitations.put(id, username);
    }

    public void removeInvitation(int id) {
        myInvitations.remove(id);
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