package org.yestech.celow.core;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    Game game;

//    @Mock
//    private IPlayer mockPlayer;

    @Mock
    private IEngine mockEngine;

    @Mock
    private IView mockView;
    
    @Before
    public void setUp() {
        game = new Game();
    }

    @Test(expected = RuntimeException.class)
    public void testAtleastOnePlayerRegistered() {
        game.rollDice();
    }

    @Test
    @Ignore
    public void testInitialRollPoint() {
        doNothing().when(mockEngine).rollDice();
        when(mockEngine.getDice1()).thenReturn(2);
        when(mockEngine.getDice2()).thenReturn(2);
        when(mockEngine.getDice3()).thenReturn(5);
        when(mockView.getWagerAmount()).thenReturn("1");
        game.setView(mockView);
        game.setEngine(mockEngine);
        game.rollDice();
        verify(mockView).getWagerAmount();
        verify(mockEngine).rollDice();
        verify(mockEngine).getDice1();
        verify(mockEngine).getDice2();
        verify(mockEngine).getDice3();
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
