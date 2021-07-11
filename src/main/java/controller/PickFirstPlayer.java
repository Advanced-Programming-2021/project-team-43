package controller;

import model.Player;
import model.UserModel;
import view.GameMatView;


import java.io.IOException;
import java.util.*;


public class PickFirstPlayer {

    public static String result;

    public static String rockPaperScissors(String player1, String player2, String firstChoice, String secondChoice) {
        if (firstChoice.equals("rock")) {
            if (secondChoice.equals("rock")) {

                return  "@The game equalised";
            }
            if (secondChoice.equals("paper")) {
                return player2+"@"+UserModel.getUserByUsername(player2).getNickname() + " is win";
            }
            if (secondChoice.equals("scissors")) {
                return player1+"@"+UserModel.getUserByUsername(player1).getNickname() + " is win";
            }
        }
        if (firstChoice.equals("paper")) {
            if (secondChoice.equals("rock")) {
                return player1+"@"+ UserModel.getUserByUsername(player1).getNickname() + " is win";
            }
            if (secondChoice.equals("paper")) {
              return "@The game equalised";
            }
            if (secondChoice.equals("scissors")) {
                return player2+"@"+ UserModel.getUserByUsername(player2).getNickname() + " is win";
            }
        }
        if (firstChoice.equals("scissors")) {
            if (secondChoice.equals("rock")) {
                return player2+"@"+ UserModel.getUserByUsername(player2).getNickname() + " is win";
            }
            if (secondChoice.equals("paper")) {
                return player1+"@"+UserModel.getUserByUsername(player1).getNickname() + " is win";
            }
            if (secondChoice.equals("scissors")) {
              return "@The game equalised";
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

    public static String ply1 = MainMenuController.username;
    public static String ply2 = MainMenuController.username2;

    public static String chose(String username, String playerName) {
        Random random = new Random();
        int chance = random.nextInt(50);
        if (ply1.equals("AI")) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply2).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply1).getNickname();
            if (Player.getPlayerByName("AI") == null) {
               // new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), true, MainMenuController.roundNumber1);
              //  new Player(UserModel.getUserByUsername(ply1).getNickname(), UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), false, MainMenuController.roundNumber1);

            }
            //Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(primaryStage);
        } else if (ply2.equals("AI")) {
            GameMatController.onlineUser = UserModel.getUserByUsername(ply1).getNickname();
            GameMatController.rivalUser = UserModel.getUserByUsername(ply2).getNickname();
            if (Player.getPlayerByName("AI") == null) {
             //   new Player(GameMatController.onlineUser, UserModel.getUserByUsername(ply1).userAllDecks.get(UserModel.getUserByUsername(ply1).getActiveDeck()), true, MainMenuController.roundNumber1);
             //   new Player(UserModel.getUserByUsername(ply2).getNickname(), UserModel.getUserByUsername(ply2).userAllDecks.get(UserModel.getUserByUsername(ply2).getActiveDeck()), false, MainMenuController.roundNumber1);
            }
          //  Objects.requireNonNullElseGet(GameMatController.gameMatView, () -> GameMatController.gameMatView = new GameMatView()).start(primaryStage);
        } else {
            if (chance % 2 == 0) {
                //   chanceCoin(ply1,ply2);
                return "chanceCoin";
            } else {
                return "rockPaperScissors";
//                rockPaperScissors(ply1,ply2,);
            }
        }
return "AI";
    }
}