package it.yahtzee.lite.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {
    @Test
    void rollValuesAreWithinRange() {
        Dice d = new Dice();
        d.rollAll();
        for (int v : d.values()) {
            assertTrue(v >= 1 && v <= 6);
        }
    }
}
