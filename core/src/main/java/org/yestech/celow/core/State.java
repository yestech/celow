package org.yestech.celow.core;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 *
 *
 */
public class State implements IState {
    private List<Integer> dice = new ArrayList<Integer>(3);
    private int total;
    private boolean point;
    private int pointValue;
    private GameResultEnum result;
    private static final long serialVersionUID = -1155542650736376361L;

    @Override
    public void setDie(int die, int value) {
        dice.add(die, value);
    }

    @Override
    public int getDie(int die) {
        return dice.get(die);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (point != state.point) return false;
        if (pointValue != state.pointValue) return false;
        if (total != state.total) return false;
        if (dice != null ? !dice.equals(state.dice) : state.dice != null) return false;
        if (result != state.result) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = dice != null ? dice.hashCode() : 0;
        result1 = 31 * result1 + total;
        result1 = 31 * result1 + (point ? 1 : 0);
        result1 = 31 * result1 + pointValue;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "State{" +
                "dice=" + dice +
                ", total=" + total +
                ", point=" + point +
                ", pointValue=" + pointValue +
                ", result=" + result +
                '}';
    }
}
