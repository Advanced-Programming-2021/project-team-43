package main.java.controller;
import main.java.model.*;
import main.java.view.*;
import java.util.*;

public class SpellEffect {

    private static String command;
    private static int chosenAddress;


    public static void spellEffectController(int spellCardAddress, String player1, String rival) {
        String spellName = SpellTrapZoneCard.getSpellCardByAddress(spellCardAddress, player1).getSpellTrapName();
        Player player = Player.getPlayerByName(player1);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(player1);
        String[] split;
        switch (spellName) {
            case "MonsterReborn" -> monsterReborn(player1, rival, ownGameMat);
            case "Terraforming" -> terraforming(player1, player);
            case "Pot of Greed" -> potOfGreed(player1, player);
            case "Raigeki" -> raigeki(rival, player1, spellCardAddress);
            case "Change of Heart" -> changeOfHeart(player1, rival);
            case "Harpie’s Feather Duster" -> harpieFeatherDuster(rival);
            case "Swords of Revealing Light" -> swordsOfRevealingLight(rival, spellCardAddress, player1);
            case "Dark Hole" -> darkHole(rival, player1);
            case "Supply Squad" -> supplySquad(player1, player);
            case "Spell Absorption" -> spellAbsorption(player1);
            case "Messenger of peace" -> messengerOfPeace(player1, rival);
            case "Twin Twisters" -> {
                split = getAddress("Twin Twisters", player1, spellCardAddress).split(" ");
                if (split.length == 4)
                    twinTwisters(Integer.parseInt(split[1]), Integer.parseInt(split[3]), rival, split[0].matches("own"), player1, split[2].matches("own"), ownGameMat);
                else
                    twinTwisters(Integer.parseInt(split[1]), -1, rival, split[0].matches("own"), player1, false, ownGameMat);
            }
            case "Mystical space typhoon" -> {
                split = getAddress("Mystical space typhoon", player1, spellCardAddress).split(" ");
                mysticalSpaceTyphoon(Integer.parseInt(split[1]), rival, split[0].equals("own"), player1);
            }
            case "Ring of Defense" -> ringOfDefense();
            case "Yami" -> yami(rival, player1);
            case "Forest" -> forest(player1, rival);
            case "Closed Forest" -> closedForest(player1, rival);
            case "UMIIRUKA" -> umiiruka(player1, rival);
            case "Sword of Dark Destruction" -> {
                getAddress(spellName, player1, spellCardAddress);
                swordOfDarkDestruction(player1, spellCardAddress);
            }
            case "Black Pendant" -> {
                getAddress(spellName, player1, spellCardAddress);
                blackPendant(player1, spellCardAddress);
            }
            case "United We Stand" -> {
                getAddress(spellName, player1, spellCardAddress);
                unitedWeStand(player1, spellCardAddress);
            }
            case "Magnum Shield" -> {
                getAddress(spellName, player1, spellCardAddress);
                magnumShield(player1, spellCardAddress);
            }
        }

    }

    private static int chooseCardAddressFromZone(String fromWhere, String onlineUser, String rivalUser) {
        int numberOfDeadCard;
        switch (fromWhere) {
            case "ownGraveyard" -> {
                numberOfDeadCard = GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCards();
                GameMatView.showInput("Please enter the number of a Dead Monster from your graveyard to reborn: (from 1 to " + numberOfDeadCard);
                command = GameMatView.getCommand();
                while (!command.matches("\\d+")) {
                    GameMatView.showInput("Please enter correctly the number of your own Dead Monster to reborn: (from 1 to " + numberOfDeadCard);
                    command = GameMatView.getCommand();
                }
                return Integer.parseInt(command);
            }
            case "rivalGraveyard" -> {
                numberOfDeadCard = GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCards();
                GameMatView.showInput("Please enter the number of a Dead Monster from rival graveyard to reborn: (from 1 to " + numberOfDeadCard);
                command = GameMatView.getCommand();
                while (!command.matches("\\d+")) {
                    GameMatView.showInput("Please enter correctly the number of rival Dead Monster to reborn: (from 1 to " + numberOfDeadCard);
                    command = GameMatView.getCommand();
                }
                return Integer.parseInt(command);
            }
            case "rivalMonsterZone" -> {
                GameMatView.showInput("Please enter the address of one of the Rival Monster: ");
                command = GameMatView.getCommand();
                while (!command.matches("[1-5]") || MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(Integer.parseInt(command)) == null) {
                    GameMatView.showInput("Please enter the correct address of one of the Rival Monster:");
                    command = GameMatView.getCommand();
                }
                return Integer.parseInt(command);
            }
            case "hand" -> {
                GameMatView.showInput("Please enter the address of one of your Hand Cards: ");
                command = GameMatView.getCommand();
                while (!command.matches("\\d+") || HandCardZone.getNumberOfFullHouse(onlineUser) < Integer.parseInt(command)) {
                    GameMatView.showInput("Please enter the correct address of one of your Hand Cards:");
                    command = GameMatView.getCommand();
                }
                return Integer.parseInt(command);
            }
        }
        return 0;
    }

