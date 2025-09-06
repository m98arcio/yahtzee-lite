package it.yahtzee.lite;

public class YahtzeeGame {
    private final Dice dice = new Dice();
    private final ScoreCard score = new ScoreCard();
    public int playRandom(){ return playWith(new RandomStrategy()); }
    public int playWith(Strategy strategy){
        dice.rollAll(); for(int r=2;r>=1;r--){ boolean[] keep = strategy.chooseKeep(dice.values(), r); dice.reroll(keep);} return score.scoreSum(dice.values()); }
}
