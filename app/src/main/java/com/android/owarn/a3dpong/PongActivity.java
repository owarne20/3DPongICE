package com.android.owarn.a3dpong;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

public class PongActivity extends Activity {

    private GLSurfaceView pongGL;
    private boolean rendererSet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(pongGL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(rendererSet){
            System.out.println("Get Paused!");
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
}
