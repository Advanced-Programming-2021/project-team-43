package main.java.controller;

import main.java.controller.JSON;
import main.java.model.UserModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.*;

public class JSONTest {

    @Test
    public void writeUserModelInfo() {
        HashMap<String, UserModel> allUsersInfo =UserModel.allUsersInfo;
        ArrayList<String> nicknames = UserModel.allUsersNicknames;
        ArrayList<String> names = UserModel.allUsernames;
        JSON.writeUserModelInfo(allUsersInfo, nicknames, names);
        HashMap<String, UserModel> userInfo = JSON.readUserInfo();
        ArrayList<String> allNicknames = JSON.readUserNicknames();
        ArrayList<String> allNames = JSON.readUsernames();
        assertEquals(allUsersInfo, userInfo);
        assertEquals(nicknames, allNicknames);
        assertEquals(nicknames, allNames);
    }


    @Test
    public void importCard() {
        ArrayList<String> cards=UserModel.importedCards;
        JSON.importCard(cards);
        ArrayList<String> cardsAfterWriting=JSON.exportCad();
        assertEquals(cards,cardsAfterWriting);
    }
}