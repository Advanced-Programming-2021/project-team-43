package view;
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
import model.*;
import java.util.*;


public class ShowGraveYard extends Application {

    private static int imageCounter = 0;
    private List<Image> cardImages = new ArrayList<>();
    public ImageView cardImgView;
    public static String userNickname;
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
        stage1.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        Scene scene = new Scene(root);
        stage1.setTitle("Graveyard");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    public void initialize() throws Exception {
        cardImgView.setFitWidth(166);
        cardImgView.setFitHeight(210);
        cardImgView.setLayoutX(207);
        cardImgView.setLayoutY(80);
        nickName.setText(userNickname);
        profile.setImage(new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByNickname(userNickname).getImageUrl())).toExternalForm()));
        List<String> deadCards = (GameMatModel.getGameMatByNickname(userNickname).getGraveyard());
        numberOfDeadCards.setText(String.valueOf(deadCards.size()));
        new ShowCardsView().setAllCards();
        for (String deadCard : deadCards) {
            String[] split = deadCard.split("/");
            cardImages.add(ShowCardsView.getCardImageByName(split[0]));
        }
        number = new Label("0");
        if (!cardImages.isEmpty()) {
            cardImgView.setImage(cardImages.get(0));
        }
    }

    public void pressPreviousBtn() {
        if (!cardImages.isEmpty()) {
            if (imageCounter != 0)
                imageCounter--;
            else
                imageCounter = cardImages.size() - 1;
            number.setText(String.valueOf(imageCounter));
            cardImgView.setImage(cardImages.get(imageCounter));
        }
    }

    public void pressNextBtn() {
        if (!cardImages.isEmpty()) {
            if (imageCounter != cardImages.size() - 1)
                imageCounter++;
            else
                imageCounter = 0;
            number.setText(String.valueOf(imageCounter));
            cardImgView.setImage(cardImages.get(imageCounter));
        }
    }

}
