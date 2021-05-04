package main.java.controller;

import main.java.model.*;

import java.util.HashMap;
import java.util.Map;

//public class MonsterEffect {
//    public static void monsterEffectController( int rivalsAddress, String player1, String rival, int victim1Address, int victim2Address, int victim3Address
//            , String kindOfSummonOfBeastCard, int ownAddress) {
//
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Command knight")) {
//            commandKnight(player1, rival, ownAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Yomi Ship")) {
//            yomiShip(rival, rivalsAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Suijin")) {
//            suijin(player1, rival, ownAddress, rivalsAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Man-Eater Bug")) {
//            manEaterBug(victim1Address, rival);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Gate Guardian")) {
//            gateGuardian(victim1Address, victim2Address, victim3Address, rival);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Scanner")) {
//            scanner(player1, ownAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Marshmallon")) {
//            marshmallon(rival, player1, ownAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Beast King Barbaros")) {
//            beastKingBarbaros(rival, player1, kindOfSummonOfBeastCard, victim1Address, victim2Address, victim3Address, ownAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("The Calculator")) {
//            theCalculator(player1, ownAddress);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Mirage Dragon")) {
//            mirageDragon(player1, ownAddress, rival);
//        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMonsterName().equals("Exploder Dragon")) {
//            exploderDragon(rivalsAddress, rival);
//        }
//    }
//
//    public static void commandKnight(String player1, String rival, int ownAddress) {//kamel
//        int counter = 0;
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
//                MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO")) {
//            Map<Integer, MonsterZoneCard> monsters;
//            monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
//            Integer[] keys;
//            Map<Integer, MonsterZoneCard> rivalsMonsters;
//            rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
//            Integer[] rivalsKeys;
//            keys = monsters.keySet().toArray(new Integer[0]);
//            rivalsKeys = rivalsMonsters.keySet().toArray(new Integer[0]);
//            for (Integer key : keys) {
//                MonsterZoneCard.getMonsterCardByAddress(key, player1).changeAttack(400);
//                if (!MonsterZoneCard.getMonsterCardByAddress(key, player1).getMonsterName().equals("Command knight")) {
//                    counter++;
//                }
//            }
//            if (counter != 0) {
//                MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).setCanAttackToThisMonster(false);
//            }
//            for (int i = 0; i < rivalsMonsters.size(); i++) {
//                MonsterZoneCard.getMonsterCardByAddress(rivalsKeys[i], rival).changeAttack(400);
//            }
//        }
//    }
//
//    public static void yomiShip(String rival, int rivalsAddress) {//kamel
//        MonsterZoneCard.getMonsterCardByAddress(rivalsAddress, rival).removeMonsterFromZone();
//    }
//
//    public static void suijin(String player1, String rival, int ownAddress, int rivalsAddress) {
//        if (!MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getIsUsed &&
//                (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
//                        MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO"))) {
//            MonsterZoneCard.getMonsterCardByAddress(rivalsAddress, rival).setAttack(0);
//        }
//    }
//
//
//    public static void manEaterBug(int victimAddress, String rival) {//kamel
//        MonsterZoneCard.getMonsterCardByAddress(victimAddress, rival).removeMonsterFromZone();
//    }
//
//    public static void gateGuardian(int victim1Address, int victim2Address, int victim3Address, String rival) {//kamel
//        MonsterZoneCard.getMonsterCardByAddress(victim1Address, rival).removeMonsterFromZone();
//        MonsterZoneCard.getMonsterCardByAddress(victim2Address, rival).removeMonsterFromZone();
//        MonsterZoneCard.getMonsterCardByAddress(victim3Address, rival).removeMonsterFromZone();
//    }
//
//    public static void scanner(String player1, int address) {
//     //////////////////////////////////////////////////////////////////
//    }
//
//    public static void marshmallon(String rivalName, String player1, int ownAddress) {//kamel
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DH")) {
//            Player.getPlayerByName(rivalName).changeLifePoint(-1000);
//        }
//    }
//
//    public static void beastKingBarbaros(String rival, String player1, String kindOfSummon, int victim1Address, int victim2Address, int victim3Address, int ownAddress) {//kamel
//        if (kindOfSummon.equals("Normal")) {
//            MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).setAttack(1900);
//        }
//        if (kindOfSummon.equals("notNormal")) {
//            MonsterZoneCard.getMonsterCardByAddress(victim1Address, player1).removeMonsterFromZone();
//            MonsterZoneCard.getMonsterCardByAddress(victim2Address, player1).removeMonsterFromZone();
//            MonsterZoneCard.getMonsterCardByAddress(victim3Address, player1).removeMonsterFromZone();
//            Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
//            Map<Integer, SpellTrapZoneCard> spellsTraps = SpellTrapZoneCard.getAllSpellsByPlayerName(rival);
//            Integer[] monster = monsters.keySet().toArray(new Integer[0]);
//            Integer[] spellTraps = spellsTraps.keySet().toArray(new Integer[0]);
//            for (int s : monster) {
//                MonsterZoneCard.getMonsterCardByAddress(s, rival).removeMonsterFromZone();
//            }
//            for (int s : spellTraps) {
//                SpellTrapZoneCard.getSpellCardByAddress(s, rival).removeSpellTrapFromZone();
//            }
//        }
//    }
//
//    public static void theCalculator(String player1, int ownAddress) {//kamel
//        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
//        Integer[] monster = monsters.keySet().toArray(new Integer[0]);
//        int attack = 0;
//        for (int s : monster) {
//            if (MonsterZoneCard.getMonsterCardByAddress(s, player1).getMode().equals("OO") ||
//                    MonsterZoneCard.getMonsterCardByAddress(s, player1).getMode().equals("DO")) {
//                attack += MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(s, player1).getMonsterName()).getLevel();
//            }
//        }
//        attack *= 300;
//        MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).setAttack(attack);
//    }
//
//    public static void mirageDragon(String player1, int ownAddress, String rival) {//kamel
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO") ||
//                MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO")) {
//            Player.getPlayerByName(rival).setCanUseTrap(false);
//        }
//    }
//
//    public static void exploderDragon(int addressOfAttacker, String rival) {//kamel
//        MonsterZoneCard.getMonsterCardByAddress(addressOfAttacker, rival).removeMonsterFromZone();
//    }
//}
//
