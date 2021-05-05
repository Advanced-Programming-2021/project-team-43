package main.java.model;
import java.util.*;

public class Player {

    private String nickname;
    private int lifePoint;
    private int counterOfTurn;
    private boolean isYourTurn;
    private boolean isYourMoveFinished;
    private boolean canUseTrap;
    private int numberOfRound;
    private int numberOfWin;
    private final Map<Integer,String> playerMainDeck = new HashMap<>();
    private final Map<Integer,String> playerSideDeck = new HashMap<>();
    public static Map<String,Player> allPlayers = new HashMap<>();

    public Player (String nickname, DeckModel activeDeck, boolean isYourTurn, int numberOfRound) {
        this.nickname = nickname;
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.counterOfTurn = 0;
        this.canUseTrap = true;
        this.numberOfRound = numberOfRound;
        this.numberOfWin = 0;
        for (String key : activeDeck.cardsInMainDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInMainDeck.get(key); i++)
                playerMainDeck.put(i, key);

        for (String key : activeDeck.cardsInSideDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(key); i++)
                playerSideDeck.put(i, key);

        allPlayers.put(this.nickname, this);
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void changeLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }

    public Map<Integer,String> getPlayerDeck(String whichDeck) {
        if (whichDeck.equals("main"))
            return playerMainDeck;
        else
            return playerSideDeck;
    }

    private int getCounterOfTurn() {
        return counterOfTurn;
    }

    private void changeCounterOfTurn() {
        counterOfTurn++;
    }

    public void setIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }

    public boolean getIsYourTurn() {
        return isYourTurn;
    }

    public boolean getIsYourMoveFinished() {
        return isYourMoveFinished;
    }

    public void setIsYourMoveFinished(boolean isYourMoveFinished) {
        this.isYourMoveFinished = isYourMoveFinished;
    }

    public boolean getCanUseTrap() {
        return canUseTrap;
    }

    public void setCanUseTrap(boolean canUseTrap) {
        this.canUseTrap = canUseTrap;
    }

    private int getNumberOfRound() {
        return numberOfRound;
    }

    private void changeNumberOfRound() {
        numberOfRound--;
    }

    private int getNumberOfWin() {
        return numberOfWin;
    }

    private void changeNumberOfWin() {
        numberOfWin++;
    }

    public String drawCard() {
        return playerMainDeck.get(1);
    }

    public int getNumberOfMainDeckCards() {
        return playerMainDeck.size();
    }

    public void addToMainDeck(String cardName) {
        playerMainDeck.put(playerMainDeck.size()+1, cardName);
    }

    public void removeFromMainDeck() {
        playerMainDeck.remove(1);
    }

    public int getNumberOfSideDeckCards() {
        return playerSideDeck.size();
    }

    public void addToSideDeck(String cardName) {
        playerSideDeck.put(playerSideDeck.size()+1, cardName);
    }

    public void removeFromSideDeck() {
        playerSideDeck.remove(1);
    }

    public static Player getPlayerByName(String playerNickname) {
        return allPlayers.get(playerNickname);
    }

}
