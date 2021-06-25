package controller;

import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import model.UserModel;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class JsonTest {

    @Test
    public void writeUserModelInfo() {
        HashMap<String, UserModel> allUsersInfo =UserModel.allUsersInfo;
        ArrayList<String> nicknames = UserModel.allUsersNicknames;
        ArrayList<String> names = UserModel.allUsernames;
        Json.writeUserModelInfo(allUsersInfo, nicknames, names);
        HashMap<String, UserModel> userInfo = Json.readUserInfo();
        ArrayList<String> allNicknames = Json.readUserNicknames();
        ArrayList<String> allNames = Json.readUsernames();
        assertEquals(allUsersInfo, userInfo);
        assertEquals(nicknames, allNicknames);
        assertEquals(nicknames, allNames);
    }

    @Test
    public void importMonsterCard() {
        ArrayList<MonsterCard> cards=UserModel.importedMonsterCards;
        Json.importMonsterCard(cards);
        ArrayList<MonsterCard> cardsAfterWriting= Json.exportMonsterCad();
        assertEquals(cards,cardsAfterWriting);
    }
    @Test
    public void importSpellCard() {
        ArrayList<SpellCard> cards=UserModel.importedSpellCards;
        Json.importSpellCard(cards);
        ArrayList<SpellCard> cardsAfterWriting= Json.exportSpellCard();
        assertEquals(cards,cardsAfterWriting);
    }    @Test
    public void importTrapCard() {
        ArrayList<TrapCard> cards=UserModel.importedTrapCards;
        Json.importTrapCard(cards);
        ArrayList<TrapCard> cardsAfterWriting= Json.exportTrapCard();
        assertEquals(cards,cardsAfterWriting);
    }
}