package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TrapCardTest {

    TrapCard trapCard = new TrapCard("plato", "Trap", "Limited", "strongest TrapCard", 73000, "FIRE");

    @Test
    public void getCardName() {
        String actualName = TrapCard.getTrapCardByName("plato").getCardName();
        assertEquals("plato", actualName);
    }

    @Test
    public void getIcon() {
        String actualIcon = TrapCard.getTrapCardByName("plato").getIcon();
        assertEquals("Limited", actualIcon);
    }

    @Test
    public void getStatus() {
        String actualStatus = TrapCard.getTrapCardByName("plato").getStatus();
        assertEquals("FIRE", actualStatus);
    }

    @Test
    public void getTrapCards() {
        int actualPrice = TrapCard.getTrapCardByName("plato").getPrice();
        assertEquals(73000, actualPrice);
    }

    @Test
    public void getTrapCardByName() {
        TrapCard actualTrap = TrapCard.getTrapCardByName("plato");
        assertEquals(trapCard, actualTrap);
    }

    @Test
    public void getTrapCardsTest() {
        HashMap<String, TrapCard> expected = new HashMap<>();
        expected.put("plato", trapCard);
        HashMap<String, TrapCard> actualTraps = TrapCard.getTrapCards();
        assertEquals(expected, actualTraps);
    }

}