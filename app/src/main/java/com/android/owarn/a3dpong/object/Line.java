package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;

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
public class Line extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uColorLocation;

    private static final float[] vertexData =
            {
                    0.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
            };
    private static final float[] normalData =
            {
                    //No normal data
            };

    private float startX, length, y;



    public Line(Context c, float startX, float length, float y, GameState gs) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

       this.startX = startX;
       this.length = length;
       this.y = y;

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
        float [] modelViewProjectionMatrix = positionObject(startX, y,0.015f, viewProjectionMatrix, length, 1.0f, 1.0f);

        shader.useProgram();

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);

        glUniform4f(uColorLocation, Lang.two.nR,Lang.two.nG,Lang.two.nB,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        glLineWidth(2);

        glDrawArrays(GL_LINES, 0, 2);
        glDrawArrays(GL_POINTS, 1, 1);
    }
    @Override
    public void update() {
        super.update();
    }
    public void setLine(float startX, float y, float length){
        this.startX = startX;
        this.y = y;
        this.length = length;
    }
}

