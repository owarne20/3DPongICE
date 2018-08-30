package com.android.owarn.a3dpong;
/**
 * Created by Oscar Warne on 27/06/2018 for 3DPong.
 */

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.android.owarn.a3dpong.gameState.ServerBrowser;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.gameState.InGame;
import com.android.owarn.a3dpong.gameState.Title;
import com.android.owarn.a3dpong.util.FPSCounter;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Point;
import com.android.owarn.a3dpong.util.Ray;
import com.android.owarn.a3dpong.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LESS;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setLookAtM;

public class PongRenderer implements GLSurfaceView.Renderer {

    private Context context;

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];
    private float[] viewProjectionMatrix = new float[16];

    private Lang.GameState currentState;
    private GameState currentGameState;
    private FPSCounter counter;
    private float rotationX, rotationY;
    private float offsetX, offsetY;
    private boolean glStartFlag = true;

    public PongRenderer(Context context)
    {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(Lang.four.nR, Lang.four.nG, Lang.four.nB, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        // Create perspective project with required aspect and field of vision
        perspectiveM(projectionMatrix, 50, (float) width / (float) height, 1f, 100f);
        setLookAtM(viewMatrix, 0, 0f, 0.0f, 3f, 0f, 0f, 0f, 0f, 1f, 0f);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if(glStartFlag)
        {
            glStartFlag = false;
            changeGameState(Lang.GameState.TITLE);
            this.counter = new FPSCounter();
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        setLookAtM(viewMatrix, 0, 0f, 0.0f, 3f, 0f, 0f, 0f, 0f, 1f, 0f);
        rotateM(viewMatrix, 0, rotationX - offsetX, 0.0f, 1.0f, 0.0f);
        rotateM(viewMatrix, 0, rotationY - offsetY, 1.0f, 0.0f, 0.0f);

        counter.logFrame();
        // Combine the view and perspective projection matrices
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        // Enable depth test
        glEnable(GL_DEPTH_TEST);

        //Enable blending
        glEnable(GL_BLEND);

        // Accept fragment if it closer to the camera than the former one
        glDepthFunc(GL_LESS);

        //Set blending mode
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Update gameState which forwards details to objects
        final Lang.GameState cs = currentState;
        currentGameState.update();
        if (cs != currentState){
            //Do not draw if update for state has not been done (if update has not been done there may be null objects)
            return;
        }
        currentGameState.draw(viewProjectionMatrix, viewMatrix);
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
    public void changeGameState(Lang.GameState gs)
    {

        // WARNING: ONLY CALL IN UPDATE!

        if (currentState == gs)
            return;

        if(currentGameState != null)
            currentGameState.cleanUp();

        currentGameState = enumToGameState(gs);
        currentGameState.initialise();
        currentState = gs;
    }
    private GameState enumToGameState(Lang.GameState gameState)
    {
        if(gameState == Lang.GameState.IN_GAME){
            return new InGame(context, this);
        }
        if(gameState == Lang.GameState.TITLE){
            return new Title(context, this);
        }
        if(gameState == Lang.GameState.SERVER_BROWSER){
            return new ServerBrowser(context, this);
        }
        if(gameState == Lang.GameState.exit)
        {
            System.exit(0);
        }
        Log.e("EnumToGameState", "Could not convert enum to gameState");
        return null;
    }

    public void handleTouchPress(float normalisedX, float normalisedY)
    {
        currentGameState.handleTouchPress(normalisedX, normalisedY, convertNormalised2DPointToRay(normalisedX, normalisedY));
    }

    public void handleTouchDrag(float normalisedX, float normalisedY)
    {
        currentGameState.handleTouchDrag(normalisedX, normalisedY, convertNormalised2DPointToRay(normalisedX, normalisedY));
    }

    public void handleTouchRelease(float normalisedX, float normalisedY)
    {
        currentGameState.handleTouchRelease(normalisedX, normalisedY, convertNormalised2DPointToRay(normalisedX, normalisedY));
    }

    public Ray convertNormalised2DPointToRay(float normalisedX, float normalisedY)
    {
        final float[] nearPointNdc = {normalisedX, normalisedY, -1, 1};
        final float[] farPointNdc = {normalisedX, normalisedY, 1, 1};

        final float[] nearPointWorld = new float[4];
        final float[] farPointWorld = new float[4];

        multiplyMV(nearPointWorld, 0, invertedViewProjectionMatrix, 0, nearPointNdc, 0);
        multiplyMV(farPointWorld, 0, invertedViewProjectionMatrix, 0, farPointNdc, 0);
        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Point nearPointRay = new Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Point farPointRay = new Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);
        return new Ray(nearPointRay, vectorBetween(nearPointRay, farPointRay));
    }

    public void divideByW(float[] vector) {
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }

    public Vector vectorBetween(Point from, Point to)
    {
        return new Vector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
        );
    }
    public void setOrientation(float[] orientation)
    {
        rotationX = limit((((rotationX * 20) + (orientation[2] * 30.0f)) / 21.0f), -35, 35);
        rotationY = limit((((rotationY * 20) + (orientation[1] * 20.0f)) / 21.0f), -15, 15);
    }
    public float limit(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void doubleTap()
    {
        offsetX = rotationX;
        offsetY = rotationY;
    }

}
