package org.yestech.celow.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import org.yestech.celow.core.Game;
import org.yestech.celow.core.IGame;
import org.yestech.celow.core.IState;

public class Celow extends Activity {
    private final static String TAG = "Celow";

    private GestureDetector gestureDetector;
    private IGame game;
    private AndroidView androidGameView;
    private boolean running = false;
    private StateDao stateDao;

    @Override
    protected void onPause() {
        //save state
        stateDao.save(game.getState());
        running = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (!running && stateDao != null) {
            //reload state
            IState state = stateDao.load();
            if (state != null && state.isInitialized()) {
                game.apply(state);
            }
            running = true;
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
//        stateDao.delete();
        super.onStop();
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.stateDao = new StateDao(this);
        androidGameView = new AndroidView(this);
        game = new Game();
        game.setView(androidGameView);
        gestureDetector = new GestureDetector(new ProcessSwipeGesture(game));
        //need to add to parent first for hierarchy
        setContentView(R.layout.celow);
        initalize();
        androidGameView.hidePoint();
        running = true;
    }

    private void initalize() {
        TableRow pointRow = (TableRow) findViewById(R.id.point_row);
        TextView pointValue = (TextView) findViewById(R.id.point_value);
        TextView statusLabel = (TextView) findViewById(R.id.status_label);
        TextView bankValue = (TextView) findViewById(R.id.bank_value);
        EditText wagerValue = (EditText) findViewById(R.id.wager_text_field);
        androidGameView.setPointRow(pointRow);
        androidGameView.setPointValue(pointValue);
        androidGameView.setStatusLabel(statusLabel);
        androidGameView.setBankValue(bankValue);
        androidGameView.setWagerValue(wagerValue);

        TextView die1 = (TextView) findViewById(R.id.die1);
        TextView die2 = (TextView) findViewById(R.id.die2);
        TextView die3 = (TextView) findViewById(R.id.die3);
        androidGameView.setDie1(die1);
        androidGameView.setDie2(die2);
        androidGameView.setDie3(die3);
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

    /**
     * Create the Menu Options
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about_menu:
                showAbout();
                return true;
            case R.id.help_menu:
                show(Help.class);
                return true;
            case R.id.quit_menu:
                quit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_title)
                .setIcon(R.drawable.about_icon)
                .setMessage(R.string.about_text)
                .setCancelable(true)
                .setNeutralButton(R.string.close_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog aboutDialog = builder.create();
        aboutDialog.show();
    }

    private void show(Class<?> activity) {
        Intent myIntent = new Intent(this, activity);
        startActivity(myIntent);
    }

    private void quit() {
        stateDao.delete();
        finish();
    }
}
