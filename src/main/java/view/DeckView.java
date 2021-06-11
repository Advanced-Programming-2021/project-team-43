package main.java.view;
import main.java.controller.*;
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
import main.java.model.*;
import java.util.*;


public class DeckView extends Application {

    public ImageView previousBtn;
    public ImageView nextBtn;
    public AnchorPane deckPane;
    public TextField deckNameTxt;
    public Label messageLbl;
    public Button newDeckBtn;
    public CheckBox isActiveCheckBox;
    public AnchorPane mainDeckPane;
    public AnchorPane sideDeckPane;
    public ImageView deckImgView;
    public Label deckInfoLbl;
    private final UserModel user = UserModel.getUserByUsername(MainMenuController.username);
    private static int deckCounter = 0;
    private static int deckSize = 0;
    private static int activeDeck = -1;
    private static String whichDeckName;
    public static Stage deckStage;
    private final List<String> allDeckInformation = new ArrayList<>();
    private final HashMap<String, DeckModel> allDecks = new HashMap<>();
    private final HashMap<String, Integer> mainDeck = new HashMap<>();
    private final HashMap<String, Integer> sideDeck = new HashMap<>();
    private final List<ImageView> mainCardsImg = new ArrayList<>();
    private final List<ImageView> sideCardsImg = new ArrayList<>();



    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/deckPage.fxml")));
        deckStage = primaryStage;
        deckStage.setScene(new Scene(root));
        deckStage.show();
    }

    public void initialize() {
        isActiveCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isActiveCheckBox.isSelected()) {
                    activeDeck = deckCounter;
                    user.setActiveDeck(getDeckNameByNumber(deckCounter));
                }
                else {
                    activeDeck = -1;
                    user.setActiveDeck("");
                }
            }
        });
        deckSize = user.userAllDecks.size();
        if (deckSize != 0) {
            allDecks.putAll(user.userAllDecks);
            Object[] values = allDecks.keySet().toArray();
            for (int i = 0; i < values.length; i++) {
                allDeckInformation.add(values[i].toString() + "\nMain Deck Size: " + allDecks.get(values[i].toString()).getMainAllCardNumber() + "\nSide Deck Size: " + allDecks.get(values[i].toString()).getSideAllCardNumber() + "\n" + allDecks.get(values[i].toString()).validOrInvalid());
                if (user.getActiveDeck().equals(values[i].toString())) {
                    activeDeck = i;
                    isActiveCheckBox.setSelected(true);
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/activeBack.jpg")).toExternalForm()));
                }
                else
                    deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
            }
            deckInfoLbl.setText(allDeckInformation.get(0));
        }
        else {
            deckImgView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()));
            deckInfoLbl.setText("No Deck!");
        }


        deckInfoLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        deckInfoLbl.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fillMainDeck(getDeckNameByNumber(deckCounter));
                fillSideDeck(getDeckNameByNumber(deckCounter));
                mainDeckPane.setVisible(true);
                sideDeckPane.setVisible(true);
            }
        });
        deckInfoLbl.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainDeckPane.setVisible(false);
                sideDeckPane.setVisible(false);
                mainDeckPane.getChildren().clear();
                sideDeckPane.getChildren().clear();
            }
        });
    }

    public void fillMainDeck(String deckName) {
        int x = 0;
        int y = 0;
        int counter = 0;
        String cardName = "";
        if (deckName != null) {
            DeckModel pickedDeck = user.userAllDecks.get(deckName);
            mainDeck.putAll(pickedDeck.cardsInMainDeck);
            for (Map.Entry<String, Integer> eachCard : mainDeck.entrySet()) {
                for (int j = 0; j < eachCard.getValue(); j++) {
                    if (eachCard.getKey().equals("\"Terratiger, the Empowered Warrior\""))
                        cardName = "Terratiger, the Empowered Warrior";
                    else
                        cardName = eachCard.getKey();
                    mainCardsImg.add(ShowCardsView.getCardImageViewByName(cardName));
                    mainCardsImg.get(counter).setX(x);
                    mainCardsImg.get(counter).setY(y);
                    mainDeckPane.getChildren().add(mainCardsImg.get(counter));
                    x += 80;
                    if (x >= 500) {
                        y += 80;
                        x = 0;
                    }
                    counter++;
                }
            }
        }
        mainDeck.clear();
    }

    public void fillSideDeck(String deckName) {
        int x = 0;
        int y = 0;
        int counter = 0;
        String cardName = "";
        if (deckName != null) {
            DeckModel pickedDeck = user.userAllDecks.get(deckName);
            sideDeck.putAll(pickedDeck.cardsInSideDeck);
            for (Map.Entry<String, Integer> eachCard : sideDeck.entrySet()) {
                for (int j = 0; j < eachCard.getValue(); j++) {
                    System.out.println(eachCard.getKey());///////////
                    if (eachCard.getKey().equals("\"Terratiger, the Empowered Warrior\""))
                        cardName = "Terratiger, the Empowered Warrior";
                    else
                        cardName = eachCard.getKey();
                    sideCardsImg.add(ShowCardsView.getCardImageViewByName(cardName));
                    sideCardsImg.get(counter).setX(x);
                    sideCardsImg.get(counter).setY(y);
                    sideDeckPane.getChildren().add(sideCardsImg.get(counter));
                    x += 80;
                    if (x >= 500) {
                        y += 80;
                        x = 0;
                    }
                    counter++;
                }
            }
        }
        sideDeck.clear();
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
    }

    public void createNewDeck() throws Exception {
        deckNameTxt.setVisible(true);
        newDeckBtn.setText("Create Deck");
        if (!deckNameTxt.getText().trim().isEmpty()) {
            if (allDeckInformation.size() == 0)
                deckInfoLbl.setText("");
            messageLbl.setText(DeckController.createDeck(deckNameTxt.getText().trim()));
            if (messageLbl.getText().equals("Deck created successfully")) {
                deckSize++;
                newDeckBtn.setText("New Deck");
                deckNameTxt.setVisible(false);
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
