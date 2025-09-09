package it.yahtzee.lite.app;

import it.yahtzee.lite.ai.GAEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static Map<String,String> parseArgs(String[] args) {
        Map<String,String> m = new HashMap<>();
        for (String a : args) {
            if (a.startsWith("--") && a.contains("=")) {
                int i = a.indexOf('=');
                m.put(a.substring(2, i), a.substring(i+1));
            }
        }
        return m;
    }

    public static void main(String[] args) {
        Map<String,String> cfg = parseArgs(args);
        long seed = cfg.containsKey("seed")
                ? Long.parseLong(cfg.get("seed"))
                : ThreadLocalRandom.current().nextLong();

        int pop     = Integer.parseInt(cfg.getOrDefault("pop", "100"));
        int gens    = Integer.parseInt(cfg.getOrDefault("gens", "50"));
        int games   = Integer.parseInt(cfg.getOrDefault("games", "100"));

        // STEP 15: parametri GA da CLI (con default sensati)
        int k           = Integer.parseInt(cfg.getOrDefault("k", "4"));
        int elitism     = Integer.parseInt(cfg.getOrDefault("elitism", "3"));
        double mutRate  = Double.parseDouble(cfg.getOrDefault("mutRate", "0.2"));

        System.out.println("Yahtzee Lite — seed=" + seed +
                " pop=" + pop + " gens=" + gens + " games=" + games +
                " k=" + k + " elitism=" + elitism + " mutRate=" + mutRate);

        GAEngine ga = new GAEngine(pop, gens, games, seed, k, elitism, mutRate);
        GAEngine.Genome best = ga.run();
        System.out.println("Best threshold trovato: " + best.threshold);
    }
}
