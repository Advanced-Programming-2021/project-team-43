package main.java.view;

import java.util.Scanner;


public class ShopView {

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

}