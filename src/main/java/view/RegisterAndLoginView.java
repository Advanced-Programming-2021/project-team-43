package view;



import java.util.Scanner;

public class RegisterAndLoginView {


    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public static void showInput(String input) {
        System.out.println(input);

    }
}
