package com.android.owarn.a3dpong.object;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;

import java.util.Random;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static com.android.owarn.a3dpong.util.Lang.A_POSITION;
import static com.android.owarn.a3dpong.util.Lang.U_COLOR;
import static com.android.owarn.a3dpong.util.Lang.U_MATRIX;

/**
 * Created by Oscar Warne on 27/06/2018 for 3DPong.
 */
public class DirectionHint extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uColorLocation;

    private static final float[] vertexData =
            {
                    0.0f, 0.0f, 0.0f,
                    1.0f, 1.0f, 0.0f,
            };
    private static final float[] normalData =
            {
                    //No normal data
            };

    private float startX, startY, lengthX, lengthY;

    private long timeAtStart, nanosecondsToComplete, nanosecondPadding;
    private float angleToReach;

    private boolean visible;
    public DirectionHint(Context c, float startX, float startY, float lengthX, float lengthY, GameState gs) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

       this.startX = startX;
       this.startY = startY;
       this.lengthX = lengthX;
       this.lengthY = lengthY;

        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uColorLocation = shader.getUniformLocation(U_COLOR);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);


        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
    }
    @Override
    public void Start() {
        super.Start();
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        if(!visible)
        {
            return;
        }

        float [] modelViewProjectionMatrix = positionObject(startX, startY,0.015f, viewProjectionMatrix, lengthX, lengthY, 1.0f);

        shader.useProgram();

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);

        glUniform4f(uColorLocation, Lang.three.nR,Lang.three.nG,Lang.three.nB,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        glLineWidth(3);

        glDrawArrays(GL_LINES, 0, 2);
        glDrawArrays(GL_POINTS, 1, 1);
    }
    @Override
    public void update() {
        super.update();
        if(System.nanoTime() < timeAtStart + nanosecondsToComplete)
        {
            double currentRotation = linearInterpolation(angleToReach, System.nanoTime() - timeAtStart, nanosecondsToComplete);
            setLine(startY, startY, currentRotation, 0.2f);
        }
        else {
            setLine(startY, startY, angleToReach, 0.2f);
        }
        if(System.nanoTime() > timeAtStart + nanosecondsToComplete + nanosecondPadding)
        {
            Log.e("DHint","noooooo");
            visible = false;
        }
    }
    private void setLine(float startX, float startY, double rotationInDegrees, float length)
    {
        this.startX = startX;
        this.startY = startY;
        double angleInRadians = Math.toRadians(rotationInDegrees);
        lengthX = (float) Math.sin(angleInRadians) * length;
        lengthY = (float) Math.cos(angleInRadians) * length;
    }
    public void setRotationAndTime(float rotationInDegrees, float secondsToComplete, float secondsPadding)
    {
        Random r = new Random();
        int rots = r.nextInt(5);
        angleToReach = (rots * 360) + rotationInDegrees;
        nanosecondsToComplete = (long) secondsToComplete * 1000000000L;
        nanosecondPadding = (long) secondsPadding * 1000000000L;
        timeAtStart = System.nanoTime();
    }

    private double linearInterpolation(double finalValue, long nanoSecondsThrough, long nanosecondsToComplete)
    {
        double secondsThrough = nanoSecondsThrough / 1000000000d;
        double secondsToComplete = nanosecondsToComplete / 1000000000d;
        return finalValue * (float) (secondsThrough / secondsToComplete);
    }
    public void toggleVisible()
    {
        visible = !visible;
        Log.e("DHint", String.valueOf(visible));
    }
}

