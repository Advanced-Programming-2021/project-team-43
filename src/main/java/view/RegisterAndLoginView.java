package main.java.view;
import main.java.controller.JSON;
import main.java.controller.RegisterAndLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.controller.SetCards;
import main.java.model.Card;
import main.java.model.ShopModel;
import main.java.model.UserModel;
import java.io.IOException;
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
    private static Stage registerLoginStage;


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new ShopModel(Card.getCards());
        registerLoginStage = stage;
        registerLoginStage.setWidth(1000);
        registerLoginStage.setHeight(760);
        registerLoginStage.setTitle("Yu Gi Oh");
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

    public void pressRegisterBtn() {
        Random random = new Random();
        int whichImage = random.nextInt(32);
        if (!registerUsernameTxt.getText().trim().isEmpty() && !registerNicknameTxt.getText().trim().isEmpty() && !registerPassword.getText().trim().isEmpty())
            registerMessageLbl.setText(RegisterAndLoginController.registerInGame(registerUsernameTxt.getText(), registerNicknameTxt.getText(), registerPassword.getText(), "/images/profile/char" + whichImage + ".jpg"));
        registerUsernameTxt.clear();
        registerNicknameTxt.clear();
        registerPassword.clear();
    }

    public void pressLoginBtn() throws Exception {
        loginMessageLbl.setText(RegisterAndLoginController.loginInGame(loginUsernameTxt.getText(), loginPassword.getText()));
        loginUsernameTxt.clear();
        loginPassword.clear();
        if (loginMessageLbl.getText().equals("User logged in successfully!")) {
            ShopView.resetFields();
            new MainMenuView().start(registerLoginStage);
        }
    }

    public void goToRegisterPage() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/registerPage.fxml")));
        registerLoginStage.setScene(new Scene(root));
    }

    public void gotToLoginPage() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/loginPage.fxml")));
        registerLoginStage.setScene(new Scene(root));
    }

    public void pressBackBtn() throws IOException {
        start(registerLoginStage);
    }

}