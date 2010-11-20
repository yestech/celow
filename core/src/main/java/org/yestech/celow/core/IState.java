package org.yestech.celow.core;

/**
 *
 *
 */
public interface IState {
    int getDie(int die);

    int getTotal();

    String getBank();

    boolean isPoint();

    int getPoint();

    String getPointAsString();

    GameResultEnum getResult();

    void setResult(GameResultEnum title);

    void setTotal(int total);

    void setPoint(int point);

    void setPoint(boolean point);

    void resetPoint();

    void setDie(int die, int value);
}
