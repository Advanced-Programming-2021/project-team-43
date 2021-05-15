package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class SpellEffect {

    private static String response;
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
            case "Harpie’s Feather Duster" -> harpieFeatherDuster(rivalUser);
            case "Swords of Revealing Light" -> swordsOfRevealingLight(rivalUser, 1, onlineUser);
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
            case "Sword of Dark Destruction" -> swordOfDarkDestruction(onlineUser, ownSpell, rivalUser);
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
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            GameMatView.showInput("Please choose from which graveyard you want to pick a Monster? (own/rival)");
            response = GameMatView.getCommand();
            while (!response.matches("own|rival")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please choose a graveyard correctly: (own/rival)");
                response = GameMatView.getCommand();
            }
            if (response.equals("own")) {
                GameMatView.showInput("Please enter a Monster address from your graveyard to reborn:");
                response = GameMatView.getCommand();
                while (!response.matches("\\d+") || !ownGameMat.getKindOfDeadCardByAddress(Integer.parseInt(response)).equals("Monster")) {
                    if (response.equals("cancel"))
                        return 0;
                    GameMatView.showInput("Please choose a Monster from your graveyard correctly:");
                    response = GameMatView.getCommand();
                }
                chosenAddress = Integer.parseInt(response);
                ownGameMat.removeFromGraveyardByAddress(chosenAddress);
                GameMatController.addToMonsterZoneCard(ownGameMat.getNameOfDeadCardByAddress(chosenAddress), "OO");
            }
            else {
                GameMatView.showInput("Please enter a Monster address from rival graveyard to reborn:");
                response = GameMatView.getCommand();
                while (!response.matches("\\d+") || !rivalGameMat.getKindOfDeadCardByAddress(Integer.parseInt(response)).equals("Monster")) {
                    if (response.equals("cancel"))
                        return 0;
                    GameMatView.showInput("Please choose a Monster from rival graveyard correctly:");
                    response = GameMatView.getCommand();
                }
                chosenAddress = Integer.parseInt(response);
                rivalGameMat.removeFromGraveyardByAddress(chosenAddress);
                GameMatController.addToMonsterZoneCard(rivalGameMat.getNameOfDeadCardByAddress(chosenAddress), "OO");
            }
            GameMatView.showInput("Dead Monster reborn successfully!");
        }
        return 1;
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
            player.removeFromMainDeck();
            new HandCardZone(onlineUser, cardName);
            cardName = player.drawCard(false);
            player.removeFromMainDeck();
            new HandCardZone(onlineUser, cardName);
        }
        return 1;
    }

    private static int raigeki(String rivalUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.removeMonsterFromZone(rivalUser, key);
        }
        return 1;
    }

    private static int changeOfHeart(String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
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
            MonsterZoneCard.removeMonsterFromZone(rivalUser, chosenAddress);
            new MonsterZoneCard(onlineUser, MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).getMonsterName(), "OO", false, true);
        }
        return 1;
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
        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, onlineUser).getMode().equals("OO") ||
                MonsterZoneCard.getMonsterCardByAddress(ownAddress, onlineUser).getMode().equals("DO")) {
            for (int key : keys) {
                MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setCanAttack(false);
                List<Integer> effectedMonsters = MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).getAllEffectedMonster(rivalUser);
                effectedMonsters.add(ownAddress);
                MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setAllEffectedMonster(rivalUser, effectedMonsters);
            }
        }
        return 1;
    }//////?????/????

    private static int darkHole(String rivalUser, String onlineUser) {
        Map<Integer, MonsterZoneCard> monster;
        monster = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] monsterAddress = monster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monster.size(); i++) {
            MonsterZoneCard.removeMonsterFromZone(rivalUser, monsterAddress[i]);
        }
        monster = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownMonsterAddress = monster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monster.size(); i++) {
            MonsterZoneCard.removeMonsterFromZone(onlineUser, monsterAddress[i]);
        }
        return 1;
    }

    public static void supplySquad(String onlineUser) {///////////
        int continuousSpellAddress = SpellTrapZoneCard.getAddressOfSpellByIcon(onlineUser, "Continuous", "Supply Squad");
        Player player = Player.getPlayerByName(onlineUser);
        if (continuousSpellAddress != 0) {
            if (GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadMonsterThisTurn() != 0) {
                String cardName = player.drawCard(false);
                player.removeFromMainDeck();
                new HandCardZone(onlineUser, cardName);
                //call it end of each turn
            }
        }
    }

    public static void spellAbsorption(String onlineUser) {////////
        Player.getPlayerByName(onlineUser).changeLifePoint(500);
        //just check is this spell exist in activate method and call this
    }

    public static void messengerOfPeace(String onlineUser, String rivalUser) {////////
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
        //need standby phase change after it its dead set attack true
    }

    private static int twinTwisters(String onlineUser, String rivalUser) {
        GameMatView.showInput("Please enter the address of one of your Hand Card to remove:");
        response = GameMatView.getCommand();
        while (Integer.parseInt(response) < 1 || Integer.parseInt(response) > HandCardZone.getNumberOfFullHouse(onlineUser)) {
            if (response.equals("cancel"))
                break;
            GameMatView.showInput("Please enter the address correctly:");
            response = GameMatView.getCommand();
        }
        if (!response.equals("cancel")) {
            chosenAddress = Integer.parseInt(response);
            HandCardZone.removeFromHandCard(onlineUser, chosenAddress);
        }
        GameMatView.showInput("Please enter the number of Spell/Trap you want to destroy: (1 or 2)");
        response = GameMatView.getCommand();
        while (!response.matches("[1-2]")) {
            if (response.equals("cancel"))
                return 0;
            GameMatView.showInput("Please enter the number correctly: (1 or 2)");
            response = GameMatView.getCommand();
        }
        chosenAddress = Integer.parseInt(response);
        String spellTrapAddress;
        for (int i = 0; i < chosenAddress; i++) {
            GameMatView.showInput("Whose Spell/Trap you want to destroy: (own or rival)");
            response = GameMatView.getCommand();
            while (!response.matches("own|rival")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the answer correctly: (own or rival)");
                response = GameMatView.getCommand();
            }
            if (response.equals("own")) {
                GameMatView.showInput("Please enter the address of  your Spell/Trap to destroy:");
                spellTrapAddress = GameMatView.getCommand();
                while (!spellTrapAddress.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), onlineUser) == null) {
                    if (spellTrapAddress.equals("cancel"))
                        return 0;
                    GameMatView.showInput("Please enter the address correctly:");
                    spellTrapAddress = GameMatView.getCommand();
                }
                SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), onlineUser).removeSpellTrapFromZone();
            }
            else {
                GameMatView.showInput("Please enter the address of rival Spell/Trap to destroy:");
                spellTrapAddress = GameMatView.getCommand();
                while (!spellTrapAddress.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), rivalUser) == null) {
                    if (spellTrapAddress.equals("cancel"))
                        return 0;
                    GameMatView.showInput("Please enter the address correctly:");
                    spellTrapAddress = GameMatView.getCommand();
                }
                SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), rivalUser).removeSpellTrapFromZone();
            }
        }
        return 1;
    }///refactor

    private static int mysticalSpaceTyphoon(String onlineUser, String rivalUser) {
        GameMatView.showInput("Whose Spell/Trap you want to destroy: (own or rival)");
        response = GameMatView.getCommand();
        while (!response.matches("own|rival")) {
            if (response.equals("cancel"))
                return 0;
            GameMatView.showInput("Please enter the answer correctly: (own or rival)");
            response = GameMatView.getCommand();
        }
        if (response.equals("own")) {
            GameMatView.showInput("Please enter the address of  your Spell/Trap to destroy:");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the address correctly:");
                response = GameMatView.getCommand();
            }
            SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), onlineUser).removeSpellTrapFromZone();
        }
        else {
            GameMatView.showInput("Please enter the address of rival Spell/Trap to destroy:");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the address correctly:");
                response = GameMatView.getCommand();
            }
            SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), rivalUser).removeSpellTrapFromZone();
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
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast-Type")) {
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
            int ownRelatedMonsterAddress = relatedMonster.get(onlineUser);
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Fiend")) {
                MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeAttack(400);
                MonsterZoneCard.getMonsterCardByAddress(ownRelatedMonsterAddress, onlineUser).changeDefend(-200);
            }
        }
        if (key[0].equals("rival")) {
            int rivalRelatedMonsterAddress = relatedMonster.get(rivalUser);
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
