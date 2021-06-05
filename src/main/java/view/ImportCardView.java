package view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.JSON;
import controller.SetCards;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
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
import model.Card;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImportCardView extends Application {
    @Override
    public void start(Stage stg) throws Exception {
        Stage stage=new Stage();
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
                    target.setText("File Imported");
                    success = true;
                    show();
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
        String cardName="Not known card!" ;
        String cardInfo = "There is no card with this name!";
        try {
            String readCardNames = new String(Files.readAllBytes(Paths.get(dragboard.getUrl())));//moshkel
          cardName= new Gson().fromJson(readCardNames,
                    new TypeToken<String>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
            cardInfo = "Name: " + cardName + "\n" + "Level: " + MonsterCard.getMonsterByName(cardName).getLevel() + "\n" +
                    "Attribute: " + MonsterCard.getMonsterByName(cardName).getAttribute() + "\n" +
                    "Monster Type: " + MonsterCard.getMonsterByName(cardName).getMonsterType() + "\n" +
                    "Card Type: " + MonsterCard.getMonsterByName(cardName).getCardType() + "\n" +
                    "Atk: " + MonsterCard.getMonsterByName(cardName).getAttack() + "\n" +
                    "Def: " + MonsterCard.getMonsterByName(cardName).getDefend() + "\n" +
                    "Description: " + MonsterCard.getMonsterByName(cardName).getDescription() + "\n" +
                    "Price: " + MonsterCard.getMonsterByName(cardName).getPrice();
        } else if (Card.getCardsByName(cardName).getCardModel().equals("Trap")) {
            cardInfo = "Name: " + cardName + "\n" + "Type: Trap" + "\n" +
                    "Icon (Property): " + TrapCard.getTrapCardByName(cardName).getIcon() + "\n" +
                    "Description: " + TrapCard.getTrapCardByName(cardName).getDescription() + "\n" +
                    "Price: " + TrapCard.getTrapCardByName(cardName).getPrice();
        } else if (Card.getCardsByName(cardName).getCardModel().equals("Spell")) {
            cardInfo = "Name: " + cardName + "\n" + "Type: Spell" + "\n" +
                    "Icon (Property): " + SpellCard.getSpellCardByName(cardName).getIcon() + "\n" +
                    "Description: " + SpellCard.getSpellCardByName(cardName).getDescription() + "\n" +
                    "Price: " + SpellCard.getSpellCardByName(cardName).getPrice();
        }
        if (!cardInfo.equals("There is no card with this name!")) {
            ArrayList<String> cards = JSON.exportCad();
            cards.add(cardName);
            JSON.importCard(cards);
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
