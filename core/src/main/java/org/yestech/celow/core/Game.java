package org.yestech.celow.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 */
public class Game implements IGame {
    private Map<String, IPlayer> idToplayers;
    private List<IPlayer> players;
    private int currentPlayerIdx = 0;
    private IEngine engine;
    private int playerWithPointIdx = -1;
    private boolean initialRoll;

    //the total of all the player bets;
    private int pot;

    public Game() {
        players = new ArrayList<IPlayer>();
        idToplayers = new HashMap<String, IPlayer>();
        engine = new Engine();
        initialRoll = true;
    }

    public boolean isInitialRoll() {
        return initialRoll;
    }

    public void setInitialRoll(boolean initialRoll) {
        this.initialRoll = initialRoll;
    }

    List<IPlayer> getPlayers() {
        return players;
    }

    void setPlayers(List<IPlayer> players) {
        this.players = players;
    }

    @Override
    public IEngine getEngine() {
        return engine;
    }

    @Override
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public int getPlayerWithPointIdx() {
        return playerWithPointIdx;
    }

    public void setPlayerWithPointIdx(int playerWithPointIdx) {
        this.playerWithPointIdx = playerWithPointIdx;
    }

    @Override
    public int getCurrentPlayerIdx() {
        return currentPlayerIdx;
    }

    @Override
    public void setCurrentPlayerIdx(int currentPlayerIdx) {
        this.currentPlayerIdx = currentPlayerIdx;
    }

    @Override
    public void addPlayer(IPlayer player) {
        players.add(player);
        idToplayers.put(player.getId(), player);
    }

    @Override
    public IPlayer getPlayer(String id) {
        return idToplayers.get(id);
    }

    @Override
    public IPlayer getPlayer(int i) {
        return players.get(i);
    }

    @Override
    public int getTotalPlayers() {
        return players.size();
    }

    @Override
    public IPlayer getPointPlayer() {
        return players.get(playerWithPointIdx);
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return players.get(currentPlayerIdx);
    }

    @Override
    public boolean isPointInEffect() {
        return playerWithPointIdx > -1 && !isInitialRoll();
    }

    protected void clearPoint() {
        if (isPointInEffect()) {
            IPlayer player = getPlayer(playerWithPointIdx);
            player.clearPoint();
            playerWithPointIdx = -1;
            initialRoll = true;
        }
    }

    /**
     * to make sure amount to wager is valid
     */
    @Override
    public boolean isAmountValid(String amount) {
        if (amount == null) {
            return false;
        }
        try {
            Double db = new Double(amount);
            boolean ok = db.isNaN();
            if (!ok) {
                if (db > 0) {
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

    @Override
    public void rollDice() {
        if (getTotalPlayers() < 1) {
            throw new RuntimeException("There must be atleast one player...");
        }
        //play game
        engine.rollDice();
        processRoll();
        engine.reset();
    }

    /**
     * Process a dice role
     */
    protected void processRoll() {
        ResultsEnum result = engine.getResult();
        IPlayer playerRolling = initializeCurrentPlayer();
        double balance = playerRolling.getBalance();
        double bet = playerRolling.getWager();
        if (isPointInEffect()) {
            rotateCurrentUserIdx();            
            IPlayer pointPlayer = getPlayer(getPlayerWithPointIdx());
            double pointPlayerBalance = pointPlayer.getBalance();
            double pointPlayerWager = pointPlayer.getWager();
            switch (result) {
                case LOSER:
                    //player rolling loss
                    balance = updatePointLossRoll(balance, bet, pointPlayer, pointPlayerBalance, pointPlayerWager);
                    rotateCurrentUserIdx();
                    clearPoint();
                    break;
                case WINNER:
                    //player rolling win
                    balance = updatePointWinRoll(balance, bet, pointPlayer, pointPlayerBalance, pointPlayerWager);
                    rotateCurrentUserIdx();
                    clearPoint();
                    break;
                case POINT:
                    //need to figure out winner
                    int playerRollingPoint = engine.getPoint();
                    int pointPlayerPoint = pointPlayer.getPoint();
                    if (pointPlayerPoint < playerRollingPoint) {
                        //player rolling win
                        balance = updatePointWinRoll(balance, bet, pointPlayer, pointPlayerBalance, pointPlayerWager);
                    } else if (pointPlayerPoint == playerRollingPoint) {
                        //push
                        pointPlayer.clearPoint();
                    } else if (pointPlayerPoint > playerRollingPoint) {
                        //player rolling loss
                        balance = updatePointLossRoll(balance, bet, pointPlayer, pointPlayerBalance, pointPlayerWager);
                    }
                    clearPoint();
                    rotateCurrentUserIdx();
                    break;
                case NOTHING:
                    break;
            }

        } else {
            switch (result) {
                case LOSER:
                    balance = calculateLoss(balance, bet);
                    clearPoint();
                    break;
                case WINNER:
                    balance = calculateWin(balance, bet);
                    clearPoint();
                    break;
                case POINT:
                    playerRolling.setPoint(engine.getPoint());
                    playerWithPointIdx = currentPlayerIdx;
                    break;
                case NOTHING:
                    clearPoint();
                    break;
            }
        }
        playerRolling.setBalance(balance);
    }

    protected double updatePointWinRoll(double balance, double bet, IPlayer pointPlayer, double pointPlayerBalance, double pointPlayerWager) {
        balance = calculateWin(balance, bet);
        pointPlayer.setBalance(calculateLoss(pointPlayerBalance, pointPlayerWager));
        pointPlayer.clearPoint();
        return balance;
    }

    protected double updatePointLossRoll(double balance, double bet, IPlayer pointPlayer, double pointPlayerBalance, double pointPlayerWager) {
        balance = calculateLoss(balance, bet);
        pointPlayer.setBalance(calculateWin(pointPlayerBalance, pointPlayerWager));
        pointPlayer.clearPoint();
        return balance;
    }

    private double calculateWin(double balance, double bet) {
        balance = balance + bet;
        return balance;
    }

    private double calculateLoss(double balance, double bet) {
        balance = balance - bet;
        return balance;
    }

    protected void rotateCurrentUserIdx() {
        currentPlayerIdx = ((currentPlayerIdx + 1) % getTotalPlayers());
    }

    private IPlayer initializeCurrentPlayer() {
        IPlayer player = players.get(currentPlayerIdx);
        int dice1 = engine.getDice1();
        int dice2 = engine.getDice2();
        int dice3 = engine.getDice3();
        ResultsEnum result = engine.getResult();
        player.setDice1(dice1);
        player.setDice2(dice2);
        player.setDice3(dice3);
        player.setResult(result);
        System.out.println("Engine player: " + player);
        System.out.println("Engine die1: " + dice1);
        System.out.println("Engine die2: " + dice2);
        System.out.println("Engine die3: " + dice3);
        System.out.println("Engine result: " + result);
        return player;
    }
}
