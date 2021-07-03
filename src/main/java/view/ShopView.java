package view;
import controller.MainMenuController;
import controller.ShopController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Card;
import model.UserModel;

import java.util.*;


public class ShopView extends Application {


    public AnchorPane shopPane;
    public ImageView cardImgView;
    public ImageView previousBtn;
    public ImageView nextBtn;
    public Label messageLbl;
    public VBox cardsVBox;
    public Button buyBtn;
    public Label moneyLbl;
    public Label priceLbl;
    private boolean isShowBtnPressed;
    private UserModel user;
    private final List<Image> cardImages = new ArrayList<>();
    private final List<Label> cardInfo = new ArrayList<>();
    private static int imageCounter = 0;
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
        moneyLbl.setFont(new Font(20));
        user = UserModel.getUserByUsername(MainMenuController.username);
        HashMap<String, Image> allCards = new HashMap<>(ShowCardsView.getAllCardImage());
        for (Map.Entry<String, Image> eachCard : allCards.entrySet())
            cardImages.add(eachCard.getValue());
        cardImgView = new ImageView(cardImages.get(0));
        cardImgView.setY(58);
        cardImgView.setX(44);
        cardImgView.setFitWidth(282);
        cardImgView.setFitHeight(418);
        priceLbl.setText("Price: " + Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(0))).getPrice());
        buyBtn.setDisable(Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(0))).getPrice() > user.getUserCoin());
        shopPane.getChildren().add(cardImgView);
    }

    public void pressPreviousBtn() {
        if (imageCounter != 0)
            imageCounter--;
        else
            imageCounter = cardImages.size() - 1;
        cardImgView.setImage(cardImages.get(imageCounter));
        priceLbl.setText("Price: " + Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(imageCounter))).getPrice());
        buyBtn.setDisable(Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(imageCounter))).getPrice() > user.getUserCoin());
        messageLbl.setText("");
    }

    public void pressNextBtn() {
        if (imageCounter != cardImages.size() - 1)
            imageCounter++;
        else
            imageCounter = 0;
        cardImgView.setImage(cardImages.get(imageCounter));
        priceLbl.setText("Price: " + Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(imageCounter))).getPrice());
        buyBtn.setDisable(Card.getCardsByName(ShowCardsView.getNameByImage(cardImages.get(imageCounter))).getPrice() > user.getUserCoin());
        messageLbl.setText("");
    }

    public void pressBuyBtn() {
        String cardName = ShowCardsView.getNameByImage(cardImages.get(imageCounter));
        String message = ShopController.shopBuy(cardName);
        if (message.equals("Not enough money")) {
            messageLbl.setText("");
            buyBtn.setDisable(true);
        }
        else {
            messageLbl.setText(message);
            if (message.equals("Card added successfully!")) {
                if (getCardInfoLblByCardName(cardName) != null)
                    getCardInfoLblByCardName(cardName).setText(cardName + "  :  " + user.getUserAllCards().get(cardName));
                else {
                    cardInfo.add(new Label(cardName + "  :  " + user.getUserAllCards().get(cardName)));
                    cardInfo.get(cardInfoCounter).setTextFill(Color.rgb(255, 255, 0));
                    cardInfo.get(cardInfoCounter).setFont(new Font("Bodoni MT", 15));
                    cardsVBox.getChildren().add(cardInfo.get(cardInfoCounter));
                    cardInfoCounter++;
                }
            }
        }
        moneyLbl.setText("Coin: " + user.getUserCoin());
    }

    public void pressMyCardsBtn() {
        if (!isShowBtnPressed) {
            HashMap<String, Integer> myCards = new HashMap<>(user.getUserAllCards());
            for (Map.Entry<String, Integer> eachCard : myCards.entrySet()) {
                cardInfo.add(new Label(eachCard.getKey() + "  :  " + eachCard.getValue()));
                cardInfo.get(cardInfoCounter).setTextFill(Color.rgb(255, 255, 0));
                cardInfo.get(cardInfoCounter).setFont(new Font("Bodoni MT", 15));
                cardsVBox.getChildren().add(cardInfo.get(cardInfoCounter));
                cardInfoCounter++;
            }
            cardsVBox.setVisible(true);
            isShowBtnPressed = true;
        }
    }

    public Label getCardInfoLblByCardName(String cardName) {
        String[] text;
        for (int i = 0; i < cardInfoCounter; i++) {
            text = cardInfo.get(i).getText().split("\\s\\s");
            if (text[0].equals(cardName))
                return cardInfo.get(i);
        }
        return null;
    }

    public void pressBackBtn() throws Exception {
        imageCounter = 0;
        cardInfoCounter = 0;
        new MainMenuView().start(shopStage);
    }

    public static void resetFields() {
        cardInfoCounter = 0;
        imageCounter = 0;
    }

}
