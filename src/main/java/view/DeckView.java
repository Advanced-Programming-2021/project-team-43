package main.java.view;

import java.util.Scanner;

public class DeckView {
    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public static void showInput(String input) {
        System.out.println(input);

    }
}