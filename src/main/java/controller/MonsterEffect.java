package controller;
import model.*;
import view.GameMatView;
import java.util.*;


public class MonsterEffect {

    private static String response;


    public static int changeModeEffectController(MonsterZoneCard ownMonster, String onlineUser, String rivalUser) {
        String monsterName = ownMonster.getMonsterName();
        return switch (monsterName) {
            case "Command knight" -> commandKnight(ownMonster, onlineUser, rivalUser);
            case "Man-Eater Bug" -> manEaterBug(ownMonster, rivalUser);
            case "Mirage Dragon" -> mirageDragon(ownMonster, rivalUser);
            default -> 0;
        };
    }

    public static int isAttackedEffectController(MonsterZoneCard ownMonster, String onlineUser, MonsterZoneCard rivalMonster, String rivalUser) {
        String monsterName = ownMonster.getMonsterName();
        return switch (monsterName) {
            case "Yomi Ship" -> yomiShip(ownMonster, rivalMonster);
            case "Suijin" -> suijin(ownMonster);
            case "Marshmallon" -> marshmallon(ownMonster, rivalUser);
            case "Texchanger" -> texchanger(ownMonster, onlineUser);
            case "Exploder Dragon" -> exploderDragon(ownMonster, rivalMonster);
            default -> 0;
        };
    }

    private static int commandKnight(MonsterZoneCard ownMonster, String onlineUser, String rivalUser) {
        if (!ownMonster.getMode().equals("DH") && !ownMonster.getIsEffectUsed()) {
            int counter = 0;
            Map<Integer, MonsterZoneCard> monsters;
            monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
            Integer[] keys;
            Map<Integer, MonsterZoneCard> rivalsMonsters;
            rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
            Integer[] rivalsKeys;
            keys = monsters.keySet().toArray(new Integer[0]);
            rivalsKeys = rivalsMonsters.keySet().toArray(new Integer[0]);
            for (Integer key : keys) {
                MonsterZoneCard.getMonsterCardByAddress(key, onlineUser).changeAttack(400);
                if (!MonsterZoneCard.getMonsterCardByAddress(key, onlineUser).getMonsterName().equals("Command knight")) {
                    counter++;
                }
            }
            if (counter != 0) {
                ownMonster.setCanAttackToThisMonster(false);
            }
            for (int i = 0; i < rivalsMonsters.size(); i++) {
                MonsterZoneCard.getMonsterCardByAddress(rivalsKeys[i], rivalUser).changeAttack(400);
            }
            ownMonster.setIsEffectUsed(true);
        }
        ownMonster.setCanAttackToThisMonster(MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 1);
        if (ownMonster.getMode().equals("DH")) {
            ownMonster.setCanAttackToThisMonster(true);
            ownMonster.setIsEffectUsed(false);
        }
        return 1;
    }

