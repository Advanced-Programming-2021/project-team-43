package main.java.controller;

import main.java.model.*;

import java.util.ArrayList;
import java.util.HashMap;

//public class SpellEffect {
//    public static void spellEffectController(String spellCardName, String player1, String rival, String victim1, String victim2,
//                                             boolean deleteMyCard) {
//        if (spellCardName.equals("Raigeki")) {
//            raigeki(rival);
//        }
//        if (spellCardName.equals("Harpie’s Feather Duster")) {
//            harpieFeatherDuster(rival);
//        }
//
//        if (spellCardName.equals("Swords of Revealing Light")) {
//            swordsOfRevealingLight();
//        }
//        if (spellCardName.equals("Dark Hole")) {
//            darkHole(rival, player1);
//        }
//        if (spellCardName.equals("Spell Absorption")) {
//            spellAbsorption(player1);
//        }
//        if (spellCardName.equals("Messenger of peace")) {
//            messengerOfPeace(player1, rival);
//        }
//        if (spellCardName.equals("Twin Twisters")) {
//            twinTwisters(victim1, victim2, rival, deleteMyCard, player1);
//        }
//
//        if (spellCardName.equals("Mystical space typhoon")) {
//            mysticalSpaceTyphoon(victim1, rival, deleteMyCard, player1);
//        }
//        if (spellCardName.equals("Yami")) {
//            yami(rival, player1);
//        }
//        if (spellCardName.equals("Forest")) {
//            forest(player1, rival);
//        }
//        if (spellCardName.equals("Closed Forest")) {
//            closedForest(player1);
//        }
//
//        if (spellCardName.equals("UMIIRUKA")) {
//            UMIIRUKA(player1, rival);
//        }
//
//        if (spellCardName.equals("Sword of Dark Destruction")) {
//            swordOfDarkDestruction(player1);
//        }
//
//        if (spellCardName.equals("Black Pendant")) {
//            blackPendant(player1);
//        }
//
//        if (spellCardName.equals("United We Stand")) {
//            unitedWeStand(player1);
//        }
//        if (spellCardName.equals("Magnum Shield")) {
//            magnumShield(player1);
//        }
//
//    }
//
//
//    public static void raigeki(String rival) {
//        String[] keys = MonsterZoneCard.getMonstersZoneCard(rival).keySet();
//        for (String key : keys) {
//            MonsterZoneCard.deleteCard(key, rival);
//        }
//    }
//
//    public static void harpieFeatherDuster(String rival) {
//        String[] spell = SpellZoneCard.getSpellsZone(rival).keySet();
//        String[] trap = TrapZoneCard.getAllTrapZone(rival).keySet();
//        for (String key : spell) {
//            SpellZoneCard.deleteSpellZone(key, rival);
//        }
//        for (String key : trap) {
//            TrapZoneCard.deleteTrapZone(key, rival);
//        }
//    }
//
//    public static void swordsOfRevealingLight() {//naghes
//        String[] keys = GameMatModel.getGameMatModelByNumber(2).getAllMonstersZone().keySet();
//        for (String key : keys) {
//            GameMatModel.getGameMatModelByNumber(2).getMonsterByName(key).setBackAndForth("face up");//defend
//        }
//        if (GameMatModel.getGameMatModelByNumber(1).getMonsterByName("Swords of Revealing Light").getBackAndForth().equals("face up")) {
//            for (String key : keys) {
//                GameMatModel.getGameMatModelByNumber(2).getMonsterByName(key).setCanAttack(false);
//            }
//        }
//    }
//
//    public static void darkHole(String rival, String player1) {
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
//
//    public static void spellAbsorption(String player1) {
//        Player.getPlayerByName(player1).changeLifePoint(500);
//    }
//
//    public static void messengerOfPeace(String player1, String rival) {//naghes
//        HashMap<String, MonsterZoneCard> monsters = MonsterZoneCard.getMonsters(player1);
//        String[] monsterNames = monsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < monsters.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByName(monsterNames[i], player1).getAttack() >= 1500) {
//                MonsterZoneCard.getMonsterCardByName(monsterNames[i], player1).canAttack(false);
//            }
//        }
//
//        HashMap<String, MonsterZoneCard> rivalMonsters = MonsterZoneCard.getMonsters(rival);
//        String[] rivalMonsterNames = rivalMonsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < rivalMonsters.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).getAttack() >= 1500) {
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).canAttack(false);
//            }
//        }
//    }
//
//    public static void twinTwisters(String victim1, String victim2, String rival, boolean mine, String player1) {
//        if (Card.getCardsByName(victim1).getCardModel().equals("Spell")) {
//            if (mine) {
//                SpellZoneCard.deleteCard(victim1, player1);
//            }
//            SpellZoneCard.deleteCard(victim1, rival);
//        }
//        if (Card.getCardsByName(victim1).getCardModel().equals("Trap")) {
//            if (mine) {
//                TrapZoneCard.deleteCard(victim1, player1);
//            }
//            TrapZoneCard.deleteCard(victim1, rival);
//        }
//        if (Card.getCardsByName(victim2).getCardModel().equals("Spell")) {
//            if (mine) {
//                SpellZoneCard.deleteCard(victim2, player1);
//            }
//            SpellZoneCard.deleteCard(victim2, rival);
//        }
//        if (Card.getCardsByName(victim2).getCardModel().equals("Trap")) {
//            if (mine) {
//                TrapZoneCard.deleteCard(victim2, player1);
//            }
//            TrapZoneCard.deleteCard(victim2, rival);
//        }
//    }
//
//    public static void mysticalSpaceTyphoon(String victim, String rival, boolean mine, String player1) {
//        if (Card.getCardsByName(victim).getCardModel().equals("Spell")) {
//            if (mine) {
//                SpellZoneCard.deleteCard(victim, player1);
//            }
//            SpellZoneCard.deleteCard(victim, rival);
//        }
//        if (Card.getCardsByName(victim).getCardModel().equals("Trap")) {
//            if (mine) {
//                TrapZoneCard.deleteCard(victim, player1);
//            }
//            TrapZoneCard.deleteCard(victim, rival);
//        }
//    }
//
//    public static void yami(String rival, String player1) {
//        HashMap<String, MonsterZoneCard> ownMonsters = MonsterZoneCard.getMonsters(player1);
//        String[] ownMonsterNames = ownMonsters.keySet().toArray(new String[0]);
//        HashMap<String, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getMonsters(rival);
//        String[] rivalMonsterNames = rivalsMonsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < ownMonsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Fiend") ||
//                    MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Spellcaster")) {
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeAttack(200);
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeDefend(200);
//            }
//            if (MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Fairy")) {
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeAttack(-200);
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeDefend(-200);
//            }
//        }
//        for (int i = 0; i < rivalsMonsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Fiend") ||
//                    MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Spellcaster")) {
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeAttack(200);
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeDefend(200);
//            }
//            if (MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Fairy")) {
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeAttack(-200);
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeDefend(-200);
//            }
//        }
//    }
//
//    public static void forest(String player1, String rival) {
//        HashMap<String, MonsterZoneCard> ownMonsters = MonsterZoneCard.getMonsters(player1);
//        String[] ownMonsterNames = ownMonsters.keySet().toArray(new String[0]);
//        HashMap<String, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getMonsters(rival);
//        String[] rivalMonsterNames = rivalsMonsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < ownMonsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Insect") ||
//                    MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Beast") ||
//                    MonsterCard.getMonsterByName(ownMonsterNames[i]).getMonsterType().equals("Beast-Warrior")) {
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeAttack(200);
//                MonsterZoneCard.getMonsterCardByName(ownMonsterNames[i], player1).changeDefend(200);
//            }
//        }
//        for (int i = 0; i < rivalsMonsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Insect") ||
//                    MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Beast") ||
//                    MonsterCard.getMonsterByName(rivalMonsterNames[i]).getMonsterType().equals("Beast-Warrior")) {
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeAttack(200);
//                MonsterZoneCard.getMonsterCardByName(rivalMonsterNames[i], rival).changeDefend(200);
//            }
//        }
//    }
//
//    public static void closedForest(String player1) {
//        HashMap<String, MonsterZoneCard> monsters = MonsterZoneCard.getMonsters(player1);
//        String[] monsterNames = monsters.keySet().toArray(new String[0]);
//        int increaseAttack = MonsterZoneCard.getMonsterGraveYardNumber() * 100;
//        for (int i = 0; i < monsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(monsterNames[i]).getMonsterType().equals("Beast-Type")) {
//                MonsterZoneCard.getMonsterCardByName(monsterNames[i], player1).changeAttack(increaseAttack);
//            }
//        }
//    }
//
//    public static void UMIIRUKA(String player1, String rival) {
//        HashMap<String, MonsterZoneCard> monsters = MonsterZoneCard.getMonsters(player1);
//        String[] monsterNames = monsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < monsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(monsterNames[i]).getMonsterType().equals("Aqua")) {
//                MonsterZoneCard.getMonsterCardByName(monsterNames[i], player1).changeAttack(500);
//                MonsterZoneCard.getMonsterCardByName(monsterNames[i], player1).changeDefend(-400);
//            }
//        }
//        HashMap<String, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getMonsters(rival);
//        String[] rivalsMonsterNames = rivalsMonsters.keySet().toArray(new String[0]);
//        for (int i = 0; i < rivalsMonsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(rivalsMonsterNames[i]).getMonsterType().equals("Aqua")) {
//                MonsterZoneCard.getMonsterCardByName(rivalsMonsterNames[i], rival).changeAttack(500);
//                MonsterZoneCard.getMonsterCardByName(rivalsMonsterNames[i], rival).changeDefend(-400);
//            }
//        }
//    }
//
//    public static void swordOfDarkDestruction(String player1) {
//        ArrayList<String> monsters = SpellZoneCard.getSpellCardByName("Sword of Dark Destruction", player1).getRelatedMonsters();
//        for (int i = 0; i < monsters.size(); i++) {
//            if (MonsterCard.getMonsterByName(monsters.get(i)).getMonsterType().equals("Spellcaster") ||
//                    MonsterCard.getMonsterByName(monsters.get(i)).getMonsterType().equals("Fiend")) {
//                MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeAttack(400);
//                MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeDefend(-200);
//            }
//        }
//    }
//
//    public static void blackPendant(String player1) {
//        ArrayList<String> monsters = SpellZoneCard.getSpellCardByName("Black Pendant", player1).getRelatedMonsters();
//        for (int i = 0; i < monsters.size(); i++) {
//            MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeAttack(500);
//        }
//    }
//
//    public static void unitedWeStand(String player1) {
//        ArrayList<String> monsters = SpellZoneCard.getSpellCardByName("Black Pendant", player1).getRelatedMonsters();
//        HashMap<String, MonsterZoneCard> monstersZone = MonsterZoneCard.getMonsters(player1);
//        String[] namesOfMonstersInZone = monstersZone.keySet().toArray(new String[0]);
//        int counter = 0;
//        for (int i = 0; i < monstersZone.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByName(namesOfMonstersInZone[i], player1).getMode().equals("defend") ||
//                    MonsterZoneCard.getMonsterCardByName(namesOfMonstersInZone[i], player1).getMode().equals("summon")) {
//                counter++;
//            }
//        }
//        int increaseAttackAndDefend = 800 * counter;
//        for (int i = 0; i < monsters.size(); i++) {
//            MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeAttack(increaseAttackAndDefend);
//            MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeDefend(increaseAttackAndDefend);
//        }
//    }
//
//    public static void magnumShield(String player1) {
//        ArrayList<String> monsters = SpellZoneCard.getSpellCardByName("Black Pendant", player1).getRelatedMonsters();
//        for (int i = 0; i < monsters.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).getMode().equals("summon")) {
//                MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeAttack(
//                        MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).getDefend());
//            }
//            if (MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).getMode().equals("defend")) {
//                MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).changeDefend(
//                        MonsterZoneCard.getMonsterCardByName(monsters.get(i), player1).getAttack());
//            }
//        }
//    }
//}


