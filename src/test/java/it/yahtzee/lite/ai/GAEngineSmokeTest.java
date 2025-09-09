package it.yahtzee.lite.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GAEngineSmokeTest {
    @Test
    void runDoesNotThrow() {
        // nuova firma: GAEngine(pop, gens, games, seed, k, elitism, mutRate, bestRuns)
        GAEngine ga = new GAEngine(
                6,    // population (piccolo per test veloce)
                1,    // generations
                1,    // gamesPerEval
                123L, // seed
                4,    // k (tournament)
                1,    // elitism
                0.1,  // mutRate
                3     // bestRuns (rigioca 3 partite per il report finale)
        );
        GAEngine.Genome best = ga.run();
        assertNotNull(best);
        assertTrue(best.threshold >= 1 && best.threshold <= 6);
    }
}