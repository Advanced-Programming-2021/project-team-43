package model;
import view.GameMatView;
import java.util.*;


public class GameMatModel {

    private String playerNickname;
    private Phase phase;
    private List<String> graveyard = new ArrayList<>();
    private String fieldZone = "";
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
    }

    public void removeFromGraveyard(String cardName) {
        graveyard.removeIf(eachCard -> eachCard.equals(cardName));
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

    public String getFieldZone() {
        return fieldZone;
    }

    public int getNumberOfDeadCards() {
        return graveyard.size();
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
