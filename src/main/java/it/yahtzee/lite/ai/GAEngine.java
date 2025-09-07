package it.yahtzee.lite.ai;

import it.yahtzee.lite.core.YahtzeeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GAEngine {
    public static class Genome { public int threshold; public Genome(int t){ this.threshold=t; } }

    private final int population;
    private final int generations;
    private final int gamesPerEval;
    private final Random rng;

    //torneo più selettivo
    private final int tournamentK = 4;
    //ELITISMO -> numero di migliori da copiare direttamente nella generazione successiva
    private final int elitism = 2;

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
            // --- valutazione ---
            double[] fit = new double[population];
            for(int i=0;i<population;i++){
                fit[i] = eval(game, pop.get(i));
                if(fit[i] > overallBestFit){ overallBestFit = fit[i]; best = pop.get(i); }
            }

            // logging per generazione
            double genBest = -1, sum = 0;
            for (double f : fit) { if (f > genBest) genBest = f; sum += f; }
            double avg = sum / fit.length;
            System.out.printf("Gen %3d | best=%.2f | avg=%.2f%n", g, genBest, avg);

            // --- nuova popolazione ---
            List<Genome> next = new ArrayList<>();

            //ELITISMO: copia i migliori 'elitism' individui così come sono
            int elitesToCopy = Math.min(elitism, population);
            boolean[] used = new boolean[population];
            for (int e = 0; e < elitesToCopy; e++) {
                int bestIdx = -1;
                double bestFit = Double.NEGATIVE_INFINITY;
                for (int i = 0; i < population; i++) {
                    if (!used[i] && fit[i] > bestFit) { bestFit = fit[i]; bestIdx = i; }
                }
                used[bestIdx] = true;
                // copia "profonda" (nuovo oggetto Genome con stesso threshold)
                next.add(new Genome(pop.get(bestIdx).threshold));
            }

            //RIEMPIMENTO: torneo k=4, crossover uniforme (sul singolo gene) + mutazione
            while(next.size() < population){
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

    // torneo k=4: scegli il migliore tra k candidati
    private Genome tournament(List<Genome> pop, double[] fit){
        int bestIdx = rng.nextInt(pop.size());
        double bestFit = fit[bestIdx];
        for (int c = 1; c < tournamentK; c++) {
            int idx = rng.nextInt(pop.size());
            if (fit[idx] > bestFit) {
                bestIdx = idx;
                bestFit = fit[idx];
            }
        }
        return pop.get(bestIdx);
    }
}