package view;
import controller.DeckController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.DeckModel;
import model.UserModel;
import java.util.*;



public class DeckView extends Application {


    public ImageView previousBtn;
    public ImageView nextBtn;
    public AnchorPane deckPane;
    public TextField deckNameTxt;
    public Label messageLbl;
    public Button newDeckBtn;
    public CheckBox isActiveCheckBox;
    public AnchorPane mainDeckPane = new AnchorPane();
    public AnchorPane sideDeckPane = new AnchorPane();
    public ImageView deckImgView;
    public Label deckInfoLbl;
    private final UserModel user = UserModel.getUserByUsername(MainMenuController.username);
    private static int deckCounter = 0;
    private static int deckSize = 0;
    private static int activeDeck = -1;
    private static String whichDeckName;
    public static Stage deckStage;
    private final List<String> allDeckInformation = new ArrayList<>();
    private final Map<String, DeckModel> allDecks = new HashMap<>();
    private final List<AnchorPane> allMainCardsPane = new ArrayList<>();
    private final List<AnchorPane> allSideCardsPane = new ArrayList<>();



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/deckPage.fxml")));
        deckStage = primaryStage;
        deckStage.setScene(new Scene(root));
        deckStage.show();
    }

    public void initialize() {
        deckImgView.setFitWidth(231);
        deckImgView.setFitHeight(292);
        isActiveCheckBox.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isActiveCheckBox.isSelected()) {
                    activeDeck = deckCounter;
                    user.setActiveDeck(getDeckNameByNumber(deckCounter));
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
                }
                else {
                    activeDeck = -1;
                    user.setActiveDeck("");
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
                }
            }
        });
        deckSize = user.userAllDecks.size();
        if (deckSize != 0) {
            allDecks.putAll(user.userAllDecks);
            int i = 0;
            for (Map.Entry<String, DeckModel> eachDeck : allDecks.entrySet()) {
                allDeckInformation.add(eachDeck.getKey() + "\nMain Deck Size: " + allDecks.get(eachDeck.getKey()).getMainAllCardNumber() + "\nSide Deck Size: " + allDecks.get(eachDeck.getKey()).getSideAllCardNumber() + "\n" + allDecks.get(eachDeck.getKey()).validOrInvalid());
                if (user.getActiveDeck().equals(eachDeck.getKey())) {
                    activeDeck = i;
                    isActiveCheckBox.setSelected(true);
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
                }
                else {
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
                }
                i++;
            }
            deckInfoLbl.setText(allDeckInformation.get(0));
        }
        else {
            deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
            deckInfoLbl.setText("No Deck!");
        }
        fillMainCardPane(getDeckNameByNumber(deckCounter));
        fillSideCardDeck(getDeckNameByNumber(deckCounter));
        deckInfoLbl.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                whichDeckName = getDeckNameByNumber(deckCounter);
                try {
                    new EditDeckView().start(deckStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        deckInfoLbl.setOnMouseEntered(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                allMainCardsPane.get(deckCounter).setVisible(true);
                allMainCardsPane.get(deckCounter).setLayoutX(340);
                allMainCardsPane.get(deckCounter).setLayoutY(7);
                deckPane.getChildren().add(allMainCardsPane.get(deckCounter));
                allSideCardsPane.get(deckCounter).setVisible(true);
                allSideCardsPane.get(deckCounter).setLayoutX(342);
                allSideCardsPane.get(deckCounter).setLayoutY(444);
                deckPane.getChildren().add(allSideCardsPane.get(deckCounter));
            }
        });
        deckInfoLbl.setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                allMainCardsPane.get(deckCounter).setVisible(false);
                allSideCardsPane.get(deckCounter).setVisible(false);
                deckPane.getChildren().remove(allMainCardsPane.get(deckCounter));
                deckPane.getChildren().remove(allSideCardsPane.get(deckCounter));
            }
        });
        if (user.getActiveDeck().equals(getDeckNameByNumber(deckCounter))) {
            deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
        }
    }

    public void fillSideCardDeck(String deckName) {
        allSideCardsPane.add(new AnchorPane());
        allSideCardsPane.get(allSideCardsPane.size() - 1).setVisible(false);
        if (user.userAllDecks.get(deckName) != null) {
            HashMap<String, Integer> sideCards = new HashMap<>(user.userAllDecks.get(deckName).cardsInSideDeck);
            int x = 0;
            int y = 0;
            int counter = 0;
            for (Map.Entry<String, Integer> entry : sideCards.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(entry.getKey()));
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(80);
                    allSideCardsPane.get(allSideCardsPane.size() - 1).getChildren().add(imageView);
                    allSideCardsPane.get(allSideCardsPane.size() - 1).getChildren().get(counter).setLayoutY(y);
                    allSideCardsPane.get(allSideCardsPane.size() - 1).getChildren().get(counter).setLayoutX(x);
                    x += 55;
                    if (x >= 500) {
                        y += 80;
                        x = 0;
                    }
                    counter++;
                }
            }
        }
    }

    public void fillMainCardPane(String deckName) {
        allMainCardsPane.add(new AnchorPane());
        allMainCardsPane.get(allMainCardsPane.size() - 1).setVisible(false);
        if (user.userAllDecks.get(deckName) != null) {
            int x = 0;
            int y = 0;
            int counter = 0;
            HashMap<String, Integer> mainCards = new HashMap<>(user.userAllDecks.get(deckName).cardsInMainDeck);
            for (Map.Entry<String, Integer> entry : mainCards.entrySet()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(entry.getKey()));
                    imageView.setFitWidth(70);
                    imageView.setFitHeight(80);
                    allMainCardsPane.get(allMainCardsPane.size() - 1).getChildren().add(imageView);
                    allMainCardsPane.get(allMainCardsPane.size() - 1).getChildren().get(counter).setLayoutY(y);
                    allMainCardsPane.get(allMainCardsPane.size() - 1).getChildren().get(counter).setLayoutX(x);
                    x += 55;
                    if (x >= 500) {
                        y += 80;
                        x = 0;
                    }
                    counter++;
                }
            }
        }
    }

    public void nextDeck() {
        messageLbl.setText("");
        if (deckSize != 0) {
            if (deckCounter != deckSize - 1)
                deckCounter++;
            else
                deckCounter = 0;
            if (deckCounter == activeDeck) {
                deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
                isActiveCheckBox.setSelected(true);
            } else {
                deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
                isActiveCheckBox.setSelected(false);
            }
            deckInfoLbl.setText(allDeckInformation.get(deckCounter));
        }
        fillMainCardPane(getDeckNameByNumber(deckCounter));
        fillSideCardDeck(getDeckNameByNumber(deckCounter));
    }

    public void previousDeck() {
        messageLbl.setText("");
        if (deckSize != 0) {
            if (deckCounter != 0)
                deckCounter--;
            else
                deckCounter = deckSize - 1;
            if (deckCounter == activeDeck) {
                deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
                isActiveCheckBox.setSelected(true);
            } else {
                deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
                isActiveCheckBox.setSelected(false);
            }
            deckInfoLbl.setText(allDeckInformation.get(deckCounter));
        }
        fillMainCardPane(getDeckNameByNumber(deckCounter));
        fillSideCardDeck(getDeckNameByNumber(deckCounter));
    }

    public void createNewDeck() {
        deckNameTxt.setVisible(true);
        newDeckBtn.setText("Create Deck");
        if (!deckNameTxt.getText().trim().isEmpty()) {
            if (allDeckInformation.size() == 0)
                deckInfoLbl.setText("");
            messageLbl.setText(DeckController.createDeck(deckNameTxt.getText().trim()));
            if (messageLbl.getText().equals("Deck created successfully")) {
                deckSize++;
                newDeckBtn.setText("New Deck");
                allDeckInformation.add(deckNameTxt.getText() + "\nMain Deck Size: 0" + "\nSide Deck Size: 0" + "\ninvalid");
            }
            deckNameTxt.clear();
        }
    }

    public void pressDeleteDeckBtn() {
        allDeckInformation.remove(deckCounter);
        deckInfoLbl.setText("");
        if (allDeckInformation.size() == 0)
            deckInfoLbl.setText("No Deck!");
        DeckController.deleteDeck(getDeckNameByNumber(deckCounter));
        messageLbl.setText("Deck deleted successfully!");
        deckSize--;
        if (deckCounter != 0)
            deckCounter--;
    }

    public String getDeckNameByNumber(int number) {
        HashMap<String, DeckModel> allDecks = user.userAllDecks;
        deckSize = allDecks.size();
        Object[] values = allDecks.keySet().toArray();
        for (int i = 0; i < values.length; i++) {
            if (i == number)
                return values[i].toString();
        }
        return null;
    }

    public void pressBackBtn() throws Exception {
        new MainMenuView().start(deckStage);
    }

    public static String getWhichDeckName() {
        return whichDeckName;
    }

}

