package main.java.model;

import java.util.*;

public class Player {

    private String username;
    private String nickname;
    private int lifePoint;
    private DeckModel activeDeck;
    private boolean canUseTrap;
    private int counterOfTurn;
    private boolean isYourTurn;
    private boolean isYourMoveFinished;
    public static List<Player> allPlayers = new ArrayList<>();

    public Player (String username, String nickname, DeckModel activeDeck) {
        this.username = username;
        this.nickname = nickname;
        this.activeDeck = activeDeck;
        this.lifePoint = 8000;
        this.counterOfTurn = 0;
        allPlayers.add(this);
    }


    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void changeLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }

    public DeckModel getPlayerDeck() {
        return activeDeck;
    }

    private int getCounterOfTurn() {
        return counterOfTurn;
    }

    private void changeCounterOfTurn() {
        counterOfTurn++;
    }

    public void setIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;//coin or ...
    }

    public boolean getIsYourMoveFinished() {
        return isYourMoveFinished;
    }

    private void setIsYourMoveFinished(boolean isYourMoveFinished) {
        this.isYourMoveFinished = isYourMoveFinished;
    }


    public static Player getPlayerByName(String playerNickname) {
        for (Player eachPlayer: allPlayers)
            if (eachPlayer.username.equals(playerNickname))
                return eachPlayer;
        return null;
    }

}
