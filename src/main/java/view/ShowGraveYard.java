package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.GameMatModel;
import main.java.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowGraveYard extends Application {

    private static int imageCounter = 0;
    private List<Image> cardImages = new ArrayList<>();
    public ImageView cardImgView;
    public static String userNickname ;//fill in game mat view
    public ImageView profile;
    public Label numberOfDeadCards;
    public Label nickName;
    public AnchorPane pane;
    public Label number;

    @Override
    public void start(Stage stg) throws Exception {
        Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/showGraveYard.fxml")));
        Scene scene = new Scene(root);
        stage1.setTitle("Show Grave Yard");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    public void initialize() throws Exception {
        nickName.setText(userNickname);
        profile.setImage(new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByNickname(userNickname).getImageUrl().toString()).toExternalForm())));
        List<String> deadCards = (GameMatModel.getGameMatByNickname(userNickname).getGraveyard());
        numberOfDeadCards.setText(String.valueOf(deadCards.size()));
        new ShowCardsView().setAllCards();
        for (String deadCard : deadCards) {
            cardImages.add(ShowCardsView.getCardImageByName(deadCard));
        }
        number = new Label("0");
        cardImgView.setImage(cardImages.get(0));
    }

    public void pressPreviousBtn() {
        if (imageCounter != 0)
            imageCounter--;
        else
            imageCounter = cardImages.size() - 1;
        number.setText(String.valueOf(imageCounter));
        cardImgView.setImage(cardImages.get(imageCounter));
    }

    public void pressNextBtn() {
        if (imageCounter != cardImages.size() - 1)
            imageCounter++;
        else
            imageCounter = 0;
        number.setText(String.valueOf(imageCounter));
        cardImgView.setImage(cardImages.get(imageCounter));
    }

}
