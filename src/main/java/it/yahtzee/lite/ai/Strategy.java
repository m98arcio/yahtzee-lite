package it.yahtzee.lite.ai;

/** Strategia per decidere quali dadi tenere. */
public interface Strategy {
    boolean[] chooseKeep(int[] dice, int rollsLeft);
}
