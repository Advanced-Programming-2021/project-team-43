package controller;

import model.BazarModel;
import model.UserModel;
import view.RegisterAndLoginView;

import java.io.IOException;
import java.util.ArrayList;

public class BazarController {


    public static String newBazar(String cardName, int firstPrice) {
        try {
            if (!UserModel.getUserByUsername(MainMenuController.username).userAllCards.containsKey(cardName)) {
                return "you don't have this card!";
            }

            RegisterAndLoginView.dataOutputStream.writeUTF("BB " + MainMenuController.token + " n " + cardName + " n " + firstPrice);
            String string = RegisterAndLoginView.dataInputStream.readUTF();
            if (MainMenuController.isSuccessful(string)) {
                UserModel.getUserByUsername(MainMenuController.username).removeCardFromUserAllCards(cardName);
            }
            return string;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //pisNahad gheymat baraye 1 mozayede
    // dar inja bala tar boodan gheymat check shavad
    public static String newOffer(BazarModel bazarModel, int newPrice) {
        updateBazar();
        bazarModel=UserModel.all.get(bazarModel.bazarCode);
        if (UserModel.getUserByUsername(MainMenuController.username).getUserCoin() < newPrice) {

            return "not enough money!";
        }
        if (bazarModel.bestPrice >= newPrice) {
            System.out.println("Low"+bazarModel.bestPrice);
            return "this offer is low";
        }
        try {

            RegisterAndLoginView.dataOutputStream.writeUTF("BB " + MainMenuController.token + " f " + bazarModel.bazarCode + " f " + newPrice);
            String string = RegisterAndLoginView.dataInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
        updateBazar();
        return "null";
    }
    public static void updateBazar(){
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("1020315");
            UserModel.all=(ArrayList<BazarModel>) RegisterAndLoginView.objectInputStream.readUnshared();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}