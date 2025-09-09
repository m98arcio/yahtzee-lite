package it.yahtzee.lite.ai;

// Strategia dummy: tiene sempre tutti i dadi (nessun reroll)
public class AlwaysKeepAllStrategy implements Strategy {
    @Override
    public boolean[] chooseKeep(int[] dice, int rollsLeft) {
        return new boolean[]{true, true, true, true, true};
    }
}

