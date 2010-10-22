package org.yestech.celow.applet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.yestech.celow.core.IGame;
import org.yestech.celow.core.IPlayer;
import org.yestech.celow.core.ResultsEnum;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CelowTest {
    Celow celow;

    @Mock
    IPlayer mockCurrentPlayer;

    @Mock
    IPlayer mockPointPlayer;

    @Mock
    IGame mockGame;

    @Before
    public void setUp() {
        celow = new Celow();
    }

    @Test
    public void testInitialRollPoint() {
        when(mockGame.getCurrentPlayer()).thenReturn(mockCurrentPlayer);
        when(mockGame.getPointPlayer()).thenReturn(mockPointPlayer);
        when(mockGame.isPointInEffect()).thenReturn(Boolean.FALSE);
        when(mockCurrentPlayer.getResult()).thenReturn(ResultsEnum.POINT);
        when(mockCurrentPlayer.getDice1()).thenReturn(1);
        when(mockCurrentPlayer.getDice2()).thenReturn(1);
        when(mockCurrentPlayer.getDice3()).thenReturn(2);
        doNothing().when(mockGame).setInitialRoll(false);
        celow.setGame(mockGame);
        Image[] diceImages = new Image[6];
        
        celow.setDiceImages(diceImages);
        celow.processRoll();
        assertEquals(Celow.POINT_TITLE, celow.getTitle());
        InOrder order = inOrder(mockGame, mockCurrentPlayer);
        order.verify(mockGame).getCurrentPlayer();
        order.verify(mockGame, never()).getPointPlayer();
        order.verify(mockGame).isPointInEffect();
        order.verify(mockCurrentPlayer).getResult();
        order.verify(mockCurrentPlayer).getDice1();
        order.verify(mockCurrentPlayer).getDice2();
        order.verify(mockCurrentPlayer).getDice3();
        order.verify(mockGame).setInitialRoll(false);
    }

    @Test
    public void testProcessNothingRoll() {

        when(mockGame.getCurrentPlayer()).thenReturn(mockCurrentPlayer);
        when(mockGame.getPointPlayer()).thenReturn(mockPointPlayer);
        when(mockCurrentPlayer.getResult()).thenReturn(ResultsEnum.NOTHING);
        when(mockCurrentPlayer.getDice1()).thenReturn(1);
        when(mockCurrentPlayer.getDice2()).thenReturn(6);
        when(mockCurrentPlayer.getDice3()).thenReturn(2);
        doNothing().when(mockGame).setInitialRoll(true);
        celow.setGame(mockGame);
        Image[] diceImages = new Image[6];

        celow.setDiceImages(diceImages);
        celow.processRoll();
        assertEquals(Celow.NOTHING_TITLE, celow.getTitle());
        assertEquals("0", celow.getPoint());
        InOrder order = inOrder(mockGame, mockCurrentPlayer);
        order.verify(mockGame).getCurrentPlayer();
        order.verify(mockGame, never()).getPointPlayer();
        order.verify(mockCurrentPlayer).getResult();
        order.verify(mockCurrentPlayer).getDice1();
        order.verify(mockCurrentPlayer).getDice2();
        order.verify(mockCurrentPlayer).getDice3();
        order.verify(mockGame).setInitialRoll(true);
    }

    @Test
    public void testActionNonButtonCall() {
        String amount = "-1";
        Button btn = new Button();
        Event event = new Event(btn, 1, null);
        when(mockGame.isAmountValid(amount)).thenReturn(Boolean.FALSE);
        boolean valid = celow.action(event, null);
        assertFalse(valid);
        assertNull(celow.getTitle());
        InOrder order = inOrder(mockGame);
        order.verify(mockGame, never()).isAmountValid(amount);
    }

    @Test
    public void testActionInvalidAmount() {
        String amount = "-1";
        TextField tf = new TextField();
        tf.setText(amount);
        Button btn = new Button();
        Event event = new Event(btn, 1, null);
        celow.setBtRoll(btn);
        celow.setGame(mockGame);
        celow.setTfWager(tf);
        when(mockGame.isAmountValid(amount)).thenReturn(Boolean.FALSE);
        when(mockGame.getCurrentPlayer()).thenReturn(mockCurrentPlayer);
        boolean valid = celow.action(event, null);
        assertFalse(valid);
        assertEquals(Celow.INVALID_WAGER_TITLE, celow.getTitle());
        InOrder order = inOrder(mockGame);
        order.verify(mockGame).isAmountValid(amount);
        order.verify(mockGame, never()).getCurrentPlayer();
    }

    @Test
    public void testActionValidAmount() {
        Celow spy = spy(celow);
        String amount = "1";
        TextField tf = new TextField();
        tf.setText(amount);
        Button btn = new Button();
        Event event = new Event(btn, 1, null);
        spy.setBtRoll(btn);
        spy.setGame(mockGame);
        spy.setTfWager(tf);
        when(mockGame.isAmountValid(amount)).thenReturn(Boolean.TRUE);
        when(mockGame.getCurrentPlayer()).thenReturn(mockCurrentPlayer);
        doNothing().when(mockGame).rollDice();
        doNothing().when(mockCurrentPlayer).setWager(1.0);
        doNothing().when(spy).processRoll();
        boolean valid = spy.action(event, null);
        assertTrue(valid);
        assertNull(celow.getTitle());
        InOrder order = inOrder(mockGame, mockCurrentPlayer, spy);
        order.verify(mockGame).isAmountValid(amount);
        order.verify(mockGame).getCurrentPlayer();
        order.verify(mockCurrentPlayer).setWager(1.0);
        order.verify(mockGame).rollDice();
        order.verify(spy).processRoll();
    }
}
