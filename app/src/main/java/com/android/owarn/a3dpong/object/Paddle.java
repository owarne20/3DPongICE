package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Vector;

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
 * Created by Oscar Warne on 30/05/2018 for 3DPong.
 */
public class Paddle extends GameObject{

    private final float width = 0.3f, depth = 0.06f, height = 0.06f;

    private final int aNormalLocation, aPositionLocation, uMatrixLocation, uLightPositionLocation, uModelViewMatrixLocation, uColorLocation;

    private final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    private final float[] lightPosInWorldSpace = new float[4];

    private final float[] lightPosInEyeSpace = new float[4];

    private static final float[] vertexData =
            {

            };
    private static final float[] normalData =
            {

            };

    private Vector position;



    public Paddle(Context c) {
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), vertexData, normalData);

        position = new Vector(0.0f, -0.7f, depth);

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
    public void Start() {
        super.Start();
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        float [] modelViewProjectionMatrix = positionObject(position.x, position.y, position.z, viewProjectionMatrix, 0.2f, 1.0f, 0.0f, 0.0f);
        float [] modelViewMatrix = positionObject(position.x, position.y, 0, viewMatrix, 0.2f, 1.0f, 0.0f, 0.0f);
        float [] lightModelMatrix = new float[16];

        setIdentityM(lightModelMatrix, 0);
        translateM(lightModelMatrix, 0, 0.0f, 0.0f, 0.7f);

        multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
        multiplyMV(lightPosInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0);

        shader.useProgram();

        glUniform3f(uLightPositionLocation, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
        glUniform4f(uColorLocation, 0.1f,0.1f,0.2f,0.8f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
    @Override
    public void update() {
        super.update();

    }
}
