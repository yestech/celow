package org.yestech.celow.android;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 *
 */
public class GameStatus {
    private final static String TAG = "GameStatus";

    private TextView pointValue;
    private EditText wagerValue;
    private TextView bankValue;
    private TextView statusLabel;
    private TableRow pointRow;
    private TextView die1;
    private TextView die2;
    private TextView die3;
    private Context context;

    public GameStatus(Context context) {
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
        Toast.makeText(context, R.string.invalid_wager_status_text,Toast.LENGTH_LONG).show();
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
}