    private static int manEaterBug(MonsterZoneCard ownMonsterCard, String rivalUser) {
        if (!ownMonsterCard.getMode().equals("DH") && !ownMonsterCard.getIsEffectUsed()) {
            GameMatView.showInput("Please enter the address of a Rival Monster to destroy:");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the correct address of a Rival Monster:");
                response = GameMatView.getCommand();
            }
            MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser).removeMonsterFromZone();
            ownMonsterCard.setIsEffectUsed(true);
            return 1;
        }
        return 0;
    }

    private static int mirageDragon(MonsterZoneCard ownMonster, String rivalUser) {
        Player.getPlayerByName(rivalUser).setCanUseTrap(ownMonster.getMode().equals("DH"));
        return 1;
    }

    private static int yomiShip(MonsterZoneCard ownMonster, MonsterZoneCard rivalMonster) {
        if (ownMonster == null) {
            rivalMonster.removeMonsterFromZone();
            return 1;//with success
        }
        return 0;
    }

    private static int suijin(MonsterZoneCard ownMonster) {
        if (!ownMonster.getMode().equals("DH") && !ownMonster.getIsEffectUsed()) {
            GameMatView.showInput("Do you want to use this Monster Effect? (yes/no)");
            response = GameMatView.getCommand();
            while (!response.matches("yes|no")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the answer correctly! (yse/no)");
                response = GameMatView.getCommand();
            }
            if (response.equals("yes")) {
                ownMonster.setIsEffectUsed(true);
                return 1;
            }
        }
        return 0;
    }

    public static int marshmallon(MonsterZoneCard ownMonster, String rivalUser) {
        if (ownMonster.getMode().equals("DH")) {
            Player.getPlayerByName(rivalUser).changeLifePoint(-1000);
            return 1;
        }
        return 0;
    }

    private static int exploderDragon(MonsterZoneCard ownMonster, MonsterZoneCard rivalMonster) {
        if (ownMonster == null) {
            rivalMonster.removeMonsterFromZone();
            return 1;
        }
        return 0;
    }

    public static void theCalculator(String onlineUser, MonsterZoneCard ownMonster) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monster = monsters.keySet().toArray(new Integer[0]);
        int attack = 0;
        for (int s : monster) {
            if (MonsterZoneCard.getMonsterCardByAddress(s, onlineUser).getMode().equals("OO") ||
                    MonsterZoneCard.getMonsterCardByAddress(s, onlineUser).getMode().equals("DO")) {
                attack += MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(s, onlineUser).getMonsterName()).getLevel();
            }
        }
        attack *= 300;
        ownMonster.setAttack(attack);
    }//call this when it comes to monster zone

    public static int terratiger(MonsterZoneCard ownMonster, String onlineUser) {
        GameMatView.showInput("Do you want Summon another Monster in Defend position? (yes/no)");
        response = GameMatView.getCommand();
        while (!response.matches("yes|no")) {
            if (response.equals("cancel"))
                return 0;//cancel
            GameMatView.showInput("Please enter the correct answer: (yes/no)");
            response = GameMatView.getCommand();
        }
        if (response.equals("no"))
            return 0;
        else {
            if (HandCardZone.getNumberOfFullHouse(onlineUser) == 0)
                GameMatView.showInput("Oops! You cant have Special Summon because of lack of HandCard!");
            else if (!HandCardZone.doesAnyLevelFourMonsterExisted(onlineUser))
                GameMatView.showInput("Oops! You cant have Special Summon because of no 4 level or less Monster!");
            else {
                GameMatView.showInput("Please enter the address of a 4 level or less to summon in Defend Position:");
                response = GameMatView.getCommand();
                while (Integer.parseInt(response) < 1 || Integer.parseInt(response) > HandCardZone.getNumberOfFullHouse(onlineUser)) {
                    if (response.equals("cancel"))
                        return 0;//cancel
                    String cardName = HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser).getCardName();
                    if (!MonsterCard.getMonsterByName(cardName).getCardModel().equals("Monster") || MonsterCard.getMonsterByName(cardName).getLevel() > 4) {
                        GameMatView.showInput("Please enter the correct address:");
                        continue;
                    }
                    GameMatView.showInput("Please enter the correct address:");
                    response = GameMatView.getCommand();
                }
                HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser).removeFromHandCard();
                new MonsterZoneCard(onlineUser, "The Tricky", "DO", false, false, false);
                return 1;
            }
        }
        return 0;
    }

    public static int theTricky(MonsterZoneCard ownMonster, String onlineUser) {
        GameMatView.showInput("Do you want a Special Summon? (yes/no)");
        response = GameMatView.getCommand();
        while (!response.matches("yes|no")) {
            if (response.equals("cancel"))
                return 0;//cancel
            GameMatView.showInput("Please enter the correct answer: (yes/no)");
            response = GameMatView.getCommand();
        }
        if (response.equals("no"))
            return 0;
        else {
            if (HandCardZone.getNumberOfFullHouse(onlineUser) == 1)
                GameMatView.showInput("Oops! You cant have Special Summon because of lack of HandCard!");
            else {
                GameMatView.showInput("Please enter the address of a HandCard to tribute:");
                response = GameMatView.getCommand();
                while (Integer.parseInt(response) < 1 || Integer.parseInt(response) > HandCardZone.getNumberOfFullHouse(onlineUser) || Integer.parseInt(response) == ownMonster.getAddress()) {
                    if (response.equals("cancel"))
                        return 0;//cancel
                    GameMatView.showInput("Please enter the correct address:");
                    response = GameMatView.getCommand();
                }
                HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser).removeFromHandCard();
                new MonsterZoneCard(onlineUser, "The Tricky", "OO", false, false, false);
                return 1;
            }
        }
        return 0;
    }

    public static int beastKingBarbaros(MonsterZoneCard ownMonster, HandCardZone handCard, String onlineUser, String rivalUser) {
        GameMatView.showInput("Do you want to summon Beast King Barbaros without tributing? (yes/no)");
        response = GameMatView.getCommand();
        while (!response.matches("yes|no")) {
            if (response.equals("cancel"))
                return 0;//cancel
            GameMatView.showInput("Please enter the correct answer: (yes/no)");
            response = GameMatView.getCommand();
        }
        if (response.equals("yes")) {
            if (handCard != null) {
                new MonsterZoneCard(onlineUser, "Beast King Barbaros", "OO", false, false, true);
                handCard.removeFromHandCard();
            }
            else
                ownMonster.setAttack(1900);
        }
        else {
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 3)
                GameMatView.showInput("Oops! You cant summon this Monster because of lack of Monster to tribute");
            else {
                GameMatView.showInput("Please enter the address of three Monster to tribute: ");
                for (int i = 1; i < 4; i++) {
                    GameMatView.showInput("Please enter the address of Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                        if (response.equals("cancel"))
                            return 0;//cancel
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser).removeMonsterFromZone();
                }
                new MonsterZoneCard(onlineUser, "Beast King Barbaros", "OO", false,false, false);
                Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
                Map<Integer, SpellTrapZoneCard> spellsTraps = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rivalUser);
                Integer[] monster = monsters.keySet().toArray(new Integer[0]);
                Integer[] spellTraps = spellsTraps.keySet().toArray(new Integer[0]);
                for (int s : monster) {
                    MonsterZoneCard.getMonsterCardByAddress(s, rivalUser).removeMonsterFromZone();
                }
                for (int s : spellTraps) {
                    SpellTrapZoneCard.getSpellCardByAddress(s, rivalUser).removeSpellTrapFromZone();
                }
            }
        }
        return 1;//with success
    }

    public static int gateGuardian(String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 3) {
            GameMatView.showInput("Oops! You cant summon this Monster because of lack of Monster to tribute");
        }
        else {
            GameMatView.showInput("Please enter the address of three Monster to tribute: ");
            for (int i = 1; i < 4; i++) {
                GameMatView.showInput("Do you want to tribute your Monster or rival Monster? (own/rival)");
                response = GameMatView.getCommand();
                while (!response.matches("own|rival")) {
                    if (response.equals("cancel"))
                        return 0;//cancel
                    GameMatView.showInput("Please enter the answer correctly: (own/rival)");
                    response = GameMatView.getCommand();
                }
                if (response.equals("own")) {
                    GameMatView.showInput("Please enter the address of your Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                        if (response.equals("cancel"))
                            return 0;//cancel
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser).removeMonsterFromZone();
                }
                else {
                    GameMatView.showInput("Please enter the address of rival Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                        if (response.equals("cancel"))
                            return 0;//cancel
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser).removeMonsterFromZone();
                }
            }
            new MonsterZoneCard(onlineUser, "Gate Guardian", "OO", false,false, false);
        }
        return 1;
    }

    private static int texchanger(MonsterZoneCard ownMonster, String onlineUser) {
        if (!ownMonster.getIsEffectUsed()) {
            GameMatView.showInput("Please enter the zone name from which you want to pick a monster to summon: (hand/graveyard/deck)");
            response = GameMatView.getCommand();
            while (!response.matches("hand|deck|graveyard")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the correct zone name: (hand/graveyard/deck)");
                response = GameMatView.getCommand();
            }
            if (response.equals("hand")) {
                if (!HandCardZone.doesThisCardTypeExist(onlineUser, "Monster", "Cyberse")) {
                    GameMatView.showInput("Oops! You dont have any Cyberse to summon!");
                } else {

                }
            } else if (response.equals("graveyard")) {

            } else {

            }
            ownMonster.setIsEffectUsed(true);
            return 1;
        }
        return 0;
    }//////???????

    public static void heraldOfCreation() {//???

    }////?????

}

