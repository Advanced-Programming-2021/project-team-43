package main.java.controller;

import main.java.model.UserModel;

import java.util.Random;
import java.util.Scanner;

public class PickFirstPlayer {
    public static void chose(String player1, String player2) {
        Random rand = new Random();
        int n = rand.nextInt(2);
        if (n == 0) {
            chanceCoin(player1, player2);
        }
        if (n == 1) {
            rockPaperScissors(player1, player2);
        }
    }

    private static void rockPaperScissors(String player1, String player2) {
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
                    System.out.println(UserModel.getUserByUsername(player2).getNickname()+" is win");


                }


                if (t2.equals("scissors")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname()+" is win");

                }

            }

            if (t1.equals("paper")) {
                if (t2.equals("rock")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname()+" is win");

                }
                if (t2.equals("paper")) {
                    System.out.println("The game equalised");


                }


                if (t2.equals("scissors")) {
                    System.out.println(UserModel.getUserByUsername(player2).getNickname()+" is win");

                }

            }


            if (t1.equals("scissors")) {
                if (t2.equals("rock")) {
                    System.out.println(UserModel.getUserByUsername(player2).getNickname()+" is win");

                }
                if (t2.equals("paper")) {
                    System.out.println(UserModel.getUserByUsername(player1).getNickname()+" is win");

                }


                if (t2.equals("scissors")) {
                    System.out.println("The game equalised");

                }

            }


        }
    }

    private static void chanceCoin(String player1, String player2) {
        Random rand = new Random();
        int n = rand.nextInt(2);
        if (n == 0) {
            //player 1 is first
        }
        if (n == 1) {
            //player 2 is first
        }

    }
}
