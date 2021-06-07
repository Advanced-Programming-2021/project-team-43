package main.java.controller;
import javafx.scene.image.ImageView;
import main.java.model.*;
import main.java.view.*;

import java.util.Random;
import java.util.regex.*;


public class RegisterAndLoginController {

//    public static int run() {
//        if (JSON.readUserInfo() == null) {
//            UserModel userModel = new UserModel("AI", "p", "AI");
//            DeckModel deckModel = new DeckModel("AILevel1");
//            MainMenuController.username = "AI";
//            for (int i = 0; i < 6; i++) {
//                deckModel.addCardToMain("Axe Raider");
//                deckModel.addCardToMain("Horn Imp");
//                deckModel.addCardToMain("Silver Fang");
//                deckModel.addCardToMain("Fireyarou");
//                deckModel.addCardToMain("Curtain of the dark ones");
//                deckModel.addCardToMain("Dark Blade");
//                deckModel.addCardToMain("Warrior Dai Grepher");
//                deckModel.addCardToMain("Bitron");
//            }
//            userModel.addDeck(deckModel);
//            userModel.setActiveDeck("AILevel1");
//
//
//            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
//        } else {
//            UserModel.allUsersInfo = JSON.readUserInfo();
//            UserModel.allUsernames = JSON.readUsernames();
//            UserModel.allUsersNicknames = JSON.readUserNicknames();
//        }
//
//        if (JSON.exportCad() != null) {
//            UserModel.importedCards = JSON.exportCad();
//        }
//        new ShopModel(Card.getCards());
//        while (true) {
//            String command = DeckView.getCommand();
//            command = command.trim();
//            int breaker = findMatcher(command);
//            if (breaker == 0) {
//                break;
//            }
//        }
//        return 0;
//    }

//    public static int findMatcher(String command) {
//        Pattern pattern = Pattern.compile("^user \\s*login \\s*--username\\s* (.+?) --password \\s*(.+?)$");
//        Matcher matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            loginInGame(matcher.group(1), matcher.group(2));
//            return 1;
//        }
//        pattern = Pattern.compile("^user \\s*login\\s* --password \\s*(.+?) \\s*--username \\s*(.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            loginInGame(matcher.group(2), matcher.group(1));
//            return 1;
//
//        }
//        pattern = Pattern.compile("^user \\s*create \\s*--username\\s* (.+?) \\s*--nickname\\s* (.+?) \\s*--password \\s*(.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(1), matcher.group(2), matcher.group(3));
//            return 1;
//        }
//        pattern = Pattern.compile("^user\\s* create\\s* --username \\s*(.+?)\\s* --password \\s*(.+?)\\s* --nickname\\s* (.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(1), matcher.group(3), matcher.group(2));
//            return 1;
//        }
//        pattern = Pattern.compile("^user\\s* create\\s* --nickname\\s* (.+?)\\s* --username\\s* (.+?)\\s* --password\\s* (.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(2), matcher.group(1), matcher.group(3));
//            return 1;
//        }
//        pattern = Pattern.compile("^user\\s* create\\s* --nickname\\s* (.+?)\\s* --password \\s*(.+?)\\s*--usaername \\s*(.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(2), matcher.group(3), matcher.group(1));
//            return 1;
//        }
//        pattern = Pattern.compile("^user\\s* create\\s* --password\\s* (.+?)\\s* --username\\s* (.+?)\\s* --nickname\\s* (.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(3), matcher.group(1), matcher.group(2));
//            return 1;
//        }
//        pattern = Pattern.compile("^user\\s* create\\s* --password\\s* (.+?)\\s* --nickname\\s* (.+?)\\s* --username\\s* (.+?)\\s*$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            registerInGame(matcher.group(3), matcher.group(2), matcher.group(1));
//            return 1;
//        }
//        pattern = Pattern.compile("^menu enter\\s* (.+?)$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            if (matcher.group(1).equals("duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
//                RegisterAndLoginView.showInput("menu navigation is not possible");
//            } else {
//                RegisterAndLoginView.showInput("invalid command");
//            }
//            return 1;
//        }
//        pattern = Pattern.compile("^menu \\s*exit$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            return 0;
//        }
//        pattern = Pattern.compile("^menu \\s*show-current$");
//        matcher = pattern.matcher(command);
//        if (matcher.find()) {
//            RegisterAndLoginView.showInput("Login Menu");
//            return 1;
//        }
//        RegisterAndLoginView.showInput("invalid command");
//        return 1;
//    }

    public static String registerInGame(String username, String nickname, String password, String imageUrl) {
        if (username.isEmpty())
            return "please fill the username field!";
        if (nickname.isEmpty())
            return "please fill the nickname field!";
        if (password.isEmpty())
            return "please fill the password filed!";
        if (UserModel.isRepeatedUsername(username))
            return "user with username " + username + " already exists";
        if (UserModel.isRepeatedNickname(nickname))
            return "user with nickname " + nickname + " already exists";
        else {
            new UserModel(username, password, nickname, imageUrl);
            return "user created successfully!";
        }
    }

    public static String loginInGame(String username, String password) {
        if (username.isEmpty())
            return "please fill the username field!";
        if (password.isEmpty())
            return "please fill the password filed!";
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                MainMenuController.username = username;
                return "user logged in successfully!";
            }
            else
                return "Username and password didn’t match!";
        }
        else
            return "Username and password didn’t match!";
    }

}