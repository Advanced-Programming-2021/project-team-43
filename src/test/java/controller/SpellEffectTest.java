package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class SpellEffectTest {
    MonsterZoneCard ownMonster;
    MonsterZoneCard ownMonster2;
    MonsterZoneCard rivalMonster2;
    DeckModel deckModel;
    DeckModel deckModel2;
    Player player;

    @Before
    public void beforeAllClassTest() {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        new UserModel("Guy", "123", "me");
        new UserModel("Guy2", "123", "me2");
        MainMenuController.username = "Guy";
        MainMenuController.username = "Guy2";
        deckModel = new DeckModel("myDeck");
        deckModel2 = new DeckModel("rivalDeck");
        for (int i = 0; i < 10; i++) {
            deckModel.addCardToMain("Yami");
            deckModel2.addCardToMain("Yami");
        }
        for (int i = 0; i < 5; i++) {
            deckModel.addCardToSide("Battle Ox");
            deckModel2.addCardToSide("Battle Ox");
        }
        player = new Player("me", deckModel, true, 1);
        new Player("me2", deckModel2, false, 1);
        GameMatController.onlineUser = "me";
        GameMatController.onlineUser = "me2";
        ownMonster = new MonsterZoneCard("me", "Battle OX", "DO", false, true);
        ownMonster2 = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        rivalMonster2 = new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
    }

    @Test
    public void normalEffectController() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Mirror Force", "H");
        assertEquals(0, SpellEffect.normalEffectController(ee, "me", "me2"));
    }

    @Test
    public void quickPlayEffectController() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Mirror Force", "H");
        assertEquals(0, SpellEffect.quickPlayEffectController(ee, "me", "me2"));
    }

    @Test
    public void equipEffectController() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Mirror Force", "H");
        SpellEffect.equipEffectController(ee, "me", "me2");
    }

    @Test
    public void fieldEffectController() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Mirror Force", "H");
        SpellEffect.fieldEffectController("Mirror Force", "me", "me2");
    }

    @Test
    public void terraforming() {
        Player.getPlayerByName("me").playerMainDeck.clear();
        assertEquals(0, SpellEffect.terraforming("me", player));

        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        assertEquals(1, SpellEffect.terraforming("me", player));

        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.terraforming("me", player));

    }

    @Test
    public void potOfGreed() {
        Player.getPlayerByName("me").playerMainDeck.clear();
        assertEquals(0, SpellEffect.potOfGreed("me", player));

        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        Player.getPlayerByName("me").playerMainDeck.add("Umiiruka");
        assertEquals(1, SpellEffect.potOfGreed("me", player));

    }

    @Test
    public void raigeki() {
        MonsterZoneCard.allMonsterCards.get("me2").clear();
        assertEquals(0, SpellEffect.raigeki("me2"));

        new MonsterZoneCard("me2", "The Tricky", "DO", false, true);
        assertEquals(1, SpellEffect.raigeki("me2"));
    }

    @Test
    public void changeOfHeart() {
        MonsterZoneCard.allMonsterCards.get("me").clear();
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        SpellEffect.changeOfHeart("me", "me2");

        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        MonsterZoneCard.allMonsterCards.get("me2").clear();
        SpellEffect.changeOfHeart("me", "me2");

        new MonsterZoneCard("me2", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me2", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        assertEquals(1, SpellEffect.changeOfHeart("me", "me2"));

        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.changeOfHeart("me", "me2"));

    }

    @Test
    public void harpieFeatherDuster() {
        SpellTrapZoneCard.getAllSpellTrapByPlayerName("me2").clear();
        assertEquals(0, SpellEffect.harpieFeatherDuster("me2"));

        new SpellTrapZoneCard("me2", "Umiiruka", "H");
        assertEquals(1, SpellEffect.harpieFeatherDuster("me2"));
    }

    @Test
    public void swordsOfRevealingLigh() {
        MonsterZoneCard.getAllMonstersByPlayerName("me2").clear();
        assertEquals(0, SpellEffect.swordsOfRevealingLight("me2", 1));

        new MonsterZoneCard("me2", "The Tricky", "DO", false, true);
        assertEquals(1, SpellEffect.swordsOfRevealingLight("me2", 1));
    }

    @Test
    public void returnPermissionMessenger() {
        SpellEffect.returnPermissionMessenger(1, "me2","me");
    }
    @Test
    public void returnPermission() {
        SpellEffect.returnPermission(1, "me2");
    }

    @Test
    public void darkHole() {
        assertEquals(1, SpellEffect.darkHole("me2", "me"));
    }

    @Test
    public void spellAbsorption() {
        SpellEffect.spellAbsorption("me");
    }

    @Test
    public void messengerOfPeace() {
        SpellEffect.messengerOfPeace("me", "me2", 1);
    }

    //    @Test
