package view;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MainMenuViewTest {

    @Test
    public void getCommand() {
    }

    @Test
    public void showInput() {
        ByteArrayOutputStream show =new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        MainMenuView.showInput("expected");
        assertEquals("expected",show.toString().substring(0,show.toString().length()-2));
    }
}