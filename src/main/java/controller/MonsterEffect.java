package controller;
import model.*;
import view.GameMatView;
import java.util.*;


public class MonsterEffect {

    private static String response;

    public static int changeModeEffectController(MonsterZoneCard ownMonster, String onlineUser, String rivalUser) {
        String monsterName = ownMonster.getMonsterName();
        switch (monsterName) {
            case "Command knight":
                return commandKnight(ownMonster, onlineUser, rivalUser);
            case "Man-Eater Bug":
                return manEaterBug(ownMonster, rivalUser);
            case "Mirage Dragon":
                return mirageDragon(ownMonster, rivalUser);
        }
        return 0;
    }

    private static int commandKnight(MonsterZoneCard ownMonster, String onlineUser, String rivalUser) {
        if (!ownMonster.getMode().equals("DH") && !ownMonster.getIsEffectUsed()) {
            int counter = 0;
            Map<Integer, MonsterZoneCard> monsters;
            monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
            Integer[] keys;
            Map<Integer, MonsterZoneCard> rivalsMonsters;
            rivalsMonsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
            Integer[] rivalsKeys;
            keys = monsters.keySet().toArray(new Integer[0]);
            rivalsKeys = rivalsMonsters.keySet().toArray(new Integer[0]);
            for (Integer key : keys) {
                MonsterZoneCard.getMonsterCardByAddress(key, onlineUser).changeAttack(400);
                if (!MonsterZoneCard.getMonsterCardByAddress(key, onlineUser).getMonsterName().equals("Command knight")) {
                    counter++;
                }
            }
            if (counter != 0) {
                ownMonster.setCanAttackToThisMonster(false);
            }
            for (int i = 0; i < rivalsMonsters.size(); i++) {
                MonsterZoneCard.getMonsterCardByAddress(rivalsKeys[i], rivalUser).changeAttack(400);
            }
            ownMonster.setIsEffectUsed(true);
        }
        ownMonster.setCanAttackToThisMonster(MonsterZoneCard.getNumberOfFullHouse(onlineUser) == 1);
        if (ownMonster.getMode().equals("DH")) {
            ownMonster.setCanAttackToThisMonster(true);
            ownMonster.setIsEffectUsed(false);
        }
        return 1;
    }

