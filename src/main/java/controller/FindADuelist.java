package controller;

import model.UserModel;
import java.util.*;


public class FindADuelist {

    public static HashMap<String, Integer> waitingPlayerToken = new HashMap<>();
    public static HashMap<String, String> matchedPlayer = new HashMap<>();
    public static ArrayList<String> oneRoundPlayer = new ArrayList<>();
    public static ArrayList<String> oneRoundPlayerTwo = new ArrayList<>();
    public static ArrayList<String> threeRoundPlayer = new ArrayList<>();
    public static final LinkedList<String> linkedList = new LinkedList<>();

//    public ArrayList<String> requested=new ArrayList<>();
//    public String find(String token){
//        requested.add(Run.userByToken(token));
//
//    }

    public static synchronized ArrayList<Object> run(String onlineToken, int roundNumber) {
        if (waitingPlayerToken.size() == 1 && waitingPlayerToken.containsKey(onlineToken)) {
            return null;
        }
        else {
            int size = waitingPlayerToken.size();
            Random random = new Random();
            String result;
            int counter = 0;
            int randNum = random.nextInt(size);
            for (Map.Entry<String, Integer> eachPlayer : waitingPlayerToken.entrySet()) {
                if (counter == randNum) {
                    if (!eachPlayer.getKey().equals(onlineToken) && roundNumber == eachPlayer.getValue()) {
                        result = eachPlayer.getKey();
                        waitingPlayerToken.remove(result);
                        waitingPlayerToken.remove(onlineToken);
                        matchedPlayer.put(onlineToken, result);
                        ArrayList<Object> allPlayer = new ArrayList<>();
                        UserModel.getUserByUsername(Run.userByToken(result)).setRivalToken(onlineToken);
                        UserModel.getUserByUsername(Run.userByToken(onlineToken)).setRivalToken(result);
                        allPlayer.add(UserModel.getUserByUsername(Run.userByToken(onlineToken)));
                        allPlayer.add(UserModel.getUserByUsername(Run.userByToken(result)));
                        allPlayer.add(onlineToken);
                        allPlayer.add(result);
                        System.out.println(onlineToken + "online");
                        System.out.println(result + "rival");
                        System.out.println(allPlayer.size());
                        return allPlayer;
                    }
                }
                counter++;
            }
        }
        return null;
    }

    public static void addToList(String token) {
        synchronized (linkedList) {
            linkedList.addLast(token);

            linkedList.notify();
        }
    }


    public static synchronized void searchForOneRound(String onlineToken) {
        ArrayList<Object> allPlayer = new ArrayList<>();
        String take;
        while (true) {
            synchronized (linkedList) {
                System.out.println(linkedList.size());
                while (linkedList.size() == 1) {
                    try {
                        linkedList.wait();
                    } catch (Exception ignored) {
                    }
                }
                take = linkedList.removeLast();
                linkedList.remove(onlineToken);
                allPlayer.add(onlineToken);
                allPlayer.add(take);
                matchedPlayer.put(onlineToken, take);
                return;
            }
        }
//        while (allPlayer.isEmpty()) {
//            try {
//                take = BQ.take();
//                if (take.equals(onlineToken))
//                    BQ.add(take);
//                else {
//                    allPlayer.add(onlineToken);
//                    allPlayer.add(take);
//                    BQ.remove(onlineToken);
//                    System.out.println(take);
//                    System.out.println(onlineToken);
//                }
//            } catch (Exception ignored) {
//            }
//        }
//        return allPlayer;
    }

    public static int getIndex(ArrayList<String> arrayList, String wanted) {
        for (int i = 0; i < arrayList.size(); i++)
            if (arrayList.get(i).equals(wanted))
                return i;
        return -1;
    }


    public static synchronized ArrayList<Object> searchForThreeRound(String onlineToken) {
        ArrayList<Object> allPlayer = new ArrayList<>();
        if (threeRoundPlayer.size() == 1)
            return null;
        else {
            int size = threeRoundPlayer.size();
            Random random = new Random();
            int index = getIndex(threeRoundPlayer, onlineToken);
            int whichOne = index;
            while(whichOne == index) {
                whichOne =  random.nextInt(size);
            }
            String rivalToken;
            for (int i = 0; i < size; i++) {
                if (whichOne == i) {
                    rivalToken = threeRoundPlayer.get(i);
                    threeRoundPlayer.remove(rivalToken);
                    threeRoundPlayer.remove(onlineToken);
                    UserModel.getUserByUsername(Run.userByToken(rivalToken)).setRivalToken(onlineToken);
                    UserModel.getUserByUsername(Run.userByToken(onlineToken)).setRivalToken(rivalToken);
                    allPlayer.add(UserModel.getUserByUsername(Run.userByToken(onlineToken)));
                    allPlayer.add(UserModel.getUserByUsername(Run.userByToken(rivalToken)));
                    allPlayer.add(onlineToken);
                    allPlayer.add(rivalToken);
                    System.out.println(onlineToken + "online");////////
                    System.out.println(rivalToken + "rival");///////////
                    System.out.println(allPlayer.size());
                    return allPlayer;
                }
            }
        }
        return null;
    }


}