    private static String getAddress(String spellName, String onlineUser, int spellAddress) {
        SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(spellAddress, onlineUser);
        switch (spellName) {
            case "Twin Twisters":
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
            case "Mystical space typhoon":
                GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
                command = GameMatView.getCommand();
                while (!command.matches("own\\s+\\d+") && !command.matches("rival\\s+\\d+")) {
                    GameMatView.showInput("Please enter the address of Spell card you want to destroy: (own 1/rival 1)");
                    command = GameMatView.getCommand();
                }
                return command;
            case "Sword of Dark Destruction":
                if (!MonsterZoneCard.isThisMonsterTypeExisted("Fiend", onlineUser) && !MonsterZoneCard.isThisMonsterTypeExisted("Spellcaster", onlineUser)) {
                    GameMatView.showInput("You dont have any related Monster Type to equip!");
                    ownSpell.setRelatedMonsterAddress(-1);
                } else {
                    GameMatView.showInput("Please enter the address of a Fiend or Spellcaster Monster card you want to equip:");
                    command = GameMatView.getCommand();
                    MonsterZoneCard relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                    while (!command.matches("[1-5]") || relatedMonsterCard == null) {
                        if (!relatedMonsterCard.getMonsterType().equals("Fiend") && !relatedMonsterCard.getMonsterType().equals("Spellcaster"))
                            GameMatView.showInput("Please enter the address of a Spellcaster/Fiend Monster card to equip:");
                        else
                            GameMatView.showInput("Please enter the correct address of a Monster card to equip:");
                        command = GameMatView.getCommand();
                        relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                    }
                    ownSpell.setRelatedMonsterAddress(Integer.parseInt(command));
                }
                break;
            case "Magnum Shield":
                if (!MonsterZoneCard.isThisMonsterTypeExisted("Warrior", onlineUser)) {
                    GameMatView.showInput("You dont have any related Monster Type to equip!");
                    ownSpell.setRelatedMonsterAddress(-1);
                } else {
                    GameMatView.showInput("Please enter the address of a Warrior Monster card you want to equip:");
                    command = GameMatView.getCommand();
                    MonsterZoneCard relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                    while (!command.matches("[1-5]") || relatedMonsterCard == null) {
                        if (!relatedMonsterCard.getMonsterType().equals("Warrior"))
                            GameMatView.showInput("Please enter the address of a Warrior Monster card to equip:");
                        else
                            GameMatView.showInput("Please enter the correct address of a Monster card to equip:");
                        command = GameMatView.getCommand();
                        relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                    }
                    ownSpell.setRelatedMonsterAddress(Integer.parseInt(command));
                }
                break;
            default:
                GameMatView.showInput("Please enter the address of a Monster card you want to equip:");
                command = GameMatView.getCommand();
                MonsterZoneCard relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                while (!command.matches("[1-5]") || relatedMonsterCard == null) {
                    GameMatView.showInput("Please enter the correct address of a Monster card to equip:");
                    command = GameMatView.getCommand();
                    relatedMonsterCard = MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(command), onlineUser);
                }
                ownSpell.setRelatedMonsterAddress(Integer.parseInt(command));
                break;
        }
        return null;
    }

    private static void monsterReborn(String onlineUser, String rivalUser, GameMatModel ownGameMat) {//not completed
        GameMatView.showInput("Please choose from which graveyard you want to pick a Monster? (own/rival)");
        command = GameMatView.getCommand();
        while (!command.matches("own|rival")) {
            GameMatView.showInput("Please choose correctly from which graveyard you want to pick a Monster? (own/rival)");
            command = GameMatView.getCommand();
        }
        chosenAddress = chooseCardAddressFromZone(command + "Graveyard", onlineUser, rivalUser);
        ownGameMat.removeFromGraveyardByAddress(chosenAddress);
        ownGameMat.addToGraveyard("Monster Reborn");
       // GameMatController.specialSummon(command, chosenAddress);
    }

    private static void terraforming(String onlineUser, Player player) {//not completed print deck
        chosenAddress = player.getAddressOfFieldSpellFromDeck();
        if (chosenAddress == -1)
            GameMatView.showInput("You dont have any Field Spell in Your Deck");
        else {
            new HandCardZone(onlineUser, player.getCardNameByAddress(chosenAddress));
            player.removeFromMainDeckByAddress(chosenAddress);
        }
    }

    private static void potOfGreed(String onlineUser, Player player) {
        String cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);
        cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);

    }

    private static void raigeki(String rival, String player1, int ownAddress) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rival).removeMonsterFromZone();
        }
        SpellTrapZoneCard.getSpellCardByAddress(ownAddress,player1).removeSpellTrapFromZone();
    }

    private static void changeOfHeart(String onlineUser, String rivalUser) {//???
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("You cant control any rival Monster card because of no free space!");
        else {
            chosenAddress = chooseCardAddressFromZone("rivalMonsterZone", onlineUser, rivalUser);
            new MonsterZoneCard(onlineUser, MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).getMonsterName(), "OO", false);
            MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).removeMonsterFromZone();
        }

    }

    private static void harpieFeatherDuster(String rival) {
        Integer[] spell = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rival).keySet().toArray(new Integer[0]);
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rival).removeSpellTrapFromZone();
        }
    }

    private static void swordsOfRevealingLight(String rival, int ownAddress, String player1) {///????
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

    private static void darkHole(String rival, String player1) {
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

    private static void supplySquad(String onlineUser, Player player) {//????
        String cardName = player.drawCard(false);
        player.removeFromMainDeck();
        new HandCardZone(onlineUser, cardName);
    }

    private static void spellAbsorption(String player1) {
        Player.getPlayerByName(player1).changeLifePoint(500);
    }

    private static void messengerOfPeace(String player1, String rival) {//???
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

    private static void twinTwisters(int victim1Address, int victim2Address, String rival, boolean mine1, String player1, boolean mine2, GameMatModel ownGameMat) {
        chosenAddress = chooseCardAddressFromZone("hand",player1, rival);
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

    private static void mysticalSpaceTyphoon(int victimAddress, String rival, boolean mine, String player1) {
        if (mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, player1);
        }
        if (!mine) {
            SpellTrapZoneCard.getSpellCardByAddress(victimAddress, rival);
        }
    }

    private static void ringOfDefense() {//???

    }

    private static void yami(String rival, String player1) {
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

    private static void forest(String player1, String rival) {
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

    private static void closedForest(String player1, String rival) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        int increaseAttack = (GameMatModel.getGameMatByNickname(player1).getNumberOfDeadMonster() + GameMatModel.getGameMatByNickname(rival).getNumberOfDeadMonster()) * 100;
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).getMonsterName()).getMonsterType().equals("Beast-Type")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], player1).changeAttack(increaseAttack);
            }
        }
    }

    private static void umiiruka(String player1, String rival) {
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

    private static void swordOfDarkDestruction(String player1, int ownAddress) {
        int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsterAddress();

        if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getMonsterName()).getMonsterType().equals("Fiend")) {
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeAttack(400);
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeDefend(-200);

        }
    }

    private static void blackPendant(String player1, int ownAddress) {
        int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsterAddress();
        MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeAttack(500);
    }

    private static void unitedWeStand(String player1, int ownAddress) {
        int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsterAddress();
        Map<Integer, MonsterZoneCard> monstersZone = MonsterZoneCard.getAllMonstersByPlayerName(player1);
        Integer[] namesOfMonstersInZone = monstersZone.keySet().toArray(new Integer[0]);
        int counter = 0;
        for (int i = 0; i < monstersZone.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("DO") ||
                    MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], player1).getMode().equals("OO")) {
                counter++;
            }
        }
        int increaseAttackAndDefend = 800 * counter;
        MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeAttack(increaseAttackAndDefend);
        MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeDefend(increaseAttackAndDefend);
    }

    private static void magnumShield(String player1, int ownAddress) {
        int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, player1).getRelatedMonsterAddress();
        if (MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getMode().equals("OO")) {
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeAttack(
                    MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getDefend());

            if (MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getMode().equals("DO")) {
                MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).changeDefend(
                        MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, player1).getAttack());
            }
        }
    }

}