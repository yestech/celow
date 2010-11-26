package org.yestech.celow.core;

/**
 *
 *
 */
public interface IView {
    void update(IState state);

    String getWagerAmount();

    void showInvalidAmount();

    void reset();
}
