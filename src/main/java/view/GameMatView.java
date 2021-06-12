package main.java.view;
import main.java.controller.GameMatController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.model.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;


public class GameMatView extends Application {

    private static Stage gameMatStage;
    public AnchorPane mainPane;
    public AnchorPane boardPane;
    public ImageView rivalUserAvatarImg;
    public AnchorPane leftAnchorPane;
    public Label rivalUserNameLbl;
    public Label rivalUserLpLbl;
    public ImageView onlineUserAvatarImg;
    public Label onlineUserNameLbl;
    public Label onlineUserLpLbl;
    public ImageView ownSpellTrap3;
    public ImageView ownMonster5;
    public ImageView ownMonster3;
    public ImageView ownMonster1;
    public ImageView ownSpellTrap5;
    public ImageView ownSpellTrap1;
    public ImageView ownMonster2;
    public ImageView ownSpellTrap4;
    public ImageView ownSpellTrap2;
    public ImageView rivalMonster3;
    public ImageView ownMonster4;
    public ImageView rivalMonster4;
    public ImageView rivalMonster2;
    public ImageView rivalMonster1;
    public ImageView rivalMonster5;
    public ImageView rivalSpellTrap5;
    public ImageView rivalSpellTrap3;
    public ImageView rivalSpellTrap1;
    public ImageView rivalSpellTrap4;
    public ImageView rivalSpellTrap2;
    public ImageView rivalField;
    public ImageView ownGraveYard;
    public ImageView ownField;
    public ImageView rivalGraveYard;
    public ImageView rivalDeck;
    public ImageView ownDeck;
    public HBox onlineHbox;
    public HBox rivalHbox;
    public Label rivalDeckSize;
    public Label onlineDeckSize;
    public ArrayList<ImageView> allOwnMonstersZone = new ArrayList<>();
    public ArrayList<ImageView> allRivalMonstersZone = new ArrayList<>();
    public ArrayList<ImageView> allOwnSpellTrapZone = new ArrayList<>();
    public ArrayList<ImageView> allRivalSpellTrapZone = new ArrayList<>();
    public ImageView selectedCardImage;
    public Label phaseLbl;
    public Label errorLbl;
    public Label ownGraveyardLbl;
    public Label rivalGraveyardLbl;
    public Label popUpMessageLbl;
    public AnchorPane messagePane;
    public TextField answerTxt = new TextField();
    public Button okBtn = new Button();
    public Button cancelBtn = new Button();
    public Label questionLbl = new Label();
    private boolean isVBoxVisible;
    private boolean haveQuestion;
    private int onlineUserRedOpacity = 0;
    private int rivalUserRedOpacity = 0;
    private int rivalMonsterAddress = 0;
    public MenuItem menuItem = new MenuItem();

    public static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    public static void showInput(String input) {
        System.out.println(input);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/gameboard.fxml")));
        gameMatStage = primaryStage;
        gameMatStage.setHeight(790);
        Scene scene = new Scene(root);
        gameMatStage.setTitle("Game Board");
        gameMatStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/logo.jpg")).toExternalForm()));
        gameMatStage.setResizable(false);
        gameMatStage.setScene(scene);
        gameMatStage.show();
    }

