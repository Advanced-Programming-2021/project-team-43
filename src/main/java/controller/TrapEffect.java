package main.java.controller;

import main.java.model.Card;
import main.java.model.*;
import main.java.model.Player;
import main.java.model.SpellTrapZoneCard;

import java.util.HashMap;
import java.util.Map;


//////////////////////////////////////////////////////////////////////////////////////////////
public class TrapEffect {
    public static void trapEffectController(int addressOfTrapCard, String rival, int addressOfSummonCard, String player1,
                                            boolean summonMine) {

        if (SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, player1).getSpellTrapName().equals("Mirror Force")) {
            mirrorForce(rival,player1);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, player1).getSpellTrapName().equals("Torrential Tribute")) {
            torrentialTribute(rival, player1);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, player1).getSpellTrapName().equals("Time Seal")) {
            timeSeal(rival);
        }

        if (SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, player1).getSpellTrapName().equals("Solemn Warning")) {
            solemnWarning(player1, addressOfSummonCard, summonMine, rival);
        }
        if (SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, player1).getSpellTrapName().equals("Magic Jammer")) {
            magicJammer(player1, addressOfSummonCard, summonMine, rival);
        }
    }

    public static void mirrorForce(String rival, String onlineUser) {//completed!
        if (!ringOfDefenseEffect(rival, onlineUser)) {
            Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rival);
            Integer[] addressOfRivalsMonsters = rivalsMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < rivalsMonster.size(); i++) {
                if (MonsterZoneCard.getMonsterCardByAddress(addressOfRivalsMonsters[i], rival).getMode().equals("OO")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfRivalsMonsters[i], rival).removeMonsterFromZone();
                }
            }
        }
    }

    private static boolean ringOfDefenseEffect(String rival, String onlineUser) {
        Map<Integer, SpellTrapZoneCard> spellTraps = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rival);
        Integer[] keys = spellTraps.keySet().toArray(new Integer[0]);
        int counter = 0;
        for (Integer key : keys) {
            if (SpellTrapZoneCard.getSpellCardByAddress(key, rival).getSpellTrapName().equals("Ring of Defense") &&
                    SpellTrapZoneCard.getSpellCardByAddress(key, rival).getMode().equals("O")) {
                counter++;
            }
        }
        spellTraps = SpellTrapZoneCard.getAllSpellTrapByPlayerName(onlineUser);
        keys = spellTraps.keySet().toArray(new Integer[0]);
        for (Integer key : keys) {
            if (SpellTrapZoneCard.getSpellCardByAddress(key, onlineUser).getSpellTrapName().equals("Ring of Defense") &&
                    SpellTrapZoneCard.getSpellCardByAddress(key, onlineUser).getMode().equals("O")) {
                counter++;
            }
        }
        return counter != 0;
    }


    public static void torrentialTribute(String rival, String player1) {//kamel3
        if (!ringOfDefenseEffect(rival, player1)) {
            Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rival);
            Integer[] monsterNames = rivalsMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < rivalsMonster.size(); i++) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], rival).removeMonsterFromZone();
            }
            Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(player1);
            Integer[] ownMonsterNames = ownMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < ownMonster.size(); i++) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).removeMonsterFromZone();
            }
        }
    }

    public static void timeSeal(String rival) {//kamel1
        Player.getPlayerByName(rival).setCanDrawCard(false);
    }


    public static void solemnWarning(String player1, int addressOfSummonCard, boolean summonMine, String rival) {//kamel3
        if (!ringOfDefenseEffect(rival, player1)) {
            Player.getPlayerByName(player1).changeLifePoint(-2000);
            if (summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1).removeMonsterFromZone();
                }
//            if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1).getMonsterName()).getCardModel().equals("Spell") ||
//                    Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1).getMonsterName()).getCardModel().equals("Trap")) {
//                SpellTrapZoneCard.getSpellCardByAddress(addressOfSummonCard, player1).removeSpellTrapFromZone();
//            }
            }
            if (!summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).removeMonsterFromZone();
                }
//            if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Spell") ||
//                    Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Trap")) {
//                SpellTrapZoneCard.getSpellCardByAddress(addressOfSummonCard, rival).removeSpellTrapFromZone();
//            }
            }
        }
    }

    public static void magicJammer(String player1, int activateCardAddress, boolean activateMine, String rival) {//
        if (!ringOfDefenseEffect(rival, player1)) {
            if (activateMine) {
                SpellTrapZoneCard.getSpellCardByAddress(activateCardAddress, player1).removeSpellTrapFromZone();
            }
            if (!activateMine) {
                SpellTrapZoneCard.getSpellCardByAddress(activateCardAddress, rival).removeSpellTrapFromZone();
            }
        }
    }

}
