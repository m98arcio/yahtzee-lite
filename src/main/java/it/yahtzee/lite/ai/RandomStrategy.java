package it.yahtzee.lite.ai;

import java.util.concurrent.ThreadLocalRandom;

//Strategia baseline: sceglie casualmente quali dadi tenere
public class RandomStrategy implements Strategy {
    @Override
    public boolean[] chooseKeep(int[] dice, int rollsLeft) {
        boolean[] keep = new boolean[5];
        for (int i = 0; i < 5; i++) keep[i] = ThreadLocalRandom.current().nextBoolean();
        return keep;
    }
}
