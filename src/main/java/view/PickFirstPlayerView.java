package main.java.view;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;


public class PickFirstPlayerView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Random random = new Random();
        int chance = random.nextInt(50);
        if (chance % 2 == 0) {
            try {
                new CoinChanceView().start(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new RockPaperView().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}





