package main.java.controller;
import main.java.model.*;
import java.util.HashMap;

public class MonsterEffect {
    public static void monsterEffectController(String monsterCardName, String player1, String rival, String nameOfAttackerCard, String victim1, String victim2, String victim3
            , String nameOfHostCardOfScanner, String kindOfSummonOfBeastCard
    ,String mode,int address) {
        if (monsterCardName.equals("Command knight")) {
            commandKnight(player1, rival);
        }
        if (monsterCardName.equals("Yomi Ship")) {
            yomiShip(nameOfAttackerCard,rival);
        }
        if (monsterCardName.equals("Suijin")) {
            suijin(nameOfAttackerCard,player1,rival);
        }
        if (monsterCardName.equals("Man-Eater Bug")) {
            manEaterBug(victim1,rival);
        }
        if (monsterCardName.equals("Gate Guardian")) {
            gateGuardian(victim1, victim2, victim3,rival);
        }
        if (monsterCardName.equals("Scanner")) {
            scanner(nameOfHostCardOfScanner,player1,address,mode);
        }
        if (monsterCardName.equals("Marshmallon")) {
            marshmallon(rival,player1);
        }
        if (monsterCardName.equals("Beast King Barbaros")) {
            beastKingBarbaros(rival,player1,kindOfSummonOfBeastCard, victim1, victim2, victim3);
        }
        if (monsterCardName.equals("The Calculator")) {
            theCalculator(player1);
        }
        if (monsterCardName.equals("Mirage Dragon")) {
            mirageDragon(player1);
        }
        if (monsterCardName.equals("Exploder Dragon")) {
            exploderDragon(nameOfAttackerCard,rival);
        }
    }

    public static void commandKnight(String player1, String rival) {
        int counter = 0;
        if (MonsterZoneCard.getMonsterCardByName("Command knight", player1).getMode().equals("summon") ||
                MonsterZoneCard.getMonsterCardByName("Command knight", player1).getMode().equals("defend")) {//5
            HashMap<String, MonsterZoneCard> monsters;
            monsters = GameMatModel.getMonsters();
            String[] keys;
            keys = monsters.keySet().toArray(new String[0]);
            for (String key : keys) {
                MonsterZoneCard.getMonsterCardByName(key, player1).changeDefend(400);
                if (!key.equals("Command knight")) {
                    counter++;
                }
            }
            if (counter != 0) {
                MonsterZoneCard.getMonsterCardByName("Command knight", player1).canAttacktoThisCard(false);
            }
        }
    }

    public static void yomiShip(String nameOfAttackerCard, String rival) {
        MonsterZoneCard.deleteCard(nameOfAttackerCard, rival);
        // GameMatModel.getGameMatModelByNumber(2).addMonsterToGraveYard(nameOfAttacker);
    }

    public static void suijin(String nameOfAttacker, String player1,String rival) {////6
        if (!MonsterZoneCard.getMonsterCardByName("Suijin", player1).getIsUsed &&
                MonsterZoneCard.getMonsterCardByName("Suijin", player1).getMode.equals("defend") ||
                MonsterZoneCard.getMonsterCardByName("Suijin", player1).getMode.equals("summon")) {
            MonsterZoneCard.getMonsterCardByName(nameOfAttacker, rival).setAttack(0);
        }
    }


    public static void manEaterBug(String victim, String rival) {//6
        MonsterZoneCard.deleteCard(victim, rival);
    }

    public static void gateGuardian(String victim1, String victim2, String victim3, String rival) {//6
        MonsterZoneCard.delelteCard(victim1, rival);
        MonsterZoneCard.deleteCard(victim2, rival);
        MonsterZoneCard.deleteCard(victim3, rival);
    }

