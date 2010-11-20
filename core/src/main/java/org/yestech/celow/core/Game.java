package org.yestech.celow.core;

/**
 *
 *
 */
public class Game implements IGame {
    private final static String TAG = "Game";

    private int bet;
    private int total;
    private IState state;
    private IView view;

    private IEngine engine;
    private int playerPoint;
    private int computerPoint;
    private boolean hasPoint;

    public Game() {
        engine = new Engine();
        state = new State();
    }

    @Override
    public IState gettate() {
        return state;
    }

    @Override
    public void setState(IState state) {
        this.state = state;
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public IEngine getEngine() {
        return engine;
    }

    @Override
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    private void processRoll() {
        ResultsEnum result = engine.getResult();
        boolean currentPoint = engine.isPoint();
        int point = engine.getPoint();
        state.setDie(0, engine.getDice1());
        state.setDie(1, engine.getDice2());
        state.setDie(2, engine.getDice3());
        if (!hasPoint) {
            switch (result) {
                case LOSER:
                    total = total - bet;
                    state.setResult(GameResultEnum.LOSER);
                    state.setTotal(total);
                    break;
                case WINNER:
                    total = total + bet;
                    state.setResult(GameResultEnum.WINNER);
                    state.setTotal(total);
                    break;
                case POINT:
                    initializePoint(point);
                    break;
                case NOTHING:
                    state.setResult(GameResultEnum.NOTHING);
                    break;
            }
        } else {
            switch (result) {
                case LOSER:
                    total = total + bet;
                    state.setResult(GameResultEnum.COMPUTER_LOSER);
                    state.setTotal(total);
                    resetPoint();
                    break;
                case WINNER:
                    total = total - bet;
                    state.setResult(GameResultEnum.COMPUTER_WINNER);
                    state.setTotal(total);
                    resetPoint();
                    break;
                case POINT:
                    state.setResult(GameResultEnum.COMPUTER_POINT);
                    computerPoint = point;
                    if (playerPoint < computerPoint) {
                        total = total - bet;
                        state.setResult(GameResultEnum.COMPUTER_WINNER);
                        state.setTotal(total);
                    } else if (playerPoint == computerPoint) {
                        state.setResult(GameResultEnum.PUSH);
                    } else if (playerPoint > computerPoint) {
                        state.setResult(GameResultEnum.COMPUTER_LOSER);
                        total = total + bet;
                        state.setTotal(total);
                    }
                    resetPoint();
                    break;
                case NOTHING:
                    state.setResult(GameResultEnum.COMPUTER_NOTHING);
                    break;
            }
        }
        view.update(state);
    }

    protected void initializePoint(int point) {
        playerPoint = point;
        computerPoint = 0;
        state.setResult(GameResultEnum.POINT);
        state.setPoint(point);
        state.setPoint(true);
        hasPoint = true;
    }

    protected void resetPoint() {
        state.resetPoint();
        computerPoint = 0;
        playerPoint = 0;
        state.setPoint(false);
        hasPoint = false;
    }

    /**
     * Check the balance
     *
     * @param amount
     * @return
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
                    bet = db.intValue();
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
        if (!isAmountValid(view.getWagerAmount())) {
            view.showInvalidAmount();
        } else {
            engine.rollDice();
            processRoll();
        }
    }
}
