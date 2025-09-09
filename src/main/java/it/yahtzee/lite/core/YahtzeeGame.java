package it.yahtzee.lite.core;

import it.yahtzee.lite.ai.Strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class YahtzeeGame {
    private final Dice dice;

    public YahtzeeGame() {
        this(new Random());
    }

    public YahtzeeGame(Random rng) {
        this.dice = new Dice(rng);
    }

    // === Log strutturato di una partita (upper section) ===
    public static class RoundLog {
        public final ScoreCard.Category category;
        public final int[] dice;
        public final int roundScore;

        public RoundLog(ScoreCard.Category category, int[] dice, int roundScore) {
            this.category = category;
            this.dice = dice;
            this.roundScore = roundScore;
        }
    }

    public static class MatchLog {
        public final List<RoundLog> rounds = new ArrayList<>();
        public final int upperTotal; // somma delle 6 categorie (senza bonus)
        public final boolean bonus;  // true se upperTotal >= 63 (info, non usata nel punteggio restituito)

        public MatchLog(List<RoundLog> rounds, int upperTotal, boolean bonus) {
            this.rounds.addAll(rounds);
            this.upperTotal = upperTotal;
            this.bonus = bonus;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rounds.size(); i++) {
                RoundLog r = rounds.get(i);
                sb.append(String.format("  Round %d: cat=%s  score=%d  dice=%s%n", i + 1, r.category, r.roundScore, Arrays.toString(r.dice)));
            }
            sb.append(String.format("  Totale upper=%d%n", upperTotal)); // niente bonus in stampa
            return sb.toString();
        }
    }

    /**
     * Gioca l'upper section con la strategia fornita.
     * Simula 6 round, ognuno con 3 roll di dadi, e sceglie la categoria migliore.
     * @param strategy strategia che decide quali dadi tenere ad ogni roll
     * @return log della partita con i dettagli dei round e il punteggio totale (upper section)
     */

    public MatchLog playUpperWithLog(Strategy strategy) {
        ScoreCard sc = new ScoreCard();
        List<RoundLog> logs = new ArrayList<>();

        for (int round = 0; round < 6; round++) {
            dice.rollAll();
            // due reroll (totale 3 lanci): la strategia decide cosa tenere
            for (int r = 2; r >= 1; r--) {
                boolean[] keep = strategy.chooseKeep(dice.values(), r);
                dice.reroll(keep);
            }
            // scegli la miglior categoria rimasta (greedy)
            ScoreCard.Category bestCat = null;
            int bestScore = -1;
            for (ScoreCard.Category c : ScoreCard.Category.values()) {
                if (sc.isUsed(c)) continue;
                int s = sc.scoreFor(c, dice.values());
                if (s > bestScore) {
                    bestScore = s;
                    bestCat = c;
                }
            }
            sc.use(bestCat, dice.values());
            logs.add(new RoundLog(bestCat, dice.values(), bestScore));
        }
        int upper = sc.getUpperTotal();
        boolean gotBonus = upper >= 63;  // info, non incide sul return
        return new MatchLog(logs, upper, gotBonus);
    }

    public int playUpperWith(Strategy strategy) {
        MatchLog log = playUpperWithLog(strategy);
        return log.upperTotal;  // niente bonus nel punteggio restituito
    }
}