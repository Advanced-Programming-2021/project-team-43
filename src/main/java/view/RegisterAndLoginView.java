package view;
import controller.JSON;
import controller.RegisterAndLoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.SetCards;
import model.Card;
import model.ShopModel;
import model.UserModel;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class RegisterAndLoginView extends Application {


    public TextField registerUsernameTxt;
    public TextField registerNicknameTxt;
    public PasswordField registerPassword;
    public Label registerMessageLbl;
    public TextField loginUsernameTxt;
    public PasswordField loginPassword;
    public Label loginMessageLbl;
    private static Stage registerLoginStage;


    @Override
    public void start(Stage stage) throws IOException {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        registerLoginStage = stage;
        registerLoginStage.setWidth(1000);
        registerLoginStage.setHeight(760);
        registerLoginStage.setTitle("Yo Gi Oh");
        registerLoginStage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/welcomePage.fxml")));
        registerLoginStage.setScene(new Scene(root));
        registerLoginStage.show();
        if (JSON.readUserInfo() != null)
            UserModel.allUsersInfo.putAll(JSON.readUserInfo());
        if (JSON.readUsernames() != null)
            UserModel.allUsernames.addAll(JSON.readUsernames());
        if (JSON.readUserNicknames() != null)
            UserModel.allUsersNicknames.addAll(JSON.readUserNicknames());

        new ShowCardsView().setAllCards();
    }
    @FXML
    public void initialize() {
        playAudio();
    }

    MediaPlayer backgroundMusic;

    public void playAudio() {
        Media media = new Media(this.getClass().getResource("/18.mp3").toExternalForm());
        MediaPlayer note = new MediaPlayer(media);
        backgroundMusic = note;
        note.setCycleCount(-1);
        note.setAutoPlay(true);

   }
    public static void main(String[] args) {
        launch();
    }

    public void pressRegisterBtn() {
        Random random = new Random();
        int whichImage = random.nextInt(32);
        registerMessageLbl.setText(RegisterAndLoginController.registerInGame(registerUsernameTxt.getText(), registerNicknameTxt.getText(), registerPassword.getText(), "/images/profile/char" + whichImage + ".jpg"));
        registerUsernameTxt.clear();
        registerNicknameTxt.clear();
        registerPassword.clear();
    }

    public void pressLoginBtn() throws Exception {
        loginMessageLbl.setText(RegisterAndLoginController.loginInGame(loginUsernameTxt.getText(), loginPassword.getText()));
        loginUsernameTxt.clear();
        loginPassword.clear();
        if (loginMessageLbl.getText().equals("User logged in successfully!"))
            new MainMenuView().start(registerLoginStage);
    }

    public void goToRegisterPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/registerPage.fxml")));
        registerLoginStage.setScene(new Scene(root));
    }

    public void gotToLoginPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/loginPage.fxml")));
        registerLoginStage.setScene(new Scene(root));
    }

    public void pressBackBtn() throws IOException {
        start(registerLoginStage);
    }

}