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

    IEngine getEngine();

    void setEngine(IEngine engine);

    IState gettate();

    void setState(IState state);

    IView getView();

    void setView(IView view);
}
