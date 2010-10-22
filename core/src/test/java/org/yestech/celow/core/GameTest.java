package org.yestech.celow.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    Game game;

    @Mock
    private IPlayer mockPlayer;

    @Mock
    private IEngine mockEngine;
    
    @Before
    public void setUp() {
        game = new Game();
    }

    @Test(expected = RuntimeException.class)
    public void testAtleastOnePlayerRegistered() {
        game.rollDice();
    }

    @Test
    public void testInitialRollPoint() {
        doNothing().when(mockEngine).rollDice();
        when(mockEngine.getDice1()).thenReturn(2);
        when(mockEngine.getDice2()).thenReturn(2);
        when(mockEngine.getDice3()).thenReturn(5);
        game.addPlayer(mockPlayer);
        game.setEngine(mockEngine);
        game.rollDice();
        verify(mockEngine).rollDice();
        verify(mockEngine).getDice1();
        verify(mockEngine).getDice2();
        verify(mockEngine).getDice3();
    }

    @Test
    public void testIntialState() {
        assertTrue(game.isInitialRoll());
    }

    @Test
    public void testPointInEffectInitialLoad() {
        assertFalse(game.isPointInEffect());
        assertTrue(game.isInitialRoll());
    }

    @Test
    public void testPointInEffectAfterClearPoint() {
        game.clearPoint();
        assertFalse(game.isPointInEffect());
        assertTrue(game.isInitialRoll());
    }

    @Test
    public void testPointInEffectSetPointZero() {
        game.setInitialRoll(false);
        game.setPlayerWithPointIdx(0);
        assertTrue(game.isPointInEffect());
        assertFalse(game.isInitialRoll());
    }

    @Test
    public void testPointInEffectSetPointGreaterThanZero() {
        game.setInitialRoll(false);
        game.setPlayerWithPointIdx(9);
        assertTrue(game.isPointInEffect());
        assertFalse(game.isInitialRoll());
    }

    @Test
    public void testClearPointNotInPointState() {
        game.clearPoint();
        assertFalse(game.isPointInEffect());
        assertTrue(game.isInitialRoll());
    }

    @Test
    public void testClearPointInPointState() {
        ArrayList<IPlayer> list = new ArrayList<IPlayer>();
        list.add(mockPlayer);
        doNothing().when(mockPlayer).clearPoint();
        game.setPlayers(list);
        game.setPlayerWithPointIdx(0);
        game.setInitialRoll(false);
        game.clearPoint();
        assertFalse(game.isPointInEffect());
        verify(mockPlayer).clearPoint();
        assertTrue(game.isInitialRoll());
    }

    @Test
    public void testValidAmountInteger() {
        assertTrue(game.isAmountValid("10"));
    }

    @Test
    public void testValidAmountDecimal() {
        assertTrue(game.isAmountValid("0.56"));
    }

    @Test
    public void testInValidAmountNull() {
        assertFalse(game.isAmountValid(null));
    }

    @Test
    public void testInValidAmountEmpty() {
        assertFalse(game.isAmountValid(""));
    }

    @Test
    public void testInValidAmountBlank() {
        assertFalse(game.isAmountValid(" "));
    }

    @Test
    public void testInValidAmountNonDigit() {
        assertFalse(game.isAmountValid("e"));
    }
}
