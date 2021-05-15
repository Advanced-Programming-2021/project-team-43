package test.java;

import main.java.controller.MainMenuController;
import main.java.controller.RegisterAndLoginController;
import main.java.controller.SpellEffect;
import main.java.model.DeckModel;
import main.java.model.MonsterZoneCard;
import main.java.model.Player;
import main.java.model.UserModel;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SpellEffectTest {
    @Test
    public void twinTwisters() {
        UserModel userModel = new UserModel("rival", "p", "rival");
        MainMenuController.username = "rival";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 4; i++) {
            deck.addCardToMain("Harpie's Feather Duster");
        }
        for (int i = 0; i < 2; i++) {
            deck.addCardToMain("Monster Reborn");
        }
        deck.addCardToMain("Mirror Force");
        deck.addCardToMain("Call of The Haunted");
        deck.addCardToMain("Magic Jamamer");
        userModel.addDeck(deck);
        Player rival = new Player("rival", deck, false, 1);
        UserModel userModel2 = new UserModel("onlineUser", "p", "onlineUser");
        MainMenuController.username = "onlineUser";
        DeckModel deck2 = new DeckModel("deck");
        for (int i = 0; i < 4; i++) {
            deck.addCardToMain("Advanced Ritual Art");
        }
        for (int i = 0; i < 2; i++) {
            deck.addCardToMain("Magnum Shield");
        }
        deck.addCardToMain("United We Stand");
        deck.addCardToMain("Black Pendant");
        deck.addCardToMain("Umiiruka");
        userModel.addDeck(deck);
        Player onlineUser = new Player("onlineUser", deck, true, 1);
        int acted = SpellEffect.twinTwisters("onlineUser", "rival");
        assertEquals(1, acted);
    }

    @Test
    public void normalEffectController() {
    }

    @Test
    public void quickPlayEffectController() {
    }

    @Test
    public void equipEffectController() {
    }

    @Test
    public void fieldEffectController() {
    }

    @Test
    public void raigeki() {
        UserModel userModel = new UserModel("rival", "p", "rival");
        MainMenuController.username = "rival";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Axe Raider");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Battle OX");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Wattkid");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Flame manipulator");
        }
        userModel.addDeck(deck);
        Player rival = new Player("rival", deck, false, 1);
        int acted = SpellEffect.raigeki("rival");
        assertEquals(1, acted);

    }

    @Test
    public void supplySquad() {
    }

    @Test
    public void spellAbsorption() {
    }

    @Test
    public void messengerOfPeace() {
    }
    @Test
    public void terraforming(){
       // UserModel userModel = new UserModel("rival", "p", "rival");
       RegisterAndLoginController.registerInGame("rival","rival","p");
       // RegisterAndLoginController.loginInGame("rival","p");
        DeckModel deck = new DeckModel("deck");
       // System.out.println(MainMenuController.username+"dddd");
        UserModel.getUserByUsername("rival").addDeck(deck);
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Axe Raider");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Battle OX");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Wattkid");
        }
        for (int i = 0; i < 10; i++) {
            deck.addCardToMain("Flame manipulator");
        }

        Player rival = new Player("rival", deck, false, 1);
        SpellEffect.terraforming("rival",rival);
        assertEquals(1,     SpellEffect.terraforming("rival",rival));
    }
}