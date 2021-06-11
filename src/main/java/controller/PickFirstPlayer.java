package main.java.controller;
import main.java.model.UserModel;
import java.util.*;


public class PickFirstPlayer {

    public static String result;

    public static String rockPaperScissors(String player1, String player2, String firstChoice, String secondChoice) {
        if (firstChoice.equals("rock")) {
            if (secondChoice.equals("rock")) {
                result = "The game equalised";
            }
            if (secondChoice.equals("paper")) {
                result = UserModel.getUserByUsername(player2).getNickname() + " is win";
                return player2;
            }
            if (secondChoice.equals("scissors")) {
                result = UserModel.getUserByUsername(player1).getNickname() + " is win";
                return player1;
            }
        }
        if (firstChoice.equals("paper")) {
            if (secondChoice.equals("rock")) {
                result = UserModel.getUserByUsername(player1).getNickname() + " is win";
                return player1;
            }
            if (secondChoice.equals("paper")) {
                result = "The game equalised";
            }
            if (secondChoice.equals("scissors")) {
                result = UserModel.getUserByUsername(player2).getNickname() + " is win";
                return player2;
            }
        }
        if (firstChoice.equals("scissors")) {
            if (secondChoice.equals("rock")) {
                result = UserModel.getUserByUsername(player2).getNickname() + " is win";
                return player2;
            }
            if (secondChoice.equals("paper")) {
                result = UserModel.getUserByUsername(player1).getNickname() + " is win";
                return player1;
            }
            if (secondChoice.equals("scissors")) {
                result = "The game equalised";
            }
        }
        return null;
    }

    public static String chanceCoin(String player1, String player2) {
        Random rand = new Random();
        int n = rand.nextInt(2);
        if (n == 0) {
            return player1;
        }
        return player2;
    }

}