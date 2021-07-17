package view;
import controller.DeckController;
import controller.GameMatController;
import controller.MainMenuController;
import controller.RegisterAndLoginController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import java.util.*;



public class EditDeckView extends Application {

    public ImageView boughtCardImgView;
    public ImageView nextCardBtn;
    public ImageView previousCardBtn;
    public AnchorPane editDeckPane;
    public Label cardAmountLbl;
    public Label deckNameLbl;
    public AnchorPane mainDeckPane;
    public AnchorPane sideDeckPane;
    public Label messageLbl;
    private int mainCardX = 0;
    private int mainCardY = 0;
    private int sideCardX = 0;
    private int sideCardY = 0;
    public List<Image> cardImages = new ArrayList<>();
    private int cardCounter = 0;
    private String deckName;
    private int numberOfBoughtCard = 0;
    private final UserModel user = UserModel.getUserByUsername(MainMenuController.username);
    private final HashMap<String, Integer> myBoughtCards = new HashMap<>();
    private static Stage editDeckStage;
    private String selectedCard = "";
    private int sideCardCounter = 0;
    private int mainCardCounter = 0;
    private ImageView selectedCardImage = new ImageView();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/editDeckPage.fxml")));
        editDeckStage = primaryStage;
        editDeckStage.setScene(new Scene(root));
        editDeckStage.show();
    }

    public void initialize() {
        deckName = DeckView.getWhichDeckName();
        deckNameLbl.setText(deckName);
        String cardName;
        myBoughtCards.putAll(user.userAllCards);
        if (myBoughtCards.size() != 0) {
            for (Map.Entry<String, Integer> eachCard : myBoughtCards.entrySet()) {
                cardName = eachCard.getKey();
                cardImages.add(ShowCardsView.getCardImageByName(cardName));
                numberOfBoughtCard += eachCard.getValue();
            }
            boughtCardImgView.setImage(cardImages.get(0));
            cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(0))));
        }
        else
            cardAmountLbl.setText("No Bought Card yet!");
        fillMainCardDeck();
        fillSideCardDeck();
        implementDragAndDropToMain(boughtCardImgView);
        implementDragAndDropToSide(boughtCardImgView);
    }

    public void fillMainCardDeck() {
        RegisterAndLoginController.updateUser(MainMenuController.token);
        HashMap<String, Integer> mainDeckCards = new HashMap<>(user.userAllDecks.get(deckName).cardsInMainDeck);
        for (Map.Entry<String, Integer> entry : mainDeckCards.entrySet()) {
            for (int j = 0; j < entry.getValue(); j++) {
                ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(entry.getKey()));
                imageView.setFitWidth(70);
                imageView.setFitHeight(80);
                mainDeckPane.getChildren().add(imageView);
                mainDeckPane.getChildren().get(mainCardCounter).setLayoutY(mainCardY);
                mainDeckPane.getChildren().get(mainCardCounter).setLayoutX(mainCardX);
                mainCardX += 50;
                if (mainCardX >= 600) {
                    mainCardY += 80;
                    mainCardX = 0;
                }
                mainCardCounter++;
            }
        }
        clickOnCards();
    }

    public void fillSideCardDeck() {
        HashMap<String, Integer> sideDeckCards = new HashMap<>(user.userAllDecks.get(deckName).cardsInSideDeck);
        for (Map.Entry<String, Integer> entry : sideDeckCards.entrySet()) {
            for (int j = 0; j < entry.getValue(); j++) {
                ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(entry.getKey()));
                imageView.setFitWidth(70);
                imageView.setFitHeight(80);
                sideDeckPane.getChildren().add(imageView);
                sideDeckPane.getChildren().get(sideCardCounter).setLayoutY(sideCardY);
                sideDeckPane.getChildren().get(sideCardCounter).setLayoutX(sideCardX);
                sideCardX += 50;
                if (sideCardX >= 600) {
                    sideCardY += 80;
                    sideCardX = 0;
                }
                sideCardCounter++;
            }
        }
        clickOnCards();
    }

    public void deleteCard() {
        if (selectedCard.startsWith("main")) {
            DeckController.removeCardFromMainDeck(ShowCardsView.getNameByImage(selectedCardImage.getImage()), deckName);
            mainDeckPane.getChildren().remove(selectedCardImage);
        } else {
            DeckController.removeCardFromSideDeck(ShowCardsView.getNameByImage(selectedCardImage.getImage()), deckName);
            sideDeckPane.getChildren().remove(selectedCardImage);
        }
        selectedCard = "";
        selectedCardImage = null;
    }

    public void nextCard() {
        if (cardCounter != cardImages.size() - 1)
            cardCounter++;
        else
            cardCounter = 0;
        boughtCardImgView.setImage(cardImages.get(cardCounter));
        cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(cardCounter))));
        implementDragAndDropToMain(boughtCardImgView);
        implementDragAndDropToSide(boughtCardImgView);
    }

    public void previousCard() {
        if (cardCounter != 0)
            cardCounter--;
        else
            cardCounter = cardImages.size() - 1;
        boughtCardImgView.setImage(cardImages.get(cardCounter));
        cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(cardCounter))));
        implementDragAndDropToMain(boughtCardImgView);
        implementDragAndDropToSide(boughtCardImgView);
    }

    public void addToMainDeck() {
        String s= DeckController.addCardToMainDeck(ShowCardsView.getNameByImage(cardImages.get(cardCounter)), deckName);
        if(MainMenuController.isSuccessful(s)){
            ImageView imageView = new ImageView(cardImages.get(cardCounter));
            imageView.setFitWidth(70);
            imageView.setFitHeight(80);
            mainDeckPane.getChildren().add(imageView);
            mainDeckPane.getChildren().get(mainCardCounter).setLayoutY(mainCardY);
            mainDeckPane.getChildren().get(mainCardCounter).setLayoutX(mainCardX);
            mainCardCounter++;
            mainCardX += 50;
            if (mainCardX >= 600) {
                mainCardY += 80;
                mainCardX = 0;
            }}
    }

    public void addToSideDeck() {
        String s =DeckController.addCardToSideDeck(ShowCardsView.getNameByImage(cardImages.get(cardCounter)), deckName);
        if(MainMenuController.isSuccessful(s)){
            ImageView imageView = new ImageView(cardImages.get(cardCounter));
            imageView.setFitWidth(70);
            imageView.setFitHeight(80);
            sideDeckPane.getChildren().add(imageView);
            sideDeckPane.getChildren().get(sideCardCounter).setLayoutY(sideCardY);
            sideDeckPane.getChildren().get(sideCardCounter).setLayoutX(sideCardX);
            sideCardX += 50;
            if (sideCardX >= 600) {
                sideCardY += 80;
                sideCardX = 0;
            }
            sideCardCounter++;
        }}

    public void clickOnCards() {
        for (Node child : mainDeckPane.getChildren()) {
            child.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ImageView imageView = (ImageView) child;
                    selectedCard = "main " + ShowCardsView.getNameByImage(imageView.getImage());
                    selectedCardImage = imageView;
                    deleteCard();
                }
            });
        }
        for (Node child : sideDeckPane.getChildren()) {
            child.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ImageView imageView = (ImageView) child;
                    selectedCard = "side " + ShowCardsView.getNameByImage(imageView.getImage());
                    selectedCardImage = imageView;
                    deleteCard();
                }
            });
        }

    }

    public void implementDragAndDropToMain(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
        });
        mainDeckPane.setOnDragOver(Event::consume);
        imageView.setOnDragEntered(Event::consume);
        mainDeckPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                addToMainDeck();
                dragEvent.consume();
            }
        });
        mainDeckPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = db.hasString();
            event.setDropCompleted(success);
            event.consume();
        });
        imageView.setOnDragDone(Event::consume);
    }

    public void implementDragAndDropToSide(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
        });
        sideDeckPane.setOnDragOver(Event::consume);
        imageView.setOnDragEntered(Event::consume);
        sideDeckPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                addToSideDeck();
                dragEvent.consume();
            }
        });
        sideDeckPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = db.hasString();
            addToSideDeck();
            event.setDropCompleted(success);
            event.consume();
        });
        imageView.setOnDragDone(Event::consume);
    }

    public void pressBackBtn() throws Exception {
        new DeckView().start(editDeckStage);
    }

}
