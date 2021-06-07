package view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.SetCards;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportCardView extends Application {
    @Override
    public void start(Stage stg) throws Exception {
        Stage stage = new Stage();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        stage.setTitle("IMPORT CARD");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.DARKRED);
        final Text target = new Text(100, 100, "DROP CARD'S FILE HERE");
        target.setScaleX(2.0);
        target.setScaleY(2.0);
        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasFiles()) {
                    target.setFill(Color.RED);
                }
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                target.setFill(Color.BLACK);
                event.consume();
            }
        });

        target.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard file = event.getDragboard();
                boolean success = false;
                if (file.hasFiles()) {
                    dragboard = file;
                    show();
                    target.setText("File Imported");
                    success = true;

                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        root.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }

    static Dragboard dragboard;

    public void show() {
        ArrayList<String> card = new ArrayList<>();
        String cardInfo = "There is no info in this file!";
        try {
            String address = dragboard.getUrl().substring(6);
            String readCardNames = new String(Files.readAllBytes(Paths.get(address)));
            card = new Gson().fromJson(readCardNames,
                    new TypeToken<List<String>>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (card != null) {
            if (card.get(9).equals("Monster")) {
                cardInfo = "Name: " + card.get(0) + "\n" + "Level: " + card.get(1) + "\n" +
                        "Attribute: " + card.get(2) + "\n" +
                        "Monster Type: " + card.get(3) + "\n" +
                        "Card Type: " + card.get(4) + "\n" +
                        "Atk: " + card.get(5) + "\n" +
                        "Def: " + card.get(6) + "\n" +
                        "Description: " + card.get(7) + "\n" +
                        "Price: " + card.get(8);
                new MonsterCard(card.get(2), card.get(0), Integer.parseInt(card.get(1)), card.get(3),
                        Integer.parseInt(card.get(5)), Integer.parseInt(card.get(6)), "Monster", card.get(4),
                        false, card.get(7), Integer.parseInt(card.get(8)));
            } else if (card.get(6).equals("Trap")) {
                cardInfo = "Name: " + card.get(0) + "\n" + "Type: " + card.get(1) + "\n" +
                        "Icon (Property): " + card.get(2) + "\n" +
                        "Description: " + card.get(3) + "\n" +
                        "Price: " + card.get(4);
                new TrapCard(card.get(0), card.get(1), card.get(2), card.get(3), Integer.parseInt(card.get(4)), card.get(5));
            } else if (card.get(6).equals("Spell")) {
                cardInfo = "Name: " + card.get(0) + "\n" + "Type: " + card.get(1) + "\n" +
                        "Icon (Property): " + card.get(2) + "\n" +
                        "Description: " + card.get(3) + "\n" +
                        "Price: " + card.get(4);
                new SpellCard(card.get(0), card.get(1), card.get(2), card.get(3), Integer.parseInt(card.get(4)), card.get(5));
            }
        }
        Stage stage = new Stage();
        Text text = new Text(150, 80, cardInfo);
        text.setFont(Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(new Group(text));
        scene.setFill(Color.DARKRED);
        stage.setTitle("Card Info");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

}
