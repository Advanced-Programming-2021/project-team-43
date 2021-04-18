package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DeckModel {
    private String deckName;
    private int cardNumber=0;

    public String getDeckName() {
        return deckName;
    }


    private HashMap<String, Integer> cardNumberChecker;
    private ArrayList<String> cardName;
    private boolean isMainDeck;
    private boolean isSideDeck;



    public void addCard(String cardName) {

    }


    public void removeCard(String cardName) {

    }


    public  void setMainDeck(String deckName) {

    }


//    public  String getMainDeck() {
//
//    }


//    public HashMap<String, Integer> getCardNumberChecker() {
//
//    }


//    public  int getCardNumber() {
//
//    }


    public  void setSideDeck(String sideDeck) {

    }


//    public String getSideDeck() {
//
//    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

//    public int getCardNumber() {
//        return cardNumber;
//    }



}

