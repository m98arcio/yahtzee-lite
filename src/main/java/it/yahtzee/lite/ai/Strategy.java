package it.yahtzee.lite.ai;

public interface Strategy {
    boolean[] chooseKeep(int[] dice, int rollsLeft);
}
