package controller;
import model.*;
import view.GameMatView;
import java.util.*;


public class MonsterEffect {

    private static String response;
    private static int chosenAddress;

    public static void monsterEffectController(int ownAddress, int rivalsAddress, String player1, String rival, String kindOfSummonOfBeastCard, String mode, boolean isAttacked) {
        MonsterZoneCard ownMonsterCard = MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1);
        String ownMonsterName = ownMonsterCard.getMonsterName();
        boolean isEffectUsed = MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getIsEffectUsed();

        if (ownMonsterName.equals("Command knight"))
            commandKnight(player1, rival, ownAddress, ownMonsterCard);
        else if (ownMonsterName.equals("Yomi Ship"))
            yomiShip(rival, rivalsAddress, ownMonsterCard);
        else if (ownMonsterName.equals("Suijin")) {
            suijin(player1, rival, ownAddress, rivalsAddress);
        }
        else if (ownMonsterName.equals("Man-Eater Bug")) {
            if (!ownMonsterCard.getIsEffectUsed())
                manEaterBug(ownMonsterCard, getChosenAddress("Man-Eater Bug", rival, player1), rival);
        }
        else if (ownMonsterName.equals("Gate Guardian")) {
            ///gateGuardian(rival);
        }
        else if (ownMonsterName.equals("Marshmallon")) {
            marshmallon(rival, ownMonsterCard, isAttacked);
        }
        else if (ownMonsterName.equals("Beast King Barbaros")) {
            if (!ownMonsterCard.getIsEffectUsed()) {
                chosenAddress = getChosenAddress(ownMonsterName, rival, player1);
                if (chosenAddress == 0)
                    beastKingBarbaros(ownMonsterCard, rival, player1, "Normal", -1, -1, -1, ownAddress);
                else {
                    int[] victims = new int[2];
                    victims[0] = chosenAddress % 10;
                    chosenAddress = (chosenAddress - victims[0]) / 10;
                    victims[1] = chosenAddress % 10;
                    chosenAddress = (chosenAddress - victims[1]) / 10;
                    beastKingBarbaros(ownMonsterCard, rival, player1, "NotNormal", victims[0], victims[1], chosenAddress, ownAddress);
                }
            }
        }
        else if (ownMonsterName.equals("Texchanger")) {
            texchanger();
        }
        else if (ownMonsterName.equals("The Calculator")) {
            theCalculator(player1, ownAddress);
        }
        else if (ownMonsterName.equals("Mirage Dragon")) {
            mirageDragon(ownMonsterCard, rival);
        }
        else if (ownMonsterName.equals("Herald of Creation")) {
            heraldOfCreation();
        }
        else if (ownMonsterName.equals("Exploder Dragon")) {
            exploderDragon(ownMonsterCard, rivalsAddress, rival);
        }
    }

    public static void commandKnight(String player1, String rival, int ownAddress, MonsterZoneCard ownMonsterCard) {
        if (ownMonsterCard == null) {
            //az attack hame oon monsterai ke 400 ezafeh kardi 400 kam kon
        }
        else {
            if (ownMonsterCard.getMode().equals("OO") || ownMonsterCard.getMode().equals("DO")) {
                if (!ownMonsterCard.getIsEffectUsed()) {
                    //inja faghat be atack hame monstera 400 ezafeh kon
                    int counter = 0;
                    if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
                            MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO")) {
                        Map<Integer, MonsterZoneCard> monsters;
                        monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
                        Integer[] keys;
                        Map<Integer, MonsterZoneCard> rivalsMonsters;
                        rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
                        Integer[] rivalsKeys;
                        keys = monsters.keySet().toArray(new Integer[0]);
                        rivalsKeys = rivalsMonsters.keySet().toArray(new Integer[0]);
                        for (Integer key : keys) {
                            MonsterZoneCard.getMonsterCardByAddress(key, player1).changeAttack(400);
                            if (!MonsterZoneCard.getMonsterCardByAddress(key, player1).getMonsterName().equals("Command knight")) {
                                counter++;
                            }
                        }
                        if (counter != 0) {
                            MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).setCanAttackToThisMonster(false);
                        }
                        for (int i = 0; i < rivalsMonsters.size(); i++) {
                            MonsterZoneCard.getMonsterCardByAddress(rivalsKeys[i], rival).changeAttack(400);
                        }
                    }
                    ownMonsterCard.setIsEffectUsed(true);
                }
                ownMonsterCard.setCanAttackToThisMonster(MonsterZoneCard.getNumberOfFullHouse(player1) == 1);
            }
        }
    }

    public static void yomiShip(String rival, int rivalsAddress, MonsterZoneCard ownMonsterCard) {
        if (ownMonsterCard == null) {
            MonsterZoneCard.getMonsterCardByAddress(rivalsAddress, rival).removeMonsterFromZone();
        }
    }

    public static void suijin(String player1, String rival, int ownAddress, int rivalsAddress) {//??????
        if (!MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getIsEffectUsed() &&
                (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
                        MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO"))) {
            MonsterZoneCard.getMonsterCardByAddress(rivalsAddress, rival).setAttack(0);
        }
    }


    public static void manEaterBug(MonsterZoneCard ownMonsterCard, int victimAddress, String rival) {
        if (ownMonsterCard.getMode().equals("OO") || ownMonsterCard.getMode().equals("DO")) {
            MonsterZoneCard.getAllMonstersByPlayerName(rival).get(victimAddress).removeMonsterFromZone();
            ownMonsterCard.setIsEffectUsed(true);
        }
    }


    public static void gateGuardian(int victim1Address, int victim2Address, int victim3Address, String rival) {//?????
        MonsterZoneCard.getMonsterCardByAddress(victim1Address, rival).removeMonsterFromZone();
        MonsterZoneCard.getMonsterCardByAddress(victim2Address, rival).removeMonsterFromZone();
        MonsterZoneCard.getMonsterCardByAddress(victim3Address, rival).removeMonsterFromZone();
    }

//    public static void scanner(String player1, int address) {
//        //////////////////////////////////////////////////////////////////
//    }

    public static void marshmallon(String rival, MonsterZoneCard ownMonsterCard, boolean isAttacked) {///???
        if (ownMonsterCard.getMode().equals("DH") && isAttacked) {
            Player.getPlayerByName(rival).changeLifePoint(-1000);
        }
    }


    public static void beastKingBarbaros(MonsterZoneCard ownMonsterCard, String rival, String player1, String kindOfSummon, int victim1Address, int victim2Address, int victim3Address, int ownAddress) {
        if (kindOfSummon.equals("Normal"))
            ownMonsterCard.setAttack(1900);
        if (kindOfSummon.equals("NotNormal")) {
            MonsterZoneCard.getMonsterCardByAddress(victim1Address, player1).removeMonsterFromZone();
            MonsterZoneCard.getMonsterCardByAddress(victim2Address, player1).removeMonsterFromZone();
            MonsterZoneCard.getMonsterCardByAddress(victim3Address, player1).removeMonsterFromZone();
            Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
            Map<Integer, SpellTrapZoneCard> spellsTraps = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rival);
            Integer[] monster = monsters.keySet().toArray(new Integer[0]);
            Integer[] spellTraps = spellsTraps.keySet().toArray(new Integer[0]);
            for (int s : monster) {
                MonsterZoneCard.getMonsterCardByAddress(s, rival).removeMonsterFromZone();
            }
            for (int s : spellTraps) {
                SpellTrapZoneCard.getSpellCardByAddress(s, rival).removeSpellTrapFromZone();
            }
        }
        ownMonsterCard.setIsEffectUsed(true);
    }

    public static void texchanger() {//?????

    }

    public static void theCalculator(String player1, int ownAddress) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monster = monsters.keySet().toArray(new Integer[0]);
        int attack = 0;
        for (int s : monster) {
            if (MonsterZoneCard.getMonsterCardByAddress(s, player1).getMode().equals("OO") ||
                    MonsterZoneCard.getMonsterCardByAddress(s, player1).getMode().equals("DO")) {
                attack += MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(s, player1).getMonsterName()).getLevel();
            }
        }
        attack *= 300;
        MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).setAttack(attack);
    }

    public static void mirageDragon(MonsterZoneCard ownMonsterCard, String rival) {
        if (ownMonsterCard == null || ownMonsterCard.getMode().equals("DH"))
            Player.getPlayerByName(rival).setCanUseTrap(true);
        else if (!ownMonsterCard.getIsEffectUsed()) {
            if (ownMonsterCard.getMode().equals("OO") || ownMonsterCard.getMode().equals("DO")) {
                Player.getPlayerByName(rival).setCanUseTrap(false);
                ownMonsterCard.setIsEffectUsed(true);
            }
        }
    }

    public static void heraldOfCreation() {//???

    }

    public static void exploderDragon(MonsterZoneCard ownMonsterCard, int addressOfAttacker, String rival) {
        if (ownMonsterCard == null) {
            MonsterZoneCard.getMonsterCardByAddress(addressOfAttacker, rival).removeMonsterFromZone();
        }
    }

    public static int getChosenAddress(String monsterName, String rival, String player1) {
        switch (monsterName) {
            case "Man-Eater Bug":
                GameMatView.showInput("Please enter the address of a rival monster to destroy: ");
                response = GameMatView.getCommand();
                while (!response.matches("[1-5]") && MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rival) == null) {
                    GameMatView.showInput("Please enter the correct address of a monster to destroy:");
                    response = GameMatView.getCommand();
                }
                return Integer.parseInt(response);
            case "Gate Guardian":

                break;
            case "Beast King Barbaros":
                GameMatView.showInput("Do you want to summon Beast King Barbaros without tributing? (yes/no)");
                response = GameMatView.getCommand();
                if (response.equals("yes")) {
                    GameMatController.addToMonsterZoneCard("Beast King Barbaros", "OO");
                    GameMatView.showInput("Summoned successfully");
                    return 0;
                } else {
                    if (MonsterZoneCard.getNumberOfFullHouse(player1) < 3) {
                        GameMatView.showInput("There are not enough cards for tribute");
                        //deselect card selectedOwncard = ""
                    } else {
                        int result = 0;
                        GameMatView.showInput("Please enter address of 3 monsters to tribute: (each line, one Address)");
                        response = GameMatView.getCommand();
                        if (!response.matches("[1-5]") && MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), player1) == null) {
                            GameMatView.showInput("Please enter correct address of monster one to tribute:");
                            response = GameMatView.getCommand();
                        }
                        result += Integer.parseInt(response) * 10;
                        response = GameMatView.getCommand();
                        if (!response.matches("[1-5]") && MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), player1) == null) {
                            GameMatView.showInput("Please enter correct address of monster two to tribute:");
                            response = GameMatView.getCommand();
                        }
                        result += Integer.parseInt(response) * 10;
                        response = GameMatView.getCommand();
                        if (!response.matches("[1-5]") && MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), player1) == null) {
                            GameMatView.showInput("Please enter correct address of monster three to tribute: ");
                            response = GameMatView.getCommand();
                        }
                        result += Integer.parseInt(response) * 10;
                        return result / 10;
                    }
                }
                break;
        }
        return 0;
    }
}

