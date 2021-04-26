package controller;

import model.*;
import view.GameMatView;

import java.util.regex.*;



public class GameMatController {

    public static String selectedOwnCard;
    public static String selectedRivalCard;

    public static void findMatcher(String ownName, String rivalName) {
        String command = GameMatView.getCommand();

        while (true) {
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
                ShopView.showInput("invalid command");
                continue;
            }
            if (getMatcher(command,"next\\s+phase").find()) {
                changePhase();
                continue;
            }
            if (getMatcher(command,"summon").find()) {
                summon();
                continue;
            }
            if (getMatcher(command,"set").find()) {
                setMonster();
                continue;
            }
            if (getMatcher(command,"set\\s+--position\\s+(attack|defense)").find()) {
                changeMonsterPosition(getMatcher(command,"set\\s+--position\\s+(attack|defense)").group(1));
                continue;
            }
            if (getMatcher(command,"flip-summon").find()) {
                flipSummon();
                continue;
            }
            if (getMatcher(command,"menu exit").find()) {
                break;
            }
            ShopView.showInput("invalid command");
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }


    public static void selectMonsterCard(int address, boolean isOwnMonsterCard) {
        if (address < 1 || address > 5)
            GameMatView.showInput("invalid selection");
        else if (GameMatModel.getGameMatByNickname("").getMonsterNameByAddress(address) == null)
            GameMatView.showInput("no card found in the given position");
        else {
            GameMatView.showInput("card selected");
            if (isOwnMonsterCard) {
                MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(address, "online user");
                selectedOwnCard = "monster " + monsterCard.getMonsterName() + " " + address;
                //model.GameMatModel.getGameMatByNickname("").setOwnMonsterZoneSelected(address);
                monsterCard.setIsSelected(true);
            }
            else {
                MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(address, "rival name");
                selectedRivalCard = "monster " + monsterCard.getMonsterName() + " " + address;
                //model.GameMatModel.getGameMatByNickname("").setRivalMonsterZoneSelected(address);
                monsterCard.setIsSelected(true);
            }
        }

    }

    public static void selectSpellCard(int address, boolean isOwnSpellCard) {
        if (address < 1 || address > 5)
            GameMatView.showInput("invalid selection");
        else if (GameMatModel.getGameMatByNickname("").getSpellNameByAddress(address) == null)
            GameMatView.showInput("no card found in the given position");
        else {
            GameMatView.showInput("card selected");
            if (isOwnSpellCard) {
                SpellZoneCard spellCard = SpellZoneCard.getSpellCardByAddress(address, "onlineuser");
                selectedOwnCard = "spell " + spellCard.getSpellName() + " " + address;
                //model.GameMatModel.getGameMatByNickname("").setOwnSpellZoneSelected(address);
                spellCard.setIsSelected(true);
            }
            else {
                SpellZoneCard spellCard = SpellZoneCard.getSpellCardByAddress(address, "rivalname");
                selectedRivalCard = "spell " + spellCard.getSpellName() + " " + address;
                //model.GameMatModel.getGameMatByNickname("").setRivalSpellZoneSelected(address);
                spellCard.setIsSelected(true);
            }
        }
    }

    public static void selectFieldCard(boolean isOwnField) {
        if (!GameMatModel.getGameMatByNickname("").getFieldZone().equals(""))
            GameMatView.showInput("invalid selection");
        else {
            GameMatView.showInput("card selected");
            if (isOwnField) {
                selectedOwnCard = "myField";
                GameMatModel.getGameMatByNickname("").setOwnFieldZoneSelected();
            }
            else {
                selectedOwnCard = "rivalField";

            }
        }
    }

    public static void selectHandCard(int address) {
        if (address < 1 || address > HandCardZone.getNumberOfHandCard())
            GameMatView.showInput("invalid selection");
        else {
            GameMatView.showInput("card selected");
            HandCardZone handCard = HandCardZone.getHandCardByAddress(address, "onlineuser");
            selectedOwnCard = "hand " + handCard.getCardName() + " " + address;
            //model.GameMatModel.getGameMatByNickname("").setHandCardSelected(address);
            handCard.setIsSelected(true);
        }
    }

