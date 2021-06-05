package view;

import com.google.gson.Gson;
import controller.SetCards;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Card;

import java.io.FileWriter;
import java.io.IOException;

public class ExportCardView extends Application {
    @Override
    public void start(Stage stg) throws Exception {
        Stage stage = new Stage();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        stage.setTitle("EXPORT CARD");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/export.fxml"));
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.DARKRED);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    TextField cardName;
    @FXML
    Label label;
    @FXML
    Label label2;

    public void export() {
        label.setVisible(false);
        label2.setVisible(false);
        if (Card.getCardsByName(cardName.getText()) != null) {
            try {
                FileWriter writerInfo = new FileWriter(cardName.getText() + ".txt");
                writerInfo.write(new Gson().toJson(cardName.getText()));
                writerInfo.close();
                label2.setText("Card exported!");
                label2.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            label.setText("No card exist with this name!");
            label.setVisible(true);
        }
    }
}
