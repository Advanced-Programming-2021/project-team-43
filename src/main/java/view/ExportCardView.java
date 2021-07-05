package view;
import controller.Json;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ExportCardView extends Application {

    private static Stage exportStage;
    public Label label2;
    public Label label;

    @Override
    public void start(Stage stage) throws Exception {
        exportStage = stage;
        stage.setTitle("EXPORT CARD");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/export.fxml")));
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

    public void exportMonster() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/exportMonster.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKRED);
        exportStage.setScene(scene);
        exportStage.show();
    }

    public void exportCard() {
        if (isNumeric(level.getText()) && isNumeric(atk.getText()) && isNumeric(def.getText()) &&
                isNotEmpty(cardName.getText()) && isNotEmpty(cardType.getText()) &&
                isNotEmpty(attribute.getText()) && isNotEmpty(description.getText()) && isNotEmpty(monsterType.getText())
                && isValidCardType() && isValidAttribute() && isValidMonsterType()) {
            if (isMonsterExit()) {
                ArrayList<String> cardInfo = new ArrayList<>();
                cardInfo.add(Objects.requireNonNull(getClass().getResource("/images/newCards/0.jpg")).toExternalForm());
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
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
            } else {
                label.setText("This card isn't in list!");
            }
        } else {
            label.setText("Input is Invalid");
        }
    }

    public void exportSpellTrap() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/exportSpellTrap.fxml")));
        Scene scene = new Scene(root);
        scene.setFill(Color.DARKRED);
        exportStage.setScene(scene);
        exportStage.show();
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

    public void exportCard2() {
        if (isNotEmpty(cardNam.getText()) && isNotEmpty(type.getText()) &&
                isNotEmpty(Icon.getText()) && isNotEmpty(description2.getText()) && isNotEmpty(status.getText())
                && isValidIcon()) {
            if (isSpellTrap()) {
                ArrayList<String> cardInfo = new ArrayList<>();
                cardInfo.add(Objects.requireNonNull(getClass().getResource("/images/newCards/3.jpg")).toExternalForm());
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
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
               // new StartClass().start(stage);
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
                cardInfo.add(Objects.requireNonNull(getClass().getResource("/images/newCards/3.jpg")).toExternalForm());
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
                ArrayList<String> cards = Json.exportCad();
                cards.add(id);
                Json.importCard(cards);
               // new StartClass().start(stage);
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

    public void back() throws Exception {
        new StartClass().start(exportStage);
    }

    public void backToMenu() throws Exception {
        new ExportCardView().start(exportStage);
    }

    public boolean isSpellTrap() {
        System.out.println(description2.getText() + "  nn");
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

