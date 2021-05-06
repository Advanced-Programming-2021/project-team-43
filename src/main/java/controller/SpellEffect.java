package main.java.controller;
import main.java.model.*;
import java.util.ArrayList;
import java.util.Map;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class SpellEffect {
    public static void spellEffectController(int spellCardAddress, String player1, String rival, int victim1Address, int victim2Address,
                                             boolean deleteMyCard1,boolean deleteMyCard2) {

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Raigeki")) {
            raigeki(rival,player1,spellCardAddress);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Harpie’s Feather Duster")) {
            harpieFeatherDuster(rival,player1,spellCardAddress);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Swords of Revealing Light")) {
            swordsOfRevealingLight(rival, spellCardAddress, player1);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Dark Hole")) {
            darkHole(rival, player1,spellCardAddress);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Spell Absorption")) {
            spellAbsorption(player1,spellCardAddress);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Messenger of peace")) {
            messengerOfPeace(player1, rival);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Twin Twisters")) {
            twinTwisters(victim1Address, victim2Address, rival, deleteMyCard1, player1,deleteMyCard2);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Mystical space typhoon")) {
            mysticalSpaceTyphoon(victim1Address, rival, deleteMyCard1, player1,deleteMyCard2);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Yami")) {
            yami(rival, player1);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Forest")) {
            forest(player1, rival);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Closed Forest")) {
            closedForest(player1, rival);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("UMIIRUKA")) {
            UMIIRUKA(player1, rival);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Sword of Dark Destruction")) {
            swordOfDarkDestruction(player1,spellCardAddress);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Black Pendant")) {
            blackPendant(player1,spellCardAddress);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("United We Stand")) {
            unitedWeStand(player1,spellCardAddress);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName().equals("Magnum Shield")) {
            magnumShield(player1,spellCardAddress);
        }

    }

    public static void raigeki(String rival,String player1,int ownAddress) {//kamel3
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rival).removeMonsterFromZone();
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    public static void harpieFeatherDuster(String rival,String player1,int ownAddress) {//kamel3
        Integer[] spell = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rival).removeSpellTrapFromZone();
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    public static void swordsOfRevealingLight(String rival, int ownAddress, String player1) {//Kamel3
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rival).setMode("DO");
        }
        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
                MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO")) {
            for (int key : keys) {
                MonsterZoneCard.getMonsterCardByAddress(key, rival).setCanAttack(false);
            }
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    public static void darkHole(String rival, String player1,int ownAddress) {//kamel3
        Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] monsterAddress = rivalsMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(monsterAddress[i], rival).removeMonsterFromZone();
        }
        Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterAddress = ownMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).removeMonsterFromZone();
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }


    public static void spellAbsorption(String player1,int ownAddress) {//kamel3
        Player.getPlayerByName(player1).changeLifePoint(500);
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    public static void messengerOfPeace(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).setCanAttack(false);
            }
        }

        Map<Integer, MonsterZoneCard> rivalMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterNames = rivalMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalMonsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).setCanAttack(false);
            }
        }
    }

    public static void twinTwisters(int victim1Address, int victim2Address, String rival, boolean mine1, String player1,boolean mine2) {//kamel3
        if (mine) {
            if (victim1Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim1Address, player1).removeSpellTrapFromZone();
            }
            if (victim2Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim2Address, player1).removeSpellTrapFromZone();
            }
        }
        if (!mine) {
            if (victim1Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim1Address, rival).removeSpellTrapFromZone();
            }
            if (victim2Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim2Address, rival).removeSpellTrapFromZone();
            }
        }
    }

    public static void mysticalSpaceTyphoon(int victimAddress, String rival, boolean mine1, String player1,boolean mine2) {//kamel3
        if (mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, player1);
        }
        if (!mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, rival);
        }
    }

    public static void yami(String rival, String player1) {//kamel3
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterAddress = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterAddress = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeDefend(-200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeDefend(-200);
            }
        }
    }

    public static void forest(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterNames = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).changeDefend(200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).changeDefend(200);
            }
        }
    }

    public static void closedForest(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        int increaseAttack = (GameMatModel.getGameMatByNickname(player1).getNumberOfDeadMonster() + GameMatModel.getGameMatByNickname(rival).getNumberOfDeadMonster()) * 100;
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast-Type")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeAttack(increaseAttack);
            }
        }
    }

    public static void UMIIRUKA(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeDefend(-400);
            }
        }
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalsMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).changeDefend(-400);
            }
        }
    }

    public static void swordOfDarkDestruction(String player1,int ownAddress) {//kamel3
        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
        for (Integer monster : monsters) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMonsterName()).getMonsterType().equals("Fiend")) {
                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(400);
                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(-200);
            }
        }
    }

    public static void blackPendant(String player1,int ownAddress) {//kamel3
        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
        for (Integer monster : monsters) {
            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(500);
        }
    }

    public static void unitedWeStand(String player1,int ownAddress) {//kamel3
        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
        Map<Integer, MonsterZoneCard> monstersZone = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] namesOfMonstersInZone = monstersZone.keySet().toArray(new Integer[0]);
        int counter = 0;
        for (int i = 0; i < monstersZone.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("DO") ||
                    MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("OO")) {
                counter++;
            }
        }
        int increaseAttackAndDefend = 800 * counter;
        for (Integer monster : monsters) {
            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(increaseAttackAndDefend);
            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(increaseAttackAndDefend);
        }
    }

    public static void magnumShield(String player1,int ownAddress) {//kamel3
        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
        for (Integer monster : monsters) {
            if (MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMode().equals("OO")) {
                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(
                        MonsterZoneCard.getMonsterCardByAddress(monster, player1).getDefend());
            }
            if (MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMode().equals("DO")) {
                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(
                        MonsterZoneCard.getMonsterCardByAddress(monster, player1).getAttack());
            }
        }
    }
}
