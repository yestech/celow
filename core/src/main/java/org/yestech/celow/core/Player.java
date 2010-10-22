package org.yestech.celow.core;

/**
 * Represents a player in the game
 *
 */
public class Player implements IPlayer {
    private int dice1;
    private int dice2;
    private int dice3;
    private int point = 0;
    private double balance;
    private double wager;
    private String id;
    private String screenName;
    private ResultsEnum result;
    private PlayerTypeEnum type;
    
    @Override
    public PlayerTypeEnum getType() {
        return type;
    }

    @Override
    public void setType(PlayerTypeEnum type) {
        this.type = type;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public ResultsEnum getResult() {
        return result;
    }

    @Override
    public void setResult(ResultsEnum result) {
        this.result = result;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public double getWager() {
        return wager;
    }

    @Override
    public void setWager(double wager) {
        this.wager = wager;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getPoint() {
        return point;
    }

    @Override
    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean hasPoint() {
        return point > 0;
    }

    @Override
    public int getDice1() {
        return dice1;
    }

    @Override
    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    @Override
    public int getDice2() {
        return dice2;
    }

    @Override
    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    @Override
    public int getDice3() {
        return dice3;
    }

    @Override
    public void setDice3(int dice3) {
        this.dice3 = dice3;
    }

    @Override
    public void clearPoint() {
        point = 0;
    }

    @Override
    public boolean isComputer() {
        return PlayerTypeEnum.COMPUTER == type;
    }

    @Override
    public String toString() {
        return "Player{" +
                "dice1=" + dice1 +
                ", dice2=" + dice2 +
                ", dice3=" + dice3 +
                ", point=" + point +
                ", balance=" + balance +
                ", wager=" + wager +
                ", id='" + id + '\'' +
                ", screenName='" + screenName + '\'' +
                ", result=" + result +
                ", type=" + type +
                '}';
    }
}
