package controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class RegisterAndLoginControllerTest {

    @Test
    public void run() {
        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(0, RegisterAndLoginController.run());
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(0, RegisterAndLoginController.run());
    }

    @Test
    public void findMatcher(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RegisterAndLoginController.findMatcher("invalid command  ");
        Assert.assertEquals("invalid command",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user login --username (.+?) --password (.+?)");
        Assert.assertEquals("Username and password didn’t match!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user login --password \\s*(.+?) --username s*(.+?)");
        Assert.assertEquals("Username and password didn’t match!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --username (.+?) --nickname (.+?) --password \\s*(.+?)");
        Assert.assertEquals("user created successfully!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --username a --password a --nickname a ");
        Assert.assertEquals("user created successfully!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --nickname \\s* (.+?)\\s* --username \\s* (.+?)\\s* --password s* (.+?)");
        Assert.assertEquals("user created successfully!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --nickname asd(.+?)   --password (.+?) --username s+(.+?)");
        Assert.assertEquals("user created successfully!",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --password \\s* (.+?)\\s* --username \\s* (.+?)\\s* --nickname jmuyefhghjt\\sdgjhtfdrf* (.+?)");
        Assert.assertEquals("user with username \\s* (.+?)\\s* already exists",outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.findMatcher("user create --password \\s* (.+?)\\s* --nickname \\s* rey(.+?)\\s* --username \\jhsgj* (.+?)");
        Assert.assertEquals("user created successfully!",outContentWithOutEnter(outContent));
    }

    @Test
    public void registerInGame() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RegisterAndLoginController.registerInGame("ali", "n", "p");
        Assert.assertEquals("user created successfully!", outContentWithOutEnter(outContent));/////////////
        outContent.reset();
        RegisterAndLoginController.registerInGame("ali", "nn", "p");
        Assert.assertEquals("user with username " + "ali" + " already exists",  outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.registerInGame("aliii", "n", "p");
        Assert.assertEquals("user with nickname " + "n" + " already exists", outContentWithOutEnter(outContent));
    }

    @Test
    public void loginInGame() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RegisterAndLoginController.registerInGame("reza", "ff", "p");
        outContent.reset();
        RegisterAndLoginController.loginInGame("ali", "pp");
        Assert.assertEquals("Username and password didn’t match!", outContentWithOutEnter(outContent));
        outContent.reset();
        RegisterAndLoginController.loginInGame("alio", "p");
        Assert.assertEquals("Username and password didn’t match!", outContentWithOutEnter(outContent));
        outContent.reset();
        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        RegisterAndLoginController.loginInGame("reza", "p");
        Assert.assertEquals("user logged in successfully!", outContentWithOutEnter(outContent));
    }

    public String outContentWithOutEnter(ByteArrayOutputStream outContent) {
        return outContent.toString().substring(0, outContent.toString().length() - 2);
    }

}