    private static int manEaterBug(MonsterZoneCard ownMonsterCard, String rivalUser) {
        if (!ownMonsterCard.getMode().equals("DH") && !ownMonsterCard.getIsEffectUsed()) {
            GameMatView.showInput("Please enter the address of a Rival Monster to destroy:");
            response = GameMatView.getCommand();
            while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the correct address of a Rival Monster:");
                response = GameMatView.getCommand();
            }
            MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser).removeMonsterFromZone();
            ownMonsterCard.setIsEffectUsed(true);
            return 1;
        }
        return 0;
    }

    private static int mirageDragon(MonsterZoneCard ownMonster, String rivalUser) {
        Player.getPlayerByName(rivalUser).setCanUseTrap(ownMonster.getMode().equals("DH"));
        return 1;
    }

    public static int suijin(MonsterZoneCard rivalMonster) {
        if (!rivalMonster.getMode().equals("DH") && !rivalMonster.getIsEffectUsed()) {
            GameMatView.showInput("Do you want to use this Monster Effect? (yes/no)");
            response = GameMatView.getCommand();
            while (!response.matches("yes|no")) {
                if (response.equals("cancel"))
                    return 0;
                GameMatView.showInput("Please enter the answer correctly: (yse/no)");
                response = GameMatView.getCommand();
            }
            if (response.equals("yes")) {
                rivalMonster.setIsEffectUsed(true);
                GameMatView.showInput("Suijin Monster Effect is activated and your attack is neutralized!");
                return 1;
            }
        }
        return 0;
    }

    public static void marshmallon(MonsterZoneCard rivalMonster, String onlineUser) {
        if (rivalMonster.getMode().equals("DO")) {
            GameMatView.showInput("Marshmallon MonsterEffect is activated!");
            Player.getPlayerByName(onlineUser).changeLifePoint(-1000);
        }
    }

    public static void theCalculator(String onlineUser, MonsterZoneCard ownMonster) {
        Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(onlineUser);
        Integer[] monster = monsters.keySet().toArray(new Integer[0]);
        int attack = 0;
        for (int s : monster) {
            MonsterZoneCard monsterCard = MonsterZoneCard.getMonsterCardByAddress(s, onlineUser);
            if (!monsterCard.getMode().equals("DH") && !monsterCard.getMonsterName().equals("The Calculator")) {
                attack += monsterCard.getLevel();
            }
        }
        attack *= 300;
        ownMonster.setAttack(attack);
    }

    public static void terratiger(String onlineUser) {
        GameMatView.showInput("Do you want Summon another Monster in Defend position? (yes/no)");
        response = GameMatView.getCommand();
        while (!response.matches("yes|no")) {
            if (response.equals("cancel"))
                return;
            GameMatView.showInput("Please enter the correct answer: (yes/no)");
            response = GameMatView.getCommand();
        }
        if (response.equals("no")) {
            ////////////////////////
        }
        else {
            if (HandCardZone.getNumberOfFullHouse(onlineUser) == 0)
                GameMatView.showInput("Oops! You cant have Special Summon because of lack of HandCard!");
            else if (!HandCardZone.doesAnyLevelFourMonsterExisted(onlineUser))
                GameMatView.showInput("Oops! You cant have Special Summon because of no 4 level or less Monster!");
            else {
                int address;
                String cardName;
                while (true) {
                    GameMatView.showInput("Please enter the address of a 4 level or less to summon in Defend Position:");
                    response = GameMatView.getCommand();
                    if (response.equals("cancel"))
                        return;
                    if (!response.matches("[1-8]"))
                        continue;
                    address = Integer.parseInt(response);
                    address--;
                    if (address <= HandCardZone.getNumberOfFullHouse(onlineUser)) {
                        cardName = HandCardZone.getHandCardByAddress(address, onlineUser).getCardName();
                        if (Card.getCardsByName(cardName).getCardModel().equals("Monster") && MonsterCard.getMonsterByName(cardName).getLevel() <= 4)
                            break;
                    }
                }
                HandCardZone.getHandCardByAddress(address, onlineUser).removeFromHandCard();
                new MonsterZoneCard(onlineUser, cardName, "DO", false, false);
                GameMatView.showInput("summoned successfully");
            }
        }
    }

    public static int gateGuardian(HandCardZone handCard, String onlineUser, String rivalUser) {
        if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 3)
            GameMatView.showInput("Oops! You cant summon this Monster because of lack of Monster to tribute");
        else {
            GameMatView.showInput("Please enter the address of three Monster to tribute: ");
            for (int i = 1; i < 4; i++) {
                GameMatView.showInput("Do you want to tribute your Monster or rival Monster? (own/rival)");
                response = GameMatView.getCommand();
                while (!response.matches("own|rival")) {
                    if (response.equals("cancel"))
                        return 0;
                    GameMatView.showInput("Please enter the answer correctly: (own/rival)");
                    response = GameMatView.getCommand();
                }
                if (response.equals("own")) {
                    GameMatView.showInput("Please enter the address of your Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                        if (response.equals("cancel"))
                            return 0;
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser).removeMonsterFromZone();
                }
                else {
                    GameMatView.showInput("Please enter the address of rival Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser) == null) {
                        if (response.equals("cancel"))
                            return 0;
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), rivalUser).removeMonsterFromZone();
                }
            }
            handCard.removeFromHandCard();
            new MonsterZoneCard(onlineUser, "Gate Guardian", "OO", false,false);
        }
        return 1;
    }

    public static int beastKingBarbaros(HandCardZone handCard, String onlineUser, String rivalUser) {
        GameMatView.showInput("Do you want to summon Beast King Barbaros without tributing? (yes/no)");
        response = GameMatView.getCommand();
        while (!response.matches("yes|no")) {
            if (response.equals("cancel"))
                return 0;
            GameMatView.showInput("Please enter the correct answer: (yes/no)");
            response = GameMatView.getCommand();
        }
        if (response.equals("yes")) {
            handCard.removeFromHandCard();
            new MonsterZoneCard(onlineUser, "Beast King Barbaros", "OO", false, false);
            MonsterZoneCard.getMonsterCardByAddress(MonsterZoneCard.getNumberOfFullHouse(onlineUser), onlineUser).setAttack(1900);
        }
        else {
            if (MonsterZoneCard.getNumberOfFullHouse(onlineUser) < 3) {
                GameMatView.showInput("Oops! You cant summon this Monster because of lack of Monster to tribute");
            }
            else {
                GameMatView.showInput("Please enter the address of three Monster to tribute: ");
                for (int i = 1; i < 4; i++) {
                    GameMatView.showInput("Please enter the address of Monster " + i + " to tribute: ");
                    response = GameMatView.getCommand();
                    while (!response.matches("[1-5]") || MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                        if (response.equals("cancel"))
                            return 0;
                        GameMatView.showInput("Please enter the address correctly!");
                        response = GameMatView.getCommand();
                    }
                    MonsterZoneCard.getMonsterCardByAddress(Integer.parseInt(response), onlineUser);
                }
                handCard.removeFromHandCard();
                new MonsterZoneCard(onlineUser, "Beast King Barbaros", "OO", false, false);
                handCard.removeFromHandCard();
                Map<Integer, MonsterZoneCard> monsters = MonsterZoneCard.getAllMonstersByPlayerName(rivalUser);
                Map<Integer, SpellTrapZoneCard> spellsTraps = SpellTrapZoneCard.getAllSpellTrapByPlayerName(rivalUser);
                Integer[] monster = monsters.keySet().toArray(new Integer[0]);
                Integer[] spellTraps = spellsTraps.keySet().toArray(new Integer[0]);
                for (int s : monster) {
                    MonsterZoneCard.getMonsterCardByAddress(s, rivalUser).removeMonsterFromZone();
                }
                for (int s : spellTraps) {
                    SpellTrapZoneCard.getSpellCardByAddress(s, rivalUser).removeSpellTrapFromZone();
                }
            }
        }
        return 1;
    }

    public static int theTricky(HandCardZone handCard, String onlineUser) {
        while (true) {
            GameMatView.showInput("Do you want a Special Summon? (yes/no)");
            response = GameMatView.getCommand();
            if (response.equals("cancel"))
                return 0;
            else if (response.matches("yes|no"))
                break;
        }
        if (response.equals("no"))
            return 0;
        else {
            int address;
            if (HandCardZone.getNumberOfFullHouse(onlineUser) == 1)
                GameMatView.showInput("Oops! You cant have Special Summon because of lack of HandCard!");
            else {
                while (true) {
                    GameMatView.showInput("Please enter the address of a HandCard to tribute:");
                    response = GameMatView.getCommand();
                    if (response.equals("cancel"))
                        return 0;
                    if (!response.matches("[1-7]"))
                        continue;
                    address = Integer.parseInt(response);
                    if (address == handCard.getAddress())
                        continue;
                    address--;
                    if (HandCardZone.getHandCardByAddress(address, onlineUser) != null)
                        break;
                }
                GameMatModel.getGameMatByNickname(onlineUser).addToGraveyard(HandCardZone.getHandCardByAddress(address, onlineUser).getCardName());
                HandCardZone.getHandCardByAddress(address, onlineUser).removeFromHandCard();
                handCard.removeFromHandCard();
                new MonsterZoneCard(onlineUser, "The Tricky", "OO", false, false);
            }
        }
        return 1;
    }

    public static int texchanger(MonsterZoneCard rivalMonster, String rivalUser) {
        int address;
        if (!rivalMonster.getIsEffectUsed()) {
            if (MonsterZoneCard.getNumberOfFullHouse(rivalUser) == 5) {
                GameMatView.showInput("Oops! You cant use this monster effect because of no free space in monster zone!");
                rivalMonster.setIsEffectUsed(true);
                return 0;
            }
            GameMatView.showInput("Please enter the zone name from which you want to pick a monster to summon: (hand/graveyard/deck)");
            response = GameMatView.getCommand();
            while (!response.matches("hand|deck|graveyard")) {
                if (response.equals("cancel")) {
                    rivalMonster.setIsEffectUsed(true);
                    return 1;
                }
                GameMatView.showInput("Please enter the correct zone name: (hand/graveyard/deck)");
                response = GameMatView.getCommand();
            }
            if (response.equals("hand")) {
                int realAddress = HandCardZone.getAddressOfCybreseMonster(rivalUser);
                if (realAddress == -1)
                    GameMatView.showInput("Oops! You dont have any Cyberse in your hand to summon!");
                else {
                    response = GameMatView.getCommand();
                    while (true) {
                        GameMatView.showInput("Please enter the address of a Cyberse Monster in your hand to summon:");
                        if (response.equals("cancel")) {
                            rivalMonster.setIsEffectUsed(true);
                            return 1;
                        }
                        if (!response.matches("[1-8]"))
                            continue;
                        address = Integer.parseInt(response);
                        address--;
                        if (realAddress == address)
                            break;
                        response = GameMatView.getCommand();
                    }
                    GameMatView.showInput("Texchanger Monster Effect is activated!");
                    new MonsterZoneCard(rivalUser, HandCardZone.getHandCardByAddress(address, rivalUser).getCardName(), "OO", false, false);
                    HandCardZone.getHandCardByAddress(address, rivalUser).removeFromHandCard();
                }
            }
            else if (response.equals("graveyard")) {
                if (!GameMatModel.getGameMatByNickname(rivalUser).doesThisModelAndTypeExist("Monster", "Cyberse"))
                    GameMatView.showInput("Oops! You dont have any Cyberse in your graveyard to summon!");
                else {
                    GameMatView.showInput("Please enter the address of a Cyberse Monster in your graveyard to summon:");
                    response = GameMatView.getCommand();
                    while (!response.matches("\\d+") || !GameMatModel.getGameMatByNickname(rivalUser).doesAddressAndTypeMatch(Integer.parseInt(response), "Monster", "Cyberse")) {
                        if (response.equals("cancel")) {
                            rivalMonster.setIsEffectUsed(true);
                            return 1;
                        }
                        GameMatView.showInput("Please enter the address of a Cyberse Monster in your graveyard correctly:");
                        response = GameMatView.getCommand();
                    }
                    GameMatView.showInput("Texchanger Monster Effect is activated!");
                    new MonsterZoneCard(rivalUser, GameMatModel.getGameMatByNickname(rivalUser).getDeadCardNameByAddress(Integer.parseInt(response)), "OO", false, false);
                    GameMatModel.getGameMatByNickname(rivalUser).removeFromGraveyardByAddress(Integer.parseInt(response));
                }
            }
            else {
                if (Player.getPlayerByName(rivalUser).doesThisModelAndTypeExist("Monster", "Cyberse"))
                    GameMatView.showInput("Oops! You dont have any Cyberse in your main deck to summon!");
                else {
                    while (true) {
                        GameMatView.showInput("Please enter the address of a Cyberse Monster in your main deck to summon:");
                        response = GameMatView.getCommand();
                        if (response.equals("cancel")) {
                            rivalMonster.setIsEffectUsed(true);
                            return 1;
                        }
                        if (!response.matches("\\d+"))
                            continue;
                        address = Integer.parseInt(response);
                        if (address > Player.getPlayerByName(rivalUser).getNumberOfMainDeckCards())
                            continue;
                        address--;
                        if (Player.getPlayerByName(rivalUser).doesAddressTypeMatchInMainDeck(address, "Monster", "Cyberse"))
                            break;
                    }
                    GameMatView.showInput("Texchanger Monster Effect is activated!");
                    new MonsterZoneCard(rivalUser, Player.getPlayerByName(rivalUser).getCardNameByAddress(address), "OO", false, false);
                    Player.getPlayerByName(rivalUser).removeFromMainDeckByAddress(address);
                }
            }
            rivalMonster.setIsEffectUsed(true);
            return 1;
        }
        return 0;
    }

    public static void heraldOfCreation(MonsterZoneCard ownMonster, String onlineUser) {
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(onlineUser);
        int address;
        if (!ownMonster.getIsEffectUsed()) {
            if (!ownGameMat.isAnySevenLevelMonsterInGraveyard()) {
                GameMatView.showInput("Oops! You cant use Herald of Creation effect because of no seven level or higher level monster in your graveyard!");
                return;
            }
            else if (HandCardZone.getNumberOfFullHouse(onlineUser) == 0) {
                GameMatView.showInput("Oops! You cant use Herald of Creation effect because of lack of Hand Card!");
                return;
            }
            else {
                GameMatView.showInput("Please enter the address of a hand card to remove: ");
                response = GameMatView.getCommand();
                while (!response.matches("[1-6]") || HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser) == null) {
                    if (response.equals("cancel"))
                        return;
                    GameMatView.showInput("Please enter the correct address of your hand card: ");
                    response = GameMatView.getCommand();
                }
                HandCardZone.getHandCardByAddress(Integer.parseInt(response), onlineUser).removeFromHandCard();
                while (true) {
                    GameMatView.showInput("Please enter the address of a seven level or higher level in your graveyard to add to your hand:");
                    response = GameMatView.getCommand();
                    if (response.equals("cancel"))
                        return;
                    if (!response.matches("\\d"))
                        continue;
                    address = Integer.parseInt(response);
                    address--;
                    if (ownGameMat.getDeadCardNameByAddress(address) != null && Card.getCardsByName(ownGameMat.getDeadCardNameByAddress(address)).getCardModel().equals("Monster") && MonsterCard.getMonsterByName(ownGameMat.getDeadCardNameByAddress(address)).getLevel() > 6)
                        break;
                }
                new HandCardZone(onlineUser, ownGameMat.getDeadCardNameByAddress(address));
                ownGameMat.removeFromGraveyardByAddress(address);
            }
        }
        ownMonster.setIsEffectUsed(true);
    }

}