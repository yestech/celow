package org.yestech.celow.core;

/**
 *
 *
 */
public interface IGame {

    /**
     * Tests if the amount supplied is a valid wager
     *
     * @param text
     * @return
     */
    boolean isAmountValid(String text);

    void rollDice();

    void addPlayer(IPlayer player);

    IPlayer getPlayer(String id);

    IPlayer getPlayer(int i);

    int getTotalPlayers();

    IEngine getEngine();

    void setEngine(IEngine engine);

    int getCurrentPlayerIdx();

    void setCurrentPlayerIdx(int currentPlayer);

    IPlayer getCurrentPlayer();

    IPlayer getPointPlayer();

    boolean isPointInEffect();

    boolean isInitialRoll();

    void setInitialRoll(boolean initialRoll);
}
