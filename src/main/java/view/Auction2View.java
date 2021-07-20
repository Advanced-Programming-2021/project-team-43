package view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.util.Objects;


public class Auction2View extends Application {

    public ImageView cardImg;
    public Label cardNameLbl;
    public Label cardPriceLbl;
    public Label bestPriceLbl;
    private static Stage auctionStage;
    public TextField yourPriceTxt;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/auction2.fxml")));
        auctionStage = primaryStage;
        auctionStage.setScene(new Scene(root));
        auctionStage.show();
    }

    public void initialize() {
        cardImg.setImage(ShowCardsView.getCardImageByName(AuctionView.cardNameClicked));
        cardNameLbl.setText(AuctionView.cardNameClicked);
        /////////cardPriceLbl.setText("gheymat card ro bezar inja esmesh card az AuctionView.cardNameClicked in be dast miad");
        //bestPriceLbl.setText("balatarin gheymat ro bezar");
    }

    public void offer() {
           //in tabe vaghti gheymat mored nazr ro type mikone vaghti dokme ziresh ro mizane in sedazade meshe
        yourPriceTxt.getText();///gheymati ke type carde
        //caraii ke bayad anjam bedi
    }

    public void back() throws Exception {
        new AuctionView().start(auctionStage);
    }

}
