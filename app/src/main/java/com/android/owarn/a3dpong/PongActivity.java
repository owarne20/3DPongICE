package com.android.owarn.a3dpong;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PongActivity extends Activity {

    private GLSurfaceView pongGL;
    private boolean rendererSet = false;


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
        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();
        final boolean supportsES2 = configurationInfo.reqGlEsVersion >= 0x20000;
        PongRenderer pongRenderer = new PongRenderer(this);

        if(supportsES2){
            pongGL.setEGLContextClientVersion(2);
            pongGL.setRenderer(pongRenderer);
            rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.",
                   Toast.LENGTH_LONG).show();
            return;
        }

        pongGL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
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
                    }
                    return true;
                }
                else
                {
                    return false;
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
            pongGL.onPause();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
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
}
