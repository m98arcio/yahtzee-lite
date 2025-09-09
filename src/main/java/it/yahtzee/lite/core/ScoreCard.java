package it.yahtzee.lite.core;

public class ScoreCard {

    public enum Category {
        ONES(1), TWOS(2), THREES(3), FOURS(4), FIVES(5), SIXES(6);
        public final int face;
        Category(int f){ this.face = f; }
    }

    private final boolean[] used = new boolean[6];
    private int upperTotal = 0;

    //Punti ottenibili per una categoria con i dadi correnti
    public int scoreFor(Category c, int[] dice){
        int cnt = 0;
        for (int v : dice) if (v == c.face) cnt++;
        return cnt * c.face;
    }

    //Applica la categoria scelta e aggiorna il totale
    public void use(Category c, int[] dice){
        if (used[c.ordinal()]) throw new IllegalStateException("Categoria già usata: " + c);
        upperTotal += scoreFor(c, dice);
        used[c.ordinal()] = true;
    }

    public boolean isUsed(Category c){ return used[c.ordinal()]; }

    public int categoriesLeft(){
        int left = 0;
        for (boolean u : used) if (!u) left++;
        return left;
    }

    // Totale superiore con eventuale bonus (63 -> +35)
    public int totalWithBonus(){
        int bonus = (upperTotal >= 63) ? 35 : 0;
        return upperTotal + bonus;
    }

    public int getUpperTotal(){ return upperTotal; }
}
