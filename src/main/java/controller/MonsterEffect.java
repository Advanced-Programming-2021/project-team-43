package controller;

public class MonsterEffect {

//    public static void commandKnight() {
//        int counter = 0;
//        if (model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Command knight").getBackAndForth().equals("face up")) {//5
//            HashMap<String, MonsterGame> monsters;
//            monsters = GamMatModel.getMonsters();
//            String[] keys;
//            keys = monsters.keySet().toArray(new String[0]);
//            for (String key : keys) {
//                model.GameMatModel.getGameMatModelByNumber(1).geMonsterByNameZone(key).setDefend(400);
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
//        model.GameMatModel.getGameMatModelByNumber(2).getMonsterByNameZone(nameOfAttacker).deleteMosterFromGameMat();
//        model.GameMatModel.getGameMatModelByNumber(2).addMonsterToGraveYard(nameOfAttacker);
//    }
//
//    public static void suijin(String nameOfAttacker) {////6
//        if (!model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Suijin").getIsUsed &&
//                model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Suijin").getBackAndForth().equals("face up")) {
//            model.GameMatModel.getGameMatModelByNumber(2).getMonsterByNameZone(nameOfAttacker).setAttack(0);
//        }
//
//    }
//
//    public static void manEaterBug() {//6
//        Random rand = new Random();
//        int intRandom = rand.nextInt(4) + 1;
//        String[] keys = model.GameMatModel.getGameMatModelByNumber(2).getAllMonstersZone().keySet();
//        model.GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(keys[intRandom]);
//    }
//
//    public static void gateGuardian(String victim1, String victim2, String victim3) {//6
//        model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim1);
//        model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim2);
//        model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterHand(victim3);
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
//        if (model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Marshmallon").getBackAndForth().equals("face down")) {
//            model.Player.getPlayerByname(rivalName).decreaseLP(1000);
//        }
//    }
//
//    public static void beastKingBarbaros(String kindOfSummon, String victim1, String victim2, String victim3) {//kodoom haula?
//        if (kindOfSummon.equals("Normal")) {
//            model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Beast King Barbaros").setAttack(1900);
//        }
//        if (kindOfSummon.equals("notNormal")) {
//            model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim1);
//            model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim2);
//            model.GameMatModel.getGameMatModelByNumber(1).deleteMonsterZone(victim3);
//            HashMap<String, MonsterCardZone> monsters = model.GameMatModel.getGameMatModelByNumber(2).getAllMonsterZone();
//            HashMap<String, SpeellCardZone> spells = model.GameMatModel.getGameMatModelByNumber(2).getAllSpellZone();
//            HashMap<String, TrapCardZone> traps = model.GameMatModel.getGameMatModelByNumber(2).getAllTrapZone();
//            String[] monster = monsters.keySet().toArray(new String[0]);
//            String[] trap = traps.keySet().toArray(new String[0]);
//            String[] spell = spells.keySet().toArray(new String[0]);
//            for (String s : monster) {
//                model.GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(s);
//                model.GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//            for (String s : trap) {
//                model.GameMatModel.getGameMatModelByNumber(2).deleteTrapZone(s);
//                model.GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//            for (String s : spell) {
//                model.GameMatModel.getGameMatModelByNumber(2).deleteSpellZone(s);
//                model.GameMatModel.getGameMatModelByNumber(2).addToGraveYard(s);
//            }
//        }
//    }
//
//    public static void texchanger(String summonCard) {//6
//        if (!model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Texchanger").getIsUsed) {
//            model.GameMatModel.getGameMatModelByNumber(1).setIsAttackCanceled(true);
//            model.GameMatModel.getGameMatModelByNumber(1).addToMonsterZone(summonCard);
//        }
//    }
//
//    public static void theCalculator() {//6
//        HashMap<String, MonsterCardZone> monsters = model.GameMatModel.getGameMatModelByNumber(1).getAllMonsterZone();
//        String[] monster = monsters.keySet().toArray(new String[0]);
//        int attack = 0;
//        for (String s : monster) {
//            if (model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone(s).getBackAndForth().equals("face up")) {
//                attack += model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone(s).getMonsterLevel();
//            }
//        }
//        attack *= 300;
//        model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("The Calculator").setAttack(attack);
//
//    }
//
//    public static void mirageDragon() {//6
//        if (model.GameMatModel.getGameMatModelByNumber(1).getMonsterByNameZone("Mirage Dragon").getBackAndForth().equals("face up")) {
//            model.GameMatModel.getGameMatModelByNumber(2).setcanSummonTrap(false);
//        }
//    }
//
//    public static void heraldOfCreation() {//moshkel
//
//    }
//
//    public static void exploderDragon(String nameOfAttacker) {//seda kardanesh shrt dare
//        model.GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(nameOfAttacker);
//        model.GameMatModel.getGameMatModelByNumber(2).addToGraveYard(nameOfAttacker);
//
//    }
//
//    public static void terratigerTheEmpoweredWarrior() {///piade sazi dar model.GameMatModel
//
//    }
//
//    public static void theTricky() {//piade sazi dar model.GameMatModel
//
//    }

}

