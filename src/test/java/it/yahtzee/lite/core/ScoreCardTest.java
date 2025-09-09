package it.yahtzee.lite.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreCardTest {

    @Test
    void scoreForCategoryWorks() {
        ScoreCard sc = new ScoreCard();
        int[] dice = {1, 2, 3, 4, 5};

        assertEquals(1, sc.scoreFor(ScoreCard.Category.ONES, dice));
        assertEquals(2, sc.scoreFor(ScoreCard.Category.TWOS, dice));
        assertEquals(3, sc.scoreFor(ScoreCard.Category.THREES, dice));
        assertEquals(4, sc.scoreFor(ScoreCard.Category.FOURS, dice));
        assertEquals(5, sc.scoreFor(ScoreCard.Category.FIVES, dice));
        assertEquals(0, sc.scoreFor(ScoreCard.Category.SIXES, dice));
    }
}
