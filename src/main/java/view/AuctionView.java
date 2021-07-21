package view;
import controller.BazarController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.BazarModel;
import model.Card;
import model.UserModel;

import java.util.*;


public class AuctionView extends Application {


    public Label cardNameLbl;
    public ImageView cardImg;
    public AnchorPane auctionPane;
    private static int cardCounter = 0;
    public static BazarModel cardNameClicked;
    private final List<Image> cardImages = new ArrayList<>();
    private final List<BazarModel> cardInfo = new ArrayList<>();
    private static Stage auctionStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/auctionPage.fxml")));
        auctionStage = primaryStage;
        auctionStage.setScene(new Scene(root));
        auctionStage.show();
    }

    public void initialize() {
        System.out.println(UserModel.all.size());
        ArrayList<BazarModel> allCardName = UserModel.all;//esme cartaii ke hastan in array ro por kon ba oona
        if (allCardName.size()!=0){
            System.out.println("12");
            for (BazarModel eachCard : allCardName) {
                cardImages.add(ShowCardsView.getCardImageByName(eachCard.cardName));
                cardInfo.add(eachCard);
            }
            cardImg = new ImageView(cardImages.get(0));
            cardImg.setY(117);
            cardImg.setX(307);
            cardImg.setFitWidth(386);
            cardImg.setFitHeight(489);
            auctionPane.getChildren().add(cardImg);
            cardImg.setOnMouseClicked(mouseEvent -> {
                cardNameClicked = cardInfo.get(cardCounter);
                try {
                    new Auction2View().start(auctionStage);
                } catch (Exception ignored) {
                }
            });
        }}

    public void nextCard() {
        if (cardCounter != cardImages.size() - 1)
            cardCounter++;
        else
            cardCounter = 0;
        cardImg.setImage(cardImages.get(cardCounter));
        cardNameLbl.setText(cardInfo.get(cardCounter).cardName);
    }

    public void previousCard() {
        if (cardCounter != 0)
            cardCounter--;
        else
            cardCounter = cardImages.size() - 1;
        cardImg.setImage(cardImages.get(cardCounter));
        cardNameLbl.setText(cardInfo.get(cardCounter).cardName);
    }

    public void back() throws Exception {
        new MainMenuView().start(auctionStage);
    }

}