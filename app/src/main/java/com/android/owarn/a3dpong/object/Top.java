package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static com.android.owarn.a3dpong.util.Lang.A_POSITION;
import static com.android.owarn.a3dpong.util.Lang.U_COLOR;
import static com.android.owarn.a3dpong.util.Lang.U_MATRIX;

/**
 * Created by Oscar Warne on 27/06/2018 for 3DPong.
 */
public class Top extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uColorLocation;

    private static final float[] vertexData =
            {
                    -4.0f,  1.0f, 0.13f,
                     4.0f,  1.0f, 0.13f,
                     4.0f,  7.0f, 0.13f,
                    -4.0f,  1.0f, 0.13f,
                     4.0f,  7.0f, 0.13f,
                    -4.0f,  7.0f, 0.13f,
            };
    private static final float[] normalData =
            {
                    //No normal data
            };


    public Top(Context c, GameState gs) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

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
        float [] modelViewProjectionMatrix = positionObject(0, 0,0, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);

        shader.useProgram();

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);

        glUniform4f(uColorLocation, Lang.six.nR,Lang.six.nG,Lang.six.nB,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);


        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
    @Override
    public void update() {
        super.update();
    }
}

