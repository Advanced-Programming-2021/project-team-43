package main.java.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhaseTest {

    @Test
    public void values() {
        assertEquals("Draw_Phase",Phase.values()[0].toString());
    }

}