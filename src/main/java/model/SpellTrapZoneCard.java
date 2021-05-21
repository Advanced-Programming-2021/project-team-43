package main.java.model;
import java.util.*;
import main.java.controller.GameMatController;
import main.java.view.*;


public class SpellTrapZoneCard {
    private final String playerNickname;
    private final String spellTrapName;
    private String mode;
    private final String kind;
    private final String icon;
    private final int address;
    private int turnCounter = 0;
    private boolean isSetInThisTurn;
    private final Map<String, Integer> relatedMonsterAddress = new HashMap<>();
    public static final Map<String, Map<Integer, SpellTrapZoneCard>> allSpellTrapCards = new HashMap<>();

    public SpellTrapZoneCard(String playerNickname, String spellTrapName, String mode) {
        this.playerNickname = playerNickname;
        this.spellTrapName = spellTrapName;
        this.mode = mode;
        this.kind = Card.getCardsByName(spellTrapName).getCardModel();
        if (kind.equals("Spell"))
            this.icon = SpellCard.getSpellCardByName(spellTrapName).getIcon();
        else
            this.icon = TrapCard.getTrapCardByName(spellTrapName).getIcon();
        this.address = allSpellTrapCards.get(playerNickname).size() + 1;
        allSpellTrapCards.get(playerNickname).put(address, this);
    }

