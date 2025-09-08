package it.yahtzee.lite.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GAEngineSmokeTest {
    @Test
    void runDoesNotThrow() {
        // nuova firma: GAEngine(pop, gens, games, seed, k, elitism, mutRate)
        GAEngine ga = new GAEngine(6, 2, 2, 123L, 4, 2, 0.2);
        GAEngine.Genome best = ga.run();
        assertNotNull(best);
        assertTrue(best.threshold >= 1 && best.threshold <= 6);
    }
}
