package com.android.owarn.a3dpong.object;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Point;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.android.owarn.a3dpong.util.Lang.A_POSITION;
import static com.android.owarn.a3dpong.util.Lang.U_CIRCLEPOS;
import static com.android.owarn.a3dpong.util.Lang.U_COLOR;
import static com.android.owarn.a3dpong.util.Lang.U_INVERT;
import static com.android.owarn.a3dpong.util.Lang.U_MATRIX;
import static com.android.owarn.a3dpong.util.Lang.U_MVMATRIX;
import static com.android.owarn.a3dpong.util.Lang.U_TIME;

/**
 * Created by Oscar Warne on 23/05/2018 for 3DPong.
 */
public class CircularTransition extends GameObject {

    private final int aPositionLocation, uMatrixLocation, uModelViewMatrixLocation, uCirclePositionLocation, uColorLocation, uTimeLocation, uInvertLocation;

    private static final float[] positionData =
            {
                    //Cover the screen
                    -2.0f,  2.0f, 0.2f,
                    -2.0f, -2.0f, 0.2f,
                     2.0f, -2.0f, 0.2f,
                     2.0f,  2.0f, 0.2f,
                     2.0f, -2.0f, 0.2f,
                    -2.0f,  2.0f, 0.2f,

            };

    private static final float[] normalData =
            {
                    //No normal data
            };

    private final float[] circlePosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    private final float[] circlePosInWorldSpace = new float[4];

    private final float[] circlePosInEyeSpace = new float[4];

    private float animTime = 0.1f;

    private int invert = 1;

    private boolean visible;

    private Point startPoint;

    public CircularTransition(Context c, GameState gs) {
        super(c, new ShaderCode(c, R.raw.circular_transition_fragment_shader, R.raw.circular_transition_vertex_shader), positionData, normalData, gs);
        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);
        uCirclePositionLocation = shader.getUniformLocation(U_CIRCLEPOS);
        uModelViewMatrixLocation = shader.getUniformLocation(U_MVMATRIX);
        uColorLocation = shader.getUniformLocation(U_COLOR);
        uTimeLocation = shader.getUniformLocation(U_TIME);
        uInvertLocation = shader.getUniformLocation(U_INVERT);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        startPoint = new Point(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void update() {
        super.update();
        animTime += (deltaTime * 2.0f);
        if(animTime > 2.5f)
        {
            animTime = 0.0f;
            if(invert == 1){
                invert = 0;
            } else {
                visible = false;
                invert = 1;
            }

        }
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        if(!visible)
        {
            return;
        }
        float [] modelViewProjectionMatrix = positionObject(0, 0, 0, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);
        float [] modelViewMatrix = positionObject(0, 0, 0, viewMatrix, 1.0f, 1.0f, 1.0f);
        float [] circleModelMatrix = new float[16];

        setIdentityM(circleModelMatrix, 0);
        translateM(circleModelMatrix, 0, startPoint.x, startPoint.y, startPoint.z);

        multiplyMV(circlePosInWorldSpace, 0, circleModelMatrix, 0, circlePosInModelSpace, 0);
        multiplyMV(circlePosInEyeSpace, 0, viewMatrix, 0, circlePosInWorldSpace, 0);

        shader.useProgram();

        glUniform3f(uCirclePositionLocation, circlePosInEyeSpace[0], circlePosInEyeSpace[1], circlePosInEyeSpace[2]);

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
        glUniform1f(uTimeLocation, animTime);
        glUniform1i(uInvertLocation, invert);
        glUniform4f(uColorLocation, Lang.two.nR,Lang.two.nG,Lang.two.nB,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        glDrawArrays(GL_TRIANGLES, 0, 6);

    }

    @Override
    public void Start() {
        super.Start();
    }

    public void doAnimation(Point p)
    {
        Log.e("Anim", String.valueOf(p.x));
        animTime = 0.1f;
        invert = 1;
        visible = true;
        startPoint = p;
        startPoint.z = 0.0f;
    }
}
