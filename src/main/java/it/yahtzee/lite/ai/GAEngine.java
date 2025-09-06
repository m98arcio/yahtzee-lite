package it.yahtzee.lite.ai;

import it.yahtzee.lite.core.YahtzeeGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GAEngine {
    public static class Genome { public int threshold; public Genome(int t){ this.threshold=t; } }
    private final int population = 20;
    private final int generations = 10;

    public Genome run(){
        List<Genome> pop = new ArrayList<>();
        for(int i=0;i<population;i++) pop.add(new Genome(1+ThreadLocalRandom.current().nextInt(6)));
        YahtzeeGame game = new YahtzeeGame();
        Genome best = pop.get(0); int bestFit=-1;
        for(int g=0; g<generations; g++){
            int[] fit = new int[population];
            for(int i=0;i<population;i++){
                fit[i] = eval(game, pop.get(i));
                if(fit[i]>bestFit){ bestFit=fit[i]; best=pop.get(i); }
            }
            List<Genome> next = new ArrayList<>();
            while(next.size()<population){
                Genome p1 = tournament(pop, fit);
                Genome p2 = tournament(pop, fit);
                int childT = ThreadLocalRandom.current().nextBoolean()? p1.threshold:p2.threshold;
                if(ThreadLocalRandom.current().nextDouble()<0.2)
                    childT += ThreadLocalRandom.current().nextBoolean()?1:-1;
                childT = Math.max(1, Math.min(6, childT));
                next.add(new Genome(childT));
            }
            pop = next;
            System.out.printf("Gen %d  bestFit=%d  bestThreshold=%d%n", g, bestFit, best.threshold);
        }
        return best;
    }

    private int eval(YahtzeeGame game, Genome g){
        Strategy s = (dice, rollsLeft)->{
            boolean[] keep = new boolean[5];
            for(int i=0;i<5;i++) keep[i] = dice[i] >= g.threshold;
            return keep;
        };
        return game.playWith(s);
    }

    private Genome tournament(List<Genome> pop, int[] fit){
        int i=ThreadLocalRandom.current().nextInt(pop.size());
        int j=ThreadLocalRandom.current().nextInt(pop.size());
        return fit[i]>=fit[j]?pop.get(i):pop.get(j);
    }
}
