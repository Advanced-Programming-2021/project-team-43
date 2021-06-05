package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StartClass extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startClass.fxml"));
        // Image image = new Image(getClass().getResource("/fxml/3.png").toExternalForm());
        //   Group root = new Group();
        //  ImageView view = new ImageView(image);
        //  root.getChildren().addAll(view);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //    scene.getStylesheets().add(getClass().getResource("/fxml/design.css").toExternalForm());

        stage.setTitle("Import,Export,show Cards");
        stage.show();
    }

    public void export(MouseEvent mouseEvent) throws Exception {
        new ShowCardsView().start(stage);
        new ExportCardView().start(null);
    }

    public void importCard(MouseEvent mouseEvent) throws Exception {
        new ImportCardView().start(null);
    }

    public void showAll() throws Exception {
       new ShowCardsView().start(null);
    }

}
