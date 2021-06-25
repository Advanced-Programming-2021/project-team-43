package controller;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class TrapEffectTest {

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
    public void magicCylinder() {
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        assertEquals(1, TrapEffect.magicCylinder("me", "me2", ownMonster, trapCard));
        assertEquals(0, TrapEffect.magicCylinder("me", "me2", null, trapCard));
    }

    @Test
    public void mirrorForce() {
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        assertEquals(1, TrapEffect.mirrorForce("me2", "me", trapCard));
        new SpellTrapZoneCard("me2", "Ring of defense", "O");
        assertEquals(0, TrapEffect.mirrorForce("me2", "me", trapCard));
    }

    @Test
    public void mindCrush() {
        System.setIn(new ByteArrayInputStream("Yami".getBytes()));
        HandCardZone.allHandCards.get("me2").clear();
        HandCardZone.allHandCards.get("me2").add(new HandCardZone("me2", "Mirror Force"));
        new HandCardZone("me2", "Mirror Force");
        new HandCardZone("me", "Mirror Force");
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        assertEquals(1, TrapEffect.mindCrush("me", "me2", trapCard));
        System.setIn(new ByteArrayInputStream("Yami".getBytes()));
        HandCardZone.allHandCards.get("me2").add(new HandCardZone("me2", "Yami"));
        SpellTrapZoneCard trapCard1 = new SpellTrapZoneCard("me", "Trap Hole", "H");
        assertEquals(1, TrapEffect.mindCrush("me", "me2", trapCard1));
    }

    @Test
    public void torrentialTribute() {
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        assertEquals(1, TrapEffect.torrentialTribute("me2", "me", trapCard));
        new SpellTrapZoneCard("me2", "Ring of defense", "O");
        assertEquals(0, TrapEffect.torrentialTribute("me2", "me", trapCard));
    }

    @Test
    public void timeSeal() {
        TrapEffect.timeSeal("me2");
    }

    @Test
    public void solemnWarning() {
        MonsterZoneCard monster = new MonsterZoneCard("me", "Mirage Dragon", "OO", false, false);
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Solemn Warning", "O");
        TrapEffect.solemnWarning("me", "me2", monster, trapCard);
        Assert.assertEquals(6000, Player.getPlayerByName("me").getLifePoint());
        new SpellTrapZoneCard("me2", "Ring of defense", "O");
        assertEquals(0, TrapEffect.solemnWarning("me", "me2", monster, trapCard));

    }

    @Test
    public void magicJammer() {
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        HandCardZone.allHandCards.get("me").clear();
        assertEquals(1, TrapEffect.magicJammer("me", "me2", trapCard));
        new HandCardZone("me", "Mirror Force");
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        assertEquals(1, TrapEffect.magicJammer("me", "me2", trapCard));
    }

    @Test
    public void callOfTheHaunted() {
        MonsterZoneCard.allMonsterCards.get("me").clear();
        SpellTrapZoneCard trapCard = new SpellTrapZoneCard("me", "Trap Hole", "H");
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        assertEquals(0, TrapEffect.callOfTheHaunted("me", trapCard));
        new MonsterZoneCard("me", "The Tricky", "DO", false, true);
        GameMatModel.getGameMatByNickname("me").graveyard.clear();
        assertEquals(0, TrapEffect.callOfTheHaunted("me", trapCard));
        GameMatModel.getGameMatByNickname("me").graveyard.add("The Tricky");
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, TrapEffect.callOfTheHaunted("me", trapCard));
        GameMatModel.getGameMatByNickname("me").addToGraveyard("Mirage Dragon");
        System.setIn(new ByteArrayInputStream("4".getBytes()));
        assertEquals(1, TrapEffect.callOfTheHaunted("me", trapCard));
    }

}