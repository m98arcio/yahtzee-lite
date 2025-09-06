package it.yahtzee.lite;

public class Main { public static void main(String[] args){ System.out.println("Yahtzee Lite — step 3 (GA minimale)"); GAEngine ga = new GAEngine(); GAEngine.Genome best = ga.run(); System.out.println("Best threshold trovato: "+best.threshold);} }
