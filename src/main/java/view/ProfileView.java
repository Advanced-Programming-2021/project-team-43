package view;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.UserModel;

import java.io.IOException;
import java.util.Objects;


public class ProfileView extends Application {

    public Label usernameLbl;
    public TextField nicknameTxt;
    public ImageView profileImg;
    public AnchorPane profilePane;
    public Label nicknameChangeLbl;
    public TextField passwordTxt;
    public Label changePasswordLbl;
    public TextField newPasswordTxt;
    public ImageView nextBtn;
    public ImageView previousBtn;
    public AnchorPane achievementPane;
    private UserModel user;
    private final Image[] profileImages = new Image[32];
    private static int imageCounter = 0;
    private static Stage profileStage;
    public static ProfileView profileView;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/profilePage.fxml")));
        profileStage = primaryStage;
        profileStage.setScene(new Scene(root));
        profileStage.show();
    }

    public void initialize() {
        for (int i = 1; i < 31; i++)
            profileImages[i] = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/images/profile/char" + i + ".jpg")).toExternalForm()));
        user = UserModel.getUserByUsername(MainMenuController.username);
        profileImg.setImage(new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByUsername(MainMenuController.username).getImageUrl())).toExternalForm()));
        usernameLbl.setText(user.getUsername());
        nicknameTxt.setText(user.getNickname());
    }

    public void pressChangeNickname() {
        if (!nicknameTxt.getText().equals(user.getNickname()))
            nicknameChangeLbl.setText(MainMenuController.changeNickname(nicknameTxt.getText()));
    }

    public void pressChangePassword() {
        if (passwordTxt.getText().isEmpty())
            changePasswordLbl.setText("Please fill the Password field!");
        else if (newPasswordTxt.getText().isEmpty())
            changePasswordLbl.setText("Please fill the New Password field!");
        else
            changePasswordLbl.setText(MainMenuController.changePassword(passwordTxt.getText(), newPasswordTxt.getText()));
    }

    public void pressChangeImage() {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("profile"+MainMenuController.token+" change image "+imageCounter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImageUrl("/images/profile/char" + imageCounter + ".jpg");
    }

    public void pressNextImage() {
        if (imageCounter != 31)
            imageCounter++;
        else
            imageCounter = 0;
        profileImg.setImage(profileImages[imageCounter]);
    }

    public void pressPreviousImage() {
        if (imageCounter != 0)
            imageCounter--;
        else
            imageCounter = 31;
        profileImg.setImage(profileImages[imageCounter]);
    }

    public void pressBack() throws Exception {
        new MainMenuView().start(profileStage);
    }

    public void addChatCountAchievement() {
        //achievementPane.getChildren().add(imageView);

    }

}