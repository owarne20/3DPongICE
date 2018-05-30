package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.shader.ShaderCode;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform3f;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static com.android.owarn.a3dpong.util.Util.A_NORMAL;
import static com.android.owarn.a3dpong.util.Util.A_POSITION;
import static com.android.owarn.a3dpong.util.Util.U_COLOR;
import static com.android.owarn.a3dpong.util.Util.U_LIGHTPOS;
import static com.android.owarn.a3dpong.util.Util.U_MATRIX;
import static com.android.owarn.a3dpong.util.Util.U_MVMATRIX;

/**
 * Created by Oscar Warne on 18/05/2018 for 3DPong.
 */
public class GameBoard extends GameObject {

    private final int aPositionLocation, uMatrixLocation, uLightPositionLocation, uModelViewMatrixLocation, aNormalLocation, uColorLocation;

    private static final float[] vertexData =
            {
                    -0.7f,  1f, 0f,
                    -0.7f, -1f, 0f,
                     0.7f,  1f, 0f,
                    -0.7f, -1f, 0f,
                     0.7f, -1f, 0f,
                     0.7f,  1f, 0f

            };
    private static final float[] normalData =
            {
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

    public GameBoard(Context c) {
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), vertexData, normalData);

        aPositionLocation = shader.getAttribLocation(A_POSITION);
        aNormalLocation = shader.getAttribLocation(A_NORMAL);

        uLightPositionLocation = shader.getUniformLocation(U_LIGHTPOS);
        uModelViewMatrixLocation = shader.getUniformLocation(U_MVMATRIX);
        uMatrixLocation = shader.getUniformLocation(U_MATRIX);
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
        float [] modelViewProjectionMatrix = positionObject(0, 0, 0, viewProjectionMatrix, 0f, 0f, 0f, 0f);
        float [] modelViewMatrix = positionObject(0, 0, 0, viewMatrix, 0f, 0f, 0f, 0f);
        float [] lightModelMatrix = new float[16];

        setIdentityM(lightModelMatrix, 0);
        translateM(lightModelMatrix, 0, 0.0f, 0.0f, 0.7f);

        multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
        multiplyMV(lightPosInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0);

        shader.useProgram();

        glUniform3f(uLightPositionLocation, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
        glUniform4f(uColorLocation, 0.8f,0.8f,0.8f,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);

        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
}
