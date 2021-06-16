import controller.*;


public class Main {

    public static void main(String[] args) {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        RegisterAndLoginController.run();
    }

}
