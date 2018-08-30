package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.android.owarn.a3dpong.util.Lang.A_NORMAL;
import static com.android.owarn.a3dpong.util.Lang.A_POSITION;
import static com.android.owarn.a3dpong.util.Lang.U_COLOR;
import static com.android.owarn.a3dpong.util.Lang.U_LIGHTPOS;
import static com.android.owarn.a3dpong.util.Lang.U_MATRIX;
import static com.android.owarn.a3dpong.util.Lang.U_MVMATRIX;

/**
 * Created by Oscar Warne on 23/05/2018 for 3DPong.
 */
public class Border extends GameObject {

    private final int aNormalLocation, aPositionLocation, uMatrixLocation, uModelViewMatrixLocation, uLightPositionLocation, uColorLocation;

    private static final float[] positionData =
            {
                    //Right Wall
                     0.7f,  1.0f, 0.0f,
                     0.7f, -1.0f, 0.0f,
                     0.7f, -1.0f, 0.12f,

                     0.7f,  1.0f, 0.0f,
                     0.7f, -1.0f, 0.12f,
                     0.7f,  1.0f, 0.12f,

                    //Left Wall
                    -0.7f, -1.0f,  0.0f,
                    -0.7f,  1.0f,  0.0f,
                    -0.7f,  1.0f, 0.12f,

                    -0.7f,  1.0f, 0.12f,
                    -0.7f, -1.0f, 0.12f,
                    -0.7f, -1.0f,  0.0f,

                    //Top Wall
                    -0.7f, 1.0f,  0.0f,
                     0.7f, 1.0f,  0.0f,
                     0.7f, 1.0f, 0.12f,

                    -0.7f, 1.0f,  0.0f,
                     0.7f, 1.0f, 0.12f,
                    -0.7f, 1.0f, 0.12f,

                    //Bottom Wall
                    -0.7f, -1.0f,  0.0f,
                     0.7f, -1.0f,  0.0f,
                     0.7f, -1.0f, 0.12f,

                    -0.7f, -1.0f,  0.0f,
                     0.7f, -1.0f, 0.12f,
                    -0.7f, -1.0f, 0.12f,

                    // Ceiling
                     0.7f,  1.0f, 0.12f,
                     0.7f, -1.0f, 0.12f,
                     4.0f, -2.0f, 0.12f,
                     4.0f, -2.0f, 0.12f,
                     0.7f,  1.0f, 0.12f,
                     4.0f,  2.0f, 0.12f,

                     4.0f,  2.0f, 0.12f,
                    -0.7f,  1.0f, 0.12f,
                     0.7f,  1.0f, 0.12f,
                     4.0f,  2.0f, 0.12f,
                    -4.0f,  2.0f, 0.12f,
                    -0.7f,  1.0f, 0.12f,

                    -4.0f,  2.0f, 0.12f,
                    -0.7f, -1.0f, 0.12f,
                    -0.7f,  1.0f, 0.12f,
                    -4.0f,  2.0f, 0.12f,
                    -4.0f, -2.0f, 0.12f,
                    -0.7f, -1.0f, 0.12f,

                    -4.0f, -2.0f, 0.12f,
                     0.7f, -1.0f, 0.12f,
                    -0.7f, -1.0f, 0.12f,
                    -4.0f, -2.0f, 0.12f,
                     4.0f, -2.0f, 0.12f,
                     0.7f, -1.0f, 0.12f,


            };

    private static final float[] normalData =
            {
                    //Right Wall
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,

                    //Left Wall
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,

                    //Top Wall
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,

                    //Bottom Wall
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,

                    //Ceiling
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,

                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,

                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,

                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,

            };

    private final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    private final float[] lightPosInWorldSpace = new float[4];

    private final float[] lightPosInEyeSpace = new float[4];

    public Border(Context c, GameState gs) {
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), positionData, normalData, gs);
        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aNormalLocation = shader.getAttribLocation(A_NORMAL);
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);
        uLightPositionLocation = shader.getUniformLocation(U_LIGHTPOS);
        uModelViewMatrixLocation = shader.getUniformLocation(U_MVMATRIX);
        uColorLocation = shader.getUniformLocation(U_COLOR);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        float [] modelViewProjectionMatrix = positionObject(0, 0, 0, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);
        float [] modelViewMatrix = positionObject(0, 0, 0, viewMatrix, 1.0f, 1.0f, 1.0f);
        float [] lightModelMatrix = new float[16];

        setIdentityM(lightModelMatrix, 0);
        translateM(lightModelMatrix, 0, 0.0f, 0.0f, 0.7f);

        multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
        multiplyMV(lightPosInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0);

        shader.useProgram();

        glUniform3f(uLightPositionLocation, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
        glUniform4f(uColorLocation, Lang.six.nR,Lang.six.nG,Lang.six.nB,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);

        glDrawArrays(GL_TRIANGLES, 0, 48);

    }

    @Override
    public void Start() {
        super.Start();
    }
}
