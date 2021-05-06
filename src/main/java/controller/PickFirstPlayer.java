package main.java.controller;
import main.java.model.*;
import java.util.Random;
import java.util.Scanner;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class PickFirstPlayer { public static String chose(String player1, String player2) {
    Scanner scanner= new Scanner(System.in);
    Random rand = new Random();
    int n = rand.nextInt(2);
    if (n == 0) {
        String winner =chanceCoin(player1, player2);
        System.out.println(UserModel.getUserByUsername(winner)+" do you want play first ? (yes or no)");
        while (true){
            if (scanner.nextLine().equals("yes")){
                return winner;
            }
            if (scanner.nextLine().equals("no")){
                if (winner.equals(player1)){
                    return player2;
                }
                return player1;
            }

            System.out.println("invalid command");

        }




    }

    String winner =rockPaperScissors(player1, player2);
    System.out.println(UserModel.getUserByUsername(winner)+" do you want play first ? (yes or no)");
    while (true){
        if (scanner.nextLine().equals("yes")){
            return winner;
        }
        if (scanner.nextLine().equals("no")){
            if (winner.equals(player1)){
                return player2;
            }
            return player1;
        }

        System.out.println("invalid command");

    }

}


    private static String rockPaperScissors(String player1, String player2) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(UserModel.getUserByUsername(player1).getNickname() + " is your turn");
            System.out.println("rock or paper or scissors?");
            String t1 = scanner.nextLine();
            while (!t1.equals("rock") && !t1.equals("paper") && !t1.equals("scissors")) {
                System.out.println("invalid command");
                System.out.println("rock or paper or scissors?");
                t1 = scanner.nextLine();
            }


            System.out.println(UserModel.getUserByUsername(player2).getNickname() + " is your turn");
            System.out.println("rock or paper or scissors?");
            String t2 = scanner.nextLine();
            while (!t2.equals("rock") && !t2.equals("paper") && !t2.equals("scissors")) {
                System.out.println("invalid command");
                System.out.println("rock or paper or scissors?");
                t2 = scanner.nextLine();
            }
            if (t1.equals("rock")) {
                if (t2.equals("rock")) {
                    System.out.println("The game equalised");
                    continue;
                }
                if (t2.equals("paper")) {
                    System.out.println(UserModel.getUserByUsername(player2).getNickname() + " is win");
                    return player2;


                }


                if (t2.equals("scissors")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname() + " is win");
                    return player1;

                }

            }

            if (t1.equals("paper")) {
                if (t2.equals("rock")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname() + " is win");
                    return player1;

                }
                if (t2.equals("paper")) {
                    System.out.println("The game equalised");


                }


                if (t2.equals("scissors")) {
                    System.out.println(UserModel.getUserByUsername(player2).getNickname() + " is win");
                    return player2;


                }

            }


            if (t1.equals("scissors")) {
                if (t2.equals("rock")) {
                    System.out.println(UserModel.getUserByUsername(player2).getNickname() + " is win");
                    return player2;

                }
                if (t2.equals("paper")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname() + " is win");
                    return player1;

                }


                if (t2.equals("scissors")) {
                    System.out.println("The game equalised");

                }

            }


        }
    }

    private static String chanceCoin(String player1, String player2) {
        Random rand = new Random();
        int n = rand.nextInt(2);
        if (n == 0) {
            return player1;
        }

        return player2;


    }
}