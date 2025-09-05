package it.yahtzee.lite;

/** Punteggio: somma delle facce. */
public class ScoreCard {
    public int scoreSum(int[] dice) {
        int s = 0;
        for (int v : dice) s += v;
        return s;
    }
}
