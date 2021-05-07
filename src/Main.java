import main.java.controller.*;
import main.java.model.*;

public class Main {

    public static void main(String[] args) {
        RegisterAndLoginController.findMatcher();
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();

    }
}
