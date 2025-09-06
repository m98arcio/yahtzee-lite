package it.yahtzee.lite.app;

import it.yahtzee.lite.ai.GAEngine;

public class Main {
    public static void main(String[] args) {
        System.out.println("Yahtzee Lite — step 3 (GA minimale, pacchetti riorganizzati)");
        GAEngine ga = new GAEngine();
        GAEngine.Genome best = ga.run();
        System.out.println("Best threshold trovato: " + best.threshold);
    }
}
