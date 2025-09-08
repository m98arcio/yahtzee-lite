package it.yahtzee.lite.ai;

import it.yahtzee.lite.core.YahtzeeGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * STEP 14-16:
 * - cleanup stampe superflue (resta solo logging per generazione)
 * - parametri da CLI: torneo k, elitism, mutRate
 * - mutazione adattiva: se non c'è miglioramento per alcune generazioni, aumenta temporaneamente la mutRate
 */
public class GAEngine {
    public static class Genome { public int threshold; public Genome(int t){ this.threshold=t; } }

    private final int population;
    private final int generations;
    private final int gamesPerEval;
    private final Random rng;

    private final int tournamentK;
    private final int elitism;
    private final double baseMutRate;

    // adattivo
    private int stagnation = 0;
    private double lastGenBest = Double.NEGATIVE_INFINITY;

    public GAEngine(int population, int generations, int gamesPerEval, long seed,
                    int tournamentK, int elitism, double mutRate){
        this.population = population;
        this.generations = generations;
        this.gamesPerEval = Math.max(1, gamesPerEval);
        this.rng = new Random(seed);
        this.tournamentK = Math.max(2, tournamentK);
        this.elitism = Math.max(0, Math.min(elitism, population));
        this.baseMutRate = Math.max(0.0, Math.min(mutRate, 1.0));
    }

    public Genome run(){
        List<Genome> pop = new ArrayList<>();
        for(int i=0;i<population;i++) pop.add(new Genome(1 + rng.nextInt(6)));
        YahtzeeGame game = new YahtzeeGame(rng);
        Genome best = pop.get(0);
        double overallBestFit = -1;

        // CSV (aggregato)
        File outDir = new File("runs");
        if (!outDir.exists()) outDir.mkdirs();
        try (PrintWriter csv = new PrintWriter(new FileWriter(new File(outDir, "fitness.csv")))) {
            csv.println("gen,best,avg");

            for(int g=0; g<generations; g++){
                double[] fit = new double[population];
                for(int i=0;i<population;i++){
                    fit[i] = eval(game, pop.get(i));
                    if(fit[i] > overallBestFit){ overallBestFit = fit[i]; best = pop.get(i); }
                }

                double genBest = -1, sum = 0;
                for (double f : fit) { if (f > genBest) genBest = f; sum += f; }
                double avg = sum / fit.length;
                System.out.printf("Gen %3d | best=%.2f | avg=%.2f%n", g, genBest, avg);
                csv.printf("%d,%.2f,%.2f%n", g, genBest, avg);

                // mutazione adattiva (STEP 16): se nessun miglioramento, aumentiamo temporaneamente
                double currentMutRate = baseMutRate;
                if (genBest > lastGenBest + 1e-9) { // miglioramento
                    stagnation = 0;
                    lastGenBest = genBest;
                } else {
                    stagnation++;
                    // aumenta del 50% ogni generazione senza progresso, max 0.9
                    currentMutRate = Math.min(0.9, baseMutRate * (1.0 + 0.5 * stagnation));
                }

                // nuova popolazione con elitismo + torneo
                List<Genome> next = new ArrayList<>();
                int elitesToCopy = Math.min(elitism, population);
                boolean[] used = new boolean[population];
                for (int e = 0; e < elitesToCopy; e++) {
                    int bestIdx = -1;
                    double bestFit = Double.NEGATIVE_INFINITY;
                    for (int i = 0; i < population; i++) {
                        if (!used[i] && fit[i] > bestFit) { bestFit = fit[i]; bestIdx = i; }
                    }
                    used[bestIdx] = true;
                    next.add(new Genome(pop.get(bestIdx).threshold));
                }
                while(next.size() < population){
                    Genome p1 = tournament(pop, fit);
                    Genome p2 = tournament(pop, fit);
                    int childT = rng.nextBoolean() ? p1.threshold : p2.threshold;
                    if(rng.nextDouble() < currentMutRate) {
                        childT = mutateThreshold(childT);
                    }
                    next.add(new Genome(childT));
                }
                pop = next;
            }
        } catch (IOException e) {
            System.err.println("Errore scrittura CSV: " + e.getMessage());
        }

        return best;
    }

    private int mutateThreshold(int t){
        t += rng.nextBoolean() ? 1 : -1;
        if (t < 1) t = 1;
        if (t > 6) t = 6;
        return t;
    }

    private double eval(YahtzeeGame game, Genome g){
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
