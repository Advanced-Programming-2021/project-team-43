package main.java.model;

import main.java.view.GameMatView;

import java.net.NetworkInterface;
import java.util.*;


public class Player {

    private final String nickname;
    private int lifePoint = 8000;
    private boolean isYourTurn;
    private int numberOfRound;
    private int counterOfTurn = 1;
    private boolean isYourMoveFinished;
    private static boolean canUseTrap = true;
    private boolean canSetSummonMonster = true;
    private boolean canDrawCard;
    private int numberOfWin = 0;
    private boolean canBattle;
    private final List<String> playerMainDeck = new ArrayList<>();
    private final List<String> playerSideDeck = new ArrayList<>();
    private final List<Integer> allLifePoints = new ArrayList<>();
    private static final Map<String, Player> allPlayers = new HashMap<>();
    private static int randomCardNumber;
    public static boolean isOneRound;

    public Player(String nickname, DeckModel activeDeck, boolean isYourTurn, int numberOfRound) {
        this.nickname = nickname;
        this.isYourTurn = isYourTurn;
        this.numberOfRound = numberOfRound;
        this.canDrawCard = !isYourTurn;
        isOneRound = (numberOfRound == 1);
        canBattle = !isYourTurn;
        new GameMatModel(nickname);
        List<HandCardZone> eachHandCard = new ArrayList<>();
        HandCardZone.allHandCards.put(nickname, eachHandCard);
        Map<Integer, MonsterZoneCard> eachMonsterCard = new HashMap<>();
        MonsterZoneCard.allMonsterCards.put(nickname, eachMonsterCard);
        Map<Integer, SpellTrapZoneCard> eachSpellTrapCard = new HashMap<>();
        SpellTrapZoneCard.allSpellTrapCards.put(nickname, eachSpellTrapCard);
        fillTheGameDecks(activeDeck);
        if (nickname.equals("pari")) {
            new HandCardZone("pari", "Mirage Dragon");
            new HandCardZone("pari", "Call of The Haunted");
            new HandCardZone("pari", "Advanced Ritual Art");
            new HandCardZone("pari", "Axe Raider");

            new HandCardZone("pari", "Herald of Creation");
            GameMatModel.getGameMatByNickname(nickname).addToGraveyard("Suijin");
            new MonsterZoneCard(nickname, "Axe Raider", "OO", false, false);
            new MonsterZoneCard(nickname, "Axe Raider", "OO", false, false);
        } else {
            if (nickname.equals("harry")) {
                new HandCardZone("harry", "Call of The Haunted");
                new HandCardZone("harry", "Advanced Ritual Art");
                new HandCardZone("harry", "Axe Raider");
                new HandCardZone("harry", "Axe Raider");
                new HandCardZone("harry", "Herald of Creation");
            } else {
                for (int i = 0; i < 5; i++)
                    new HandCardZone(nickname, drawCard(false));//////////////true//////////////////
            }
        }
        allPlayers.put(nickname, this);

    }

    public void startNewGame(DeckModel activeDeck, boolean isYourTurn) {
        allLifePoints.add(lifePoint);
        playerMainDeck.clear();
        playerSideDeck.clear();
        HandCardZone.allHandCards.get(nickname).clear();
        MonsterZoneCard.allMonsterCards.get(nickname).clear();
        SpellTrapZoneCard.allSpellTrapCards.get(nickname).clear();
        numberOfRound--;
        this.lifePoint = 8000;
        this.isYourTurn = isYourTurn;
        this.canDrawCard = !isYourTurn;
        canBattle = !isYourTurn;
        this.counterOfTurn = 0;
        this.canUseTrap = true;
        this.canSetSummonMonster = true;
        GameMatModel.getGameMatByNickname(nickname).setPhase(Phase.Draw_Phase);
        fillTheGameDecks(activeDeck);
        for (int i = 0; i < 5; i++)
            new HandCardZone(nickname, drawCard(true));
    }

    public void fillTheGameDecks(DeckModel activeDeck) {
        playerMainDeck.addAll(activeDeck.getArrayMain());
        String[] cardName2 = activeDeck.cardsInSideDeck.keySet().toArray(new String[0]);
        for (String eachCard : cardName2) {
            for (int i = 0; i < activeDeck.cardsInSideDeck.get(eachCard); i++)
                playerSideDeck.add(eachCard);
        }
    }

    public String drawCard(boolean isRandom) {
        if (isRandom) {
            Random random = new Random();
            randomCardNumber = random.nextInt(playerMainDeck.size() - 1);
        } else
            randomCardNumber = 0;
        String cardName = playerMainDeck.get(randomCardNumber);
        removeFromMainDeck();
        return cardName;
    }

