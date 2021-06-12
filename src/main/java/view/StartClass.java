package main.java.view;
import main.java.controller.SetCards;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.Objects;



public class StartClass extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/startClass.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Import,Export,show Cards");
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        stage.show();
    }

    public void export(MouseEvent mouseEvent) throws Exception {
        new ShowCardsView().start(stage);
        new ExportCardView().start(stage);
    }

    public void importCard(MouseEvent mouseEvent) throws Exception {
        new ImportCardView().start(null);
    }

    public void showAll() throws Exception {
        new ShowCardsView().start(null);
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}