package main.java.view;

import main.java.controller.DeckController;
import main.java.view.DeckView;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class DeckViewTest {


    @Test
    public void getCommand() {

    }

    @Test
    public void showInput() {
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        DeckView.showInput("expected");
        assertEquals("expected", show.toString().substring(0, show.toString().length() - 2));
    }

}