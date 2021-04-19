package controller;

import model.Card;
import model.MonsterCard;

import java.util.ArrayList;
import java.util.HashMap;

public class MonsterEffect {

    public static void commandKnight() {
        MonsterCard.setEpic(true);
        if (Card.getCardsByName("Command knight").getBackAndForth().equals("Forth")) {
            HashMap<String, MonsterCard> monsters;
            monsters = MonsterCard.getAllMonsters();
            String[] keys;
            keys= monsters.keySet().toArray(new String[0]);
            for (String key : keys) {
                MonsterCard.getMonsterByName(key).changeAttack(400);
            }
        }
    }

    public static void yomiShip(String nameOfAttacker) {

    }

    public static void suijin() {

    }

    public static void manEaterBug() {

    }

    public static void gateGuardian() {

    }

    public static void scanner() {

    }

    public static void marshmallon() {

    }

    public static void beastKingBarbaros() {

    }

    public static void texchanger() {

    }

    public static void theCalculator() {

    }

    public static void mirageDragon() {

    }

    public static void heraldOfCreation() {

    }

    public static void exploderDragon() {

    }

    public static void terratigerTheEmpoweredWarrior() {

    }

    public static void theTricky() {

    }

}

