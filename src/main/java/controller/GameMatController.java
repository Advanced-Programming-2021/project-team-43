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
    public static String message = "";
    public static String sideMsg = "";
    public static String sideMsg2 = "";
    public static String error = "";
    public static boolean isNewTurn;
    private static String cardNameAnswer;
    public static GameMatView gameMatView;
    public static int round;
    private static String summon;
    private static String effectName;

    public static int run(String firstPlayer, String secondPlayer) {
     //  onlineUser = firstPlayer;
       // rivalUser = secondPlayer;

        message = "The game starts!\nits " + onlineUser + "’s turn";
        sideMsg = "phase: " + Phase.Draw_Phase;
        while (true) {
            if (onlineUser.equals("AI")) {
                AI();
            } else {
                return 0;
            }
            String breaker = commandController(command);
            if (breaker.equals("38")) {
                break;
            }
        }
        return 0;
    }

    public static String commandController(String command) {
        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
        if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+-m\\s+(\\d+)\\s+-o$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
            return message + "@" + selectedRivalCard;
        }
        if ((matcher = getMatcher(command, "^select\\s+--monster\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-m\\s+(\\d+)$")).find()) {
            selectMonsterCard(Integer.parseInt(matcher.group(1)), true);
            return message + "@" + selectedOwnCard;
        }
        if ((matcher = getMatcher(command, "^select\\s+--hand\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-h\\s+(\\d+)$")).find()) {
            selectHandCard(Integer.parseInt(matcher.group(1)));
            return message + "@" + selectedOwnCard;
        }

        if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^s\\s+-s\\s+(\\d+)$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), true);
            return message + "@" + selectedOwnCard;
        }
        if ((matcher = getMatcher(command, "^select\\s+--spell\\s+(\\d+)\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+-s\\s+(\\d+)\\s+-o$")).find()) {
            selectSpellCard(Integer.parseInt(matcher.group(1)), false);
            return message + "@" + selectedRivalCard;
        }
        if (getMatcher(command, "^summon$").find() || getMatcher(command, "^sn$").find()) {
            summon(currentPhase);
            return summon + "@" + selectedOwnCard+"@"+effectName;
        }//ok
        if (getMatcher(command, "^select \\s*Field \\s*Card").find() || getMatcher(command, "^s\\s+-f$").find()) {
            selectFieldCard(true);
            return summon + "@" + selectedOwnCard;
        }//ok
        if ((matcher = getMatcher(command, "^select \\s*Field \\s*Card\\s+--opponent$")).find() || (matcher = getMatcher(command, "^s\\s+-f\\s+-o$")).find()) {
            selectFieldCard(false);
            return message + "@" + selectedRivalCard;
        }//ok
        if ((matcher = getMatcher(command, "^flip\\s* summon$")).find() || (matcher = getMatcher(command, "^fs$")).find()) {
            flipSummon(currentPhase);
            return message + "@" + selectedOwnCard;
        }//ok
        if ((matcher = getMatcher(command, "^change\\s* to\\s* attack\\s* position$")).find()) {
            changeToAttackPosition(currentPhase);
            return message + "@" + selectedOwnCard;
        }//ok
        if ((matcher = getMatcher(command, "^change\\s* to\\s* defend\\s* position$")).find()) {
            changeToDefensePosition(currentPhase);
            return message + "@" + selectedOwnCard + "@" + error;
        }//ok
        if ((matcher = getMatcher(command, "^set$")).find()) {
            set(currentPhase);
            return message + "@";
        }//ok
        if (getMatcher(command, "^attack\\s+(\\d+)$").find() ) {
            int value = attack(Integer.parseInt(matcher.group(1)), currentPhase);
            return message + "@" + selectedOwnCard + "@" + value;
        }
        if (getMatcher(command, "^attack\\s+direct$").find() || getMatcher(command, "^a\\s+d$").find()) {
            int value = attackDirect( currentPhase);
            return message + "@" + selectedOwnCard + "@" + value;
        }
        if (getMatcher(command, "^activate \\s*Spell\\s* Effect$").find()) {
            int value = activateSpellEffect( currentPhase);
            return message + "@" + selectedOwnCard + "@" + value;
        }

        if (getMatcher(command, "^next\\s+phase$").find() || getMatcher(command, "^n\\s+p$").find()) {
            changePhase(currentPhase);
            return message + "@" + sideMsg + "@" + sideMsg2;
        }

        //////////////
        if (AIAttack(command, currentPhase) == 1) {
            return "25";
        }
        //////////////
        return "39";
    }


    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void AI() {
        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
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

    public static int AIAttack(String command, Phase currentPhase) {
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
            } else if (rivalMonsterName.equals("Texchanger")) {
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
                    } else {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (rivalMonsterName.equals("Yomi Ship")) {
                        ownMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    }
                    GameMatView.showInput("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                    if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                        Player.getPlayerByName(rivalUser).setLifePoint(0);
                        endGame(rivalUser);
                    }
                } else if (damage == 0) {
                    ownMonster.removeMonsterFromZone();
                    rivalMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatView.showInput("both you and your opponent monster cards are destroyed and no one receives damage");
                } else {
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
                        endGame(onlineUser);
                    }
                }
            } else {
                if (ownMonster.getAttack() > rivalMonster.getDefend()) {
                    if (!rivalMonsterName.equals("Marshmallon")) {
                        rivalMonster.removeMonsterFromZone();
                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    } else {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (rivalMonster.getMode().equals("DH")) {
                        if (ownMonster.getMonsterName().equals("The Calculator"))
                            MonsterEffect.theCalculator(onlineUser, ownMonster);
                        rivalMonster.setMode("DO");
                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
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
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed but you received " + damage + " battle damage");
                    } else
                        GameMatView.showInput("no card is destroyed but you received " + damage + " battle damage");
                    if (Player.getPlayerByName(onlineUser).getLifePoint() < 0) {
                        Player.getPlayerByName(onlineUser).setLifePoint(0);
                        endGame(onlineUser);
                    }
                }
            }
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

//    public static void increaseLP(int lifePoint, String onlineUser) {
//        Player player = Player.getPlayerByName(onlineUser);
//        player.changeLifePoint(lifePoint);
//    }

    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (isOwnMonsterCard) {
            MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, onlineUser);
            if (ownMonsterCard == null)
                message = "no card found in the given position";
            else {
                selectedOwnCard = "Monster/" + ownMonsterCard.getMonsterName() + "/" + address;
                message = "card selected";
            }
        } else {
            MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
            if (rivalMonsterCard == null)
                message = "no card found in the given position";
            else {
                selectedRivalCard = "Monster/" + rivalMonsterCard.getMonsterName() + "/" + address;
                message = "card selected";
            }
        }
    }

    public static int selectSpellCard(int address, boolean isOwnSpellCard) {
        SpellTrapZoneCard spellTrapCard;
        if (isOwnSpellCard) {
            spellTrapCard = SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser);
            if (spellTrapCard == null) {
                message = "no card found in the given position";
                return 0;
            } else {
                selectedOwnCard = spellTrapCard.getKind() + "/" + spellTrapCard.getSpellTrapName() + "/" + address;
                message = "card selected";
            }
        } else {
            spellTrapCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
            if (spellTrapCard == null) {
                message = "no card found in the given position";
                return 0;
            } else {
                selectedRivalCard = spellTrapCard.getKind() + "/" + spellTrapCard.getSpellTrapName() + "/" + address;
                message = "card selected";
            }
        }
        return 1;
    }

    public static void selectFieldCard(boolean isOwnField) {
        GameMatModel gameMat;
        if (isOwnField) {
            gameMat = GameMatModel.getGameMatByNickname(onlineUser);
            if (gameMat.getFieldZone().equals(""))
                message = "invalid selection";
            else {
                selectedOwnCard = "Field/" + gameMat.getFieldZone();
                message = "card selected";
            }
        } else {
            gameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (gameMat.getFieldZone().equals(""))
                message = "invalid selection";
            else {
                selectedRivalCard = "Field/" + gameMat.getFieldZone();
                message = "card selected";
            }
        }
    }

    public static void selectHandCard(int address) {
        selectedOwnCard = "Hand/" + HandCardZone.getHandCardByAddress(address, onlineUser).getCardName() + "/" + address;
        message = "card selected";
    }

    public static boolean errorOfNoCardSelected(String whoseCard) {
        if (whoseCard.equals("own")) {
            if (selectedOwnCard.equals("")) {
                message = "no card is selected yet";
                return false;
            } else
                return true;
        } else {
            return !selectedRivalCard.equals("");
        }
    }

    public static void summon(Phase currentPhase) {
        if (!errorOfNoCardSelected("own")) {
            summon = "no card is selected yet";
            return;
        }
        String[] split = selectedOwnCard.split("/");
        Player player = Player.getPlayerByName(onlineUser);
        if (split[0].equals("Hand")) {
            summonInHand(player, currentPhase);
            summon = "summoned in hand";
        } else if (split[0].equals("Monster")) {
            summonInMonsterZone(player, currentPhase);
            summon = "summoned in zone";
        } else
            message = "you can’t summon this card";
        summon = "you can’t summon this card";
        selectedOwnCard = "";
    }

    public static int summonInHand(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!handCard.getKind().equals("Monster")) {
            message = "you can’t summon this card";
            return 1;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return 2;
        if (!errorOfFullZone("Monster"))
            return 3;
        if (!player.getCanSetSummonMonster())
            message = "you already summoned/set on this turn";
        else {
            if (split[1].equals("Scanner"))
                summonInHandSuccessfully(player, handCard);
            else if (specialSummon(handCard, split[1]) != 2) {
                checkForSetTrapToActivateInRivalTurn("Solemn Warning", MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser));
                selectedOwnCard = "";
            } else {
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
                        message = "there are not enough cards for tribute";
                    else if (tributeMonster(1, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                } else {
                    if (split[1].equals("Crab Turtle") || split[1].equals("Skull Guardian")) {
                        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Advanced Ritual Art");
                        if (address == -1) {
                            message = "You should activate Ritual Spell then summon this monster";
                            return 4;
                        }
                    }
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                        message = "there are not enough cards for tribute";
                    else if (tributeMonster(2, split[1]) == 1)
                        summonInHandSuccessfully(player, handCard);
                }
            }
        }
        return 5;
    }

    public static void summonInHandSuccessfully(Player player, HandCardZone handCard) {//ok
        if (addToMonsterZoneCard(handCard.getCardName(), "OO") == 0)
            return;
        player.setCanSetSummonMonster(false);
        handCard.removeFromHandCard();
        message = "summoned successfully";
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
      //  GameMatView.effectCardName = ownMonster.getMonsterName();
        effectName=ownMonster.getMonsterName();//
        if (ownMonster.getAttack() >= 1000) {
            checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
            trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
        }
        checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
        checkForSetTrapToActivateInRivalTurn("Solemn Warning", ownMonster);
    }

    public static int summonInMonsterZone(Player player, Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getMode().equals("OO") || ownMonster.getHaveChangedPositionThisTurn()) {
            message = "you can’t summon this card";
            return 1;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return 2;
        if (!player.getCanSetSummonMonster()) {
            message = "you already summoned/set on this turn";
        } else {
            int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
            if (monsterLevel <= 4) {
                summonInMonsterZoneSuccessfully(player, ownMonster);
            } else if (monsterLevel == 5 || monsterLevel == 6) {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                    message = "there are not enough cards for tribute";
                else {
                    if (tributeMonster(1, split[1]) == 1)
                        summonInMonsterZoneSuccessfully(player, ownMonster);
                }
            } else {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                    message = "there are not enough cards for tribute";
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
        message = "summoned successfully";
        if (ownMonster.getAttack() >= 1000) {
            checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
            trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
        }
        MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
        checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
        checkForSetTrapToActivateInRivalTurn("Solemn Warning", ownMonster);
    }

    public static int flipSummon(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            message = "you can’t change this card position";
            selectedOwnCard = "";
            return 2;
        }
        if (!errorOfWrongPhase("flip", currentPhase))
            return 3;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!ownMonster.getMode().equals("DH") || ownMonster.getHaveChangedPositionThisTurn()) {
            message = "you can’t flip summon this card";
        } else {
            ownMonster.setMode("OO");
            ownMonster.setHaveChangedPositionThisTurn(true);
            message = "flip summoned successfully";
            if (ownMonster.getAttack() >= 1000) {
                checkForSetTrapToActivateInRivalTurn("Trap Hole", ownMonster);
                trapAddress = SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Trap Hole");
            }
            MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
            checkForSetTrapToActivateInRivalTurn("Torrential Tribute", ownMonster);
            checkForSetTrapToActivateInRivalTurn("Solemn Warning", ownMonster);
        }
        selectedOwnCard = "";
        return 4;
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

    public static int changeToAttackPosition(Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        if (!errorOfNoCardSelected("own"))
            return 1;
        if (!split[0].equals("Monster")) {
            message = "you can’t change this card position";
            return 1;
        }
        if (!errorOfWrongPhase("change", currentPhase))
            return 1;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getMode().equals("OO"))
            message = "this card is already in the wanted position";
        else if (ownMonster.getHaveChangedPositionThisTurn())
            message = "you already changed this card position in this turn";
        else {
            ownMonster.setHaveChangedPositionThisTurn(true);
            ownMonster.setMode("OO");
            selectedOwnCard = "";
            message = "monster card position changed successfully";
            MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
        }
        return 1;
    }

    public static int changeToDefensePosition(Phase currentPhase) {
        String[] split = selectedOwnCard.split("/");
        if (!errorOfNoCardSelected("own"))
            return 1;
        if (!split[0].equals("Monster")) {
            message = "you can’t change this card position";
            return 1;
        }
        if (!errorOfWrongPhase("change", currentPhase))
            return 1;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getMode().equals("DO"))
            message = "this card is already in the wanted position";
        else if (ownMonster.getHaveChangedPositionThisTurn())
            error = "you already changed this card position in this turn";
        else {
            ownMonster.setHaveChangedPositionThisTurn(true);
            ownMonster.setMode("DO");
            selectedOwnCard = "";
            message = "monster card position changed successfully";
            MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser);
        }
        return 1;
    }

    public static int ritualSummon() {//moshkel
//        int ritualMonsterAddress = HandCardZone.doIHaveAnyRitualMonster(onlineUser);
//        if (ritualMonsterAddress == -1) {
//            message = "there is no way you could ritual summon a monster";
//            return -1;
//        }
//        if (MonsterZoneCard.getSumOfMonstersLevel(onlineUser) < 7) {
//            message = "there is no way you could ritual summon a monster";
//            return -1;
//        }
//        int address;
//   //     while (true) {
//            ////// message = "Please enter the address of a Ritual Monster in your hand to summon: ";
//            String response = GameMatView.getCommand();
//            if (response.equals("cancel"))
//                return -1;
//            if (!response.matches("\\d+")) {
//                message = "you should ritual summon right now";
//              //  continue;
//            }
//            address = Integer.parseInt(response);
//            address--;
//            if (HandCardZone.getHandCardByAddress(address, onlineUser) == null) {
//                message = "you should ritual summon right now";
//               // continue;
//            }
//            HandCardZone card = HandCardZone.getHandCardByAddress(address, onlineUser);
//          //  if (card.getKind().equals("Monster") && MonsterCard.getMonsterByName(card.getCardName()).getCardType().equals("Ritual"))
//               // break;
//      //  }
//        HandCardZone handCardRitualMonster = HandCardZone.getHandCardByAddress(address, onlineUser);
//        String ritualMonsterName = handCardRitualMonster.getCardName();
//        int numberOfTribute;
//        while (true) {
//            GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
//            response = GameMatView.getCommand();
//            if (response.equals("cancel"))
//                return -1;
//            if (!response.matches("[1-3]"))
//                continue;
//            numberOfTribute = Integer.parseInt(response);
//            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < numberOfTribute)
//                message = "You dont have this much monster in your zone!";
//            else
//                break;
//        }
//        if (tributeMonster(numberOfTribute, "Ritual") == 1) {
//            GameMatView.showInput("Please enter Ritual Monster mode: (defensive/attacking)");
//            command = GameMatView.getCommand();
//            while (!command.matches("defensive") && !command.matches("attacking")) {
//                GameMatView.showInput("Please enter the answer correctly: (defensive/attacking)");
//                command = GameMatView.getCommand();
//            }
//            if (command.equals("defensive"))
//                new MonsterZoneCard(onlineUser, ritualMonsterName, "DO", false, false);
//            else
//                new MonsterZoneCard(onlineUser, ritualMonsterName, "OO", false, false);
//            message = "summoned successfully";
//            handCardRitualMonster.removeFromHandCard();
//        }
        return 0;
    }

    public static int tributeMonster(int numberOfTribute, String monsterType) {
        ArrayList<Integer> victimAddress = new ArrayList<>(Objects.requireNonNull(getAddressOfTributeMonster(numberOfTribute)));
        if (victimAddress.contains(-1))
            return 0;
        if (monsterType.equals("Ritual")) {
            int monstersLevel = 0;
            for (int i = 0; i < numberOfTribute; i++)
                monstersLevel += MonsterZoneCard.getMonsterCardByAddress(victimAddress.get(i), onlineUser).getLevel();
            if (monstersLevel < 7) {
                message = "selected monsters levels don’t match with ritual monster";
                String response;
                while (true) {
                    GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
                    response = GameMatView.getCommand();
                    if (response.equals("cancel")) {
                        selectedOwnCard = "";
                        return 0;
                    }
                    if (response.matches("\\d+"))
                        if (Integer.parseInt(response) == 1 || Integer.parseInt(response) == 2 || Integer.parseInt(response) == 3)
                            break;
                }
                numberOfTribute = Integer.parseInt(response);
                GameMatView.showInput("Please enter some Monster address to tribute:");
                tributeMonster(numberOfTribute, "Ritual");
            }
        }
        for (int i = 0; i < numberOfTribute; i++)
            MonsterZoneCard.getMonsterCardByAddress(victimAddress.get(i), onlineUser).removeMonsterFromZone();
        victimAddress.clear();
        return 1;
    }

    public static ArrayList<Integer> getAddressOfTributeMonster(int numberOfTribute) {
        ArrayList<Integer> tributeMonsterAddress = new ArrayList<>();
        int address = 0;
        String response;
        for (int i = 1; i <= numberOfTribute; i++) {
            GameMatView.showInput("Please enter the address of monster " + i + " to tribute:");
            while (true) {
                response = GameMatView.getCommand();
                if (response.equals("cancel")) {
                    selectedOwnCard = "";
                    tributeMonsterAddress.add(-1);
                    break;
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
        return tributeMonsterAddress;
    }

    public static int set(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Hand")) {
            message = "you can’t set this card";
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
                    message = "you already summoned/set on this turn";
                else if (cardName.equals("Beast King Barbaros"))
                    message = "Oops! Beast King Barbaros cant be set!";
                else {
                    int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
                    if (monsterLevel <= 4) {
                        if (addToMonsterZoneCard(cardName, "DH") != 0) {
                            handCard.removeFromHandCard();
                            player.setCanSetSummonMonster(false);
                            message = "set successfully";
                            return 5;
                        }
                        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
                        if (ownMonster != null) {
                            if (split[1].equals("Herald of Creation"))
                                MonsterEffect.heraldOfCreation(ownMonster, onlineUser);
                        }
                    } else if (monsterLevel == 5 || monsterLevel == 6) {
                        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                            message = "there are not enough cards for tribute";
                        else if (tributeMonster(1, split[1]) == 1) {
                            if (addToMonsterZoneCard(cardName, "DH") != 0) {
                                handCard.removeFromHandCard();
                                player.setCanSetSummonMonster(false);
                                message = "set successfully";
                                return 6;
                            }
                        }
                    } else {
                        if (split[1].equals("Crab Turtle") || split[1].equals("Skull Guardian")) {
                            int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Advanced Ritual Art");
                            if (address == -1) {
                                message = "You should activate Ritual Spell then summon this monster";
                                return 7;
                            }
                        }
                        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                            message = "there are not enough cards for tribute";
                        else if (tributeMonster(2, split[1]) == 1) {
                            if (addToMonsterZoneCard(cardName, "DH") != 0) {
                                handCard.removeFromHandCard();
                                player.setCanSetSummonMonster(false);
                                message = "set successfully";
                                return 8;
                            }
                        }
                    }
                }
                break;
            case "Spell":
                if (!errorOfFullZone("Spell"))
                    return 9;
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
                message = "set successfully";
                break;
            case "Trap":
                if (!errorOfFullZone("Spell"))
                    return 10;
                handCard.removeFromHandCard();
                addToSpellTrapZoneCard(handCard.getCardName(), "H");
                SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                message = "set successfully";
                break;
        }
        selectedOwnCard = "";
        return 0;
    }

    public static int addToMonsterZoneCard(String monsterName, String mode) {
        String whichCard = "";
        if (monsterName.equals("Scanner")) {
            if (GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards() == 0) {
                message = "Oops! You cant summon Scanner because of no dead card in your rival graveyard!";
                return 0;
            } else {
                do {
                    GameMatView.showInput("Which rival dead monster for Scanner? (enter the monster name)");
                    whichCard = GameMatView.getCommand();
                    if (whichCard.equals("cancel"))
                        return 0;
                } while (!GameMatModel.getGameMatByNickname(rivalUser).doesThisMonsterExistInGraveyard(whichCard));
                new MonsterZoneCard(onlineUser, whichCard, mode, true, false);
            }
        } else
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
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    message = "action not allowed in this phase";
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;
            case "activate":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    message = "you can’t activate an effect on this turn";
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;
            case "attack":
            case "attackDirect":
                if (!currentPhase.name().equals("Battle_Phase")) {
                    message = "you can’t do this action in this phase";
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;
            case "change":
            case "flip":
            case "set":
                if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                    message = "you can’t do this action in this phase";
                    selectedOwnCard = "";
                    return false;
                } else
                    return true;
        }
        return false;
    }

    public static boolean errorOfFullZone(String whichZone) {
        if (whichZone.equals("Monster")) {
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                message = "monster card zone is full";
                selectedOwnCard = "";
                return false;
            }
            return true;
        } else if (whichZone.equals("Spell")) {
            if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                message = "spell card zone is full";
                selectedOwnCard = "";
                return false;
            }
            return true;
        } else {
            if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                message = "trap card zone is full";
                selectedOwnCard = "";
                return false;
            }
            return true;
        }
    }

    public static int attack(int rivalMonsterAddress, Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 0;
        String[] split = selectedOwnCard.split("/");
        System.out.println(selectedOwnCard);
        if (!split[0].equals("Monster")) {
            message = "you can’t attack with this card";
            selectedOwnCard = "";
            return 0;
        } else if (!errorOfWrongPhase("attack", currentPhase))
            return 0;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (!ownMonster.getMode().equals("OO")) {
            message = "your card is not in an attack mode";
            selectedOwnCard = "";
            return 0;
        }
        if (ownMonster.getHaveAttackThisTurn()) {
            message = "this card already attacked";
            selectedOwnCard = "";
            return 0;
        }
        if (!ownMonster.getCanAttack()) {
            message = "this Monster cant attack because of a spell effect!";
            selectedOwnCard = "";
            return 0;
        }
        MonsterZoneCard rivalMonster = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress, rivalUser);
        if (rivalMonster == null) {
            message = "there is no card to attack here";
            selectedOwnCard = "";
            return 0;
        }
        if (!rivalMonster.getCanAttackToThisMonster()) {
            message = "you cant attack to this monster!";
            selectedOwnCard = "";
            return 0;
        }
        message = "I want to attack to your Monster!";
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
        String rivalMonsterName = rivalMonster.getSecondName();
        if (rivalMonsterName.equals("Suijin")) {
            if (MonsterEffect.suijin(rivalMonster) == 1) {
                selectedOwnCard = "";
                return 1;
            }
        } else if (rivalMonsterName.equals("Texchanger")) {
            if (MonsterEffect.texchanger(rivalMonster, rivalUser) != 0) {
                selectedOwnCard = "";
                return 1;
            }
        }
        if (rivalMonsterName.equals("Exploder Dragon")) {
            message = "Exploder Dragon Effect is activated!";
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
                } else {
                    MonsterEffect.marshmallon(rivalMonster, onlineUser);
                }
                if (rivalMonsterName.equals("Yomi Ship")) {
                    ownMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                }
                message = "your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage";
                if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                    Player.getPlayerByName(rivalUser).setLifePoint(0);
                    endGame(rivalUser);
                }
            } else if (damage == 0) {
                ownMonster.removeMonsterFromZone();
                rivalMonster.removeMonsterFromZone();
                GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                message = "both you and your opponent monster cards are destroyed and no one receives damage";
            } else {
                Player.getPlayerByName(onlineUser).changeLifePoint(damage);
                if (ownMonster.getSecondName().equals("Exploder Dragon")) {
                    message = "Exploder Dragon Effect is activated!";
                    rivalMonster.removeMonsterFromZone();
                    ownMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                    return 1;
                }
                ownMonster.removeMonsterFromZone();
                GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                message = "your monster card is destroyed and you received " + -1 * damage + " battle damage";
                if (Player.getPlayerByName(onlineUser).getLifePoint() <= 0) {
                    Player.getPlayerByName(onlineUser).setLifePoint(0);
                    endGame(onlineUser);
                }
            }
        } else {
            if (ownMonster.getAttack() > rivalMonster.getDefend()) {
                if (!rivalMonsterName.equals("Marshmallon")) {
                    rivalMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
                } else {
                    MonsterEffect.marshmallon(rivalMonster, onlineUser);
                }
                if (rivalMonster.getMode().equals("DH")) {
                    if (ownMonster.getSecondName().equals("The Calculator"))
                        MonsterEffect.theCalculator(onlineUser, ownMonster);
                    rivalMonster.setMode("DO");
                    MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                }
                if (rivalMonsterName.equals("Yomi Ship")) {
                    ownMonster.removeMonsterFromZone();
                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
                }
                if (rivalMonsterMode.equals("DH"))
                    message = "opponent’s monster card was " + rivalMonsterName + " and the defense position monster is destroyed";
                else {
                    if (rivalMonsterName.equals("Marshmallon"))
                        message = "the defense position monster is destroyed";
                }
            } else if (ownMonster.getAttack() == rivalMonster.getDefend()) {
                if (rivalMonsterMode.equals("DH")) {
                    if (rivalMonsterName.equals("Marshmallon")) {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (ownMonster.getSecondName().equals("The Calculator"))
                        MonsterEffect.theCalculator(onlineUser, ownMonster);
                    rivalMonster.setMode("DO");
                    MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                    message = "opponent’s monster card was " + rivalMonsterName + " and no card is destroyed";
                } else
                    message = "no card is destroyed";
            } else {
                damage = rivalMonster.getDefend() - ownMonster.getAttack();
                Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                if (rivalMonsterMode.equals("DH")) {
                    if (rivalMonsterName.equals("Marshmallon")) {
                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
                    }
                    if (ownMonster.getSecondName().equals("The Calculator"))
                        MonsterEffect.theCalculator(onlineUser, ownMonster);
                    rivalMonster.setMode("DO");
                    MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
                    message = "opponent’s monster card was " + rivalMonsterName + " and no card is destroyed but you received " + damage + " battle damage";
                } else
                    message = "no card is destroyed but you received " + damage + " battle damage";
                if (Player.getPlayerByName(onlineUser).getLifePoint() < 0) {
                    Player.getPlayerByName(onlineUser).setLifePoint(0);
                    endGame(onlineUser);
                }
            }
        }
        selectedOwnCard = "";
        return 1;
    }

    public static int attackDirect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (!split[0].equals("Monster")) {
            message = "you can’t attack with this card";
            selectedOwnCard = "";
            return 2;
        }
        if (!errorOfWrongPhase("attackDirect", currentPhase))
            return 3;
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        if (ownMonster.getHaveAttackThisTurn())
            message = "this card already attacked";
        else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) != 0)
            message = "you can’t attack the opponent directly";
        else if (!ownMonster.getCanAttack())
            message = "this Monster cant attack because of a spell effect!";
        else {
            message = "I want to attack you directly!";
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
            message = "your opponent receives " + damage + " battle damage";
            if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
                Player.getPlayerByName(rivalUser).setLifePoint(0);
                endGame(rivalUser);
            }
        }
        selectedOwnCard = "";
        return 0;
    }


    public static int activateSpellEffect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return 1;
        String[] split = selectedOwnCard.split("/");
        if (split[0].equals("Hand") && Card.getCardsByName(split[1]).getCardModel().equals("Trap")) {
            message = "You should set this trap first then activate it next turn!";
            selectedOwnCard = "";
            return 2;
        }
        if (split[0].equals("Monster") || (split[0].equals("Hand") && Card.getCardsByName(split[1]).getCardModel().equals("Monster"))) {
            message = "activate effect is only for spell cards";
            selectedOwnCard = "";
            return 3;
        }
        if (!errorOfWrongPhase("activate", currentPhase))
            return 4;
        SpellTrapZoneCard ownSpell = null;
        if (split[2].matches("\\d+"))
            ownSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        String spellIcon = "";
        if (SpellCard.getSpellCardByName(split[1]) != null)
            spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
        HandCardZone handCard = null;
        if (split[2].matches("\\d+"))
            handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        int res = 0;
        switch (split[0]) {
            case "Trap":
                if (ownSpell.getIsSetInThisTurn() || !Player.getPlayerByName(onlineUser).getCanUseTrap()) {
                    message = "You cant activate this trap in this turn!";
                } else {
                    activateTrapEffect(true, null);
                    return 5;
                }
                break;
            case "Spell":
                if (ownSpell.getMode().equals("O")) {
                    message = "you have already activated this card";
                    selectedOwnCard = "";
                    return 6;
                }
                if (spellIcon.equals("Ritual")) {
                    int result = ritualSummon();
                    if (result != -1)
                        ownSpell.removeSpellTrapFromZone();
                    return 7;
                } else {
                    if (spellIcon.equals("Quick-play") && ownSpell.getIsSetInThisTurn()) {
                        message = "Oops! You cant activate the effect in the turn you set the card!";
                        selectedOwnCard = "";
                        return 8;
                    }
                    ownSpell.setMode("O");
                    res = checkForSetTrapToActivateInRivalTurn("Magic Jammer", null);
                    if (res == 1)
                        ownSpell.removeSpellTrapFromZone();
                    else
                        chooseSpellEffectController(spellIcon, ownSpell);
                }
                checkForSpellAbsorption();
                if (split[1].equals("Messenger of peace"))
                    SpellEffect.messengerOfPeace(onlineUser, rivalUser, SpellTrapZoneCard.getNumberOfFullHouse(onlineUser));
                break;
            case "Field":
                ownGameMat.changeModeOfFieldCard("O");
                SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                message = "spell activated";
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
                        message = "You should set this trap first then activate it next turn!";
                        selectedOwnCard = "";
                        return 1;
                    } else {
                        handCard.removeFromHandCard();
                        addToSpellTrapZoneCard(split[1], "O");
                        ownSpell = SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser);
                        res = checkForSetTrapToActivateInRivalTurn("Magic Jammer", null);
                        if (res == 1)
                            ownSpell.removeSpellTrapFromZone();
                        else {
                            if (chooseSpellEffectController(spellIcon, ownSpell) == 0) {
                                if (ownSpell.getIcon().equals("Continuous"))
                                    ownSpell.removeSpellTrapFromZone();
                                message = "Your spell is not activated and is gone to graveyard!";
                                selectedOwnCard = "";
                                return 1;
                            }
                        }
                    }
                }
                if (split[1].equals("Messenger of peace"))
                    SpellEffect.messengerOfPeace(onlineUser, rivalUser, SpellTrapZoneCard.getNumberOfFullHouse(onlineUser));
                checkForSpellAbsorption();
                if (res != 1)
                    message = "spell activated";
                break;
        }
        selectedOwnCard = "";
        return 0;
    }

    public static int chooseSpellEffectController(String spellIcon, SpellTrapZoneCard ownSpell) {
        switch (spellIcon) {
            case "Normal":
                message = "I want to activate a Spell!";
                int result = SpellEffect.normalEffectController(ownSpell, onlineUser, rivalUser);
                if (!ownSpell.getSecondName().equals("Swords of Revealing Light"))
                    ownSpell.removeSpellTrapFromZone();
                else if (ownSpell.getSecondName().equals("Swords of Revealing Light") && result == 0)
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

    public static int checkForSetTrapToActivateInRivalTurn(String trapName, MonsterZoneCard rivalMonster) {
//        int trapAddress = SpellTrapZoneCard.getAddressOfSetTrap(rivalUser, trapName);
//        if (trapAddress != -1) {
//            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
//            do {
//                GameMatView.showInput(rivalUser + " do you want to activate " + trapName + " ? (yes/no)");
//                response = GameMatView.getCommand();
//            } while (!response.matches("yes|no"));
//            if (response.equals("no")) {
//                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
//            } else {
//                if (!Player.getPlayerByName(rivalUser).getCanUseTrap())
//                    GameMatView.showInput("Oops! You cant activate trap!");
//                else {
//                    GameMatView.showInput("Please select and activate your trap:");
//                    while (true) {
//                        response = GameMatView.getCommand();
//                        if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(response, "^s\\s+-s\\s+(\\d+)$")).find()) {
//                            if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1) {
//                                if (trapAddress == Integer.parseInt(matcher.group(1)))
//                                    break;
//                                else
//                                    GameMatView.showInput("Please select the trap " + trapName + " correctly");
//                            }
//                        } else
//                            GameMatView.showInput("it’s not your turn to play this kind of moves");
//                    }
//                    while (true) {
//                        response = GameMatView.getCommand();
//                        if (getMatcher(response, "^activate\\s+effect$").find() || getMatcher(response, "^a\\s+e$").find()) {
//                            return activateTrapEffect(false, rivalMonster);
//                        } else
//                            GameMatView.showInput("it’s not your turn to play this kind of moves");
//                    }
//                }
//            }
//        }
        return 0;
    }

    public static void checkForQuickSpellInRivalTurn(String spellName) {
//        int quickSpellAddress = SpellTrapZoneCard.getAddressOfQuickSpellByName(rivalUser, spellName);
//        SpellTrapZoneCard quickSpell;
//        if (quickSpellAddress != -1) {
//            GameMatView.showInput("now it will be " + rivalUser + "’s turn");
//            do {
//                GameMatView.showInput(rivalUser + " do you want to activate " + spellName + " ? (yes/no)");
//                response = GameMatView.getCommand();
//            } while (!response.matches("yes|no"));
//            if (response.equals("no")) {
//                GameMatView.showInput("now it will be " + onlineUser + "’s turn");
//            }
//            else {
//                GameMatView.showInput("Please select and activate your Quick-Play Spell:");
//                while (true) {
//                    response = GameMatView.getCommand();
//                    if ((matcher = getMatcher(response, "^select\\s+--spell\\s+(\\d+)$")).find() || (matcher = getMatcher(response, "^s\\s+-s\\s+(\\d+)$")).find()) {
//                        if (selectSpellCard(Integer.parseInt(matcher.group(1)), false) == 1) {
//                            quickSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(matcher.group(1)), rivalUser);
//                            if (quickSpell != null && quickSpell.getIsSetInThisTurn()) {
//                                GameMatView.showInput("Oops! You cant activate the effect in the turn you set the card!");
//                                return;
//                            }
//                            if (quickSpellAddress == Integer.parseInt(matcher.group(1)))
//                                break;
//                            else
//                                GameMatView.showInput("Please select the spell " + spellName + " correctly");
//                        }
//                    }
//                    else
//                        GameMatView.showInput("it’s not your turn to play this kind of moves");
//                }
//                while (true) {
//                    response = GameMatView.getCommand();
//                    if (getMatcher(response, "^activate\\s+effect$").find() || getMatcher(response, "^a\\s+e$").find()) {
//                        SpellTrapZoneCard spell = SpellTrapZoneCard.getSpellCardByAddress(quickSpellAddress, rivalUser);
//                        spell.setMode("O");
//                        if (SpellEffect.quickPlayEffectController(spell, rivalUser, onlineUser) == 0)
//                            GameMatView.showInput("Your spell is not activated and is gone to graveyard!");
//                        else
//                            GameMatView.showInput("spell/trap activated");
//                        spell.removeSpellTrapFromZone();
//                        break;
//                    }
//                    else
//                        GameMatView.showInput("it’s not your turn to play this kind of moves");
//                }
//            }
//        }
    }

    public static int activateTrapEffect(Boolean isInYourTurn, MonsterZoneCard rivalMonster) {
        String[] split;
        int result = 0;
        SpellTrapZoneCard trapCard;
        if (!isInYourTurn) {
            split = selectedRivalCard.split("/");
            trapCard = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), rivalUser);
        } else {
            split = selectedOwnCard.split("/");
            trapCard = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        }
        trapCard.setMode("O");
        GameMatView.showInput("trap activated");
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
                    result = TrapEffect.solemnWarning(onlineUser, rivalUser, null, trapCard);
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
        } else {
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
                    result = TrapEffect.solemnWarning(rivalUser, onlineUser, rivalMonster, trapCard);
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

    public static int getAddressOfRelatedMonster(SpellTrapZoneCard ownSpell) {//moshkel
//        String spellName = ownSpell.getSpellTrapName();
//        do {
//            GameMatView.showInput("Whose Monster do you want to equip? (own/rival)");
//            response = GameMatView.getCommand();
//            if (response.equals("cancel"))
//                return 0;
//        } while (!response.matches("own|rival"));
//        int address;
//        if (response.equals("own")) {
//            address = getResponseForEquipSpell("own", spellName);
//            if (address != 0) {
//                ownSpell.setRelatedMonsterAddress("own", address);
//            } else
//                return 0;
//        } else {
//            address = getResponseForEquipSpell("rival", spellName);
//            if (address != 0)
//                ownSpell.setRelatedMonsterAddress("rival", address);
//            else
//                return 0;
//        }
        return 1;
    }

    public static int getResponseForEquipSpell(String whoseResponse, String spellName) {//moshekl
//        MonsterZoneCard monsterCard;
//        while (true) {
//            GameMatView.showInput("Please enter the address of one of your " + whoseResponse + " Monster to equip:");
//            response = GameMatView.getCommand();
//            if (response.equals("cancel"))
//                return 0;
//            if (!response.matches("[1-5]"))
//                continue;
//            if (whoseResponse.equals("own"))
//                monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser);
//            else
//                monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser);
//            if (monsterCard == null || monsterCard.getMode().equals("DH"))
//                continue;
//            String monsterType = monsterCard.getMonsterType();
//            if (spellName.equals("Sword of dark destruction")) {
//                if (monsterType.equals("Fiend") || monsterType.equals("Spellcaster")) {
//                    break;
//                }
//            }
//            if (spellName.equals("Magnum Shield") && monsterType.equals("Warrior"))
//                break;
//            if (spellName.equals("Black Pendant") || spellName.equals("United We Stand"))
//                break;
//        }
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

    public static int checkForMessengerOfPeace() {//moshekl
//        int address = SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Messenger of peace");
//        if (address != -1) {
//            SpellEffect.messengerOfPeace(onlineUser, rivalUser, address);
//            do {
//                GameMatView.showInput("Do you want to destroy Messenger of peace? (yes/no)");
//                response = GameMatView.getCommand();
//            } while (!response.matches("yes|no"));
//            if (response.equals("yes")) {
//                SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).removeSpellTrapFromZone();
//            } else {
//                Player.getPlayerByName(onlineUser).changeLifePoint(-100);
//            }
//        }
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

    public static void changePhase(Phase currentPhase) {//moshkel
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        int address;
        switch (currentPhase.name()) {
            case "Draw_Phase":
                sideMsg = "phase: " + Phase.Standby_Phase;
                playerGameMat.setPhase(Phase.Standby_Phase);
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
                        message = "Oops! You cant change the Scanner because of no dead card in your rival graveyard!";
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
                trapAddress = MonsterZoneCard.getAddressByMonsterName(onlineUser, "Herald of Creation");
                if (trapAddress != -1)
                    MonsterEffect.heraldOfCreation(MonsterZoneCard.getMonsterCardByAddress(trapAddress, onlineUser), onlineUser);
                break;
            case "Standby_Phase":
                sideMsg = "phase: " + Phase.Main_Phase1;
                playerGameMat.setPhase(Phase.Main_Phase1);
                break;
            case "Main_Phase1":
                playerGameMat.setPhase(Phase.Battle_Phase);
                if (!player.getCanBattle()) {
                    sideMsg = "phase: " + Phase.Battle_Phase + "Oops! You cant battle this turn!";
                    player.setCanBattle(true);
                    changePhase(playerGameMat.getPhase());
                } else {
                    sideMsg = "phase: " + Phase.Battle_Phase;
                }
                break;
            case "Battle_Phase":
                sideMsg = "phase: " + Phase.Main_Phase2;
                playerGameMat.setPhase(Phase.Main_Phase2);
                break;
            case "Main_Phase2":
                checkForSupplySquad();
                MonsterZoneCard.removeUselessMonster(onlineUser);
                sideMsg = "phase: " + Phase.End_Phase + "\nI end my turn!\n";
                sideMsg2 = "its " + rivalUser + "’s turn" + "phase: " + Phase.Draw_Phase;
                playerGameMat.setPhase(Phase.Draw_Phase);
                changeTurn();
                int res = SpellTrapZoneCard.isThisTrapActivated(onlineUser, "Time Seal");
                if (res != -1) {
                    Player.getPlayerByName(rivalUser).setCanDrawCard(false);
                    SpellTrapZoneCard.getSpellCardByAddress(res, onlineUser).removeSpellTrapFromZone();
                }
                player = Player.getPlayerByName(onlineUser);
                if (player.getNumberOfMainDeckCards() == 0)
                    endGame(onlineUser);
                else if (HandCardZone.getNumberOfFullHouse(onlineUser) == 7) {
                    Random random = new Random();
                    HandCardZone.getHandCardByAddress(random.nextInt(7), onlineUser).removeFromHandCard();
                } else if (player.getCanDrawCard()) {
                    String cardName = player.drawCard(false);
                    new HandCardZone(onlineUser, cardName);
                } else {
                    message = "Oops! you can not draw card";
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
        String temp = Run.userByToken(Run.onlineToken);
        onlineUser = Run.userByToken(Run.rivalToken);
        rivalUser = temp;

        Player.getPlayerByName(onlineUser).setIsYourTurn(true);
        Player.getPlayerByName(rivalUser).setIsYourTurn(false);
    }

    public static void endGame(String loserNickname) {
        String winnerNickname, winnerUsername, loserUsername;
        if (loserNickname.equals(onlineUser)) {
            winnerNickname = rivalUser;
            winnerUsername = UserModel.getUserByNickname(rivalUser).getUsername();
            loserUsername = UserModel.getUserByNickname(onlineUser).getUsername();
        } else {
            winnerNickname = onlineUser;
            winnerUsername = UserModel.getUserByNickname(onlineUser).getUsername();
            loserUsername = UserModel.getUserByNickname(rivalUser).getUsername();
        }
        Player winnerPlayer = Player.getPlayerByName(winnerNickname);
        Player loserPlayer = Player.getPlayerByName(loserNickname);
        UserModel.getUserByUsername(winnerUsername).changeUserScore(1000);
        winnerPlayer.changeNumberOfWin();
        if (Player.isOneRound) {
            message = "The Duel is Over!\n" + winnerUsername + " won the game and the score is: 1000-0";
            UserModel.getUserByUsername(winnerUsername).changeUserCoin(1000 + winnerPlayer.getLifePoint());
            UserModel.getUserByUsername(loserUsername).changeUserCoin(100);
        } else {
            isNewTurn = true;
            round--;
            int round = winnerPlayer.getNumberOfRound();
            round--;
            if (round == 0) {
                System.out.println(winnerPlayer.getNumberOfWin() + " [[[[ " + loserPlayer.getNumberOfWin());
                if (winnerPlayer.getNumberOfWin() > loserPlayer.getNumberOfWin()) {
                    message = "The Match is Over!\n" + winnerUsername + " won the whole match with score: 3000-0";
                    UserModel.getUserByUsername(winnerUsername).changeUserCoin(3000 + 3 * winnerPlayer.getMaxLifePoints());
                    UserModel.getUserByUsername(loserUsername).changeUserCoin(300);
                } else {
                    message = "The Match is Over!\n" + loserUsername + " won the whole match with score: 3000-0";
                    UserModel.getUserByUsername(loserUsername).changeUserCoin(3000 + 3 * loserPlayer.getMaxLifePoints());
                    UserModel.getUserByUsername(winnerUsername).changeUserCoin(300);
                }
                Player.allPlayers.remove(winnerPlayer.getNickname());
                Player.allPlayers.remove(loserPlayer.getNickname());
            } else {
                sideMsg = "Round " + round + " starts!";
            }
        }
    }

}