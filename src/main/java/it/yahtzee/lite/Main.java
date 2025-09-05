package it.yahtzee.lite;

public class Main {
    public static void main(String[] args) {
        System.out.println("Yahtzee Lite — step 2 (partita casuale)");
        YahtzeeGame game = new YahtzeeGame();
        int score = game.playRandom();
        System.out.println("Punteggio finale (somma facce): " + score);
    }
}
