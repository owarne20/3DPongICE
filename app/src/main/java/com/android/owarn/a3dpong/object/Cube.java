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
 * Created by Oscar Warne on 16/05/2018 for 3DPong.
 */
public class Cube extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uLightPositionLocation, uModelViewMatrixLocation, aNormalLocation, uColorLocation;
    private static final float width = 0.06f;


    private float rightBound = 0.7f, leftBound = -0.7f, nearBound = -1f, farBound = 1f;
    private float timeSinceStart;
    private float timeatStart;
    private float speed;
    private Vector position;
    private Vector direction;
    private static final float[] vertexData3D = {

            //Make sure each face faces outwards with anti-clockwise winding.

            // Front face
            -width, width, width,
            -width, -width, width,
            width, width, width,
            -width, -width, width,
            width, -width, width,
            width, width, width,

            // Right face
            width, width, width,
            width, -width, width,
            width, width, -width,
            width, -width, width,
            width, -width, -width,
            width, width, -width,

            // Back face
            width, width, -width,
            width, -width, -width,
            -width, width, -width,
            width, -width, -width,
            -width, -width, -width,
            -width, width, -width,

            // Left face
            -width, width, -width,
            -width, -width, -width,
            -width, width, width,
            -width, -width, -width,
            -width, -width, width,
            -width, width, width,

            // Top face
            -width, width, -width,
            -width, width, width,
            width, width, -width,
            -width, width, width,
            width, width, width,
            width, width, -width,

            // Bottom face
            width, -width, -width,
            width, -width, width,
            -width, -width, -width,
            width, -width, width,
            -width, -width, width,
            -width, -width, -width,


    };

    public static final float[] normalData =
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

    private final float[] lightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};

    private final float[] lightPosInWorldSpace = new float[4];

    private final float[] lightPosInEyeSpace = new float[4];

    private float distanceAccurate;
    private float distanceInaccurate;

    public Cube(float positionX, float positionY, Context c) {
        // Send required information to superclass for setup
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), vertexData3D, normalData);

        // Set the start position to the position parsed through the constructor
        position = new Vector(positionX, positionY, width);

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
    public void Start(){
        super.Start();
        speed = 6.0f;
        direction = Vector.random2DNormalisedVector(speed);
        timeatStart = System.nanoTime();
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
        glUniform4f(uColorLocation, 0.1f,0.1f,0.1f,1.0f);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
        glDrawArrays(GL_TRIANGLES, 0, 36);
    }

    @Override
    public void update() {
        super.update();
        timeSinceStart = (System.nanoTime() - timeatStart) / 1000000000.0f;
        //Log.e("Time since start", String.valueOf(timeSinceStart));

        if(position.x < leftBound + width || position.x > rightBound - width){
            direction = new Vector(-direction.x, direction.y, 0);
            if(position.x < leftBound + width) {
                float correctionX = (leftBound + width) - position.x;
                position.translate(new Vector(correctionX, 0, 0));
            }
            if(position.x > rightBound - width) {
                float correctionX = position.x - (rightBound - width);
                position.translate(new Vector(correctionX, 0, 0));
            }
        }
        if(position.y < nearBound + width || position.y > farBound - width){
            direction = new Vector(direction.x, -direction.y, 0);
            if(position.y < nearBound + width) {
                float correctionY = (nearBound + width) - position.x;
                position.translate(new Vector(0, correctionY, 0));
            }
            if(position.y > farBound - width) {
                float correctionY = position.x - (farBound - width);
                position.translate(new Vector(0, correctionY, 0));
            }
        }
        position = new Vector(limit(position.x, leftBound + width, rightBound - width), limit(position.y, nearBound + width, farBound - width), width);
        position = position.translate(direction.scale(deltaTime));
        distanceInaccurate += direction.scale(deltaTime).length();
        distanceAccurate = timeSinceStart * speed;
        System.out.println(distanceAccurate + " " + distanceInaccurate);
    }

    public float limit(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
    private void nonSinusoidalWave(){
        timeSinceStart = (System.nanoTime() - timeatStart) / 1000000000.0f;
        final float precision = 15;
        float x = 0;
        float y = 0;

        for(int i = 1; i < precision; i++)
        {
            final int odd = (i * 2) - 1;
            final boolean minus = (i / 2.0f) / Math.floor(i / 2.0f) == 1;
            y = minus ? y - (float)((Math.sin(odd * timeSinceStart * (2.0f + 0.3242341f))) / (float) ( odd * odd)) : y + (float)((Math.sin(odd * timeSinceStart * (2.0f + 0.3242341f))) / (float) ( odd * odd));
            x = minus ? x - (float)((Math.sin(odd * timeSinceStart * (2.0f - 0.3242341f))) / (float) ( odd * odd)) : x + (float)((Math.sin(odd * timeSinceStart * (2.0f - 0.3242341f))) / (float) ( odd * odd));
        }
        position.x = x * ((0.7f - width) * (float) (8.0f / (Math.PI * Math.PI)));
        position.y = y * ((1.0f - width) * (float) (8.0f / (Math.PI * Math.PI)));
    }
}
