package controller;

import model.BazarModel;

public class BazarController {

    public static void ss(String cardName ,String  seller,int firstPrice) {
        BazarModel bazarModel =new BazarModel(cardName,seller,firstPrice);
        new Thread(() -> {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bazarModel.end();
        }).start();
    }

}