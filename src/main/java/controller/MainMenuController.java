package controller;

import model.Player;
import model.UserModel;
import view.MainMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainMenuController {

    public static String username;
    public static String username2;

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

        Pattern pattern = Pattern.compile("^menu \\s*enter\\s* (.+?)$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (matcher.group(1).equals("duel")) {
                duelRun();
                return 1;

            }
            if (matcher.group(1).equals("deck")) {
                DeckController.run();
                return 1;
            }
            if (matcher.group(1).equals("scoreboard")) {
                scoreboardRun();
                return 1;

            }
            if (matcher.group(1).equals("profile")) {
                profileRun();
                return 1;

            }
            if (matcher.group(1).equals("shop")) {
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


        pattern = Pattern.compile("^menu\\s* show-current$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            MainMenuView.showInput("Main Menu");
            return 1;
        }

        pattern = Pattern.compile("^menu \\s*exit$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            return 0;
        }
        pattern = Pattern.compile("^increase\\s* --money\\s* (\\d+)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            increaseMoney(Integer.parseInt(matcher.group(1)));
        }


        MainMenuView.showInput("invalid command");
        return 1;

    }

    public static void increaseMoney(int money) {
        UserModel userModel = UserModel.getUserByUsername(MainMenuController.username);
        userModel.changeUserCoin(money);
    }

    public static int importExport() {

        while (true) {
            String command = MainMenuView.getCommand();
            Pattern pattern = Pattern.compile("import \\s*card \\s*(.+?)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                UserModel.importedCards = JSON.exportCad();
                UserModel.importedCards.add(matcher.group(1));
                JSON.importCard(UserModel.importedCards);

                continue;

            }
            pattern = Pattern.compile("export \\s*card \\s*(.+?)");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                UserModel.importedCards = JSON.exportCad();


                continue;
            }
            pattern = Pattern.compile("^menu\\s* exit$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                break;
            }

            pattern = Pattern.compile("^menu\\s* show-current$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                MainMenuView.showInput("import/export Menu");
                continue;
            }


            pattern = Pattern.compile("^menu\\s* enter\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
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


        Pattern pattern = Pattern.compile("duel\\s* --new \\s*--second-player\\s* (.+?)\\s* --rounds \\s*(\\d+)");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            duelMenu(matcher.group(1), Integer.parseInt(matcher.group(2)));
            return 1;
        }
        pattern = Pattern.compile("^menu\\s* exit$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            return 0;
        }
        pattern = Pattern.compile("^menu \\s*show-current$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            MainMenuView.showInput("Duel Menu");
            return 1;
        }


        pattern = Pattern.compile("^menu\\s* enter \\s*(.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (matcher.group(1).equals("duel") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard") || matcher.group(1).equals("Import/Export")) {
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
                                //GameMatController.commandController(UserModel.getUserByUsername(firstPlayer).getNickname(), UserModel.getUserByUsername(secondPlayer).getNickname());

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


        Pattern pattern = Pattern.compile("^menu\\s* exit$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            return 0;
        }

        pattern = Pattern.compile("^menu \\s*show-current$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            MainMenuView.showInput("Profile Menu");
            return 1;
        }


        pattern = Pattern.compile("^menu \\s*enter (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (matcher.group(1).equals("duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                MainMenuView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }


        pattern = Pattern.compile("^profile\\s* change\\s* --nickname (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changeNickname(matcher.group(1));
            return 1;
        }


        pattern = Pattern.compile("^profile\\s* change\\s* --password \\s*--current \\s*(.+?)\\s* --new\\s* (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changePassword(matcher.group(1), matcher.group(2));
            return 1;
        }

        pattern = Pattern.compile("^profile\\s* change\\s* --current\\s* (.+?)\\s* --password \\s*--new\\s* (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changePassword(matcher.group(1), matcher.group(2));
            return 1;
        }


        pattern = Pattern.compile("^profile\\s* change\\s* --current\\s* (.+?)\\s* --new\\s* (.+?) \\s*--password$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changePassword(matcher.group(1), matcher.group(2));
            return 1;
        }


        pattern = Pattern.compile("^profile\\s* change\\s* --password\\s* --new\\s* (.+?) \\s*--current\\s* (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }


        pattern = Pattern.compile("^profile \\s*change\\s* --new\\s* (.+?)\\s* --password\\s* --current\\s* (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            changePassword(matcher.group(2), matcher.group(1));
            return 1;
        }


        pattern = Pattern.compile("^profile \\s*change\\s* --new\\s* (.+?) \\s*--current\\s* (.+?) \\s*--password$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
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


    public static void scoreboardRun() {
        while (true) {
            String command = MainMenuView.getCommand();
            command = command.trim();
            int breaker = scoreboard(command);
            if (breaker == 0) {
                break;
            }
        }
    }

    public static int scoreboard(String command) {


        Pattern pattern = Pattern.compile("^scoreboard \\s*show$");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            showScoreboard();
            return 1;
        }

        pattern = Pattern.compile("^menu\\s* exit$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {

            return 0;
        }
        pattern = Pattern.compile("^menu\\s* enter\\s* (.+?)$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (matcher.group(1).equals("duel") || matcher.group(1).equals("Import/Export") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                MainMenuView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }

        pattern = Pattern.compile("^menu\\s* show-current$");
        matcher = pattern.matcher(command);
        if (matcher.find()) {
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

        int k = 1;
        for (int i = 0; i < keysUsers.length; i++) {
            if (keysUsers[i].equals("AI")) {
                continue;
            }
            MainMenuView.showInput((k) + "_" + UserModel.getUserByUsername(keysUsers[i]).getNickname() + ": " + UserModel.getUserByUsername(keysUsers[i]).getUserScore());
            k++;
        }
    }


    public static void changePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(newPassword)) {
            MainMenuView.showInput("please enter a new password");
            return;
        }
        if (UserModel.getUserByUsername(MainMenuController.username).getPassword().equals(currentPassword)) {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.changePassword(newPassword);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            MainMenuView.showInput("password changed successfully!");
        } else {
            MainMenuView.showInput("current password is invalid");
        }

    }
}