package org.yestech.celow.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 *
 *
 */
public class AccelerometerManager {
    /**
     * Accuracy configuration
     */
    private float threshold = 0.2f;
    private int interval = 1000;

    private Sensor sensor;
    private SensorManager sensorManager;
    private Context context;

    // you could use an OrientationListener array instead
    // if you plans to use more than one listener
    private AccelerometerListener listener;

    /**
     * The listener that listen to events from the accelerometer listener
     */
    private SensorEventListener sensorEventListener =new AccelerometerSensorEventListener(this);


    /**
     * indicates whether or not Accelerometer Sensor is supported
     */
    private Boolean supported;
    /**
     * indicates whether or not Accelerometer Sensor is running
     */
    private static boolean running = false;

    public AccelerometerManager(Context context) {
        this.context = context;
    }

    public AccelerometerListener getListener() {
        return listener;
    }

    public void setListener(AccelerometerListener listener) {
        this.listener = listener;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * Returns true if the manager is listening to orientation changes
     */
    public boolean isListening() {
        return running;
    }

    /**
     * Unregisters listeners
     */
    public void stopListening() {
        running = false;
        try {
            if (sensorManager != null && sensorEventListener != null) {
                sensorManager.unregisterListener(sensorEventListener);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Returns true if at least one Accelerometer sensor is available
     */
    public boolean isSupported() {
        if (supported == null) {
            if (context != null) {
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
                supported = new Boolean(sensors.size() > 0);
            } else {
                supported = Boolean.FALSE;
            }
        }
        return supported;
    }

    /**
     * Configure the listener for shaking
     *
     * @param threshold minimum acceleration variation for considering shaking
     * @param interval  minimum interval between to shake events
     */
    public void configure(int threshold, int interval) {
        this.threshold = threshold;
        this.interval = interval;
    }

    /**
     * Registers a listener and start listening
     *
     * @param accelerometerListener callback for accelerometer events
     */
    public void startListening(AccelerometerListener accelerometerListener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            running = sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
            listener = accelerometerListener;
        }
    }

    /**
     * Configures threshold and interval
     * And registers a listener and start listening
     *
     * @param accelerometerListener callback for accelerometer events
     * @param threshold             minimum acceleration variation for considering shaking
     * @param interval              minimum interval between to shake events
     */
    public void startListening(AccelerometerListener accelerometerListener,int threshold, int interval) {
        configure(threshold, interval);
        startListening(accelerometerListener);
    }
}
