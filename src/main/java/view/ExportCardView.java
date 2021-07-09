package view;
import controller.*;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExportCardView extends Application {
    private static Stage stage;
    @FXML
    private Label Label;
    @FXML
    private Label label2;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("EXPORT CARD");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/export.fxml"));
        Scene scene = new Scene(root);
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

    public void exportMonster(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/exportMonster.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKRED);
        stage.setScene(scene);
        stage.show();
    }

    public void exportCard(MouseEvent mouseEvent) throws Exception {
        if (isNumeric(level.getText()) && isNumeric(atk.getText()) && isNumeric(def.getText()) &&
                isNotEmpty(cardName.getText()) && isNotEmpty(cardType.getText()) &&
                isNotEmpty(attribute.getText()) && isNotEmpty(description.getText()) && isNotEmpty(monsterType.getText())
                && isValidCardType() && isValidAttribute() && isValidMonsterType()) {
            if (isMonsterExit()) {
                ArrayList<String> cardInfo = new ArrayList<>();
                cardInfo.add(getClass().getResource("/images/newCards/0.jpg").toExternalForm());
                cardInfo.add(cardName.getText());
                cardInfo.add(level.getText());
                cardInfo.add(attribute.getText());
                cardInfo.add(monsterType.getText());
                cardInfo.add(cardType.getText());
                cardInfo.add(atk.getText());
                cardInfo.add(def.getText());
                cardInfo.add(description.getText());
                String price = String.valueOf(Integer.parseInt(level.getText()) * 100);
                cardInfo.add(price);
                UserModel.getUserByUsername(MainMenuController.username).changeUserCoin(-Integer.parseInt(level.getText()) * 10);
                cardInfo.add("Monster");
                String id;
                if (cardName == null) {
                    id = "nullName";
                } else {
                    id = cardName.getText();
                }
                FileWriter writer = new FileWriter(id+".csv");
                String collect = cardInfo.stream().collect(Collectors.joining("\n"));
                writer.write(collect);
                writer.close();
                try {
                    FileWriter writerInfo = new FileWriter(id + ".txt");
                    writerInfo.write(new Gson().toJson(cardInfo));
                    writerInfo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
                new StartClass().start(stage);
            } else {
                Label.setText("This card isn't in list!");
            }
        } else {
            Label.setText("Input is Invalid");
        }
    }

    public void exportSpellTrap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/exportSpellTrap.fxml"));
        Scene scene = new Scene(root);
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
    TextField status;

    public void exportCard2(MouseEvent mouseEvent) throws Exception {
        if (isNotEmpty(cardNam.getText()) && isNotEmpty(type.getText()) &&
                isNotEmpty(Icon.getText()) && isNotEmpty(description2.getText()) && isNotEmpty(status.getText())
                && isValidIcon()) {
            if (isSpellTrap()) {
                ArrayList<String> cardInfo = new ArrayList<>();
                cardInfo.add(getClass().getResource("/images/newCards/3.jpg").toExternalForm());
                cardInfo.add(cardNam.getText());
                cardInfo.add(type.getText());
                cardInfo.add(Icon.getText());
                cardInfo.add(description2.getText());
                int count = 0;
                for (int i = 0; i < cardNam.getText().length(); i++) {
                    if (cardNam.getText().charAt(i) != ' ')
                        count++;
                }
                String price2 = String.valueOf(count * 5);
                cardInfo.add(price2);
                UserModel.getUserByUsername(MainMenuController.username).changeUserCoin(-count / 2);
                cardInfo.add(status.getText());
                cardInfo.add("Spell");
                String id;
                if (cardNam == null) {
                    id = "nullName";
                } else {
                    id = cardNam.getText();
                }
                FileWriter writer = new FileWriter(id+".csv");
                String collect = cardInfo.stream().collect(Collectors.joining("\n"));
                writer.write(collect);
                writer.close();
                try {
                    FileWriter writerInfo = new FileWriter(id + ".txt");
                    writerInfo.write(new Gson().toJson(cardInfo));
                    writerInfo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
                new StartClass().start(stage);
            } else {
                label2.setText("This card isn't in list!");
            }
        } else {
            label2.setText("Input is Invalid");
        }
    }


    public void exportCard3(MouseEvent mouseEvent) throws Exception {

        if (isNotEmpty(cardNam.getText()) && isNotEmpty(type.getText()) &&
                isNotEmpty(Icon.getText()) && isNotEmpty(description2.getText()) && isNotEmpty(status.getText())
                && isValidIcon()) {
            if (isSpellTrap()) {
                ArrayList<String> cardInfo = new ArrayList<>();
                cardInfo.add(getClass().getResource("/images/newCards/3.jpg").toExternalForm());
                cardInfo.add(cardNam.getText());
                cardInfo.add(type.getText());
                cardInfo.add(Icon.getText());
                cardInfo.add(description2.getText());
                int count = 0;
                for (int i = 0; i < cardNam.getText().length(); i++) {
                    if (cardNam.getText().charAt(i) != ' ')
                        count++;
                }
                String price2 = String.valueOf(count * 5);
                cardInfo.add(price2);
                UserModel.getUserByUsername(MainMenuController.username).changeUserCoin(-count / 2);
                cardInfo.add(status.getText());
                cardInfo.add("Trap");
                String id;
                if (cardNam == null) {
                    id = "nullName";
                } else {
                    id = cardNam.getText();
                }

                FileWriter writer = new FileWriter(id+".csv");
                String collect = cardInfo.stream().collect(Collectors.joining("\n"));
                writer.write(collect);
                writer.close();

                try {
                    FileWriter writerInfo = new FileWriter(id + ".txt");
                    writerInfo.write(new Gson().toJson(cardInfo));
                    writerInfo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
                new StartClass().start(stage);
            } else {
                label2.setText("This card isn't in list!");
            }
        } else {
            label2.setText("Input is Invalid");
        }
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.equals("");
    }

    public static boolean isNumeric(String string) {
        int number;
        if (string == null || string.equals("")) {
            return false;
        }
        try {
            number = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new StartClass().start(stage);
    }

    public void backToMenu(MouseEvent mouseEvent) throws Exception {
        new ExportCardView().start(stage);
    }

    public boolean isSpellTrap() {
        System.out.println(    TrapCard.getTrapCardByName(description2.getText()) != null);
        return SpellCard.getSpellCardByName(description2.getText()) != null ||
                TrapCard.getTrapCardByName(description2.getText()) != null;

    }

    public boolean isMonsterExit() {
        System.out.println(description.getText() + "  vv");
        return MonsterCard.getMonsterByName(description.getText()) != null;
    }

    public boolean isValidAttribute() {
        return attribute.getText().equals("EARHT") ||
                attribute.getText().equals("WATER") ||
                attribute.getText().equals("DARK") ||
                attribute.getText().equals("FIRE") ||
                attribute.getText().equals("LIGHT") ||
                attribute.getText().equals("WIND");
    }

    public boolean isValidMonsterType() {
        return monsterType.getText().equals("Beast-Warrior") ||
                monsterType.getText().equals("Warrior") ||
                monsterType.getText().equals("Aqua") ||
                monsterType.getText().equals("Fiend") ||
                monsterType.getText().equals("Beast") ||
                monsterType.getText().equals("Pyro") ||
                monsterType.getText().equals("Spellcaster") ||
                monsterType.getText().equals("Thunder") ||
                monsterType.getText().equals("Dragon") ||
                monsterType.getText().equals("Machine") ||
                monsterType.getText().equals("Rock") ||
                monsterType.getText().equals("Insect") ||
                monsterType.getText().equals("Cyberse") ||
                monsterType.getText().equals("Fairy") ||
                monsterType.getText().equals("Sea Serpent") ;
    }

    public boolean isValidCardType() {
        return cardType.getText().equals("Normal") ||
                cardType.getText().equals("Effect") ||
                cardType.getText().equals("Ritual");
    }

    public boolean isValidIcon() {
        return Icon.getText().equals("Normal") ||
                Icon.getText().equals("Counter") ||
                Icon.getText().equals("Continuous") ||
                Icon.getText().equals("Quick-play") ||
                Icon.getText().equals("Field") ||
                Icon.getText().equals("Equip") ||
                Icon.getText().equals("Ritual");
    }
}