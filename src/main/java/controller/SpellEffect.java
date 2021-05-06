package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class SpellEffect {

    private static String command;
    private static int chosenAddress;


    public static void spellEffectController(int spellCardAddress, String player1, String rival) {
        String spellName = SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName();
        Player player = Player.getPlayerByName(player1);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(player1);
        String[] split;
        if (spellName.equals("MonsterReborn"))
            monsterReborn(player1, rival, ownGameMat);
        if (spellName.equals("Terraforming"))
            terraforming(player1, player, ownGameMat);
        if (spellName.equals("Pot of Greed"))
            potOfGreed(player1, player);
        if (spellName.equals("Raigeki"))
            raigeki(rival, player1, spellCardAddress);
        if (spellName.equals("Change of Heart"))
            changeOfHeart(player1, rival);
        if (spellName.equals("Harpie’s Feather Duster"))
            harpieFeatherDuster(rival);
        if (spellName.equals("Swords of Revealing Light"))
            swordsOfRevealingLight(rival, spellCardAddress, player1);
        if (spellName.equals("Dark Hole"))
            darkHole(rival, player1);
        if (spellName.equals("Supply Squad"))
            supplySquad(player1, player);
        if (spellName.equals("Spell Absorption"))
            spellAbsorption(player1);
        if (spellName.equals("Messenger of peace"))
            messengerOfPeace(player1, rival);
        if (spellName.equals("Twin Twisters")) {
            split = getAddress("Twin Twisters").split(" ");
            if (split.length == 4)
                twinTwisters(Integer.parseInt(split[1]), Integer.parseInt(split[3]), rival, split[0].matches("own"), player1, split[2].matches("own"), ownGameMat);
            else
                twinTwisters(Integer.parseInt(split[1]), -1, rival, split[0].matches("own"), player1, false, ownGameMat);
        }
        if (spellName.equals("Mystical space typhoon")) {
            split = getAddress("Mystical space typhoon").split(" ");
            mysticalSpaceTyphoon(Integer.parseInt(split[1]), rival, split[0].equals("own"), player1);
        }
        if (spellName.equals("Ring of Defense"))
            ringOfDefense();
        if (spellName.equals("Yami"))
            yami(rival, player1);
        if (spellName.equals("Forest"))
            forest(player1, rival);
        if (spellName.equals("Closed Forest"))
            closedForest(player1, rival);
        if (spellName.equals("UMIIRUKA"))
            UMIIRUKA(player1, rival);
        if (spellName.equals("Sword of Dark Destruction"))
            swordOfDarkDestruction(player1,spellCardAddress);
        if (spellName.equals("Black Pendant"))
            blackPendant(player1,spellCardAddress);
        if (spellName.equals("United We Stand"))
            unitedWeStand(player1,spellCardAddress);
        if (spellName.equals("Magnum Shield"))
            magnumShield(player1,spellCardAddress);

    }


    public static void monsterReborn(String onlineUser, String rivalUser, GameMatModel ownGameMat) {
        GameMatView.showInput("Please choose from which graveyard you want to pick a Monster? (own/rival)");
        command = GameMatView.getCommand();
        while (!command.matches("own|rival")) {
            GameMatView.showInput("Please choose correctly from which graveyard you want to pick a Monster? (own/rival)");
            command = GameMatView.getCommand();
        }
        chosenAddress = chooseCardAddress(command + "Graveyard", onlineUser, rivalUser);
        ownGameMat.removeFromGraveyardByAddress(chosenAddress);
        ownGameMat.addToGraveyard("Monster Reborn");
        GameMatController.specialSummon("ownGraveyard", Integer.parseInt(command));
    }

    public static void terraforming(String onlineUser, Player player, GameMatModel ownGameMat) {
        chosenAddress = player.getAddressOfFieldSpellFromDeck();
        if (chosenAddress == -1)
            GameMatView.showInput("You dont have any Field Spell in Your Deck");
        else {
            new HandCardZone(onlineUser, player.getCardNameByAddress(chosenAddress));
            player.removeFromMainDeckByAddress(chosenAddress);
        }
    }

    public static void potOfGreed(String onlineUser, Player player) {
        String cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);
        cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);
    }

    public static void raigeki(String rival, String player1, int ownAddress) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rival).removeMonsterFromZone();
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    public static void changeOfHeart(String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("You cant control any rival Monster card because of no free space!");
        else {
            chosenAddress = chooseCardAddress("rivalMonsterZone", onlineUser, rivalUser);
            new MonsterZoneCard(onlineUser, MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).getMonsterName(), "OO", false);
            MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).removeMonsterFromZone();
        }
    }

    public static void harpieFeatherDuster(String rival) {
        Integer[] spell = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rival).removeSpellTrapFromZone();
        }
    }

    public static void swordsOfRevealingLight(String rival, int ownAddress, String player1) {//Kamel3
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rival).setMode("DO");
        }
        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("OO") ||
                MonsterZoneCard.getMonsterCardByAddress(ownAddress, player1).getMode().equals("DO")) {
            for (int key : keys) {
                MonsterZoneCard.getMonsterCardByAddress(key, rival).setCanAttack(false);
            }
        }
    }

    public static void darkHole(String rival, String player1) {
        Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] monsterAddress = rivalsMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(monsterAddress[i], rival).removeMonsterFromZone();
        }
        Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterAddress = ownMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).removeMonsterFromZone();
        }
    }

    public static void supplySquad(String onlineUser, Player player) {
        String cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);
    }

    public static void spellAbsorption(String player1) {
        Player.getPlayerByName(player1).changeLifePoint(500);
    }

    public static void messengerOfPeace(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).setCanAttack(false);
            }
        }

        Map<Integer, MonsterZoneCard> rivalMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterNames = rivalMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalMonsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).setCanAttack(false);
            }
        }
    }

    public static int chooseCardAddress(String fromWhere, String onlineUser, String rivalUser) {
        int numberOfDeadCard;
        if (fromWhere.equals("ownGraveyard")) {
            numberOfDeadCard = GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards();
            GameMatView.showInput("Please enter the number of a Dead Monster from your graveyard to reborn: (from 1 to " + numberOfDeadCard);
            command = GameMatView.getCommand();
            while (!command.matches("\\d+")) {
                GameMatView.showInput("Please enter correctly the number of your own Dead Monster to reborn: (from 1 to " + numberOfDeadCard);
                command = GameMatView.getCommand();
            }
            return Integer.parseInt(command);
        }
        else if (fromWhere.equals("rivalGraveyard")) {
            numberOfDeadCard = GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards();
            GameMatView.showInput("Please enter the number of a Dead Monster from rival graveyard to reborn: (from 1 to " + numberOfDeadCard);
            command = GameMatView.getCommand();
            while (!command.matches("\\d+")) {
                GameMatView.showInput("Please enter correctly the number of rival Dead Monster to reborn: (from 1 to " + numberOfDeadCard);
                command = GameMatView.getCommand();
            }
            return Integer.parseInt(command);
        }
        else if (fromWhere.equals("rivalMonsterZone")) {
            GameMatView.showInput("Please enter the address of one of the Rival Monster: ");
            command = GameMatView.getCommand();
            while (!command.matches("[1-5]") || MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(Integer.parseInt(command)) == null) {
                GameMatView.showInput("Please enter the correct address of one of the Rival Monster:");
                command = GameMatView.getCommand();
            }
            return Integer.parseInt(command);
        }
        else if (fromWhere.equals("hand")) {
            GameMatView.showInput("Please enter the address of one of your Hand Cards: ");
            command = GameMatView.getCommand();
            while (!command.matches("\\d+") || HandCardZone.getNumberOfFullHouse(onlineUser) < Integer.parseInt(command)) {
                GameMatView.showInput("Please enter the correct address of one of your Hand Cards:");
                command = GameMatView.getCommand();
            }
            return Integer.parseInt(command);
        }
       return 0;
    }

    public static String getAddress(String spellName) {
        if (spellName.equals("Twin Twisters")) {
            GameMatView.showInput("Please enter the number of Spell/Trap card you want to destroy: (1 or 2)");
            command = GameMatView.getCommand();
            while (!command.matches("[1-2]")) {
                GameMatView.showInput("Please enter the number of Spell/Trap card you want to destroy: (1 or 2)");
                command = GameMatView.getCommand();
            }
            int counter = Integer.parseInt(command);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < counter; i++) {
                GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
                command = GameMatView.getCommand();
                while (!command.matches("own\\s+\\d+") && !command.matches("rival\\s+\\d+")) {
                    GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
                    command = GameMatView.getCommand();
                }
                result.append(command);
            }
            return result.toString();
        }
        else if (spellName.equals("Mystical space typhoon")) {
            GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
            command = GameMatView.getCommand();
            while (!command.matches("own\\s+\\d+") && !command.matches("rival\\s+\\d+")) {
                GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
                command = GameMatView.getCommand();
            }
            return command;
        }
        else {//equip
            GameMatView.showInput("Please enter the address of Monster card you want to equip:");
            command = GameMatView.getCommand();
            while (!command.matches("[1-5]")) {
                GameMatView.showInput("Please enter the address of Monster card you want to equip:");
                command = GameMatView.getCommand();
            }
            return command;
        }
    }
    public static void twinTwisters(int victim1Address, int victim2Address, String rival, boolean mine1, String player1, boolean mine2, GameMatModel ownGameMat) {
        chosenAddress = chooseCardAddress("hand",player1, rival);
        ownGameMat.addToGraveyard(HandCardZone.getHandCardByAddress(chosenAddress, player1).getCardName());
        HandCardZone.getHandCardByAddress(chosenAddress, player1).removeFromHandCard();
        if (mine1) {
            if (victim1Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim1Address, player1).removeSpellTrapFromZone();
            }
        }
        if (!mine1) {
            if (victim1Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim1Address, rival).removeSpellTrapFromZone();
            }
        }
        if (mine2) {
            if (victim2Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim2Address, player1).removeSpellTrapFromZone();
            }
        }
        if (!mine2) {
            if (victim2Address != -1) {
                SpellTrapZoneCard.getSpellCardByAddress(victim2Address, rival).removeSpellTrapFromZone();
            }
        }
    }

    public static void mysticalSpaceTyphoon(int victimAddress, String rival, boolean mine, String player1) {
        if (mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, player1);
        }
        if (!mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, rival);
        }
    }

    public static void ringOfDefense() {

    }
    public static void yami(String rival, String player1) {
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterAddress = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterAddress = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], player1).changeDefend(-200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rival).changeDefend(-200);
            }
        }
    }

    public static void forest(String player1, String rival) {
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] ownMonsterNames = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], player1).changeDefend(200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rival).changeDefend(200);
            }
        }
    }

    public static void closedForest(String player1, String rival) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        int increaseAttack = (GameMatModel.getGameMatByNickname(player1).getNumberOfDeadMonster() + GameMatModel.getGameMatByNickname(rival).getNumberOfDeadMonster()) * 100;
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast-Type")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeAttack(increaseAttack);
            }
        }
    }

    public static void UMIIRUKA(String player1, String rival) {//kamel3
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeDefend(-400);
            }
        }
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] rivalsMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rival).changeDefend(-400);
            }
        }
    }

    public static void swordOfDarkDestruction(String player1,int ownAddress) {//kamel3
//        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
//        for (Integer monster : monsters) {
//            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMonsterName()).getMonsterType().equals("Spellcaster") ||
//                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMonsterName()).getMonsterType().equals("Fiend")) {
//                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(400);
//                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(-200);
//            }
//        }
    }

    public static void blackPendant(String player1,int ownAddress) {//kamel3
//        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
//        for (Integer monster : monsters) {
//            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(500);
//        }
    }

    public static void unitedWeStand(String player1,int ownAddress) {//kamel3
//        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
//        Map<Integer, MonsterZoneCard> monstersZone = MonsterZoneCard.getAllMonstersByPlayerName(player1);
//        Integer[] namesOfMonstersInZone = monstersZone.keySet().toArray(new Integer[0]);
//        int counter = 0;
//        for (int i = 0; i < monstersZone.size(); i++) {
//            if (MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("DO") ||
//                    MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("OO")) {
//                counter++;
//            }
//        }
//        int increaseAttackAndDefend = 800 * counter;
//        for (Integer monster : monsters) {
//            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(increaseAttackAndDefend);
//            MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(increaseAttackAndDefend);
//        }
    }

    public static void magnumShield(String player1,int ownAddress) {//kamel3
//        ArrayList<Integer> monsters = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsters();
//        for (Integer monster : monsters) {
//            if (MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMode().equals("OO")) {
//                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeAttack(
//                        MonsterZoneCard.getMonsterCardByAddress(monster, player1).getDefend());
//            }
//            if (MonsterZoneCard.getMonsterCardByAddress(monster, player1).getMode().equals("DO")) {
//                MonsterZoneCard.getMonsterCardByAddress(monster, player1).changeDefend(
//                        MonsterZoneCard.getMonsterCardByAddress(monster, player1).getAttack());
//            }
//        }
    }
}
