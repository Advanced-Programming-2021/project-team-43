package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class TrapEffect {

    public static int isAttackedController(MonsterZoneCard ownMonster, String onlineUser, String rivalUser) {
        if (!SpellTrapZoneCard.isThisSpellActivated(onlineUser, "Ring of Defense")) {
            if (SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Magic Cylinder")) {
                GameMatView.showInput("Magic Cylinder Effect is activated!");
                Player.getPlayerByName(onlineUser).changeLifePoint(-1 * ownMonster.getAttack());
                return 1;//with success
            }
            if (SpellTrapZoneCard.isThisTrapActivated(rivalUser, "Mirror Force")) {
                mirrorForce(rivalUser, onlineUser);
                return 1;
            }
        }
        return 0;
    }

    public static int isActivatedController(SpellTrapZoneCard ownTrap, String onlineUser, String rivalUser) {
        String trapName = ownTrap.getSpellTrapName();
        if (trapName.equals("Mind Crush")) {
            mindCrush(onlineUser, rivalUser);
        }
        return 0;
    }

    private static int mindCrush(String onlineUser, String rivalUser) {
        String response;
        do {
            GameMatView.showInput("Please enter the name of a game card: ");
            response = GameMatView.getCommand();
        } while (Card.getCardsByName(response) == null);
        if (HandCardZone.doesThisCardNameExist(rivalUser, response)) {
            int[] allAddress = HandCardZone.getAddressByName(rivalUser, response);
            for (int address : allAddress) {
                HandCardZone.getHandCardByAddress(address, rivalUser).removeFromHandCard();
            }
        }
        else {
            Random random = new Random();
            HandCardZone.getHandCardByAddress(random.nextInt(HandCardZone.getNumberOfFullHouse(onlineUser)) + 1, rivalUser).removeFromHandCard();
        }
        return 0;
    }

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

    public static void mirrorForce(String rivalUser, String onlineUser) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
            Integer[] addressOfOwnMonsters = ownMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < ownMonster.size(); i++) {
                if (MonsterZoneCard.getMonsterCardByAddress(addressOfOwnMonsters[i], onlineUser).getMode().equals("OO")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfOwnMonsters[i], onlineUser).removeMonsterFromZone();
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
            }
            if (!summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).removeMonsterFromZone();
                }
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
