package main.java.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.*;


public class ShowCardsView extends Application {


    private static final Map<String, ImageView> allCards = new HashMap<>();
    private static final Map<String, Image> allCardImage = new HashMap<>();

    @Override
    public void start(Stage stg) throws Exception {
        showMonster();
        showSpellTrap();
    }

    public void setAllCards() {
        allCards.put("Advanced Ritual Art", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Advanced Ritual Art.jpg")).toExternalForm())));
        allCards.put("Alexandrite Dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Alexandrite Dragon.jpg")).toExternalForm())));
        allCards.put("Axe Raider", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Axe Raider.jpg")).toExternalForm())));
        allCards.put("Baby dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Baby dragon.jpg")).toExternalForm())));
        allCards.put("Battle OX", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Battle OX.jpg")).toExternalForm())));
        allCards.put("Battle warrior", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Battle warrior.jpg")).toExternalForm())));
        allCards.put("Beast King Barbaros", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Beast King Barbaros.jpg")).toExternalForm())));
        allCards.put("Bitron", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Bitron.jpg")).toExternalForm())));
        allCards.put("BlackPendant", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/BlackPendant.jpg")).toExternalForm())));
        allCards.put("Blue-Eyes white dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Blue-Eyes white dragon.jpg")).toExternalForm())));
        allCards.put("Call of The Haunted", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Call of The Haunted.jpg")).toExternalForm())));
        allCards.put("Change of Heart", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Change of Heart.jpg")).toExternalForm())));
        allCards.put("Closed Forest", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Closed Forest.jpg")).toExternalForm())));
        allCards.put("Command knight", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Command knight.jpg")).toExternalForm())));
        allCards.put("Crab Turtle", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Crab Turtle.jpg")).toExternalForm())));
        allCards.put("Crawling dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Crawling dragon.jpg")).toExternalForm())));
        allCards.put("Curtain of the dark ones", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Curtain of the dark ones.jpg")).toExternalForm())));
        allCards.put("Dark Blade", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Dark Blade.jpg")).toExternalForm())));
        allCards.put("Dark magician", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Dark magician.jpg")).toExternalForm())));
        allCards.put("DarkHole", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/DarkHole.jpg")).toExternalForm())));
        allCards.put("Exploder Dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Exploder Dragon.jpg")).toExternalForm())));
        allCards.put("Feral Imp", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Feral Imp.jpg")).toExternalForm())));
        allCards.put("Fireyarou", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Fireyarou.jpg")).toExternalForm())));
        allCards.put("Flame manipulator", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Flame manipulator.jpg")).toExternalForm())));
        allCards.put("Forest", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Forest.jpg")).toExternalForm())));
        allCards.put("Gate Guardian", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Gate Guardian.jpg")).toExternalForm())));
        allCards.put("Haniwa", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Haniwa.jpg")).toExternalForm())));
        allCards.put("Harpie's Feather Duster", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Harpie's Feather Duster.jpg")).toExternalForm())));
        allCards.put("Herald of Creation", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Herald of Creation.jpg")).toExternalForm())));
        allCards.put("Hero of the east", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Hero of the east.jpg")).toExternalForm())));
        allCards.put("Leotron", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Leotron.jpg")).toExternalForm())));
        allCards.put("Magic Cylinder", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magic Cylinder.jpg")).toExternalForm())));
        allCards.put("Magic Jammer", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magic Jammer.jpg")).toExternalForm())));
        allCards.put("Magnum Shield", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magnum Shield.jpg")).toExternalForm())));
        allCards.put("Man-Eater Bug", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Man-Eater Bug.jpg")).toExternalForm())));
        allCards.put("Marshmallon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Marshmallon.jpg")).toExternalForm())));
        allCards.put("Messenger of peace", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Messenger of peace.jpg")).toExternalForm())));
        allCards.put("Mind Crush", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mind Crush.jpg")).toExternalForm())));
        allCards.put("Mirage Dragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mirage Dragon.jpg")).toExternalForm())));
        allCards.put("Mirror Force", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mirror Force.jpg")).toExternalForm())));
        allCards.put("Monster Reborn", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Monster Reborn.jpg")).toExternalForm())));
        allCards.put("Mystical space typhoon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mystical space typhoon.jpg")).toExternalForm())));
        allCards.put("Negate Attack", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Negate Attack.jpg")).toExternalForm())));
        allCards.put("Pot of Greed", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Pot of Greed.jpg")).toExternalForm())));
        allCards.put("Raigeki", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Raigeki.jpg")).toExternalForm())));
        allCards.put("Ring of defense", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Ring of defense.jpg")).toExternalForm())));
        allCards.put("Scanner", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Scanner.jpg")).toExternalForm())));
        allCards.put("Silver Fang", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Silver Fang.jpg")).toExternalForm())));
        allCards.put("Skull Guardian", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Skull Guardian.jpg")).toExternalForm())));
        allCards.put("Slot Machine", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Slot Machine.jpg")).toExternalForm())));
        allCards.put("Solemn Warning", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Solemn Warning.jpg")).toExternalForm())));
        allCards.put("Spell Absorption", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Spell Absorption.jpg")).toExternalForm())));
        allCards.put("Spiral Serpent", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Spiral Serpent.jpg")).toExternalForm())));
        allCards.put("Suijin", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Suijin.jpg")).toExternalForm())));
        allCards.put("Supply Squad", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Supply Squad.jpg")).toExternalForm())));
        allCards.put("Sword of dark destruction", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Sword of dark destruction.jpg")).toExternalForm())));
        allCards.put("Swords of Revealing Light", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Swords of Revealing Light.jpg")).toExternalForm())));
        allCards.put("Terraforming", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Terraforming.jpg")).toExternalForm())));
        allCards.put("Terratiger, the Empowered Warrior", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Terratiger, the Empowered Warrior.jpg")).toExternalForm())));
        allCards.put("Texchanger", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Texchanger.jpg")).toExternalForm())));
        allCards.put("The Calculator", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/The Calculator.jpg")).toExternalForm())));
        allCards.put("The Tricky", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/The Tricky.jpg")).toExternalForm())));
        allCards.put("Time Seal", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Time Seal.jpg")).toExternalForm())));
        allCards.put("Torrential Tribute", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Torrential Tribute.jpg")).toExternalForm())));
        allCards.put("Trap Hole", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Trap Hole.jpg")).toExternalForm())));
        allCards.put("Twin Twisters", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Twin Twisters.jpg")).toExternalForm())));
        allCards.put("Umiiruka", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Umiiruka.jpg")).toExternalForm())));
        allCards.put("United We Stand", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/United We Stand.jpg")).toExternalForm())));
        allCards.put("Warrior Dai Grepher", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Warrior Dai Grepher.jpg")).toExternalForm())));
        allCards.put("Wattaildragon", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Wattaildragon.jpg")).toExternalForm())));
        allCards.put("Wattkid", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Wattkid.jpg")).toExternalForm())));
        allCards.put("Yami", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Yami.jpg")).toExternalForm())));
        allCards.put("Yomi Ship", new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Yomi Ship.jpg")).toExternalForm())));
        allCardImage.put("Advanced Ritual Art", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Advanced Ritual Art.jpg")).toExternalForm()));
        allCardImage.put("Alexandrite Dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Alexandrite Dragon.jpg")).toExternalForm()));
        allCardImage.put("Axe Raider", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Axe Raider.jpg")).toExternalForm()));
        allCardImage.put("Baby dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Baby dragon.jpg")).toExternalForm()));
        allCardImage.put("Battle OX", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Battle OX.jpg")).toExternalForm()));
        allCardImage.put("Battle warrior", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Battle warrior.jpg")).toExternalForm()));
        allCardImage.put("Beast King Barbaros", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Beast King Barbaros.jpg")).toExternalForm()));
        allCardImage.put("Bitron", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Bitron.jpg")).toExternalForm()));
        allCardImage.put("BlackPendant", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/BlackPendant.jpg")).toExternalForm()));
        allCardImage.put("Blue-Eyes white dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Blue-Eyes white dragon.jpg")).toExternalForm()));
        allCardImage.put("Call of The Haunted", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Call of The Haunted.jpg")).toExternalForm()));
        allCardImage.put("Change of Heart", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Change of Heart.jpg")).toExternalForm()));
        allCardImage.put("Closed Forest", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Closed Forest.jpg")).toExternalForm()));
        allCardImage.put("Command knight", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Command knight.jpg")).toExternalForm()));
        allCardImage.put("Crab Turtle", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Crab Turtle.jpg")).toExternalForm()));
        allCardImage.put("Crawling dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Crawling dragon.jpg")).toExternalForm()));
        allCardImage.put("Curtain of the dark ones", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Curtain of the dark ones.jpg")).toExternalForm()));
        allCardImage.put("Dark Blade", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Dark Blade.jpg")).toExternalForm()));
        allCardImage.put("Dark magician", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Dark magician.jpg")).toExternalForm()));
        allCardImage.put("DarkHole", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/DarkHole.jpg")).toExternalForm()));
        allCardImage.put("Exploder Dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Exploder Dragon.jpg")).toExternalForm()));
        allCardImage.put("Feral Imp", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Feral Imp.jpg")).toExternalForm()));
        allCardImage.put("Fireyarou", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Fireyarou.jpg")).toExternalForm()));
        allCardImage.put("Flame manipulator", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Flame manipulator.jpg")).toExternalForm()));
        allCardImage.put("Forest", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Forest.jpg")).toExternalForm()));
        allCardImage.put("Gate Guardian", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Gate Guardian.jpg")).toExternalForm()));
        allCardImage.put("Haniwa", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Haniwa.jpg")).toExternalForm()));
        allCardImage.put("Harpie's Feather Duster", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Harpie's Feather Duster.jpg")).toExternalForm()));
        allCardImage.put("Herald of Creation", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Herald of Creation.jpg")).toExternalForm()));
        allCardImage.put("Hero of the east", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Hero of the east.jpg")).toExternalForm()));
        allCardImage.put("Leotron", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Leotron.jpg")).toExternalForm()));
        allCardImage.put("Magic Cylinder", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magic Cylinder.jpg")).toExternalForm()));
        allCardImage.put("Magic Jammer", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magic Jammer.jpg")).toExternalForm()));
        allCardImage.put("Magnum Shield", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Magnum Shield.jpg")).toExternalForm()));
        allCardImage.put("Man-Eater Bug", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Man-Eater Bug.jpg")).toExternalForm()));
        allCardImage.put("Marshmallon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Marshmallon.jpg")).toExternalForm()));
        allCardImage.put("Messenger of peace", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Messenger of peace.jpg")).toExternalForm()));
        allCardImage.put("Mind Crush", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mind Crush.jpg")).toExternalForm()));
        allCardImage.put("Mirage Dragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mirage Dragon.jpg")).toExternalForm()));
        allCardImage.put("Mirror Force", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mirror Force.jpg")).toExternalForm()));
        allCardImage.put("Monster Reborn", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Monster Reborn.jpg")).toExternalForm()));
        allCardImage.put("Mystical space typhoon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Mystical space typhoon.jpg")).toExternalForm()));
        allCardImage.put("Negate Attack", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Negate Attack.jpg")).toExternalForm()));
        allCardImage.put("Pot of Greed", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Pot of Greed.jpg")).toExternalForm()));
        allCardImage.put("Raigeki", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Raigeki.jpg")).toExternalForm()));
        allCardImage.put("Ring of defense", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Ring of defense.jpg")).toExternalForm()));
        allCardImage.put("Scanner", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Scanner.jpg")).toExternalForm()));
        allCardImage.put("Silver Fang", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Silver Fang.jpg")).toExternalForm()));
        allCardImage.put("Skull Guardian", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Skull Guardian.jpg")).toExternalForm()));
        allCardImage.put("Slot Machine", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Slot Machine.jpg")).toExternalForm()));
        allCardImage.put("Solemn Warning", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Solemn Warning.jpg")).toExternalForm()));
        allCardImage.put("Spell Absorption", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Spell Absorption.jpg")).toExternalForm()));
        allCardImage.put("Spiral Serpent", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Spiral Serpent.jpg")).toExternalForm()));
        allCardImage.put("Suijin", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Suijin.jpg")).toExternalForm()));
        allCardImage.put("Supply Squad", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Supply Squad.jpg")).toExternalForm()));
        allCardImage.put("Sword of dark destruction", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Sword of dark destruction.jpg")).toExternalForm()));
        allCardImage.put("Swords of Revealing Light", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Swords of Revealing Light.jpg")).toExternalForm()));
        allCardImage.put("Terraforming", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Terraforming.jpg")).toExternalForm()));
        allCardImage.put("Terratiger, the Empowered Warrior", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Terratiger, the Empowered Warrior.jpg")).toExternalForm()));
        allCardImage.put("Texchanger", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Texchanger.jpg")).toExternalForm()));
        allCardImage.put("The Calculator", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/The Calculator.jpg")).toExternalForm()));
        allCardImage.put("The Tricky", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/The Tricky.jpg")).toExternalForm()));
        allCardImage.put("Time Seal", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Time Seal.jpg")).toExternalForm()));
        allCardImage.put("Torrential Tribute", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Torrential Tribute.jpg")).toExternalForm()));
        allCardImage.put("Trap Hole", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Trap Hole.jpg")).toExternalForm()));
        allCardImage.put("Twin Twisters", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Twin Twisters.jpg")).toExternalForm()));
        allCardImage.put("Umiiruka", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Umiiruka.jpg")).toExternalForm()));
        allCardImage.put("United We Stand", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/United We Stand.jpg")).toExternalForm()));
        allCardImage.put("Warrior Dai Grepher", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Warrior Dai Grepher.jpg")).toExternalForm()));
        allCardImage.put("Wattaildragon", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Wattaildragon.jpg")).toExternalForm()));
        allCardImage.put("Wattkid", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Wattkid.jpg")).toExternalForm()));
        allCardImage.put("Yami", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Yami.jpg")).toExternalForm()));
        allCardImage.put("Yomi Ship", new Image(Objects.requireNonNull(getClass().getResource("/images/yugioh_Cards/Yomi Ship.jpg")).toExternalForm()));
    }

    private void showMonster() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0, j = 0; i <41; i++, k++) {
            if (k >= 9) {
                k = 0;
                j++;
            }
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/cards/" + i + ".jpg")).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(140);
            imageView.setFitWidth(140);
            gridPane.add(imageView, k, j);
        }
        Scene scene = new Scene(gridPane, 600, 600);
        stage.setScene(scene);
        stage.setTitle("Monster Cards");
        stage.show();
    }

    private void showSpellTrap() {
        Stage stage1 = new Stage();
        GridPane gridPane = new GridPane();
        for (int i = 0, k = 0, j = 0; i <35; i++, k++) {
            if (k >= 9) {
                k = 0;
                j++;
            }
            Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/cards2/" + i + ".jpg")).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            gridPane.add(imageView, k, j);
        }
        Scene scene = new Scene(gridPane, 600, 600);
        stage1.setScene(scene);
        stage1.setTitle("Spell&Trap Cards");
        stage1.show();
    }

    public static ImageView getCardImageViewByName(String cardName) {
        return allCards.get(cardName);
    }

    public static HashMap<String, ImageView> getAllCards() {
        return (HashMap<String, ImageView>) allCards;
    }

    public static HashMap<String, Image> getAllCardImage() {
        return (HashMap<String, Image>)allCardImage;
    }

}