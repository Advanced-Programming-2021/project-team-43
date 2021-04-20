package main.java.model;

import main.java.controller.MainMenuController;
import main.java.view.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DeckModel {
    private String deckName;
    private int cardNumber = 0;
    private int cardNumberSide=0;

    public DeckModel(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }


    public HashMap<String, Integer> cardsInMaindeck;
    public HashMap<String, Integer> cardsInSidedeck;
    private ArrayList<String> cardName;
    public void addCardTomain(String cardName){
        if(cardsInMaindeck.get(cardName)==null){
            cardsInSidedeck.put(cardName,1);
        }
        else {
            cardsInMaindeck.replace(cardName,cardsInMaindeck.get(cardName)+1);
        }
        cardNumber=cardNumber+1;
    }









    public void removeCardToMain(String cardName) {
        cardsInMaindeck.replace(cardName,cardsInMaindeck.get(cardName)-1);
        if (cardsInMaindeck.get(cardName)==0){
            cardsInMaindeck.remove(cardName);
        }
        cardNumber=cardNumber-1;

    }

    public void addCardToSide(String cardName) {
        if(cardsInSidedeck.get(cardName)==null){
            cardsInSidedeck.put(cardName,1);
        }
        else {
            cardsInSidedeck.replace(cardName,cardsInSidedeck.get(cardName)+1);
        }
        cardNumberSide=cardNumberSide+1;

    }

    public void removeCardToSide(String cardName) {
        cardsInSidedeck.replace(cardName,cardsInSidedeck.get(cardName)-1);
        if (cardsInSidedeck.get(cardName)==0){
            cardsInSidedeck.remove(cardName);
        }
        cardNumberSide=cardNumberSide-1;
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


    public void setSideDeck(String sideDeck) {

    }


    public void setDeckName(String deckName) {

        this.deckName = deckName;
    }

}





