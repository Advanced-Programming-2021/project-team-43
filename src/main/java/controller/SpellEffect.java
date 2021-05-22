package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class SpellEffect {

    private static String response;
    private static String responseTwo;
    private static int chosenAddress;

    public static int normalEffectController(SpellTrapZoneCard ownSpell, String onlineUser, String rivalUser) {
        String spellName = ownSpell.getSpellTrapName();
        Player player = Player.getPlayerByName(onlineUser);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        return switch (spellName) {
            case "Monster Reborn" -> monsterReborn(onlineUser, rivalUser, ownGameMat);
            case "Terraforming" -> terraforming(onlineUser, player);
            case "Pot of Greed" -> potOfGreed(onlineUser, player);
            case "Raigeki" -> raigeki(rivalUser);
            case "Change of Heart" -> changeOfHeart(onlineUser, rivalUser);
            case "Harpie's Feather Duster" -> harpieFeatherDuster(rivalUser);
            case "Swords of Revealing Light" -> swordsOfRevealingLight(rivalUser, ownSpell.getAddress(), onlineUser);
            case "Dark Hole" -> darkHole(rivalUser, onlineUser);
            default -> 0;
        };
    }

    public static int quickPlayEffectController(SpellTrapZoneCard ownSpell, String onlineUser, String rivalUser) {
        String spellName = ownSpell.getSpellTrapName();
        return switch (spellName) {
            case "Twin Twisters" -> twinTwisters(onlineUser, rivalUser);
            case "Mystical space typhoon" -> mysticalSpaceTyphoon(onlineUser, rivalUser);
            default -> 0;
        };
    }

    public static void equipEffectController(SpellTrapZoneCard ownSpell, String onlineUser, String rivalUser) {
        String spellName = ownSpell.getSpellTrapName();
        switch (spellName) {
            case "Sword of dark destruction" -> swordOfDarkDestruction(onlineUser, ownSpell, rivalUser);
            case "Black Pendant" -> blackPendant(onlineUser, ownSpell, rivalUser);
            case "United We Stand" -> unitedWeStand(onlineUser, ownSpell, rivalUser);
            case "Magnum Shield" -> magnumShield(onlineUser, ownSpell, rivalUser);
        }
    }

    public static void fieldEffectController(String spellName, String onlineUser, String rivalUser) {
        switch (spellName) {
            case "Yami" -> yami(onlineUser, rivalUser);
            case "Forest" -> forest(onlineUser, rivalUser);
            case "Closed Forest" -> closedForest(onlineUser, rivalUser);
            case "UMIIRUKA" -> umiiruka(onlineUser, rivalUser);
        }
    }

    private static int monsterReborn(String onlineUser, String rivalUser, GameMatModel ownGameMat) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            do {
                GameMatView.showInput("Please choose from which graveyard you want to pick a Monster? (own/rival)");
                response = GameMatView.getCommand();
                if (response.equals("cancel"))
                    return 0;
            } while (!response.matches("own|rival"));
            if ((response.equals("own") && ownGameMat.getNumberOfDeadCards() == 0) || (response.equals("rival") && rivalGameMat.getNumberOfDeadCards() == 0)) {
                GameMatView.showInput("Oops! You cant use this Spell effect because of no dead Card in your " + response + " graveyard!");
                return 1;
            }
            chosenAddress = getResponseForDeadCardToReborn(response, ownGameMat, rivalGameMat);
            if (chosenAddress != 0) {
                chosenAddress--;
                if (response.equals("own")) {
                    GameMatController.addToMonsterZoneCard(ownGameMat.getNameOfDeadCardByAddress(chosenAddress), "OO");
                    ownGameMat.removeFromGraveyardByAddress(chosenAddress);
                } else {
                    GameMatController.addToMonsterZoneCard(rivalGameMat.getNameOfDeadCardByAddress(chosenAddress), "OO");
                    rivalGameMat.removeFromGraveyardByAddress(chosenAddress);
                }
                GameMatView.showInput("Dead Monster reborn successfully!");
            }
        }
        return 1;
    }

    private static int getResponseForDeadCardToReborn(String whoseGraveyard, GameMatModel ownGameMat, GameMatModel rivalGameMat) {
        int deadCardAddress;
        while (true) {
            GameMatView.showInput("Please enter a Monster address from your " + whoseGraveyard + " graveyard to reborn: ");
            responseTwo = GameMatView.getCommand();
            if (responseTwo.equals("cancel"))
                return 0;
            if (!responseTwo.matches("[^0][0-9]*"))
                continue;
            deadCardAddress = Integer.parseInt(responseTwo);
            deadCardAddress--;
            if (whoseGraveyard.equals("own") && ownGameMat.getDeadCardNameByAddress(deadCardAddress) != null) {
                if (Card.getCardsByName(ownGameMat.getDeadCardNameByAddress(deadCardAddress)).getCardModel().equals("Monster"))
                    break;
            }
            else if (whoseGraveyard.equals("rival") && rivalGameMat.getDeadCardNameByAddress(deadCardAddress) != null) {
                if (Card.getCardsByName(rivalGameMat.getDeadCardNameByAddress(deadCardAddress)).getCardModel().equals("Monster"))
                    break;
            }
        }
        return deadCardAddress;
    }

    private static int terraforming(String onlineUser, Player player) {
        if (!player.doesThisModelAndTypeExist("Spell", "Field")) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no Field Spell in Your Main Deck!");
        }
        else {
            GameMatView.showInput("Please enter the address of a Field Spell in your Main Deck to add to your Hand Cards: ");
            response = GameMatView.getCommand();
            while (!response.matches("\\d+") || !player.doesAddressTypeMatchInMainDeck(Integer.parseInt(response), "Spell", "Field")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please choose a Field Spell from your Main Deck correctly: ");
                response = GameMatView.getCommand();
            }
            chosenAddress = Integer.parseInt(response);
            new HandCardZone(onlineUser, player.getCardNameByAddress(chosenAddress));
            player.removeFromMainDeckByAddress(chosenAddress);
        }
        return 1;
    }

    private static int potOfGreed(String onlineUser, Player player) {
        if (player.getNumberOfMainDeckCards() < 2)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no enough card in Your Main Deck!");
        else {
            String cardName = player.drawCard(false);
            new HandCardZone(onlineUser, cardName);
            cardName = player.drawCard(false);
            new HandCardZone(onlineUser, cardName);
        }
        return 1;
    }

    private static int raigeki(String rivalUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).removeMonsterFromZone();
        }
        return 1;
    }

    private static int changeOfHeart(String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
        else if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) == 0)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no Monster in rival Zone!");
        else {
            GameMatView.showInput("Please enter the address of one of the Rival Monster to control in your Turn: ");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response),rivalUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the Monster address correctly: ");
                response = GameMatView.getCommand();
            }
            chosenAddress = Integer.parseInt(response);
            new MonsterZoneCard(onlineUser, MonsterZoneCard.getMonsterCardByAddress(chosenAddress, rivalUser).getMonsterName(), "OO", false, true);
            MonsterZoneCard.getMonsterCardByAddress(chosenAddress, rivalUser).removeMonsterFromZone();
            return 1;
        }
        return 0;
    }

    private static int harpieFeatherDuster(String rivalUser) {
        Integer[] spell = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rivalUser).removeSpellTrapFromZone();
        }
        return 1;
    }

    private static int swordsOfRevealingLight(String rivalUser, int ownAddress, String onlineUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setMode("DO");
        }
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setCanAttack(false);
            List<Integer> effectedMonsters = MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).getAllEffectedMonster(rivalUser);
            effectedMonsters.add(ownAddress);
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setAllEffectedMonster(rivalUser, effectedMonsters);
        }
        return 1;
    }

    public static void returnPermission(int ownAddress, String rival) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] addresses = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            List<Integer> spells = monsters.get(addresses[i]).getAllEffectedMonster(rival);
            if (spells.size() == 1 && spells.get(0) == ownAddress) {
                MonsterZoneCard.getMonsterCardByAddress(addresses[i], rival).setCanAttack(true);
            }
        }
    }

    private static int darkHole(String rivalUser, String onlineUser) {
        Map<Integer, MonsterZoneCard> monster;
        monster = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalMonsterAddress = monster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).removeMonsterFromZone();
        }
        monster = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownMonsterAddress = monster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser);
        }
        return 1;
    }

    public static void supplySquad(String onlineUser) {
        Player player = Player.getPlayerByName(onlineUser);
        if (GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadMonsterThisTurn() != 0)
            new HandCardZone(onlineUser, player.drawCard(true));
    }

    public static void spellAbsorption(String onlineUser) {
        Player.getPlayerByName(onlineUser).changeLifePoint(500);
    }

    public static void messengerOfPeace(String onlineUser, String rivalUser) {
        Map<Integer, MonsterZoneCard> monsters;
        monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).setCanAttack(false);
            }
        }
        monsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalMonsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).setCanAttack(false);
            }
        }
    }

    public static void returnPermissionMessenger(int ownAddress, String rival,String onlineUser) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rival);
        Integer[] addresses = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            List<Integer> spells = monsters.get(addresses[i]).getAllEffectedMonster(rival);
            if (spells.size() == 1 && spells.get(0) == ownAddress) {
                MonsterZoneCard.getMonsterCardByAddress(addresses[i], rival).setCanAttack(true);
            }
        }
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownAddresses = ownMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            List<Integer> spells = ownMonsters.get(ownAddresses[i]).getAllEffectedMonster(onlineUser);
            if (spells.size() == 1 && spells.get(0) == ownAddress) {
                MonsterZoneCard.getMonsterCardByAddress(ownAddresses[i], onlineUser).setCanAttack(true);
            }
        }
    }

    private static int twinTwisters(String onlineUser, String rivalUser) {
        if (HandCardZone.getNumberOfFullHouse(onlineUser) == 0)
            GameMatView.showInput("Oops! You dont have any card in your Hand to drop!");
        else {
            while (true) {
                GameMatView.showInput("Please enter the address of one of your Hand Card to remove:");
                response = GameMatView.getCommand();
                if (response.equals("cancel"))
                    return 0;
                if (!response.matches("[1-7]"))
                    continue;
                chosenAddress = Integer.parseInt(response);
                if (chosenAddress <= HandCardZone.getNumberOfFullHouse(onlineUser))
                    break;
            }
            chosenAddress = Integer.parseInt(response);
            HandCardZone.getHandCardByAddress(chosenAddress-1, onlineUser).removeFromHandCard();
        }
        do {
            GameMatView.showInput("Please enter the number of Spell/Trap you want to destroy: (1 or 2)");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
        } while (!response.matches("[1-2]"));
        chosenAddress = Integer.parseInt(response);
        for (int i = 0; i < chosenAddress; i++) {
            do {
                GameMatView.showInput("Whose Spell/Trap you want to destroy: (own or rival)");
                response = GameMatView.getCommand();
                if (response.equals("cancel"))
                    return 0;
            } while (!response.matches("own|rival"));
            while (true) {
                GameMatView.showInput("Please enter the address of your " + response + " Spell/Trap to destroy:");
                responseTwo = GameMatView.getCommand();
                if (responseTwo.equals("cancel"))
                    return 0;
                if (!responseTwo.matches("[1-5]"))
                    continue;
                if (response.equals("own"))
                    if (SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(responseTwo), onlineUser) != null)
                        break;
                    else
                    if (SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(responseTwo), rivalUser) != null)
                        break;
            }
            if (response.equals("own"))
                SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(responseTwo), onlineUser).removeSpellTrapFromZone();
            else
                SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(responseTwo), rivalUser).removeSpellTrapFromZone();
        }
        return 1;
    }

    private static int mysticalSpaceTyphoon(String onlineUser, String rivalUser) {
        int address;
        do {
            GameMatView.showInput("Whose Spell/Trap you want to destroy: (own or rival)");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
        } while (!response.matches("own|rival"));
        if (response.equals("own")) {
            while (true) {
                GameMatView.showInput("Please enter the address of your " + response + " Spell/Trap to destroy:");
                responseTwo = GameMatView.getCommand();
                if (responseTwo.equals("cancel"))
                    return 0;
                if (!responseTwo.matches("[1-5]"))
                    continue;
                address = Integer.parseInt(responseTwo);
                if (response.equals("own"))
                    if (SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser) != null)
                        break;
                    else
                    if (SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser) != null)
                        break;
            }
            if (response.equals("own"))
                SpellTrapZoneCard.getSpellCardByAddress(address, onlineUser).removeSpellTrapFromZone();
            else
                SpellTrapZoneCard.getSpellCardByAddress(address, rivalUser).removeSpellTrapFromZone();
        }
        return 1;
    }

    private static void yami(String onlineUser, String rivalUser) {
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownMonsterAddress = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalMonsterAddress = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).changeDefend(-200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).getMonsterName()).getMonsterType().equals("Fiend") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).getMonsterName()).getMonsterType().equals("Spellcaster")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).changeDefend(200);
            }
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).getMonsterName()).getMonsterType().equals("Fairy")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).changeAttack(-200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterAddress[i], rivalUser).changeDefend(-200);
            }
        }
    }

    private static void forest(String onlineUser, String rivalUser) {
        Map<Integer, MonsterZoneCard> ownMonsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownMonsterNames = ownMonsters.keySet().toArray(new Integer[0]);
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], onlineUser).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(ownMonsterNames[i], onlineUser).changeDefend(200);
            }
        }
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getMonsterName()).getMonsterType().equals("Insect") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getMonsterName()).getMonsterType().equals("Beast") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getMonsterName()).getMonsterType().equals("Beast-Warrior")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).changeAttack(200);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).changeDefend(200);
            }
        }
    }

    private static void closedForest(String onlineUser, String rivalUser) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        int increaseAttack = (GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadCardByModel("Monster") + GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadCardByModel("Monster")) * 100;
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast-Warrior") || MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).changeAttack(increaseAttack);
            }
        }
    }

    private static void umiiruka(String onlineUser, String rivalUser) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).changeDefend(-400);
            }
        }
        Map<Integer, MonsterZoneCard> rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalsMonsterNames = rivalsMonsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rivalUser).getMonsterName()).getMonsterType().equals("Aqua")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rivalUser).changeAttack(500);
                MonsterZoneCard.getMonsterCardByAddress(rivalsMonsterNames[i], rivalUser).changeDefend(-400);
            }
        }
    }

    private static void swordOfDarkDestruction(String onlineUser, SpellTrapZoneCard ownSpell, String rivalUser) {
        Map<String, Integer> relatedMonster = ownSpell.getRelatedMonsterAddress();
        String[] key = relatedMonster.keySet().toArray(new String[0]);
        if (key[0].equals("own")) {
            int ownRelatedMonsterAddress = relatedMonster.get("own");
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Fiend")) {
                MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeAttack(400);
                MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeDefend(-200);
            }
        }
        if (key[0].equals("rival")) {
            int rivalRelatedMonsterAddress = relatedMonster.get("rival");
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getMonsterName()).getMonsterType().equals("Fiend")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeAttack(400);
                MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeDefend(-200);
            }
        }
    }

    private static void blackPendant(String onlineUser, SpellTrapZoneCard ownSpell,  String rivalUser) {
        Map<String, Integer> relatedMonster = ownSpell.getRelatedMonsterAddress();
        String[] key = relatedMonster.keySet().toArray(new String[0]);
        if (key[0].equals("own")) {
            int ownRelatedMonsterAddress = relatedMonster.get(onlineUser);
            MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeAttack(500);
        }
        if (key[0].equals("rival")) {
            int rivalRelatedMonsterAddress = relatedMonster.get(rivalUser);
            MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeAttack(500);
        }
    }

    private static void unitedWeStand(String onlineUser, SpellTrapZoneCard ownSpell, String rivalUser) {
        Map<String, Integer> relatedMonster = ownSpell.getRelatedMonsterAddress();
        String[] key = relatedMonster.keySet().toArray(new String[0]);
        Map<Integer, MonsterZoneCard> monstersZone = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] namesOfMonstersInZone = monstersZone.keySet().toArray(new Integer[0]);
        int counter = 0;
        for (int i = 0; i < monstersZone.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], onlineUser).getMode().equals("DO") ||
                    MonsterZoneCard.getMonsterCardByAddress(namesOfMonstersInZone[i], onlineUser).getMode().equals("OO")) {
                counter++;
            }
        }
        int increaseAttackAndDefend = 800 * counter;

        if (key[0].equals("own")) {
            int ownRelatedMonsterAddress = relatedMonster.get(onlineUser);
            MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeAttack(increaseAttackAndDefend);
            MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeDefend(increaseAttackAndDefend);
        }
        if (key[0].equals("rival")) {
            int rivalRelatedMonsterAddress = relatedMonster.get(rivalUser);
            MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeAttack(increaseAttackAndDefend);
            MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeDefend(increaseAttackAndDefend);
        }
    }

    private static void magnumShield(String onlineUser, SpellTrapZoneCard ownSpell,  String rivalUser) {
        Map<String, Integer> relatedMonster = ownSpell.getRelatedMonsterAddress();
        String[] key = relatedMonster.keySet().toArray(new String[0]);
        if (key[0].equals("own")) {
            int ownRelatedMonsterAddress = relatedMonster.get(onlineUser);
            if (MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMode().equals("OO")) {
                MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeAttack(
                        MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getDefend());

                if (MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMode().equals("DO")) {
                    MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeDefend(
                            MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getAttack());
                }
            }
        }
        if (key[0].equals("rival")) {
            int rivalRelatedMonsterAddress = relatedMonster.get(rivalUser);
            if (MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getMode().equals("OO")) {
                MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeAttack(
                        MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getDefend());

                if (MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getMode().equals("DO")) {
                    MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).changeDefend(
                            MonsterZoneCard.getMonsterCardByAddress(rivalRelatedMonsterAddress, rivalUser).getAttack());
                }
            }
        }
    }

}