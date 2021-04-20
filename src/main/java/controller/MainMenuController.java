package main.java.controller;

import main.java.model.DeckModel;
import main.java.model.UserModel;
import main.java.view.MainMenuView;
import main.java.view.RegisterAndLoginView;

import java.util.*;
import java.lang.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuController {

    public static String username;

    public static void findMatcher() {
        while (true) {
            String command;
            command = MainMenuView.getCommand();

            Pattern pattern = Pattern.compile("^menu enter (.+?)>$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel")) {

                } else if (matcher.group(1).equals("deck")) {
                    DeckController.findMatcher();
                    break;
                } else if (matcher.group(1).equals("scoreboard")) {
                    scoreboard();
                    break;

                } else if (matcher.group(1).equals("profile")) {

                } else if (matcher.group(1).equals("shop")) {
                    ShopController.findMatcher();
                    break;
                } else {
                    MainMenuView.showInput("invalid command");
                    continue;
                }
            }
            pattern = Pattern.compile("^menu show-current$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                RegisterAndLoginView.showInput("Main Menu");
            }

        }
    }


    private static void scoreboard() {
        while (true) {
            String command = MainMenuView.getCommand();
            Pattern pattern = Pattern.compile("^scoreboard show$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                showScoreboard();
                break;
            }

            pattern = Pattern.compile("^menu exit$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                findMatcher();
                break;
            }


        }
    }


    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    private static void showScoreboard() {

        HashMap<String, Integer> hm = new HashMap<String, Integer>();

        // enter data into hashmap
        for (int i = 0; i < UserModel.allUsernames.size(); i++) {
            hm.put(UserModel.allUsernames.get(i), UserModel.getUserByUsername(UserModel.allUsernames.get(i)).getUserScore() * (-1));
        }
        Map<String, Integer> hm1 = sortByValue(hm);

        // print the sorted hashmap
        int counter = 1;
        for (Map.Entry<String, Integer> en : hm1.entrySet()) {
            MainMenuView.showInput(counter + "_" + en.getKey() + ": " + en.getValue());
            counter++;
        }
    }


    private static void enterMenu(String menu) {

    }


//    public final String chooseFirstPlayer(String rivalName)
//    {
//
//    }
//
//
//
//
//
//    private void logout()
//    {
//
//    }
//
//
//    private static void scoreboard()
//    {
//
//    }
//
//
//    private static void profile()
//    {
//
//    }
//}

}
