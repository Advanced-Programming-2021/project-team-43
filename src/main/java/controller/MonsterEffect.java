package main.java.controller;

public class MonsterEffect {

//    public static void commandKnight() {
//        int counter = 0;
//        if (GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Command knight").getBackAndForth().equals("face up")) {//5
//            HashMap<String, MonsterGame> monsters;
//            monsters = GamMatModel.getMonsters();
//            String[] keys;
//            keys = monsters.keySet().toArray(new String[0]);
//            for (String key : keys) {
//                GameMatModel.getGameMatModelByNumber(1).geMonsterByNameZone(key).setDefend(400);
//                if (!key.equals("Command knight")) {
//                    counter++;
//                }
//            }
//            if (counter != 0) {
//                System.out.println("Can Not Attack To This Card");////chi bayad chap beshe?
//            }
//        }
//    }
//
//    public static void yomiShip(String nameOfAttacker) {//5
//        GameMatModel.getGameMatModelByNumber(2).getMonsterByNameZone(nameOfAttacker).deleteMosterFromGameMat();
//        GameMatModel.getGameMatModelByNumber(2).addMonsterToGraveYard(nameOfAttacker);
//    }
//
//    public static void suijin(String nameOfAttacker) {////6
//        if (!GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Suijin").getIsUsed &&
//                GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Suijin").getBackAndForth().equals("face up")) {
//            GameMatModel.getGameMatModelByNumber(2).getMonsterByNameZone(nameOfAttacker).setAttack(0);
//        }
//
//    }
//
//    public static void manEaterBug() {//6
//        Random rand = new Random();
//        int intRandom = rand.nextInt(4) + 1;
//        String[] keys = GameMatModel.getGameMatModelByNumber(2).getAllMonstersZone().keySet();
//        GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(keys[intRandom]);
//    }
//
//    public static void gateGuardian(String victim1, String victim2, String victim3) {//6
//        GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim1);
//        GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim2);
//        GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim3);
//    }
//
//    public static void scanner(String nameOfHostCard) {//6
//        MonsterCard.deleteScanner();
//        String Attribute = MonsterCard.getMonsterByName(nameOfHostCard).getAttribute();
//        int level = MonsterCard.getMonsterByName(nameOfHostCard).getLevel();
//        String type = MonsterCard.getMonsterByName(nameOfHostCard).getMonsterType();
//        int attack = MonsterCard.getMonsterByName(nameOfHostCard).getAttack();
//        int defend = MonsterCard.getMonsterByName(nameOfHostCard).getDefend();
//        String cardType = MonsterCard.getMonsterByName(nameOfHostCard).getCardType();
//        String description = MonsterCard.getMonsterByName(nameOfHostCard).getDescription();
//        int price = MonsterCard.getMonsterByName(nameOfHostCard).getPrice();
//        new MonsterCard(Attribute, nameOfHostCard, level, type, attack, defend, "Monster", cardType
//                , true, description, price);
//    }
//
//    public static void marshmallon(String rivalName) {//6naghes
//        if (GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Marshmallon").getBackAndForth().equals("face down")) {
//            Player.getPlayerByname(rivalName).decreaseLP(1000);
//        }
//    }
//
//    public static void beastKingBarbaros(String kindOfSummon, String victim1, String victim2, String victim3) {//kodoom haula?
//        if (kindOfSummon.equals("Normal")) {
//            GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Beast King Barbaros").setAttack(1900);
//        }
//        if (kindOfSummon.equals("notNormal")) {
//            GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim1);
//            GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim2);
//            GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim3);
//            HashMap<String, MonsterCardZone> monsters = GameMatModel.getGameMatModelByNumber(2).getAllMonsterZone();
//            HashMap<String, SpeellCardZone> spells = GameMatModel.getGameMatModelByNumber(2).getAllSpellZone();
//            HashMap<String, TrapCardZone> traps = GameMatModel.getGameMatModelByNumber(2).getAllTrapZone();
//            String[] monster = monsters.keySet().toArray(new String[0]);
//            String[] trap = traps.keySet().toArray(new String[0]);
//            String[] spell = spells.keySet().toArray(new String[0]);
//            for (String s : monster) {
//                GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(s);
//                GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//            for (String s : trap) {
//                GameMatModel.getGameMatModelByNumber(2).deleteTrapZone(s);
//                GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//            for (String s : spell) {
//                GameMatModel.getGameMatModelByNumber(2).deleteSpellZone(s);
//                GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//        }
//    }
//
//    public static void texchanger(String summonCard) {//6
//        if (!GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Texchanger").getIsUsed) {
//            GameMatModel.getGameMatModelByNumber(1).setIsAttackCanceled(true);
//            GameMatModel.getGameMatModelByNumber(1).addToMonsterZone(summonCard);
//        }
//    }
//
//    public static void theCalculator() {//6
//        HashMap<String, MonsterCardZone> monsters = GameMatModel.getGameMatModelByNumber(1).getAllMonsterZone();
//        String[] monster = monsters.keySet().toArray(new String[0]);
//        int attack = 0;
//        for (String s : monster) {
//            if (GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone(s).getBackAndForth().equals("face up")) {
//                attack += GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone(s).getMonsterLevel();
//            }
//        }
//        attack *= 300;
//        GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("The Calculator").setAttack(attack);
//
//    }
//
//    public static void mirageDragon() {//6
//        if (GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Mirage Dragon").getBackAndForth().equals("face up")) {
//            GameMatModel.getGameMatModelByNumber(2).setcanSummonTrap(false);
//        }
//    }
//
//    public static void heraldOfCreation() {//moshkel
//
//    }
//
//    public static void exploderDragon(String nameOfAttacker) {//seda kardanesh shrt dare
//        GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(nameOfAttacker);
//        GameMatModel.getGameMatModelByNumber(2).addToGraveYard(nameOfAttacker);
//
//    }
//
//    public static void terratigerTheEmpoweredWarrior() {///piade sazi dar GameMatModel
//
//    }
//
//    public static void theTricky() {//piade sazi dar GameMatModel
//
//    }

}

