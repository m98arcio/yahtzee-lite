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
                : java.util.concurrent.ThreadLocalRandom.current().nextLong();
        System.out.println("Yahtzee Lite — GA con seed = " + seed);

        int pop = Integer.parseInt(cfg.getOrDefault("pop", "20"));
        int gens = Integer.parseInt(cfg.getOrDefault("gens", "10"));
        // STEP 5: fitness ancora debole ma un po' meno rumorosa → 3 partite di default
        int games = Integer.parseInt(cfg.getOrDefault("games", "3"));

        GAEngine ga = new GAEngine(pop, gens, games, seed);
        GAEngine.Genome best = ga.run();
        System.out.println("Best threshold trovato: " + best.threshold);
    }
}
