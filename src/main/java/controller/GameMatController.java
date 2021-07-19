package controller;

import view.GameMatView;
import model.*;
import view.RegisterAndLoginView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.regex.*;


public class GameMatController {

    public static String selectedOwnCard = "";
    public static String selectedRivalCard = "";
    public static String onlineUser = MainMenuController.username;
    public static String rivalUser = MainMenuController.username2;
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
    public static String rivalToken;
    public static String onlineToken;

    public static int commandController(String command, GameMatView gameMatView) {
        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();

        ///cheat
        if (getMatcher(command, "duel \\s*set-winner \\s*" + onlineUser).find()) {
            endGame(rivalUser);
            gameMatView.endGame();
            return 35;
        }
        if (getMatcher(command, "duel \\s*set-winner \\s*" + rivalUser).find()) {
            endGame(onlineUser);
            gameMatView.endGame();
            return 36;
        }
        if ((matcher = getMatcher(command, "increase\\s+--LP\\s+(\\d+)")).find()) {
            increaseLP(Integer.parseInt(matcher.group(1)), onlineUser);
            return 37;
        }
        if ((matcher = getMatcher(command, "select\\s+--hand\\s+(.+?)\\s+--force")).find() || (matcher = getMatcher(command, "s\\s+-h\\s+(.+?)\\s+-f")).find() || (matcher = getMatcher(command, "select\\s+--force\\s+--hand\\s+(.+?)")).find() || (matcher = getMatcher(command, "s\\s+-f\\s+-h\\s+(.+?)")).find()) {
            if (Card.getCardsByName(matcher.group(1)) != null) {
                HandCardZone handCard = new HandCardZone(onlineUser, matcher.group(1));
                selectedOwnCard = "Hand/" + matcher.group(1) + "/" + handCard.getAddress();
            }
            return 38;
        }


        return 39;
    }

    public static ArrayList<Object> getObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(GameMatModel.getGameMatByNickname(onlineUser));
        objects.add(HandCardZone.getAllHandCardZoneByName(onlineUser));
        objects.add(MonsterZoneCard.getAllMonstersByPlayerName(onlineUser));
        objects.add(Player.getPlayerByName(onlineUser));
        objects.add(SpellTrapZoneCard.getAllSpellTrapByPlayerName(onlineUser));
        objects.add(UserModel.getUserByNickname(onlineUser));

