package model;

import view.GameMatView;

import java.util.*;


public class GameMatModel {

    private Phase phase;
    private String fieldZone = "";
    private int numberOfDeadMonsterThisTurn = 0;
    private final List<String> graveyard = new ArrayList<>();
    private static final Map<String, GameMatModel> playerGameMat = new HashMap<>();



    public GameMatModel (String playerNickname) {
        this.phase = Phase.Draw_Phase;
        playerGameMat.put(playerNickname, this);
    }

    public List<String> getGraveyard() {
        return graveyard;
    }

    public static Map<String, GameMatModel> getPlayerGameMat() {
        return playerGameMat;
    }

    public void startNewGame() {
        this.phase = Phase.Draw_Phase;
        removeFromFieldZone();
        graveyard.clear();
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getDeadCardNameByAddress(int address) {
        if (graveyard.size() - 1 < address||address<0)
            return null;
        else
            return graveyard.get(address);
    }

    public void addToGraveyard(String cardName) {
        graveyard.add(cardName);
    }

    public void removeFromGraveyardByAddress(int whichCard) {
        graveyard.remove(whichCard);
    }

    public String getKindOfDeadCardByAddress(int address) {
        if (address<0||graveyard.size() - 1 < address)
            return null;
        else
            return Card.getCardsByName(graveyard.get(address)).getCardModel();
    }

    public String getNameOfDeadCardByAddress(int address) {
        if (address<0||address > graveyard.size() - 1)
            return null;
        else
            return graveyard.get(address);
    }

    public int getNumberOfDeadCards() {
        return graveyard.size();
    }

    public int getNumberOfDeadCardByModel(String model) {
        int numberOfDeadCard = 0;
        for (String deadCardName : graveyard)
            if (Card.getCardsByName(deadCardName).getCardModel().equals(model))
                numberOfDeadCard++;
        return numberOfDeadCard;
    }

    public boolean doesThisModelAndTypeExist(String model, String type) {
        for (String eachCard : graveyard) {
            String kind = Card.getCardsByName(eachCard).getCardModel();
            if (kind.equals("Monster") && model.equals("Monster")) {
                if (MonsterCard.getMonsterByName(eachCard).getMonsterType().equals(type))
                    return true;
            }
            else if (kind.equals("Spell") && model.equals("Spell")) {
                if (SpellCard.getSpellCardByName(eachCard).getIcon().equals(type))
                    return true;
            }
        }
        return false;
    }

    public boolean doesAddressAndTypeMatch(int address, String model, String type) {
        String cardName = graveyard.get(address);
        if (model.equals("Monster") && Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
            return MonsterCard.getMonsterByName(cardName).getMonsterType().equals(type);
        }
        return false;
    }

    public boolean isAnySevenLevelMonsterInGraveyard() {
        for (String deadCardName : graveyard)
            if (Card.getCardsByName(deadCardName).getCardModel().equals("Monster"))
                if (MonsterCard.getMonsterByName(deadCardName).getLevel() > 6)
                    return true;
        return false;
    }

    public void showGraveyard() {
        if (graveyard.isEmpty())
            GameMatView.showInput("Graveyard Empty");
        else {
            int counter = 1;
            for (String eachDeadCard : graveyard) {
                GameMatView.showInput(counter + ". " + eachDeadCard + " : " + Card.getCardsByName(eachDeadCard).getDescription());
                counter++;
            }
        }
    }

    public String getFieldZone() {
        return fieldZone;
    }

    public void addToFieldZone(String cardName, String mode) {
        fieldZone = cardName + "/" + mode;
    }

    public void changeModeOfFieldCard(String mode) {
        String[] split = fieldZone.split("/");
        fieldZone = split[0] + "/" + mode;
    }//use when activate a set field card

    public void removeFromFieldZone() {
        fieldZone = "";
    }

    public int getNumberOfDeadMonsterThisTurn() {
        return numberOfDeadMonsterThisTurn;
    }

    public void changeNumberOfDeadMonsterThisTurn() {
        numberOfDeadMonsterThisTurn++;
    }//use in attack method

    public void resetNumberOfDeadMonsterThisTurn() {
        numberOfDeadMonsterThisTurn = 0;
    }

    public static GameMatModel getGameMatByNickname(String playerNickname) {
        return playerGameMat.get(playerNickname);
    }



}