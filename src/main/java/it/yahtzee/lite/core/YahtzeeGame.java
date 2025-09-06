package it.yahtzee.lite.core;

import it.yahtzee.lite.ai.RandomStrategy;
import it.yahtzee.lite.ai.Strategy;

import java.util.Random;

public class YahtzeeGame {
    private final Dice dice;
    private final ScoreCard score = new ScoreCard();

    public YahtzeeGame(){ this(new Random()); }
    public YahtzeeGame(Random rng){ this.dice = new Dice(rng); }

    public int playRandom(){ return playWith(new RandomStrategy()); }

    public int playWith(Strategy strategy){
        dice.rollAll();
        for(int r=2;r>=1;r--){
            boolean[] keep = strategy.chooseKeep(dice.values(), r);
            dice.reroll(keep);
        }
        return score.scoreSum(dice.values());
    }
}
