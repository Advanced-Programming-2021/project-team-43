package model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ShopModelTest {

    @Test
    public void getCardPriceByName() {
        Assert.assertEquals(0, ShopModel.getCardPriceByName("Yami"));
        HashMap<String, Card> cards = new HashMap<>();
        Card card= new Card("Yami","Monster","take no action",200);
        cards.put("Yami",card);
        new ShopModel(cards);
        assertEquals(200,ShopModel.getCardPriceByName("Yami"));
    }

    @Test
    public void getCardInfo() {
        HashMap<String,Card> cards = new HashMap<>();
        Card card= new Card("Yami","Monster","take no action",200);
        cards.put("Yami",card);
        new ShopModel(cards);
        HashMap<String,Integer> cards2 = new HashMap<>();
        cards2.put("Yami",200);
        assertEquals(cards2,ShopModel.getCardInfo());
    }
}