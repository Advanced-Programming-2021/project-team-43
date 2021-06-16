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
        switch (spellName) {
            case "Monster Reborn":
                return monsterReborn(onlineUser, rivalUser, ownGameMat);
            case "Terraforming":
                return terraforming(onlineUser, player);
            case "Pot of Greed":
                return potOfGreed(onlineUser, player);
            case "Raigeki":
                return raigeki(rivalUser);
            case "Change of Heart":
                return changeOfHeart(onlineUser, rivalUser);
            case "Harpie's Feather Duster":
                return harpieFeatherDuster(rivalUser);
            case "Swords of Revealing Light":
                return swordsOfRevealingLight(rivalUser, ownSpell.getAddress());
            case "Dark Hole":
                return darkHole(rivalUser, onlineUser);
            default:
                return 0;
        }
    }

    public static int quickPlayEffectController(SpellTrapZoneCard ownSpell, String onlineUser, String rivalUser) {
        String spellName = ownSpell.getSpellTrapName();
        if (spellName.equals("Twin Twisters"))
            return twinTwisters(onlineUser, rivalUser);
        else if (spellName.equals("Mystical space typhoon"))
            return mysticalSpaceTyphoon(onlineUser, rivalUser);
        else
            return 0;
    }

    public static void equipEffectController(SpellTrapZoneCard ownSpell, String onlineUser, String rivalUser) {
        String spellName = ownSpell.getSpellTrapName();
        switch (spellName) {
            case "Sword of dark destruction":
                swordOfDarkDestruction(onlineUser, ownSpell, rivalUser);
                break;
            case "Black Pendant":
                blackPendant(onlineUser, ownSpell, rivalUser);
                break;
            case "United We Stand":
                unitedWeStand(onlineUser, ownSpell, rivalUser);
                break;
            case "Magnum Shield":
                magnumShield(onlineUser, ownSpell, rivalUser);
                break;
        }
    }

    public static void fieldEffectController(String spellName, String onlineUser, String rivalUser) {
        switch (spellName) {
            case "Yami":
                yami(onlineUser, rivalUser);
                break;
            case "Forest":
                forest(onlineUser, rivalUser);
                break;
            case "Closed Forest":
                closedForest(onlineUser, rivalUser);
                break;
            case "Umiiruka":
                umiiruka(onlineUser, rivalUser);
                break;
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
                return 0;
            }
            chosenAddress = getResponseForDeadCardToReborn(response, ownGameMat, rivalGameMat);
            if (chosenAddress != -1) {
                if (response.equals("own")) {
                    GameMatController.addToMonsterZoneCard(ownGameMat.getDeadCardNameByAddress(chosenAddress), "OO");
                    ownGameMat.removeFromGraveyardByAddress(chosenAddress);
                } else {
                    GameMatController.addToMonsterZoneCard(rivalGameMat.getDeadCardNameByAddress(chosenAddress), "OO");
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
                return -1;
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

    public static int terraforming(String onlineUser, Player player) {
        if (!player.doesThisModelAndTypeExist("Spell", "Field")) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no Field Spell in Your Main Deck!");
            return 0;
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
        if (player.getNumberOfMainDeckCards() < 2) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no enough card in Your Main Deck!");
            return 0;
        }
        else {
            String cardName = player.drawCard(false);
            new HandCardZone(onlineUser, cardName);
            cardName = player.drawCard(false);
            new HandCardZone(onlineUser, cardName);
            return 1;
        }
    }

    public static int raigeki(String rivalUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        if (keys.length == 0) {
            GameMatView.showInput("Oops! Your rival doesnt have any monster in control to destroy!");
            return 0;
        }
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).removeMonsterFromZone();
        }
        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
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
        if (spell.length == 0) {
            GameMatView.showInput("Oops! Your rival doesnt have any spell in control to destroy!");
            return 0;
        }
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rivalUser).removeSpellTrapFromZone();
        }
        return 1;
    }

    private static int swordsOfRevealingLight(String rivalUser, int ownAddress) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        if (keys.length == 0) {
            GameMatView.showInput("Oops! Your rival doesnt have any monster in control to affect!");
            return 0;
        }
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
        GameMatModel.getGameMatByNickname(rivalUser).changeNumberOfDeadMonsterThisTurn();
        GameMatModel.getGameMatByNickname(onlineUser).changeNumberOfDeadMonsterThisTurn();
        return 1;
    }

    public static void spellAbsorption(String onlineUser) {
        Player.getPlayerByName(onlineUser).changeLifePoint(500);
    }

    public static void messengerOfPeace(String onlineUser, String rivalUser,int ownAddress) {
        Map<Integer, MonsterZoneCard> monsters;
        monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).setCanAttack(false);
                List<Integer> effectedMonsters = MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getAllEffectedMonster(onlineUser);
                effectedMonsters.add(ownAddress);
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).setAllEffectedMonster(onlineUser, effectedMonsters);
            }
        }
        monsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
        Integer[] rivalMonsterNames = monsters.keySet().toArray(new Integer[0]);
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getAttack() >= 1500) {
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).setCanAttack(false);
                List<Integer> effectedMonsters = MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).getAllEffectedMonster(rivalUser);
                effectedMonsters.add(ownAddress);
                MonsterZoneCard.getMonsterCardByAddress(rivalMonsterNames[i], rivalUser).setAllEffectedMonster(rivalUser, effectedMonsters);
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

    public static int twinTwisters(String onlineUser, String rivalUser) {
        System.out.println("twin twister");
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
        MonsterZoneCard monster;
        for (int i = 1; i < 6; i++) {
            monster = MonsterZoneCard.getMonsterCardByAddress(i, onlineUser);
            if (monster != null) {
                System.out.println(MonsterCard.getMonsterByName(monster.getMonsterName()).getMonsterType());
                if (MonsterCard.getMonsterByName(monster.getMonsterName()).getMonsterType().equals("Aqua")) {
                    monster.changeAttack(500);
                    monster.changeDefend(-400);
                }
            }
        }
        for (int i = 1; i < 6; i++) {
            monster = MonsterZoneCard.getMonsterCardByAddress(i, rivalUser);
            if (monster != null) {
                if (MonsterCard.getMonsterByName(monster.getMonsterName()).getMonsterType().equals("Aqua")) {
                    monster.changeAttack(500);
                    monster.changeDefend(-400);
                }
            }
        }
    }

    private static void swordOfDarkDestruction(String onlineUser, SpellTrapZoneCard ownSpell, String rivalUser) {
        Map<String, Integer> relatedMonster = new HashMap<>(ownSpell.getRelatedMonsterAddress());
        MonsterZoneCard monster;
        if (relatedMonster.get("own") != null)
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("own"), onlineUser);
        else
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("rival"), rivalUser);
        if (MonsterCard.getMonsterByName(monster.getMonsterName()).getMonsterType().equals("Spellcaster") || MonsterCard.getMonsterByName(monster.getMonsterName()).getMonsterType().equals("Fiend")) {
            monster.changeAttack(400);
            monster.changeDefend(-200);
        }
    }

    private static void blackPendant(String onlineUser, SpellTrapZoneCard ownSpell,  String rivalUser) {
        Map<String, Integer> relatedMonster = new HashMap<>(ownSpell.getRelatedMonsterAddress());
        if (relatedMonster.get("own") != null)
            MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("own"), onlineUser).changeAttack(500);
        else
            MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("rival"), rivalUser).changeAttack(500);
    }

    private static void unitedWeStand(String onlineUser, SpellTrapZoneCard ownSpell, String rivalUser) {
        Map<String, Integer> relatedMonster = new HashMap<>(ownSpell.getRelatedMonsterAddress());
        MonsterZoneCard monster;
        int counter = 0;
        if (relatedMonster.get("own") != null) {
            for (int i = 1; i < 6; i++) {
                monster = MonsterZoneCard.getMonsterCardByAddress(i, onlineUser);
                if (monster != null && monster.getAddress() != relatedMonster.get("own") && !monster.getMode().equals("DH"))
                    counter++;
            }
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("own"), onlineUser);
        }
        else  {
            for (int i = 1; i < 6; i++) {
                monster = MonsterZoneCard.getMonsterCardByAddress(i, rivalUser);
                if (monster != null && monster.getAddress() != relatedMonster.get("rival") && !monster.getMode().equals("DH"))
                    counter++;
            }
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("rival"), rivalUser);
        }
        monster.changeAttack(800 * counter);
        monster.changeDefend(800 * counter);
    }

    private static void magnumShield(String onlineUser, SpellTrapZoneCard ownSpell,  String rivalUser) {
        Map<String, Integer> relatedMonster = new HashMap<>(ownSpell.getRelatedMonsterAddress());
        MonsterZoneCard monster;
        if (relatedMonster.get("own") != null)
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("own"), onlineUser);
        else
            monster = MonsterZoneCard.getMonsterCardByAddress(relatedMonster.get("rival"), rivalUser);
        if (monster.getMode().equals("OO"))
            monster.changeAttack(monster.getDefend());
        else if (monster.getMode().equals("DO"))
            monster.changeDefend(monster.getAttack());
    }

}