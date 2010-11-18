package org.yestech.celow.android;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static java.lang.Math.abs;

/**
 *
 *
 */
public class ProcessSwipeGesture extends GestureDetector.SimpleOnGestureListener {
    private final static String TAG = "ProcessSwipeGesture";

    private static final int SWIPE_MIN_DISTANCE = 75;
    private static final int SWIPE_THRESHOLD_VELOCITY = 50;
    private Game game;

    public ProcessSwipeGesture(Game game) {
        this.game = game;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean processed = false;
        Log.d(TAG, "begin swipe caught");
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.d(TAG, "process roll left to right swipe");
            game.roll();
            processed = true;
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.d(TAG, "process roll right to left swipe");
            game.roll();
            processed = true;
        }
        Log.d(TAG, "end swipe caught");
        return processed;
    }
}
