package org.yestech.celow.core;

import static java.lang.String.valueOf;

/**
 *
 *
 */
public class State implements IState {
    private int[] dice = new int[3];
    private int total;
    private boolean point;
    private int pointValue;
    private GameResultEnum result;

    @Override
    public void setDie(int die, int value) {
        dice[die] = value;
    }

    @Override
    public int getDie(int die) {
        return dice[die];
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public String getBank() {
        return valueOf(total);
    }

    @Override
    public boolean isPoint() {
        return point;
    }

    @Override
    public int getPoint() {
        return pointValue;
    }

    @Override
    public String getPointAsString() {
        return valueOf(pointValue);
    }

    @Override
    public GameResultEnum getResult() {
        return result;
    }

    @Override
    public void setResult(GameResultEnum result) {
        this.result = result;
    }

    @Override
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public void setPoint(int point) {
        this.pointValue = point;
    }

    @Override
    public void setPoint(boolean point) {
        this.point = point;
    }

    @Override
    public void resetPoint() {
        setPoint(false);
        setPoint(0);
    }
}
