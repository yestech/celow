package org.yestech.celow.core;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 *
 */
public class EngineTest {
    Engine engine;

    @Before
    public void setUp() {
        engine = new Engine();
    }

    @Ignore
    public void testReset() {
//        engine.rollDice();
    }

    @Test
    public void testRoll() {
        for (int i = 0; i < 1000; i++) {
            engine.rollDice();
            assertTrue(engine.getDice1() <= 6);
            assertTrue(engine.getDice1() >= 1);
            assertTrue(engine.getDice2() <= 6);
            assertTrue(engine.getDice2() >= 1);
            assertTrue(engine.getDice3() <= 6);
            assertTrue(engine.getDice3() >= 1);
            assertNotNull(engine.getResult());
        }
    }
}
