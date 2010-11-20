package org.yestech.celow.android;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 *
 *
 */
public class ProcessSwipeGesture extends GestureDetector.SimpleOnGestureListener {
    private final static String TAG = "ProcessSwipeGesture";
    private Game game;
    private static final double MIN_DISTANCE = 10;

    public ProcessSwipeGesture(Game game) {
        this.game = game;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "begin swipe caught");
        double distance = calculateDistance(e1, e2);
        Log.d(TAG, "swipe distance: "+ distance);
        if (distance > MIN_DISTANCE) {
            Log.d(TAG, "processing roll");
            game.roll();
        }
        Log.d(TAG, "end swipe caught");
        return true;
    }

    private double calculateDistance(MotionEvent e1, MotionEvent e2) {
        float x1 =e1.getX();
        float y1 =e1.getY();
        float x2 =e2.getX();
        float y2 =e2.getY();
        double x = pow(x2 - x1, 2);
        double y = pow(y2 - y1, 2);
        return sqrt(x + y);
    }
}
