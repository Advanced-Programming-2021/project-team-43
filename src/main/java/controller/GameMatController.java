package controller;
import view.GameMatView;
import model.*;
import java.util.regex.*;


public class GameMatController {

    private static String selectedOwnCard = "";
    private static String selectedRivalCard = "";
    public static String onlineUser = "";
    public static String rivalUser = "";
    private static String command;

    public static void findMatcher(String ownName, String rivalName, int roundNumber) {
        String whoseFirst = PickFirstPlayer.chose(ownName, rivalName);
        //UserModel ownUser = UserModel.getUserByUsername(ownName);
        //UserModel rivalUser = UserModel.getUserByUsername(rivalName);
        //new Player(ownUser.getNickname(), ownUser.userAllDecks.get(ownUser.getActiveDeck()), whoseFirst.equals(ownName), roundNumber);
        //new Player(rivalUser.getNickname(), rivalUser.userAllDecks.get(rivalUser.getActiveDeck()), whoseFirst.equals(rivalName), roundNumber);
        Phase currentPhase;
        while (true) {
            command = GameMatView.getCommand();
            currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
            if (selectCommand(command) == 1) {
                showGameBoard();
                continue;
            }
            if (summon(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (changePhase(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (set(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (changeMonsterPosition(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (flipSummon(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (attack(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (attackDirect(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (activateSpellEffect(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (showSelectedCard(command) == 1) {
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+graveyard").find()) {
                GameMatModel.getGameMatByNickname(onlineUser).showGraveyard();
                backCommand();
                continue;
            }
            if (getMatcher(command,"menu exit").find()) {
                break;
            }
            GameMatView.showInput("invalid command");
        }
    }



    public static int summon(String command, Phase currentPhase) {
        if (getMatcher(command, "summon").find()) {
            if (errorOfNoCardSelected("own")) {
                String monsterName = getSpecificPartOfSelectedCard(1);
                HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                if (!selectedOwnCard.startsWith("Hand") || !handCard.getKind().equals("Monster"))
                    GameMatView.showInput("You can’t summon this card");
                else if (errorOfWrongPhase("summon", currentPhase)) {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                        GameMatView.showInput("Monster card zone is full");
                    else if (!Player.getPlayerByName(onlineUser).getCanSetSummonMonster())
                        GameMatView.showInput("You already summoned/set on this turn");
                    else {
                        int monsterLevel = MonsterCard.getMonsterByName(monsterName).getLevel();
                        String response;
                        if (monsterLevel <= 4) {
                            selectedOwnCard = "";
                            Player.getPlayerByName(onlineUser).setCanSetSummonMonster(false);
                            addToMonsterZoneCard(monsterName, "OO");//new
                            handCard.removeFromHandCard();
                            GameMatView.showInput("Summoned successfully");
                            MonsterEffect.monsterEffectController(-1, onlineUser, rivalUser, -1, -1, -1, "Normal", MonsterZoneCard.getNumberOfFullHouse(onlineUser));
                        }
                        else if (monsterLevel == 5 || monsterLevel == 6) {
                            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                                GameMatView.showInput("There are not enough cards for tribute");
                            else {
                                response = GameMatView.getCommand();
                                if (response.equals("cancel")) {
                                    selectedOwnCard = "";
                                    return 1;
                                }
                                int victimAddress = Integer.parseInt(response);
                                if (MonsterZoneCard.getMonsterCardByAddress(victimAddress, onlineUser) == null)
                                    GameMatView.showInput("There is no monster in this address");
                                else {
                                    selectedOwnCard = "";
                                    MonsterZoneCard.getAllMonstersByPlayerName(onlineUser).get(victimAddress).removeMonsterFromZone();
                                    Player.getPlayerByName(onlineUser).setCanSetSummonMonster(false);
                                    addToMonsterZoneCard(monsterName, "OO");
                                    GameMatView.showInput("Summoned successfully");
                                    MonsterEffect.monsterEffectController(-1, onlineUser, rivalUser, victimAddress, -1, -1, "NotNormal", MonsterZoneCard.getNumberOfFullHouse(onlineUser));
                                }
                            }
                        }
                        else {
                            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                                GameMatView.showInput("There are not enough cards for tribute");
                            else {
                                response = GameMatView.getCommand();
                                if (command.equals("cancel")) {
                                    selectedOwnCard = "";
                                    return 1;
                                }
                                int victimOneAddress = Integer.parseInt(response);
                                response = GameMatView.getCommand();
                                if (response.equals("cancel")) {
                                    selectedOwnCard = "";
                                    return 1;
                                }
                                int victimTwoAddress = Integer.parseInt(response);
                                if (MonsterZoneCard.getMonsterCardByAddress(victimOneAddress, onlineUser) == null || MonsterZoneCard.getMonsterCardByAddress(victimTwoAddress, onlineUser) == null)
                                    GameMatView.showInput("There is no monster in one of these addresses");
                                else {
                                    selectedOwnCard = "";
                                    MonsterZoneCard.getAllMonstersByPlayerName(onlineUser).get(victimOneAddress).removeMonsterFromZone();
                                    MonsterZoneCard.getAllMonstersByPlayerName(onlineUser).get(victimTwoAddress).removeMonsterFromZone();
                                    Player.getPlayerByName(onlineUser).setCanSetSummonMonster(false);
                                    addToMonsterZoneCard(monsterName, "OO");
                                    GameMatView.showInput("Summoned successfully");
                                    MonsterEffect.monsterEffectController(-1, onlineUser, rivalUser, victimOneAddress, victimTwoAddress, -1, "NotNormal", MonsterZoneCard.getNumberOfFullHouse(onlineUser));
                                }
                            }
                        }
                    }
                }
                return 1;
            }
        }
        return 0;
    }


    public static int selectCommand(String command) {
        if (command.startsWith("select")) {
            if (getMatcher(command,"select\\s+--monster\\s+(\\d+)").find()) {
                selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--monster\\s+(\\d+)").group(1)), true);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--monster\\s+(\\d+)\\s+--opponent").find()) {
                selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--monster\\s+(\\d+)\\s+--opponent").group(1)), false);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--opponent\\s+--monster\\s+(\\d+)").find()) {
                selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--opponent\\s+--monster\\s+(\\d+)").group(1)), false);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--spell\\s+(\\d+)").find()) {
                selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)").group(1)), true);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").find()) {
                selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").group(1)), false);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--opponent\\s+--spell\\s+(\\d+)").find()) {
                selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").group(1)), false);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--field").find()) {
                selectFieldCard(true);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--field\\s+--opponent").find() || getMatcher(command,"select\\s+--opponent\\s+--field").find()) {
                selectFieldCard(false);
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+--hand\\s+(\\d+)").find()) {
                selectHandCard(Integer.parseInt(getMatcher(command,"select\\s+--hand\\s+(\\d+)").group(1)));
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+-d").find()) {
                selectDelete();
                showGameBoard();
                return 1;
            }
        }
        return 0;
    }


    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (address < 1 || address > 5)
            GameMatView.showInput("Invalid selection");
        else {
            if (isOwnMonsterCard) {
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, onlineUser);
                if (ownMonsterCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    selectedOwnCard = "Monster " + ownMonsterCard.getMonsterName() + " " + address;
                    ownMonsterCard.setIsSelected(true);
                }
            }
            else {
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                if (rivalMonsterCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    selectedRivalCard = "Monster " + rivalMonsterCard.getMonsterName() + " " + address;
                    rivalMonsterCard.setIsSelected(true);
                }
            }
        }
    }

    public static void selectSpellCard(int address, boolean isOwnSpellCard) {
        if (address < 1 || address > 5)
            GameMatView.showInput("Invalid selection");
        else {
            if (isOwnSpellCard) {
                SpellTrapZoneCard ownSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser);
                if (ownSpellCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    if (Card.getCardsByName(ownSpellCard.getSpellTrapName()).getCardModel().equals("Spell"))
                        selectedOwnCard = "Spell " + ownSpellCard.getSpellTrapName() + " " + address;
                    else
                        selectedOwnCard = "Trap " + ownSpellCard.getSpellTrapName() + " " + address;
                    ownSpellCard.setIsSelected(true);
                }
            }
            else {
                SpellTrapZoneCard rivalSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
                if (rivalSpellCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    if (Card.getCardsByName(rivalSpellCard.getSpellTrapName()).getCardModel().equals("Spell"))
                        selectedRivalCard = "Spell " + rivalSpellCard.getSpellTrapName() + " " + address;
                    else
                        selectedRivalCard = "Trap " + rivalSpellCard.getSpellTrapName() + " " + address;
                    rivalSpellCard.setIsSelected(true);
                }
            }
        }
    }

    public static void selectFieldCard(boolean isOwnField) {
        if (isOwnField) {
            GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
            if (ownGameMat.getFieldZone().equals(""))
                GameMatView.showInput("Invalid selection");
            else {
                GameMatView.showInput("Card selected");
                selectedOwnCard = "Field";
                ownGameMat.setOwnFieldZoneSelected();
            }
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (rivalGameMat.getFieldZone().equals(""))
                GameMatView.showInput("Invalid selection");
            else {
                GameMatView.showInput("Card selected");
                selectedRivalCard = "Field";
                rivalGameMat.setOwnFieldZoneSelected();
            }
        }
    }

    public static void selectHandCard(int address) {
        if (address < 1 || address > HandCardZone.getNumberOfFullHouse(onlineUser))
            GameMatView.showInput("Invalid selection");
        else {
            GameMatView.showInput("Card selected");
            HandCardZone handCard = HandCardZone.getHandCardByAddress(address, onlineUser);
            selectedOwnCard = "Hand " + handCard.getCardName() + " " + address;
            handCard.setIsSelected(true);
        }
    }

    public static void selectDelete() {
//        int whoseCardIsSelected = errorOfNoCardSelected("own");
//        if (whoseCardIsSelected != 0) {
//            GameMatView.showInput("Card deselected");
//            String[] split;
//            if (whoseCardIsSelected == 1) {
//                split = selectedOwnCard.split(" ");
//                selectedOwnCard = "";
//                switch (split[0]) {
//                    case "Monster" -> MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).setIsSelected(false);
//                    case "Spell" -> SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser).setIsSelected(false);
//                    case "Hand" -> HandCardZone.getHandCardByAddress(Integer.parseInt(split[1]), onlineUser).setIsSelected(false);
//                }
//            }
//            else {
//                split = selectedRivalCard.split(" ");
//                selectedRivalCard = "";
//                switch (split[0]) {
//                    case "Monster" -> MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), rivalUser).setIsSelected(false);
//                    case "Spell" -> SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), rivalUser).setIsSelected(false);
//                }
//            }
//        }
    }

    public static boolean errorOfNoCardSelected(String whoseCard) {
        if (whoseCard.equals("own")) {
            if (selectedOwnCard.equals("")) {
                GameMatView.showInput("No card is selected yet");
                return false;//own card is not selected
            }
            else
                return true;
        }
        else {
            if (selectedRivalCard.equals("")) {
                GameMatView.showInput("No Rival card is selected yet");
                return false;//rival card is not selected
            }
            else
                return true;
        }
    }





    public static void addToMonsterZoneCard(String monsterName, String mode) {
        if (monsterName.equals("Scanner")) {
            GameMatView.showInput("Which card for Scanner?");
            String whichCard = GameMatView.getCommand();
            new MonsterZoneCard(onlineUser, whichCard, mode, true);
        }
        else
            new MonsterZoneCard(onlineUser, monsterName, mode, false);
    }


    public static void addToSpellTrapZoneCard(String spellTrapName, String mode) {
        new SpellTrapZoneCard(onlineUser, spellTrapName, mode);
    }

    public static String getSpecificPartOfSelectedCard(int whichPart) {
        String[] split = selectedOwnCard.split(" ");
        return split[whichPart];
    }

    public static boolean errorOfWrongPhase(String whichAction, Phase currentPhase) {
        if (whichAction.equals("summon") || whichAction.equals("set")) {
            if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                GameMatView.showInput("Action not allowed in this phase");
                return false;//wrong Phase
            }
            else
                return true;
        }
        return false;
    }

    public static int set(String command, Phase currentPhase) {
        if (getMatcher(command,"set").find()) {
            if (errorOfNoCardSelected("own")) {
                if (!selectedOwnCard.startsWith("Hand"))
                    GameMatView.showInput("You can’t set this card");
                else if (errorOfWrongPhase("set", currentPhase)) {
                    HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                    String cardName = handCard.getCardName();
                    if (handCard.getKind().equals("Monster")) {
                        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                            GameMatView.showInput("Monster card zone is full");
                        else if (Player.getPlayerByName(onlineUser).getCanSetSummonMonster())
                            GameMatView.showInput("You already summoned/set on this turn");
                        else {
                            selectedOwnCard = "";
                            handCard.removeFromHandCard();
                            addToMonsterZoneCard(cardName, "DH");
                            Player.getPlayerByName(onlineUser).setCanSetSummonMonster(false);
                            GameMatView.showInput("Set successfully");
                        }
                    }
                    else if (handCard.getKind().equals("Spell")) {
                        if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                            GameMatView.showInput("Spell card zone is full");
                        else {
                            handCard.removeFromHandCard();
                            if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Field")) {
                                GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                                if (!ownGameMat.getFieldZone().equals(""))
                                    ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                                GameMatModel.getGameMatByNickname(onlineUser).addToFieldZone(cardName + " H");
                            } else
                                addToSpellTrapZoneCard(handCard.getCardName(), "H");
                            GameMatView.showInput("Set successfully");
                            selectedOwnCard = "";
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int changeMonsterPosition(String command, Phase currentPhase) {
        if (getMatcher(command,"set\\s+--position\\s+(attack|defense)").find()) {
            String mode = getMatcher(command,"set\\s+--position\\s+(attack|defense)").group(1);
            if (errorOfNoCardSelected("own")) {
                if (!getSpecificPartOfSelectedCard(0).equals("Monster")) {
                    GameMatView.showInput("You can’t change this card position");
                    return 1;
                }
                MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                    GameMatView.showInput("You can’t do this action in this phase");
                else if (mode.equals("attack") && !monsterCard.getMode().equals("DO"))
                    GameMatView.showInput("This card is already in the wanted position");
                else if (mode.equals("defend") && !monsterCard.getMode().equals("OO"))
                    GameMatView.showInput("This card is already in the wanted position");
                else if (monsterCard.getHaveChangedPositionThisTurn())
                    GameMatView.showInput("You already changed this card position in this turn");
                else {
                    selectedOwnCard = "";
                    GameMatView.showInput("Monster card position changed successfully");
                    monsterCard.setHaveChangedPositionThisTurn(true);
                    if (mode.equals("attack"))
                        monsterCard.setMode("OO");
                    else
                        monsterCard.setMode("DO");
                }
            }
        }
        return 0;
    }

    public static int flipSummon(String command, Phase currentPhase) {
        if (getMatcher(command,"flip-summon").find()) {
            if (errorOfNoCardSelected("own")) {
                if (!getSpecificPartOfSelectedCard(0).equals("Monster")) {
                    GameMatView.showInput("You can’t change this card position");
                    return 1;
                }
                MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                    GameMatView.showInput("You can’t do this action in this phase");
                else if (!monsterCard.getMode().equals("set") || monsterCard.getHaveChangedPositionThisTurn()) {
                    GameMatView.showInput("You can’t flip summon this card");
                } else {
                    selectedOwnCard = "";
                    monsterCard.setMode("OO");
                    monsterCard.setHaveChangedPositionThisTurn(true);
                    GameMatView.showInput("Flip summoned successfully");
                }
            }
        }
        return 0;
    }

    public static int attack(String command, Phase currentPhase) {
        if (getMatcher(command, "attack\\s+(\\d+)").find()) {
            int address = Integer.parseInt(getMatcher(command, "attack\\s+(\\d+)").group(1));
            if (errorOfNoCardSelected("own")) {
                if (!getSpecificPartOfSelectedCard(0).equals("Monster"))
                    GameMatView.showInput("You can’t attack with this card");
                else if (currentPhase.name().equals("Battle_Phase")) {
                    GameMatView.showInput("You can’t do this action in this phase");
                    return 1;
                }
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                if (ownMonsterCard.getHaveAttackThisTurn())
                    GameMatView.showInput("This card already attacked");
                else if (MonsterZoneCard.getMonsterCardByAddress(address, rivalUser) == null) {
                    GameMatView.showInput("There is no card to attack here");
                    return 1;
                }
                selectedOwnCard = "";
                selectedRivalCard = "";
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                int damage;
                String rivalMonsterMode = rivalMonsterCard.getMode();
                if (rivalMonsterMode.equals("OO")) {
                    if (ownMonsterCard.getAttack() > rivalMonsterCard.getAttack()) {
                        damage = ownMonsterCard.getAttack() - rivalMonsterCard.getAttack();
                        Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                        GameMatView.showInput("Your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                    } else if (ownMonsterCard.getAttack() == rivalMonsterCard.getAttack()) {
                        GameMatView.showInput("Both you and your opponent monster cards are destroyed and no one receives damage");
                    } else {
                        damage = rivalMonsterCard.getAttack() - ownMonsterCard.getAttack();
                        Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                        GameMatView.showInput("Your monster card is destroyed and you received " + damage + " battle damage");
                    }
                } else {
                    if (ownMonsterCard.getAttack() > rivalMonsterCard.getDefend()) {
                        if (rivalMonsterMode.equals("DH"))
                            GameMatView.showInput("Opponent’s monster card was " + rivalMonsterCard.getMonsterName() + " and the defense position monster is destroyed");
                        else
                            GameMatView.showInput("The defense position monster is destroyed");
                    } else if (ownMonsterCard.getAttack() == rivalMonsterCard.getDefend()) {
                        if (rivalMonsterMode.equals("DH"))
                            GameMatView.showInput("Opponent’s monster card was " + rivalMonsterCard.getMonsterName() + " and no card is destroyed");
                        else
                            GameMatView.showInput("No card is destroyed");
                    } else {
                        damage = rivalMonsterCard.getDefend() - ownMonsterCard.getAttack();
                        Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                        if (rivalMonsterMode.equals("DH"))
                            GameMatView.showInput("Opponent’s monster card was " + rivalMonsterCard.getMonsterName() + " and no card is destroyed but you received " + damage + " battle damage");
                        else
                            GameMatView.showInput("No card is destroyed but you received " + damage + " battle damage");
                    }
                }
            }
        }
        return 0;
    }

    public static int attackDirect(String command, Phase currentPhase) {
        if (getMatcher(command, "attack\\s+direct").find()) {
            if (errorOfNoCardSelected("own")) {
                if (!getSpecificPartOfSelectedCard(0).equals("Monster"))
                    GameMatView.showInput("You can’t attack with this card");
                else if (currentPhase.name().equals("Battle_Phase")) {
                    GameMatView.showInput("You can’t do this action in this phase");
                    return 1;
                }
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
                if (ownMonsterCard.getHaveAttackThisTurn())
                    GameMatView.showInput("This card already attacked");
                else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) != 0)
                    GameMatView.showInput("You can’t attack the opponent directly");
                else {
                    selectedOwnCard = "";
                    int damage = ownMonsterCard.getAttack();
                    Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                    GameMatView.showInput("You opponent receives " + damage + " battle damage");
                }
            }
        }
        return 0;
    }

    public static int activateSpellEffect(String command, Phase currentPhase) {
        if (getMatcher(command, "activate\\s+effect").find()) {
            if (errorOfNoCardSelected("own")) {
                String[] split = selectedOwnCard.split(" ");
                if (split[0].equals("monster"))
                    GameMatView.showInput("Activate effect is only for spell cards");
                else if (split[0].equals("hand") && !Card.getCardsByName(split[1]).getCardModel().equals("spell"))
                    GameMatView.showInput("Activate effect is only for spell cards");
                else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                    GameMatView.showInput("You can’t activate an effect on this turn");
                else if (split[0].equals("spell")) {
                    SpellTrapZoneCard ownSpellCard = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
                    if (ownSpellCard.getMode().equals("O"))
                        GameMatView.showInput("You have already activated this card");
                    else {
                        String spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
                        if (spellIcon.equals("Field")) {
                            GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                            if (!ownGameMat.getFieldZone().equals(""))
                                ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                            ownGameMat.addToFieldZone(split[1]);
                        }
                        if (split[1].equals("Advanced Ritual Art"))
                            ritualSummon(currentPhase);
                        ownSpellCard.setMode("O");
                        GameMatView.showInput("Spell activated");
                    }
                } else if (split[0].equals("hand")) {
                    if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                        GameMatView.showInput("spell card zone is full");
                    else {
                        new SpellTrapZoneCard(onlineUser, split[1], "O");
                        HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser).setIsRemoved(true);
                        GameMatView.showInput("Spell activated");
                    }
                }
            }
        }
        return 0;
    }

    public static void activateSomeSpellEffect(String spellName) {
        if (spellName.equals("Pot of Greed")) {
            Player player = Player.getPlayerByName(onlineUser);
            String cardName = player.drawCard();
            player.removeFromMainDeck();
            new HandCardZone(onlineUser, cardName);
            cardName = player.drawCard();
            player.removeFromMainDeck();
            new HandCardZone(onlineUser, cardName);
        }
    }

    public static void ritualSummon(Phase currentPhase) {
        if (HandCardZone.doIHaveAnyRitualMonster(onlineUser) == 0) {
            GameMatView.showInput("There is no way you could ritual summon a monster");
        }
        else {
            HandCardZone handCard = HandCardZone.getHandCardByAddress(HandCardZone.doIHaveAnyRitualMonster(onlineUser), onlineUser);
            String ritualMonsterName = handCard.getCardName();
            if (MonsterZoneCard.getSumOfMonstersLevel(onlineUser) < MonsterCard.getMonsterByName(ritualMonsterName).getLevel())
                GameMatView.showInput("There is no way you could ritual summon a monster");
            GameMatView.showInput("Please select a Ritual Monster to summon!");
            String command = GameMatView.getCommand();
            while (!getMatcher(command,"select\\s+--hand\\s+(\\d+)").find()) {
                GameMatView.showInput("Please select a Ritual Monster from your HandCards to summon!");
                command = GameMatView.getCommand();
            }
            int ritualMonsterAddress = Integer.parseInt(getMatcher(command,"select\\s+--hand\\s+(\\d+)").group(1));
            selectHandCard(ritualMonsterAddress);

        }
    }


    public static void specialSummon() {

    }



    public static int showSelectedCard(String command) {
        if (getMatcher(command, "card\\s+show\\s+--selected").find()) {
            if (errorOfNoCardSelected("own")) {
                String[] split = selectedOwnCard.split(" ");
                String cardModel = Card.getCardsByName(split[1]).getCardModel();
                if (cardModel.equals("monster"))
                    GameMatView.showInput(MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
                else if (cardModel.equals("spell"))
                    GameMatView.showInput(SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
            }
        }
        return 0;
    }


    public static void surrender() {

    }


    public static void showGameBoard() {
        GameMatView.showInput(rivalUser + " : " + Player.getPlayerByName(rivalUser).getLifePoint());
        for (int i = 0; i < HandCardZone.getNumberOfFullHouse(rivalUser); i++)
            System.out.print("   " + HandCardZone.getNumberOfFullHouse(rivalUser));
        GameMatView.showInput(String.valueOf(Player.getPlayerByName(rivalUser).getNumberOfMainDeckCards()));
        String[] rivalSpellsMode = SpellTrapZoneCard.getAllSpellTrapMode(rivalUser);
        for (int i = 4; i > -1; i--)
            System.out.print("  " + rivalSpellsMode[i]);
        System.out.println("");
        String[] rivalMonstersMode = MonsterZoneCard.getAllMonstersMode(rivalUser);
        for (int i = 4; i > -1; i--)
            System.out.print("  " + rivalMonstersMode[i]);
        if (GameMatModel.getGameMatByNickname(onlineUser).getFieldZone().isEmpty())
            GameMatView.showInput("\n" + GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        E\n");
        else
            GameMatView.showInput("\n" + GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        O\n");

        GameMatView.showInput("--------------------------\n");

        if (GameMatModel.getGameMatByNickname(onlineUser).getFieldZone().isEmpty())
            GameMatView.showInput("E                        " + GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "\n");
        else
            GameMatView.showInput("O                        " + GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "\n");

        String[] ownMonstersMode = MonsterZoneCard.getAllMonstersMode(onlineUser);
        for (int i = 0; i < 5; i++)
            System.out.print("  " + ownMonstersMode[i]);
        System.out.println("");
        String[] ownSpellsMode = SpellTrapZoneCard.getAllSpellTrapMode(onlineUser);
        for (int i = 0; i < 5; i++)
            System.out.print("  " + ownSpellsMode[i]);
        GameMatView.showInput("\n                         " + Player.getPlayerByName(onlineUser).getNumberOfMainDeckCards());
        for (int i = 0; i < HandCardZone.getNumberOfFullHouse(onlineUser); i++)
            System.out.print(HandCardZone.getNumberOfFullHouse(onlineUser) + "  ");
        GameMatView.showInput(onlineUser + " : " + Player.getPlayerByName(onlineUser).getLifePoint());
    }




    public static int changePhase(String command, Phase currentPhase) {
        if (getMatcher(command,"next\\s+phase").find()) {
            GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
            switch (currentPhase.name()) {
                case "Draw_Phase" -> {
                    ///candrawphase
                    GameMatView.showInput("phase: " + Phase.Standby_Phase);
                    playerGameMat.setPhase(Phase.Standby_Phase);
                }
                case "Standby_Phase" -> {
                    GameMatView.showInput("phase: " + Phase.Main_Phase1);
                    showGameBoard();
                    playerGameMat.setPhase(Phase.Main_Phase1);
                }
                case "Main_Phase1" -> {
                    GameMatView.showInput("phase: " + Phase.Battle_Phase);
                    playerGameMat.setPhase(Phase.Battle_Phase);
                }
                case "Battle_Phase" -> {
                    GameMatView.showInput("phase: " + Phase.Main_Phase2);
                    playerGameMat.setPhase(Phase.Main_Phase2);
                }
                case "Main_Phase2" -> {
                    GameMatView.showInput("phase: " + Phase.End_Phase);
                    playerGameMat.setPhase(Phase.End_Phase);
                }
                case "End_Phase" -> {
                    playerGameMat.setPhase(Phase.Draw_Phase);
                    GameMatView.showInput("its " + rivalUser + "’s turn");
                    GameMatView.showInput("phase: " + Phase.Draw_Phase);
                    changeTurn();
                    Player onlinePlayer = Player.getPlayerByName(onlineUser);
                    if (onlinePlayer.getNumberOfMainDeckCards() == 0)
                        endGame();
                    else {
                        String cardName = onlinePlayer.drawCard();
                        onlinePlayer.removeFromMainDeck();
                        new HandCardZone(onlineUser, cardName);
                        GameMatView.showInput("new card added to the hand : " + cardName);
                    }
                }
            }
        }
        return 0;
    }

    public static void endGame() {

    }


    public static void changeTurn() {
        Player.getPlayerByName(onlineUser).changeCounterOfTurn();
        String temp = onlineUser;
        onlineUser = rivalUser;
        rivalUser = temp;
        Player.getPlayerByName(onlineUser).setIsYourTurn(true);
        Player.getPlayerByName(rivalUser).setIsYourTurn(false);
    }


    public static void backCommand() {
        while (true) {
            command = GameMatView.getCommand();
            if (command.equals("back"))
                return;
            else
                GameMatView.showInput("invalid command");
        }
    }

}
