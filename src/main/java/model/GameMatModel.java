package main.java.model;
import main.java.view.*;
import java.util.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////
public class GameMatModel {

    private String playerNickname;
    private Phase phase;
    private boolean haveLostMonsterThisTurn;
    private List<String> graveyard = new ArrayList<>();
    private String fieldZone = "";
    private int numberOfDeadMonster = 0;
    private boolean isOwnFieldZoneSelected;
    private static Map<String,GameMatModel> playerGameMat = new HashMap<>();

    public GameMatModel (String playerNickname) {
        this.playerNickname = playerNickname;
        this.phase = Phase.Draw_Phase;
        playerGameMat.put(playerNickname, this);
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void addToGraveyard(String cardName) {
        graveyard.add(cardName);
        if (Card.getCardsByName(cardName).getCardModel().equals("Monster"))
            numberOfDeadMonster++;
    }

    public void removeFromGraveyard(String cardName) {
        graveyard.removeIf(eachCard -> eachCard.equals(cardName));
        if (Card.getCardsByName(cardName).getCardModel().equals("Monster"))
            numberOfDeadMonster--;
    }

    public void addToFieldZone(String cardName) {
        fieldZone = cardName;
    }

    public void removeFromFieldZone(String cardName) {
        fieldZone = "";
    }

    public void setOwnFieldZoneSelected() {
        this.isOwnFieldZoneSelected = true;
    }

    public boolean getOwnFieldZoneSelected() {
        return isOwnFieldZoneSelected;
    }

    public void setHaveLostMonsterThisTurn(boolean haveLostMonsterThisTurn) {
        this.haveLostMonsterThisTurn = haveLostMonsterThisTurn;
    }

    public boolean getHaveLostMonsterThisTurn() {
        return haveLostMonsterThisTurn;
    }

    public String getFieldZone() {
        return fieldZone;
    }

    public int getNumberOfDeadCards() {
        return graveyard.size();
    }

    public int getNumberOfDeadMonster() {
        return numberOfDeadMonster;
    }

    public void showGraveyard() {
        if (graveyard.isEmpty())
            GameMatView.showInput("graveyard empty");
        else {
            int counter = 1;
            for (String eachDeadCard : graveyard) {
                GameMatView.showInput(counter + ". " + eachDeadCard + " : " + Card.getCardsByName(eachDeadCard).getDescription());
                counter++;
            }
        }
    }

    public static GameMatModel getGameMatByNickname(String playerNickname) {
        return playerGameMat.get(playerNickname);
    }

}
