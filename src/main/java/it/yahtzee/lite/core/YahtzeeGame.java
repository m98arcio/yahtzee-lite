package it.yahtzee.lite.core;

import it.yahtzee.lite.ai.Strategy;

import java.util.Random;

public class YahtzeeGame {
    private final Dice dice;

    public YahtzeeGame(){ this(new Random()); }
    public YahtzeeGame(Random rng){ this.dice = new Dice(rng); }

    /**
     * Gioca l'upper section: 6 round, al termine di ciascun round
     * scegli la categoria migliore tra quelle non ancora usate.
     */
    public int playUpperWith(Strategy strategy){
        ScoreCard sc = new ScoreCard();
        for (int round = 0; round < 6; round++){
            dice.rollAll();
            for(int r=2; r>=1; r--){
                boolean[] keep = strategy.chooseKeep(dice.values(), r);
                dice.reroll(keep);
            }
            // scegli la categoria migliore rimasta
            ScoreCard.Category bestCat = null;
            int bestScore = -1;
            for (ScoreCard.Category c : ScoreCard.Category.values()){
                if (sc.isUsed(c)) continue;
                int s = sc.scoreFor(c, dice.values());
                if (s > bestScore){
                    bestScore = s;
                    bestCat = c;
                }
            }
            sc.use(bestCat, dice.values());
        }
        return sc.totalWithBonus();
    }
}
