package controller;
import view.*;
import model.*;
import java.io.IOException;


public class DeckController {


    public static void setActivate(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck set-activate " + deckName);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).setActiveDeck(deckName);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteDeck(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck delete " + deckName);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).deleteDeck(deckName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String createDeck(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck create " + deckName);
            RegisterAndLoginView.dataOutputStream.flush();
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).addDeck(new DeckModel(deckName));
            }

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
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToMain(cardName);
            }

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
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToSide(cardName);
            }
            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void removeCardFromMainDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck rm-card --card " + cardName + " --deck " + deckName);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).removeCardFromMain(cardName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void removeCardFromSideDeck(String cardName, String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D " + MainMenuController.token + "deck rm-card --card " + cardName + " --deck " + deckName + " --side");
            String string =RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)){
                UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).removeCardFromSide(cardName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}