package controller;

import model.BazarModel;
import model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BazarController {


    public static String findMatcher(String command){
        Matcher matcher;
        if ((matcher = getMatcher(command, "^BB (.+?) n (.+?) n (.+?)$")).find()) {
            newBazar(matcher.group(2),RegisterAndLoginController.allOnlineUsers.get(matcher.group(1)), Integer.parseInt(matcher.group(3)));
            return "card added to bazar is successful";
        }
        if ((matcher = getMatcher(command, "^BB (.+?) f (.+?) f (.+?)$")).find()) {
            newOffer(matcher.group(2),RegisterAndLoginController.allOnlineUsers.get(matcher.group(1)), Integer.parseInt(matcher.group(3)));
            return "new offer is successful";
        }
        System.out.println("al al");
        return "1";
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void newBazar(String cardName ,String  seller,int firstPrice) {
       BazarModel bazarModel =new BazarModel(cardName,seller,firstPrice);

        new Thread(() -> {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            bazarModel.end();
        }).start();
    }

    public static void newOffer(String bazarCode, String customer , int newPrice){
        BazarModel bazarModel = UserModel.all.get(Integer.parseInt(bazarCode));
        if (bazarModel.bazarCode!=(Integer.parseInt(bazarCode))){
            System.out.println("na na no");
        }
        bazarModel.changeCustomer(customer,newPrice);
    }
}

