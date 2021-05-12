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

    public static void commandController(String firstPlayer, String secondPlayer, int roundNumber) {
        onlineUser = firstPlayer;
        rivalUser = secondPlayer;
        Phase currentPhase;
        while (true) {
            currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
            command = GameMatView.getCommand();
            if (selectCommand(command) == 1) {
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
            if (getMatcher(command, "show\\s+graveyard\\s+--opponent").find()) {
                GameMatModel.getGameMatByNickname(rivalUser).showGraveyard();
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+main\\s+deck").find()) {
                Player.getPlayerByName(onlineUser).showMainDeck();
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+side\\s+deck").find()) {
                Player.getPlayerByName(onlineUser).showSideDeck();
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+my\\s+hand").find()) {
                HandCardZone.showHandCard(onlineUser);
                backCommand();
                continue;
            }
            if (getMatcher(command, "surrender").find()) {
                endGame("surrender", roundNumber == 1, onlineUser);
                break;
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

    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (address < 1 || address > 5)
            GameMatView.showInput("Invalid selection");
        else {
            if (isOwnMonsterCard) {
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, onlineUser);
                if (ownMonsterCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    selectedOwnCard = "Monster " + ownMonsterCard.getMonsterName() + " " + address;
                    GameMatView.showInput("Card selected");
                }
            }
            else {
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                if (rivalMonsterCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    selectedRivalCard = "Monster " + rivalMonsterCard.getMonsterName() + " " + address;
                    GameMatView.showInput("Card selected");
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
                    if (Card.getCardsByName(ownSpellCard.getSpellTrapName()).getCardModel().equals("Spell"))
                        selectedOwnCard = "Spell " + ownSpellCard.getSpellTrapName() + " " + address;
                    else
                        selectedOwnCard = "Trap " + ownSpellCard.getSpellTrapName() + " " + address;
                    GameMatView.showInput("Card selected");
                }
            }
            else {
                SpellTrapZoneCard rivalSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
                if (rivalSpellCard == null)
                    GameMatView.showInput("No card found in the given position");
                else {
                    if (Card.getCardsByName(rivalSpellCard.getSpellTrapName()).getCardModel().equals("Spell"))
                        selectedRivalCard = "Spell " + rivalSpellCard.getSpellTrapName() + " " + address;
                    else
                        selectedRivalCard = "Trap " + rivalSpellCard.getSpellTrapName() + " " + address;
                    GameMatView.showInput("Card selected");
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
                selectedOwnCard = "Field";
                GameMatView.showInput("Card selected");
            }
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (rivalGameMat.getFieldZone().equals(""))
                GameMatView.showInput("Invalid selection");
            else {
                selectedRivalCard = "Field";
                GameMatView.showInput("Card selected");
            }
        }
    }

    public static void selectHandCard(int address) {
        if (address < 1 || address > HandCardZone.getNumberOfFullHouse(onlineUser))
            GameMatView.showInput("Invalid selection");
        else {
            selectedOwnCard = "Hand " + HandCardZone.getHandCardByAddress(address, onlineUser).getCardName() + " " + address;
            GameMatView.showInput("Card selected");
        }
    }

    public static void selectDelete() {
        boolean isRivalCardSelected = errorOfNoCardSelected("rival");
        if (isRivalCardSelected) {
            selectedRivalCard = "";
            GameMatView.showInput("Card deselected");
            return;
        }
        boolean isOwnCardSelected = errorOfNoCardSelected("own");
        if (isOwnCardSelected) {
            selectedOwnCard = "";
            GameMatView.showInput("card deselected");
        }
        else
            GameMatView.showInput("No card is selected yet");
    }

    public static String getSpecificPartOfSelectedCard(int whichPart) {
        String[] split = selectedOwnCard.split(" ");
        return split[whichPart];
    }

    public static void addToMonsterZoneCard(String monsterName, String mode) {
        if (monsterName.equals("Scanner")) {
            GameMatView.showInput("Which card for Scanner?");
            String whichCard = GameMatView.getCommand();
            new MonsterZoneCard(onlineUser, whichCard, mode, true, false, false);
        }
        else
            new MonsterZoneCard(onlineUser, monsterName, mode, false, false, false);
    }

    public static void addToSpellTrapZoneCard(String spellTrapName, String mode) {
        new SpellTrapZoneCard(onlineUser, spellTrapName, mode);
    }

    public static boolean errorOfNoCardSelected(String whoseCard) {
        if (whoseCard.equals("own")) {
            if (selectedOwnCard.equals("")) {
                GameMatView.showInput("no card is selected yet");
                return false;//own card is not selected
            }
            else
                return true;//no error
        }
        else {
            if (selectedRivalCard.equals("")) {
                GameMatView.showInput("no rival card is selected yet");
                return false;//rival card is not selected
            }
            else
                return true;
        }
    }

    public static boolean errorOfWrongPhase(String whichAction, Phase currentPhase) {
        if (whichAction.equals("summon") || whichAction.equals("set") || whichAction.equals("change") || whichAction.equals("flip")) {
            if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                GameMatView.showInput("action not allowed in this phase");
                selectedOwnCard = "";
                return false;//wrong Phase
            }
            else
                return true;
        }
        else if (whichAction.equals("activate")) {
            if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
                GameMatView.showInput("you can’t activate an effect on this turn");
                selectedOwnCard = "";
                return false;
            }
            else
                return true;//no error
        }
        else if (whichAction.equals("attack") || whichAction.equals("attackDirect")) {
            if (!currentPhase.name().equals("Battle_Phase")) {
                GameMatView.showInput("you can’t do this action in this phase");
                selectedOwnCard = "";
                return false;
            }
            else
                return true;//no error
        }
        return false;
    }

    public static boolean errorOfFullZone(String whichZone) {
        if (whichZone.equals("Monster")) {
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
                GameMatView.showInput("monster card zone is full");
                selectedOwnCard = "";
                return false;// have error
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
        return true;
    }

    public static int summon(String command, Phase currentPhase) {
        if (getMatcher(command, "summon").find()) {
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            Player player = Player.getPlayerByName(onlineUser);
            if (split[0].equals("Spell") || (split[0].equals("Hand") && !handCard.getKind().equals("Monster")) || (split[0].equals("Monster") && ownMonster.getMode().equals("OO"))) {
                GameMatView.showInput("you can’t summon this card");
                return 1;
            }
            if (!errorOfWrongPhase("summon", currentPhase))
                return 1;
            if (split[0].equals("Hand") && !errorOfFullZone("Monster"))
                return 1;
            if (specialSummon(ownMonster, handCard, split[1]) != 2) {
                selectedOwnCard = "";
                return 1;
            }
            if (!player.getCanSetSummonMonster())
                GameMatView.showInput("you already summoned/set on this turn");
            else {
                int monsterLevel = MonsterCard.getMonsterByName(split[1]).getLevel();
                if (monsterLevel <= 4) {
                    player.setCanSetSummonMonster(false);
                    if (split[0].equals("Hand")) {
                        addToMonsterZoneCard(split[1], "OO");
                        handCard.removeFromHandCard();
                        ownMonster = MonsterZoneCard.getMonsterCardByAddress(HandCardZone.getNumberOfFullHouse(onlineUser), onlineUser);
                    }
                    else
                        ownMonster.setMode("OO");
                    ownMonster.setHaveChangedPositionThisTurn(true);
                    GameMatView.showInput("Summoned successfully");
                    if (MonsterEffect.changeModeEffectController(ownMonster, onlineUser, rivalUser) == 0)
                        if (split[1].equals("The Calculator"))
                            MonsterEffect.theCalculator(onlineUser, ownMonster);
                }
                else if (monsterLevel == 5 || monsterLevel == 6) {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 0)
                        GameMatView.showInput("There are not enough cards for tribute");
                    else {
                        if (tributeMonster(1, split[1]) == 1) {
                            player.setCanSetSummonMonster(false);
                            if (split[0].equals("Hand")) {
                                addToMonsterZoneCard(split[1], "OO");
                                handCard.removeFromHandCard();
                                ownMonster = MonsterZoneCard.getMonsterCardByAddress(HandCardZone.getNumberOfFullHouse(onlineUser), onlineUser);
                            }
                            else
                                ownMonster.setMode("OO");
                            ownMonster.setHaveChangedPositionThisTurn(true);
                            GameMatView.showInput("Summoned successfully");
                        }
                    }
                }
                else {
                    if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 2)
                        GameMatView.showInput("There are not enough cards for tribute");
                    else {
                        if (tributeMonster(2, split[1]) == 1) {
                            player.setCanSetSummonMonster(false);
                            if (split[0].equals("Hand")) {
                                addToMonsterZoneCard(split[1], "OO");
                                handCard.removeFromHandCard();
                                ownMonster = MonsterZoneCard.getMonsterCardByAddress(HandCardZone.getNumberOfFullHouse(onlineUser), onlineUser);
                            }
                            else
                                ownMonster.setMode("OO");
                            ownMonster.setHaveChangedPositionThisTurn(true);
                            GameMatView.showInput("Summoned successfully");
                        }
                    }
                }
            }
            selectedOwnCard = "";
            return 1;
        }
        return 0;//invalid command
    }

    public static int specialSummon(MonsterZoneCard ownMonster, HandCardZone handCard, String monsterName) {
        return switch (monsterName) {
            case "Gate Guardian" -> MonsterEffect.gateGuardian(onlineUser, rivalUser);//0 cancel 1 success -1 cant
            case "Beast King Barbaros" -> MonsterEffect.beastKingBarbaros(ownMonster, handCard, onlineUser, rivalUser);
            case "Terratiger, the Empowered Warrior" -> MonsterEffect.terratiger(ownMonster, onlineUser);
            case "The Tricky" -> MonsterEffect.theTricky(ownMonster, onlineUser);
            default -> 2; //no special monster
        };
    }//check this method!!!

    public static int tributeMonster(int numberOfTribute, String monsterName) {
        int[] victimAddress = getAddressOfTributeMonster(numberOfTribute);
        if (victimAddress == null)
            return 0;//cancel
        for (int i = 0; i < numberOfTribute; i++) {
            MonsterZoneCard.getAllMonstersByPlayerName(onlineUser).get(victimAddress[i]).removeMonsterFromZone();
        }
        if (monsterName.equals("ritual")) {
            int monstersLevel = 0;
            for (int i = 0; i < numberOfTribute; i++) {
                monstersLevel += MonsterZoneCard.getMonsterCardByAddress(victimAddress[i],onlineUser).getLevel();
            }
            if (monstersLevel < 7) {
                GameMatView.showInput("selected monsters levels don’t match with ritual monster");
                GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
                command = GameMatView.getCommand();
                while (!command.matches("\\d+")) {
                    if (command.equals("cancel")) {
                        selectedOwnCard = "";
                        return 0;
                    }
                    command = GameMatView.getCommand();
                }
                numberOfTribute = Integer.parseInt(command);
                GameMatView.showInput("Please enter some Monster address to tribute:");
                tributeMonster(numberOfTribute, "ritual");
            }
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
                }
                else if (response.matches("\\d+"))
                    address = Integer.parseInt(response);

                if (address < 1 || address > 5 || MonsterZoneCard.getMonsterCardByAddress(address, onlineUser) == null)
                    GameMatView.showInput("Please enter the address of a monster correctly:");
                else {
                    tributeMonsterAddress[i-1] = address;
                    break;
                }
            }
        }
        return tributeMonsterAddress;
    }

    public static int flipSummon(String command, Phase currentPhase) {
        if (getMatcher(command,"flip-summon").find()) {
            if (errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t change this card position");
                return 1;
            }
            if (!errorOfWrongPhase("flip", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (!ownMonster.getMode().equals("DH") || ownMonster.getHaveChangedPositionThisTurn()) {
                GameMatView.showInput("you can’t flip summon this card");
            }
            else {
                ownMonster.setMode("OO");
                ownMonster.setHaveChangedPositionThisTurn(true);
                GameMatView.showInput("flip summoned successfully");
            }
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

    public static int changeMonsterPosition(String command, Phase currentPhase) {
        if (getMatcher(command,"set\\s+--position\\s+(attack|defense)").find()) {
            String mode = getMatcher(command, "set\\s+--position\\s+(attack|defense)").group(1);
            String[] split = selectedOwnCard.split(" ");
            if (!errorOfNoCardSelected("own"))
                return 1;
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t change this card position");
                return 1;
            }
            if (!errorOfWrongPhase("change", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if ((mode.equals("attack") && !ownMonster.getMode().equals("DO")) || (mode.equals("defend") && !ownMonster.getMode().equals("OO")))
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
        }
        return 0;
    }







    public static void ritualSummon(Phase currentPhase) {
        int ritualMonsterAddress = HandCardZone.doIHaveAnyRitualMonster(onlineUser);
        if (ritualMonsterAddress == 0)
            GameMatView.showInput("There is no way you could ritual summon a monster");
        else {
            HandCardZone handCard = HandCardZone.getHandCardByAddress(ritualMonsterAddress, onlineUser);
            String ritualMonsterName = handCard.getCardName();
            if (MonsterZoneCard.getSumOfMonstersLevel(onlineUser) < MonsterCard.getMonsterByName(ritualMonsterName).getLevel())
                GameMatView.showInput("There is no way you could ritual summon a monster");
            else {
                GameMatView.showInput("Please select a Ritual Monster to summon!");
                while (true) {
                    command = GameMatView.getCommand();
                    if (!getMatcher(command, "select\\s+--hand\\s+(\\d+)").find()) {
                        GameMatView.showInput("You should ritual summon right now");
                        continue;
                    }
                    int address = Integer.parseInt(getMatcher(command, "select\\s+--hand\\s+(\\d+)").group());
                    if (address == ritualMonsterAddress)
                        break;
                }
                selectHandCard(ritualMonsterAddress);
                GameMatView.showInput("Please enter the number of Monsters you want to tribute (Maximum 3 Monsters):");
                command = GameMatView.getCommand();
                while (!command.matches("\\d+")) {
                    if (command.equals("cancel")) {
                        selectedOwnCard = "";
                        return;
                    }
                    command = GameMatView.getCommand();
                }
                int numberOfTribute = Integer.parseInt(command);
                GameMatView.showInput("Please enter some Monster address to tribute:");
                if (tributeMonster(numberOfTribute, "ritual") == 1) {
                    GameMatView.showInput("Please enter Ritual Monster mode (defensive/attacking):");
                    command = GameMatView.getCommand();
                    while (!command.matches("defensive") && !command.matches("attacking")) {
                        command = GameMatView.getCommand();
                    }
                    if (command.equals("defensive"))
                        new MonsterZoneCard(onlineUser, ritualMonsterName, "DO", false, false, false);
                    else
                        new MonsterZoneCard(onlineUser, ritualMonsterName, "OO", false, false, false);

                    handCard.removeFromHandCard();
                }
                else
                    selectedOwnCard = "";
            }
        }
    }

    public static int set(String command, Phase currentPhase) {
        if (getMatcher(command,"set").find()) {
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("Hand")) {
                GameMatView.showInput("you can’t set this card");
                return 1;
            }
            if (errorOfWrongPhase("set", currentPhase))
                return 1;
            HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
            Player player = Player.getPlayerByName(onlineUser);
            String cardName = handCard.getCardName();
            if (handCard.getKind().equals("Monster")) {
                if (!errorOfFullZone("Monster"))
                    return 1;
                if (!player.getCanSetSummonMonster())
                    GameMatView.showInput("You already summoned/set on this turn");
                else {
                    handCard.removeFromHandCard();
                    addToMonsterZoneCard(cardName, "DH");
                    player.setCanSetSummonMonster(false);
                    GameMatView.showInput("Set successfully");
                    selectedOwnCard = "";
                    if (cardName.equals("The Calculator"))
                        MonsterEffect.theCalculator(onlineUser, MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser) , onlineUser));
                }
            }
//            else if (handCard.getKind().equals("Spell")) {
//                if (SpellTrapZoneCard.getNumberOfFullHouse(onlineUser) == 5)
//                    GameMatView.showInput("Spell card zone is full");
//                else { setinthisturn(true)
//                    handCard.removeFromHandCard();
//                    if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Field")) {
//                        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
//                        if (!ownGameMat.getFieldZone().equals(""))
//                            ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
//                        GameMatModel.getGameMatByNickname(onlineUser).addToFieldZone(cardName , "H");
//                        //SpellEffect.spellEffectController(, onlineUser, rivalUser, -1, -1, false);
//                    }
//                    else if (SpellCard.getSpellCardByName(cardName).getIcon().equals("Equip")) {
//
//                    }
//                    else
//                        addToSpellTrapZoneCard(handCard.getCardName(), "H");
//                    GameMatView.showInput("Set successfully");
//                    selectedOwnCard = "";
//                }
//            }
        }
        return 0;
    }



    public static int attack(String command, Phase currentPhase) {
        if (getMatcher(command, "attack\\s+(\\d+)").find()) {
            int rivalMonsterAddress = Integer.parseInt(getMatcher(command, "attack\\s+(\\d+)").group(1));
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t attack with this card");
                return 1;
            }
            else if (!errorOfWrongPhase("attack", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(getSpecificPartOfSelectedCard(2)), onlineUser);
            if (ownMonster.getHaveAttackThisTurn()) {
                GameMatView.showInput("this card already attacked");
                return 1;
            }
            MonsterZoneCard rivalMonster = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress, rivalUser);
            if (rivalMonster == null) {
                GameMatView.showInput("there is no card to attack here");
                return 1;
            }
            GameMatView.showInput("I want to attack to your Monster!");
            int damage;
            String rivalMonsterName = rivalMonster.getMonsterName();
            if (rivalMonsterName.equals("Suijin")) {
                if (MonsterEffect.suijin(rivalMonster) == 1)
                    return 1;
            }
            else if (rivalMonsterName.equals("Texchanger")) {
                if (MonsterEffect.texchanger(rivalMonster, rivalUser) == 1)
                    return 1;
            }
            String rivalMonsterMode = rivalMonster.getMode();
            if (rivalMonsterMode.equals("OO")) {
                damage = ownMonster.getAttack() - rivalMonster.getAttack();
                if (damage > 0) {
                    Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                    if (!rivalMonsterName.equals("Marshmallon"))
                        rivalMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Exploder Dragon")) {
                        ownMonster.removeMonsterFromZone();
                        return 1;
                    }
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
                }
                else if (damage == 0) {
                    ownMonster.removeMonsterFromZone();
                    if (!rivalMonsterName.equals("Marshmallon"))
                        rivalMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Exploder Dragon")) {
                        ownMonster.removeMonsterFromZone();
                        return 1;
                    }
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("both you and your opponent monster cards are destroyed and no one receives damage");
                }
                else {
                    Player.getPlayerByName(onlineUser).changeLifePoint(damage);
                    ownMonster.removeMonsterFromZone();
                    GameMatView.showInput("your monster card is destroyed and you received " + -1 * damage + " battle damage");
                }
            }
            else {
                if (ownMonster.getAttack() > rivalMonster.getDefend()) {
                    if (!rivalMonsterName.equals("Marshmallon"))
                        rivalMonster.removeMonsterFromZone();
                    if (rivalMonsterName.equals("Exploder Dragon")) {
                        ownMonster.removeMonsterFromZone();
                        return 1;
                    }
                    if (rivalMonsterName.equals("Yomi Ship"))
                        ownMonster.removeMonsterFromZone();
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and the defense position monster is destroyed");
                    else
                        GameMatView.showInput("the defense position monster is destroyed");
                }
                else if (ownMonster.getAttack() == rivalMonster.getDefend()) {
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed");
                    else
                        GameMatView.showInput("no card is destroyed");
                }
                else {
                    damage = rivalMonster.getDefend() - ownMonster.getAttack();
                    Player.getPlayerByName(onlineUser).changeLifePoint(-1 * damage);
                    if (rivalMonsterMode.equals("DH"))
                        GameMatView.showInput("opponent’s monster card was " + rivalMonsterName + " and no card is destroyed but you received " + damage + " battle damage");
                    else
                        GameMatView.showInput("no card is destroyed but you received " + damage + " battle damage");
                }
            }
            if (rivalMonsterName.equals("Marshmallon"))
                MonsterEffect.marshmallon(rivalMonster, onlineUser);
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

    public static int attackDirect(String command, Phase currentPhase) {
        if (getMatcher(command, "attack\\s+direct").find()) {
            if (errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t attack with this card");
                return 1;
            }
            if (!errorOfWrongPhase("attackDirect", currentPhase))
                return 1;
            MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
            if (ownMonsterCard.getHaveAttackThisTurn())
                GameMatView.showInput("this card already attacked");
            else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) != 0)
                GameMatView.showInput("you can’t attack the opponent directly");
            else {
                int damage = ownMonsterCard.getAttack();
                Player.getPlayerByName(rivalUser).changeLifePoint(-1 * damage);
                GameMatView.showInput("your opponent receives " + damage + " battle damage");
            }
            selectedOwnCard = "";
            return 1;
        }
        return 0;
    }

    public static int activateSpellEffect(String command, Phase currentPhase) {
        if (getMatcher(command, "activate\\s+effect").find()) {
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if ((split[0].equals("Monster") || split[0].equals("Trap")) || (split[0].equals("Hand") && !Card.getCardsByName(split[1]).getCardModel().equals("Spell"))) {
                GameMatView.showInput("activate effect is only for spell cards");
                return 1;
            }
            if (!errorOfWrongPhase("activate", currentPhase))
                return 1;
            String spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
            if (split[0].equals("Spell")) {
                SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
                if (ownSpell.getMode().equals("O")) {
                    GameMatView.showInput("you have already activated this card");
                    return 1;
                }
                ownSpell.setMode("O");

                if (spellIcon.equals("Normal")) {
                    GameMatView.showInput("I want to activate a Spell!\nSpell activated");
                    if (SpellEffect.normalEffectController(ownSpell, onlineUser, rivalUser) == 1)
                        ownSpell.removeSpellTrapFromZone();
                }
                else if (spellIcon.equals("Quick-play")) {
                    if (!ownSpell.getIsSetInThisTurn()) {
                        if (SpellEffect.quickPlayEffectController(ownSpell, onlineUser, rivalUser) == 1)
                            ownSpell.removeSpellTrapFromZone();
                    }
                }
                else if (spellIcon.equals("Equip")) {
                    if (getAddressOfRelatedMonster(ownSpell) != 0) {
                        SpellEffect.equipEffectController(ownSpell, onlineUser);
                    }
                    else
                        ownSpell.removeSpellTrapFromZone();
                }
                else
                    ritualSummon(currentPhase);
            }
            else if (split[0].equals("Hand")) {
                if (spellIcon.equals("Field")) {
                    GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                    if (!ownGameMat.getFieldZone().equals(""))
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    ownGameMat.addToFieldZone(split[1], "O");
                    SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
                }
                else {
                    if (!errorOfFullZone("Spell"))
                        return 1;
                    new SpellTrapZoneCard(onlineUser, split[1], "O");

                }
                GameMatView.showInput("spell activated");
            }
        }
        return 0;
    }


    public static int getAddressOfRelatedMonster(SpellTrapZoneCard ownSpell) {
        GameMatView.showInput("Whose Monster do you want to equip? (own/rival)");
        String response = GameMatView.getCommand();
        while (!response.matches("own|rival")) {
            if (response.equals("cancel"))
                return 0;
            GameMatView.showInput("Please enter the answer correctly: (own/rival)");
        }
        if (response.equals("own")) {
            GameMatView.showInput("Please enter the address of one of your Monster to equip: ");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the address correctly:");
            }
            ownSpell.setRelatedMonsterAddress("own", Integer.parseInt(response));
        }
        else {
            GameMatView.showInput("Please enter the address of one of the rival Monster to equip: ");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the address correctly:");
            }
            ownSpell.setRelatedMonsterAddress("rival", Integer.parseInt(response));
        }
        return 1;
    }




























    public static int changePhase(String command, Phase currentPhase) {
        if (getMatcher(command,"next\\s+phase").find()) {
            GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
            Player onlinePlayer = Player.getPlayerByName(onlineUser);
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
//                    if (onlinePlayer.getNumberOfMainDeckCards() == 0)
//                        endGame(false);
//                    else if (onlinePlayer.getCanDrawCard()) {
//                        String cardName = onlinePlayer.drawCard(false);
//                        GameMatView.showInput("new card added to the hand : " + cardName);
//
//                        new HandCardZone(onlineUser, cardName);
//                    }
                }
            }
        }
        return 0;
    }

    public static void endGame(String reason, boolean isOneRound, String loser) {
        String winner;
        if (loser.equals(onlineUser))
            winner = rivalUser;
        else
            winner = onlineUser;

        if (isOneRound) {
            GameMatView.showInput("The Duel is Over!");
            //UserModel.getUserByNickname(winner).changeUserCoin(1000 + Player.getPlayerByName(winner).getLifePoint());
            //UserModel.getUserByNickname(loser).changeUserCoin(100);
            //numberofwin
            if (reason.equals("surrender")) {

            }
            else if (reason.equals("nocard")) {

            }
            else if (reason.equals("lp")) {

            }
            else if (reason.equals("effect")) {

            }
        }
        else {

        }

    }

    public static void changeTurn() {
        Player onlinePlayer = Player.getPlayerByName(onlineUser);
        Player rivalPlayer = Player.getPlayerByName(rivalUser);
        MonsterZoneCard.changeOneTurnMonstersIsEffectUsed(onlineUser);
        MonsterZoneCard.removeUselessMonster(onlineUser);
        onlinePlayer.changeTurn();
        //MonsterEffect.heraldOfCreation()
        String temp = onlineUser;
        onlineUser = rivalUser;
        rivalUser = temp;
        Player.getPlayerByName(onlineUser).setIsYourTurn(true);
        Player.getPlayerByName(rivalUser).setIsYourTurn(false);
    }

    public static void backCommand() {
        while (true) {
            command = GameMatView.getCommand();
            if (command.equals("back")) {
                showGameBoard();
                return;
            }
            else
                GameMatView.showInput("invalid command");
        }
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

}
