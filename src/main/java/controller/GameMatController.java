package controller;
import view.GameMatView;
import model.*;
import java.util.regex.*;


public class GameMatController {

    private static String selectedOwnCard = "";
    private static String selectedRivalCard = "";
    private static String onlineUser = "";
    private static String rivalUser = "";
    private static String command;
    private static String response;
    private static Matcher matcher;
    private static int numberOfMonster;
    private static int trapAddress;


    public static void commandController(String firstPlayer, String secondPlayer) {
        GameMatView.showInput("The game starts!");
        onlineUser = firstPlayer;
        rivalUser = secondPlayer;
        GameMatView.showInput("its " + onlineUser + "’s turn");
        GameMatView.showInput("phase: " + Phase.Draw_Phase);
        showGameBoard();
        Phase currentPhase;
        int counter = 0;
        int counter1 = 0;
        while (true) {
            numberOfMonster = MonsterZoneCard.getNumberOfFullHouse(onlineUser);
            currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
            if (onlineUser.equals("AI")) {
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (currentPhase.equals(Phase.Draw_Phase)) {
                    command = "next phase";
                }
                if (currentPhase.equals(Phase.Standby_Phase)) {
                    command = "next phase";
                }
                if (currentPhase.equals(Phase.Main_Phase1)) {
                    if (counter == 0) {
                        command = "select --hand 1";
                        counter++;
                    }
                    else if (counter == 1) {
                        command = "summon";
                        counter++;
                    }
                    else if (counter == 2) {
                        command = "next phase";
                    }
                }
                if (currentPhase.equals(Phase.Battle_Phase)) {
                    counter = 0;
                    if (counter1 == 0) {
                        command = "select --monster 1";
                        counter1++;
                    }
                    else if (counter1 == 1) {
                        if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) == 0) {
                            command = "attack direct";
                        } else {
                            command = "attack 1";
                        }
                        counter1++;
                    }
                    else if (counter1 == 2) {
                        command = "next phase";
                        counter1 = 0;
                    }
                }
                if (currentPhase.equals(Phase.Main_Phase2)) {
                    command = "next phase";
                }
            }
            else {
                command = GameMatView.getCommand();
            }
            if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)\\s+--opponent$")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                continue;
            }

            if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)$")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), true);
                showGameBoard();
                continue;
            }
            if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+--monster\\s+(\\d+)$")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                continue;
            }

            if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)\\s+--opponent$")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                continue;
            }
            if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)$")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), true);
                showGameBoard();
                continue;
            }
            if ((matcher = getMatcher(command, "^select\\s+--opponent\\s+--spell\\s+(\\d+)$")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^select\\s+--field$").find()) {
                selectFieldCard(true);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^select\\s+--field\\s+--opponent$").find() || getMatcher(command, "^select\\s+--opponent\\s+--field$").find()) {
                selectFieldCard(false);
                showGameBoard();
                continue;
            }
            if ((matcher = getMatcher(command, "^select\\s+--hand\\s+(\\d+)$")).find()) {
                selectHandCard(Integer.parseInt(matcher.group(1)));
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^select\\s+-d$").find()) {
                selectDelete();
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^next\\s+phase$").find()) {
                changePhase(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^summon$").find()) {
                summon(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^set$").find()) {
                set(currentPhase);
                showGameBoard();
                continue;
            }
            if (changeMonsterPosition(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^flip-summon$").find()) {
                flipSummon(currentPhase);
                showGameBoard();
                continue;
            }
            if (attack(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^attack\\s+direct$").find()) {
                attackDirect(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^activate\\s+effect$").find()) {
                activateSpellEffect(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "^card\\s+show\\s+--selected$").find()) {
                showSelectedCard();
                backCommand();
                continue;
            }
            if (getMatcher(command, "^show\\s+graveyard$").find()) {
                GameMatModel.getGameMatByNickname(onlineUser).showGraveyard();
                backCommand();
                continue;
            }
            if (getMatcher(command, "^show\\s+graveyard\\s+--opponent$").find()) {
                getPermission();
                continue;
            }
            if (getMatcher(command, "^show\\s+main\\s+deck$").find()) {
                Player.getPlayerByName(onlineUser).showMainDeck();
                backCommand();
                continue;
            }
            if (getMatcher(command, "^show\\s+side\\s+deck$").find()) {
                Player.getPlayerByName(onlineUser).showSideDeck();
                backCommand();
                continue;
            }
            if (getMatcher(command, "^show\\s+my\\s+hand$").find()) {
                HandCardZone.showHandCard(onlineUser);
                backCommand();
                continue;
            }
            if (getMatcher(command, "^surrender$").find()) {
                endGame("surrender", onlineUser);
                break;
            }
            if (getMatcher(command, "^menu exit$").find())
                break;
            GameMatView.showInput("invalid command");
        }
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
        } else
            GameMatView.showInput("Oops! You dont have permission to see your rival graveyard!");
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
            if (selectedRivalCard.equals("")) {
                GameMatView.showInput("no rival card is selected yet");
                return false;
            } else
                return true;
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
    //--------------------------------------------------------------------------------------------------------------------
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

    public static void summonInHand(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!handCard.getKind().equals("Monster")) {
            GameMatView.showInput("you can’t summon this card");
            return;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return;
        if (!errorOfFullZone("Monster"))
            return;
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
                    if (MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser) == 0) {
                        if (split[1].equals("\"Terratiger, the Empowered Warrior\""))
                            MonsterEffect.terratiger(ownMonster, onlineUser);
                        if (split[1].equals("Herald of Creation"))
                            MonsterEffect.heraldOfCreation(ownMonster, onlineUser);
                    }
                } else if (monsterLevel == 5 || monsterLevel == 6) {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                        GameMatView.showInput("there are not enough cards for tribute");
                    else if (tributeMonster(1, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                } else {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                        GameMatView.showInput("there are not enough cards for tribute");
                    else if (tributeMonster(2, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                }
            }
        }
    }

    public static void summonInHandSuccessfully(Player player, HandCardZone handCard) {
        if (addToMonsterZoneCard(handCard.getCardName(), "OO") == 0)
            return;
        player.setCanSetSummonMonster(false);
        handCard.removeFromHandCard();
        GameMatView.showInput("summoned successfully");
        /////////trap
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
        if (ownMonster.getAttack() >= 1000) {
            trapAddress = checkForSetTrapToActivateInRivalTurn("Trap Hole");
            if (trapAddress != -1) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonster.getAddress(), onlineUser).removeMonsterFromZone();
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
            }
        }
        trapAddress = checkForSetTrapToActivateInRivalTurn("Torrential Tribute");
        if (trapAddress != -1) {
            SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
        }
    }

    public static void summonInMonsterZone(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getMode().equals("OO") || ownMonster.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t summon this card");
            return;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return;
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
    }

    public static void summonInMonsterZoneSuccessfully(Player player, MonsterZoneCard ownMonster) {
        player.setCanSetSummonMonster(false);
        ownMonster.setMode("OO");
        ownMonster.setHaveChangedPositionThisTurn(true);
        GameMatView.showInput("summoned successfully");
        ////////////trap
        if (ownMonster.getAttack() >= 1000) {
            trapAddress = checkForSetTrapToActivateInRivalTurn("Trap Hole");
            if (trapAddress != -1) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonster.getAddress(), onlineUser).removeMonsterFromZone();
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
            }
        }
        trapAddress = checkForSetTrapToActivateInRivalTurn("Torrential Tribute");
        if (trapAddress != -1) {
            SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
        }
    }

    public static int changeMonsterPosition(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command, "^set\\s+--position\\s+(attack|defense)$")).find()) {
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
                selectedOwnCard = "";
                GameMatView.showInput("monster card position changed successfully");
                MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
            }
            return 1;
        }
        return 0;
    }

    public static void set(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Hand")) {
            GameMatView.showInput("you can’t set this card");
            return;
        }
        if (!errorOfWrongPhase("set", currentPhase))
            return;
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        String cardName = handCard.getCardName();
        switch (handCard.getKind()) {
            case "Monster" -> {
                if (!errorOfFullZone("Monster"))
                    return;
                if (!player.getCanSetSummonMonster())
                    GameMatView.showInput("you already summoned/set on this turn");
                else if (cardName.equals("Beast King Barbaros"))
                    GameMatView.showInput("Oops! Beast King Barbaros cant be set!");
                else {
                    if (addToMonsterZoneCard(cardName, "DH") == 0)
                        return;
                    handCard.removeFromHandCard();
                    player.setCanSetSummonMonster(false);
                    GameMatView.showInput("set successfully");
                }
            }
            case "Spell" -> {
                if (!errorOfFullZone("Spell"))
                    return;
                HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser).removeFromHandCard();
                if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Field")) {
                    GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                    if (!ownGameMat.getFieldZone().equals(""))
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    GameMatModel.getGameMatByNickname(onlineUser).addToFieldZone(cardName, "H");
                } else {
                    addToSpellTrapZoneCard(handCard.getCardName(), "H", currentPhase);
                    SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                }
                GameMatView.showInput("set successfully");
            }
            case "Trap" -> {
                if (!errorOfFullZone("Spell"))
                    return;
                HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser).removeFromHandCard();
                addToSpellTrapZoneCard(handCard.getCardName(), "H", currentPhase);
                SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                GameMatView.showInput("Set successfully");
            }
        }
        selectedOwnCard = "";
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

    public static void addToSpellTrapZoneCard(String spellTrapName, String mode, Phase currentPhase) {
        new SpellTrapZoneCard(onlineUser, spellTrapName, mode);
//        trapAddress = checkForSetTrapToActivateInRivalTurn("Magic Jammer");
//        if (trapAddress != 0) {
//            SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
//        }
    }

    public static boolean errorOfWrongPhase(String whichAction, Phase currentPhase) {
        switch (whichAction) {
            case "summon":
            case "set":
            case "change":
            case "flip":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    GameMatView.showInput("action not allowed in this phase");
                    selectedOwnCard = "";
                    return false;//wrong Phase
                } else
                    return true;
            case "activate":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    GameMatView.showInput("you can’t activate an effect on this turn");
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;//no error
            case "attack":
            case "attackDirect":
                if (!currentPhase.name().equals("Battle_Phase")) {
                    GameMatView.showInput("you can’t do this action in this phase");
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;//no error
        }
        return false;
    }

    public static boolean errorOfFullZone(String whichZone) {
        switch (whichZone) {
            case "Monster" -> {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                    GameMatView.showInput("monster card zone is full");
                    selectedOwnCard = "";
                    return false;
                }
                return true;
            }
            case "Spell" -> {
                if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                    GameMatView.showInput("spell card zone is full");
                    selectedOwnCard = "";
                    return false;
                }
                return true;
            }
            case "Trap" -> {
                if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                    GameMatView.showInput("trap card zone is full");
                    selectedOwnCard = "";
                    return false;
                }
                return true;
            }
        }
        return true;
    }
    //--------------------------------------------------------------------------------------------------------------------
    public static int specialSummon(HandCardZone handCard, String monsterName) {
        return switch (monsterName) {
            case "Gate Guardian" -> MonsterEffect.gateGuardian(handCard, onlineUser, rivalUser);
            case "Beast King Barbaros" -> MonsterEffect.beastKingBarbaros(handCard, onlineUser, rivalUser);
            case "The Tricky" -> MonsterEffect.theTricky(handCard, onlineUser);
            default -> 2;
        };
    }

    public static int ritualSummon(SpellTrapZoneCard ownSpell, HandCardZone handCardSpell, Phase currentPhase) {
        int ritualMonsterAddress = HandCardZone.doIHaveAnyRitualMonster(onlineUser);
        if (ritualMonsterAddress == 0) {
            GameMatView.showInput("there is no way you could ritual summon a monster");
            return 0;
        }
        if (MonsterZoneCard.getSumOfMonstersLevel(onlineUser) < 7) {
            GameMatView.showInput("there is no way you could ritual summon a monster");
            return 0;
        }

        GameMatView.showInput("Please enter the address of a Ritual Monster in your hand to summon: ");
        String response;
        while (true) {
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
            else if (!response.matches("\\d+"))
                GameMatView.showInput("you should ritual summon right now");
            else if (HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                GameMatView.showInput("Please enter the address of a ritual Monster correctly:");
                continue;
            }
            HandCardZone card = HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser);
            if (!card.getKind().equals("Monster") || !MonsterCard.getMonsterByName(card.getCardName()).getMonsterType().equals("Ritual")) {
                GameMatView.showInput("Please enter the address of a ritual Monster correctly:");
            } else
                break;
        }
        ritualMonsterAddress = Integer.parseInt(response);
        HandCardZone handCardRitualMonster = HandCardZone.getHandCardByAddress(ritualMonsterAddress, onlineUser);
        String ritualMonsterName = handCardRitualMonster.getCardName();
        GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
        response = GameMatView.getCommand();
        while (!response.matches("[1-3]")) {
            if (response.equals("cancel")) {
                return 0;
            }
            response = GameMatView.getCommand();
        }
        int numberOfTribute = Integer.parseInt(response);
        if (tributeMonster(numberOfTribute, "Ritual") == 1) {
            GameMatView.showInput("Please enter Ritual Monster mode: (defensive/attacking)");
            command = GameMatView.getCommand();
            while (!command.matches("defensive") && !command.matches("attacking")) {
                GameMatView.showInput("Please enter the answer correctly: (defensive/attacking)");
                command = GameMatView.getCommand();
            }
            if (ownSpell != null)
                ownSpell.removeSpellTrapFromZone();
            else
                handCardSpell.removeFromHandCard();
            if (command.equals("defensive"))
                new MonsterZoneCard(onlineUser, ritualMonsterName, "DO", false, false);
            else
                new MonsterZoneCard(onlineUser, ritualMonsterName, "OO", false, false);
            handCardRitualMonster.removeFromHandCard();
        }
        return 1;
    }

    public static int tributeMonster(int numberOfTribute, String monsterType) {
        int[] victimAddress = getAddressOfTributeMonster(numberOfTribute);
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

    public static int[] getAddressOfTributeMonster(int numberOfTribute) {
        int[] tributeMonsterAddress = new int[numberOfTribute];
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
                if (address < 1 || address > 5 || MonsterZoneCard.getMonsterCardByAddress(address, onlineUser) == null)
                    GameMatView.showInput("Please enter the address of a monster correctly:");
                else {
                    tributeMonsterAddress[i - 1] = address;
                    break;
                }
            }
        }
        return tributeMonsterAddress;
    }

    public static void flipSummon(Phase currentPhase) {
        if (errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t change this card position");
            return;
        }
        if (!errorOfWrongPhase("flip", currentPhase))
            return;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!ownMonster.getMode().equals("DH") || ownMonster.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t flip summon this card");
        } else {
            trapAddress = checkForSetTrapToActivateInRivalTurn("Solemn Warning");
            if (trapAddress != 0) {
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                return;
            }
            ownMonster.setMode("OO");
            ownMonster.setHaveChangedPositionThisTurn(true);
            GameMatView.showInput("flip summoned successfully");
            if (ownMonster.getAttack() >= 1000) {
                trapAddress = checkForSetTrapToActivateInRivalTurn("Trap Hole");
                if (trapAddress != 0) {
                    MonsterZoneCard.getMonsterCardByAddress(ownMonster.getAddress(), onlineUser).removeMonsterFromZone();
                    SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                }
            }
            trapAddress = checkForSetTrapToActivateInRivalTurn("Torrential Tribute");
            if (trapAddress != 0) {
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
            }

        }
        selectedOwnCard = "";
    }







    public static int attack(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command, "^attack\\s+(\\d+)$")).find()) {
            int rivalMonsterAddress = Integer.parseInt(matcher.group(1));
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split("/");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t attack with this card");
                return 1;
            } else if (!errorOfWrongPhase("attack", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (!ownMonster.getMode().equals("OO")) {
                GameMatView.showInput("your card is not in an attack mode");
                return 1;
            }
            if (ownMonster.getHaveAttackThisTurn()) {
                GameMatView.showInput("this card already attacked");
                return 1;
            }
            if (!ownMonster.getCanAttack()) {
                GameMatView.showInput("this Monster cant attack because of a spell effect!");
                return 1;
            }
            MonsterZoneCard rivalMonster = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress, rivalUser);
            if (rivalMonster == null) {
                GameMatView.showInput("there is no card to attack here");
                return 1;
            }
            if (!rivalMonster.getCanAttackToThisMonster()) {
                GameMatView.showInput("you cant attack to this monster!");
                return 1;
            }
            GameMatView.showInput("I want to attack to your Monster!");
            int trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Magic Cylinder");
            if (trapAddress != -1) {

            }
            //////////////////////////////////////////////////////////////////
            int damage;
            String rivalMonsterName = rivalMonster.getMonsterName();
            if (rivalMonsterName.equals("Suijin")) {
                if (MonsterEffect.suijin(rivalMonster) == 1)
                    return 1;
            } else if (rivalMonsterName.equals("Texchanger")) {
                if (MonsterEffect.texchanger(rivalMonster, rivalUser) == 1)
                    return 1;
            }
            if (rivalMonsterName.equals("Exploder Dragon")) {
                rivalMonster.removeMonsterFromZone();
                ownMonster.removeMonsterFromZone();
                return 1;
            }
            String rivalMonsterMode = rivalMonster.getMode();
            if (rivalMonsterMode.equals("OO")) {
                damage = ownMonster.getAttack() - rivalMonster.getAttack();
                if (damage > 0) {
                    Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                    if (!rivalMonsterName.equals("Marshmallon"))
                        rivalMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                    if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                        Player.getPlayerByName(rivalUser).setLifePoint(0);
                        endGame("lp", rivalUser);
                    }
                } else if (damage == 0) {
                    ownMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("both you and your opponent monster cards are destroyed and no one receives damage");
                } else {
                    Player.getPlayerByName(onlineUser).changeLifePoint(damage);
                    ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("your monster card is destroyed and you received " + -1 * damage + " battle damage");
                    if (Player.getPlayerByName(onlineUser).getLifePoint() <= 0) {
                        Player.getPlayerByName(onlineUser).setLifePoint(0);
                        endGame("lp", onlineUser);
                    }
                }
            } else {
                if (ownMonster.getAttack() > rivalMonster.getDefend()) {
                    if (rivalMonster.getMode().equals("DH")) {
                        rivalMonster.setMode("DO");
                        showGameBoard();
                    }
                    if (!rivalMonsterName.equals("Marshmallon"))
                        rivalMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and the defense position monster is destroyed");
                    else
                        GameMatView.showInput("the defense position monster is destroyed");
                } else if (ownMonster.getAttack() == rivalMonster.getDefend()) {
                    if (rivalMonsterMode.equals("DH")) {
                        rivalMonster.setMode("DO");
                        showGameBoard();
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed");
                    } else
                        GameMatView.showInput("no card is destroyed");
                } else {
                    damage = rivalMonster.getDefend() - ownMonster.getAttack();
                    Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                    if (rivalMonsterMode.equals("DH")) {
                        rivalMonster.setMode("DO");
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
            if (rivalMonsterName.equals("Marshmallon"))
                MonsterEffect.marshmallon(rivalMonster, onlineUser);
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

    public static void attackDirect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t attack with this card");
            return;
        }
        if (!errorOfWrongPhase("attackDirect", currentPhase))
            return;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getHaveAttackThisTurn())
            GameMatView.showInput("this card already attacked");
        else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) != 0)
            GameMatView.showInput("you can’t attack the opponent directly");
        else if (!ownMonster.getCanAttack())
            GameMatView.showInput("this Monster cant attack because of a spell effect!");
        else {
            GameMatView.showInput("I want to attack you directly!");
            trapAddress = checkForSetTrapToActivateInRivalTurn("Magic Cylinder");
            if (trapAddress != 0) {
                ownMonster.removeMonsterFromZone();
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                return;
            }
            trapAddress = checkForSetTrapToActivateInRivalTurn("Mirror Force");
            if (trapAddress != 0) {
                ownMonster.removeMonsterFromZone();
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                return;
            }
            trapAddress = checkForSetTrapToActivateInRivalTurn("Negate Attack");
            if (trapAddress != 0) {
                SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                return;
            }
            int damage = ownMonster.getAttack();
            Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
            GameMatView.showInput("your opponent receives " + damage + " battle damage");
            if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                Player.getPlayerByName(rivalUser).setLifePoint(0);
                endGame("lp", rivalUser);
            }
        }
        selectedOwnCard = "";
    }

    public static SpellTrapZoneCard checkForIsAttackedTrap(MonsterZoneCard ownMonster) {
        int trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Magic Cylinder");
        if (trapAddress != -1) {
            TrapEffect.magicCylinder(rivalUser, onlineUser, ownMonster);
            ownMonster.removeMonsterFromZone();
            return SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser);
        }
        trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Mirror Force");
        if (trapAddress == -1) {
            TrapEffect.mirrorForce(onlineUser, rivalUser);
            return SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser);
        }
        trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Negate Attack");
        if (trapAddress == -1) {
            return SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser);
        }
        return null;
    }




    public static int checkForSetTrapToActivateInRivalTurn(String trapName) {
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
                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find()) {
                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1) {
                            if (SpellTrapZoneCard.doesAddressAndTrapNameMatch(rivalUser, trapName, Integer.parseInt(matcher.group(1))))
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
                    if (getMatcher(response, "^activate\\s+effect$").find()) {
                        activateTrapEffect(null, false);
                        break;
                    }
                    else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
            }
        }
        return -1;
    }



    public static void activateSpellEffect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split("/");
        if (split[0].equals("Trap") || (split[0].equals("Hand") && Card.getCardsByName(split[1]).getCardModel().equals("Trap"))) {
            activateTrapEffect(null, true);
            return;
        }
        if (split[0].equals("Monster") || (split[0].equals("Hand") && !Card.getCardsByName(split[1]).getCardModel().equals("Spell"))) {
            GameMatView.showInput("activate effect is only for spell cards");
            return;
        }
        if (!errorOfWrongPhase("activate", currentPhase))
            return;
        String spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
        SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        System.out.println(Integer.parseInt(split[2]));
        System.out.println(handCard.getAddress());
        switch (split[0]) {
            case "Spell" -> {
                if (ownSpell.getMode().equals("O")) {
                    GameMatView.showInput("you have already activated this card");
                    return;
                }
                if (spellIcon.equals("Ritual"))////////////////ritual
                    ritualSummon(ownSpell, handCard, currentPhase);
                else {
                    ownSpell.setMode("O");
                    chooseSpellEffectController(spellIcon, ownSpell);
                }
                checkForSpellAbsorption();
            }
            case "Field" -> {
                GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                ownGameMat.changeModeOfFieldCard("O");
                SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                GameMatView.showInput("spell activated");
                checkForSpellAbsorption();
            }
            case "Hand" -> {
                if (spellIcon.equals("Field")) {
                    GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                    if (!ownGameMat.getFieldZone().equals("")) {
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    }
                    handCard.removeFromHandCard();
                    ownGameMat.addToFieldZone(split[1], "O");
                    GameMatView.showInput("spell activated");
                    SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                }
                else {
                    if (!errorOfFullZone("Spell"))
                        return;
                    else if (spellIcon.equals("Ritual"))
                        ritualSummon(ownSpell, handCard, currentPhase);
                    else {
                        handCard.removeFromHandCard();
                        new SpellTrapZoneCard(onlineUser, split[1], "O");
                        ownSpell = SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
                        if (chooseSpellEffectController(spellIcon, ownSpell) == 0) {
                            if (ownSpell.getIcon().equals("Continuous"))
                                ownSpell.removeSpellTrapFromZone();
                            GameMatView.showInput("Your spell is not activated and is gone to graveyard!");
                            return;
                        }
                    }
                }
                if (split[1].equals("Messenger of peace"))
                    SpellEffect.messengerOfPeace(onlineUser, rivalUser);
                checkForSpellAbsorption();
                GameMatView.showInput("spell activated");
            }
        }
        selectedOwnCard = "";
    }

    public static int checkForQuickSpellInZoneEveryTurn(String SpellName, Phase currentPhase) {
        int address = SpellTrapZoneCard.doesThisCardNameExist(rivalUser, SpellName);
        if (address != -1) {
            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
            showGameBoard();
            do {
                GameMatView.showInput("do you want to activate " + SpellName + " ? (yes/no)");
                response = GameMatView.getCommand();
            } while (!response.matches("yes|no"));
            if (response.equals("no")) {
                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
                return 0;
            } else {
                while (true) {
                    response = GameMatView.getCommand();
                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find()) {
                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1)
                            break;
                        showGameBoard();
                    } else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
                while (true) {
                    response = GameMatView.getCommand();
                    if (getMatcher(response, "^activate\\s+effect$").find()) {
                        activateSpellEffect(Phase.Main_Phase1);
                        break;
                    } else
                        GameMatView.showInput("it’s not your turn to play this kind of moves");
                }
            }
        }
        return 0;
    }

    public static void checkForSpellAbsorption() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Spell Absorption");
        if (address != -1)
            SpellEffect.spellAbsorption(onlineUser);
        address = SpellTrapZoneCard.isThisSpellActivated(rivalUser, "Spell Absorption");
        if (address != -1)
            SpellEffect.spellAbsorption(rivalUser);
    }

    public static void checkForMessengerOfPeace() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Messenger of peace");
        if (address != -1) {
            SpellEffect.messengerOfPeace(onlineUser, rivalUser);
            do {
                GameMatView.showInput("Do you want to destroy Messenger of peace? (yes/no)");
                response = GameMatView.getCommand();
            } while (!response.matches("yes|no"));
            if (response.equals("yes"))
                SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).removeSpellTrapFromZone();
            else {
                Player.getPlayerByName(onlineUser).changeLifePoint(-100);
            }
        }
    }

    public static void checkForSupplySquad() {
        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Supply Squad");
        if (address != -1 && numberOfMonster != MonsterZoneCard.getNumberOfFullHouse(onlineUser))
            SpellEffect.supplySquad(onlineUser);
    }


    public static int activateTrapQuickSpellEffect() {
        if (!errorOfNoCardSelected("own"))
            return 0;
        String[] split = selectedOwnCard.split("/");
        String spellTrapIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
        SpellTrapZoneCard trapQuickSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (spellTrapIcon.equals("Quick-play") || trapQuickSpell.getKind().equals("Trap")) {
            if (trapQuickSpell.getMode().equals("O")) {
                GameMatView.showInput("you have already activated this card");
                return 0;
            } else {
                GameMatView.showInput("spell/trap activated");
                GameMatView.showInput(trapQuickSpell.getSpellTrapName() + "is activated!");
                if (trapQuickSpell.getKind().equals("Trap")) {
                    switch (split[1]) {
                        case "Mind Crush" -> TrapEffect.mindCrush(rivalUser, onlineUser);
                        case "Time Seal" -> TrapEffect.timeSeal(onlineUser);
                        case "Call of the Haunted" -> TrapEffect.callOfTheHaunted(rivalUser);
                    }
                } else
                    SpellEffect.quickPlayEffectController(trapQuickSpell, onlineUser, rivalUser);
                return 1;
            }
        } else {
            GameMatView.showInput("you can activate effect just for trap and quick-play spell cards!");
        }
        selectedOwnCard = "";
        return 0;
    }