//    public void twinTwisters() {
//        HandCardZone.allHandCards.get("me").clear();
//        SpellEffect.twinTwisters("me","me2");
//
//        System.setIn(new ByteArrayInputStream("own".getBytes()));
//        new HandCardZone("me","Yami");
//        new HandCardZone("me","Yami");
//        new HandCardZone("me","Yami");
//        assertEquals(0,SpellEffect.twinTwisters("me","me2"));
//
//
//    }
    @Test
    public void mysticalSpaceTyphoon() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.mysticalSpaceTyphoon("me", "me2"));

        System.setIn(new ByteArrayInputStream("own".getBytes()));
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.mysticalSpaceTyphoon("me", "me2"));

        System.setIn(new ByteArrayInputStream("rival".getBytes()));
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.mysticalSpaceTyphoon("me", "me2"));
    }

    @Test
    public void yami() {
        SpellEffect.yami("me", "me2");
    }

    @Test
    public void forest() {
        SpellEffect.forest("me", "me2");
    }

    @Test
    public void closedForest() {
        SpellEffect.closedForest("me", "me2");
    }

    @Test
    public void umiiruka() {
        SpellEffect.umiiruka("me", "me2");
    }

    @Test
    public void swordOfDarkDestruction() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Umiiruka", "H");
        new MonsterZoneCard("me2", "The Tricky", "DO", false, true);
        ee.setRelatedMonsterAddress("rival", 1);
        SpellEffect.swordOfDarkDestruction("me", ee, "me2");

        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        ee.setRelatedMonsterAddress("own", 1);
        SpellEffect.swordOfDarkDestruction("me", ee, "me2");
    }

    @Test
    public void blackPendant() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Umiiruka", "H");
        ee.setRelatedMonsterAddress("rival", 1);
        SpellEffect.blackPendant("me", ee, "me2");

        ee.setRelatedMonsterAddress("own", 1);
        SpellEffect.blackPendant("me", ee, "me2");
    }


    @Test
    public void getResponseForDeadCardToReborn() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(-1, SpellEffect.getResponseForDeadCardToReborn("own", GameMatModel.getGameMatByNickname("me"),
                GameMatModel.getGameMatByNickname("me2")));

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        GameMatModel.getGameMatByNickname("me").graveyard.add("The Tricky");
        assertEquals(0, SpellEffect.getResponseForDeadCardToReborn("own", GameMatModel.getGameMatByNickname("me"),
                GameMatModel.getGameMatByNickname("me2")));

        System.setIn(new ByteArrayInputStream("1".getBytes()));
        GameMatModel.getGameMatByNickname("me2").graveyard.add("The Tricky");
        assertEquals(0, SpellEffect.getResponseForDeadCardToReborn("rival", GameMatModel.getGameMatByNickname("me"),
                GameMatModel.getGameMatByNickname("me2")));
    }

    @Test
    public void monsterReborn() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(1, SpellEffect.monsterReborn("me", "me2", GameMatModel.getGameMatByNickname("me")));/////////

        System.setIn(new ByteArrayInputStream("own".getBytes()));
        assertEquals(0, SpellEffect.monsterReborn("me", "me2", GameMatModel.getGameMatByNickname("me")));

        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, SpellEffect.monsterReborn("me", "me2", GameMatModel.getGameMatByNickname("me")));
    }

    @Test
    public void unitedWeStand() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Umiiruka", "H");
        ee.setRelatedMonsterAddress("rival", 1);
        SpellEffect.unitedWeStand("me", ee, "me2");

        ee.setRelatedMonsterAddress("own", 1);
        SpellEffect.unitedWeStand("me", ee, "me2");
    }

    @Test
    public void magnumShield() {
        SpellTrapZoneCard ee = new SpellTrapZoneCard("me", "Umiiruka", "H");
        ee.setRelatedMonsterAddress("rival", 1);
        SpellEffect.magnumShield("me", ee, "me2");

        ee.setRelatedMonsterAddress("own", 1);
        SpellEffect.magnumShield("me", ee, "me2");

    }


}