        objects.add(GameMatModel.getGameMatByNickname(rivalUser));
        objects.add(HandCardZone.getAllHandCardZoneByName(rivalUser));
        objects.add(MonsterZoneCard.getAllMonstersByPlayerName(rivalUser));
        objects.add(Player.getPlayerByName(rivalUser));
        objects.add(SpellTrapZoneCard.getAllSpellTrapByPlayerName(rivalUser));
        objects.add(UserModel.getUserByNickname(rivalUser));
        return objects;

    }

    public static void setObjects(ArrayList<Object> objects) {
        GameMatModel.setObject(MainMenuController.username, (GameMatModel) objects.get(0));
        HandCardZone.setObject(MainMenuController.username, (List<HandCardZone>) objects.get(1));
        MonsterZoneCard.setObject(MainMenuController.username, ( Map<Integer, MonsterZoneCard>) objects.get(2));
        Player.setObject(MainMenuController.username, (Player) objects.get(3));
        SpellTrapZoneCard.setObject(MainMenuController.username, ( Map<Integer, SpellTrapZoneCard>) objects.get(4));
        UserModel.setObject((UserModel) objects.get(5));

        GameMatModel.setObject(rivalUser, (GameMatModel) objects.get(6));
        HandCardZone.setObject(rivalUser, (List<HandCardZone>) objects.get(7));
        MonsterZoneCard.setObject(rivalUser, ( Map<Integer, MonsterZoneCard>) objects.get(8));
        Player.setObject(rivalUser, (Player) objects.get(9));
        SpellTrapZoneCard.setObject(rivalUser, ( Map<Integer, SpellTrapZoneCard>) objects.get(10));
        UserModel.setObject((UserModel) objects.get(11));
    }


    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {//ok
        if (isOwnMonsterCard) {
            try {

                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --monster " + address+":"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //

                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedOwnCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --monster " + address + " --opponent:"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedRivalCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void selectHandCard(int address) {//ok
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --hand " + address+":"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changePhase(Phase currentPhase) {//shak
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" +onlineToken + ":next phase:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            if (currentPhase.name().equals("Main_Phase2")) {
                System.out.println(onlineUser);
              //  gameMatView.start(GameMatView.gameMatStage);//not rotate
                gameMatView.showGameBoard();
            }
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            sideMsg = response[1];
            sideMsg2 = response[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int selectSpellCard(int address, boolean isOwnSpellCard) {//ok
        if (isOwnSpellCard) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --spell " + address+":"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedOwnCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --spell " + address + " --opponent:"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedRivalCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 1;
    }

    public static void summon(Phase currentPhase) {//ok
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":summon:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
            if(response[2]!=null){
                GameMatView.effectCardName = response[2];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectFieldCard(boolean isOwnField) {//ok
        if (isOwnField) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --field:"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedOwnCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":select --monster --opponent:"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                selectedRivalCard = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void flipSummon(Phase currentPhase) {//ok
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":flip summon:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeToAttackPosition(Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":change to attack position:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int changeToDefensePosition(Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":change to defend position:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
            error = response[2];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static void set(Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":set:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int attack(int rivalMonsterAddress, Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" +onlineToken + ":attack " + rivalMonsterAddress+":"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
            return Integer.parseInt(response[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int attackDirect(Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" +onlineToken + ":attack direct:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
            return Integer.parseInt(response[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void endGame(String loserNickname) {
        if (loserNickname.equals(onlineUser)) {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":duel set-winner" + rivalUser+":"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                sideMsg = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":duel set-winner" + onlineUser+":"+rivalToken);
                RegisterAndLoginView.dataOutputStream.flush();
                ///
                RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
                setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
                //
                String answer = RegisterAndLoginView.dataInputStream.readUTF();
                String[] response = answer.split("@");
                message = response[0];
                sideMsg = response[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static int activateSpellEffect(Phase currentPhase) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("GameMat:" + onlineToken + ":activate Spell Effect:"+rivalToken);
            RegisterAndLoginView.dataOutputStream.flush();
            ///
            RegisterAndLoginView.objectOutputStream.writeObject(getObjects());
            setObjects((ArrayList<Object>) RegisterAndLoginView.objectInputStream.readObject());
            //
            String answer = RegisterAndLoginView.dataInputStream.readUTF();
            String[] response = answer.split("@");
            message = response[0];
            selectedOwnCard = response[1];
            return Integer.parseInt(response[2]);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void increaseLP(int lifePoint, String onlineUser) {
        Player player = Player.getPlayerByName(onlineUser);
        player.changeLifePoint(lifePoint);
    }

//
//    public static void AI() {//server
//        currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
////        try {
////            Thread.sleep(2000);
////        } catch (InterruptedException e) {
////            Thread.currentThread().interrupt();
////        }
//        if (currentPhase.equals(Phase.Draw_Phase)) {
//            command = "next phase";
//        }
//        if (currentPhase.equals(Phase.Standby_Phase)) {
//            command = "next phase";
//        }
//        if (currentPhase.equals(Phase.Main_Phase1)) {
//            if (counterOne == 0) {
//                command = "select --hand 1";
//                counterOne++;
//            } else if (counterOne == 1) {
//                command = "summon";
//                counterOne++;
//            } else if (counterOne == 2) {
//                command = "next phase";
//            }
//        }
//        if (currentPhase.equals(Phase.Battle_Phase)) {
//            counterOne = 0;
//            if (counterTwo == 0) {
//                command = "select --monster 1";
//                counterTwo++;
//            } else if (counterTwo == 1) {
//                if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) == 0) {
//                    command = "attack direct";
//                } else {
//                    command = "attack 1";
//                }
//                counterTwo++;
//            } else if (counterTwo == 2) {
//                command = "next phase";
//                counterTwo = 0;
//            }
//        }
//        if (currentPhase.equals(Phase.Main_Phase2)) {
//            command = "next phase";
//        }
//    }
//
//    public static int AIAttack(String command, Phase currentPhase) {
//        if ((matcher = getMatcher(command, "^attack\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^a\\s+(\\d+)$")).find()) {
//            int rivalMonsterAddress = Integer.parseInt(matcher.group(1));
//            if (!errorOfNoCardSelected("own"))
//                return 1;
//            String[] split = selectedOwnCard.split("/");
//            if (!split[0].equals("Monster")) {
//                GameMatView.showInput("you can’t attack with this card");
//                selectedOwnCard = "";
//                return 1;
//            } else if (!errorOfWrongPhase("attack", currentPhase))
//                return 1;
//            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
//            if (!ownMonster.getMode().equals("OO")) {
//                GameMatView.showInput("your card is not in an attack mode");
//                selectedOwnCard = "";
//                return 1;
//            }
//            if (ownMonster.getHaveAttackThisTurn()) {
//                GameMatView.showInput("this card already attacked");
//                selectedOwnCard = "";
//                return 1;
//            }
//            if (!ownMonster.getCanAttack()) {
//                GameMatView.showInput("this Monster cant attack because of a spell effect!");
//                selectedOwnCard = "";
//                return 1;
//            }
//            MonsterZoneCard rivalMonster = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress, rivalUser);
//            if (rivalMonster == null) {
//                GameMatView.showInput("there is no card to attack here");
//                selectedOwnCard = "";
//                return 1;
//            }
//            if (!rivalMonster.getCanAttackToThisMonster()) {
//                GameMatView.showInput("you cant attack to this monster!");
//                selectedOwnCard = "";
//                return 1;
//            }
//            GameMatView.showInput("I want to attack to your Monster!");
//            ownMonster.setHaveAttackThisTurn(true);
//            int result;
//            result = checkForSetTrapToActivateInRivalTurn("Magic Cylinder", ownMonster);
//            if (result == 1)
//                return 1;
//            result = checkForSetTrapToActivateInRivalTurn("Mirror Force", ownMonster);
//            if (result == 1)
//                return 1;
//            result = checkForSetTrapToActivateInRivalTurn("Negate Attack", ownMonster);
//            if (result == 1)
//                return 1;
//            int damage;
//            String rivalMonsterName = rivalMonster.getMonsterName();
//            if (rivalMonsterName.equals("Suijin")) {
//                if (MonsterEffect.suijin(rivalMonster) == 1) {
//                    selectedOwnCard = "";
//                    return 1;
//                }
//            } else if (rivalMonsterName.equals("Texchanger")) {
//                if (MonsterEffect.texchanger(rivalMonster, rivalUser) != 0) {
//                    selectedOwnCard = "";
//                    return 1;
//                }
//            }
//            if (rivalMonsterName.equals("Exploder Dragon")) {
//                GameMatView.showInput("Exploder Dragon Effect is activated!");
//                rivalMonster.removeMonsterFromZone();
//                ownMonster.removeMonsterFromZone();
//                GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
//                GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                return 1;
//            }
//            String rivalMonsterMode = rivalMonster.getMode();
//            if (rivalMonsterMode.equals("OO")) {
//                damage = ownMonster.getAttack() - rivalMonster.getAttack();
//                if (damage > 0) {
//                    Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
//                    if (!rivalMonsterName.equals("Marshmallon")) {
//                        rivalMonster.removeMonsterFromZone();
//                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
//                    } else {
//                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
//                    }
//                    if (rivalMonsterName.equals("Yomi Ship")) {
//                        ownMonster.removeMonsterFromZone();
//                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                    }
//                    GameMatView.showInput("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
//                    if (Player.getPlayerByName(rivalUser).getLifePoint() <= 0) {
//                        Player.getPlayerByName(rivalUser).setLifePoint(0);
//                        endGame(rivalUser);
//                    }
//                } else if (damage == 0) {
//                    ownMonster.removeMonsterFromZone();
//                    rivalMonster.removeMonsterFromZone();
//                    GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
//                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                    GameMatView.showInput("both you and your opponent monster cards are destroyed and no one receives damage");
//                } else {
//                    Player.getPlayerByName(onlineUser).changeLifePoint(damage);
//                    if (ownMonster.getMonsterName().equals("Exploder Dragon")) {
//                        GameMatView.showInput("Exploder Dragon Effect is activated!");
//                        rivalMonster.removeMonsterFromZone();
//                        ownMonster.removeMonsterFromZone();
//                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
//                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                        return 1;
//                    }
//                    ownMonster.removeMonsterFromZone();
//                    GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                    GameMatView.showInput("your monster card is destroyed and you received " + -1 * damage + " battle damage");
//                    if (Player.getPlayerByName(onlineUser).getLifePoint() <= 0) {
//                        Player.getPlayerByName(onlineUser).setLifePoint(0);
//                        endGame(onlineUser);
//                    }
//                }
//            } else {
//                if (ownMonster.getAttack() > rivalMonster.getDefend()) {
//                    if (!rivalMonsterName.equals("Marshmallon")) {
//                        rivalMonster.removeMonsterFromZone();
//                        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
//                    } else {
//                        MonsterEffect.marshmallon(rivalMonster, onlineUser);
//                    }
//                    if (rivalMonster.getMode().equals("DH")) {
//                        if (ownMonster.getMonsterName().equals("The Calculator"))
//                            MonsterEffect.theCalculator(onlineUser, ownMonster);
//                        rivalMonster.setMode("DO");
//                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
//                    }
//                    if (rivalMonsterName.equals("Yomi Ship")) {
//                        ownMonster.removeMonsterFromZone();
//                        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
//                    }
//                    if (rivalMonsterMode.equals("DH"))
//                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and the defense position monster is destroyed");
//                    else {
//                        if (rivalMonsterName.equals("Marshmallon"))
//                            GameMatView.showInput("the defense position monster is destroyed");
//                    }
//                } else if (ownMonster.getAttack() == rivalMonster.getDefend()) {
//                    if (rivalMonsterMode.equals("DH")) {
//                        if (rivalMonsterName.equals("Marshmallon")) {
//                            MonsterEffect.marshmallon(rivalMonster, onlineUser);
//                        }
//                        if (ownMonster.getMonsterName().equals("The Calculator"))
//                            MonsterEffect.theCalculator(onlineUser, ownMonster);
//                        rivalMonster.setMode("DO");
//                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
//                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed");
//                    } else
//                        GameMatView.showInput("no card is destroyed");
//                } else {
//                    damage = rivalMonster.getDefend() - ownMonster.getAttack();
//                    Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
//                    if (rivalMonsterMode.equals("DH")) {
//                        if (rivalMonsterName.equals("Marshmallon")) {
//                            MonsterEffect.marshmallon(rivalMonster, onlineUser);
//                        }
//                        if (ownMonster.getMonsterName().equals("The Calculator"))
//                            MonsterEffect.theCalculator(onlineUser, ownMonster);
//                        rivalMonster.setMode("DO");
//                        MonsterEffect.changeModeEffectController(rivalMonster, rivalUser, onlineUser);
//                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed but you received " + damage + " battle damage");
//                    } else
//                        GameMatView.showInput("no card is destroyed but you received " + damage + " battle damage");
//                    if (Player.getPlayerByName(onlineUser).getLifePoint() < 0) {
//                        Player.getPlayerByName(onlineUser).setLifePoint(0);
//                        endGame(onlineUser);
//                    }
//                }
//            }
//            selectedOwnCard = "";
//            return 1;
//        }
//        return 0;
//    }


}