    public void initialize() {
        mainPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_ANY);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    messagePane.setVisible(true);
                    okBtn.setVisible(true);
                    answerTxt.setVisible(true);
                    isVBoxVisible = true;
                    haveQuestion = false;
                    clickOnPane();
                    okBtn.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            okBtn("cheat");
                        }
                    });
                }
            }
        });
        Random random = new Random();
        int x = random.nextInt(14) + 1;
        Image boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g" + x + ".jpg")).toExternalForm());
        Background background = new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false)));
        boardPane.setBackground(background);
        allOwnMonstersZone.add(ownMonster1);
        allOwnMonstersZone.add(ownMonster2);
        allOwnMonstersZone.add(ownMonster3);
        allOwnMonstersZone.add(ownMonster4);
        allOwnMonstersZone.add(ownMonster5);
        allRivalMonstersZone.add(rivalMonster1);
        allRivalMonstersZone.add(rivalMonster2);
        allRivalMonstersZone.add(rivalMonster3);
        allRivalMonstersZone.add(rivalMonster4);
        allRivalMonstersZone.add(rivalMonster5);
        allOwnSpellTrapZone.add(ownSpellTrap1);
        allOwnSpellTrapZone.add(ownSpellTrap2);
        allOwnSpellTrapZone.add(ownSpellTrap3);
        allOwnSpellTrapZone.add(ownSpellTrap4);
        allOwnSpellTrapZone.add(ownSpellTrap5);
        allRivalSpellTrapZone.add(rivalSpellTrap1);
        allRivalSpellTrapZone.add(rivalSpellTrap2);
        allRivalSpellTrapZone.add(rivalSpellTrap3);
        allRivalSpellTrapZone.add(rivalSpellTrap4);
        allRivalSpellTrapZone.add(rivalSpellTrap5);
        onlineUserAvatarImg.setImage((new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByNickname(GameMatController.onlineUser).getImageUrl())).toExternalForm())));
        rivalUserAvatarImg.setImage((new Image(Objects.requireNonNull(getClass().getResource(UserModel.getUserByNickname(GameMatController.rivalUser).getImageUrl())).toExternalForm())));
        rivalUserNameLbl.setText("username: " + UserModel.getUserByNickname(GameMatController.rivalUser).getUsername() + "\nnickname: " + GameMatController.rivalUser);
        onlineUserNameLbl.setText("username: " + UserModel.getUserByNickname(GameMatController.onlineUser).getUsername() + "\nnickname: " + GameMatController.onlineUser);
        questionLbl.setVisible(false);
        okBtn.setVisible(false);
        cancelBtn.setVisible(false);
        answerTxt.setVisible(false);
        showGameBoard();
    }

    public void showGameBoard() {
        Player onlinePlayer = Player.getPlayerByName(GameMatController.onlineUser);
        GameMatModel ownGameMat = GameMatModel.getGameMatByNickname(GameMatController.onlineUser);
        Player rivalPlayer = Player.getPlayerByName(GameMatController.rivalUser);
        GameMatModel rivalGameMat = GameMatModel.getGameMatByNickname(GameMatController.rivalUser);
        rivalUserLpLbl.setText("LifePoint: " + Player.getPlayerByName(GameMatController.rivalUser).getLifePoint());
        onlineUserLpLbl.setText("LifePoint: " + Player.getPlayerByName(GameMatController.onlineUser).getLifePoint());
        rivalUserLpLbl.setTextFill(Color.rgb(rivalUserRedOpacity, 255 - rivalUserRedOpacity, 0));
        onlineUserLpLbl.setTextFill(Color.rgb(onlineUserRedOpacity, 255 - onlineUserRedOpacity, 0));
        onlineHbox.getChildren().clear();
        ArrayList<HandCardZone> onlineHand = (ArrayList<HandCardZone>) HandCardZone.allHandCards.get(GameMatController.onlineUser);
        for (int i = 0; i < onlineHand.size(); i++) {
            ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(onlineHand.get(i).getCardName()));
            imageView.setFitHeight(77);
            imageView.setFitWidth(85);
            int finalI = i;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMatController.selectHandCard(finalI);
                    selectedCardImage.setImage(imageView.getImage());

                }
            });
            onlineHbox.getChildren().add(imageView);

        }
        onlineHbox.setAlignment(Pos.CENTER);
        rivalHbox.getChildren().clear();
        ArrayList<HandCardZone> rivalHand = (ArrayList<HandCardZone>) HandCardZone.allHandCards.get(GameMatController.rivalUser);
        for (int i = 0; i < rivalHand.size(); i++) {
            ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm());
            imageView.setFitHeight(77);
            imageView.setFitWidth(85);
            rivalHbox.getChildren().add(imageView);
        }
        rivalHbox.setAlignment(Pos.CENTER);
        rivalDeckSize.setText(String.valueOf(rivalPlayer.getNumberOfMainDeckCards()));
        onlineDeckSize.setText(String.valueOf(onlinePlayer.getNumberOfMainDeckCards()));

        for (int i = 0; i < MonsterZoneCard.allMonsterCards.get(onlinePlayer.getNickname()).size(); i++) {
            MonsterZoneCard monsterZoneCard = MonsterZoneCard.allMonsterCards.get(onlinePlayer.getNickname()).get(i + 1);
            ImageView imageView;
            if (monsterZoneCard.getMode().equals("OO")) {
                imageView = ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);

            } else if (monsterZoneCard.getMode().equals("DH")) {
                imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);

            } else {
                imageView = ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName());
                allOwnMonstersZone.get(i).setRotate(90);
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);
            }
            int finalI = i+1;
            allOwnMonstersZone.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMatController.selectMonsterCard(finalI, true);
                    selectedCardImage.setImage(ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName()).getImage());
                }
            });
            allOwnMonstersZone.get(i).setImage(imageView.getImage());
        }

        for (int i = 0; i < MonsterZoneCard.allMonsterCards.get(rivalPlayer.getNickname()).size(); i++) {
            MonsterZoneCard monsterZoneCard = MonsterZoneCard.allMonsterCards.get(rivalPlayer.getNickname()).get(i + 1);
            ImageView imageView;
            if (monsterZoneCard.getMode().equals("OO")) {
                imageView = ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);
            } else if (monsterZoneCard.getMode().equals("DH")) {
                imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);
            } else {
                imageView = ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName());
                allRivalMonstersZone.get(i).setRotate(90);
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);
            }
            int finalI = i+1;
            allRivalMonstersZone.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMatController.selectMonsterCard(finalI, false);
                    rivalMonsterAddress = finalI;
                    selectedCardImage.setImage(ShowCardsView.getCardImageViewByName(monsterZoneCard.getMonsterName()).getImage());
                    if (monsterZoneCard.getMode().equals("DH")) {
                        selectedCardImage.setImage(new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()).getImage());
                    }
                }
            });
            allRivalMonstersZone.get(i).setImage(imageView.getImage());
        }

        for (int i = 0; i < SpellTrapZoneCard.allSpellTrapCards.get(onlinePlayer.getNickname()).size(); i++) {
            SpellTrapZoneCard spellTrapZoneCard = SpellTrapZoneCard.allSpellTrapCards.get(onlinePlayer.getNickname()).get(i + 1);
            ImageView imageView;
            if (spellTrapZoneCard.getMode().equals("O")) {
                imageView = ShowCardsView.getCardImageViewByName(spellTrapZoneCard.getSpellTrapName());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);

            } else {
                imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);

            }

            int finalI = i+1;
            allOwnSpellTrapZone.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameMatController.selectSpellCard(finalI, true);
                    selectedCardImage.setImage(ShowCardsView.getCardImageViewByName(spellTrapZoneCard.getSpellTrapName()).getImage());
                }
            });
            allOwnSpellTrapZone.get(i).setImage(imageView.getImage());
        }

        for (int i = 0; i < SpellTrapZoneCard.allSpellTrapCards.get(rivalPlayer.getNickname()).size(); i++) {
            SpellTrapZoneCard spellTrapZoneCard = SpellTrapZoneCard.allSpellTrapCards.get(rivalPlayer.getNickname()).get(i + 1);
            ImageView imageView;
            if (spellTrapZoneCard.getMode().equals("O")) {
                imageView = ShowCardsView.getCardImageViewByName(spellTrapZoneCard.getSpellTrapName());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);

            } else {
                imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm());
                imageView.setFitWidth(87);
                imageView.setFitHeight(111);
            }
            int finalI = i+1;
            allRivalSpellTrapZone.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    GameMatController.selectSpellCard(finalI, false);
                    selectedCardImage.setImage(ShowCardsView.getCardImageViewByName(spellTrapZoneCard.getSpellTrapName()).getImage());
                    if (spellTrapZoneCard.getMode().equals("")) {
                        selectedCardImage.setImage(new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()).getImage());
                    }
                }
            });
            allRivalSpellTrapZone.get(i).setImage(imageView.getImage());
        }
        //Field Spell
        Image boardImg;
        if (rivalGameMat.getFieldZone().isEmpty()) {
            rivalField.setImage(null);
            boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g7.jpg")).toExternalForm());
            boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
        }
        else {
            String field = rivalGameMat.getFieldZone();
            String[] split = field.split("/");
            if (split[1].equals("O")) {
                rivalField.setImage(ShowCardsView.getCardImageByName(split[0]));
                switch (split[0]) {
                    case "Yami":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g14.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Forest":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g6.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Closed Forest":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g1.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Umiiruka":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g12.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                }
            }
            else
                rivalField.setImage(new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()).getImage());
        }
        if (ownGameMat.getFieldZone().isEmpty()) {
            ownField.setImage(null);
            boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g7.jpg")).toExternalForm());
            boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
        }
        else {
            String field = ownGameMat.getFieldZone();
            String[] split = field.split("/");
            if (split[1].equals("O")) {
                ownField.setImage(ShowCardsView.getCardImageByName(split[0]));
                switch (split[0]) {
                    case "Yami":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g14.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Forest":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g6.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Closed Forest":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g1.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                    case "Umiiruka":
                        boardImg = new Image(Objects.requireNonNull(getClass().getResource("/images/gameboard/g12.jpg")).toExternalForm());
                        boardPane.setBackground(new Background(new BackgroundImage(boardImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1, 1, true, true, false, false))));
                        break;
                }
            }
            else
                ownField.setImage(new ImageView(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/back2.jpg")).toExternalForm()).getImage());
        }
        ///////////////////////////////////
        ownGameMat.addToGraveyard("Wattaildragon");
        rivalGameMat.addToGraveyard("Wattaildragon");
        //graveyard
        ownGraveyardLbl.setText(String.valueOf(ownGameMat.getNumberOfDeadCards()));
        rivalGraveyardLbl.setText(String.valueOf(rivalGameMat.getNumberOfDeadCards()));
        ownGraveYard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = new Stage();
                GridPane gridPane = new GridPane();
                if (!ownGameMat.getGraveyard().isEmpty()) {
                    for (int i = 0, k = 0, j = 0; i < ownGameMat.getGraveyard().size(); i++, k++) {
                        if (k >= 9) {
                            k = 0;
                            j++;
                        }
                        ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(ownGameMat.getGraveyard().get(i)));
                        imageView.setFitHeight(140);
                        imageView.setFitWidth(140);
                        gridPane.add(imageView, k, j);
                    }
                }
                else {
                    gridPane.add(new Label("Graveyard Empty!"), 0, 0);
                }
                Scene scene = new Scene(gridPane, 600, 600);
                stage.setScene(scene);
                stage.setTitle("Own Graveyard");
                stage.show();
            }
        });
        rivalGraveYard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = new Stage();
                GridPane gridPane = new GridPane();
                if (!rivalGameMat.getGraveyard().isEmpty()) {
                    for (int i = 0, k = 0, j = 0; i < rivalGameMat.getGraveyard().size(); i++, k++) {
                        if (k >= 9) {
                            k = 0;
                            j++;
                        }
                        ImageView imageView = new ImageView(ShowCardsView.getCardImageByName(rivalGameMat.getGraveyard().get(i)));
                        imageView.setFitHeight(140);
                        imageView.setFitWidth(140);
                        gridPane.add(imageView, k, j);
                    }
                }
                else {
                    gridPane.add(new Label("Graveyard Empty!"), 0, 0);
                }
                Scene scene = new Scene(gridPane, 600, 600);
                stage.setScene(scene);
                stage.setTitle("Rival Graveyard");
                stage.show();
            }
        });
        ////////////////////////////////////
    }

    public void nextPhase() throws Exception {
        GameMatController.changePhase(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        phaseLbl.setText(GameMatController.error + "\n" + GameMatController.message);
        GameMatController.error = "";
        selectedCardImage.setImage(null);
        if (phaseLbl.getText().equals("phase: " + Phase.End_Phase + "\nI end my turn!\n" + "phase: " + Phase.Draw_Phase)) {
            start(gameMatStage);
        }
    }

    public void summon(ActionEvent actionEvent) {
        GameMatController.summon(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void clickOnPane() {
        if (isVBoxVisible && !haveQuestion) {
            mainPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    messagePane.setVisible(false);
                    popUpMessageLbl.setText("");
                    showGameBoard();
                }
            });
        }
    }

    public void set(ActionEvent actionEvent) {
        GameMatController.set(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void changeToDefense(ActionEvent actionEvent) {
        GameMatController.changeToDefensePosition(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void changeToAttack(ActionEvent actionEvent) {
        GameMatController.changeToAttackPosition(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void flipSummon(ActionEvent actionEvent) {/////////////////
        GameMatController.flipSummon(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void attack(ActionEvent actionEvent) {
        System.out.println(rivalMonsterAddress);
        GameMatController.attack(rivalMonsterAddress, GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void attackDirect(ActionEvent actionEvent) {
        int result = GameMatController.attackDirect(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        lifePointAnimation(result);
        showGameBoard();
    }

    public void lifePointAnimation(int result) {
        if (result == 1) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), evt -> rivalUserLpLbl.setVisible(false)), new KeyFrame(Duration.seconds(0.1), evt -> rivalUserLpLbl.setVisible(true)));
            timeline.setCycleCount(10);
            timeline.play();
            rivalUserLpLbl.setTextFill(Color.rgb(rivalUserRedOpacity, 255 - rivalUserRedOpacity, 0));
            rivalUserRedOpacity += 50;
            if (rivalUserRedOpacity >= 255)
                rivalUserRedOpacity = 255;
        }
    }

    public void activateEffect(ActionEvent actionEvent) {
        GameMatController.activateSpellEffect(GameMatModel.getGameMatByNickname(GameMatController.onlineUser).getPhase());
        messagePane.setVisible(true);
        popUpMessageLbl.setText(GameMatController.error);
        GameMatController.error = "";
        isVBoxVisible = true;
        haveQuestion = false;
        clickOnPane();
        showGameBoard();
    }

    public void surrender(ActionEvent actionEvent) {

    }

    public void setErrorLbl(Label errorLbl) {
        this.errorLbl = errorLbl;
    }

    public String checkForScanner() {
        messagePane.setVisible(true);
        questionLbl.setText("Which rival dead monster for Scanner? (enter the monster name)");
        final String[] answer = new String[1];
        okBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                answer[0] = answerTxt.getText();
                if (GameMatModel.getGameMatByNickname(GameMatController.rivalUser).doesThisMonsterExistInGraveyard(answer[0])) {
                    messagePane.setVisible(false);
                }
                else {
                    answerTxt.clear();
                }
            }
        });
        return answer[0];
    }

    public void cancelBtn() {
        messagePane.setVisible(false);
    }

    public void okBtn(String whichAction) {
        if (whichAction.equals("cheat")) {
            questionLbl.setVisible(true);
            questionLbl.setText(GameMatController.cheatCommandChecker(answerTxt.getText()));
            System.out.println(questionLbl.getText());
        }
    }

    public void endGameBox() {
        Stage endGameStage = new Stage();

    }


}