    public static void selectDelete() {
        if (selectedRivalCard.equals(null)) {
            if (selectedOwnCard.equals(null))
                GameMatView.showInput("no card is selected yet");
            else {
                selectedOwnCard = "";
                GameMatView.showInput("card deselected");
                String[] split = selectedOwnCard.split(" ");
                if (selectedOwnCard.startsWith("monster"))
                    MonsterZoneCard.getMonsterCardByName(split[1], "onlineuser").setIsSelected(false);
                else if (selectedOwnCard.startsWith("spell"))
                    SpellZoneCard.getSpellCardByName(split[1], "onlineuser").setIsSelected(false);
                else if (selectedOwnCard.startsWith("hand"))
                    HandCardZone.getHandCardByName(split[1], "onlineuser").setIsSelected(false);

            }
        }
        else {
            selectedRivalCard = "";
            GameMatView.showInput("card deselected");
            String[] split = selectedRivalCard.split(" ");
            if (selectedRivalCard.startsWith("monster"))
                MonsterZoneCard.getMonsterCardByName(split[1], "rivalname").setIsSelected(false);
            else if (selectedRivalCard.startsWith("spell"))
                SpellZoneCard.getSpellCardByName(split[1], "rivalname").setIsSelected(false);
        }

    }


    public void addCardToHand(String cardName) {

    }


    public static void changePhase() {
        GameMatModel playerGameMat = GameMatModel.getGameMatByNickname("onlineuser");
        Phase currentPhase = playerGameMat.getPhase();
        if (currentPhase.name().equals("Draw_Phase")) {
            GameMatView.showInput("phase: " + Phase.Standby_Phase);
            playerGameMat.setPhase(Phase.Standby_Phase);
        }
        else if (currentPhase.name().equals("Standby_Phase")) {
            GameMatView.showInput("phase: " + Phase.Main_Phase1);
            playerGameMat.setPhase(Phase.Main_Phase1);
        }
        else if (currentPhase.name().equals("Main_Phase1")) {
            GameMatView.showInput("phase: " + Phase.Battle_Phase);
            playerGameMat.setPhase(Phase.Battle_Phase);
        }
        else if (currentPhase.name().equals("Battle_Phase")) {
            GameMatView.showInput("phase: " + Phase.Main_Phase2);
            playerGameMat.setPhase(Phase.Main_Phase2);
        }
        else if (currentPhase.name().equals("Main_Phase2")) {
            GameMatView.showInput("phase: " + Phase.End_Phase);
            playerGameMat.setPhase(Phase.End_Phase);
        }
        else if (currentPhase.name().equals("End_Phase")) {
            GameMatView.showInput("phase: " + Phase.Draw_Phase);
            GameMatView.showInput("its " + "rivalname///////////" + "’s turn");
            playerGameMat.setPhase(Phase.Draw_Phase);
        }
    }


