package controller;
import model.*;
import view.GameMatView;
import java.util.*;

public class SpellEffect {

    private static String response;
    private static int chosenAddress;
    private static SpellTrapZoneCard spellCard;


    public static void normalEffectController(int spellAddress, String onlineUser, String rivalUser) {
        spellCard = SpellTrapZoneCard.getSpellCardByAddress(spellAddress, onlineUser);
        String spellName = spellCard.getSpellTrapName();
        Player player = Player.getPlayerByName(onlineUser);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        switch (spellName) {
            case "Monster Reborn" -> monsterReborn(onlineUser, rivalUser, ownGameMat);
            case "Terraforming" -> terraforming(onlineUser, player);
            case "Pot of Greed" -> potOfGreed(onlineUser, player);
            case "Raigeki" -> raigeki(rivalUser);
            case "Change of Heart" -> changeOfHeart(onlineUser, rivalUser);
            case "Harpie’s Feather Duster" -> harpieFeatherDuster(rivalUser);
            case "Swords of Revealing Light" -> swordsOfRevealingLight(rivalUser, spellAddress, onlineUser);
            case "Dark Hole" -> darkHole(rivalUser, onlineUser);
        }
    }

    private static void monsterReborn(String onlineUser, String rivalUser, GameMatModel ownGameMat) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
        }
        else {
            GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(rivalUser);
            GameMatView.showInput("Please choose from which graveyard you want to pick a Monster? (own/rival)");
            response = GameMatView.getCommand();
            while (!response.matches("own|rival")) {
                if (response.equals("cancel"))
                    return;
                GameMatView.showInput("Please choose a graveyard correctly: (own/rival)");
                response = GameMatView.getCommand();
            }
            if (response.equals("own")) {
                GameMatView.showInput("Please enter a Monster address from your graveyard to reborn:");
                response = GameMatView.getCommand();
                while (!response.matches("\\d+") || !ownGameMat.getKindOfDeadCardByAddress(Integer.parseInt(response)).equals("Monster")) {
                    if (response.equals("cancel"))
                        return;
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
                        return;
                    GameMatView.showInput("Please choose a Monster from rival graveyard correctly:");
                    response = GameMatView.getCommand();
                }
                chosenAddress = Integer.parseInt(response);
                rivalGameMat.removeFromGraveyardByAddress(chosenAddress);
                GameMatController.addToMonsterZoneCard(rivalGameMat.getNameOfDeadCardByAddress(chosenAddress), "OO");
            }
            GameMatView.showInput("Dead Monster reborn successfully!");
        }
    }

    private static void terraforming(String onlineUser, Player player) {
        if (!player.doesThisIconExistInMainDeck("Field")) {
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no Field Spell in Your Main Deck!");
        }
        else {
            GameMatView.showInput("Please enter the address of a Field Spell in your Main Deck to add to your Hand Cards: ");
            response = GameMatView.getCommand();
            while (!response.matches("\\d+") || !player.doesAddressIconMatchInMainDeck(Integer.parseInt(response), "Field")) {
                if (response.equals("cancel"))
                    return;
                GameMatView.showInput("Please choose a Field Spell from your Main Deck correctly: ");
                response = GameMatView.getCommand();
            }
            chosenAddress = Integer.parseInt(response);
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

    private static void raigeki(String rivalUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).removeMonsterFromZone();
        }
    }

    private static void changeOfHeart(String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 5)
            GameMatView.showInput("Oops! You cant use this Spell Effect Because of no free space in Your Monster Zone!");
        else {
            GameMatView.showInput("Please enter the address of one of the Rival Monster to control in your Turn: ");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response),rivalUser) == null) {
                if (response.equals("cancel"))
                    return;
                GameMatView.showInput("Please enter the Monster address correctly: ");
                response = GameMatView.getCommand();
            }
            chosenAddress = Integer.parseInt(response);
            MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).removeMonsterFromZone();
            new MonsterZoneCard(onlineUser, MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).get(chosenAddress).getMonsterName(), "OO", false);
            ///set one property
        }
    }

    private static void harpieFeatherDuster(String rivalUser) {
        Integer[] spell = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : spell) {
            SpellTrapZoneCard.getSpellCardByAddress(key, rivalUser).removeSpellTrapFromZone();
        }
    }

    private static void swordsOfRevealingLight(String rivalUser, int ownAddress, String onlineUser) {
        Integer[] keys = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser).keySet().toArray(new Integer[0]);
        for (int key : keys) {
            MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setMode("DO");
        }
