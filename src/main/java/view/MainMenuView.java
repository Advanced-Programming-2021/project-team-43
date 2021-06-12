package main.java.view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.Scanner;



public class MainMenuView extends Application {

    public Button shop;
    public Button profile;
    public Button duel;
    public Button importExport;
    public Button cardCreator;
    public Button scoreboard;
    public Button deck;
    public Button logout;
    private static Stage stage;

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initialize() {

    }

    public void Duel() throws Exception {
        new Duel().start(stage);
    }

    public void Deck() throws Exception {
        new DeckView().start(stage);
    }

    public void Scoreboard() throws Exception {
        new Scoreboard().start(stage);
    }

    public void Profile() throws Exception {
        new ProfileView().start(stage);
    }

    public void Shop() throws Exception {
        new ShopView().start(stage);
    }

    public void ImportExport() throws Exception {
        new ImportCardView().start(stage);
    }

    public void LogOut() throws Exception {
        new RegisterAndLoginView().start(stage);
    }

    public void CardCreator() throws Exception {

    }

}
