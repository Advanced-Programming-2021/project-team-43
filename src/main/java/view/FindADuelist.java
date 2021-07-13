package view;

import controller.GameMatController;
import controller.MainMenuController;

public class FindADuelist {

    private static boolean isDone;
    private static String rivalToken;
    private static String onlineToken;


    public static int run(int roundNumber) {
        new Thread(() -> {
            while (!isDone) {
                try {
                    System.out.println("first");
                    RegisterAndLoginView.dataOutputStream.writeUTF("findADuelist/" + MainMenuController.token + "/" + 1);
                    String result = RegisterAndLoginView.dataInputStream.readUTF();
                    System.out.println("last");
                    System.out.println("kkkkkkkkkkkkkkkkkk" + result);
                    if (!result.equals("no")) {
                        rivalToken = result;
                        setDone(true);
                    }
                } catch (Exception ignored) {
                }
            }

        }).start();
        return 1;
    }

    public static void setDone(boolean done) {
        System.out.println("done");
        System.out.println(rivalToken + "rival");
        isDone = done;
        Thread.currentThread().interrupt();
    }

}
