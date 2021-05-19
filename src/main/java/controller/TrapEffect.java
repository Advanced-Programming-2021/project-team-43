package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class TrapEffect {

    public static void trapEffectController(int addressOfTrapCard, String rivalUser, String onlineUser, boolean summonMine, MonsterZoneCard ownMonster) {
        String spellTrapName = SpellTrapZoneCard.getSpellCardByAddress(addressOfTrapCard, onlineUser).getSpellTrapName();
        if (spellTrapName.equals("Mind Crush")) {
            mindCrush(onlineUser, rivalUser);
        }
        if (spellTrapName.equals("Torrential Tribute")) {
            torrentialTribute(rivalUser, onlineUser);
        }
        if (spellTrapName.equals("Time Seal")) {
            timeSeal(rivalUser);
        }
        if (spellTrapName.equals("Solemn Warning")) {
            solemnWarning(onlineUser, ownMonster.getAddress(), summonMine, rivalUser);
        }
        if (spellTrapName.equals("Magic Jammer")) {
            magicJammer(onlineUser, ownMonster.getAddress(), summonMine, rivalUser);
        }
        if (spellTrapName.equals("Call of the Haunted")) {
            callOfTheHaunted(onlineUser);
        }
    }

    public static int isAttackedController(String TrapName, String onlineUser, String rivalUser, MonsterZoneCard rivalMonster) {
        if (TrapName.equals("Magic Cylinder"))
            return magicCylinder(onlineUser, rivalUser, rivalMonster);
        if (TrapName.equals("Mirror Force"))
            return mirrorForce(rivalUser,onlineUser);
        return 0;
    }

    public static int magicCylinder(String onlineUser, String rivalUser, MonsterZoneCard ownMonster) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            Player.getPlayerByName(rivalUser).changeLifePoint(-1 * ownMonster.getAttack());
            return 1;
        }
        return 0;
    }

    public static int mirrorForce(String rivalUser, String onlineUser) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
            Integer[] addressOfOwnMonsters = ownMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < ownMonster.size(); i++) {
                if (MonsterZoneCard.getMonsterCardByAddress(addressOfOwnMonsters[i], onlineUser).getMode().equals("OO")) {
                    MonsterZoneCard.removeMonsterFromZone(onlineUser, addressOfOwnMonsters[i]);
                }
            }
            return 1;
        }
        return 0;
    }

    public static void mindCrush(String onlineUser, String rivalUser) {
        String response;
        do {
            GameMatView.showInput("Please enter the name of a game card: ");
            response = GameMatView.getCommand();
        } while (Card.getCardsByName(response) == null);
        if (HandCardZone.doesThisCardNameExist(rivalUser, response)) {
            int[] allAddress = HandCardZone.getAddressByName(rivalUser, response);
            for (int address : allAddress) {
                HandCardZone.removeFromHandCard(onlineUser, address);
            }
        }
        else {
            Random random = new Random();
            HandCardZone.removeFromHandCard(rivalUser, random.nextInt(HandCardZone.getNumberOfFullHouse(onlineUser)) + 1);
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

    public static int torrentialTribute(String rival, String player1) {
        if (!ringOfDefenseEffect(rival, player1)) {
            Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rival);
            Integer[] monsterNames = rivalsMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < rivalsMonster.size(); i++) {
                MonsterZoneCard.removeMonsterFromZone(rival, monsterNames[i]);
            }
            Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(player1);
            Integer[] ownMonsterNames = ownMonster.keySet().toArray(new Integer[0]);
            for (int i = 0; i < ownMonster.size(); i++) {
                MonsterZoneCard.removeMonsterFromZone(player1, ownMonsterNames[i]);
            }
            return 1;
        }
        return 0;
    }

    public static void timeSeal(String rival) {
        Player.getPlayerByName(rival).setCanDrawCard(false);
    }

    public static int solemnWarning(String player1, int addressOfSummonCard, boolean summonMine, String rival) {
        if (!ringOfDefenseEffect(rival, player1)) {
            Player.getPlayerByName(player1).changeLifePoint(-2000);
            if (summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.removeMonsterFromZone(player1, addressOfSummonCard);
                }
            }
            if (!summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.removeMonsterFromZone(rival, addressOfSummonCard);
                }
            }
            return 1;
        }
        return 0;
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

    public static void callOfTheHaunted(String onlineUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
            GameMatView.showInput("Oops! You cant use this trap effect!");
            return;
        }
        GameMatView.showInput("Please enter the address of your own dead Monster to summon: ");
        String cardName = "";
        String response = GameMatView.getCommand();
        while (Integer.parseInt(response) < 1 || Integer.parseInt(response) > GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards()) {
            if (response.equals("cancel"))
                return;
            cardName = GameMatModel.getGameMatByNickname(onlineUser).getNameOfDeadCardByAddress(Integer.parseInt(response));
            if (MonsterCard.getMonsterByName(cardName) != null)
                break;
            GameMatView.showInput("Please enter the Monster address correctly: ");
            response = GameMatView.getCommand();
        }
        new MonsterZoneCard(onlineUser, cardName, "OO", false, false);
        GameMatModel.getGameMatByNickname(onlineUser).removeFromGraveyardByAddress(Integer.parseInt(response));
    }
}