    public int exchangeCard(int cardAddressInMainDeck, int cardAddressInSideDeck) {
        if (playerSideDeck.get(cardAddressInSideDeck) != null) {
            addToMainDeck(playerSideDeck.get(cardAddressInSideDeck));
            removeFromSideDeckByAddress(cardAddressInSideDeck);
            addToSideDeck(playerMainDeck.get(cardAddressInMainDeck));
            removeFromMainDeckByAddress(cardAddressInMainDeck);
            return 1;
        } else
            return 0;
    }

    public String getNickname() {
        return nickname;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
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

    public int getNumberOfRound() {
        return numberOfRound;
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

    public boolean getCanBattle() {
        return canBattle;
    }

    public void setCanBattle(boolean canBattle) {
        this.canBattle = canBattle;
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

    public void showMainDeck() {
        if (playerMainDeck.isEmpty())
            GameMatView.showInput("Main Deck Empty!");
        else {
            for (int i = 0; i < playerMainDeck.size(); i++)
                GameMatView.showInput(i + 1 + ". " + playerMainDeck.get(i));
        }
    }

    public void addToSideDeck(String cardName) {
        playerSideDeck.add(playerSideDeck.size(), cardName);
    }

    public void removeFromSideDeckByAddress(int address) {
        playerSideDeck.remove(address);
    }

    public int getNumberOfSideDeckCards() {
        return playerSideDeck.size();
    }

    public void showSideDeck() {
        for (int i = 0; i < playerSideDeck.size(); i++)
            GameMatView.showInput(i + 1 + ". " + playerMainDeck.get(i));
    }

    public int getMaxLifePoints() {
        if (allLifePoints.size() == 2) {
            if (allLifePoints.get(0) >= allLifePoints.get(1))
                return allLifePoints.get(0);
            else
                return allLifePoints.get(1);
        } else {
            if (allLifePoints.get(0) >= allLifePoints.get(1) && allLifePoints.get(0) >= allLifePoints.get(2))
                return allLifePoints.get(0);
            else if (allLifePoints.get(1) >= allLifePoints.get(0) && allLifePoints.get(1) >= allLifePoints.get(2))
                return allLifePoints.get(1);
            else
                return allLifePoints.get(2);
        }
    }

    public boolean doesThisModelAndTypeExist(String model, String type) {
        String kind;
        for (String eachCard : playerMainDeck) {
            kind = Card.getCardsByName(eachCard).getCardModel();
            if (kind.equals("Monster") && model.equals("Monster")) {
                if (MonsterCard.getMonsterByName(eachCard).getMonsterType().equals(type))
                    return true;
            } else if (kind.equals("Spell") && model.equals("Spell")) {
                if (SpellCard.getSpellCardByName(eachCard).getIcon().equals(type))
                    return true;
            }
        }
        return false;
    }

    public boolean doesAddressTypeMatchInMainDeck(int address, String model, String type) {
        String cardName = playerMainDeck.get(address);
        String cardModel = Card.getCardsByName(cardName).getCardModel();
        if (cardModel.equals("Monster") && model.equals("Monster"))
            return MonsterCard.getMonsterByName(cardName).getMonsterType().equals(type);
        else if (cardModel.equals("Spell") && model.equals("Spell"))
            return SpellCard.getSpellCardByName(cardName).getIcon().equals(type);
        return false;
    }

    public ArrayList<String> getPlayerDeck(String whichDeck) {
        if (whichDeck.equals("main"))
            return (ArrayList<String>) playerMainDeck;
        else
            return (ArrayList<String>) playerSideDeck;
    }

    public void changeTurn() {
        changeCounterOfTurn();
        setIsYourMoveFinished(false);
        int address = MonsterZoneCard.getAddressByMonsterName(nickname, "Mirage Dragon");
        if (address == -1)
            setCanUseTrap(true);
        else {
            if (MonsterZoneCard.getMonsterCardByAddress(address, nickname).getMode().equals("DO") || MonsterZoneCard.getMonsterCardByAddress(address, nickname).getMode().equals("OO")) {
                setCanUseTrap(false);
            }
        }
        setCanDrawCard(true);
        setCanSetSummonMonster(true);
        setCanBattle(true);
        GameMatModel.getGameMatByNickname(nickname).resetNumberOfDeadMonsterThisTurn();
    }

    public static Player getPlayerByName(String playerNickname) {
        return allPlayers.get(playerNickname);
    }


}