//    public static int checkForTrapInZoneEveryTurn(String trapName, Phase currentPhase, MonsterZoneCard ownMonster) {
//        int address = SpellTrapZoneCard.doesThisCardNameExist(rivalUser, trapName);
//        if (address != -1) {
//            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
//            showGameBoard();
//            do {
//                GameMatView.showInput(rivalUser + " do you want to activate " + trapName + " ? (yes/no)");
//                response = GameMatView.getCommand();
//            } while (!response.matches("yes|no"));
//            if (response.equals("no")) {
//                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
//                return 0;
//            } else {
//                while (true) {
//                    response = GameMatView.getCommand();
//                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find()) {
//                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1)
//                            break;
//                        showGameBoard();
//                    }
//                    else
//                        GameMatView.showInput("it’s not your turn to play this kind of moves");
//                }
//                while (true) {
//                    response = GameMatView.getCommand();
//                    if (getMatcher(response, "^activate\\s+effect$").find())
//                        return activateTrapEffect(ownMonster, false);
//                    else
//                        GameMatView.showInput("it’s not your turn to play this kind of moves");
//                }
//            }
//        }
//        return 0;
//    }


    public static int chooseSpellEffectController(String spellIcon, SpellTrapZoneCard ownSpell) {
        switch (spellIcon) {
            case "Normal":
                GameMatView.showInput("I want to activate a Spell!");
                showGameBoard();
                if (SpellEffect.normalEffectController(ownSpell, onlineUser, rivalUser) == 1) {
                    ownSpell.removeSpellTrapFromZone();
                    return 1;
                } else
                    return 0;
            case "Quick-play":
                if (!ownSpell.getIsSetInThisTurn()) {
                    if (SpellEffect.quickPlayEffectController(ownSpell, onlineUser, rivalUser) == 1)
                        ownSpell.removeSpellTrapFromZone();
                    break;
                }
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
            if (address != 0)
                ownSpell.setRelatedMonsterAddress("own", address);
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

    public static int activateTrapEffect(MonsterZoneCard rivalMonster, Boolean isInYourTurn) {
        String[] split = selectedOwnCard.split("/");
        SpellTrapZoneCard ownTrap;
        if (split[0].equals("Hand")) {
            HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser).removeFromHandCard();
            new SpellTrapZoneCard(onlineUser, split[1], "O");
            ownTrap = SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
        }
        else {
            ownTrap = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
            ownTrap.setMode("O");
        }
        GameMatView.showInput("trap activated");
        if (!ownTrap.getIsSetInThisTurn()) {
            if (isInYourTurn) {
                if (split[1].equals("Mind Crush"))
                    TrapEffect.mindCrush(onlineUser, rivalUser);
                if (split[1].equals("Call of the Haunted"))
                    TrapEffect.callOfTheHaunted(onlineUser);
                if (split[1].equals("Time Seal"))
                    TrapEffect.timeSeal(rivalUser);
            }
            else {
                if (split[1].equals("Mind Crush"))
                    TrapEffect.mindCrush(rivalUser, onlineUser);
                if (split[1].equals("Call of the Haunted"))
                    TrapEffect.callOfTheHaunted(rivalUser);
                if (split[1].equals("Time Seal"))
                    TrapEffect.timeSeal(onlineUser);
                if (split[1].equals("Torrential Tribute"))
                    TrapEffect.torrentialTribute(onlineUser, rivalUser);
            }
        }
        return 0;
    }





    public static void changePhase(Phase currentPhase) {
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        int address;
        switch (currentPhase.name()) {
            case "Draw_Phase" -> {
                GameMatView.showInput("phase: " + Phase.Standby_Phase);
                playerGameMat.setPhase(Phase.Standby_Phase);
                checkForMessengerOfPeace();
                address = MonsterZoneCard.getAddressOfScanner(onlineUser);
                if (address != -1) {
                    if (GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards() == 0) {
                        GameMatView.showInput("Oops! You cant change the Scanner because of no dead card in your rival graveyard!");
                        MonsterZoneCard.getMonsterCardByAddress(address, onlineUser).removeMonsterFromZone();
                    }
                    else {
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
                checkForSetTrapToActivateInRivalTurn("Twin Twisters");
                checkForSetTrapToActivateInRivalTurn("Mystical space typhoon");
                trapAddress = checkForSetTrapToActivateInRivalTurn("Mind Crush");
                if (trapAddress != -1) {
                    TrapEffect.mindCrush(rivalUser, onlineUser);
                    SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                }
                trapAddress = checkForSetTrapToActivateInRivalTurn("Time Seal");
                if (trapAddress != -1) {
                    TrapEffect.timeSeal(onlineUser);
                    SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                }
                trapAddress = checkForSetTrapToActivateInRivalTurn("Call of the Haunted");
                if (trapAddress != -1) {
                    TrapEffect.callOfTheHaunted(rivalUser);
                    SpellTrapZoneCard.getSpellCardByAddress(trapAddress, rivalUser).removeSpellTrapFromZone();
                }
                trapAddress = MonsterZoneCard.getAddressByMonsterName(onlineUser, "Herald of Creation");
                if (trapAddress != -1)
                    MonsterEffect.heraldOfCreation(MonsterZoneCard.getMonsterCardByAddress(trapAddress, onlineUser), onlineUser);
            }
            case "Standby_Phase" -> {
                GameMatView.showInput("phase: " + Phase.Main_Phase1);
                playerGameMat.setPhase(Phase.Main_Phase1);
            }
            case "Main_Phase1" -> {
                GameMatView.showInput("phase: " + Phase.Battle_Phase);
                playerGameMat.setPhase(Phase.Battle_Phase);
                if (!player.getCanBattle()) {
                    GameMatView.showInput("Oops! You cant battle this turn!");
                    player.setCanBattle(true);
                    changePhase(playerGameMat.getPhase());
                }
            }
            case "Battle_Phase" -> {
                GameMatView.showInput("phase: " + Phase.Main_Phase2);
                playerGameMat.setPhase(Phase.Main_Phase2);
            }
            case "Main_Phase2" -> {
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
                        if (!response.matches("[1,7]"))
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
                }
                else if (player.getCanDrawCard()) {
                    String cardName = player.drawCard(false);
                    new HandCardZone(onlineUser, cardName);
                    GameMatView.showInput("new card added to the hand : " + cardName);
                } else {
                    GameMatView.showInput("Oops! you can not draw card");
                }
            }
        }
    }



    public static void changeTurn() {
        Player onlinePlayer = Player.getPlayerByName(onlineUser);
        Player rivalPlayer = Player.getPlayerByName(rivalUser);
        if (onlinePlayer.getCounterOfTurn() == 1) onlinePlayer.setCanDrawCard(true);
        MonsterZoneCard.changeOneTurnMonstersIsEffectUsed(onlineUser);
        MonsterZoneCard.removeUselessMonster(onlineUser);
        MonsterZoneCard.changeAllHaveChangePosition(onlineUser);
        onlinePlayer.changeTurn();
        //MonsterEffect.heraldOfCreation()
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
                    commandController(winnerPlayer.getNickname(), loserPlayer.getNickname());
                else
                    commandController(loserPlayer.getNickname(), winnerPlayer.getNickname());

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

}