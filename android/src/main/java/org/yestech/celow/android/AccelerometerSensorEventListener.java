package org.yestech.celow.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
*The listener that listen to events from the accelerometer listener
*
*/
class AccelerometerSensorEventListener implements SensorEventListener {

    private long now = 0;
    private long timeDiff = 0;
    private long lastUpdate = 0;
    private long lastShake = 0;

    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;
    private float force = 0;
    private AccelerometerManager accelerometerManager;


    public AccelerometerSensorEventListener(AccelerometerManager accelerometerManager) {
        this.accelerometerManager = accelerometerManager;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        // use the event timestamp as reference
        // so the manager precision won't depends
        // on the AccelerometerListener implementation
        // processing time
        now = event.timestamp;

        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        // if not interesting in shake events
        // just remove the whole if then else bloc
        if (lastUpdate == 0) {
            lastUpdate = now;
            lastShake = now;
            lastX = x;
            lastY = y;
            lastZ = z;
        } else {
            timeDiff = now - lastUpdate;
            if (timeDiff > 0) {
                force = Math.abs(x + y + z - lastX - lastY - lastZ) / timeDiff;
                if (force > accelerometerManager.getThreshold()) {
                    if (now - lastShake >= accelerometerManager.getInterval()) {
                        // trigger shake event
                        accelerometerManager.getListener().onShake(force);
                    }
                    lastShake = now;
                }
                lastX = x;
                lastY = y;
                lastZ = z;
                lastUpdate = now;
            }
        }
        // trigger change event
        accelerometerManager.getListener().onAccelerationChanged(x, y, z);
    }

}
