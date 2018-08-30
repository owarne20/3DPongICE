package com.android.owarn.a3dpong;

/**
 * Created by Oscar Warne on 27/06/2018 for 3DPong.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PongActivity extends Activity implements SensorEventListener {

    private GLSurfaceView pongGL;
    private boolean rendererSet = false;

    private SensorManager sensorManager;

    private PongRenderer pongRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        pongGL = new GLSurfaceView(this);
        final ActivityManager activityManager = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();
        final boolean supportsES2 = configurationInfo.reqGlEsVersion >= 0x20000;
        pongRenderer = new PongRenderer(this);

        if(supportsES2){
            pongGL.setEGLContextClientVersion(2);
            pongGL.setRenderer(pongRenderer);
            rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        final Context context = this;
        final GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                pongRenderer.doubleTap();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }
        };

        final GestureDetector detector = new GestureDetector(this, listener);

        detector.setOnDoubleTapListener(listener);
        detector.setIsLongpressEnabled(true);

        pongGL.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                boolean r = detector.onTouchEvent(event);
                if(event != null) {
                    final float normalisedX = (event.getX() / (float) view.getWidth()) * 2 - 1;
                    final float normalisedY = -((event.getY() / (float) view.getHeight()) * 2 - 1);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        pongGL.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                //System.out.println(normalisedX + " " + normalisedY);
                                pongRenderer.handleTouchPress(normalisedX, normalisedY);
                            }
                        });
                        //167
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        pongGL.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                //System.out.println(normalisedX + " " + normalisedY);
                                pongRenderer.handleTouchDrag(normalisedX, normalisedY);
                            }
                        });
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        pongGL.queueEvent(new Runnable() {
                            @Override
                            public void run() {
                                //System.out.println(normalisedX + " " + normalisedY);
                                pongRenderer.handleTouchRelease(normalisedX, normalisedY);
                            }
                        });
                    }
                    return true;

                }
                else
                {
                    return r;
                }
            }
        });

        setContentView(pongGL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(rendererSet){
            System.out.println("Paused!");
            System.exit(0);
            pongGL.onPause();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
        if(rendererSet){
            System.out.println("Resumed!");
            pongGL.onResume();
        }
    }

     @Override
        protected void onStop() {
            super.onStop();
        System.out.println("Stopping");
    }

    float[] gravity;
    float[] geomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;
        if (gravity != null && geomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                pongRenderer.setOrientation(orientation);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
