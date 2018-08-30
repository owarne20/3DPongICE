package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Point;

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
 * Created by Oscar Warne on 30/05/2018 for 3DPong.
 */
public class Paddle extends GameObject{

    private static final float width = 0.2f, depth = 0.06f, height = 0.06f;

    private final int aNormalLocation, aPositionLocation, uMatrixLocation, uLightPositionLocation, uModelViewMatrixLocation, uColorLocation;

    private final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    private final float[] lightPosInWorldSpace = new float[4];

    private final float[] lightPosInEyeSpace = new float[4];

    private float touchedX = 0;

    private boolean isDummy;

    private static final float[] vertexData =
            {
                    //Front face
                    -width,  height, depth,
                    -width, -height, depth,
                     width, height, depth,
                    -width, -height, depth,
                     width, -height, depth,
                     width, height, depth,

                    // Right face
                    width,  height, depth,
                    width, -height, depth,
                    width,  height, -depth,
                    width, -height, depth,
                    width, -height, -depth,
                    width,  height, -depth,

                    // Back face
                    width, height, -depth,
                    width, -height, -depth,
                    -width, height, -depth,
                    width, -height, -depth,
                    -width, -height, -depth,
                    -width, height, -depth,

                    // Left face
                    -width, height, -depth,
                    -width, -height, -depth,
                    -width, height, depth,
                    -width, -height, -depth,
                    -width, -height, depth,
                    -width, height, depth,

                    // Top face
                    -width, height, -depth,
                    -width, height, depth,
                    width, height, -depth,
                    -width, height, depth,
                    width, height, depth,
                    width, height, -depth,

                    // Bottom face
                    width, -height, -depth,
                    width, -height, depth,
                    -width, -height, -depth,
                    width, -height, depth,
                    -width, -height, depth,
                    -width, -height, -depth,

            };
    private static final float[] normalData =
            {
                    // Front face
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 1.0f,

                    // Right face
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,
                    1.0f, 0.0f, 0.0f,

                    // Back face
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,
                    0.0f, 0.0f, -1.0f,

                    // Left face
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,
                    -1.0f, 0.0f, 0.0f,

                    // Top face
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,
                    0.0f, 1.0f, 0.0f,

                    // Bottom face
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
                    0.0f, -1.0f, 0.0f,
            };

    public Point position;

    private boolean isPlayer = false;

    private float r, b, g;

    public Paddle(Context c, float y, GameState gs) {
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), vertexData, normalData, gs);

        position = new Point(0.0f, y, depth);

        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aNormalLocation = shader.getAttribLocation(A_NORMAL);
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);
        uLightPositionLocation = shader.getUniformLocation(U_LIGHTPOS);
        uModelViewMatrixLocation = shader.getUniformLocation(U_MVMATRIX);
        uColorLocation = shader.getUniformLocation(U_COLOR);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
        this.r = Lang.six.nR;
        this.g = Lang.six.nG;
        this.b = Lang.six.nB;
    }
    @Override
    public void Start() {
        super.Start();
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        float [] modelViewProjectionMatrix = positionObject(position.x, position.y, position.z, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);
        float [] modelViewMatrix = positionObject(position.x, position.y, 0, viewMatrix, 1.0f, 1.0f, 1.0f);
        float [] lightModelMatrix = new float[16];

        setIdentityM(lightModelMatrix, 0);
        translateM(lightModelMatrix, 0, 0.0f, 0.0f, 0.7f);

        multiplyMV(lightPosInWorldSpace, 0, lightModelMatrix, 0, lightPosInModelSpace, 0);
        multiplyMV(lightPosInEyeSpace, 0, viewMatrix, 0, lightPosInWorldSpace, 0);

        shader.useProgram();

        glUniform3f(uLightPositionLocation, lightPosInEyeSpace[0], lightPosInEyeSpace[1], lightPosInEyeSpace[2]);

        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
        glUniform4f(uColorLocation, r, g, b,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
        glDrawArrays(GL_TRIANGLES, 0, 36);
    }
    @Override
    public void update() {
        super.update();
        if(isDummy)
        {
            if(Math.abs(touchedX - position.x) <  0.01f)
            {
                return;
            }
            if(touchedX > position.x){
                position.x += deltaTime;
            } else if(touchedX < position.x){
                position.x -= deltaTime;
            }
        }
        if(Math.abs(touchedX - position.x) <  0.01f || !isPlayer){
            return;
        }
        if(touchedX > position.x){
            position.x += deltaTime;
        } else if(touchedX < position.x){
            position.x -= deltaTime;
        }
    }

    public void setTouchX(float touchedX){
        this.touchedX = touchedX;
    }

    public float getHeight()
    {
        return height;
    }
    public float getWidth()
    {
        return width;
    }

    public void setPlayer()
    {
        isPlayer = true;
    }

    public void setDummy()
    {
        isDummy = true;
    }

    public void setColour(Lang.Color color)
    {
        this.r = color.nR;
        this.g = color.nG;
        this.b = color.nB;
    }
}
