package it.yahtzee.lite.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreCardTest {
    @Test
    void scoreSumIsCorrect() {
        ScoreCard sc = new ScoreCard();
        int s = sc.scoreSum(new int[]{1,2,3,4,5});
        assertEquals(15, s);
    }
}
