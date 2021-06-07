package main.java.view;
import main.java.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.java.controller.ShopController;
import main.java.model.UserModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;


public class ShopView extends Application {

    public AnchorPane shopPane;
    public ImageView cardImgView;
    public ImageView previousBtn;
    public ImageView nextBtn;
    public Label messageLbl;
    public VBox cardsVBox;
    private boolean isShowBtnPressed;
    private UserModel user;
    private Image[] cardImages = new Image[74];
    private static int imageCounter = 0;
    private final Label[] cardInfo = new Label[74];
    private static int cardInfoCounter = 0;
    private static Stage shopStage;


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
        user = UserModel.getUserByUsername(MainMenuController.username);
        HashMap<String, Image> allCards = new HashMap<>(ShowCardsView.getAllCardImage());
        int i = 0;
        for (Map.Entry eachCard : allCards.entrySet()) {
            cardImages[i] = (Image) eachCard.getValue();
            i++;
        }
        cardImgView = new ImageView(cardImages[0]);
        cardImgView.setY(46);
        cardImgView.setX(76);
        cardImgView.setFitWidth(350);
        cardImgView.setFitHeight(500);
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
        if (imageCounter != 72)
            imageCounter++;
        else
            imageCounter = 0;
        cardImgView.setImage(cardImages[imageCounter]);
    }

    public void pressBuyBtn() {
        String cardName = ShowCardsView.getNameByImage(cardImages[imageCounter]);
        if (cardName.equals("Terratiger, the Empowered Warrior"))
            cardName = "\"Terratiger, the Empowered Warrior\"";
        messageLbl.setText(ShopController.shopBuy(cardName));
        if (messageLbl.getText().equals("Card added successfully!")) {
            if (getCardInfoLblByCardName(cardName) != null)
                getCardInfoLblByCardName(cardName).setText(cardName + "  :  " + user.getUserAllCards().get(cardName));
            else {
                cardInfo[cardInfoCounter] = new Label(cardName + "  :  " + user.getUserAllCards().get(cardName));
                cardInfo[cardInfoCounter].setTextFill(Color.rgb(255, 255, 0));
                cardInfo[cardInfoCounter].setFont(new Font("Bodoni MT", 15));
                cardsVBox.getChildren().add(cardInfo[cardInfoCounter]);
            }
        }
    }

    public void pressMyCardsBtn() {
        if (!isShowBtnPressed) {
            HashMap<String, Integer> myCards = new HashMap<>(user.getUserAllCards());
            for (Map.Entry eachCard : myCards.entrySet()) {
                cardInfo[cardInfoCounter] = new Label(eachCard.getKey().toString() + "  :  " + eachCard.getValue().toString());
                cardInfo[cardInfoCounter].setTextFill(Color.rgb(255, 255, 0));
                cardInfo[cardInfoCounter].setFont(new Font("Bodoni MT", 15));
                cardsVBox.getChildren().add(cardInfo[cardInfoCounter]);
                cardInfoCounter++;
            }
            cardsVBox.setVisible(true);
            isShowBtnPressed = true;
        }
    }

    public Label getCardInfoLblByCardName(String cardName) {
        String[] text;
        for (int i = 0; i < cardInfoCounter; i++) {
            text = cardInfo[i].getText().split("\\s\\s");
            if (text[0].equals(cardName))
                return cardInfo[i];
        }
        return null;
    }

    public void pressBackBtn() throws Exception {
        new MainMenuView().start(shopStage);
    }

}
