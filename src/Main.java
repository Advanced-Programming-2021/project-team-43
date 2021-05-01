import main.java.controller.SetCards;
import main.java.model.MonsterCard;

public class Main {

    public static void main(String[] args) {
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        System.out.println(MonsterCard.getMonsterByName("Yomi Ship").getAttack());

    }
}
