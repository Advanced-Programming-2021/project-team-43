package main.java.controller;

import main.java.model.Card;
import main.java.model.MonsterZoneCard;
import main.java.model.Player;
import main.java.model.SpellZoneCard;
import java.util.HashMap;

//public class TrapEffect {
//    public static void trapEffectController(String nameOfTrapCard, String rival, String nameOfSummonCard,String player1,
//                                            boolean summonMine) {
//        if (nameOfTrapCard.equals("Mirror Force")) {
//            mirrorForce(rival);
//        }
//        if (nameOfTrapCard.equals("Torrential Tribute")) {
//            torrentialTribute(rival,player1);
//        }
//        if (nameOfTrapCard.equals("Time Seal")) {
//            timeSeal(rival);
//        }
//
//        if (nameOfTrapCard.equals("Solemn Warning")) {
//            solemnWarning(player1,nameOfSummonCard,summonMine,rival);
//        }
//        if (nameOfTrapCard.equals("Magic Jammer")) {
//            magicJammer(player1,nameOfSummonCard,summonMine,rival);
//        }
//    }
//
//    public static void mirrorForce(String rival) {
//        HashMap<String, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getMonsters(rival);
//        String[] nameOfRivalsMonsters = rivalsMonster.keySet().toArray(new String[0]);
//        for (int i = 0; i < rivalsMonster.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByName(nameOfRivalsMonsters[i], rival).getMode().equals("defend")) {
//                MonsterZoneCard.deleteCard(nameOfRivalsMonsters[i], rival);
//            }
//        }
//    }
//
//
//    public static void torrentialTribute(String rival, String player1) {
//
//        HashMap<String, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getMonsters(rival);
//        String[] monsterNames = rivalsMonster.keySet().toArray(new String[0]);
//        for (int i = 0; i < rivalsMonster.size(); i++) {
//            MonsterZoneCard.deleteCard(monsterNames[i], rival);
//        }
//        HashMap<String, MonsterZoneCard> ownMonster = MonsterZoneCard.getMonsters(player1);
//        String[] ownMonsterNames = ownMonster.keySet().toArray(new String[0]);
//        for (int i = 0; i < ownMonster.size(); i++) {
//            MonsterZoneCard.deleteCard(ownMonsterNames[i], player1);
//        }
//    }
//
//    public static void timeSeal(String rival) {
//        Player.getPlayerByName(rival).setPermit(false);
//    }
//
//
//    public static void solemnWarning(String player1, String nameOfSummonCard, boolean summonMine, String rival) {
//        Player.getPlayerByName(player1).changeLifePoint(-2000);
//        if (Card.getCardsByName(nameOfSummonCard).getCardModel().equals("Monster")) {
//            if (summonMine) {
//                MonsterZoneCard.deleteCard(nameOfSummonCard, player1);
//            }
//            MonsterZoneCard.deleteCard(nameOfSummonCard, rival);
//        }
//        if (Card.getCardsByName(nameOfSummonCard).getCardModel().equals("Spell")) {
//            if (summonMine) {
//                SpellZoneCard.deleteCard(nameOfSummonCard, player1);
//            }
//            SpellZoneCard.deleteCard(nameOfSummonCard, rival);
//        }
//        if (Card.getCardsByName(nameOfSummonCard).getCardModel().equals("Trap")) {
//            if (summonMine) {
//                TrapZoneCard.deleteCard(nameOfSummonCard, player1);
//            }
//            TrapZoneCard.deleteCard(nameOfSummonCard, rival);
//        }
//
//    }
//
//    public static void magicJammer(String player1, String activateCardName, boolean activateMine, String rival) {
//        if (activateMine) {
//            SpellZoneCard.deleteCard(activateCardName, player1);
//        }
//        SpellZoneCard.deleteCard(activateCardName, rival);
//    }
//
//}
