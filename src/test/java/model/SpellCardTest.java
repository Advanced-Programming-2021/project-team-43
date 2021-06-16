package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SpellCardTest {

    SpellCard spellCard = new SpellCard("salato", "Spell", "WATER", "when a card returns, this card disappear", 9019, "Limited");

    @Test
    public void getSpellCards() {
        HashMap<String, SpellCard> expected = new HashMap<>();
        expected.put("salato", spellCard);
        HashMap<String, SpellCard> actual = SpellCard.getSpellCards();
        assertEquals(expected, actual);
    }

    @Test
    public void getStatus() {
        String status=SpellCard.getSpellCardByName("salato").getStatus();
        assertEquals("Limited",status);
    }

    @Test
    public void getCardName() {
        String actualName=SpellCard.getSpellCardByName("salato").getCardName();
        assertEquals("salato",actualName);
    }

    @Test
    public void getIcon() {
        String actualIcon=SpellCard.getSpellCardByName("salato").getIcon();
        assertEquals("WATER",actualIcon);
    }

    @Test
    public void getSpellCardByName() {
        SpellCard actualSpellCard=SpellCard.getSpellCardByName("salato");
        assertEquals(spellCard,actualSpellCard);
    }

}