package main.java.view;
import main.java.controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.model.*;
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
    public List<ImageView> mainDeckCardImg = new ArrayList<>();
    public List<ImageView> sideDeckCardImg = new ArrayList<>();
    private int cardCounter = 0;
    private String deckName;
    private int numberOfBoughtCard = 0;
    private final UserModel user = UserModel.getUserByUsername(MainMenuController.username);
    private final HashMap<String, Integer> myBoughtCards = new HashMap<>();
    private final HashMap<String, Integer> mainDeckCards = new HashMap<>();
    private final HashMap<String, Integer> sideDeckCards = new HashMap<>();
    private static Stage editDeckStage;
    private String selectedCard = "";


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
                if (cardName.equals("\"Terratiger, the Empowered Warrior\""))
                    cardName = "Terratiger, the Empowered Warrior";
                cardImages.add(ShowCardsView.getCardImageByName(cardName));
                numberOfBoughtCard += eachCard.getValue();
            }
            boughtCardImgView.setImage(cardImages.get(0));
            cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(0))));
        }
        else {
            cardAmountLbl.setText("No Bought Card yet!");
        }
        fillMainDeck();
        fillSideDeck();
    }

    public void fillMainDeck() {
        mainDeckPane.getChildren().clear();
        mainDeckCardImg.clear();
        DeckModel pickedDeck = user.userAllDecks.get(deckName);
        mainDeckCards.putAll(pickedDeck.cardsInMainDeck);
        int counter = 0;
        for (Map.Entry<String, Integer> eachCard : mainDeckCards.entrySet()) {
            for (int j = 0; j < eachCard.getValue(); j++) {
                mainDeckCardImg.add(ShowCardsView.getCardImageViewByName(eachCard.getKey()));
                mainDeckCardImg.get(counter).setY(mainCardY);
                mainDeckCardImg.get(counter).setX(mainCardX);
                mainDeckPane.getChildren().add(mainDeckCardImg.get(counter));
                mainCardX += 80;
                if (mainCardX >= 600) {
                    mainCardY += 80;
                    mainCardX = 0;
                }
                counter++;
            }
        }
        selectMainDeck();
    }

    public void fillSideDeck() {
        sideDeckPane.getChildren().clear();
        sideDeckCardImg.clear();
        DeckModel pickedDeck = user.userAllDecks.get(deckName);
        sideDeckCards.putAll(pickedDeck.cardsInSideDeck);
        int counter = 0;
        for (Map.Entry<String, Integer> eachCard : sideDeckCards.entrySet()) {
            for (int j = 0; j < eachCard.getValue(); j++) {
                sideDeckCardImg.add(ShowCardsView.getCardImageViewByName(eachCard.getKey()));
                sideDeckCardImg.get(counter).setY(sideCardY);
                sideDeckCardImg.get(counter).setX(sideCardX);
                sideDeckPane.getChildren().add(sideDeckCardImg.get(counter));
                sideCardX += 80;
                if (sideCardX >= 600) {
                    sideCardY += 80;
                    sideCardX = 0;
                }
                counter++;
            }
        }
        selectSideDeck();
    }

    public void deleteCard() {
        if (!selectedCard.isEmpty()) {
            String[] split = selectedCard.split(" ");
            if (split[0].equals("main")) {
                mainDeckCardImg.remove(Integer.parseInt(split[1]));
                mainDeckPane.getChildren().remove(Integer.parseInt(split[1]));
            } else {
                sideDeckCardImg.remove(Integer.parseInt(split[1]));
                sideDeckPane.getChildren().remove(Integer.parseInt(split[1]));
            }
            selectedCard = "";
        }
        else {
            messageLbl.setText("No Card Selected!");
        }
        fillSideDeck();
        fillMainDeck();
    }

    public void selectMainDeck() {
        for (int i = 0; i < mainDeckCardImg.size(); i++) {
            int finalI = i;
            mainDeckPane.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = "main " + finalI;
                }
            });
        }
    }

    public void selectSideDeck() {
        for (int i = 0; i < sideDeckCardImg.size(); i++) {
            int finalI = i;
            sideDeckPane.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCard = "side " + finalI;
                }
            });
        }
    }

    public void nextCard() {
        if (cardCounter != cardImages.size() - 1)
            cardCounter++;
        else
            cardCounter = 0;
        boughtCardImgView.setImage(cardImages.get(cardCounter));
        cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(cardCounter))));
    }

    public void previousCard() {
        if (cardCounter != 0)
            cardCounter--;
        else
            cardCounter = cardImages.size() - 1;
        boughtCardImgView.setImage(cardImages.get(cardCounter));
        cardAmountLbl.setText("Amount: " + myBoughtCards.get(ShowCardsView.getNameByImage(cardImages.get(cardCounter))));
    }

    public void addToMainDeck() {
        DeckController.addCardToMainDeck(ShowCardsView.getNameByImage(cardImages.get(cardCounter)), deckName);
        mainDeckCardImg.add(new ImageView(cardImages.get(cardCounter)));
        mainDeckCardImg.get(mainDeckCardImg.size() - 1).setFitWidth(80);
        mainDeckCardImg.get(mainDeckCardImg.size() - 1).setFitHeight(80);
        mainDeckCardImg.get(mainDeckCardImg.size() - 1).setX(mainCardX);
        mainDeckCardImg.get(mainDeckCardImg.size() - 1).setY(mainCardY);
        if (mainCardX >= 500) {
            mainCardX = 40;
            mainCardY += 80;
        }
        else
            mainCardX += 70;
        mainDeckPane.getChildren().add(mainDeckCardImg.get(mainDeckCardImg.size() - 1));
        clickOnDeck();
        selectMainDeck();
    }

    public void addToSideDeck() {
        DeckController.addCardToSideDeck(ShowCardsView.getNameByImage(cardImages.get(cardCounter)), deckName);
        sideDeckCardImg.add(new ImageView(cardImages.get(cardCounter)));
        sideDeckCardImg.get(sideDeckCardImg.size() - 1).setFitWidth(80);
        sideDeckCardImg.get(sideDeckCardImg.size() - 1).setFitHeight(80);
        sideDeckCardImg.get(sideDeckCardImg.size() - 1).setX(sideCardX);
        sideDeckCardImg.get(sideDeckCardImg.size() - 1).setY(sideCardY);
        if (sideCardX >= 500) {
            sideCardX = 40;
            sideCardY += 80;
        }
        else
            sideCardX += 70;
        sideDeckPane.getChildren().add(sideDeckCardImg.get(sideDeckCardImg.size() - 1));
        clickOnDeck();
        selectSideDeck();
    }

    public void clickOnDeck() {
        for (ImageView eachMainCard : mainDeckCardImg) {
            eachMainCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage s = new Stage();
                    s.show();
                }
            });
        }
        for (ImageView eachSideCard : sideDeckCardImg) {
            eachSideCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage s = new Stage();
                    s.show();
                }
            });
        }
    }

    public void pressBackBtn() throws Exception {
        new DeckView().start(editDeckStage);
    }

}
