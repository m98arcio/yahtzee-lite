package it.yahtzee.lite.core;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private final int[] values = new int[5];

    public void rollAll(){
        for(int i=0;i<values.length;i++){
            values[i] = ThreadLocalRandom.current().nextInt(1,7);
        }
    }
    public void reroll(boolean[] keep){
        if(keep==null||keep.length!=5) throw new IllegalArgumentException("keep deve essere lungo 5");
        for(int i=0;i<values.length;i++){
            if(!keep[i]) values[i] = ThreadLocalRandom.current().nextInt(1,7);
        }
    }
    public int[] values(){ return Arrays.copyOf(values, values.length); }
    @Override public String toString(){ return Arrays.toString(values); }
}
