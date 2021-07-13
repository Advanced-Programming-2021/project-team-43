package controller;

import model.Card;
import model.DeckModel;
import model.ShopModel;
import model.UserModel;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterAndLoginController {

    public static HashMap<String, String> allOnlineUsers=new HashMap<>();
    //token ,username
    public static String run(String input) {
        if (Json.readUserInfo() == null) {
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
        } else {
            UserModel.allUsersInfo = Json.readUserInfo();
            UserModel.allUsernames = Json.readUsernames();
            UserModel.allUsersNicknames = Json.readUserNicknames();
        }
        if (!UserModel.isRepeatedUsername("AI")) {
            UserModel userModel = new UserModel("AI", "p", "AI", "/images/profile/char0.jpg");
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


        return findMatcher(input);


    }

    public static String findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^R user login --username (.+?) --password (.+?)$")).find()) {
            loginInGame(matcher.group(1), matcher.group(2));

            return loginInGame(matcher.group(1), matcher.group(2));
        }

        if ((matcher = getMatcher(command, "^R user create --username (.+?) --nickname (.+?) --password (.+?) --imageURL (.+?)$")).find()) {

            return registerInGame(matcher.group(1), matcher.group(2), matcher.group(3),matcher.group(4));

        }

        return null;

    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static String registerInGame(String username, String nickname, String password,String imageURL) {
        if (UserModel.isRepeatedUsername(username)) {
            return ("user with username " + username + " already exists");

        }
        if (UserModel.isRepeatedNickname(nickname)) {
            return ("user with nickname " + nickname + " already exists");

        }
        new UserModel(username, password, nickname ,imageURL);
        return ("user created successfully!");
    }

    public static String loginInGame(String username, String password) {
        if (UserModel.isRepeatedUsername(username)) {
            if (UserModel.getUserByUsername(username).getPassword().equals(password)) {
                String token = UUID.randomUUID().toString().toUpperCase();
                allOnlineUsers.put(token,username);

                return "user logged in successfully!"+token;
            } else {
                return "Username and password didn’t match!";
            }
        } else {
            return "Username and password didn’t match!";
        }
    }

}