    public static void summon() {
        Phase currentPhase = GameMatModel.getGameMatByNickname("onlineuser").getPhase();
        if (selectedOwnCard.equals("")) {
            GameMatView.showInput("no card is selected yet");
            return;
        }
        String[] split = selectedOwnCard.split(" ");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]),"onlineuser");
        if (!selectedOwnCard.startsWith("hand") || !handCard.getKind().equals("monster"))
            GameMatView.showInput("you can’t summon this card");
        else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2")) {
            GameMatView.showInput("action not allowed in this phase");
        }
        else if (MonsterZoneCard.getNumberOfFullHouse() == 5) {
            GameMatView.showInput("monster card zone is full");
        }
        else if (Player.getPlayerByName("onlineuser").getIsYourMoveFinished()) {
            GameMatView.showInput("you already summoned/set on this turn");
        }
        else {
            ///should get level of card from monster name
            /*if () {
                view.GameMatView.showInput("there are not enough cards for tribute");
            }*/
            int whichHouse = MonsterZoneCard.getNumberOfFullHouse();
            new MonsterZoneCard("onlineuser", split[1], whichHouse + 1, "summon");
            GameMatView.showInput("summoned successfully");
        }
    }


    public static void setMonster() {
        Phase currentPhase = GameMatModel.getGameMatByNickname("onlineuser").getPhase();
        if (selectedOwnCard.equals("")) {
            GameMatView.showInput("no card is selected yet");
            return;
        }
        String[] split = selectedOwnCard.split(" ");
        HandCardZone handCard = HandCardZone.getHandCardByAddress(Integer.parseInt(split[2]),"onlineuser");
        if (!selectedOwnCard.startsWith("hand"))
            GameMatView.showInput("you can’t set this card");
        else if (handCard.getKind().equals("monster")) {
            if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
                GameMatView.showInput("you can’t do this action in this phase");
            else if (MonsterZoneCard.getNumberOfFullHouse() == 5)
                GameMatView.showInput("monster card zone is full");
            else if (Player.getPlayerByName("onlineuser").getIsYourMoveFinished())
                GameMatView.showInput("you already summoned/set on this turn");
            else {
                int whichHouse = MonsterZoneCard.getNumberOfFullHouse();
                new MonsterZoneCard("onlineuser", split[1], whichHouse + 1, "set");
                GameMatView.showInput("set successfully");
            }
        }



    }


    public static void changeMonsterPosition(String mode) {
        Phase currentPhase = GameMatModel.getGameMatByNickname("onlineuser").getPhase();
        if (selectedOwnCard.equals("")) {
            GameMatView.showInput("no card is selected yet");
            return;
        }
        String[] split = selectedOwnCard.split(" ");
        MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByName(split[1],"onlineuser");
        if (!split[0].equals("monster"))
            GameMatView.showInput("you can’t change this card position");
        else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
            GameMatView.showInput("you can’t do this action in this phase");
        else if (mode.equals("attack")) {
            if (!monsterCard.getMode().equals("defend"))
                GameMatView.showInput("this card is already in the wanted position");
        }
        else if (mode.equals("defense")) {
            if (!monsterCard.getMode().equals("summon"))
                GameMatView.showInput("this card is already in the wanted position");
        }
        else if (monsterCard.getHaveChangedPositionThisTurn())
            GameMatView.showInput("you already changed this card position in this turn");
        else {
            GameMatView.showInput("monster card position changed successfully");
            monsterCard.setHaveChangedPositionThisTurn(true);
            if (mode.equals("attack"))
                monsterCard.setMode("summon");
            else
                monsterCard.setMode("defend");
        }

    }


    public static void flipSummon() {
        Phase currentPhase = GameMatModel.getGameMatByNickname("onlineuser").getPhase();
        if (selectedOwnCard.equals("")) {
            GameMatView.showInput("no card is selected yet");
            return;
        }
        String[] split = selectedOwnCard.split(" ");
        MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByName(split[1],"onlineuser");
        if (!split[0].equals("monster"))
            GameMatView.showInput("you can’t change this card position");
        else if (!currentPhase.name().equals("Main_Phase1") && !currentPhase.name().equals("Main_Phase2"))
            GameMatView.showInput("you can’t do this action in this phase");
        else if (!monsterCard.getMode().equals("set") || monsterCard.getHaveChangedPositionThisTurn()) {
            GameMatView.showInput("you can’t flip summon this card");
        }
        else {
            monsterCard.setMode("summon");
            GameMatView.showInput("flip summoned successfully");
        }
    }


    public void attack(int rivalCardAddress) {

    }


    public void attackDirect() {

    }


    public void activateSpellEffect() {

    }


    public void setSpell() {

    }


    public void setTrap() {

    }


    public void selfActivateEffectAndTrap() {

    }


    public void ritualSummon() {

    }


    public void specialSummon() {

    }


    public void showGraveyard() {

    }



    public void showOneCard(String cardName) {

    }


    public void surrender() {

    }

}
