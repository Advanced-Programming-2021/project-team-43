package view;

import com.google.gson.Gson;
import controller.SetCards;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class ExportCardView extends Application {
    static Dragboard dragboard;


    @Override
    public void start(Stage stg) throws Exception {
        Stage stage = new Stage();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        stage.setTitle("EXPORT CARD");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.DARKRED);
        final Text target = new Text(100, 100, "DROP CARD'S NAME HERE");
        target.setScaleX(2.0);
        target.setScaleY(2.0);


        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
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
                Dragboard cardName = event.getDragboard();
                boolean success = false;
                if (cardName.hasString()) {
                    dragboard = cardName;
                    target.setText("Card exported");
                    export();
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

    public void export() {
        try {
            FileWriter writerInfo = new FileWriter(dragboard.getString()+".txt");
            writerInfo.write(new Gson().toJson(dragboard.getString()));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