    public String getSpellTrapName() {
        return spellTrapName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getKind() {
        return kind;
    }

    public String getIcon() {
        return icon;
    }

    public int getAddress() {
        return address;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }

    public void changeTurnCounter() {
        turnCounter--;
    }

    public boolean getIsSetInThisTurn() {
        return isSetInThisTurn;
    }

    public void setIsSetInThisTurn(boolean isSetInThisTurn) {
        this.isSetInThisTurn = isSetInThisTurn;
    }

    public static int getNumberOfFullHouse(String playerNickname) {
        if (allSpellTrapCards.get(playerNickname) != null)
            return allSpellTrapCards.get(playerNickname).size();
        else
            return 0;
    }

    public Map<String, Integer> getRelatedMonsterAddress() {
        return relatedMonsterAddress;
    }

    public void setRelatedMonsterAddress(String whoseMonster, int address) {
        System.out.println("porrrr "+ whoseMonster+"   "+ address);///////////////////////////////////



        this.relatedMonsterAddress.put(whoseMonster, address);
    }

    public void removeSpellTrapFromZone() {///////////check this
        GameMatModel.getGameMatByNickname(playerNickname).addToGraveyard(spellTrapName);
        allSpellTrapCards.get(playerNickname).remove(address);
    }

    public static boolean isAnyTrapQuickSpellSet(String playerNickname) {
        String cardName;
        for (int i = 1; i < 6; i++) {
            SpellTrapZoneCard spellTrapCard = allSpellTrapCards.get(playerNickname).get(i);
            if (spellTrapCard != null) {
                cardName = allSpellTrapCards.get(playerNickname).get(i).getSpellTrapName();
                if (spellTrapCard.getKind().equals("Spell") && spellTrapCard.getMode().equals("H")) {
                    if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Quick-play"))
                        return true;
                }
                else if (allSpellTrapCards.get(playerNickname).get(i).getKind().equals("Trap") && allSpellTrapCards.get(playerNickname).get(i).getMode().equals("H"))
                    return true;
            }
        }
        return false;
    }

    public static boolean isIsAttackedTrapSet(String playerNickname) {
        String cardName;
        for (int i = 0; i < 6; i++) {
            if (allSpellTrapCards.get(playerNickname).get(i) != null) {
                cardName = allSpellTrapCards.get(playerNickname).get(i).getSpellTrapName();
                if (allSpellTrapCards.get(playerNickname).get(i).getKind().equals("Trap")) {
                    if (cardName.equals("Negate Attack") || cardName.equals("Mirror Force") || cardName.equals("Magic Cylinder")) {
                        System.out.println(cardName);//////////////////////////
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int doesThisCardNameExist(String playerNickname, String cardName) {
        for (int i = 1; i < 6; i++) {
            if (allSpellTrapCards.get(playerNickname).get(i) != null)
                if (allSpellTrapCards.get(playerNickname).get(i).getSpellTrapName().equals(cardName))
                    return i;
        }
        return -1;
    }

    public static int getAddressOfSpellByIcon(String playerNickname, String icon, String spellName) {
        for (int i = 1; i < 6; i++)
            if (allSpellTrapCards.get(playerNickname).get(i) != null && allSpellTrapCards.get(playerNickname).get(i).getIcon().equals(icon))
                if (allSpellTrapCards.get(playerNickname).get(i).getSpellTrapName().equals(spellName))
                    return i;
        return 0;
    }

    public static boolean isThisTrapActivated(String playerNickname, String trapName) {
        SpellTrapZoneCard trapCard;
        for (int i = 1; i < 6; i++) {
            trapCard = allSpellTrapCards.get(playerNickname).get(i);
            if (trapCard.getKind().equals("Trap"))
                if (trapCard.getSpellTrapName().equals(trapName) && trapCard.getMode().equals("O"))
                    return true;
        }
        return false;
    }

    public static boolean isThisSpellActivated(String playerNickname, String spellName) {
        SpellTrapZoneCard spellCard;
        for (int i = 1; i < 6; i++) {
            spellCard = allSpellTrapCards.get(playerNickname).get(i);
            if (spellCard != null)
                if (spellCard.getKind().equals("Spell"))
                    if (spellCard.getSpellTrapName().equals(spellName) && spellCard.getMode().equals("O"))
                        return true;
        }
        return false;
    }

    public static String[] getAllSpellTrapMode(String playerNickname) {
        String[] allSpellsMode = new String[6];
        if (getAllSpellTrapByPlayerName(playerNickname) == null)
            for (int i = 1; i < 6; i++)
                allSpellsMode[i] = "E";
        else {
            for (int i = 1; i < 6; i++) {
                if (!getAllSpellTrapByPlayerName(playerNickname).containsKey(i))
                    allSpellsMode[i] = "E";
                else
                    allSpellsMode[i] = getAllSpellTrapByPlayerName(playerNickname).get(i).getMode();
            }
        }
        return allSpellsMode;
    }

    public static SpellTrapZoneCard getSpellCardByAddress(int address, String playerNickname) {
        if (allSpellTrapCards.get(playerNickname).get(address) != null)
            return allSpellTrapCards.get(playerNickname).get(address);
        else
            return null;
    }

    public static Map<Integer, SpellTrapZoneCard> getAllSpellTrapByPlayerName(String playerNickname) {
        return allSpellTrapCards.get(playerNickname);
    }

    public static HashMap<Integer, HashMap<Integer, String>> effectStack = new HashMap<>();

    public static HashMap<Integer, HashMap<Integer, String>> getEffectStack() {
        return effectStack;
    }

//    public static void chain(String rivalNickname, String ownNickname) {
//        Integer[] cards = allSpellTrapCards.get(rivalNickname).keySet().toArray(new Integer[0]);
//        int address1 = -1;
//        int address2 = -1;
//        for (int i = 0; i < 5; i++) {
//            if (cards[i] != null) {
//                if (allSpellTrapCards.get(rivalNickname).get(cards[i]).getKind().equals("Spell") &&
//                        allSpellTrapCards.get(rivalNickname).get(cards[i]).getIcon().equals("Quick-play") &&
//                        allSpellTrapCards.get(rivalNickname).get(cards[i]).getMode().equals("H")) {
//                    GameMatView.showInput("now it will be " + rivalNickname + "’s turn");
//                    GameMatController.showGameBoard();
//                    address1 = cards[i];
//                    break;
//                }
//                if (allSpellTrapCards.get(rivalNickname).get(cards[i]).getKind().equals("Trap") &&
//                        allSpellTrapCards.get(rivalNickname).get(cards[i]).getMode().equals("H")) {
//                    GameMatView.showInput("now it will be " + rivalNickname + "’s turn");
//                    GameMatController.showGameBoard();
//                    address2 = cards[i];
//                    break;
//                }
//            }
//            if (address1 != -1) {
//
//                while (true) {
//                    GameMatView.showInput("do you want to activate your spell with address " + address1 + " ? (yes/no)");
//                    String command4 = GameMatView.getCommand();
//                    if (command4.equals("yes")) {
//                        HashMap<Integer, String> hash = new HashMap<>();
//                        hash.put(cards[i], rivalNickname);
//                        effectStack.put(i, hash);
//                        while (true) {
//                            String command = GameMatView.getCommand();
//                            if (command.matches("activate \\s*effect")) {
//                                SpellTrapZoneCard.getSpellCardByAddress(address1, rivalNickname).setMode("O");
//                                GameMatView.showInput("spell activated");
//                                break;
//                            }
//                            GameMatView.showInput("it’s not your turn to play this kind of moves");
//                        }
//                        break;
//                    }
//                    String command2 = GameMatView.getCommand();
//                    if (command2.equals("no")) {
//                        GameMatView.showInput("now it will be " + ownNickname + "’s turn");
//                        GameMatController.showGameBoard();
//                        break;
//                    }
//                    GameMatView.showInput("");
//                }
//            }
//
//            if (address2 != -1) {
//                while (true) {
//                    GameMatView.showInput("do you want to activate your trap with address " + address2 + " ? (yes/no)");
//                    String command5 = GameMatView.getCommand();
//                    if (command5.equals("yes")) {
//                        HashMap<Integer, String> hash2 = new HashMap<>();
//                        hash2.put(cards[i], rivalNickname);
//                        effectStack.put(i, hash2);
//                        while (true) {
//                            String command3 = GameMatView.getCommand();
//                            if (command3.matches("activate \\s*effect")) {
//                                SpellTrapZoneCard.getSpellCardByAddress(address2, rivalNickname).setMode("O");
//                                GameMatView.showInput("spell activated");
//                                break;
//                            }
//                            GameMatView.showInput("it’s not your turn to play this kind of moves");
//                        }
//                        break;
//                    }
//                    String command3 = GameMatView.getCommand();
//                    if (command3.equals("no")) {
//                        GameMatView.showInput("now it will be " + ownNickname + "’s turn");
//                        GameMatController.showGameBoard();
//                        break;
//                    }
//                }
//            }
//        }
//    }

}