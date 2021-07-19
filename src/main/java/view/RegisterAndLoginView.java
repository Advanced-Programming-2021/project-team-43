package view;
import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;
import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;


public class RegisterAndLoginView extends Application {

    public TextField registerUsernameTxt;
    public TextField registerNicknameTxt;
    public PasswordField registerPassword;
    public Label registerMessageLbl;
    public TextField loginUsernameTxt;
    public PasswordField loginPassword;
    public Label loginMessageLbl;
    public Button pauseBtn = new Button();
    private static Stage registerLoginStage;
    public static MediaPlayer note;
    public static boolean isMusicOn = true;
    private static boolean isFirstTime = true;
    public static ShowCardsView showCardsView;
    public static Socket socket;
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static ObjectOutputStream objectOutputStream;
    public static ObjectInputStream objectInputStream;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 1111);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (isFirstTime) {
            playAudio();
            isFirstTime = false;
        }
        registerLoginStage = stage;
        registerLoginStage.setWidth(1000);
        registerLoginStage.setHeight(760);
        registerLoginStage.setTitle("Yu Gi Oh");
        registerLoginStage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/welcomePage.fxml")));
        registerLoginStage.setScene(new Scene(root));
        registerLoginStage.show();
        setUpGame();
    }

    public void setUpGame() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        (showCardsView = new ShowCardsView()).setAllCards();
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
        } else {
            note.play();
            isMusicOn = true;
            pauseBtn.setText("Pause Music");
        }
    }

}