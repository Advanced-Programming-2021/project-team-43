package controller;

import model.*;
import view.MainMenuView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainMenuController {

    public static String username;
    public static String username2;
    private static Matcher matcher;

    public static String findMatcher(String command) {


        if (getMatcher(command, "^M(.+?)menu \\s*exit$").find() || getMatcher(command, "^m \\s*ex$").find()) {
            RegisterAndLoginController.allOnlineUsers.remove(matcher.group(1));
            return "continue";
        }
        if (getMatcher(command, "^M(.+?)user \\s*logout$").find()) {

            RegisterAndLoginController.allOnlineUsers.remove(matcher.group(1));
            return ("user logged out successfully!");
        }
        if ((matcher = getMatcher(command, "^M(.+?)increase --money (\\d+)$")).find() || (matcher = getMatcher(command, "^increase\\s* -m\\s* (\\d+)$")).find()) {
            increaseMoney(Integer.parseInt(matcher.group(2)), matcher.group(1));
            return "continue";
        }
        return null;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void increaseMoney(int money, String token) {
        UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        userModel.changeUserCoin(money);
    }

    public static String profile(String command) {
        if ((matcher = getMatcher(command, "^profile(.+?) change --nickname (.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s* change\\s* -n (.+?)$")).find()) {
            return changeNickname(matcher.group(2), matcher.group(1));
        }
        if ((matcher = getMatcher(command, "^profile(.+?) change --password --new (.+?)  --current (.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-new\\s+(.+?)\\s+-p\\s+-c\\s+(.+?)$")).find()) {
            return changePassword(matcher.group(1), matcher.group(3), matcher.group(2));
        }
        if ((matcher = getMatcher(command, "^profile(.+?) change image (\\d*)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-new\\s+(.+?)\\s+-p\\s+-c\\s+(.+?)$")).find()) {
            UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(matcher.group(1))).setImageUrl("/images/profile/char" + matcher.group(2) + ".jpg");
            return "continue";
        }
        return null;
    }

    public static String changeNickname(String matcher, String token) {
        if (UserModel.isRepeatedNickname(matcher)) {
            return ("user with nickname " + matcher + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changeNickname(matcher);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            return ("nickname changed successfully!");
        }
    }

    public static String changePassword(String currentPassword, String newPassword, String token) {
        if (!UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token)).getPassword().equals(currentPassword))
            return ("current password is invalid");
        else if (currentPassword.equals(newPassword))
            return ("please enter a new password");
        else {
            UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
            user.changePassword(newPassword);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            return ("password changed successfully!");
        }
    }

    public static ArrayList<UserModel> showScoreboard() {
        ArrayList<UserModel> score = new ArrayList<>(UserModel.allUsersInfo.values());
        Collections.sort(score, Comparator.comparing(UserModel::getUserScore).reversed());
        score.removeIf(userModel -> userModel.getUsername().equals("AI"));
        return score;
    }



}