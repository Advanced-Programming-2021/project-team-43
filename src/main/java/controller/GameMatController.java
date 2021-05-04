package controller;
import view.GameMatView;
import model.*;
import java.util.*;
import java.util.regex.*;


public class GameMatController {

    private static String selectedOwnCard = "";
    private static String selectedRivalCard = "";
    public static String onlineUser = "";
    public static String rivalUser = "";

    public static void findMatcher(String ownName, String rivalName) {
        String whoseFirst = PickFirstPlayer.chose(ownName, rivalName);
        UserModel ownUser = UserModel.getUserByUsername(ownName);
        UserModel rivalUser = UserModel.getUserByUsername(rivalName);
        new Player(ownUser.getNickname(), ownUser.userAllDecks.get(ownUser.getActiveDeck()), whoseFirst.equals(ownName));
        new Player(rivalUser.getNickname(), rivalUser.userAllDecks.get(rivalUser.getActiveDeck()), whoseFirst.equals(rivalName));
        String command;
        Phase currentPhase;
        while (true) {
            command = GameMatView.getCommand();
            if (Player.getPlayerByName(onlineUser).getIsYourTurn())
                currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
            else
                currentPhase = GameMatModel.getGameMatByNickname(rivalName).getPhase();

            if (command.startsWith("select")) {
                if (getMatcher(command,"select\\s+--monster\\s+(\\d+)").find()) {
                    selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--monster\\s+(\\d+)").group(1)), true);
                    continue;
                }
                if (getMatcher(command,"select\\s+--monster\\s+(\\d+)\\s+--opponent").find()) {
                    selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--monster\\s+(\\d+)\\s+--opponent").group(1)), false);
                    continue;
                }
                if (getMatcher(command,"select\\s+--opponent\\s+--monster\\s+(\\d+)").find()) {
                    selectMonsterCard(Integer.parseInt(getMatcher(command,"select\\s+--opponent\\s+--monster\\s+(\\d+)").group(1)), false);
                    continue;
                }
                if (getMatcher(command,"select\\s+--spell\\s+(\\d+)").find()) {
                    selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)").group(1)), true);
                    continue;
                }
                if (getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").find()) {
                    selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").group(1)), false);
                    continue;
                }
                if (getMatcher(command,"select\\s+--opponent\\s+--spell\\s+(\\d+)").find()) {
                    selectSpellCard(Integer.parseInt(getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent").group(1)), false);
                    continue;
                }
                if (getMatcher(command,"select\\s+--field").find()) {
                    selectFieldCard(true);
                    continue;
                }
                if (getMatcher(command,"select\\s+--field\\s+--opponent").find() || getMatcher(command,"select\\s+--opponent\\s+--field").find()) {
                    selectFieldCard(false);
                    continue;
                }
                if (getMatcher(command,"select\\s+--hand\\s+(\\d+)").find()) {
                    selectHandCard(Integer.parseInt(getMatcher(command,"select\\s+--hand\\s+(\\d+)").group(1)));
                    continue;
                }
                if (getMatcher(command,"select\\s+-d").find()) {
                    selectDelete();
                    continue;
                }
                GameMatView.showInput("invalid command");
                continue;
            }
            if (getMatcher(command,"next\\s+phase").find()) {
                changePhase(currentPhase);
                continue;
            }
            if (getMatcher(command,"summon").find()) {
                summon(currentPhase);
                continue;
            }
            if (getMatcher(command,"set").find()) {
                set(currentPhase);
                continue;
            }
            if (getMatcher(command,"set\\s+--position\\s+(attack|defense)").find()) {
                changeMonsterPosition(getMatcher(command,"set\\s+--position\\s+(attack|defense)").group(1), currentPhase);
                continue;
            }
            if (getMatcher(command,"flip-summon").find()) {
                flipSummon(currentPhase);
                continue;
            }
            if (getMatcher(command, "attack\\s+(\\d+)").find()) {
                attack(Integer.parseInt(getMatcher(command, "attack\\s+(\\d+)").group(1)), currentPhase);
                continue;
            }
            if (getMatcher(command, "attack\\s+direct").find()) {
                attackDirect(currentPhase);
                continue;
            }
            if (getMatcher(command, "activate\\s+effect").find()) {
                activateSpellEffect(currentPhase);
                continue;
            }
            if (getMatcher(command, "card\\s+show\\s+--selected").find()) {
                showSelectedCard();
                continue;
            }
            if (getMatcher(command, "show\\s+graveyard").find()) {
                GameMatModel.getGameMatByNickname(onlineUser).showGraveyard();
                continue;
            }
            if (getMatcher(command,"menu exit").find()) {
                break;
            }
            GameMatView.showInput("invalid command");
        }
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
                    selectedOwnCard = "monster " + ownMonsterCard.getMonsterName() + " " + address;
                    ownMonsterCard.setIsSelected(true);
                }
            }
            else {
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                if (rivalMonsterCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    selectedRivalCard = "monster " + rivalMonsterCard.getMonsterName() + " " + address;
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
                    selectedOwnCard = "spell " + ownSpellCard.getSpellName() + " " + address;
                    ownSpellCard.setIsSelected(true);
                }
            }
            else {
                SpellTrapZoneCard rivalSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
                if (rivalSpellCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    GameMatView.showInput("Card selected");
                    selectedRivalCard = "spell " + rivalSpellCard.getSpellName() + " " + address;
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
                selectedOwnCard = "field";
                ownGameMat.setOwnFieldZoneSelected();
            }
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (rivalGameMat.getFieldZone().equals(""))
                GameMatView.showInput("Invalid selection");
            else {
                GameMatView.showInput("Card selected");
                selectedRivalCard = "field";
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
            selectedOwnCard = "hand " + handCard.getCardName() + " " + address;
            handCard.setIsSelected(true);
        }
    }

