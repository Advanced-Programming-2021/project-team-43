package test.java;

import main.java.model.Phase;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhaseTest {

    @Test
    public void values() {
        Assert.assertEquals("Draw_Phase", Phase.values()[0].toString());
    }

}