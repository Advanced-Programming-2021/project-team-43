import controller.SetCards;
import model.TrapCard;

public class Main {

    public static void main(String[] args) {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        System.out.println(TrapCard.getTrapCardByName("Trap Hole").getIcon());
    }
}
