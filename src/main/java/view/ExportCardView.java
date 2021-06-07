package view;

import controller.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.SetCards;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ExportCardView extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("EXPORT CARD");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/export.fxml"));
        Scene scene = new Scene(root, 200, 200);
        scene.setFill(Color.DARKRED);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    TextField cardName;
    @FXML
    TextField level;
    @FXML
    TextField attribute;
    @FXML
    TextField cardType;
    @FXML
    TextField monsterType;
    @FXML
    TextField atk;
    @FXML
    TextField def;
    @FXML
    TextField description;
    @FXML
    TextField price;


    public void exportMonster(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/exportMonster.fxml"));
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.DARKRED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public AnchorPane pane1;

    public void exportCard(MouseEvent mouseEvent) throws Exception {
        ArrayList<String> cardInfo = new ArrayList<>();
        cardInfo.add(getClass().getResource("/newCards/0.jpg").toExternalForm());
        cardInfo.add(cardName.getText());
        cardInfo.add(level.getText());
        cardInfo.add(attribute.getText());
        cardInfo.add(monsterType.getText());
        cardInfo.add(cardType.getText());
        cardInfo.add(atk.getText());
        cardInfo.add(def.getText());
        cardInfo.add(description.getText());
        cardInfo.add(price.getText());
        cardInfo.add("Monster");
        String id;
        if (cardName == null) {
            id = "nullName";
        } else {
            id = cardName.getText();
        }
        try {
            FileWriter writerInfo = new FileWriter(id + ".txt");
            writerInfo.write(new Gson().toJson(cardInfo));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> cards = JSON.exportCad();
        cards.add(id);
        JSON.importCard(cards);
        new StartClass().start(stage);
    }

    public void exportSpellTrap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/exportSpellTrap.fxml"));
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.DARKRED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    TextField cardNam;
    @FXML
    TextField type;
    @FXML
    TextField Icon;
    @FXML
    TextField description2;
    @FXML
    TextField price2;
    @FXML
    TextField status;

    public void exportCard2(MouseEvent mouseEvent) throws Exception {
        ArrayList<String> cardInfo = new ArrayList<>();
        cardInfo.add(getClass().getResource("/newCards/3.jpg").toExternalForm());
        cardInfo.add(cardNam.getText());
        cardInfo.add(type.getText());
        cardInfo.add(Icon.getText());
        cardInfo.add(description2.getText());
        cardInfo.add(price2.getText());
        cardInfo.add(status.getText());
        cardInfo.add("Spell");

        String id;
        if (cardNam == null) {
            id = "nullName";
        } else {
            id = cardNam.getText();
        }
        try {
            FileWriter writerInfo = new FileWriter(id + ".txt");
            writerInfo.write(new Gson().toJson(cardInfo));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> cards = JSON.exportCad();
        cards.add(id);
        JSON.importCard(cards);
        new StartClass().start(stage);
    }

    public void exportCard3(MouseEvent mouseEvent) throws Exception {
        ArrayList<String> cardInfo = new ArrayList<>();
        cardInfo.add(getClass().getResource("/newCards/3.jpg").toExternalForm());
        cardInfo.add(cardNam.getText());
        cardInfo.add(type.getText());
        cardInfo.add(Icon.getText());
        cardInfo.add(description2.getText());
        cardInfo.add(price2.getText());
        cardInfo.add(status.getText());
        cardInfo.add("Trap");

        String id;
        if (cardNam == null) {
            id = "nullName";
        } else {
            id = cardNam.getText();
        }
        try {
            FileWriter writerInfo = new FileWriter(id + ".txt");
            writerInfo.write(new Gson().toJson(cardInfo));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> cards = JSON.exportCad();
        cards.add(id);
        JSON.importCard(cards);
        new StartClass().start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartClass().start(stage);
    }
}
