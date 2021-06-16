package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void getDescription() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        String description = Card.getCardsByName("sogo").getDescription();
        assertEquals("this card is normal with out any effect", description);
    }

    @Test
    public void getCardName() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        String cardName = Card.getCardsByName("sogo").getCardName();
        assertEquals("sogo",cardName);
    }

    @Test
    public void getCardModel() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        String cardModel=Card.getCardsByName("sogo").getCardModel();
        assertEquals("Spell",cardModel);
    }

    @Test
    public void getPrice() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        int price=Card.getCardsByName("sogo").getPrice();
        assertEquals(700,price);
    }

    @Test
    public void getCards() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        HashMap<String,Card> cards=new HashMap<>();
        cards.put("sogo",cardSpell);
        HashMap<String,Card> actual=Card.getCards();
        assertEquals(cards,actual);
    }

    @Test
    public void getCardsByName() {
        Card cardSpell = new Card("sogo", "Spell", "this card is normal with out any effect", 700);
        Card expectedCard=cardSpell;
        Card actual=Card.getCardsByName("sogo");
        assertEquals(expectedCard,actual);
    }
}