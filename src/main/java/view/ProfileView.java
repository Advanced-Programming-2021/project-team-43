package main.java.view;
import main.java.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.model.UserModel;
import java.util.Objects;


public class ProfileView extends Application {

    public Label usernameLbl;
    public TextField nicknameTxt;
    public Button changeNicknameBtn;
    public ImageView profileImg;
    public AnchorPane profilePane;
    public Label nicknameChangeLbl;
    public TextField passwordTxt;
    public Label changePasswordLbl;
    public TextField newPasswordTxt;
    public ImageView nextBtn;
    public ImageView previousBtn;
    public Button changePasswordBtn;
    private UserModel user;
    private Image[] profileImages = new Image[32];
    private static int imageCounter = 0;
    private static Stage profileStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/profilePage.fxml")));
        profileStage = primaryStage;
        profileStage.setWidth(1000);
        profileStage.setHeight(760);
        profileStage.setTitle("Yo Gi Oh");
        profileStage.setResizable(false);
        profileStage.setScene(new Scene(root));
        profileStage.show();
    }

    public void initialize() {
        for (int i = 0; i < 31; i++)
            profileImages[i] = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/images/profile/char" + i + ".jpg")).toExternalForm()));
        user = UserModel.getUserByUsername(MainMenuController.username);
        profileImg = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByUsername(MainMenuController.username).getImageUrl())).toExternalForm()));
        profileImg.setX(436);
        profileImg.setY(110);
        usernameLbl.setText(user.getUsername());
        nicknameTxt.setText(user.getNickname());
        profilePane.getChildren().add(profileImg);
    }

    public void pressChangeNickname() {
        if (!nicknameTxt.getText().equals(user.getNickname())) {
            user.changeNickname(nicknameTxt.getText());
            nicknameChangeLbl.setText("Nickname Changed Successfully!");
        }
    }

    public void pressChangePassword() {
        if (passwordTxt.getText().isEmpty())
            changePasswordLbl.setText("Please enter your Password!");
        else if (newPasswordTxt.getText().isEmpty())
            changePasswordLbl.setText("Please enter your New Password!");
        else if (!passwordTxt.getText().equals(user.getPassword()))
            changePasswordLbl.setText("Please enter your Password correctly!");
        else {
            user.changePassword(newPasswordTxt.getText());
            changePasswordLbl.setText("Password Changed Successfully!");
        }
    }

    public void pressChangeImage() {
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

}
