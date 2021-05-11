package model;
import view.GameMatView;
import java.util.*;


public class GameMatModel {

    private Phase phase;
    private String fieldZone = "";
    private final List<String> graveyard = new ArrayList<>();
    private static final Map<String,GameMatModel> playerGameMat = new HashMap<>();

    public GameMatModel (String playerNickname) {
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

    public void removeFromGraveyardByAddress(int whichCard) {
        graveyard.remove(whichCard);
    }

    public String getKindOfDeadCardByAddress(int address) {
        if (graveyard.get(address).isEmpty())
            return null;
        else
            return Card.getCardsByName(graveyard.get(address)).getCardModel();
    }

    public String getNameOfDeadCardByAddress(int address) {
        if (graveyard.get(address).isEmpty())
            return null;
        else
            return graveyard.get(address);
    }

    public boolean isAnySevenLevelMonsterInGraveyard() {
        for (String deadCardName : graveyard) {
            if (Card.getCardsByName(deadCardName).getCardModel().equals("Monster")) {
                if (MonsterCard.getMonsterByName(deadCardName).getLevel() > 6)
                    return true;
            }
        }
        return false;
    }


    public int getNumberOfDeadCards() {
        return graveyard.size();
    }

    public int getNumberOfDeadMonster() {
        int numberOfDeadMonsters = 0;
        for (String deadCardName : graveyard)
            if (Card.getCardsByName(deadCardName).getCardModel().equals("Monster"))
                numberOfDeadMonsters++;
        return numberOfDeadMonsters;
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
        fieldZone = cardName + " " + mode;
    }

    public void removeFromFieldZone() {
        fieldZone = "";
    }

    public static GameMatModel getGameMatByNickname(String playerNickname) {
        return playerGameMat.get(playerNickname);
    }

}
