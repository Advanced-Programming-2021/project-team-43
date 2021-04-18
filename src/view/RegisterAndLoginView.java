package view;

import controller.*;
import model.*;

import java.util.Scanner;

class RegisterAndLoginView {


    public final String getCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public final void showInput(String input) {
        System.out.println(input);

    }
}
