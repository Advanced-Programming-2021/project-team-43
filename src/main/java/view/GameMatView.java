package view;
import java.util.Scanner;

public class GameMatView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getCommand() {
        return scanner.nextLine();
    }

    public static int getAddress() {
        return scanner.nextInt();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }
}
