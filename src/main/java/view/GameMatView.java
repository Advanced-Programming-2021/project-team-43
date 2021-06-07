package main.java.view;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Scanner;


public class GameMatView extends Application {

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