    public static void scanner(String nameOfHostCard, String player1, int address, String mode) {//6
        MonsterZoneCard.deleteScanner();
        String Attribute = MonsterCard.getMonsterByName(nameOfHostCard).getAttribute();
        int level = MonsterCard.getMonsterByName(nameOfHostCard).getLevel();
        String type = MonsterCard.getMonsterByName(nameOfHostCard).getMonsterType();
        int attack = MonsterCard.getMonsterByName(nameOfHostCard).getAttack();
        int defend = MonsterCard.getMonsterByName(nameOfHostCard).getDefend();
        String cardType = MonsterCard.getMonsterByName(nameOfHostCard).getCardType();
        String description = MonsterCard.getMonsterByName(nameOfHostCard).getDescription();
        int price = MonsterCard.getMonsterByName(nameOfHostCard).getPrice();
        new MonsterZoneCard(player1, nameOfHostCard, address, mode);
//        Card( attack, defend
//         , true);
    }

    public static void marshmallon(String rivalName, String player1) {//6naghes
        if (MonsterZoneCard.getMonsterCardByName("Marshmallon", player1).getMode.equals("set")) {
            Player.getPlayerByName(rivalName).changeLifePoint(-1000);
        }
    }

    public static void beastKingBarbaros(String rival, String player1, String kindOfSummon, String victim1, String victim2, String victim3) {//kodoom haula?
        if (kindOfSummon.equals("Normal")) {
            MonsterZoneCard.getMonsterCardByName("Beast King Barbaros", player1).setAttack(1900);
        }
        if (kindOfSummon.equals("notNormal")) {
            MonsterZoneCard.deleteCard(victim1, player1);
            MonsterZoneCard.deleteCard(victim2, player1);
            MonsterZoneCard.deleteCard(victim3, player1);
            HashMap<String, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonsterZone(rival);
            HashMap<String, SpellZoneCard> spells = MonsterZoneCard.getAllSpellZone(rival);
            HashMap<String, TrapZoneCard> traps = MonsterZoneCard.getAllTrapZone(rival);
            String[] monster = monsters.keySet().toArray(new String[0]);
            String[] trap = traps.keySet().toArray(new String[0]);
            String[] spell = spells.keySet().toArray(new String[0]);
            for (String s : monster) {
                MonsterZoneCard.deleteMonsterZone(s, rival);
                // MonsterZoneCard.addToGraveYard(s,rival);
            }
            for (String s : trap) {
                TrapZoneCard.deleteTrapZone(s, rival);
                //   TrapZoneCard.addToGraveYard(s,rival);
            }
            for (String s : spell) {
                SpellZoneCard.deleteSpellZone(s, rival);
                //  SpellZoneCard.addToGraveYard(s,rival);
            }
        }
    }

    public static void theCalculator(String player1) {//6
        HashMap<String, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonsterZone(player1);
        String[] monster = monsters.keySet().toArray(new String[0]);
        int attack = 0;
        for (String s : monster) {
            if (MonsterZoneCard.getMonsterCardByName(s, player1).getMode().equals("summon") ||
                    MonsterZoneCard.getMonsterCardByName(s, player1).getMode().equals("defend")) {
                attack += MonsterZoneCard.getMonsterCardByName(s, player1).getLevel();
            }
        }
        attack *= 300;
        MonsterZoneCard.getMonsterCardByName("The Calculator", player1).setAttack(attack);
    }

    public static void mirageDragon(String player1) {//6
        if (MonsterZoneCard.getMonsterCardByName("Mirage Dragon", player1).getMode().equals("defend") ||
                MonsterZoneCard.getMonsterCardByName("Mirage Dragon", player1).getMode().equals("summon")) {
            HashMap<String, TrapZoneCard> allTraps = TrapZoneCard.getTraps();
            String[] trapNames = allTraps.keySet().toArray(new String[0]);
            for (int i = 0; i < allTraps.size(); i++) {
                TrapZoneCard.getTrapByName(trapNames[i]).canActivate(false);
            }
        }
    }

    public static void exploderDragon(String nameOfAttacker, String rival) {//seda kardanesh shart dare
        MonsterZoneCard.deleteCard(nameOfAttacker, rival);
        //  GameMatModel.getGameMatModelByNumber(2).addToGraveYard(nameOfAttacker);

    }

}

