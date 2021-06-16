package controller;
import model.*;
import view.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.*;


public class RegisterAndLoginController {

    public static int run() {
        if (Json.readUserInfo() == null) {
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
        } else {
            UserModel.allUsersInfo = Json.readUserInfo();
            UserModel.allUsernames = Json.readUsernames();
            UserModel.allUsersNicknames = Json.readUserNicknames();
        }
        if (!UserModel.isRepeatedUsername("AI")){
            UserModel userModel = new UserModel("AI", "p", "AI");
            DeckModel deckModel = new DeckModel("AILevel1");
            MainMenuController.username = "AI";
            for (int i = 0; i < 6; i++) {
                deckModel.addCardToMain("Axe Raider");
                deckModel.addCardToMain("Horn Imp");
                deckModel.addCardToMain("Silver Fang");
                deckModel.addCardToMain("Fireyarou");
                deckModel.addCardToMain("Curtain of the dark ones");
                deckModel.addCardToMain("Dark Blade");
                deckModel.addCardToMain("Warrior Dai Grepher");
                deckModel.addCardToMain("Bitron");
            }
            userModel.addDeck(deckModel);
            userModel.setActiveDeck("AILevel1");
        }
        new ShopModel(Card.getCards());
        while (true) {
            String command = DeckView.getCommand();
            command = command.trim();
            int breaker = findMatcher(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^user \\s*login \\s*--username\\s* (.+?) --password \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^user \\s*login \\s*-u\\s* (.+?) -p \\s*(.+?)$")).find()) {
            loginInGame(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user \\s*login\\s* --password \\s*(.+?) \\s*--username \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^user \\s*login\\s* -p \\s*(.+?) \\s*-u \\s*(.+?)$")).find()) {
            loginInGame(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user \\s*create \\s*--username\\s* (.+?) \\s*--nickname\\s* (.+?) \\s*--password \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^user \\s*create \\s*-u\\s* (.+?) \\s*-n\\s* (.+?) \\s*-p \\s*(.+?)$")).find()) {
            registerInGame(matcher.group(1), matcher.group(2), matcher.group(3));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user\\s* create\\s* --username \\s*(.+?)\\s* --password \\s*(.+?)\\s* --nickname\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^user\\s* create\\s* -u \\s*(.+?)\\s* -p \\s*(.+?)\\s* -n\\s* (.+?)$")).find()) {
            registerInGame(matcher.group(1), matcher.group(3), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command,"^user\\s* create\\s* --nickname\\s* (.+?)\\s* --username\\s* (.+?)\\s* --password\\s* (.+?)$" )).find() || (matcher = getMatcher(command,"^user\\s* create\\s* -n\\s* (.+?)\\s* -u\\s* (.+?)\\s* -p\\s* (.+?)$" )).find()) {
            registerInGame(matcher.group(2), matcher.group(1), matcher.group(3));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user\\s* create\\s* --nickname\\s* (.+?)\\s* --password \\s*(.+?)\\s*--username \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^user\\s* create\\s* -n\\s* (.+?)\\s* -p \\s*(.+?)\\s*-u \\s*(.+?)$")).find()) {
            registerInGame(matcher.group(3), matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user\\s* create\\s* --password\\s* (.+?)\\s* --username\\s* (.+?)\\s* --nickname\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^user\\s* create\\s* -p\\s* (.+?)\\s* -u\\s* (.+?)\\s* -n\\s* (.+?)$")).find()) {
            registerInGame(matcher.group(2), matcher.group(3), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^user\\s* create\\s* --password\\s* (.+?)\\s* --nickname\\s* (.+?)\\s* --username\\s* (.+?)\\s*$")).find() || (matcher = getMatcher(command, "^user\\s* create\\s* -p\\s* (.+?)\\s* -n\\s* (.+?)\\s* -u\\s* (.+?)\\s*$")).find()) {
            registerInGame(matcher.group(3), matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^menu enter\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^m en\\s* (.+?)$")).find()) {
            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
                RegisterAndLoginView.showInput("please login first");
            } else {
                RegisterAndLoginView.showInput("invalid command");
            }
            return 1;
        }
        if ((getMatcher(command, "^menu \\s*exit$")).find() || (getMatcher(command, "^m \\s*ex$")).find()) {
            return 0;
        }
        if ((getMatcher(command, "^menu \\s*show-current$")).find() || (getMatcher(command, "^m \\s*s-c$")).find()) {
            RegisterAndLoginView.showInput("Login Menu");
            return 1;
        }
        RegisterAndLoginView.showInput("invalid command");
        return 1;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void registerInGame(String username, String nickname, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            RegisterAndLoginView.showInput("user with username " + username + " already exists");
            return;
        }
        if (UserModel.isRepeatedNickname(nickname)) {
            RegisterAndLoginView.showInput("user with nickname " + nickname + " already exists");
            return;
        }
        new UserModel(username, password, nickname);
        RegisterAndLoginView.showInput("user created successfully!");
    }

    public static void loginInGame(String username, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                RegisterAndLoginView.showInput("user logged in successfully!");
                MainMenuController.username = username;
                MainMenuController.run();
            } else {
                RegisterAndLoginView.showInput("Username and password didn’t match!");
            }
        } else {
            RegisterAndLoginView.showInput("Username and password didn’t match!");
        }
    }

}