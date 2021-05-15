package test.java;
import main.java.controller.RegisterAndLoginController;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class RegisterAndLoginControllerTest {

    @Test
    public void registerInGame() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RegisterAndLoginController.registerInGame("ali", "n", "p");
        Assert.assertEquals("user created successfully!", outContent.toString().substring(0, outContent.toString().length() - 2));
        outContent.reset();
        RegisterAndLoginController.registerInGame("ali", "nn", "p");
        Assert.assertEquals("user with username " + "ali" + " already exists", outContent.toString().substring(0, outContent.toString().length() - 2));

        outContent.reset();
        RegisterAndLoginController.registerInGame("aliii", "n", "p");
        Assert.assertEquals("user with nickname " + "n" + " already exists", outContent.toString().substring(0, outContent.toString().length() - 2));
    }

    @Test
    public void loginInGame() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        RegisterAndLoginController.registerInGame("reza", "ff", "p");
        outContent.reset();

        RegisterAndLoginController.loginInGame("ali", "pp");
        Assert.assertEquals("Username and password didn’t match!", outContent.toString().substring(0, outContent.toString().length() - 2));

        outContent.reset();
        RegisterAndLoginController.loginInGame("alio", "p");
        Assert.assertEquals("Username and password didn’t match!", outContent.toString().substring(0, outContent.toString().length() - 2));

    }
}