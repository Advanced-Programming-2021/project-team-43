package controller;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writerNames = new FileWriter("jsonUsernames.txt");
            writerNames.write(new Gson().toJson(allUsernames));
            writerNames.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writerNicknames = new FileWriter("jsonNicknames.txt");
            writerNicknames.write(new Gson().toJson(allUsersNicknames));
            writerNicknames.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeUserAllInfo(HashMap<String, UserModel> allUsersInfo) {
        try {
            FileWriter writerInfo = new FileWriter("jsonUsersInfo.txt");
            writerInfo.write(new Gson().toJson(allUsersInfo));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static HashMap<String, UserModel> readUserInfo() {
        File newFile = new File("jsonUsersInfo.txt");
        if (newFile.exists()) {
            try {
                String readInfo = new String(Files.readAllBytes(Paths.get("jsonUsersInfo.txt")));
                HashMap<String, UserModel> userInfo;
                userInfo = new Gson().fromJson(readInfo, new TypeToken<HashMap<String, UserModel>>() {}.getType());
                return userInfo;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<String> readUsernames() {
        File newFile = new File("jsonUsernames.txt");
        if (newFile.exists()) {
            try {
                String readNames = new String(Files.readAllBytes(Paths.get("jsonUsernames.txt")));
                ArrayList<String> usernames;
                usernames = new Gson().fromJson(readNames, new TypeToken<List<String>>() {
                }.getType());
                return usernames;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<String> readUserNicknames() {
        File newFile = new File("jsonNicknames.txt");
        if (newFile.exists()) {
            try {
                String readNicknames = new String(Files.readAllBytes(Paths.get("jsonNicknames.txt")));
                ArrayList<String> nicknames;
                nicknames = new Gson().fromJson(readNicknames, new TypeToken<List<String>>() {
                }.getType());
                return nicknames;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void importCard(ArrayList<String> cardNames) {
        try {
            FileWriter writerInfo = new FileWriter("jsonAddedCards.txt");
            writerInfo.write(new Gson().toJson(cardNames));
            writerInfo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> exportCad() {
        try {
            String readCardNames = new String(Files.readAllBytes(Paths.get("jsonAddedCards.txt")));
            ArrayList<String> cardNames;
            cardNames = new Gson().fromJson(readCardNames,
                    new TypeToken<List<String>>() {
                    }.getType());
            return cardNames;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
