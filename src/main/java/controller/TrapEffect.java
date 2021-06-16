package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class TrapEffect {

    public static int magicCylinder(String onlineUser, String rivalUser, MonsterZoneCard ownMonster, SpellTrapZoneCard trapCard) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser) && ownMonster != null) {
            Player.getPlayerByName(rivalUser).changeLifePoint(-1 * ownMonster.getAttack());
            trapCard.removeSpellTrapFromZone();
            return 1;
        }
        return 0;
    }

    public static int mirrorForce(String rivalUser, String onlineUser, SpellTrapZoneCard trapCard) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            MonsterZoneCard monster;
            for (int i = 1; i < 6; i++) {
                monster = MonsterZoneCard.getMonsterCardByAddress(i, rivalUser);
                if (monster != null && monster.getMode().equals("OO"))
                    monster.removeMonsterFromZone();
            }
            trapCard.removeSpellTrapFromZone();
            return 1;
        }
        return 0;
    }

    public static int mindCrush(String onlineUser, String rivalUser, SpellTrapZoneCard trapCard) {
        String response;
        do {
            GameMatView.showInput("Please enter the name of a game card: ");
            response = GameMatView.getCommand();
        } while (Card.getCardsByName(response) == null);
        if (HandCardZone.doesThisCardNameExist(rivalUser, response))
            HandCardZone.removeAllTypeCard(rivalUser, response);
        else {
            Random random = new Random();
            HandCardZone.getHandCardByAddress(random.nextInt(HandCardZone.getNumberOfFullHouse(onlineUser)) + 1, rivalUser).removeFromHandCard();
        }
        trapCard.removeSpellTrapFromZone();
        return 1;
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

    public static int torrentialTribute(String rivalUser, String onlineUser, SpellTrapZoneCard trapCard) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            MonsterZoneCard monster;
            for (int i = 1; i < 6; i++) {
                monster = MonsterZoneCard.getMonsterCardByAddress(i, onlineUser);
                if (monster != null)
                    monster.removeMonsterFromZone();
            }
            for (int i = 1; i < 6; i++) {
                monster = MonsterZoneCard.getMonsterCardByAddress(i, rivalUser);
                if (monster != null)
                    monster.removeMonsterFromZone();
            }
            trapCard.removeSpellTrapFromZone();
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
                    MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, player1);
                }
            }
            if (!summonMine) {
                if (Card.getCardsByName(MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).getMonsterName()).getCardModel().equals("Monster")) {
                    MonsterZoneCard.getMonsterCardByAddress(addressOfSummonCard, rival).removeMonsterFromZone();
                }
            }
            return 1;
        }
        return 0;
    }

    public static int magicJammer(String onlineUser, String rivalUser, SpellTrapZoneCard trapCard) {
        if (!ringOfDefenseEffect(rivalUser, onlineUser)) {
            if (HandCardZone.getNumberOfFullHouse(onlineUser) == 0) {
                GameMatView.showInput("Oops! You dont have any card in your hand to drop!");
            }
            else {
                int address;
                while (true) {
                    GameMatView.showInput("Please enter the address of one of your hand card to drop:");
                    String response = GameMatView.getCommand();
                    if (!response.matches("\\d+"))
                        continue;
                    address = Integer.parseInt(response);
                    if (address > 0 && address <= HandCardZone.getNumberOfFullHouse(onlineUser))
                        break;
                }
                address--;
                HandCardZone.getHandCardByAddress(address, onlineUser).removeFromHandCard();
            }
            trapCard.removeSpellTrapFromZone();
            return 1;
        }
        return 0;
    }

    public static int callOfTheHaunted(String onlineUser, SpellTrapZoneCard trapCard) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
            GameMatView.showInput("Oops! You cant use this trap effect!");
            trapCard.removeSpellTrapFromZone();
            return 0;
        }
        else if (!GameMatModel.getGameMatByNickname(onlineUser).isAnyMonsterInGraveyard()) {
            GameMatView.showInput("Oops! You dont have any dead monster to summon!");
            trapCard.removeSpellTrapFromZone();
            return 0;
        }
        String cardName, response;
        int address;
        while (true) {
            GameMatView.showInput("Please enter the address of your own dead Monster to summon: ");
            response = GameMatView.getCommand();
            if (response.equals("cancel")) {
                GameMatView.showInput("Your trap effect is not activated and the trap is gone to graveyard!");
                trapCard.removeSpellTrapFromZone();
                return 0;
            }
            if (!response.matches("\\d+"))
                continue;
            address = Integer.parseInt(response);
            if (address > 0 && address <= GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards()) {
                cardName = GameMatModel.getGameMatByNickname(onlineUser).getDeadCardNameByAddress(address - 1);
                if (MonsterCard.getMonsterByName(cardName) != null)
                    break;
            }
        }
        address--;
        new MonsterZoneCard(onlineUser, cardName, "OO", false, false);
        GameMatModel.getGameMatByNickname(onlineUser).removeFromGraveyardByAddress(address);
        trapCard.removeSpellTrapFromZone();
        return 1;
    }

}