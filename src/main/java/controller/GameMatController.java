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

    public static void commandController(String firstPlayer, String secondPlayer) {
        GameMatView.showInput("The game starts!");
        onlineUser = firstPlayer;
        rivalUser = secondPlayer;
        Phase currentPhase;
        while (true) {
            currentPhase = GameMatModel.getGameMatByNickname(onlineUser).getPhase();
            command = GameMatView.getCommand();
            if (selectCommand(command) == 1) {
                continue;
            }
            if (getMatcher(command,"next\\s+phase").find()) {
                changePhase(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "summon").find()) {
                summon(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command,"set").find()) {
                set(currentPhase);
                showGameBoard();
                continue;
            }
            if (changeMonsterPosition(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (getMatcher(command,"flip-summon").find()) {
                flipSummon(currentPhase);
                showGameBoard();
                continue;
            }
            if (attack(command, currentPhase) == 1) {
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "attack\\s+direct").find()) {
                attackDirect(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "activate\\s+effect").find()) {
                activateSpellEffect(currentPhase);
                showGameBoard();
                continue;
            }
            if (getMatcher(command, "card\\s+show\\s+--selected").find()) {
                showSelectedCard();
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+graveyard").find()) {
                GameMatModel.getGameMatByNickname(onlineUser).showGraveyard();
                backCommand();
                continue;
            }
            if (getMatcher(command, "show\\s+graveyard\\s+--opponent").find()) {
                getPermission();
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

                break;
            }
            if (exchangeCard() == 1) {
                continue;
            }
            if (getMatcher(command,"menu exit").find()) {
                break;
            }
            GameMatView.showInput("invalid command");
        }
    }


    public static void getPermission() {
        do {
            GameMatView.showInput("**Your opponent want to see your graveyard. Do you give him permission? (yes/no)**");
            response = GameMatView.getCommand();
        } while (!response.matches("yes|no"));
        if (response.equals("yes")) {
            GameMatModel.getGameMatByNickname(rivalUser).showGraveyard();
            backCommand();
        }
        else
            GameMatView.showInput("Oops! You dont have permission to see rival graveyard!");
    }


    public static int exchangeCard() {
        if ((matcher = getMatcher(command, "exchange\\s+main\\s+card\\s+(\\d+)with\\s+side\\s+card\\s+(\\d+)")).find()) {
            int cardAddressInMainDeck = Integer.parseInt(matcher.group(1));
            int cardAddressInSideDeck = Integer.parseInt(matcher.group(2));
            if (Player.getPlayerByName(onlineUser).exchangeCard(cardAddressInMainDeck, cardAddressInSideDeck) == 0)
                GameMatView.showInput("Oops! You cant exchange this two cards!");
            return 1;
        }
        else
            return 0;
    }


    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static int selectCommand(String command) {
        if (command.startsWith("select")) {
            if ((matcher = getMatcher(command,"select\\s+--monster\\s+(\\d+)")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), true);
                showGameBoard();
                return 1;
            }
            if ((matcher = getMatcher(command,"select\\s+--monster\\s+(\\d+)\\s+--opponent")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                return 1;
            }
            if ((matcher = getMatcher(command,"select\\s+--opponent\\s+--monster\\s+(\\d+)")).find()) {
                selectMonsterCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                return 1;
            }
            if ((matcher = getMatcher(command,"select\\s+--spell\\s+(\\d+)")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), true);
                showGameBoard();
                return 1;
            }
            if ((matcher = getMatcher(command,"select\\s+--spell\\s+(\\d+)\\s+--opponent")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), false);
                showGameBoard();
                return 1;
            }
            if ((matcher = getMatcher(command,"select\\s+--opponent\\s+--spell\\s+(\\d+)")).find()) {
                selectSpellCard(Integer.parseInt(matcher.group(1)), false);
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
            if ((matcher = getMatcher(command,"select\\s+--hand\\s+(\\d+)")).find()) {
                selectHandCard(Integer.parseInt(matcher.group(1)));
                showGameBoard();
                return 1;
            }
            if (getMatcher(command,"select\\s+-d").find()) {
                selectDelete();
                showGameBoard();
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (errorOfInvalidSelection(address, "Monster")) {
            if (isOwnMonsterCard) {
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, onlineUser);
                if (ownMonsterCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedOwnCard = "Monster " + ownMonsterCard.getMonsterName() + " " + address;
                    GameMatView.showInput("card selected");
                }
            }
            else {
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(address, rivalUser);
                if (rivalMonsterCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedRivalCard = "Monster " + rivalMonsterCard.getMonsterName() + " " + address;
                    GameMatView.showInput("card selected");
                }
            }
        }
    }

    public static void selectSpellCard(int address, boolean isOwnSpellCard) {
        if (errorOfInvalidSelection(address, "Spell")) {
            if (isOwnSpellCard) {
                SpellTrapZoneCard ownSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser);
                if (ownSpellCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedOwnCard = ownSpellCard.getKind() + " " + ownSpellCard.getSpellTrapName() + " " + address;
                    GameMatView.showInput("card selected");
                }
            }
            else {
                SpellTrapZoneCard rivalSpellCard = SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser);
                if (rivalSpellCard == null)
                    GameMatView.showInput("no card found in the given position");
                else {
                    selectedRivalCard = rivalSpellCard.getKind() + " " + rivalSpellCard.getSpellTrapName() + " " + address;
                    GameMatView.showInput("card selected");
                }
            }
        }
    }

    public static void selectFieldCard(boolean isOwnField) {
        if (isOwnField) {
            GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
            if (ownGameMat.getFieldZone().equals(""))
                GameMatView.showInput("invalid selection");
            else {
                selectedOwnCard = "Field";
                GameMatView.showInput("card selected");
            }
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            if (rivalGameMat.getFieldZone().equals(""))
                GameMatView.showInput("invalid selection");
            else {
                selectedRivalCard = "Field";
                GameMatView.showInput("card selected");
            }
        }
    }

    public static void selectHandCard(int address) {
        if (errorOfInvalidSelection(address, "Hand")) {
            selectedOwnCard = "Hand " + HandCardZone.getHandCardByAddress(address, onlineUser).getCardName() + " " + address;
            GameMatView.showInput("card selected");
        }
    }

    public static void selectDelete() {
        if (!selectedRivalCard.isEmpty()) {
            selectedRivalCard = "";
            GameMatView.showInput("card deselected");
        }
        else if (!selectedOwnCard.isEmpty()) {
            selectedOwnCard = "";
            GameMatView.showInput("card deselected");
        }
        else
            GameMatView.showInput("no card is selected yet");
    }

    public static boolean errorOfNoCardSelected(String whoseCard) {
        if (whoseCard.equals("own")) {
            if (selectedOwnCard.equals("")) {
                GameMatView.showInput("no card is selected yet");
                return false;//have error
            }
            else
                return true;//no error
        }
        else {
            if (selectedRivalCard.equals("")) {
                GameMatView.showInput("no rival card is selected yet");
                return false;
            }
            else
                return true;
        }
    }

    public static boolean errorOfInvalidSelection(int address, String whichPart) {
        if (whichPart.equals("Monster") || whichPart.equals("Spell")) {
            if (address < 1 || address > 5) {
                GameMatView.showInput("invalid selection");
                return false;//hss error
            }
            return true;
        }
        else if (whichPart.equals("Hand")) {
            if (address < 1 || address > HandCardZone.getNumberOfFullHouse(onlineUser)) {
                GameMatView.showInput("invalid selection");
                return false;//hss error
            }
            return true;
        }
        return true;
    }
