package org.yestech.celow.core;

/**
 *
 *
 */
public interface IEngine {
    /**
     * Roll the dice:
     * <p/>
     * // RULES LOGIC
     * winning hands:
     * die0 == die1 == die2
     * <p/>
     * die0 == die1 && die2 == 6
     * die1 == die2 && die0 == 6
     * die0 == die2 && die1 == 6
     * <p/>
     * die0 == 4 && die1 == 5 && die2 == 6
     * die0 == 4 && die1 == 6 && die2 == 5
     * die0 == 5 && die1 == 4 && die2 == 6
     * die0 == 5 && die1 == 6 && die2 == 4
     * die0 == 6 && die1 == 5 && die2 == 4
     * die0 == 6 && die1 == 4 && die2 == 5
     * <p/>
     * losing hands:
     * die0 == 1 && die1 == 2 && die2 == 3
     * die0 == 1 && die1 == 3 && die2 == 2
     * die0 == 2 && die1 == 1 && die2 == 3
     * die0 == 2 && die1 == 3 && die2 == 1
     * die0 == 3 && die1 == 1 && die2 == 2
     * die0 == 3 && die1 == 2 && die2 == 1
     * <p/>
     * die0 == die1 && die2 == 1
     * die1 == die2 && die0 == 1
     * die2 == die0 && die1 == 1
     * <p/>
     * points:
     * die0 == die1 && die2 != 6 && die2 != 1
     * die1 == die2 && die0 != 6 && die0 != 1
     * die2 == die0 && die1 != 6 && die1 != 1
     *
     * @return The result of the Roll
     */
    public void rollDice();

    public ResultsEnum getResult();

    public int getDice1();

    public int getDice2();

    public int getDice3();

    int getPoint();

    void reset();
}
