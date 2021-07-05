package view;
import controller.Json;
import controller.MainMenuController;
import controller.RegisterAndLoginController;
import controller.SetCards;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Card;
import model.DeckModel;
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
    public static MediaPlayer note;
    public Button pauseBtn = new Button();
    private static boolean isMusicOn = true;
    private static boolean isFirstTime = true;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (isFirstTime) {
            playAudio();
            isFirstTime = false;
        }
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
        if (Json.readUserInfo() != null)
            UserModel.allUsersInfo.putAll(Json.readUserInfo());
        if (Json.readUsernames() != null)
            UserModel.allUsernames.addAll(Json.readUsernames());
        if (Json.readUserNicknames() != null)
            UserModel.allUsersNicknames.addAll(Json.readUserNicknames());
        if (!UserModel.isRepeatedUsername("AI")){
            UserModel userModel = new UserModel("AI", "p", "AI", "/images/profile/char0.jpg");
            DeckModel deckModel = new DeckModel("AILevel1");
            MainMenuController.username = "AI";
            for (int i = 0; i < 6; i++) {
                deckModel.addCardToMain("Axe Raider");
                deckModel.addCardToMain("Horn Imp");
                deckModel.addCardToMain("Silver Fang");
                deckModel.addCardToMain("Fireyarou");
                deckModel.addCardToMain("Curtain of the dark ones");
                deckModel.addCardToMain("Dark Blade");
                deckModel.addCardToMain("Warrior Dai Grepher");
                deckModel.addCardToMain("Bitron");
            }
            userModel.addDeck(deckModel);
            userModel.setActiveDeck("AILevel1");
        }
        new ShowCardsView().setAllCards();
    }

    public void playAudio() {
        pauseBtn.setText("Pause Music");
        isMusicOn = true;
        Media media = new Media(Objects.requireNonNull(this.getClass().getResource("/sounds/18.mp3")).toExternalForm());
        note = new MediaPlayer(media);
        note.setCycleCount(-1);
        note.setAutoPlay(true);
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

    public void pressPauseMusicBtn() {
        if (isMusicOn) {
            note.stop();
            pauseBtn.setText("Resume Music");
            isMusicOn = false;
        }
        else {
            note.play();
            isMusicOn = true;
            pauseBtn.setText("Pause Music");
        }
    }

}