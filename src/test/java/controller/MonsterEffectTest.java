package controller;

import model.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import static org.junit.Assert.*;

public class MonsterEffectTest {

    MonsterZoneCard ownMonster;
    MonsterZoneCard ownMonster2;
    MonsterZoneCard rivalMonster2;

    @Before
    public void beforeAllClassTest() {
        DeckModel deckModel;
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        new UserModel("Guy", "123", "me");
        new UserModel("Guy2", "123", "me2");
        MainMenuController.username = "Guy";
        MainMenuController.username = "Guy2";
        deckModel = new DeckModel("myDeck");
        for (int i = 0; i < 10; i++) {
            deckModel.addCardToMain("Yami");
        }
        for (int i = 0; i < 5; i++) {
            deckModel.addCardToSide("Battle Ox");
        }
        new Player("me", deckModel, true, 1);
        new Player("me2", deckModel, false, 1);
        GameMatController.onlineUser = "me";
        GameMatController.onlineUser = "me2";
        ownMonster = new MonsterZoneCard("me", "Battle OX", "DO", false, true);
        ownMonster2 = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        rivalMonster2 = new MonsterZoneCard("me2", "Battle OX", "DO", false, true);

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

        System.setIn(new ByteArrayInputStream("yes".getBytes()));
        assertEquals(2, MonsterEffect.terratiger("me"));
    }

    @Test
    public void gateGuardian() {
        HandCardZone tt=new HandCardZone("me","Battle OX");
        assertEquals(1,MonsterEffect.gateGuardian(tt,"me","me2"));
    }

    @Test
    public void texchanger() {
    }

    @Test
    public void heraldOfCreation() {
    }

    @Test
    public void changeModeEffectController() {
    }




    @Test
    public void beastKingBarbaros() {
    }

    @Test
    public void theTricky() {
    }


}