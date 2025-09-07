package it.yahtzee.lite.ai;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GAEngineSmokeTest {
    @Test
    void runDoesNotThrow() {
        GAEngine ga = new GAEngine(6, 2, 2, 123L);
        GAEngine.Genome best = ga.run();
        assertNotNull(best);
        assertTrue(best.threshold >= 1 && best.threshold <= 6);
    }
}
