package controller;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import model.UserModel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Json {

    public static void writeUserModelInfo(HashMap<String, UserModel> allUsersInfo, ArrayList<String> allUsernames, ArrayList<String> allUsersNicknames) {
        try {
            FileWriter writerInfo = new FileWriter("jsonUsersInfo.txt");
            writerInfo.write(new Gson().toJson(allUsersInfo));
            writerInfo.close();
        } catch (IOException ignored) {
        }
        try {
            FileWriter writerNames = new FileWriter("jsonUsernames.txt");
            writerNames.write(new Gson().toJson(allUsernames));
            writerNames.close();
        } catch (IOException ignored) {
        }
        try {
            FileWriter writerNicknames = new FileWriter("jsonNicknames.txt");
            writerNicknames.write(new Gson().toJson(allUsersNicknames));
            writerNicknames.close();
        } catch (IOException ignored) {
        }
    }

    public static HashMap<String, UserModel> readUserInfo() {
        try {
            String readInfo = new String(Files.readAllBytes(Paths.get("jsonUsersInfo.txt")));
            HashMap<String, UserModel> userInfo;
            userInfo = new Gson().fromJson(readInfo,
                    new TypeToken<HashMap<String, UserModel>>() {
                    }.getType());
            return userInfo;
        } catch (IOException ignored) {
        }
        return null;
    }

    public static ArrayList<String> readUsernames() {
        try {
            String readNames = new String(Files.readAllBytes(Paths.get("jsonUsernames.txt")));
            ArrayList<String> usernames;
            usernames = new Gson().fromJson(readNames,
                    new TypeToken<List<String>>() {
                    }.getType());
            return usernames;
        } catch (IOException ignored) {
        }
        return null;
    }

    public static ArrayList<String> readUserNicknames() {
        try {
            String readNicknames = new String(Files.readAllBytes(Paths.get("jsonNicknames.txt")));
            ArrayList<String> nicknames;
            nicknames = new Gson().fromJson(readNicknames,
                    new TypeToken<List<String>>() {
                    }.getType());
            return nicknames;
        } catch (IOException ignored) {
        }
        return null;
    }

    public static void importMonsterCard(ArrayList<MonsterCard> cardNames) {
        try {
            FileWriter writerInfo = new FileWriter("jsonAddedMonsterCards.txt");
            writerInfo.write(new Gson().toJson(cardNames));
            writerInfo.close();
        } catch (IOException ignored) {
        }
    }

    public static ArrayList<MonsterCard> exportMonsterCad() {
        try {
            String readCardNames = new String(Files.readAllBytes(Paths.get("jsonAddedMonsterCards.txt")));
            ArrayList<MonsterCard> cardNames;
            cardNames = new Gson().fromJson(readCardNames,
                    new TypeToken<List<MonsterCard>>() {
                    }.getType());
            return cardNames;
        } catch (IOException ignored) {
        }
        return null;
    }
    public static void importSpellCard(ArrayList<SpellCard> cardNames) {
        try {
            FileWriter writerInfo = new FileWriter("jsonAddedSpellCards.txt");
            writerInfo.write(new Gson().toJson(cardNames));
            writerInfo.close();
        } catch (IOException ignored) {
        }
    }

    public static ArrayList<SpellCard> exportSpellCard() {
        try {
            String readCardNames = new String(Files.readAllBytes(Paths.get("jsonAddedSpellCards.txt")));
            ArrayList<SpellCard> cardNames;
            cardNames = new Gson().fromJson(readCardNames,
                    new TypeToken<List<SpellCard>>() {
                    }.getType());
            return cardNames;
        } catch (IOException ignored) {
        }
        return null;
    }


    public static void importTrapCard(ArrayList<TrapCard> cardNames) {
        try {
            FileWriter writerInfo = new FileWriter("jsonAddedTrapCards.txt");
            writerInfo.write(new Gson().toJson(cardNames));
            writerInfo.close();
        } catch (IOException ignored) {
        }
    }

    public static ArrayList<TrapCard> exportTrapCard() {
        try {
            String readCardNames = new String(Files.readAllBytes(Paths.get("jsonAddedTrapCards.txt")));
            ArrayList<TrapCard> cardNames;
            cardNames = new Gson().fromJson(readCardNames,
                    new TypeToken<List<TrapCard>>() {
                    }.getType());
            return cardNames;
        } catch (IOException ignored) {
        }
        return null;
    }

}
