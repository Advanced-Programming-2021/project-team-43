package controller;

import view.RegisterAndLoginView;

import java.io.IOException;


public class DeckController {


    public static void setActivate(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck set-activate " + deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterAndLoginController.updateUser(MainMenuController.token);
    }

    public static void deleteDeck(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck delete " + deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterAndLoginController.updateUser(MainMenuController.token);
    }

    public static String createDeck(String deckName) {

        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck create " + deckName);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            RegisterAndLoginController.updateUser(MainMenuController.token);
            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addCardToMainDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck add-card --card " + cardName + " --deck " + deckName);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            RegisterAndLoginController.updateUser(MainMenuController.token);
            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String addCardToSideDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck add-card --card " + cardName + " --deck " + deckName + " --side");
            String string =RegisterAndLoginView.dataInputStream.readUTF();
            RegisterAndLoginController.updateUser(MainMenuController.token);
            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void removeCardFromMainDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck rm-card --card " + cardName + " --deck " + deckName);
            RegisterAndLoginView.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterAndLoginController.updateUser(MainMenuController.token);
    }

    public static void removeCardFromSideDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck rm-card --card " + cardName + " --deck " + deckName + " --side");
            RegisterAndLoginView.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterAndLoginController.updateUser(MainMenuController.token);
    }

//    public static void showAllDeck() {
//        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
//        String[] keys;
//        keys = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.keySet().toArray(new String[0]);
//        Arrays.sort(keys);
//        //  DeckView.showInput("Decks:");
//        //DeckView.showInput("Active deck:");
//        if (!user.getActiveDeck().equals("")) {
//            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(user.getActiveDeck());
//            // DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
//        }
//        //DeckView.showInput("Other decks:");
//        for (String key : keys) {
//            if (user.getActiveDeck().equals(key)) {
//                continue;
//            }
//            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
//            // DeckView.showInput(key + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
//        }
//
//    }

//    public static void showMainDeck(String deckName) {
//        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
//            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
////            DeckView.showInput("Deck: " + deckName);
////            DeckView.showInput("Main deck:");
////            DeckView.showInput("Monsters:");
//            String[] cardNames;
//            cardNames = deck.cardsInMainDeck.keySet().toArray(new String[0]);
//            Arrays.sort(cardNames);
//            for (String cardName : cardNames) {
//                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
//                    continue;
//                }
//                //DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
//            }
//            // DeckView.showInput("Spell and Traps:");
//            for (String cardName : cardNames) {
//                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
//                    continue;
//                }
//                // DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
//            }
//        } else {
//            //  DeckView.showInput("deck with name " + deckName + " does not exist");
//        }
//
//    }
//
//    public static void showSideDeck(String deckName) {
//        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
//
//            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
//
////            DeckView.showInput("Deck: " + deckName);
////            DeckView.showInput("Side deck:");
////            DeckView.showInput("Monsters:");
//            String[] cardNames;
//            cardNames = deck.cardsInSideDeck.keySet().toArray(new String[0]);
//            Arrays.sort(cardNames);
//            for (String cardName : cardNames) {
//                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
//                    continue;
//                }
//                // DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
//            }
//            //  DeckView.showInput("Spell and Traps:");
//            for (String cardName : cardNames) {
//                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
//                    continue;
//                }
//                //   DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
//            }
//
//        } else {
//            // DeckView.showInput("deck with name " + deckName + " does not exist");
//        }
//    }

//    public static void showCards() {
//        HashMap<String, Integer> hashMap = UserModel.getUserByUsername(MainMenuController.username).userAllCards;
//        String[] keys = hashMap.keySet().toArray(new String[0]);
//        Arrays.sort(keys);
//        for (String key : keys) {
//            // DeckView.showInput(key + ":" + Card.getCards().get(key).getDescription() + "  " + hashMap.get(key));
//        }
//    }

}