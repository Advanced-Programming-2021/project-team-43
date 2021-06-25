package model;

import controller.SetCards;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopModelTest {

    @Test
    public void getCardPriceByName() {
        SetCards.readingCSVFileTrapSpell();
        HashMap<String, Card> cards = new HashMap<>();
        Card card= new Card("Yami","Monster","take no action",4300);
        cards.put("Yami",card);
        new ShopModel(cards);
        assertEquals(4300,ShopModel.getCardPriceByName("Yami"));
    }

    @Test
    public void getCardInfo() {
        SetCards.readingCSVFileTrapSpell();
        HashMap<String, Integer> cards = new HashMap<>();
        for (Map.Entry<String,Card> eachCard : Card.cards.entrySet())
            cards.put(eachCard.getKey(), Card.getCardsByName(eachCard.getKey()).getPrice());
        new ShopModel(Card.getCards());
        assertEquals(cards, ShopModel.getCardInfo());
    }

}