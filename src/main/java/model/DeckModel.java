package model;




import controller.JSON;
import controller.MainMenuController;

import java.util.ArrayList;
import java.util.HashMap;

public class DeckModel {
    private String deckName;
    private int mainAllCardNumber = 0;
    private int sideAllCardNumber = 0;
    public HashMap<String, Integer> cardsInMainDeck = new HashMap<>();
    public HashMap<String, Integer> cardsInSideDeck = new HashMap<>();

    public DeckModel(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }


    public void addCardToMain(String cardName) {
        if (cardsInMainDeck.get(cardName) == null) {
            cardsInMainDeck.put(cardName, 1);
        } else {
            cardsInMainDeck.replace(cardName, cardsInMainDeck.get(cardName) + 1);
        }
        mainAllCardNumber = mainAllCardNumber + 1;


        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.userAllDecks.replace(deckName, this);
        UserModel.allUsersInfo.replace(MainMenuController.username,userModel);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public void removeCardFromMain(String cardName) {
        cardsInMainDeck.replace(cardName, cardsInMainDeck.get(cardName) - 1);
        if (cardsInMainDeck.get(cardName) == 0) {
            cardsInMainDeck.remove(cardName);
        }
        mainAllCardNumber = mainAllCardNumber - 1;
        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.userAllDecks.replace(deckName, this);
        UserModel.allUsersInfo.replace(MainMenuController.username,userModel);
    }

    public void addCardToSide(String cardName) {
        if (cardsInSideDeck.get(cardName) == null) {
            cardsInSideDeck.put(cardName, 1);
        } else {
            cardsInSideDeck.replace(cardName, cardsInSideDeck.get(cardName) + 1);
        }
        sideAllCardNumber = sideAllCardNumber + 1;
        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.userAllDecks.replace(deckName, this);
        UserModel.allUsersInfo.replace(MainMenuController.username,userModel);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public void removeCardFromSide(String cardName) {
        cardsInSideDeck.replace(cardName, cardsInSideDeck.get(cardName) - 1);
        if (cardsInSideDeck.get(cardName) == 0) {
            cardsInSideDeck.remove(cardName);
        }
        sideAllCardNumber = sideAllCardNumber - 1;
        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.userAllDecks.replace(deckName, this);
        UserModel.allUsersInfo.replace(MainMenuController.username,userModel);
        JSON.writeUserModelInfo(UserModel.allUsersInfo,UserModel.allUsernames,UserModel.allUsersNicknames);
    }

    public int getNumberOfCardInMainDeck(String cardName) {
        if (cardsInMainDeck.get(cardName) == null) {
            return 0;
        }
        return cardsInMainDeck.get(cardName);
    }

    public int getNumberOfCardInSideDeck(String cardName) {
        if (cardsInSideDeck.get(cardName) == null) {
            return 0;
        }
        return cardsInSideDeck.get(cardName);
    }

    public int getMainAllCardNumber() {
        return mainAllCardNumber;
    }

    public int getSideAllCardNumber() {
        return sideAllCardNumber;
    }

    public boolean isMainDeckHaveThisCard(String cardName){
        return cardsInMainDeck.get(cardName) != null;
    }

    public boolean isSideDeckHaveThisCard(String cardName){
        return cardsInSideDeck.get(cardName) != null;
    }


    public String validOrInvalid(){
        if (getMainAllCardNumber()>40){

            return "valid";}

        return "invalid";
    }


    public ArrayList<String> getArrayMain(){
        ArrayList <String> arrayList= new ArrayList<>();
        String [] key = cardsInMainDeck.keySet().toArray(new String[0]);
        for (int i = 0; i < key.length ; i++) {
            for (int j = 0; j <cardsInMainDeck.get(key[i]) ; j++) {
                arrayList.add(key[i]);
            }
        }
        return arrayList ;
    }
}





