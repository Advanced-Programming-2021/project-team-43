package model;
import java.util.*;


public class Player {

    private final String nickname;
    private int lifePoint;
    private boolean isYourTurn;
    private int numberOfRound;
    private int counterOfTurn;
    private boolean isYourMoveFinished;
    private boolean canUseTrap;
    private boolean canSetSummonMonster;
    private int numberOfWin;
    private boolean canDrawCard;
    private final List<String> playerMainDeck = new ArrayList<>();
    private final List<String> playerSideDeck = new ArrayList<>();
    private static final Map<String,Player> allPlayers = new HashMap<>();
    private static int randomCardNumber;


    public Player (String nickname, DeckModel activeDeck, boolean isYourTurn, int numberOfRound) {
        this.nickname = nickname;
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.numberOfRound = numberOfRound;
        this.counterOfTurn = 0;
        this.canUseTrap = true;
        this.canSetSummonMonster = true;
        this.numberOfWin = 0;
        this.canDrawCard = !isYourTurn;
        int counter = -1;
        for (String key : activeDeck.cardsInMainDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInMainDeck.get(key); i++)
                playerMainDeck.add(++counter, key);

        counter = -1;
        for (String key : activeDeck.cardsInSideDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(key); i++)
                playerSideDeck.add(++counter, key);
        firstDrawCard();
        allPlayers.put(this.nickname, this);
    }

    public void newGame(DeckModel activeDeck, boolean isYourTurn) {
        changeNumberOfRound();
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.counterOfTurn = 0;
        this.canUseTrap = true;
        this.canSetSummonMonster = true;
        this.canDrawCard = !isYourTurn;
        int counter = -1;
        for (String key : activeDeck.cardsInMainDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInMainDeck.get(key); i++)
                playerMainDeck.add(++counter, key);

        counter = -1;
        for (String key : activeDeck.cardsInSideDeck.keySet())
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(key); i++)
                playerSideDeck.add(++counter, key);
        firstDrawCard();
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void changeLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }

    public boolean getIsYourTurn() {
        return isYourTurn;
    }

    public void setIsYourTurn(boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
    }

    private int getNumberOfRound() {
        return numberOfRound;
    }

    public void changeNumberOfRound() {
        numberOfRound--;
    }

    public int getCounterOfTurn() {
        return counterOfTurn;
    }

    public void changeCounterOfTurn() {
        counterOfTurn++;
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

    public boolean getCanSetSummonMonster() {
        return canSetSummonMonster;
    }

    public void setCanSetSummonMonster(boolean canSetSummonMonster) {
        this.canSetSummonMonster = canSetSummonMonster;
    }

    public int getNumberOfWin() {
        return numberOfWin;
    }

    public void changeNumberOfWin() {
        numberOfWin++;
    }

    public boolean getCanDrawCard() {
        return canDrawCard;
    }

    public void setCanDrawCard(boolean canDrawCard) {
        this.canDrawCard = canDrawCard;
    }

    public void addToMainDeck(String cardName) {
        playerMainDeck.add(playerMainDeck.size(), cardName);
    }

    public void removeFromMainDeck() {
        playerMainDeck.remove(randomCardNumber);
    }

    public void removeFromMainDeckByAddress(int address) {
        playerMainDeck.remove(address);
    }

    public String getCardNameByAddress(int address) {
        return playerMainDeck.get(address);
    }

    public int getNumberOfMainDeckCards() {
        return playerMainDeck.size();
    }

    public void addToSideDeck(String cardName) {
        playerSideDeck.add(playerSideDeck.size(), cardName);
    }

    public int getNumberOfSideDeckCards() {
        return playerSideDeck.size();
    }

    public String drawCard(boolean isRandom) {
        if (isRandom) {
            Random random = new Random();
            randomCardNumber = random.nextInt(playerMainDeck.size() - 1);
        }
        else
            randomCardNumber = 0;
        return playerMainDeck.get(randomCardNumber);
    }

    public void firstDrawCard() {
        for (int i = 0; i < 5; i++) {
            new HandCardZone(nickname, playerMainDeck.get(i));
            playerMainDeck.remove(0);
        }
    }

    public int getAddressOfFieldSpellFromDeck() {
        for (int i = 0; i < playerMainDeck.size(); i++) {
            if (Card.getCardsByName(playerMainDeck.get(i)).getCardModel().equals("Spell"))
                if (SpellCard.getSpellCardByName(playerMainDeck.get(i)).getIcon().equals("Field"))
                    return i;
        }
        return -1;
    }

    public ArrayList<String> getPlayerDeck(String whichDeck) {
        if (whichDeck.equals("main"))
            return (ArrayList<String>)playerMainDeck;
        else
            return (ArrayList<String>) playerSideDeck;
    }

    public void changeTurn() {
        changeCounterOfTurn();
        setIsYourMoveFinished(false);
        setCanUseTrap(true);
        setCanSetSummonMonster(true);
        setCanDrawCard(true);
    }

    public static Player getPlayerByName(String playerNickname) {
        return allPlayers.get(playerNickname);
    }

}
