package it.yahtzee.lite;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DiceTest {
    @Test
    void rollProducesValuesInRange() {
        Dice d = new Dice(new Random(1));
        d.rollAll();
        for (int v : d.values()) {
            assertTrue(v >= 1 && v <= 6, "dado fuori range: " + v);
        }
    }
}
