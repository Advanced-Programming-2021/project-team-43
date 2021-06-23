package controller;

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
    public void importCard() {
//        ArrayList<String> cards=UserModel.importedCards;
//        Json.importCard(cards);
//        ArrayList<String> cardsAfterWriting= Json.exportCad();
//        assertEquals(cards,cardsAfterWriting);
    }

}