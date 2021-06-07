package main.java.view;
import controller.RegisterAndLoginController;
import controller.SetCards;


public class Main {

    public static void main(String[] args) {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
    }

}
