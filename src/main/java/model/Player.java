package model;
import java.util.*;


public class Player {

    private String nickname;
    private DeckModel activeDeck;
    private int lifePoint;
    private int counterOfTurn;
    private boolean isYourTurn;
    private boolean isYourMoveFinished;
    private boolean canUseTrap;
    private Map<Integer,String> playerMainDeck = new HashMap<>();
    private Map<Integer,String> playerSideDeck = new HashMap<>();
    public static Map<String,Player> allPlayers = new HashMap<>();

    public Player (String nickname, DeckModel activeDeck, boolean isYourTurn) {
        this.nickname = nickname;
        this.activeDeck = activeDeck;
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.counterOfTurn = 0;
        for (String key : activeDeck.cardsInMainDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInMainDeck.get(key); i++)
                playerMainDeck.put(i, key);

        for (String key : activeDeck.cardsInSideDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(key); i++)
                playerMainDeck.put(i, key);

        allPlayers.put(this.nickname, this);
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

    public boolean getIsYourTurn() {
        return isYourTurn;
    }

    public boolean getIsYourMoveFinished() {
        return isYourMoveFinished;
    }

    public void setIsYourMoveFinished(boolean isYourMoveFinished) {
        this.isYourMoveFinished = isYourMoveFinished;
    }

    public int getNumberOfDeckCards() {
        return playerMainDeck.size();
    }

    public String addToHandCard() {
        return playerMainDeck.get(1);
    }

    public void removeFromDeckCards() {
        playerMainDeck.remove(1);
    }

    public static Player getPlayerByName(String playerNickname) {
        return allPlayers.get(playerNickname);
    }

}
