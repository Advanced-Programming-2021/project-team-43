import controller.*;
import controller.SetCards;


public class Main {

    public static void main(String[] args) {

        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        Run.run();
    }



}
