package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FindADuelist {

    public static HashMap<String, Integer> waitingPlayerToken = new HashMap<>();
    public static DataInputStream lobbyDataInputStream;
    public static DataOutputStream lobbyDataOutputStream;
    public static HashMap<String, String> matchedPlayer = new HashMap<>();



    public static void run(String onlineToken, int roundNumber) {
        new Thread(() -> {
            if (waitingPlayerToken.isEmpty() || (waitingPlayerToken.size() == 1 && waitingPlayerToken.containsKey(onlineToken))) {
                matchedPlayer.put(onlineToken, null);
                Thread.currentThread().interrupt();
            }
            else {
                int size = waitingPlayerToken.size();
                Random random = new Random();
                String result;
                while (true) {
                    int counter = 0;
                    int randNum = random.nextInt(size);
                    for (Map.Entry<String, Integer> eachPlayer : waitingPlayerToken.entrySet()) {
                        if (counter == randNum) {
                            if (!eachPlayer.getKey().equals(onlineToken) && roundNumber == eachPlayer.getValue()) {
                                result = eachPlayer.getKey();
                                System.out.println(result);
                                waitingPlayerToken.remove(result);
                                waitingPlayerToken.remove(onlineToken);
                                matchedPlayer.put(onlineToken, result);
                                Thread.currentThread().interrupt();
                            }
                        }
                        counter++;
                    }
                }
            }


        }).start();
    }

    public static void addWaitingPlayer(String token, int round) {
        waitingPlayerToken.put(token, round);
    }

    public static void removeWaitingPlayer(String token) {
        waitingPlayerToken.remove(token);
    }

}
