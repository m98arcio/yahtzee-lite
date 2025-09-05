package it.yahtzee.lite;

import java.util.concurrent.ThreadLocalRandom;

//Gioca una partita.

public class YahtzeeGame {
    private final Dice dice = new Dice();
    private final ScoreCard score = new ScoreCard();

    public int playRandom() {
        dice.rollAll();
        for (int r = 0; r < 2; r++) {
            boolean[] keep = new boolean[5];
            for (int i = 0; i < 5; i++) {
                keep[i] = ThreadLocalRandom.current().nextBoolean();
            }
            dice.reroll(keep);
        }
        return score.scoreSum(dice.values());
    }
}
