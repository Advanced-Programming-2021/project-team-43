package main.java.model;
import java.util.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Player {

    private final String nickname;
    private int lifePoint;
    private boolean isYourTurn;
    private int counterOfTurn;
    private boolean isYourMoveFinished;
    private boolean canUseTrap;
    private boolean canSetSummonMonster;
    private int numberOfRound;
    private int numberOfWin;
    private boolean canDrawCard;
    private int counterOfMainDeck;
    private final Map<Integer,String> playerMainDeck = new HashMap<>();
    private final Map<Integer,String> playerSideDeck = new HashMap<>();
    public static Map<String,Player> allPlayers = new HashMap<>();

    public Player (String nickname, DeckModel activeDeck, boolean isYourTurn, int numberOfRound) {
        this.nickname = nickname;
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.counterOfTurn = 0;
        this.canUseTrap = true;
        this.canSetSummonMonster = true;
        this.numberOfRound = numberOfRound;
        this.numberOfWin = 0;
        this.canDrawCard = !isYourTurn;
        if (isYourTurn)
            this.counterOfMainDeck = 0;
        else
            this.counterOfMainDeck = 1;
        for (String key : activeDeck.cardsInMainDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInMainDeck.get(key); i++)
                playerMainDeck.put(playerMainDeck.size() + 1, key);
        for (String key : activeDeck.cardsInSideDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(key); i++)
                playerSideDeck.put(playerSideDeck.size() + 1, key);

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

    public int getCounterOfTurn() {
        return counterOfTurn;
    }

    public void changeCounterOfTurn() {
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

    public boolean getCanDrawCard() {
        return canDrawCard;
    }

    public void setCanDrawCard(boolean canDrawCard) {
        this.canDrawCard = canDrawCard;
    }

    public boolean getCanUseTrap() {
        return canUseTrap;
    }

    public void setCanUseTrap(boolean canUseTrap) {
        this.canUseTrap = canUseTrap;
    }

    public boolean getCanSetSummonMonster() {
        return canSetSummonMonster;
    }

    public void setCanSetSummonMonster(boolean canSetSummonMonster) {
        this.canSetSummonMonster = canSetSummonMonster;
    }

    private int getNumberOfRound() {
        return numberOfRound;
    }

    public void changeNumberOfRound() {
        numberOfRound--;
    }

    public int getNumberOfWin() {
        return numberOfWin;
    }

    public void changeNumberOfWin() {
        numberOfWin++;
    }

    public String drawCard() {
        while (!playerMainDeck.containsKey(counterOfMainDeck))
            counterOfMainDeck++;
        return playerMainDeck.get(counterOfMainDeck);
    }

    public void removeFromMainDeck() {
        playerMainDeck.remove(counterOfMainDeck);
    }

    public int getNumberOfMainDeckCards() {
        return playerMainDeck.size();
    }

    public void addToMainDeck(String cardName) {
        playerMainDeck.put(playerMainDeck.size()+1, cardName);
    }

    public int getNumberOfSideDeckCards() {
        return playerSideDeck.size();
    }

    public void addToSideDeck(String cardName) {
        playerSideDeck.put(playerSideDeck.size()+1, cardName);
    }

    public static Player getPlayerByName(String playerNickname) {
        return allPlayers.get(playerNickname);
    }

}