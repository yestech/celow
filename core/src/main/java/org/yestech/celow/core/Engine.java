/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.celow.core;

import static java.lang.Math.random;
import static org.yestech.celow.core.ResultsEnum.*;

/**
 * Engine for Celow it handles rolling the dice. all die will be between (1,6) or 1 <= x <= 6
 */
public class Engine implements IEngine {
    private ResultsEnum result;
    private int dice1;
    private int dice2;
    private int dice3;
    private int point;

    private static final int DICESIDES = 6;

    public Engine() {
    }

    @Override
    public void reset() {
        dice1 = 0;
        dice2 = 0;
        dice3 = 0;
        point = 0;
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public ResultsEnum getResult() {
        return result;
    }

    @Override
    public int getDice1() {
        return dice1;
    }

    @Override
    public int getDice2() {
        return dice2;
    }

    @Override
    public int getDice3() {
        return dice3;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public void setDice3(int dice3) {
        this.dice3 = dice3;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Roll the dice:
     * <p/>
     * // RULES LOGIC
     * winning hands:
     * dice1 == dice2 == dice3
     * <p/>
     * dice1 == dice2 && dice3 == 6
     * dice2 == dice3 && dice1 == 6
     * dice1 == dice3 && dice2 == 6
     * <p/>
     * dice1 == 4 && dice2 == 5 && dice3 == 6
     * dice1 == 4 && dice2 == 6 && dice3 == 5
     * dice1 == 5 && dice2 == 4 && dice3 == 6
     * dice1 == 5 && dice2 == 6 && dice3 == 4
     * dice1 == 6 && dice2 == 5 && dice3 == 4
     * dice1 == 6 && dice2 == 4 && dice3 == 5
     * <p/>
     * losing hands:
     * dice1 == 1 && dice2 == 2 && dice3 == 3
     * dice1 == 1 && dice2 == 3 && dice3 == 2
     * dice1 == 2 && dice2 == 1 && dice3 == 3
     * dice1 == 2 && dice2 == 3 && dice3 == 1
     * dice1 == 3 && dice2 == 1 && dice3 == 2
     * dice1 == 3 && dice2 == 2 && dice3 == 1
     * <p/>
     * dice1 == dice2 && dice3 == 1
     * dice2 == dice3 && dice1 == 1
     * dice3 == dice1 && dice2 == 1
     * <p/>
     * points:
     * dice1 == dice2 && dice3 != 6 && dice3 != 1
     * dice2 == dice3 && dice1 != 6 && dice1 != 1
     * dice3 == dice1 && dice2 != 6 && dice2 != 1
     */
    @Override
    public void rollDice() {
        dice1 = ((int) ((random() * DICESIDES))) + 1;
        dice2 = ((int) ((random() * DICESIDES))) + 1;
        dice3 = ((int) ((random() * DICESIDES))) + 1;
        processResult();


        System.out.println("Engine d1: " + dice1);
        System.out.println("Engine d2: " + dice2);
        System.out.println("Engine d3: " + dice3);
        System.out.println("Engine result: " + result);
    }

    protected void processResult() {
        //winner
        if ((dice1 == dice2) && (dice2 == dice3)) {
            result = WINNER;
        } else if ((dice1 == dice2) && (dice3 == 6)) {
            result = WINNER;
        } else if ((dice2 == dice3) && (dice1 == 6)) {
            result = WINNER;
        } else if ((dice1 == dice3) && (dice2 == 6)) {
            result = WINNER;
        } else if ((dice1 == 4) && (dice2 == 5) && (dice3 == 6)) {
            result = WINNER;
        } else if ((dice1 == 4) && (dice2 == 6) && (dice3 == 5)) {
            result = WINNER;
        } else if ((dice1 == 5) && (dice2 == 4) && (dice3 == 6)) {
            result = WINNER;
        } else if ((dice1 == 5) && (dice2 == 6) && (dice3 == 4)) {
            result = WINNER;
        } else if ((dice1 == 6) && (dice2 == 5) && (dice3 == 4)) {
            result = WINNER;
        } else if ((dice1 == 6) && (dice2 == 4) && (dice3 == 5)) {
            result = WINNER;
        } else if ((dice1 == 1) && (dice2 == 2) && (dice3 == 3)) {
            result = LOSER;
        } else if ((dice1 == 1) && (dice2 == 3) && (dice3 == 2)) {
            result = LOSER;
        } else if ((dice1 == 2) && (dice2 == 1) && (dice3 == 3)) {
            result = LOSER;
        } else if ((dice1 == 2) && (dice2 == 3) && (dice3 == 1)) {
            result = LOSER;
        } else if ((dice1 == 3) && (dice2 == 1) && (dice3 == 2)) {
            result = LOSER;
        } else if ((dice1 == 3) && (dice2 == 2) && (dice3 == 1)) {
            result = LOSER;
        } else if ((dice1 == dice2) && (dice3 == 1)) {
            result = LOSER;
        } else if ((dice2 == dice3) && (dice1 == 1)) {
            result = LOSER;
        } else if ((dice3 == dice1) && (dice2 == 1)) {
            result = LOSER;
        } else if ((dice1 == dice2) && (1 < dice3) && (dice3 < 6)) {
            point = dice3;
            result = POINT;
        } else if ((dice2 == dice3) && (1 < dice1) && (dice1 < 6)) {
            point = dice1;
            result = POINT;
        } else if ((dice3 == dice1) && (1 < dice2) && (dice2 < 6)) {
            point = dice2;
            result = POINT;
        } else {
            result = NOTHING;
        }
    }
}