//----------------------------------------------------------------------------------------------------------------------
    public static void summon(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split(" ");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        if (split[0].equals("Spell") || (split[0].equals("Hand") && !handCard.getKind().equals("Monster")) || (split[0].equals("Monster") && ownMonster.getMode().equals("OO")) || ownMonster.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t summon this card");
            return;
        }
        if (!errorOfWrongPhase("summon", currentPhase))
            return;
        if (split[0].equals("Hand") && !errorOfFullZone("Monster"))
            return;
        if (specialSummon(ownMonster, handCard, split[1]) != 2) {
            selectedOwnCard = "";
            return;
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
    }

    public static void addToMonsterZoneCard(String monsterName, String mode) {
        if (monsterName.equals("Scanner")) {
            GameMatView.showInput("Which card for Scanner?");
            String whichCard = GameMatView.getCommand();
            new MonsterZoneCard(onlineUser, whichCard, mode, true, false);
        }
        else
            new MonsterZoneCard(onlineUser, monsterName, mode, false, false);
    }

    public static void addToSpellTrapZoneCard(String spellTrapName, String mode) {
        new SpellTrapZoneCard(onlineUser, spellTrapName, mode);
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

    public static int specialSummon(MonsterZoneCard ownMonster, HandCardZone handCard, String monsterName) {
        return switch (monsterName) {
            case "Gate Guardian" -> MonsterEffect.gateGuardian(onlineUser, rivalUser);//0 cancel 1 success -1 cant
            case "Beast King Barbaros" -> MonsterEffect.beastKingBarbaros(ownMonster, handCard, onlineUser, rivalUser);
            case "Terratiger, the Empowered Warrior" -> MonsterEffect.terratiger(ownMonster, onlineUser);
            case "The Tricky" -> MonsterEffect.theTricky(ownMonster, onlineUser);
            default -> 2; //no special monster
        };
    }//check this method!!!

    public static int tributeMonster(int numberOfTribute, String monsterType) {
        int[] victimAddress = getAddressOfTributeMonster(numberOfTribute);
        if (victimAddress == null)
            return 0;//cancel
        if (monsterType.equals("Ritual")) {
            int monstersLevel = 0;
            for (int i = 0; i < numberOfTribute; i++) {
                monstersLevel += MonsterZoneCard.getMonsterCardByAddress(victimAddress[i],onlineUser).getLevel();
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
            MonsterZoneCard.getAllMonstersByPlayerName(onlineUser).get(victimAddress[i]).removeMonsterFromZone();
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

    public static void flipSummon(Phase currentPhase) {
        if (errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split(" ");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t change this card position");
            return;
        }
        if (!errorOfWrongPhase("flip", currentPhase))
            return;
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
    }

    public static int changeMonsterPosition(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command,"set\\s+--position\\s+(attack|defense)")).find()) {
            String mode = matcher.group(1);
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

    public static void set(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split(" ");
        if (!split[0].equals("Hand")) {
            GameMatView.showInput("you can’t set this card");
            return;
        }
        if (errorOfWrongPhase("set", currentPhase))
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
                else {
                    handCard.removeFromHandCard();
                    addToMonsterZoneCard(cardName, "DH");
                    player.setCanSetSummonMonster(false);
                    GameMatView.showInput("set successfully");
                    if (cardName.equals("The Calculator"))
                        MonsterEffect.theCalculator(onlineUser, MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser));
                }
            }
            case "Spell" -> {
                if (!errorOfFullZone("Spell"))
                    return;
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
            }
            case "Trap" -> {
                if (!errorOfFullZone("Spell"))
                    return;
                handCard.removeFromHandCard();
                addToSpellTrapZoneCard(handCard.getCardName(), "H");
                SpellTrapZoneCard.getSpellCardByAddress(SpellTrapZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setIsSetInThisTurn(true);
                GameMatView.showInput("Set successfully");
            }
        }
        selectedOwnCard = "";
    }

    public static int attack(String command, Phase currentPhase) {
        if ((matcher = getMatcher(command, "attack\\s+(\\d+)")).find()) {
            int rivalMonsterAddress = Integer.parseInt(matcher.group(1));
            if (!errorOfNoCardSelected("own"))
                return 1;
            String[] split = selectedOwnCard.split(" ");
            if (!split[0].equals("Monster")) {
                GameMatView.showInput("you can’t attack with this card");
                return 1;
            }
            else if (!errorOfWrongPhase("attack", currentPhase))
                return 1;
            MonsterZoneCard ownMonster = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser);
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

    public static void attackDirect(Phase currentPhase) {
        if (errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split(" ");
        if (!split[0].equals("Monster")) {
            GameMatView.showInput("you can’t attack with this card");
            return;
        }
        if (!errorOfWrongPhase("attackDirect", currentPhase))
            return;
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
    }

    public static void activateSpellEffect(Phase currentPhase) {
        if (!errorOfNoCardSelected("own"))
            return;
        String[] split = selectedOwnCard.split(" ");
        if ((split[0].equals("Monster") || split[0].equals("Trap")) || (split[0].equals("Hand") && !Card.getCardsByName(split[1]).getCardModel().equals("Spell"))) {
            GameMatView.showInput("activate effect is only for spell cards");
            return;
        }
        if (!errorOfWrongPhase("activate", currentPhase))
            return;
        String spellIcon = SpellCard.getSpellCardByName(split[1]).getIcon();
        SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser);
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]), onlineUser);
        switch (split[0]) {
            case "Spell" -> {
                if (ownSpell.getMode().equals("O")) {
                    GameMatView.showInput("you have already activated this card");
                    return;
                }
                if (spellIcon.equals("Ritual"))
                    ritualSummon(ownSpell, handCard, currentPhase);
                else {
                    ownSpell.setMode("O");
                    chooseSpellEffectController(spellIcon, ownSpell);
                }
            }
            case "Field" -> {
                GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                ownGameMat.changeModeOfFieldCard("O");
                SpellEffect.fieldEffectController(split[1], onlineUser, rivalUser);
            }
            case "Hand" -> {
                if (spellIcon.equals("Field")) {
                    GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
                    if (!ownGameMat.getFieldZone().equals(""))
                        ownGameMat.addToGraveyard(ownGameMat.getFieldZone());
                    handCard.removeFromHandCard();
                    ownGameMat.addToFieldZone(split[1], "O");
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
                        chooseSpellEffectController(spellIcon, ownSpell);
                    }
                }
                GameMatView.showInput("spell activated");
            }
        }
        selectedOwnCard = "";
    }

    public static void chooseSpellEffectController(String spellIcon, SpellTrapZoneCard ownSpell) {
        switch (spellIcon) {
            case "Normal":
                GameMatView.showInput("I want to activate a Spell!\nSpell activated");
                if (SpellEffect.normalEffectController(ownSpell, onlineUser, rivalUser) == 1)
                    ownSpell.removeSpellTrapFromZone();
                break;
            case "Quick-play":
                if (!ownSpell.getIsSetInThisTurn()) {
                    if (SpellEffect.quickPlayEffectController(ownSpell, onlineUser, rivalUser) == 1) //check this one
                        ownSpell.removeSpellTrapFromZone();
                    break;
                }
            case "Equip":
                if (getAddressOfRelatedMonster(ownSpell) != 0)
                    SpellEffect.equipEffectController(ownSpell, onlineUser, rivalUser);
                else
                    ownSpell.removeSpellTrapFromZone();
                break;
        }
    }

    public static int getAddressOfRelatedMonster(SpellTrapZoneCard ownSpell) {
        String spellName = ownSpell.getSpellTrapName();
        GameMatView.showInput("Whose Monster do you want to equip? (own/rival)");
        String response = GameMatView.getCommand();
        while (!response.matches("own|rival")) {
            if (response.equals("cancel"))
                return 0;
            GameMatView.showInput("Please enter the answer correctly: (own/rival)");
            response = GameMatView.getCommand();
        }
        if (response.equals("own")) {
            while (true) {
                GameMatView.showInput("Please enter the address of one of your Monster to equip: ");
                response = GameMatView.getCommand();
                if (response.equals("cancel"))
                    return 0;
                MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser);
                if (!response.matches("[1-5]") || ownMonsterCard == null || ownMonsterCard.getMode().equals("DH"))
                    continue;
                String monsterType = ownMonsterCard.getMonsterType();
                if (spellName.equals("Sword of Dark Destruction"))
                    if (!monsterType.equals("Fiend") && !monsterType.equals("Spellcaster"))
                        continue;
                if (spellName.equals("Magnum Shield") && monsterType.equals("Warrior"))
                        break;
            }

            ownSpell.setRelatedMonsterAddress("own", Integer.parseInt(response));
        }
        else {
            while (true) {
                GameMatView.showInput("Please enter the address of one of the rival Monster to equip: ");
                response = GameMatView.getCommand();
                if (response.equals("cancel"))
                    return 0;
                MonsterZoneCard rivalMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser);
                if (!response.matches("[1-5]") ||rivalMonsterCard == null || rivalMonsterCard.getMode().equals("DH"))
                    continue;
                String monsterType = rivalMonsterCard.getMonsterType();
                if (spellName.equals("Sword of Dark Destruction"))
                    if (!monsterType.equals("Fiend") && !monsterType.equals("Spellcaster"))
                        continue;
                if (spellName.equals("Magnum Shield") && monsterType.equals("Warrior"))
                    break;
            }
            ownSpell.setRelatedMonsterAddress("rival", Integer.parseInt(response));
        }
        return 1;
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
            }
            else
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

    public static void changePhase(Phase currentPhase) {
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        Player player = Player.getPlayerByName(onlineUser);
        switch (currentPhase.name()) {
            case "Draw_Phase" -> {
                GameMatView.showInput("phase: " + Phase.Standby_Phase);
                playerGameMat.setPhase(Phase.Standby_Phase);
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
                    changePhase(currentPhase);
                }
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
                player = Player.getPlayerByName(onlineUser);
                if (player.getNumberOfMainDeckCards() == 0)
                    endGame("noCard", onlineUser);
                else if (player.getCanDrawCard()) {
                    String cardName = player.drawCard(false);
                    GameMatView.showInput("new card added to the hand : " + cardName);
                    new HandCardZone(onlineUser, cardName);
                }
                int address = MonsterZoneCard.getAddressByMonsterName(onlineUser, "Herald of Creation");
                if (address != 0) {
                    MonsterEffect.heraldOfCreation(MonsterZoneCard.getMonsterCardByAddress(address, onlineUser) , onlineUser);
                }
            }
        }
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
        }
        else {
            winnerUsername = MainMenuController.username2;
            loserUsername = MainMenuController.username;
        }
        Player winnerPlayer = Player.getPlayerByName(winner);
        Player loserPlayer = Player.getPlayerByName(loser);
        if (Player.isOneRound) {
            GameMatView.showInput("The Duel is Over!");
            UserModel.getUserByUsername(winnerUsername).changeUserCoin(1000 + winnerPlayer.getLifePoint());
            UserModel.getUserByUsername(loserUsername).changeUserCoin(100);
            if (reason.equals("surrender")) {

            }
            else if (reason.equals("noCard")) {

            }
            else if (reason.equals("lp")) {

            }
            else if (reason.equals("effect")) {

            }
        }
        else {
            UserModel.getUserByUsername(winnerUsername).changeUserCoin(3000 + 3 * winnerPlayer.getMaxLifePoints());
            UserModel.getUserByUsername(loserUsername).changeUserCoin(300);
        }
//        winnerPlayer.startNewGame();
//        loserPlayer.startNewGame();


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

    public static void showSelectedCard() {
        if (errorOfNoCardSelected("own")) {
            String[] split = selectedOwnCard.split(" ");
            String cardModel = Card.getCardsByName(split[1]).getCardModel();
            if (cardModel.equals("Monster"))
                GameMatView.showInput(MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
            else if (cardModel.equals("Spell"))
                GameMatView.showInput(SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(split[2]), onlineUser).toString());
        }
    }

}
