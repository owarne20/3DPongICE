package com.android.owarn.a3dpong;
/* 
Property of Oscar Warne (Pls don't steal my code)
*/

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.android.owarn.a3dpong.object.Border;
import com.android.owarn.a3dpong.object.Cube;
import com.android.owarn.a3dpong.object.GameBoard;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LESS;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setLookAtM;

public class PongRenderer implements GLSurfaceView.Renderer {

    private Context context;

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] viewProjectionMatrix = new float[16];

    private Cube second;
    private GameBoard gb;
    private Border b;

    public PongRenderer(Context context)
    {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Initialise game objects
        b = new Border(context);
        b.Start();
        second = new Cube(0.2f, 0.2f, context);
        second.Start();
        gb = new GameBoard(context);
        gb.Start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        // Create perspective project with required aspect and field of vision
        perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 100f);
        setLookAtM(viewMatrix, 0, 0f, 0.0f, 3f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // Combine the view and perspective projection matrices
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        //rotateM(viewMatrix, 0, 0.2f, 0f, 1f, 0f);
        // Enable depth test
        glEnable(GL_DEPTH_TEST);
        // Accept fragment if it closer to the camera than the former one
        glDepthFunc(GL_LESS);
        //glEnable(GL_CULL_FACE);
        //glCullFace(GL_BACK);
        // Update required objects.
        b.update();
        b.draw(viewProjectionMatrix, viewMatrix);
        gb.update();
        gb.draw(viewProjectionMatrix, viewMatrix);
        second.update();
        second.draw(viewProjectionMatrix, viewMatrix);
    }
    public void perspectiveM(float[] m, float yFovInDegrees, float aspect, float n, float f)
    {

        // Used in place of glitched static method in openGL library

        final float angleInRadians = (float) (yFovInDegrees * Math.PI / 180);

        final float a = (float) (1.0 / Math.tan(angleInRadians / 2.0));

        m[0] = a / aspect;
        m[1] = 0f;
        m[2] = 0f;
        m[3] = 0f;

        m[4] = 0f;
        m[5] = a;
        m[6] = 0f;
        m[7] = 0f;

        m[8] = 0f;
        m[9] = 0f;
        m[10] = -((f + n) / (f - n));
        m[11] = -1f;

        m[12] = 0f;
        m[13] = 0f;
        m[14] = -((2f * f * n) / (f - n));
        m[15] = 0f;
    }
}