//        if (MonsterZoneCard.getMonsterCardByAddress(ownAddress, onlineUser).getMode().equals("OO") ||
//                MonsterZoneCard.getMonsterCardByAddress(ownAddress, onlineUser).getMode().equals("DO")) {
//            for (int key : keys) {
//                MonsterZoneCard.getMonsterCardByAddress(key, rivalUser).setCanAttack(false);
//            }
//        }
    }

    private static void darkHole(String rivalUser, String onlineUser) {
        Map<Integer, MonsterZoneCard> rivalsMonster = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);//static map?????
        Integer[] monsterAddress = rivalsMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < rivalsMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(monsterAddress[i], rivalUser).removeMonsterFromZone();
        }
        Map<Integer, MonsterZoneCard> ownMonster = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] ownMonsterAddress = ownMonster.keySet().toArray(new Integer[0]);
        for (int i = 0; i < ownMonster.size(); i++) {
            MonsterZoneCard.getMonsterCardByAddress(ownMonsterAddress[i], onlineUser).removeMonsterFromZone();
        }
    }

    public static void supplySquad(String onlineUser) {
        int continuousSpellAddress = SpellTrapZoneCard.getAddressOfSpellByIcon(onlineUser, "Continuous", "Supply Squad");
        Player player = Player.getPlayerByName(onlineUser);
        if (continuousSpellAddress != 0) {
            if (player.getNumberOfDeadMonsterThisTurn() != 0) {
                String cardName = player.drawCard(false);
                player.removeFromMainDeck();
                new HandCardZone(onlineUser, cardName);
                //call it end of each turn
            }
        }
    }

    public static void spellAbsorption(String onlineUser) {
        Player.getPlayerByName(onlineUser).changeLifePoint(500);
        //just check is this spell exist in activate method and call this
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
        //need standby phase change after it its dead set attack true
    }





    public static void quickPlayEffectController(int spellAddress, String onlineUser, String rivalUser) {
        spellCard = SpellTrapZoneCard.getSpellCardByAddress(spellAddress, onlineUser);
        String spellName = spellCard.getSpellTrapName();
        switch (spellName) {
            case "Twin Twisters" -> twinTwisters(onlineUser, rivalUser);
            case "Mystical space typhoon" -> mysticalSpaceTyphoon(onlineUser, rivalUser);
            case "Ring of Defense" -> ringOfDefense();
        }
    }

    private static void twinTwisters(String onlineUser, String rivalUser) {
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
            HandCardZone.getHandCardByAddress(chosenAddress, onlineUser).removeFromHandCard();
        }
        GameMatView.showInput("Please enter the number of Spell/Trap you want to destroy: (1 or 2)");
        response = GameMatView.getCommand();
        while (!response.matches("[1-2]")) {
            if (response.equals("cancel"))
                return;
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
                    return;
                GameMatView.showInput("Please enter the answer correctly: (own or rival)");
                response = GameMatView.getCommand();
            }
            if (response.equals("own")) {
                GameMatView.showInput("Please enter the address of  your Spell/Trap to destroy:");
                spellTrapAddress = GameMatView.getCommand();
                while (!spellTrapAddress.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), onlineUser) == null) {
                    if (spellTrapAddress.equals("cancel"))
                        return;
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
                        return;
                    GameMatView.showInput("Please enter the address correctly:");
                    spellTrapAddress = GameMatView.getCommand();
                }
                SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(spellTrapAddress), rivalUser).removeSpellTrapFromZone();
            }
        }
    }

    private static void mysticalSpaceTyphoon(String onlineUser, String rivalUser) {
        GameMatView.showInput("Whose Spell/Trap you want to destroy: (own or rival)");
        response = GameMatView.getCommand();
        while (!response.matches("own|rival")) {
            if (response.equals("cancel"))
                return;
            GameMatView.showInput("Please enter the answer correctly: (own or rival)");
            response = GameMatView.getCommand();
        }
        if (response.equals("own")) {
            GameMatView.showInput("Please enter the address of  your Spell/Trap to destroy:");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                if (response.equals("cancel"))
                    return;
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
                    return;
                GameMatView.showInput("Please enter the address correctly:");
                response = GameMatView.getCommand();
            }
            SpellTrapZoneCard.getSpellCardByAddress(Integer.parseInt(response), rivalUser).removeSpellTrapFromZone();
        }
    }

    private static void ringOfDefense() {
        //call this method when a destructive trap activate 0 damage
    }









    public static void quickPlayEffectController(String prevSpell, String newSpell, String onlineUser, String rivalUser) {
        if (!prevSpell.isEmpty()) {
            switch (prevSpell) {
                case "Yami" -> yami(onlineUser, rivalUser, false);
                case "Forest" -> forest(onlineUser, rivalUser, false);
                case "Closed Forest" -> closedForest(onlineUser, rivalUser, false);
                case "UMIIRUKA" -> umiiruka(onlineUser, rivalUser, false);
            }
        }
        switch (newSpell) {
            case "Yami" -> yami(onlineUser, rivalUser, true);
            case "Forest" -> forest(onlineUser, rivalUser, true);
            case "Closed Forest" -> closedForest(onlineUser, rivalUser, true);
            case "UMIIRUKA" -> umiiruka(onlineUser, rivalUser, true);
        }
    }


    private static void yami( String onlineUser, String rivalUser, boolean isActivated) {
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

    private static void forest(String onlineUser, String rivalUser, boolean isActivated) {
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

    private static void closedForest(String onlineUser, String rivalUser, boolean isActivated) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monsterNames = monsters.keySet().toArray(new Integer[0]);
        int increaseAttack = (GameMatModel.getGameMatByNickname(onlineUser).getNumberOfDeadMonster() + GameMatModel.getGameMatByNickname(rivalUser).getNumberOfDeadMonster()) * 100;
        for (int i = 0; i < monsters.size(); i++) {
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).getMonsterName()).getMonsterType().equals("Beast-Type")) {
                MonsterZoneCard.getMonsterCardByAddress(monsterNames[i], onlineUser).changeAttack(increaseAttack);
            }
        }
    }

    private static void umiiruka(String onlineUser, String rivalUser, boolean isActivated) {
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




    public static void equipEffectController(String mode, String spellName, String onlineUser, String rivalUser) {
//        switch (spellName) {
//            case "Sword of Dark Destruction" -> swordOfDarkDestruction(onlineUser, rivalUser, mode);
//            case "Black Pendant" -> blackPendant(onlineUser, rivalUser, mode);
//            case "United We Stand" -> unitedWeStand(onlineUser, rivalUser, mode);
//            case "Magnum Shield" -> magnumShield(onlineUser, rivalUser, mode);
//        }
    }

    private static void swordOfDarkDestruction(String onlineUser, int ownAddress, String mode) {
        if (mode.equals("H")) {

        }
        else {
            int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, onlineUser).getRelatedMonsterAddress();
            if (MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Spellcaster") ||
                    MonsterCard.getMonsterByName(MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getMonsterName()).getMonsterType().equals("Fiend")) {
                MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeAttack(400);
                MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeDefend(-200);
            }
        }
    }

    private static void blackPendant(String onlineUser, int ownAddress, String mode) {
        if (mode.equals("H")) {

        }
        else {
            int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, onlineUser).getRelatedMonsterAddress();
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeAttack(500);
        }

    }

    private static void unitedWeStand(String onlineUser, int ownAddress, String mode) {
        if (mode.equals("H")) {

        }
        else {
            int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, onlineUser).getRelatedMonsterAddress();
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
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeAttack(increaseAttackAndDefend);
            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeDefend(increaseAttackAndDefend);
        }
    }

    private static void magnumShield(String onlineUser, int ownAddress, String mode) {
        if (mode.equals("H")) {

        }
        else {
            int relatedMonsterAddress = SpellTrapZoneCard.getSpellCardByAddress(ownAddress, onlineUser).getRelatedMonsterAddress();
            if (MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getMode().equals("OO")) {
                MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeAttack(
                        MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getDefend());

                if (MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getMode().equals("DO")) {
                    MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).changeDefend(
                            MonsterZoneCard.getMonsterCardByAddress(relatedMonsterAddress, onlineUser).getAttack());
                }
            }
        }
    }

}
