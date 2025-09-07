package it.yahtzee.lite.core;

public class ScoreCard {
    public int scoreSum(int[] dice){
        int s=0; for(int v: dice) s+=v; return s;
    }
}
