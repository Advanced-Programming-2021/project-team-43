package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MonsterEffectTest {

    MonsterZoneCard ownMonster;
    MonsterZoneCard ownMonster2;
    MonsterZoneCard rivalMonster2;
    DeckModel deckModel;
    DeckModel deckModel2;

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
        new Player("me", deckModel, true, 1);
        new Player("me2", deckModel2, false, 1);
        GameMatController.onlineUser = "me";
        GameMatController.onlineUser = "me2";
        ownMonster = new MonsterZoneCard("me", "Battle OX", "DO", false, true);
        ownMonster2 = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        rivalMonster2 = new MonsterZoneCard("me2", "Battle OX", "DO", false, true);

    }

    @Test
    public void changeModeEffectController() {
        assertEquals(0, MonsterEffect.changeModeEffectController(ownMonster2, "me", "me2"));
        MonsterZoneCard monster = new MonsterZoneCard("me", "Command knight", "OO", false, false);
        assertEquals(1, MonsterEffect.changeModeEffectController(monster, "me", "me2"));
        MonsterZoneCard monster2 = new MonsterZoneCard("me", "Mirage Dragon", "OO", false, false);
        assertEquals(1, MonsterEffect.changeModeEffectController(monster2, "me", "me2"));
    }

    @Test
    public void commandKnight() {
        MonsterZoneCard ownMonster = new MonsterZoneCard("me", "Battle OX", "DO", false, true);
        MonsterZoneCard ownMonster2 = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        MonsterZoneCard rivalMonster2 = new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        ownMonster.setIsEffectUsed(false);
        assertEquals(1, MonsterEffect.commandKnight(ownMonster, "me", "me2"));
        assertEquals(1, MonsterEffect.commandKnight(ownMonster2, "me", "me2"));
    }

    @Test
    public void manEaterBug() {
        ownMonster.setIsEffectUsed(false);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, MonsterEffect.manEaterBug(ownMonster, "me2"));
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        assertEquals(1, MonsterEffect.manEaterBug(ownMonster, "me2"));
        assertEquals(0, MonsterEffect.manEaterBug(ownMonster2, "me2"));

    }

    @Test
    public void mirageDragon() {
        assertEquals(1, MonsterEffect.mirageDragon(ownMonster, "me2"));
    }

    @Test
    public void suijin() {
        ownMonster.setIsEffectUsed(false);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, MonsterEffect.suijin(rivalMonster2));

        System.setIn(new ByteArrayInputStream("yes".getBytes()));
        assertEquals(1, MonsterEffect.suijin(rivalMonster2));

        ownMonster.setIsEffectUsed(true);
        assertEquals(0, MonsterEffect.suijin(rivalMonster2));
    }

    @Test
    public void marshmallon() {
        MonsterEffect.marshmallon(rivalMonster2, "me");
    }

    @Test
    public void theCalculator() {
        MonsterEffect.theCalculator("me", ownMonster);
    }

    @Test
    public void terratiger() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, MonsterEffect.terratiger("me"));
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        System.setIn(new ByteArrayInputStream("yes".getBytes()));
        HandCardZone.allHandCards.get("me").clear();
        assertEquals(2, MonsterEffect.terratiger("me"));
        assertNotEquals("Oops! You cant have Special Summon because of lack of HandCard!", "Oops! You cant have Special Summon because");
    }

//    @Test
//    public void gateGuardian() {
//        HandCardZone tt = new HandCardZone("me", "Battle OX");
//        assertEquals(1, MonsterEffect.gateGuardian(tt, "me", "me2"));
//    }


///

    @Test
    public void beastKingBarbaros() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        HandCardZone tt = new HandCardZone("me", "Battle OX");
        assertEquals(0, MonsterEffect.beastKingBarbaros(tt, "me", "me2"));

        System.setIn(new ByteArrayInputStream("yes".getBytes()));
        assertEquals(1, MonsterEffect.beastKingBarbaros(tt, "me", "me2"));

    }


    @Test
    public void theTricky() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        HandCardZone tt = new HandCardZone("me", "Battle OX");
        assertEquals(0, MonsterEffect.theTricky(tt, "me"));

        System.setIn(new ByteArrayInputStream("no".getBytes()));
        assertEquals(0, MonsterEffect.theTricky(tt, "me"));
    }

    @Test
    public void texchanger() {
        rivalMonster2.setIsEffectUsed(false);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        assertEquals(1, MonsterEffect.texchanger(rivalMonster2, "me2"));

        rivalMonster2.setIsEffectUsed(false);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(1, MonsterEffect.texchanger(rivalMonster2, "me2"));

        rivalMonster2.setIsEffectUsed(false);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        System.setIn(new ByteArrayInputStream("hand".getBytes()));
        assertEquals(1, MonsterEffect.texchanger(rivalMonster2, "me2"));


        rivalMonster2.setIsEffectUsed(false);
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        System.setIn(new ByteArrayInputStream("graveyard".getBytes()));
        assertEquals(1, MonsterEffect.texchanger(rivalMonster2, "me2"));


        rivalMonster2.setIsEffectUsed(false);
        Player.getPlayerByName("me2").playerMainDeck.add("Bitron");
        new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        System.setIn(new ByteArrayInputStream("deck".getBytes()));
//        assertEquals(1, MonsterEffect.texchanger(rivalMonster2, "me2"));

        rivalMonster2.setIsEffectUsed(true);
        assertEquals(0, MonsterEffect.texchanger(rivalMonster2, "me2"));

    }

    @Test
    public void heraldOfCreation() {
        ownMonster.setIsEffectUsed(true);
        assertEquals(0, MonsterEffect.heraldOfCreation(ownMonster, "me"));

        ownMonster.setIsEffectUsed(false);
        assertEquals(1, MonsterEffect.heraldOfCreation(ownMonster, "me"));

        ownMonster.setIsEffectUsed(false);
        GameMatModel.getGameMatByNickname("me").graveyard.add("Spiral Serpent");
        HandCardZone.allHandCards.get("me").clear();
        assertEquals(2, MonsterEffect.heraldOfCreation(ownMonster, "me"));

        ownMonster.setIsEffectUsed(false);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        HandCardZone.allHandCards.get("me").add(new HandCardZone("me", "Spiral Serpent"));
        assertEquals(3, MonsterEffect.heraldOfCreation(ownMonster, "me"));
    }

}