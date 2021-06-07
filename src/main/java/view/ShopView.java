package main.java.view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class ShopView extends Application {


    public AnchorPane shopPane;
    public ImageView cardImgView;
    private Stage shopStage;
    public Button previousBtn;
    private Image[] cardImages = new Image[74];
    private static int imageCounter = 0;

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/shopPage.fxml")));
        shopStage = primaryStage;
        shopStage.setScene(new Scene(root));
        shopStage.show();
    }

    public void initialize() {
        HashMap<String, Image> allCards = new HashMap<>(ShowCardsView.getAllCardImage());
        int i = 0;
        for (Map.Entry eachCard : allCards.entrySet()) {
            cardImages[i] = (Image) eachCard.getValue();
            i++;
        }
        cardImgView = new ImageView(cardImages[0]);
        cardImgView.setY(100);
        cardImgView.setX(300);
        shopPane.getChildren().add(cardImgView);
    }


    public void pressPreviousBtn() throws Exception {
        if (imageCounter != 0)
            imageCounter--;
        else
            imageCounter = 73;
        cardImgView.setImage(cardImages[imageCounter]);
    }

    public void pressNextBtn() throws Exception {
        if (imageCounter != 73)
            imageCounter++;
        else
            imageCounter = 0;
        cardImgView.setImage(cardImages[imageCounter]);
    }

}
