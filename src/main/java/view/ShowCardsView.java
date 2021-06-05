package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ShowCardsView extends Application {
    @Override
    public void start(Stage stg) throws Exception {
        showMonster();
        showSpellTrap();
    }

    private void showMonster() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0, j = 0; i <41; i++, k++) {
            if (k >= 9) {
                k = 0;
                j++;
            }
            Image image = new Image(getClass().getResource("/cards/" + i + ".jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(140);
            imageView.setFitWidth(140);
            gridPane.add(imageView, k, j);
        }
        Scene scene = new Scene(gridPane, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Monster Cards");
        stage.show();
    }

    private void showSpellTrap() {
        Stage stage1 = new Stage();
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0, j = 0; i <35; i++, k++) {
            if (k >= 9) {
                k = 0;
                j++;
            }
            Image image = new Image(getClass().getResource("/cards2/" + i + ".jpg").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            gridPane.add(imageView, k, j);
        }
        Scene scene = new Scene(gridPane, 600, 600);
        stage1.setScene(scene);
        stage1.setTitle("Spell&Trap Cards");
        stage1.show();

    }
}
