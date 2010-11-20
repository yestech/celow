package org.yestech.celow.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class Celow extends Activity {
    private final static String TAG = "Celow";

    private GestureDetector gestureDetector;
    private Game game;
    private GameStatus gameStatus;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameStatus = new GameStatus(this);
        game = new Game(gameStatus);
        gestureDetector = new GestureDetector(new ProcessSwipeGesture(game));
        //need to add to parent first for hierarchy
        Log.d(TAG, "adding main layout to parent");
        setContentView(R.layout.main);
        Log.d(TAG, "initializing game views");
        initalize();
        Log.d(TAG, "hiding point view");
        gameStatus.hidePoint();
    }

    private void initalize() {
        TableRow pointRow = (TableRow) findViewById(R.id.point_row);
        TextView pointValue = (TextView) findViewById(R.id.point_value);
        TextView statusLabel = (TextView) findViewById(R.id.status_label);
        TextView bankValue = (TextView) findViewById(R.id.bank_value);
        EditText wagerValue = (EditText) findViewById(R.id.wager_text_field);
        gameStatus.setPointRow(pointRow);
        gameStatus.setPointValue(pointValue);
        gameStatus.setStatusLabel(statusLabel);
        gameStatus.setBankValue(bankValue);
        gameStatus.setWagerValue(wagerValue);

        TextView die1 = (TextView) findViewById(R.id.die1);
        TextView die2 = (TextView) findViewById(R.id.die2);
        TextView die3 = (TextView) findViewById(R.id.die3);
        gameStatus.setDie1(die1);
        gameStatus.setDie2(die2);
        gameStatus.setDie3(die3);
    }

    /**
     * Handle the Touch Event
     *
     * @param event Event to handle
     * @return true if handled else false
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
            return true;
        else
            return false;
    }
}
