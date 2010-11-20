package org.yestech.celow.android;

import android.util.Log;

/**
 *
 *
 */
public class Game {
    private final static String TAG = "Game";

    final private int LOSER = 0;
    final private int WINNER = 1;
    final private int POINT = 2;
    final private int NOTHING = 3;
    final private int DICESIDES = 6;
    private String bank;
    private String point;
    private int title;
    // GRAPHIC IMAGES
    private double backCount;
    // END GRAPHIC IMAGES
    private double bet;
    private double total;
    private int result; // 0:lose -  1:win - 2:point - 3:nothing
    private int countPoint;
    private int playerPoint;
    private int computerPoint;
    private boolean hasPoint = false;
    private int die[] = new int[3];
    private GameStatus gameStatus;

    public Game(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    private void processRoll() {
//        dice1 = diceImages[(die[0] - 1)];
//        dice2 = diceImages[(die[1] - 1)];
//        dice3 = diceImages[(die[2] - 1)];
        //System.out.println("PROCESS hasPoint: " + hasPoint);
        if (!hasPoint) {
            switch (result) {
                case LOSER:
                    title = R.string.loser_status_text;
                    total = total - bet;
                    bank = String.valueOf(total);
                    break;
                case WINNER:
                    title = R.string.winner_status_text;
                    total = total + bet;
                    bank = String.valueOf(total);
                    break;
                case POINT:
                    break;
                case NOTHING:
                    title = R.string.nothing_status_text;
                    break;
            }
            point = "0";
        } else {
            switch (result) {
                case LOSER:
                    title = R.string.computer_loser_status_text;
                    total = total + bet;
                    bank = String.valueOf(total);
                    hasPoint = false;
                    countPoint = 0;
                    break;
                case WINNER:
                    title = R.string.computer_winner_status_text;
                    total = total - bet;
                    bank = String.valueOf(total);
                    hasPoint = false;
                    countPoint = 0;
                    break;
                case POINT:
                    title = R.string.point_status_text;
                    point = String.valueOf(playerPoint);
                    //System.out.println("Point: " + playerPoint);
                    if (computerPoint != 0 && countPoint == 1) {
                        if (playerPoint < computerPoint) {
                            title = R.string.computer_winner_status_text;
                            total = total - bet;
                            bank = String.valueOf(total);
                        } else if (playerPoint == computerPoint) {
                            title = R.string.push_status_text;
                        } else if (playerPoint > computerPoint) {
                            title = R.string.computer_loser_status_text;
                            total = total + bet;
                            bank = String.valueOf(total);
                        }
                        if (countPoint == 1) {
                            hasPoint = false;
                            countPoint = 0;
                        }
                    }
                    break;
                case NOTHING:
                    title = R.string.computer_nothing_status_text;
                    break;
            }
        }
        updateView();
    }

    private void updateView() {
        gameStatus.setStatusValue(title);
        gameStatus.setDie1Value(die[0]);
        gameStatus.setDie2Value(die[1]);
        gameStatus.setDie3Value(die[2]);
        if (bank == null || bank.equals("")) {
            bank = "0";
        }
        gameStatus.setBankValue(bank);
        if (hasPoint) {
            gameStatus.showPoint(point);
        } else {
            gameStatus.hidePoint();
        }
    }

    /**
     * Roll the dice
     */
    private void rollDice() {
        die[0] = ((int) ((Math.random() * DICESIDES))) + 1;
        die[1] = ((int) ((Math.random() * DICESIDES))) + 1;
        die[2] = ((int) ((Math.random() * DICESIDES))) + 1;
        // RULES LOGIC
        /*
       winning hands:
       die0 == die1 == die2

       die0 == die1 && die2 == 6
       die1 == die2 && die0 == 6
       die0 == die2 && die1 == 6

       die0 == 4 && die1 == 5 && die2 == 6
       die0 == 4 && die1 == 6 && die2 == 5
       die0 == 5 && die1 == 4 && die2 == 6
       die0 == 5 && die1 == 6 && die2 == 4
       die0 == 6 && die1 == 5 && die2 == 4
       die0 == 6 && die1 == 4 && die2 == 5

       losing hands:
       die0 == 1 && die1 == 2 && die2 == 3
       die0 == 1 && die1 == 3 && die2 == 2
       die0 == 2 && die1 == 1 && die2 == 3
       die0 == 2 && die1 == 3 && die2 == 1
       die0 == 3 && die1 == 1 && die2 == 2
       die0 == 3 && die1 == 2 && die2 == 1

       die0 == die1 && die2 == 1
       die1 == die2 && die0 == 1
       die2 == die0 && die1 == 1

       points:
       die0 == die1 && die2 != 6 && die2 != 1
       die1 == die2 && die0 != 6 && die0 != 1
       die2 == die0 && die1 != 6 && die1 != 1
        */
        //winner
        if ((die[0] == die[1]) && (die[1] == die[2])) {
            result = WINNER;
        } else if ((die[0] == die[1]) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[1] == die[2]) && (die[0] == 6)) {
            result = WINNER;
        } else if ((die[0] == die[2]) && (die[1] == 6)) {
            result = WINNER;
        } else if ((die[0] == 4) && (die[1] == 5) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[0] == 4) && (die[1] == 6) && (die[2] == 5)) {
            result = WINNER;
        } else if ((die[0] == 5) && (die[1] == 4) && (die[2] == 6)) {
            result = WINNER;
        } else if ((die[0] == 5) && (die[1] == 6) && (die[2] == 4)) {
            result = WINNER;
        } else if ((die[0] == 6) && (die[1] == 5) && (die[2] == 4)) {
            result = WINNER;
        } else if ((die[0] == 6) && (die[1] == 4) && (die[2] == 5)) {
            result = WINNER;
        } else if ((die[0] == 1) && (die[1] == 2) && (die[2] == 3)) {
            result = LOSER;
        } else if ((die[0] == 1) && (die[1] == 3) && (die[2] == 2)) {
            result = LOSER;
        } else if ((die[0] == 2) && (die[1] == 1) && (die[2] == 3)) {
            result = LOSER;
        } else if ((die[0] == 2) && (die[1] == 3) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[0] == 3) && (die[1] == 1) && (die[2] == 2)) {
            result = LOSER;
        } else if ((die[0] == 3) && (die[1] == 2) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[0] == die[1]) && (die[2] == 1)) {
            result = LOSER;
        } else if ((die[1] == die[2]) && (die[0] == 1)) {
            result = LOSER;
        } else if ((die[2] == die[0]) && (die[1] == 1)) {
            result = LOSER;
        } else if ((die[0] == die[1]) && (1 < die[2]) && (die[2] < 6)) {
            if (!hasPoint) {
                playerPoint = die[2];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point2: " +
                //* die[2] + " ....");
                computerPoint = die[2];
                countPoint = 1;
            }
            result = POINT;
        } else if ((die[1] == die[2]) && (1 < die[0]) && (die[0] < 6)) {
            if (!hasPoint) {
                playerPoint = die[0];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point0: " +
                //* die[0] + " ....");
                computerPoint = die[0];
                countPoint = 1;
            }
            result = POINT;
        } else if ((die[2] == die[0]) && (1 < die[1]) && (die[1] < 6)) {
            if (!hasPoint) {
                playerPoint = die[1];
                //System.out.println("....... Player Point: " + playerPoint
                //* + " .......");
                hasPoint = true;
                countPoint = 0;
            } else {
                //System.out.println("....ROLL DICE COMPUTER Point1: " +
                //* die[1] + " ....");
                computerPoint = die[1];
                countPoint = 1;
            }
            result = POINT;
        } else {
            result = NOTHING;
        }
    }

    /**
     * Check the balance
     * 
     * @param amount
     * @return
     */
    private boolean checkAmount(String amount) {
        try {
            Double db = new Double(amount);
            boolean ok = db.isNaN();
            if (!ok) {
                //if ((db.doubleValue() > 0) && (db.doubleValue() <= 1000)) {
                if (db > 0) {
                    bet = db;
                    ok = true;
                }
            } else {
                ok = false;
            }
            return ok;
        } catch (NumberFormatException nf) {
            return false;
        }
    }

    public void roll() {
        if (!checkAmount(gameStatus.getWagerAmount())) {
            gameStatus.showInvalidAmount();
        } else {
            Log.d(TAG, "pre roll");
            rollDice();
            Log.d(TAG, "post roll");
            processRoll();
            Log.d(TAG, "post roll process");
        }
    }
}
