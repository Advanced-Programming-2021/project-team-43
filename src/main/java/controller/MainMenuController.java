package controller;

import model.Player;
import model.UserModel;
import view.MainMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainMenuController {

    public static String username;
    public static String username2;
    private static Matcher matcher;

    public static int run() {
        while (true) {
            String command = MainMenuView.getCommand();
            command = command.trim();
            int breaker = findMatcher(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int findMatcher(String command) {
        if ((matcher = getMatcher(command, "^menu \\s*enter\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^m \\s*en\\s* (.+?)$")).find()) {
            if (matcher.group(1).equals("Duel")) {
                duelRun();
                return 1;
            }
            if (matcher.group(1).equals("Deck")) {
                DeckController.run();
                return 1;
            }
            if (matcher.group(1).equals("Scoreboard")) {
                scoreboardRun();
                return 1;
            }
            if (matcher.group(1).equals("Profile")) {
                profileRun();
                return 1;
            }
            if (matcher.group(1).equals("Shop")) {
                ShopController.run();
                return 1;
            }
            if (matcher.group(1).equals("Import/Export")) {
                importExport();
                return 1;
            }
            MainMenuView.showInput("invalid command");
            return 1;
        }
        if (getMatcher(command, "^menu\\s* show-current$").find() || getMatcher(command, "^m\\s* s-c$").find()) {
            MainMenuView.showInput("Main");
            return 1;
        }
        if (getMatcher(command, "^menu \\s*exit$").find() || getMatcher(command, "^m \\s*ex$").find()) {
            return 0;
        }
        if (getMatcher(command, "^user \\s*logout$").find()) {
            MainMenuView.showInput("user logged out successfully!");
            return 0;
        }
        if ((matcher = getMatcher(command, "^increase\\s* --money\\s* (\\d+)$")).find() || (matcher = getMatcher(command, "^increase\\s* -m\\s* (\\d+)$")).find()) {
            increaseMoney(Integer.parseInt(matcher.group(1)));
            return 1;
        }
        MainMenuView.showInput("invalid command");
        return 1;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void increaseMoney(int money) {
        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.changeUserCoin(money);
    }

    public static int importExport() {
        while (true) {
            String command = MainMenuView.getCommand();
            if ((matcher = getMatcher(command, "import \\s*card \\s*(.+?)")).find()) {
                if (Json.exportCad() != null)
                    UserModel.importedCards = Json.exportCad();
                UserModel.importedCards.add(matcher.group(1));
                Json.importCard(UserModel.importedCards);
                continue;
            }
            if ((matcher = getMatcher(command, "export \\s*card \\s*(.+?)")).find()) {
                UserModel.importedCards = Json.exportCad();
                continue;
            }
            if (getMatcher(command, "^menu\\s* exit$").find() || getMatcher(command, "^m\\s* ex$").find()) {
                break;
            }
            if (getMatcher(command, "^menu\\s* show-current$").find() || getMatcher(command, "^m\\s* s-c$").find()) {
                MainMenuView.showInput("Import/Export");
                continue;
            }
            if ((matcher = getMatcher(command, "^menu\\s* enter\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^m\\s* en\\s* (.+?)$")).find()) {
                if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
                    MainMenuView.showInput("menu navigation is not possible");
                } else {
                    MainMenuView.showInput("invalid command");
                }
                continue;
            }
            MainMenuView.showInput("invalid command");
        }
        return 0;
    }

    public static int duelRun() {
        while (true) {
            String command = MainMenuView.getCommand();
            command = command.trim();
            int breaker = duelFindMatcher(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int duelFindMatcher(String command) {
        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--second-player\\s+(.+?)\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-s-p\\s+(.+?)\\s+-r\\s+(\\d+)$")).find()) {
            duelMenu(matcher.group(1), Integer.parseInt(matcher.group(2)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--second-player\\s+(.+?)\\s+--new\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-s-p\\s+(.+?)\\s+-n\\s+-r\\s+(\\d+)$")).find()) {
            duelMenu(matcher.group(1), Integer.parseInt(matcher.group(2)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--rounds\\s+(\\d+)\\s+--second-player\\s+(.+?)\\s+--new$")).find() || (matcher = getMatcher(command, "^duel\\s+-r\\s+(\\d+)\\s+-s-p\\s+(.+?)\\s+-n\\s*$")).find()) {
            duelMenu(matcher.group(2), Integer.parseInt(matcher.group(1)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--rounds\\s+(\\d+)--second-player\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-r\\s+(\\d+)-s-p\\s+(.+?)$")).find()) {
            duelMenu(matcher.group(2), Integer.parseInt(matcher.group(1)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--second-player\\s+(.+?)\\s+--rounds\\s+(\\d+)\\s+--new$")).find() || (matcher = getMatcher(command, "^duel\\s+-s-p\\s+(.+?)\\s+-r\\s+(\\d+)\\s+-n$")).find()) {
            duelMenu(matcher.group(1), Integer.parseInt(matcher.group(2)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--rounds\\s+(\\d+)\\s+--new\\s+--second-player\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^duel\\s+-r\\s+(\\d+)\\s+-n\\s+-s-p\\s+(.+?)$")).find()) {
            duelMenu(matcher.group(2), Integer.parseInt(matcher.group(1)));
            return 1;
        }
        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--ai\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-ai\\s+-r\\s+(\\d+)$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if ((matcher = getMatcher(command, "^duel\\s+--new\\s+--rounds\\s+(\\d+)\\s+--ai$")).find() || (matcher = getMatcher(command, "^duel\\s+-n\\s+-r\\s+(\\d+)\\s+-ai$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if ((matcher = getMatcher(command, "^duel\\s+--ai\\s+--rounds\\s+(\\d+)\\s+--new$")).find() || (matcher = getMatcher(command, "^duel\\s+-ai\\s+-r\\s+(\\d+)\\s+-n$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if ((matcher = getMatcher(command, "^duel\\s+--rounds\\s+(\\d+)\\s+--ai\\s+--new$")).find() || (matcher = getMatcher(command, "^duel\\s+-r\\s+(\\d+)\\s+-ai\\s+-n$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if ((matcher = getMatcher(command, "^duel\\s+--rounds\\s+(\\d+)\\s+--new\\s+--ai$")).find() || (matcher = getMatcher(command, "^duel\\s+-r\\s+(\\d+)\\s+-n\\s+-ai$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if ((matcher = getMatcher(command, "^duel\\s+--ai\\s+--new\\s+--rounds\\s+(\\d+)$")).find() || (matcher = getMatcher(command, "^duel\\s+-ai\\s+-n\\s+-r\\s+(\\d+)$")).find())
            duelMenu("ai", Integer.parseInt(matcher.group(2)));
        if (getMatcher(command, "^menu \\s*show-current$").find() || getMatcher(command, "^m \\s*s-c$").find()) {
            MainMenuView.showInput("Duel Menu");
            return 1;
        }
        if (getMatcher(command, "^menu \\s*exit$").find() || getMatcher(command, "^m \\s*ex$").find()) {
            return 0;
        }
        if ((matcher = getMatcher(command, "^menu\\s* enter \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^m\\s* en \\s*(.+?)$")).find()) {
            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard") || matcher.group(1).equals("Import/Export")) {
                MainMenuView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }
        MainMenuView.showInput("invalid command");
        return 1;
    }

    public static void duelMenu(String playerName, int roundNumber) {
        if (UserModel.isRepeatedUsername(playerName)) {
            UserModel user1 = UserModel.getUserByUsername(MainMenuController.username);
            UserModel user2 = UserModel.getUserByUsername(playerName);
            if (!user1.getActiveDeck().equals("")) {
                if (!user2.getActiveDeck().equals("")) {
                    if (user1.userAllDecks.get(user1.getActiveDeck()).validOrInvalid().equals("valid")) {
                        if (user2.userAllDecks.get(user2.getActiveDeck()).validOrInvalid().equals("valid")) {
                            if (roundNumber == 1 || roundNumber == 3) {
                                username2 = playerName;
                                String firstPlayer = PickFirstPlayer.chose(MainMenuController.username, playerName);
                                String secondPlayer;
                                if (firstPlayer.equals(playerName)) {
                                    secondPlayer = MainMenuController.username;
                                } else {
                                    secondPlayer = playerName;
                                }
                                new Player(UserModel.getUserByUsername(firstPlayer).getNickname(), UserModel.getUserByUsername(firstPlayer).userAllDecks.get(UserModel.getUserByUsername(firstPlayer).getActiveDeck()), true, roundNumber);
                                new Player(UserModel.getUserByUsername(secondPlayer).getNickname(), UserModel.getUserByUsername(secondPlayer).userAllDecks.get(UserModel.getUserByUsername(secondPlayer).getActiveDeck()), false, roundNumber);
                                GameMatController.run(UserModel.getUserByUsername(firstPlayer).getNickname(), UserModel.getUserByUsername(secondPlayer).getNickname());
                            } else {
                                MainMenuView.showInput("number of rounds is not supported");
                            }
                        } else {
                            MainMenuView.showInput(user2.getUsername() + "’s deck is invalid");
                        }
                    } else {
                        MainMenuView.showInput(user1.getUsername() + "’s deck is invalid");
                    }
                } else {
                    MainMenuView.showInput(user2.getUsername() + " has no active deck");
                }
            } else {
                MainMenuView.showInput(user1.getUsername() + " has no active deck");
            }
        } else {
            MainMenuView.showInput("there is no player with this username");
        }
    }

    public static int profileRun() {
        while (true) {
            String command = MainMenuView.getCommand();
            command = command.trim();
            int breaker = profile(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int profile(String command) {
        if (getMatcher(command, "^menu\\s* exit$").find() || getMatcher(command, "^m\\s* ex$").find()) {
            return 0;
        }
        if (getMatcher(command, "^menu \\s*show-current$").find() || getMatcher(command, "^m \\s*s-c$").find()) {
            MainMenuView.showInput("Profile Menu");
            return 1;
        }
        if ((matcher = getMatcher(command, "^menu \\s*enter (.+?)$")).find() || (matcher = getMatcher(command, "^m \\s*en (.+?)$")).find()) {
            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
                MainMenuView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s* change\\s* --nickname (.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s* change\\s* -n (.+?)$")).find()) {
            changeNickname(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--password\\s+--current\\s+(.+?)\\s+--new\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-p\\s+-c\\s+(.+?)\\s+-new\\s+(.+?)$")).find()) {
            changePassword(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--new\\s+(.+?)\\s+--password\\s+--current\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-new\\s+(.+?)\\s+-p\\s+-c\\s+(.+?)$")).find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--password\\s+--new\\s+(.+?)\\s+--current\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-p\\s+-n\\s+(.+?)\\s+-c\\s+(.+?)$")).find()) {
            changePassword(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--new\\s+(.+?)\\s+--current\\s+(.+?)\\s+--password$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-n\\s+(.+?)\\s+-c\\s+(.+?)\\s+-p$")).find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--current\\s+(.+?)\\s+--new\\s+(.+?)\\s+--password$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-c\\s+(.+?)\\s+-n\\s+(.+?)\\s+-p$")).find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^profile\\s+change\\s+--current\\s+(.+?)\\s+--password\\s+--new\\s+(.+?)$")).find() || (matcher = getMatcher(command, "^profile\\s+change\\s+-c\\s+(.+?)\\s+-p\\s+-n\\s+(.+?)$")).find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }
        MainMenuView.showInput("invalid command");
        return 1;
    }

    public static void changeNickname(String matcher) {
        if (UserModel.isRepeatedNickname(matcher)) {
            MainMenuView.showInput("user with nickname " + matcher + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changeNickname(matcher);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            MainMenuView.showInput("nickname changed successfully!");
        }
    }

    public static int scoreboardRun() {
        while (true) {
            String command = MainMenuView.getCommand();
            command = command.trim();
            int breaker = scoreboard(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int scoreboard(String command) {
        if (getMatcher(command, "^scoreboard \\s*show$").find() || getMatcher(command, "^s\\s+s$").find()) {
            showScoreboard();
            return 1;
        }
        if (getMatcher(command, "^menu\\s* exit$").find() || getMatcher(command, "^m\\s* ex$").find()) {
            return 0;
        }
        if ((matcher = getMatcher(command, "^menu\\s* enter\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^m\\s* en\\s* (.+?)$")).find()) {
            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
                MainMenuView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }
        if (getMatcher(command, "^menu\\s* show-current$").find() || getMatcher(command, "^m\\s* s-c$").find()) {
            MainMenuView.showInput("Score board Menu");
            return 1;
        }
        MainMenuView.showInput("invalid command");
        return 1;
    }

    public static void showScoreboard() {
        String[] keysUsers;
        String temp;
        keysUsers = UserModel.allUsernames.toArray(new String[0]);
        for (int x = 0; x < keysUsers.length; x++) {
            for (int y = x + 1; y < keysUsers.length; y++) {
                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() < UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
                    temp = keysUsers[y];
                    keysUsers[y] = keysUsers[x];
                    keysUsers[x] = temp;
                }
                if (UserModel.getUserByUsername(keysUsers[x]).getUserScore() == UserModel.getUserByUsername(keysUsers[y]).getUserScore()) {
                    if (keysUsers[x].compareToIgnoreCase(keysUsers[y]) > 0) {
                        temp = keysUsers[x];
                        keysUsers[x] = keysUsers[y];
                        keysUsers[y] = temp;
                    }
                }
            }
        }
        int rank = 1;
        for (int i = 0; i < keysUsers.length; i++) {
            MainMenuView.showInput((rank) + "- " + UserModel.getUserByUsername(keysUsers[i]).getNickname() + ": " + UserModel.getUserByUsername(keysUsers[i]).getUserScore());
            if (i < keysUsers.length - 1 && UserModel.getUserByUsername(keysUsers[i]).getUserScore() != UserModel.getUserByUsername(keysUsers[i + 1]).getUserScore())
                rank++;
        }
    }

    public static void changePassword(String currentPassword, String newPassword) {
        if (!UserModel.getUserByUsername(MainMenuController.username).getPassword().equals(currentPassword))
            MainMenuView.showInput("current password is invalid");
        else if (currentPassword.equals(newPassword))
            MainMenuView.showInput("please enter a new password");
        else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changePassword(newPassword);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            MainMenuView.showInput("password changed successfully!");
        }
    }

}