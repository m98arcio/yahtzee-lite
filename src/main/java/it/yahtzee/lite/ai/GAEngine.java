package it.yahtzee.lite.ai;

import it.yahtzee.lite.core.YahtzeeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GA con seed e fitness configurabile.
 * In STEP 6 aggiungiamo logging console: per ogni generazione stampiamo best e avg.
 */
public class GAEngine {
    public static class Genome { public int threshold; public Genome(int t){ this.threshold=t; } }

    private final int population;
    private final int generations;
    private final int gamesPerEval;
    private final Random rng;

    public GAEngine(int population, int generations, int gamesPerEval, long seed){
        this.population = population;
        this.generations = generations;
        this.gamesPerEval = Math.max(1, gamesPerEval);
        this.rng = new Random(seed);
    }

    public Genome run(){
        List<Genome> pop = new ArrayList<>();
        for(int i=0;i<population;i++) pop.add(new Genome(1 + rng.nextInt(6)));
        YahtzeeGame game = new YahtzeeGame(rng);
        Genome best = pop.get(0);
        double overallBestFit = -1;

        for(int g=0; g<generations; g++){
            double[] fit = new double[population];
            for(int i=0;i<population;i++){
                fit[i] = eval(game, pop.get(i));
                if(fit[i] > overallBestFit){ overallBestFit = fit[i]; best = pop.get(i); }
            }
            // logging per generazione: best e average
            double genBest = -1, sum = 0;
            for (double f : fit) { if (f > genBest) genBest = f; sum += f; }
            double avg = sum / fit.length;
            System.out.printf("Gen %3d | best=%.2f | avg=%.2f%n", g, genBest, avg);

            // nuova popolazione via torneo k=2 + crossover uniforme
            List<Genome> next = new ArrayList<>();
            while(next.size()<population){
                Genome p1 = tournament(pop, fit);
                Genome p2 = tournament(pop, fit);
                int childT = rng.nextBoolean() ? p1.threshold : p2.threshold;
                if(rng.nextDouble() < 0.2) childT += rng.nextBoolean() ? 1 : -1; // mutazione
                childT = Math.max(1, Math.min(6, childT));
                next.add(new Genome(childT));
            }
            pop = next;
        }
        return best;
    }

    private double eval(YahtzeeGame game, Genome g){
        // strategia: tieni i dadi >= threshold
        Strategy s = (dice, rollsLeft)->{
            boolean[] keep = new boolean[5];
            for(int i=0;i<5;i++) keep[i] = dice[i] >= g.threshold;
            return keep;
        };
        double sum = 0;
        for(int i=0;i<gamesPerEval;i++) sum += game.playWith(s);
        return sum / gamesPerEval;
    }

    private Genome tournament(List<Genome> pop, double[] fit){
        int i = rng.nextInt(pop.size());
        int j = rng.nextInt(pop.size());
        return fit[i] >= fit[j] ? pop.get(i) : pop.get(j);
    }
}
