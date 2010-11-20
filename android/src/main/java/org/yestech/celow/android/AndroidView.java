package org.yestech.celow.android;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import org.yestech.celow.core.GameResultEnum;
import org.yestech.celow.core.IState;
import org.yestech.celow.core.IView;

/**
 *
 *
 */
public class AndroidView implements IView {
    private final static String TAG = "AndroidView";

    private TextView pointValue;
    private EditText wagerValue;
    private TextView bankValue;
    private TextView statusLabel;
    private TableRow pointRow;
    private TextView die1;
    private TextView die2;
    private TextView die3;
    private Context context;

    public AndroidView(Context context) {
        this.context = context;
    }

    public TextView getDie1() {
        return die1;
    }

    public void setDie1(TextView die1) {
        this.die1 = die1;
    }

    public TextView getDie2() {
        return die2;
    }

    public void setDie2(TextView die2) {
        this.die2 = die2;
    }

    public TextView getDie3() {
        return die3;
    }

    public void setDie3(TextView die3) {
        this.die3 = die3;
    }

    public TextView getPointValue() {
        return pointValue;
    }

    public void setPointValue(TextView pointValue) {
        this.pointValue = pointValue;
    }

    public EditText getWagerValue() {
        return wagerValue;
    }

    public void setWagerValue(EditText wagerValue) {
        this.wagerValue = wagerValue;
    }

    public String getWagerAmount() {
        return wagerValue.getText().toString();
    }

    public TextView getBankValue() {
        return bankValue;
    }

    public void setBankValue(TextView bankValue) {
        this.bankValue = bankValue;
    }

    public TextView getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(TextView statusLabel) {
        this.statusLabel = statusLabel;
    }

    public TableRow getPointRow() {
        return pointRow;
    }

    public void setPointRow(TableRow pointRow) {
        this.pointRow = pointRow;
    }

    public void hidePoint() {
        pointRow.setVisibility(View.INVISIBLE);
        pointValue.setText("0");
    }

    public void showInvalidAmount() {
        Toast.makeText(context, R.string.invalid_wager_status_text, Toast.LENGTH_LONG).show();
    }

    public void setBankValue(String bank) {
        bankValue.setText(bank);
    }

    public void setStatusValue(int title) {
        statusLabel.setText(title);
    }

    public void setDie1Value(int i) {
        die1.setText(String.valueOf(i));
    }

    public void setDie2Value(int i) {
        die2.setText(String.valueOf(i));
    }

    public void setDie3Value(int i) {
        die3.setText(String.valueOf(i));
    }

    public void showPoint(String point) {
        pointRow.setVisibility(View.VISIBLE);
        pointValue.setText(point);
    }

    @Override
    public void update(IState state) {
        GameResultEnum result = state.getResult();
        setStatusValue(getTitle(result));
        setDie1Value(state.getDie(0));
        setDie2Value(state.getDie(1));
        setDie3Value(state.getDie(2));
        String bank = state.getBank();
        if (bank == null || bank.equals("")) {
            bank = "0";
        }
        setBankValue(bank);
        boolean hasPoint = state.isPoint();
        if (hasPoint) {
            showPoint(state.getPointAsString());
        } else {
            hidePoint();
        }
    }

    protected int getTitle(GameResultEnum result) {
        int title = R.string.computer_nothing_status_text;
        switch (result) {
            case LOSER:
                title = R.string.loser_status_text;
                break;
            case WINNER:
                title = R.string.winner_status_text;
                break;
            case NOTHING:
                title = R.string.nothing_status_text;
                break;            
            case POINT:
                title = R.string.point_status_text;
                break;
            case PUSH:
                title = R.string.push_status_text;
                break;
            case COMPUTER_LOSER:
                title = R.string.computer_loser_status_text;
                break;
            case COMPUTER_WINNER:
                title = R.string.computer_winner_status_text;
                break;
            case COMPUTER_NOTHING:
                title = R.string.computer_nothing_status_text;
                break;
            case COMPUTER_POINT:
                title = R.string.point_status_text;
                break;
        }
        return title;
    }
}
