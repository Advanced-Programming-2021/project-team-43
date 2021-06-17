package controller;
import view.GameMatView;
import model.*;
import java.util.*;
import java.util.regex.*;


public class GameMatController {

    public static String selectedOwnCard = "";
    public static String selectedRivalCard = "";
    public static String onlineUser = "";
    public static String rivalUser = "";
    public static String command;
    private static String response;
    private static Matcher matcher;
    private static int trapAddress;
    private static int counterOne = 0;
    private static Phase currentPhase;
    private static int counterTwo = 0;

    public static int run(String firstPlayer, String secondPlayer) {
        onlineUser = firstPlayer;
        rivalUser = secondPlayer;
        GameMatView.showInput("The game starts!");
        GameMatView.showInput("its " + onlineUser + "’s turn");
        GameMatView.showInput("phase: " + Phase.Draw_Phase);
        showGameBoard();
        while (true) {
            if (onlineUser.equals("AI")) {
                AI();
            } else {
                command = GameMatView.getCommand();
            }
            command = command.trim();
            int breaker = commandController(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static void AI() {
        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        if (currentPhase.equals(Phase.Draw_Phase)) {
            command = "next phase";
        }
        if (currentPhase.equals(Phase.Standby_Phase)) {
            command = "next phase";
        }
        if (currentPhase.equals(Phase.Main_Phase1)) {
            if (counterOne == 0) {
                command = "select --hand 1";
                counterOne++;
            } else if (counterOne == 1) {
                command = "summon";
                counterOne++;
            } else if (counterOne == 2) {
                command = "next phase";
            }
        }
        if (currentPhase.equals(Phase.Battle_Phase)) {
            counterOne = 0;
            if (counterTwo == 0) {
                command = "select --monster 1";
                counterTwo++;
            } else if (counterTwo == 1) {
                if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) == 0) {
                    command = "attack direct";
                } else {
                    command = "attack 1";
                }
                counterTwo++;
            } else if (counterTwo == 2) {
                command = "next phase";
                counterTwo = 0;
            }
        }
        if (currentPhase.equals(Phase.Main_Phase2)) {
            command = "next phase";
        }
    }

    public static int commandController(String command) {
        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
        if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+-m\\s+(\\d+)\\s+-o$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 1;
        }
        if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-m\\s+(\\d+)$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), true);
            showGameBoard();
            return 2;
        }
        if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+--monster\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-o\\s+-m\\s+(\\d+)$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 3;
        }
        if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+(\\d+)\\s+--monster$")).find() || (matcher = getMatcher(command, "^s\\s+-o\\s+(\\d+)\\s+-m$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+--monster\\s+--opponent\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-m\\s+-o\\s+(\\d+)$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+(\\d+)\\s+--monster\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+(\\d+)\\s+-m\\s+-o$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+(\\d+)\\s+--opponent\\s+--monster$")).find() || (matcher = getMatcher(command, "^s\\s+(\\d+)\\s+-o\\s+-m$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+-s\\s+(\\d+)\\s+-o$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 4;
        }
        if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-s\\s+(\\d+)$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), true);
            showGameBoard();
            return 5;
        }
        if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-o\\s+-s\\s+(\\d+)$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 6;
        }
        if ((matcher = getMatcher(command, "^select\\s+--spell\\s+--opponent\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-s\\s+-o\\s+(\\d+)$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+(\\d+)\\s+--spell$")).find() || (matcher = getMatcher(command, "^s\\s+-o\\s+(\\d+)\\s+-s$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+(\\d+)\\s+--opponent\\s+--spell$")).find() || (matcher = getMatcher(command, "^s\\s+(\\d+)\\s+-o\\s+-s$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if ((matcher = getMatcher(command, "^select\\s+(\\d+)\\s+--spell\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+(\\d+)\\s+-s\\s+-o$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            showGameBoard();
            return 0;
        }
        if (getMatcher(command, "^select\\s+--field$").find() || getMatcher(command, "^s\\s+-f$").find()) {
            selectFieldCard(true);
            showGameBoard();
            return 7;
        }
        if (getMatcher(command, "^select\\s+--field\\s+--opponent$").find() || getMatcher(command, "^select\\s+--opponent\\s+--field$").find() || getMatcher(command, "^s\\s+-f\\s+-o$").find() || getMatcher(command, "^s\\s+-o\\s+-f$").find()) {
            selectFieldCard(false);
            showGameBoard();
            return 8;
        }
        if ((matcher = getMatcher(command, "^select\\s+--hand\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-h\\s+(\\d+)$")).find()) {
            selectHandCard(Integer.parseInt(matcher.group(1)));
            showGameBoard();
            return 9;
        }
        if ((matcher = getMatcher(command, "^select\\s+(\\d+)\\s+--hand$")).find() || (matcher = getMatcher(command, "^s\\s+(\\d+)\\s+-h$")).find()) {
            selectHandCard(Integer.parseInt(matcher.group(1)));
            showGameBoard();
            return 0;
        }
        if (getMatcher(command, "^select\\s+-d$").find() || getMatcher(command, "^s\\s+-d$").find()) {
            selectDelete();
            showGameBoard();
            return 10;
        }
        if (getMatcher(command, "^next\\s+phase$").find() || getMatcher(command, "^n\\s+p$").find()) {
            changePhase(currentPhase);
            showGameBoard();
            return 11;
        }
        if (getMatcher(command, "^summon$").find() || getMatcher(command, "^sn$").find()) {
            summon(currentPhase);
            showGameBoard();
            return 12;
        }
        if (getMatcher(command, "^set$").find() || getMatcher(command, "^st$").find()) {
            set(currentPhase);
            showGameBoard();
            return 13;
        }
        if (changeMonsterPosition(command, currentPhase) == 1) {
            showGameBoard();
            return 14;
        }
        if (getMatcher(command, "^flip-summon$").find() || getMatcher(command, "^f-s$").find()) {
            flipSummon(currentPhase);
            showGameBoard();
            return 15;
        }
        if (attack(command, currentPhase) == 1) {
            showGameBoard();
            return 16;
        }
        if (getMatcher(command, "^attack\\s+direct$").find() || getMatcher(command, "^a\\s+d$").find()) {
            attackDirect(currentPhase);
            showGameBoard();
            return 17;
        }
        if (getMatcher(command, "^activate\\s+effect$").find() || getMatcher(command, "^a\\s+e$").find()) {
            activateSpellEffect(currentPhase);
            showGameBoard();
            return 18;
        }
        if (getMatcher(command, "^card\\s+show\\s+--selected$").find() || getMatcher(command, "^c\\s+s\\s+-s$").find()) {
            showSelectedCard();
            backCommand();
            return 19;
        }
        if (getMatcher(command, "^show\\s+graveyard$").find() || getMatcher(command, "^s\\s+g$").find()) {
            GameMatModel.getGameMatByNickname(onlineUser).showGraveyard();
            backCommand();
            return 21;
        }
        if (getMatcher(command, "^show\\s+graveyard\\s+--opponent$").find() || getMatcher(command, "^s\\s+g\\s+-o$").find()) {
            getPermission();
            return 22;
        }
        if (getMatcher(command, "^show\\s+main\\s+deck$").find() || getMatcher(command, "^s\\s+m\\s+d$").find()) {
            Player.getPlayerByName(onlineUser).showMainDeck();
            backCommand();
            return 23;
        }
        if (getMatcher(command, "^show\\s+side\\s+deck$").find() || getMatcher(command, "^s\\s+s\\s+d$").find()) {
            Player.getPlayerByName(onlineUser).showSideDeck();
            backCommand();
            return 24;
        }
        if (getMatcher(command, "^show\\s+my\\s+hand$").find() || getMatcher(command, "^s\\s+h$").find()) {
            HandCardZone.showHandCard(onlineUser);
            backCommand();
            return 25;
        }
        if (getMatcher(command, "^surrender$").find()) {
            endGame("surrender", onlineUser);
            return 0;
        }
        if (getMatcher(command, "duel \\s*set-winner \\s*" + onlineUser).find()) {
            endGame("cheat", rivalUser);
            return 0;
        }
        if (getMatcher(command, "duel \\s*set-winner \\s*" + rivalUser).find()) {
            endGame("cheat", onlineUser);
            return 0;
        }
        if ((matcher = getMatcher(command, "increase\\s+--LP\\s+(\\d+)")).find()) {
            increaseLP(Integer.parseInt(matcher.group(1)), onlineUser);
            return 26;
        }
        if (getMatcher(command, "^menu exit$").find())
            return 0;
        GameMatView.showInput("invalid command");
        return 27;
    }

    public static void increaseLP(int lifePoint, String onlineUser) {
        Player player= Player.getPlayerByName(onlineUser);
        player.changeLifePoint(lifePoint);
    }

    public static String getRivalUser() {
        return rivalUser;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void getPermission() {
        do {
            GameMatView.showInput("Your opponent want to see your graveyard. Do you give him permission? (yes/no)");
            response = GameMatView.getCommand();
        } while (!response.matches("yes|no"));
        if (response.equals("yes")) {
            GameMatModel.getGameMatByNickname(rivalUser).showGraveyard();
            backCommand();
        }
        else
            GameMatView.showInput("Oops! You dont have permission to see your rival graveyard!\n" + onlineUser + ", please continue the game!");
    }

    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (errorOfInvalidSelection(address, "Monster")) {
            if (isOwnMonsterCard) {
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, onlineUser);
                if (ownMonsterCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedOwnCard = "Monster/" + ownMonsterCard.getMonsterName() + "/" + address;
                    GameMatView.showInput("card selected");
                }
            } else {
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                if (rivalMonsterCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedRivalCard = "Monster/" + rivalMonsterCard.getMonsterName() + "/" + address;
                    GameMatView.showInput("card selected");
                }
            }
        }
    }

    public static int selectSpellCard(int address, boolean isOwnSpellCard) {
        if (errorOfInvalidSelection(address, "Spell")) {
            SpellTrapZoneCard spellTrapCard;
            if (isOwnSpellCard) {
                spellTrapCard = SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser);
                if (spellTrapCard == null) {
                    GameMatView.showInput("no card found in the given position");
                    return 0;
                } else {
                    selectedOwnCard = spellTrapCard.getKind() + "/" + spellTrapCard.getSpellTrapName() + "/" + address;
                    GameMatView.showInput("card selected");
                }
            } else {
                spellTrapCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
                if (spellTrapCard == null) {
                    GameMatView.showInput("no card found in the given position");
                    return 0;
                } else {
                    selectedRivalCard = spellTrapCard.getKind() + "/" + spellTrapCard.getSpellTrapName() + "/" + address;
                    GameMatView.showInput("card selected");
                }
            }
        }
        return 1;
    }

    public static void selectFieldCard(boolean isOwnField) {
        GameMatModel gameMat;
        if (isOwnField) {
            gameMat = GameMatModel.getGameMatByNickname(onlineUser);
            if (gameMat.getFieldZone().equals(""))
                GameMatView.showInput("invalid selection");
            else {
                selectedOwnCard = "Field";
                GameMatView.showInput("card selected");
            }
        } else {
            gameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (gameMat.getFieldZone().equals(""))
                GameMatView.showInput("invalid selection");
            else {
                selectedRivalCard = "Field";
                GameMatView.showInput("card selected");
            }
        }
    }

    public static void selectHandCard(int address) {
        if (errorOfInvalidSelection(address, "Hand")) {
            address--;
            selectedOwnCard = "Hand/" + HandCardZone.getHandCardByAddress(address, onlineUser).getCardName() + "/" + address;
            GameMatView.showInput("card selected");
        }
    }

    public static void selectDelete() {
        if (!selectedRivalCard.isEmpty()) {
            selectedRivalCard = "";
            GameMatView.showInput("card deselected");
        } else if (!selectedOwnCard.isEmpty()) {
            selectedOwnCard = "";
            GameMatView.showInput("card deselected");
        } else
            GameMatView.showInput("no card is selected yet");
    }

    public static boolean errorOfNoCardSelected(String whoseCard) {
        if (whoseCard.equals("own")) {
            if (selectedOwnCard.equals("")) {
                GameMatView.showInput("no card is selected yet");
                return false;
            } else
                return true;
        } else {
            return !selectedRivalCard.equals("");
        }
    }

    public static boolean errorOfInvalidSelection(int address, String whichPart) {
        if (whichPart.equals("Monster") || whichPart.equals("Spell")) {
            if (address < 1 || address > 5) {
                GameMatView.showInput("invalid selection");
                return false;
            }
            return true;
        } else if (whichPart.equals("Hand")) {
            if (address < 1 || address > HandCardZone.getNumberOfFullHouse(onlineUser)) {
                GameMatView.showInput("invalid selection");
                return false;
            }
            return true;
        }
        return true;
    }

    public static void summon(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split("/");
        Player player = Player.getPlayerByName(onlineUser);
        if (split[0].equals("Hand"))
            summonInHand(player, currentPhase);
        else if (split[0].equals("Monster"))
            summonInMonsterZone(player, currentPhase);
        else
            GameMatView.showInput("you can’t summon this card");
        selectedOwnCard = "";
    }

    public static int summonInHand(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!handCard.getKind().equals("Monster")) {
            GameMatView.showInput("you can’t summon this card");
            return 1;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return 2;
        if (!errorOfFullZone("Monster"))
            return 3;
        if (!player.getCanSetSummonMonster())
            GameMatView.showInput("you already summoned/set on this turn");
        else {
            if (split[1].equals("Scanner"))
                summonInHandSuccessfully(player, handCard);
            else if (specialSummon(handCard, split[1]) != 2)
                selectedOwnCard = "";
            else {
                int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
                if (monsterLevel <= 4) {
                    summonInHandSuccessfully(player, handCard);
                    MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
                    if (ownMonster != null) {
                        if (MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser) == 0) {
                            if (split[1].equals("Terratiger, the Empowered Warrior"))
                                MonsterEffect.terratiger(onlineUser);
                            if (split[1].equals("Herald of Creation"))
                                MonsterEffect.heraldOfCreation(ownMonster, onlineUser);
                        }
                    }
                } else if (monsterLevel == 5 || monsterLevel == 6) {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                        GameMatView.showInput("there are not enough cards for tribute");
                    else if (tributeMonster(1, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                } else {
                    if (split[1].equals("Crab Turtle") || split[1].equals("Skull Guardian")) {
                        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Advanced Ritual Art");
                        if (address == -1) {
                            GameMatView.showInput("You should activate Ritual Spell then summon this monster");
                            return 4;
                        }
                    }
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                        GameMatView.showInput("there are not enough cards for tribute");
                    else if (tributeMonster(2, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                }
            }
        }
        return 5;
    }

    public static void summonInHandSuccessfully(Player player, HandCardZone handCard) {
        if (addToMonsterZoneCard(handCard.getCardName(), "OO") == 0)
            return;
        player.setCanSetSummonMonster(false);
        handCard.removeFromHandCard();
        GameMatView.showInput("summoned successfully");
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
        if (ownMonster.getAttack() >= 1000) {
            checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
            trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
        }
        checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
    }

    public static int summonInMonsterZone(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getMode().equals("OO") || ownMonster.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t summon this card");
            return 1;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return 2;
        if (!player.getCanSetSummonMonster()) {
            GameMatView.showInput("you already summoned/set on this turn");
        } else {
            int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
            if (monsterLevel <= 4) {
                summonInMonsterZoneSuccessfully(player, ownMonster);
            } else if (monsterLevel == 5 || monsterLevel == 6) {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                    GameMatView.showInput("there are not enough cards for tribute");
                else {
                    if (tributeMonster(1, split[1]) == 1)
                        summonInMonsterZoneSuccessfully(player, ownMonster);
                }
            } else {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                    GameMatView.showInput("there are not enough cards for tribute");
                else {
                    if (tributeMonster(2, split[1]) == 1)
                        summonInMonsterZoneSuccessfully(player, ownMonster);
                }
            }
        }
        return 0;
    }

    public static void summonInMonsterZoneSuccessfully(Player player, MonsterZoneCard ownMonster) {
        player.setCanSetSummonMonster(false);
        ownMonster.setMode("OO");
        ownMonster.setHaveChangedPositionThisTurn(true);
        GameMatView.showInput("summoned successfully");
        if (ownMonster.getAttack() >= 1000) {
            checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
            trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
        }
        MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
        checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
    }

    public static int changeMonsterPosition(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command, "^set\\s+--position\\s+(attack|defense)$")).find() || (matcher = getMatcher(command, "^set\\s+-p\\s+(attack|defense)$")).find()) {
            String mode = matcher.group(1);
            String[] split = selectedOwnCard.split("/");
            if (!errorOfNoCardSelected("own"))
                return 1;
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t change this card position");
                return 1;
            }
            if (!errorOfWrongPhase("change", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if ((mode.equals("attack") && ownMonster.getMode().equals("OO")) || (mode.equals("defend") && ownMonster.getMode().equals("DO")))
                GameMatView.showInput("this card is already in the wanted position");
            else if (ownMonster.getHaveChangedPositionThisTurn())
                GameMatView.showInput("you already changed this card position in this turn");
            else {
                ownMonster.setHaveChangedPositionThisTurn(true);
                if (mode.equals("attack"))
                    ownMonster.setMode("OO");
                else
                    ownMonster.setMode("DO");
                if (ownMonster.getMonsterName().equals("The Calculator"))
                    MonsterEffect.theCalculator(onlineUser, ownMonster);
                selectedOwnCard = "";
                GameMatView.showInput("monster card position changed successfully");
                MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
            }
            return 1;
        }
        return 0;
    }

    public static int set(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Hand")) {
            GameMatView.showInput("you can’t set this card");
            return 2;
        }
        if (!errorOfWrongPhase("set", currentPhase))
            return 3;
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        String cardName = handCard.getCardName();
        switch (handCard.getKind()) {
            case "Monster":
                if (!errorOfFullZone("Monster"))
                    return 4;
                if (!player.getCanSetSummonMonster())
                    GameMatView.showInput("you already summoned/set on this turn");
                else if (cardName.equals("Beast King Barbaros"))
                    GameMatView.showInput("Oops! Beast King Barbaros cant be set!");
                else {
                    if (addToMonsterZoneCard(cardName, "DH") == 0)
                        return 5;
                    handCard.removeFromHandCard();
                    player.setCanSetSummonMonster(false);
                    GameMatView.showInput("set successfully");
                }
                break;
            case "Spell":
                if (!errorOfFullZone("Spell"))
                    return 6;
                handCard.removeFromHandCard();
                if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Field")) {
                    GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                    if (!ownGameMat.getFieldZone().equals(""))
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    GameMatModel.getGameMatByNickname(onlineUser).addToFieldZone(cardName, "H");
                } else {
                    addToSpellTrapZoneCard(handCard.getCardName(), "H");
                    SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                }
                GameMatView.showInput("set successfully");
                break;
            case "Trap":
                if (!errorOfFullZone("Spell"))
                    return 7;
                handCard.removeFromHandCard();
                addToSpellTrapZoneCard(handCard.getCardName(), "H");
                SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                GameMatView.showInput("set successfully");
                break;
        }
        selectedOwnCard = "";
        return 0;
    }

    public static int addToMonsterZoneCard(String monsterName, String mode) {
        String whichCard = "";
        if (monsterName.equals("Scanner")) {
            if (GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards() == 0) {
                GameMatView.showInput("Oops! You cant summon Scanner because of no dead card in your rival graveyard!");
                return 0;
            }
            else {
                do {
                    GameMatView.showInput("Which rival dead monster for Scanner? (enter the monster name)");
                    whichCard = GameMatView.getCommand();
                    if (whichCard.equals("cancel"))
                        return 0;
                } while (!GameMatModel.getGameMatByNickname(rivalUser).doesThisMonsterExistInGraveyard(whichCard));
                new MonsterZoneCard(onlineUser, whichCard, mode, true, false);
            }
        }
        else
            new MonsterZoneCard(onlineUser, monsterName, mode, false, false);
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
        if (whichCard.equals("The Calculator") || monsterName.equals("The Calculator"))
            MonsterEffect.theCalculator(onlineUser, ownMonster);
        return 1;
    }

    public static int addToSpellTrapZoneCard(String spellTrapName, String mode) {
        new SpellTrapZoneCard(onlineUser, spellTrapName, mode);
        return 0;
    }

    public static boolean errorOfWrongPhase(String whichAction, Phase currentPhase) {
        switch (whichAction) {
            case "summon":
            case "set":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    GameMatView.showInput("action not allowed in this phase");
                    selectedOwnCard = "";
                    return false;
                }
                else {
                    selectedOwnCard = "";
                    return true;
                }
            case "activate":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    GameMatView.showInput("you can’t activate an effect on this turn");
                    selectedOwnCard = "";
                    return false;
                } else {
                    selectedOwnCard = "";
                    return true;
                }
            case "attack":
            case "attackDirect":
                if (!currentPhase.name().equals("Battle_Phase")) {
                    GameMatView.showInput("you can’t do this action in this phase");
                    selectedOwnCard = "";
                    return false;
                } else {
                    selectedOwnCard = "";
                    return true;
                }
            case "change":
            case "flip":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    GameMatView.showInput("you can’t do this action in this phase");
                    selectedOwnCard = "";
                    return false;
                } else {
                    selectedOwnCard = "";
                    return true;
                }
        }
        return false;
    }

    public static boolean errorOfFullZone(String whichZone) {
        if (whichZone.equals("Monster")) {
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                GameMatView.showInput("monster card zone is full");
                selectedOwnCard = "";
                return false;
            }
            return true;
        }
        else if (whichZone.equals("Spell")) {
            if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                GameMatView.showInput("spell card zone is full");
                selectedOwnCard = "";
                return false;
            }
            return true;
        }
        else {
            if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                GameMatView.showInput("trap card zone is full");
                selectedOwnCard = "";
                return false;
            }
            return true;
        }
    }

    public static int specialSummon(HandCardZone handCard, String monsterName) {
        switch (monsterName) {
            case "Gate Guardian":
                return MonsterEffect.gateGuardian(handCard, onlineUser, rivalUser);
            case "Beast King Barbaros":
                return MonsterEffect.beastKingBarbaros(handCard, onlineUser, rivalUser);
            case "The Tricky":
                return MonsterEffect.theTricky(handCard, onlineUser);
            default:
                return 2;
        }
    }

    public static int ritualSummon() {
        int ritualMonsterAddress = HandCardZone.doIHaveAnyRitualMonster(onlineUser);
        if (ritualMonsterAddress == -1) {
            GameMatView.showInput("there is no way you could ritual summon a monster");
            return -1;
        }
        if (MonsterZoneCard.getSumOfMonstersLevel(onlineUser) < 7) {
            GameMatView.showInput("there is no way you could ritual summon a monster");
            return -1;
        }
        int address;
        while (true) {
            GameMatView.showInput("Please enter the address of a Ritual Monster in your hand to summon: ");
            String response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return -1;
            if (!response.matches("\\d+")) {
                GameMatView.showInput("you should ritual summon right now");
                continue;
            }
            address = Integer.parseInt(response);
            address--;
            if (HandCardZone.getHandCardByAddress(address, onlineUser) == null) {
                GameMatView.showInput("you should ritual summon right now");
                continue;
            }
            HandCardZone card = HandCardZone.getHandCardByAddress(address, onlineUser);
            if (card.getKind().equals("Monster") && MonsterCard.getMonsterByName(card.getCardName()).getCardType().equals("Ritual"))
                break;
        }
        HandCardZone handCardRitualMonster = HandCardZone.getHandCardByAddress(address, onlineUser);
        String ritualMonsterName = handCardRitualMonster.getCardName();
        int numberOfTribute;
        while (true) {
            GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return -1;
            if (!response.matches("[1-3]"))
                continue;
            numberOfTribute = Integer.parseInt(response);
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < numberOfTribute)
                GameMatView.showInput("You dont have this much monster in your zone!");
            else
                break;
        }
        if (tributeMonster(numberOfTribute, "Ritual") == 1) {
            GameMatView.showInput("Please enter Ritual Monster mode: (defensive/attacking)");
            command = GameMatView.getCommand();
            while (!command.matches("defensive") && !command.matches("attacking")) {
                GameMatView.showInput("Please enter the answer correctly: (defensive/attacking)");
                command = GameMatView.getCommand();
            }
            if (command.equals("defensive"))
                new MonsterZoneCard(onlineUser, ritualMonsterName, "DO", false, false);
            else
                new MonsterZoneCard(onlineUser, ritualMonsterName, "OO", false, false);
            GameMatView.showInput("summoned successfully");
            handCardRitualMonster.removeFromHandCard();
        }
        return 0;
    }

    public static int tributeMonster(int numberOfTribute, String monsterType) {
        Integer[] victimAddress = getAddressOfTributeMonster(numberOfTribute);
        if (victimAddress == null)
            return 0;
        if (monsterType.equals("Ritual")) {
            int monstersLevel = 0;
            for (int i = 0; i < numberOfTribute; i++) {
                monstersLevel += MonsterZoneCard.getMonsterCardByAddress(victimAddress[i], onlineUser).getLevel();
            }
            if (monstersLevel < 7) {
                GameMatView.showInput("selected monsters levels don’t match with ritual monster");
                GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
                String response = GameMatView.getCommand();
                while (!response.matches("\\d+")) {
                    if (response.equals("cancel")) {
                        selectedOwnCard = "";
                        return 0;
                    }
                    response = GameMatView.getCommand();
                }
                numberOfTribute = Integer.parseInt(response);
                GameMatView.showInput("Please enter some Monster address to tribute:");
                tributeMonster(numberOfTribute, "Ritual");
            }
        }
        for (int i = 0; i < numberOfTribute; i++) {
            MonsterZoneCard.getMonsterCardByAddress(victimAddress[i], onlineUser).removeMonsterFromZone();
        }

        return 1;
    }

    public static Integer[] getAddressOfTributeMonster(int numberOfTribute) {
        List<Integer> tributeMonsterAddress = new ArrayList<>();
        int address = 0;
        String response;
        for (int i = 1; i <= numberOfTribute; i++) {
            GameMatView.showInput("Please enter the address of monster " + i + " to tribute:");
            while (true) {
                response = GameMatView.getCommand();
                if (response.equals("cancel")) {
                    selectedOwnCard = "";
                    return null;
                } else if (response.matches("\\d+"))
                    address = Integer.parseInt(response);
                if (address < 1 || address > 5 || MonsterZoneCard.getMonsterCardByAddress(address, onlineUser) == null || tributeMonsterAddress.contains(address))
                    GameMatView.showInput("Please enter the address of a monster correctly:");
                else {
                    tributeMonsterAddress.add(address);
                    break;
                }
            }
        }
        Integer[] addressArray = new Integer[tributeMonsterAddress.size()];
        addressArray = tributeMonsterAddress.toArray(addressArray);
        return addressArray;
    }

    public static int flipSummon(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t change this card position");
            selectedOwnCard = "";
            return 2;
        }
        if (!errorOfWrongPhase("flip", currentPhase))
            return 3;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!ownMonster.getMode().equals("DH") || ownMonster.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t flip summon this card");
        } else {
            ownMonster.setMode("OO");
            ownMonster.setHaveChangedPositionThisTurn(true);
            GameMatView.showInput("flip summoned successfully");
            if (ownMonster.getAttack() >= 1000) {
                checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
                trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
            }
            MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
            checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
        }
        selectedOwnCard = "";
        return 4;
    }

    public static int attack(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command, "^attack\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^a\\s+(\\d+)$")).find()) {
            int rivalMonsterAddress = Integer.parseInt(matcher.group(1));
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split("/");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t attack with this card");
                selectedOwnCard = "";
                return 1;
            } else if (!errorOfWrongPhase("attack", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (!ownMonster.getMode().equals("OO")) {
                GameMatView.showInput("your card is not in an attack mode");
                selectedOwnCard = "";
                return 1;
            }
            if (ownMonster.getHaveAttackThisTurn()) {
                GameMatView.showInput("this card already attacked");
                selectedOwnCard = "";
                return 1;
            }
            if (!ownMonster.getCanAttack()) {
                GameMatView.showInput("this Monster cant attack because of a spell effect!");
                selectedOwnCard = "";
                return 1;
            }
            MonsterZoneCard rivalMonster = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress, rivalUser);
            if (rivalMonster == null) {
                GameMatView.showInput("there is no card to attack here");
                selectedOwnCard = "";
                return 1;
            }
            if (!rivalMonster.getCanAttackToThisMonster()) {
                GameMatView.showInput("you cant attack to this monster!");
                selectedOwnCard = "";
                return 1;
            }
            GameMatView.showInput("I want to attack to your Monster!");
            ownMonster.setCanAttack(false);
            ownMonster.setHaveAttackThisTurn(true);
            int result;
            result = checkForSetTrapToActivateInRivalTurn("Magic Cylinder", ownMonster);
            if (result == 1)
                return 1;
            result = checkForSetTrapToActivateInRivalTurn("Mirror Force", ownMonster);
            if (result == 1)
                return 1;
            result = checkForSetTrapToActivateInRivalTurn("Negate Attack", ownMonster);
            if (result == 1)
                return 1;
            int damage;
            String rivalMonsterName = rivalMonster.getMonsterName();
            if (rivalMonsterName.equals("Suijin")) {
                if (MonsterEffect.suijin(rivalMonster) == 1) {
                    selectedOwnCard = "";
                    return 1;
                }
            }
            else if (rivalMonsterName.equals("Texchanger")) {
                if (MonsterEffect.texchanger(rivalMonster, rivalUser) != 0) {
                    selectedOwnCard = "";
                    return 1;
                }
            }
            if (rivalMonsterName.equals("Exploder Dragon")) {
                GameMatView.showInput("Exploder Dragon Effect is activated!");
                rivalMonster.removeMonsterFromZone();
                ownMonster.removeMonsterFromZone();
                GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                return 1;
            }
            String rivalMonsterMode = rivalMonster.getMode();
            if (rivalMonsterMode.equals("OO")) {
                damage = ownMonster.getAttack() - rivalMonster.getAttack();
                if (damage > 0) {
                    Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                    if (!rivalMonsterName.equals("Marshmallon")) {
                        rivalMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    }
                    else {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (rivalMonsterName.equals("Yomi Ship")) {
                        ownMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    }
                    GameMatView.showInput("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                    if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                        Player.getPlayerByName(rivalUser).setLifePoint(0);
                        endGame("lp", rivalUser);
                    }
                }
                else if (damage == 0) {
                    ownMonster.removeMonsterFromZone();
                    rivalMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatView.showInput("both you and your opponent monster cards are destroyed and no one receives damage");
                }
                else {
                    Player.getPlayerByName(onlineUser).changeLifePoint(damage);
                    if (ownMonster.getMonsterName().equals("Exploder Dragon")) {
                        GameMatView.showInput("Exploder Dragon Effect is activated!");
                        rivalMonster.removeMonsterFromZone();
                        ownMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                        return 1;
                    }
                    ownMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatView.showInput("your monster card is destroyed and you received " + -1 * damage + " battle damage");
                    if (Player.getPlayerByName(onlineUser).getLifePoint() <= 0) {
                        Player.getPlayerByName(onlineUser).setLifePoint(0);
                        endGame("lp", onlineUser);
                    }
                }
            }
            else {
                if (ownMonster.getAttack() > rivalMonster.getDefend()) {
                    if (!rivalMonsterName.equals("Marshmallon")) {
                        rivalMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    }
                    else {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (rivalMonster.getMode().equals("DH")) {
                        if (ownMonster.getMonsterName().equals("The Calculator"))
                            MonsterEffect.theCalculator(onlineUser, ownMonster);
                        rivalMonster.setMode("DO");
                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                        showGameBoard();
                    }
                    if (rivalMonsterName.equals("Yomi Ship")) {
                        ownMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    }
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and the defense position monster is destroyed");
                    else {
                        if (rivalMonsterName.equals("Marshmallon"))
                            GameMatView.showInput("the defense position monster is destroyed");
                    }
                } else if (ownMonster.getAttack() == rivalMonster.getDefend()) {
                    if (rivalMonsterMode.equals("DH")) {
                        if (rivalMonsterName.equals("Marshmallon")) {
                            MonsterEffect.marshmallon(rivalMonster, onlineUser);
                        }
                        if (ownMonster.getMonsterName().equals("The Calculator"))
                            MonsterEffect.theCalculator(onlineUser, ownMonster);
                        rivalMonster.setMode("DO");
                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                        showGameBoard();
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed");
                    } else
                        GameMatView.showInput("no card is destroyed");
                } else {
                    damage = rivalMonster.getDefend() - ownMonster.getAttack();
                    Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                    if (rivalMonsterMode.equals("DH")) {
                        if (rivalMonsterName.equals("Marshmallon")) {
                            MonsterEffect.marshmallon(rivalMonster, onlineUser);
                        }
                        if (ownMonster.getMonsterName().equals("The Calculator"))
                            MonsterEffect.theCalculator(onlineUser, ownMonster);
                        rivalMonster.setMode("DO");
                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                        showGameBoard();
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed but you received " + damage + " battle damage");
                    } else
                        GameMatView.showInput("no card is destroyed but you received " + damage + " battle damage");
                    if (Player.getPlayerByName(onlineUser).getLifePoint() < 0) {
                        Player.getPlayerByName(onlineUser).setLifePoint(0);
                        endGame("lp", onlineUser);
                    }
                }
            }
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

    public static int attackDirect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t attack with this card");
            selectedOwnCard = "";
            return 2;
        }
        if (!errorOfWrongPhase("attackDirect", currentPhase))
            return 3;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getHaveAttackThisTurn())
            GameMatView.showInput("this card already attacked");
        else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) != 0)
            GameMatView.showInput("you can’t attack the opponent directly");
        else if (!ownMonster.getCanAttack())
            GameMatView.showInput("this Monster cant attack because of a spell effect!");
        else {
            GameMatView.showInput("I want to attack you directly!");
            ownMonster.setHaveAttackThisTurn(true);
            int result;
            result = checkForSetTrapToActivateInRivalTurn("Magic Cylinder", ownMonster);
            if (result == 1)
                return 4;
            result = checkForSetTrapToActivateInRivalTurn("Mirror Force", ownMonster);
            if (result == 1)
                return 5;
            result = checkForSetTrapToActivateInRivalTurn("Negate Attack", ownMonster);
            if (result == 1)
                return 6;
            int damage = ownMonster.getAttack();
            Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
            GameMatView.showInput("your opponent receives " + damage + " battle damage");
            if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                Player.getPlayerByName(rivalUser).setLifePoint(0);
                System.out.println(rivalUser);
                endGame("lp", rivalUser);
            }
        }
        selectedOwnCard = "";
        return 0;
    }

    public static int checkForSetTrapToActivateInRivalTurn(String trapName, MonsterZoneCard rivalMonster) {
        int trapAddress = SpellTrapZoneCard.getAddressOfSetTrap(rivalUser, trapName);
        if (trapAddress != -1) {
            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
            do {
                GameMatView.showInput(rivalUser + " do you want to activate " + trapName + " ? (yes/no)");
                response = GameMatView.getCommand();
            } while (!response.matches("yes|no"));
            if (response.equals("no")) {
                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
            } else {
                GameMatView.showInput("Please select and activate your trap:");
                while (true) {
                    response = GameMatView.getCommand();
                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(response, "^s\\s+-s\\s+(\\d+)$")).find()) {
                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1) {
                            if (trapAddress == Integer.parseInt(matcher.group(1)))
                                break;
                            else
                                GameMatView.showInput("Please select the trap " + trapName + " correctly");
                        }
                    }
                    else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
                while (true) {
                    response = GameMatView.getCommand();
                    if (getMatcher(response, "^activate\\s+effect$").find() || getMatcher(response, "^a\\s+e$").find()) {
                        return activateTrapEffect(false, rivalMonster);
                    }
                    else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
            }
        }
        return 0;
    }

    public static void checkForQuickSpellInRivalTurn(String spellName) {
        int quickSpellAddress = SpellTrapZoneCard.getAddressOfQuickSpellByName(rivalUser, spellName);
        SpellTrapZoneCard quickSpell;
        if (quickSpellAddress != -1) {
            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
            do {
                GameMatView.showInput(rivalUser + " do you want to activate " + spellName + " ? (yes/no)");
                response = GameMatView.getCommand();
            } while (!response.matches("yes|no"));
            if (response.equals("no")) {
                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
            }
            else {
                GameMatView.showInput("Please select and activate your Quick-Play Spell:");
                while (true) {
                    response = GameMatView.getCommand();
                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(response, "^s\\s+-s\\s+(\\d+)$")).find()) {
                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1) {
                            quickSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(matcher.group(1)), rivalUser);
                            if (quickSpell != null && quickSpell.getIsSetInThisTurn()) {
                                GameMatView.showInput("Oops! You cant activate the effect in the turn you set the card!");
                                return;
                            }
                            if (quickSpellAddress == Integer.parseInt(matcher.group(1)))
                                break;
                            else
                                GameMatView.showInput("Please select the spell " + spellName + " correctly");
                        }
                    }
                    else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
                while (true) {
                    response = GameMatView.getCommand();
                    if (getMatcher(response, "^activate\\s+effect$").find() || getMatcher(response, "^a\\s+e$").find()) {
                        SpellTrapZoneCard spell = SpellTrapZoneCard.getSpellCardByAddress(quickSpellAddress, rivalUser);
                        spell.setMode("O");
                        showGameBoard();
                        if (SpellEffect.quickPlayEffectController(spell, rivalUser, onlineUser) == 0)
                            GameMatView.showInput("Your spell is not activated and is gone to graveyard!");
                        else
                            GameMatView.showInput("spell/trap activated");
                        spell.removeSpellTrapFromZone();
                        break;
                    }
                    else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
            }
        }
    }

    public static int activateTrapEffect(Boolean isInYourTurn, MonsterZoneCard rivalMonster) {
        String[] split;
        int result = 0;
        SpellTrapZoneCard trapCard;
        if (!isInYourTurn) {
            split = selectedRivalCard.split("/");
            trapCard = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), rivalUser);
        }
        else {
            split = selectedOwnCard.split("/");
            trapCard = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        }
        trapCard.setMode("O");
        GameMatView.showInput("trap activated");
        showGameBoard();
        if (isInYourTurn) {
            switch (split[1]) {
                case "Magic Cylinder":
                    result = TrapEffect.magicCylinder(onlineUser, rivalUser, rivalMonster, trapCard);
                    if (result == 1)
                        rivalMonster.removeMonsterFromZone();
                    break;
                case "Mirror Force":
                    result = TrapEffect.mirrorForce(rivalUser, onlineUser, trapCard);
                    break;
                case "Mind Crush":
                    result = TrapEffect.mindCrush(onlineUser, rivalUser, trapCard);
                    break;
                case "Trap Hole":
                case "Negate Attack":
                    if (rivalMonster != null) {
                        rivalMonster.removeMonsterFromZone();
                        trapCard.removeSpellTrapFromZone();
                        result = 1;
                    }
                    break;
                case "Torrential Tribute":
                    result = TrapEffect.torrentialTribute(rivalUser, onlineUser, trapCard);
                    break;
                case "Solemn Warning":

                    break;
                case "Magic Jammer":
                    result = TrapEffect.magicJammer(onlineUser, rivalUser, trapCard);
                    break;
                case "Call of The Haunted":
                    result = TrapEffect.callOfTheHaunted(onlineUser, trapCard);
                    break;
                case "Time Seal":
                    TrapEffect.timeSeal(rivalUser);
                    break;
            }
        }
        else {
            switch (split[1]) {
                case "Magic Cylinder":
                    result = TrapEffect.magicCylinder(rivalUser, onlineUser, rivalMonster, trapCard);
                    if (result == 1)
                        rivalMonster.removeMonsterFromZone();
                    break;
                case "Mirror Force":
                    result = TrapEffect.mirrorForce(onlineUser, rivalUser, trapCard);
                    break;
                case "Mind Crush":
                    result = TrapEffect.mindCrush(rivalUser, onlineUser, trapCard);
                    break;
                case "Trap Hole":
                case "Negate Attack":
                    if (rivalMonster != null) {
                        rivalMonster.removeMonsterFromZone();
                        trapCard.removeSpellTrapFromZone();
                        result = 1;
                    }
                    break;
                case "Torrential Tribute":
                    result = TrapEffect.torrentialTribute(onlineUser, rivalUser, trapCard);
                    break;
                case "Solemn Warning":

                    break;
                case "Magic Jammer":
                    result = TrapEffect.magicJammer(rivalUser, onlineUser, trapCard);
                    break;
                case "Call of The Haunted":
                    result = TrapEffect.callOfTheHaunted(rivalUser, trapCard);
                    break;
                case "Time Seal":
                    TrapEffect.timeSeal(onlineUser);
                    break;
            }
        }
        return result;
    }

    public static void changePhase(Phase currentPhase) {
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        int address;
        switch (currentPhase.name()) {
            case "Draw_Phase":
                GameMatView.showInput("phase: " + Phase.Standby_Phase);
                playerGameMat.setPhase(Phase.Standby_Phase);
                checkForQuickSpellInRivalTurn("Twin Twisters");
                checkForQuickSpellInRivalTurn("Mystical space typhoon");
                checkForMessengerOfPeace();
                checkForSetTrapToActivateInRivalTurn("Call of The Haunted", null);
                address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Swords of Revealing Light");
                if (address != -1) {
                    SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).changeTurnCounter();
                    if (SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).getTurnCounter() == 3) {
                        SpellEffect.returnPermission(address, rivalUser);
                        SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).removeSpellTrapFromZone();
                    }
                }
                address = MonsterZoneCard.getAddressOfScanner(onlineUser);
                if (address != -1) {
                    if (GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards() == 0) {
                        GameMatView.showInput("Oops! You cant change the Scanner because of no dead card in your rival graveyard!");
                        MonsterZoneCard.getMonsterCardByAddress(address, onlineUser).removeMonsterFromZone();
                    } else {
                        String whichCard;
                        do {
                            GameMatView.showInput("Which rival dead monster for Scanner? (enter the monster name)");
                            whichCard = GameMatView.getCommand();
                            if (whichCard.equals("cancel"))
                                MonsterZoneCard.getMonsterCardByAddress(address, onlineUser).removeMonsterFromZone();
                        } while (!GameMatModel.getGameMatByNickname(rivalUser).doesThisMonsterExistInGraveyard(whichCard));
                        MonsterZoneCard.getMonsterCardByAddress(address, onlineUser).changeTheMonsterFace(whichCard);
                    }
                }
                checkForQuickSpellInRivalTurn("Twin Twisters");
                checkForQuickSpellInRivalTurn("Mystical space typhoon");
                checkForSetTrapToActivateInRivalTurn("Mind Crush", null);
                checkForSetTrapToActivateInRivalTurn("Time Seal", null);
                checkForSetTrapToActivateInRivalTurn("Call of the Haunted", null);
                trapAddress = MonsterZoneCard.getAddressByMonsterName(onlineUser, "Herald of Creation");
                if (trapAddress != -1)
                    MonsterEffect.heraldOfCreation(MonsterZoneCard.getMonsterCardByAddress(trapAddress, onlineUser), onlineUser);
                break;
            case "Standby_Phase":
                GameMatView.showInput("phase: " + Phase.Main_Phase1);
                playerGameMat.setPhase(Phase.Main_Phase1);
                checkForQuickSpellInRivalTurn("Twin Twisters");
                checkForQuickSpellInRivalTurn("Mystical space typhoon");
                checkForSetTrapToActivateInRivalTurn("Call of The Haunted", null);
                break;
            case "Main_Phase1":
                GameMatView.showInput("phase: " + Phase.Battle_Phase);
                playerGameMat.setPhase(Phase.Battle_Phase);
                if (!player.getCanBattle()) {
                    GameMatView.showInput("Oops! You cant battle this turn!");
                    player.setCanBattle(true);
                    changePhase(playerGameMat.getPhase());
                }
                checkForQuickSpellInRivalTurn("Twin Twisters");
                checkForQuickSpellInRivalTurn("Mystical space typhoon");
                checkForSetTrapToActivateInRivalTurn("Call of The Haunted", null);
                break;
            case "Battle_Phase":
                GameMatView.showInput("phase: " + Phase.Main_Phase2);
                playerGameMat.setPhase(Phase.Main_Phase2);
                checkForQuickSpellInRivalTurn("Twin Twisters");
                checkForQuickSpellInRivalTurn("Mystical space typhoon");
                checkForSetTrapToActivateInRivalTurn("Call of The Haunted", null);
                break;
            case "Main_Phase2":
                checkForSupplySquad();
                MonsterZoneCard.removeUselessMonster(onlineUser);
                GameMatView.showInput("phase: " + Phase.End_Phase);
                GameMatView.showInput("I end my turn!");
                playerGameMat.setPhase(Phase.Draw_Phase);
                GameMatView.showInput("its " + rivalUser + "’s turn");
                changeTurn();
                GameMatView.showInput("phase: " + Phase.Draw_Phase);
                player = Player.getPlayerByName(onlineUser);
                if (player.getNumberOfMainDeckCards() == 0)
                    endGame("noCard", onlineUser);
                else if (HandCardZone.getNumberOfFullHouse(onlineUser) == 7) {
                    GameMatView.showInput("Oops! You have to drop one of your hand cards!");
                    while (true) {
                        GameMatView.showInput("Please enter the address of one of your hand card to drop:");
                        response = GameMatView.getCommand();
                        if (!response.matches("[1-7]"))
                            continue;
                        else if (response.equals("show my hand")) {
                            HandCardZone.showHandCard(onlineUser);
                            continue;
                        }
                        address = Integer.parseInt(response);
                        if (address > 0 && address < 8)
                            break;
                    }
                    HandCardZone.getHandCardByAddress(address - 1, onlineUser).removeFromHandCard();
                } else if (player.getCanDrawCard()) {
                    String cardName = player.drawCard(false);
                    new HandCardZone(onlineUser, cardName);
                    GameMatView.showInput("new card added to the hand : " + cardName);
                } else {
                    GameMatView.showInput("Oops! you can not draw card");
                }
                break;
        }
    }

    public static void changeTurn() {
        Player onlinePlayer = Player.getPlayerByName(onlineUser);
        if (onlinePlayer.getCounterOfTurn() == 1) onlinePlayer.setCanDrawCard(true);
        MonsterZoneCard.changeOneTurnMonstersIsEffectUsed(onlineUser);
        MonsterZoneCard.removeUselessMonster(onlineUser);
        MonsterZoneCard.changeTurn(onlineUser);
        SpellTrapZoneCard.changeTurn(onlineUser);
        onlinePlayer.changeTurn();
        GameMatModel.getGameMatByNickname(onlineUser).resetNumberOfDeadMonsterThisTurn();
        GameMatModel.getGameMatByNickname(rivalUser).resetNumberOfDeadMonsterThisTurn();
        String temp = onlineUser;
        onlineUser = rivalUser;
        rivalUser = temp;
        Player.getPlayerByName(onlineUser).setIsYourTurn(true);
        Player.getPlayerByName(rivalUser).setIsYourTurn(false);
    }

    public static void endGame(String reason, String loser) {
        String winner, winnerUsername, loserUsername;
        if (loser.equals(onlineUser))
            winner = rivalUser;
        else
            winner = onlineUser;
        if (UserModel.getUserByUsername(MainMenuController.username).getNickname().equals(winner)) {
            winnerUsername = MainMenuController.username;
            loserUsername = MainMenuController.username2;
        } else {
            winnerUsername = MainMenuController.username2;
            loserUsername = MainMenuController.username;
        }
        Player winnerPlayer = Player.getPlayerByName(winner);
        Player loserPlayer = Player.getPlayerByName(loser);
        UserModel.getUserByUsername(winnerUsername).changeUserScore(1000);
        if (Player.isOneRound) {
            GameMatView.showInput("The Duel is Over!");
            GameMatView.showInput(winnerUsername + " won the game and the score is: " + UserModel.getUserByUsername(winnerUsername).getUserScore() + "-" + UserModel.getUserByUsername(loserUsername).getUserScore());
            UserModel.getUserByUsername(winnerUsername).changeUserCoin(1000 + winnerPlayer.getLifePoint());
            UserModel.getUserByUsername(loserUsername).changeUserCoin(100);
            resetGame();
            MainMenuController.run();
        } else {
            int round = winnerPlayer.getNumberOfRound();
            if (round == 3)
                GameMatView.showInput("Round 1 is over!");
            else if (round == 2)
                GameMatView.showInput("Round 2 is over!");
            else
                GameMatView.showInput("Round 3 is over!");
            round--;
            if (round == 0) {
                if (winnerPlayer.getNumberOfWin() > loserPlayer.getNumberOfWin()) {
                    UserModel.getUserByUsername(winnerUsername).changeUserCoin(3000 + 3 * winnerPlayer.getMaxLifePoints());
                    UserModel.getUserByUsername(loserUsername).changeUserCoin(300);
                } else {
                    UserModel.getUserByUsername(loserUsername).changeUserCoin(3000 + 3 * loserPlayer.getMaxLifePoints());
                    UserModel.getUserByUsername(winnerUsername).changeUserCoin(300);
                }
                resetGame();
                MainMenuController.run();
            } else {
                String firstPlayer = PickFirstPlayer.chose(winnerUsername, loserUsername);
                if (firstPlayer.equals(winnerUsername)) {
                    winnerPlayer.startNewGame(UserModel.getUserByUsername(winnerUsername).userAllDecks.get(UserModel.getUserByUsername(winnerUsername).getActiveDeck()), true);
                    loserPlayer.startNewGame(UserModel.getUserByUsername(loserUsername).userAllDecks.get(UserModel.getUserByUsername(loserUsername).getActiveDeck()), false);
                } else {
                    winnerPlayer.startNewGame(UserModel.getUserByUsername(winnerUsername).userAllDecks.get(UserModel.getUserByUsername(winnerUsername).getActiveDeck()), false);
                    loserPlayer.startNewGame(UserModel.getUserByUsername(loserUsername).userAllDecks.get(UserModel.getUserByUsername(loserUsername).getActiveDeck()), true);
                }
                int numberOfCard;
                if (Player.getPlayerByName(winnerPlayer.getNickname()).getNumberOfMainDeckCards() == 0 || Player.getPlayerByName(winnerPlayer.getNickname()).getNumberOfSideDeckCards() == 0)
                    GameMatView.showInput("Oops! " + winnerPlayer.getNickname() + " You cant exchange card!");
                else {
                    do {
                        GameMatView.showInput(winnerPlayer.getNickname() + " do you want to exchange card?");
                        response = GameMatView.getCommand();
                    } while (!response.matches("yes|no"));
                    if (response.equals("yes")) {
                        while (true) {
                            GameMatView.showInput("Please enter the number of card you want to exchange:");
                            command = GameMatView.getCommand();
                            if (!command.matches("\\d+"))
                                continue;
                            numberOfCard = Integer.parseInt(command);
                            if (numberOfCard < Player.getPlayerByName(winnerPlayer.getNickname()).getNumberOfMainDeckCards() && numberOfCard < Player.getPlayerByName(winnerPlayer.getNickname()).getNumberOfSideDeckCards())
                                break;
                        }
                        for (int i = 0; i < numberOfCard; i++) {
                            do {
                                GameMatView.showInput("Please enter the exchange command");
                                command = GameMatView.getCommand();
                            } while (exchangeCard(winnerPlayer.getNickname()) != 1);
                        }
                    }
                }
                if (Player.getPlayerByName(loserPlayer.getNickname()).getNumberOfMainDeckCards() == 0 || Player.getPlayerByName(loserPlayer.getNickname()).getNumberOfSideDeckCards() == 0)
                    GameMatView.showInput("Oops! " + loserPlayer.getNickname() + " You cant exchange card!");
                else {
                    do {
                        GameMatView.showInput(loserPlayer.getNickname() + " do you want to exchange card?");
                        response = GameMatView.getCommand();
                    } while (!response.matches("yes|no"));
                    if (response.equals("yes")) {
                        while (true) {
                            GameMatView.showInput("Please enter the number of card you want to exchange:");
                            command = GameMatView.getCommand();
                            if (!command.matches("\\d+"))
                                continue;
                            numberOfCard = Integer.parseInt(command);
                            if (numberOfCard < Player.getPlayerByName(loserPlayer.getNickname()).getNumberOfMainDeckCards() && numberOfCard < Player.getPlayerByName(loserPlayer.getNickname()).getNumberOfSideDeckCards())
                                break;
                        }
                        for (int i = 0; i < numberOfCard; i++) {
                            do {
                                GameMatView.showInput("Please enter the exchange command");
                                command = GameMatView.getCommand();
                            } while (exchangeCard(loserPlayer.getNickname()) != 1);
                        }
                    }
                }
                GameMatView.showInput("Round " + round + " starts!");
                if (firstPlayer.equals(winnerUsername))
                    run(winnerPlayer.getNickname(), loserPlayer.getNickname());
                else
                    run(loserPlayer.getNickname(), winnerPlayer.getNickname());

            }
        }
    }

    public static int exchangeCard(String playerNickname) {
        if ((matcher = getMatcher(command, "exchange\\s+main\\s+cards\\s+(\\d+)with\\s+side\\s+card\\s+(\\d+)")).find()) {
            int cardAddressInMainDeck = Integer.parseInt(matcher.group(1));
            int cardAddressInSideDeck = Integer.parseInt(matcher.group(2));
            if (Player.getPlayerByName(playerNickname).exchangeCard(cardAddressInMainDeck, cardAddressInSideDeck) == 0)
                GameMatView.showInput("Oops! You cant exchange this two cards!");
            return 1;
        } else
            return 0;
    }

    public static void showGameBoard() {
        Player onlinePlayer = Player.getPlayerByName(onlineUser);
        GameMatModel ownGamMat = GameMatModel.getGameMatByNickname(onlineUser);
        Player rivalPlayer = Player.getPlayerByName(rivalUser);
        GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
        GameMatView.showInput(rivalUser + " : " + rivalPlayer.getLifePoint());
        for (int i = 0; i < 6; i++)
            System.out.print("   " + HandCardZone.getNumberOfFullHouse(rivalUser));
        GameMatView.showInput("\n" + rivalPlayer.getNumberOfMainDeckCards());
        String[] rivalSpellsMode = SpellTrapZoneCard.getAllSpellTrapMode(rivalUser);
        GameMatView.showInput("   " + rivalSpellsMode[4] + "   " + rivalSpellsMode[2] + "   " + rivalSpellsMode[1] + "   " + rivalSpellsMode[3] + "   " + rivalSpellsMode[5]);
        String[] rivalMonstersMode = MonsterZoneCard.getAllMonstersMode(rivalUser);
        GameMatView.showInput("   " + rivalMonstersMode[4] + "   " + rivalMonstersMode[2] + "   " + rivalMonstersMode[1] + "   " + rivalMonstersMode[3] + "   " + rivalMonstersMode[5]);
        if (rivalGameMat.getFieldZone().isEmpty())
            GameMatView.showInput(rivalGameMat.getNumberOfDeadCards() + "                      E\n");
        else {
            String field = rivalGameMat.getFieldZone();
            String[] split = field.split("/");
            GameMatView.showInput(rivalGameMat.getNumberOfDeadCards() + "                      " + split[1] + "\n");
        }

        GameMatView.showInput("--------------------------\n");

        if (ownGamMat.getFieldZone().isEmpty())
            GameMatView.showInput("E                      " + ownGamMat.getNumberOfDeadCards());
        else {
            String field = ownGamMat.getFieldZone();
            String[] split = field.split("/");
            GameMatView.showInput(split[1] + "                      " + ownGamMat.getNumberOfDeadCards());
        }

        String[] ownMonstersMode = MonsterZoneCard.getAllMonstersMode(onlineUser);
        GameMatView.showInput("   " + ownMonstersMode[5] + "   " + ownMonstersMode[3] + "   " + ownMonstersMode[1] + "   " + ownMonstersMode[2] + "   " + ownMonstersMode[4]);
        String[] ownSpellsMode = SpellTrapZoneCard.getAllSpellTrapMode(onlineUser);
        GameMatView.showInput("   " + ownSpellsMode[5] + "   " + ownSpellsMode[3] + "   " + ownSpellsMode[1] + "   " + ownSpellsMode[2] + "   " + ownSpellsMode[4]);
        GameMatView.showInput("                       " + onlinePlayer.getNumberOfMainDeckCards());
        for (int i = 0; i < 6; i++)
            System.out.print(HandCardZone.getNumberOfFullHouse(onlineUser) + "   ");
        GameMatView.showInput("\n" + onlineUser + " : " + onlinePlayer.getLifePoint());
    }

    public static void showSelectedCard() {
        if (errorOfNoCardSelected("rival")) {
            String[] split = selectedRivalCard.split("/");
            String cardModel = Card.getCardsByName(split[1]).getCardModel();
            if (split[0].equals("Monster"))
                GameMatView.showInput(MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), rivalUser).toString());
            else if (cardModel.equals("Monster")) {
                MonsterCard monster = MonsterCard.getMonsterByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Level: " + monster.getLevel() + "\n" +
                        "Type: " + monster.getMonsterType() + "\n" +
                        "ATK: " + monster.getAttack() + "\n" +
                        "DEF: " + monster.getDefend() + "\n" +
                        "Description: " + monster.getDescription());
            }
            else if (cardModel.equals("Spell")) {
                SpellCard spell = SpellCard.getSpellCardByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Spell" + "\n" +
                        "Type: " + spell.getCardModel() + "\n" +
                        "Description: " + spell.getDescription());
            }
            else {
                TrapCard trap = TrapCard.getTrapCardByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Trap" + "\n" +
                        "Type: " + trap.getCardModel() + "\n" +
                        "Description: " + trap.getDescription());
            }
            selectedRivalCard = "";
        }
        else if (errorOfNoCardSelected("own")) {
            String[] split = selectedOwnCard.split("/");
            String cardModel = Card.getCardsByName(split[1]).getCardModel();
            if (split[0].equals("Monster"))
                GameMatView.showInput(MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
            else if (cardModel.equals("Monster")) {
                MonsterCard monster = MonsterCard.getMonsterByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Level: " + monster.getLevel() + "\n" +
                        "Type: " + monster.getMonsterType() + "\n" +
                        "ATK: " + monster.getAttack() + "\n" +
                        "DEF: " + monster.getDefend() + "\n" +
                        "Description: " + monster.getDescription());
            }
            else if (cardModel.equals("Spell")) {
                SpellCard spell = SpellCard.getSpellCardByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Spell" + "\n" +
                        "Type: " + spell.getCardModel() + "\n" +
                        "Description: " + spell.getDescription());
            }
            else {
                TrapCard trap = TrapCard.getTrapCardByName(split[1]);
                GameMatView.showInput("Name: " + split[1] + "\n" +
                        "Trap" + "\n" +
                        "Type: " + trap.getCardModel() + "\n" +
                        "Description: " + trap.getDescription());
            }
        }
    }

    public static void backCommand() {
        while (true) {
            command = GameMatView.getCommand();
            if (command.equals("back")) {
                showGameBoard();
                return;
            } else
                GameMatView.showInput("invalid command");
        }
    }

    public static int activateSpellEffect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (split[0].equals("Hand") && Card.getCardsByName(split[1]).getCardModel().equals("Trap")) {
            GameMatView.showInput("You should set this trap first then activate it next turn!");
            selectedOwnCard = "";
            return 2;
        }
        if (split[0].equals("Monster") || (split[0].equals("Hand") && Card.getCardsByName(split[1]).getCardModel().equals("Monster"))) {
            GameMatView.showInput("activate effect is only for spell cards");
            selectedOwnCard = "";
            return 3;
        }
        if (!errorOfWrongPhase("activate", currentPhase))
            return 4;
        SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        String spellIcon = "";
        if (SpellCard.getSpellCardByName(split[1]) != null)
            spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        switch (split[0]) {
            case "Trap":
                if (ownSpell.getIsSetInThisTurn() || !Player.getPlayerByName(onlineUser).getCanUseTrap()) {
                    GameMatView.showInput("You cant activate this trap in this turn!");
                } else {
                    activateTrapEffect(true, null);
                    return 5;
                }
                break;
            case "Spell":
                if (ownSpell.getMode().equals("O")) {
                    GameMatView.showInput("you have already activated this card");
                    selectedOwnCard = "";
                    return 6;
                }
                if (spellIcon.equals("Ritual")) {
                    int result = ritualSummon();
                    if (result != -1)
                        ownSpell.removeSpellTrapFromZone();
                    return 7 ;
                } else {
                    if (spellIcon.equals("Quick-play") && ownSpell.getIsSetInThisTurn()) {
                        GameMatView.showInput("Oops! You cant activate the effect in the turn you set the card!");
                        selectedOwnCard = "";
                        return 8;
                    }
                    ownSpell.setMode("O");
                    chooseSpellEffectController(spellIcon, ownSpell);
                }
                checkForSpellAbsorption();
                break;
            case "Field":
                ownGameMat.changeModeOfFieldCard("O");
                SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                GameMatView.showInput("spell activated");
                checkForSpellAbsorption();
                break;
            case "Hand":
                if (spellIcon.equals("Field")) {
                    if (!ownGameMat.getFieldZone().equals("")) {
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    }
                    handCard.removeFromHandCard();
                    ownGameMat.addToFieldZone(split[1], "O");
                    SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                } else {
                    if (!errorOfFullZone("Spell"))
                        return 1;
                    if (spellIcon.equals("Ritual")) {
                        int result = ritualSummon();
                        if (result != -1)
                            handCard.removeFromHandCard();
                        selectedOwnCard = "";
                        return 1;
                    }
                    if (spellIcon.equals("Quick-play")) {
                        GameMatView.showInput("Oops! You cant activate the effect in the turn you set the card!");
                        selectedOwnCard = "";
                        return 1;
                    } else {
                        handCard.removeFromHandCard();
                        addToSpellTrapZoneCard(split[1], "O");
                        ownSpell = SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
                        if (chooseSpellEffectController(spellIcon, ownSpell) == 0) {
                            if (ownSpell.getIcon().equals("Continuous"))
                                ownSpell.removeSpellTrapFromZone();
                            GameMatView.showInput("Your spell is not activated and is gone to graveyard!");
                            selectedOwnCard = "";
                            return 1;
                        }
                    }
                }
                if (split[1].equals("Messenger of peace"))
                    SpellEffect.messengerOfPeace(onlineUser, rivalUser, SpellTrapZoneCard.getNumberOfFullHouse(onlineUser));
                checkForSpellAbsorption();
                int result = checkForSetTrapToActivateInRivalTurn("Magic Jammer", null);
                if (result == 1)
                    ownSpell.removeSpellTrapFromZone();
                GameMatView.showInput("spell activated");
                break;
        }
        selectedOwnCard = "";
        return 0;
    }

    public static int chooseSpellEffectController(String spellIcon, SpellTrapZoneCard ownSpell) {
        switch (spellIcon) {
            case "Normal":
                GameMatView.showInput("I want to activate a Spell!");
                showGameBoard();
                int result = SpellEffect.normalEffectController(ownSpell, onlineUser, rivalUser);
                if (!ownSpell.getSpellTrapName().equals("Swords of Revealing Light"))
                    ownSpell.removeSpellTrapFromZone();
                else if (ownSpell.getSpellTrapName().equals("Swords of Revealing Light") && result == 0)
                    ownSpell.removeSpellTrapFromZone();
                return result;
            case "Quick-play":
                if (SpellEffect.quickPlayEffectController(ownSpell, onlineUser, rivalUser) == 1)
                    ownSpell.removeSpellTrapFromZone();
                break;
            case "Equip":
                if (getAddressOfRelatedMonster(ownSpell) != 0) {
                    SpellEffect.equipEffectController(ownSpell, onlineUser, rivalUser);
                    return 1;
                } else {
                    ownSpell.removeSpellTrapFromZone();
                    return 0;
                }
        }
        return 1;
    }

    public static int getAddressOfRelatedMonster(SpellTrapZoneCard ownSpell) {
        String spellName = ownSpell.getSpellTrapName();
        do {
            GameMatView.showInput("Whose Monster do you want to equip? (own/rival)");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
        } while (!response.matches("own|rival"));
        int address;
        if (response.equals("own")) {
            address = getResponseForEquipSpell("own", spellName);
            if (address != 0) {
                ownSpell.setRelatedMonsterAddress("own", address);
            }
            else
                return 0;
        } else {
            address = getResponseForEquipSpell("rival", spellName);
            if (address != 0)
                ownSpell.setRelatedMonsterAddress("rival", address);
            else
                return 0;
        }
        return 1;
    }

    public static int getResponseForEquipSpell(String whoseResponse, String spellName) {
        MonsterZoneCard monsterCard;
        while (true) {
            GameMatView.showInput("Please enter the address of one of your " + whoseResponse + " Monster to equip:");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
            if (!response.matches("[1-5]"))
                continue;
            if (whoseResponse.equals("own"))
                monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser);
            else
                monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser);
            if (monsterCard == null || monsterCard.getMode().equals("DH"))
                continue;
            String monsterType = monsterCard.getMonsterType();
            if (spellName.equals("Sword of dark destruction")) {
                if (monsterType.equals("Fiend") || monsterType.equals("Spellcaster")) {
                    break;
                }
            }
            if (spellName.equals("Magnum Shield") && monsterType.equals("Warrior"))
                break;
            if (spellName.equals("Black Pendant") || spellName.equals("United We Stand"))
                break;
        }
        return Integer.parseInt(response);
    }

    public static int checkForSpellAbsorption() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Spell Absorption");
        if (address != -1)
            SpellEffect.spellAbsorption(onlineUser);
        address = SpellTrapZoneCard.isThisSpellActivated(rivalUser, "Spell Absorption");
        if (address != -1)
            SpellEffect.spellAbsorption(rivalUser);
        return 1;
    }

    public static int checkForMessengerOfPeace() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Messenger of peace");
        if (address != -1) {
            SpellEffect.messengerOfPeace(onlineUser, rivalUser, address);
            do {
                GameMatView.showInput("Do you want to destroy Messenger of peace? (yes/no)");
                response = GameMatView.getCommand();
            } while (!response.matches("yes|no"));
            if (response.equals("yes")) {
                SpellEffect.returnPermissionMessenger(address, rivalUser, onlineUser);
                SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).removeSpellTrapFromZone();
            }
            else {
                Player.getPlayerByName(onlineUser).changeLifePoint(-100);
            }
        }
        return 1;
    }

    public static int checkForSupplySquad() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Supply Squad");
        if (address != -1 && GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadMonsterThisTurn() != 0)
            new HandCardZone(onlineUser, Player.getPlayerByName(onlineUser).drawCard(true));
        address = SpellTrapZoneCard.isThisSpellActivated(rivalUser, "Supply Squad");
        if (address != -1 && GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadMonsterThisTurn() != 0)
            new HandCardZone(rivalUser, Player.getPlayerByName(rivalUser).drawCard(true));
        return 1;
    }

    public static void resetGame() {
        MonsterZoneCard.allMonsterCards.clear();
        SpellTrapZoneCard.allSpellTrapCards.clear();
        GameMatModel.playerGameMat.clear();
        Player.allPlayers.clear();
    }

}