    public static void selectDelete() {
        int whoseCardIsSelected = errorOfNoCardSelected();
        if (whoseCardIsSelected != 0) {
            GameMatView.showInput("Card deselected");
            String[] split;
            if (whoseCardIsSelected == 1) {
                split = selectedOwnCard.split(" ");
                selectedOwnCard = "";
                switch (split[0]) {
                    case "monster" -> MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).setIsSelected(false);
                    case "spell" -> SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser).setIsSelected(false);
                    case "hand" -> HandCardZone.getHandCardByAddress(Integer.parseInt(split[1]), onlineUser).setIsSelected(false);
                }
            }
            else {
                split = selectedRivalCard.split(" ");
                selectedRivalCard = "";
                switch (split[0]) {
                    case "monster" -> MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), rivalUser).setIsSelected(false);
                    case "spell" -> SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), rivalUser).setIsSelected(false);
                }
            }
        }
    }

    public static void summon(Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
            int numberOfFullHouse = MonsterZoneCard.getNumberOfFullHouse(onlineUser);
            if (!selectedOwnCard.startsWith("hand") || !handCard.getKind().equals("monster"))
                GameMatView.showInput("You can’t summon this card");
            else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                GameMatView.showInput("Action not allowed in this phase");
            else if (numberOfFullHouse == 5)
                GameMatView.showInput("Monster card zone is full");
            else if (Player.getPlayerByName(onlineUser).getIsYourMoveFinished())
                GameMatView.showInput("You already summoned/set on this turn");
            else {
                int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
                String command;
                if (monsterLevel <= 4) {
                    GameMatView.showInput("Summoned successfully");
                    selectedOwnCard = "";
                    Player.getPlayerByName(onlineUser).setIsYourMoveFinished(true);
                    if (MonsterCard.getMonsterByName(split[1]).getCardName().equals("Scanner"))
                        new MonsterZoneCard(onlineUser, split[1], "OO", true);
                    else
                        new MonsterZoneCard(onlineUser, split[1], "OO", false);
                }
                else if (monsterLevel == 5 || monsterLevel == 6) {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                        GameMatView.showInput("There are not enough cards for tribute");
                    else {
                        command = GameMatView.getCommand();
                        if (command.equals("cancel"))
                            return;
                        int victimAddress = Integer.parseInt(command);
                        if (MonsterZoneCard.getMonsterCardByAddress(victimAddress, onlineUser) == null)
                            GameMatView.showInput("There is no monster in this address");
                        else {
                            GameMatView.showInput("Summoned successfully");
                            selectedOwnCard = "";
                            Player.getPlayerByName(onlineUser).setIsYourMoveFinished(true);
                            MonsterZoneCard.getMonsterCardByAddress(victimAddress, onlineUser).setIsDead(true);
                            if (MonsterCard.getMonsterByName(split[1]).getCardName().equals("Scanner"))
                                new MonsterZoneCard(onlineUser, split[1], "OO", true);
                            else
                                new MonsterZoneCard(onlineUser, split[1], "OO", false);
                        }
                    }
                }
                else {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                        GameMatView.showInput("There are not enough cards for tribute");
                    else {
                        command = GameMatView.getCommand();
                        if (command.equals("cancel"))
                            return;
                        int victimOneAddress = Integer.parseInt(command);
                        command = GameMatView.getCommand();
                        if (command.equals("cancel"))
                            return;
                        int victimTwoAddress = Integer.parseInt(command);
                        if (MonsterZoneCard.getMonsterCardByAddress(victimOneAddress, onlineUser) == null || MonsterZoneCard.getMonsterCardByAddress(victimTwoAddress, onlineUser) == null)
                            GameMatView.showInput("There is no monster in one of these addresses");
                        else {
                            GameMatView.showInput("Summoned successfully");
                            selectedOwnCard = "";
                            Player.getPlayerByName(onlineUser).setIsYourMoveFinished(true);
                            MonsterZoneCard.getMonsterCardByAddress(victimOneAddress, onlineUser).setIsDead(true);
                            MonsterZoneCard.getMonsterCardByAddress(victimTwoAddress, onlineUser).setIsDead(true);
                            if (MonsterCard.getMonsterByName(split[1]).getCardName().equals("Scanner"))
                                new MonsterZoneCard(onlineUser, split[1], "OO", true);
                            else
                                new MonsterZoneCard(onlineUser, split[1], "OO", false);
                        }
                    }
                }
            }
        }
    }

    public static void set(Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            if (!selectedOwnCard.startsWith("hand"))
                GameMatView.showInput("You can’t set this card");
            else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                GameMatView.showInput("You can’t do this action in this phase");
                return;
            }
            HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (handCard.getKind().equals("monster")) {
                if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                    GameMatView.showInput("monster card zone is full");
                else if (Player.getPlayerByName(onlineUser).getIsYourMoveFinished())
                    GameMatView.showInput("You already summoned/set on this turn");
                else {
                    handCard.setIsRemoved(true);
                    if (MonsterCard.getMonsterByName(split[1]).getCardName().equals("Scanner"))
                        new MonsterZoneCard(onlineUser, split[1], "DH", true);
                    else
                        new MonsterZoneCard(onlineUser, split[1], "DH", false);
                    GameMatView.showInput("Set successfully");
                    selectedOwnCard = "";
                }
            }
            else if (handCard.getKind().equals("spell")) {
                if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5)
                    GameMatView.showInput("Spell card zone is full");
                else {
                    handCard.setIsRemoved(true);
                    new SpellTrapZoneCard(onlineUser, split[1], "DH");
                    GameMatView.showInput("Set successfully");
                    selectedOwnCard = "";
                }
            }
        }
    }

    public static void changeMonsterPosition(String mode, Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("monster")) {
                GameMatView.showInput("You can’t change this card position");
                return;
            }
            MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
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

    public static void flipSummon(Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("monster")) {
                GameMatView.showInput("You can’t change this card position");
                return;
            }
            MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                GameMatView.showInput("You can’t do this action in this phase");
            else if (!monsterCard.getMode().equals("set") || monsterCard.getHaveChangedPositionThisTurn()) {
                GameMatView.showInput("You can’t flip summon this card");
            }
            else {
                selectedOwnCard = "";
                monsterCard.setMode("OO");
                monsterCard.setHaveChangedPositionThisTurn(true);
                GameMatView.showInput("Flip summoned successfully");
            }
        }
    }

    public static void attack(int address, Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("monster"))
                GameMatView.showInput("You can’t attack with this card");
            else if (currentPhase.name().equals("Battle_Phase")) {
                GameMatView.showInput("You can’t do this action in this phase");
                return;
            }
            MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (ownMonsterCard.getHaveAttackThisTurn())
                GameMatView.showInput("This card already attacked");
            else if (MonsterZoneCard.getMonsterCardByAddress(address, rivalUser) == null) {
                GameMatView.showInput("There is no card to attack here");
                return;
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
                    rivalMonsterCard.setIsDead(true);
                    GameMatView.showInput("Your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                }
                else if (ownMonsterCard.getAttack() == rivalMonsterCard.getAttack()) {
                    ownMonsterCard.setIsDead(true);
                    rivalMonsterCard.setIsDead(true);
                    GameMatView.showInput("Both you and your opponent monster cards are destroyed and no one receives damage");
                }
                else {
                    damage = rivalMonsterCard.getAttack() - ownMonsterCard.getAttack();
                    Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                    ownMonsterCard.setIsDead(true);
                    GameMatView.showInput("Your monster card is destroyed and you received " + damage + " battle damage");
                }
            }
            else {
                if (ownMonsterCard.getAttack() > rivalMonsterCard.getDefend()) {
                    rivalMonsterCard.setIsDead(true);
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("Opponent’s monster card was " + rivalMonsterCard.getMonsterName() + " and the defense position monster is destroyed");
                    else
                        GameMatView.showInput("The defense position monster is destroyed");
                }
                else if (ownMonsterCard.getAttack() == rivalMonsterCard.getDefend()) {
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("Opponent’s monster card was " + rivalMonsterCard.getMonsterName() + " and no card is destroyed");
                    else
                        GameMatView.showInput("No card is destroyed");
                }
                else {
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

    public static void attackDirect(Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("monster"))
                GameMatView.showInput("You can’t attack with this card");
            else if (currentPhase.name().equals("Battle_Phase")) {
                GameMatView.showInput("You can’t do this action in this phase");
                return;
            }
            MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
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

    public static void activateSpellEffect(Phase currentPhase) {
        if (errorOfNoCardSelected() != 0) {
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
                    ownSpellCard.setMode("O");
                    GameMatView.showInput("Spell activated");
                }
            }
            else if (split[0].equals("hand")) {
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

    public static void selfActivateEffectAndTrap(Phase currentPhase) {

    }

    public static void ritualSummon(Phase currentPhase) {

    }

    public static void addCardToHand(String cardName) {

    }

    public static void specialSummon() {

    }

    public static String getSpecificPartOfSelectedCard(int whichPart) {
        String[] split = selectedOwnCard.split(" ");
        return split[whichPart];
    }

    public static void showSelectedCard() {
        if (errorOfNoCardSelected() != 0) {
            String[] split = selectedOwnCard.split(" ");
            String cardModel = Card.getCardsByName(split[1]).getCardModel();
            if (cardModel.equals("monster"))
                GameMatView.showInput(MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
            else if (cardModel.equals("spell"))
                GameMatView.showInput(SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
        }
    }


    public static void surrender() {

    }


    public static void showGameBoard() {
        GameMatView.showInput(rivalUser + " : " + Player.getPlayerByName(rivalUser).getLifePoint());
        for (int i = 0; i < HandCardZone.getNumberOfFullHouse(rivalUser); i++)
            System.out.print("   " + HandCardZone.getNumberOfFullHouse(rivalUser));
        GameMatView.showInput(String.valueOf(Player.getPlayerByName(rivalUser).getNumberOfDeckCards()));
        GameMatView.showInput("   " + SpellTrapZoneCard.getAllObjectsByPlayerName(rivalUser).get(4).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(rivalUser).get(2).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(rivalUser).get(1).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(rivalUser).get(3).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(rivalUser).get(5).getMode());

        GameMatView.showInput("   " + MonsterZoneCard.getAllObjectsByPlayerName(rivalUser).get(4).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(rivalUser).get(2).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(rivalUser).get(1).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(rivalUser).get(3).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(rivalUser).get(5).getMode());
        if (GameMatModel.getGameMatByNickname(onlineUser).getFieldZone().equals(""))
            GameMatView.showInput(GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        E\n");
        else
            GameMatView.showInput(GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        O\n");
        GameMatView.showInput("--------------------------\n");
        if (GameMatModel.getGameMatByNickname(onlineUser).getFieldZone().equals(""))
            GameMatView.showInput(GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        E\n");
        else
            GameMatView.showInput(GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards() + "                        O\n");
        GameMatView.showInput("   " + MonsterZoneCard.getAllObjectsByPlayerName(onlineUser).get(4).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(onlineUser).get(2).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(onlineUser).get(1).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(onlineUser).get(3).getMode() + "   " +
                MonsterZoneCard.getAllObjectsByPlayerName(onlineUser).get(5).getMode());
        GameMatView.showInput("   " + SpellTrapZoneCard.getAllObjectsByPlayerName(onlineUser).get(4).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(onlineUser).get(2).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(onlineUser).get(1).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(onlineUser).get(3).getMode() + "   " +
                SpellTrapZoneCard.getAllObjectsByPlayerName(onlineUser).get(5).getMode());
        GameMatView.showInput(String.valueOf(Player.getPlayerByName(onlineUser).getNumberOfDeckCards()));
        for (int i = 0; i < HandCardZone.getNumberOfFullHouse(onlineUser); i++)
            System.out.print("   " + HandCardZone.getNumberOfFullHouse(onlineUser));
        GameMatView.showInput(onlineUser + " : " + Player.getPlayerByName(onlineUser).getLifePoint());
    }


    public static int errorOfNoCardSelected() {
        if (selectedRivalCard.equals("")) {
            if (selectedOwnCard.equals("")) {
                GameMatView.showInput("no card is selected yet");
                return 0;
            }
            else
                return 1;
        }
        else
            return 2;
    }

    public static void changePhase(Phase currentPhase) {
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        switch (currentPhase.name()) {
            case "Draw_Phase" -> {
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
                if (onlinePlayer.getNumberOfDeckCards() == 0)
                    endGame();
                else {
                    String cardName = onlinePlayer.addToHandCard();
                    onlinePlayer.removeFromDeckCards();
                    new HandCardZone(onlineUser, cardName, Card.getCardsByName(cardName).getCardModel());
                    GameMatView.showInput("new card added to the hand : " + cardName);
                }
            }
        }
    }

    public static void endGame() {

    }
    public static void changeTurn() {
        String temp = onlineUser;
        onlineUser = rivalUser;
        rivalUser = temp;
    }

}
