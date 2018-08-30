package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Point;
import com.android.owarn.a3dpong.util.Rect;
import com.android.owarn.a3dpong.util.Vector;

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
 * Created by Oscar Warne on 16/05/2018 for 3DPong.
 */
public class Cube extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uLightPositionLocation, uModelViewMatrixLocation, aNormalLocation, uColorLocation;
    private static final float width = 0.06f;


    private float rightBound = 0.7f, leftBound = -0.7f, nearBound = -1f, farBound = 1f;
    private float speed;
    private Point position;
    private Vector direction;
    private Point lastPosition;
    private Point[] previousPositions;
    private int framesToAdd;
    private boolean visible;
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

    private float r, g, b;

    public Cube(float positionX, float positionY, Context c, GameState gs) {
        // Send required information to superclass for setup
        super(c, new ShaderCode(c, R.raw.simple_fragment_shader, R.raw.simple_vertex_shader), vertexData3D, normalData, gs);

        // Set the start position to the position parsed through the constructor
        position = new Point(positionX, positionY, width);
        lastPosition = new Point(positionX, positionY, width);
        previousPositions = new Point[10];
        framesToAdd = 0;
        // Get the attribute and uniform locations from the shader for our object to use for later
        aNormalLocation = shader.getAttribLocation(A_NORMAL);
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);
        uLightPositionLocation = shader.getUniformLocation(U_LIGHTPOS);
        uModelViewMatrixLocation = shader.getUniformLocation(U_MVMATRIX);
        uColorLocation = shader.getUniformLocation(U_COLOR);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        shader.setAtrribPointer(0, aNormalLocation, 3, 12, false);
        r = Lang.two.nR;
        g = Lang.two.nG;
        b = Lang.two.nB;
    }

    @Override
    public void Start()
    {
        super.Start();
        direction = new Vector(0, 0, 0);
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {
        if(!visible)
        {
            return;
        }
        float [] modelViewProjectionMatrix = positionObject(position.x, position.y, position.z, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);
        float [] modelViewMatrix = positionObject(position.x, position.y, position.z, viewMatrix, 1.0f, 1.0f, 1.0f);
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

        //For the shadowed cube
        for (int i = 0; i < previousPositions.length; i++) {

            if (previousPositions[i] == null) {
                //Check for null positions to act as safety net for null pointers
                return;
            }

            double lightness = ((float)previousPositions.length - (i + 3.3d)) / (float) previousPositions.length;
            modelViewProjectionMatrix = positionObject(previousPositions[i].x, previousPositions[i].y, previousPositions[i].z, viewProjectionMatrix, 1.0f, 1.0f, 1.0f);
            modelViewMatrix = positionObject(previousPositions[i].x, previousPositions[i].y, previousPositions[i].z, viewMatrix, 1.0f, 1.0f, 1.0f);
            glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
            glUniformMatrix4fv(uModelViewMatrixLocation, 1, false, modelViewMatrix, 0);
            glUniform4f(uColorLocation, Lang.six.nR, Lang.six.nG, Lang.six.nB, (float) lightness);
            glDrawArrays(GL_TRIANGLES, 0, 36);
        }
    }

    @Override
    public void update() {
        super.update();

        position = position.translate(direction.scale(deltaTime));

        //Collision with paddle
        Paddle p1 = (Paddle) gameState.getGameObject(2);
        Paddle p2 = (Paddle) gameState.getGameObject(3);

        Rect paddle1 = new Rect(p1.position.x, p1.position.y, p1.getWidth(), p1.getHeight());
        Rect paddle2 = new Rect(p2.position.x, p2.position.y, p2.getWidth(), p2.getHeight());
        Rect cube = new Rect(this.position.x, this.position.y, this.width, this.width);
        Rect lastCube = new Rect(this.lastPosition.x, this.lastPosition.y, this.width, this.width);

        if(cube.intersects(paddle1))
        {
            Lang.Side s = cube.sideIntersected(paddle1, lastCube);

            if(s == Lang.Side.left) {
                direction = new Vector(-direction.x, direction.y, 0);
                position.x = Math.min(position.x, paddle1.left + width);
            }
            if(s == Lang.Side.right) {
                direction = new Vector(-direction.x, direction.y, 0);
                position.x = Math.max(position.x, paddle1.right - width);
            }

            if(s == Lang.Side.bottom){
                direction = new Vector(direction.x, -direction.y, 0);
                position.y = Math.min(position.y, paddle1.bottom + width);
            }
            if(s == Lang.Side.top){
                direction = new Vector(direction.x, -direction.y, 0);
                position.y = Math.max(position.y, paddle1.top - width);
            }
        }

        if(cube.intersects(paddle2))
        {
            Lang.Side s = cube.sideIntersected(paddle2, lastCube);

            if(s == Lang.Side.left){
                direction = new Vector(-direction.x, direction.y, 0);
                position.x = Math.min(position.x, paddle2.left + width);
            }
            if(s == Lang.Side.right){
                direction = new Vector(-direction.x, direction.y, 0);
                position.x = Math.max(position.x, paddle2.right - width);
            }

            if(s == Lang.Side.bottom){
                direction = new Vector(direction.x, -direction.y, 0);
                position.y = Math.min(position.y, paddle2.bottom + width);
            }
            if(s == Lang.Side.top){
                direction = new Vector(direction.x, -direction.y, 0);
                position.y = Math.max(position.y, paddle2.top - width);
            }
        }

        lastPosition = position;

        //Collision with sides
        if(position.x < leftBound + width || position.x > rightBound - width){
            direction = new Vector(-direction.x, direction.y, 0);
            if(position.x < leftBound + width) {
                float correctionX = (leftBound + width) - position.x;
                position.translate(new Vector(correctionX * 2, 0, 0));
            }
            if(position.x > rightBound - width) {
                float correctionX = position.x - (rightBound - width);
                position.translate(new Vector(correctionX * 2, 0, 0));
            }
        }
        if(position.y < nearBound + width || position.y > farBound - width){
            direction = new Vector(direction.x, -direction.y, 0);
            if(position.y < nearBound + width) {
                float correctionY = (nearBound + width) - position.y;
                position.translate(new Vector(0, correctionY * 2, 0));
            }
            if(position.y > farBound - width) {
                float correctionY = position.y - (farBound - width);
                position.translate(new Vector(0, correctionY * 2, 0));
            }
        }

        position = new Point(limit(position.x, leftBound + width, rightBound - width), limit(position.y, nearBound + width, farBound - width), width);

        framesToAdd++;

        if(framesToAdd > 2){
            framesToAdd = 0;
            addPosition(position);
        }

    }

    public float limit(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public void setPositionAndVector(float x, float y, float vX, float vY){
        this.position.x = (position.x * 4 + x) / 5;
        this.position.y = (position.y * 4 + y) / 5;
        this.direction.x = vX;
        this.direction.y = vY;
    }

    public void setPositionAndVectorNoSmooth(float x, float y, float vX, float vY)
    {
        this.position.x = x;
        this.position.y = y;
        this.direction.x = vX;
        this.direction.y = vY;
    }

    public void setVectorAndStartGame(float vX, float vY){
        this.direction.x = vX;
        this.direction.y = vY;
    }

    private void addPosition(Point point)
    {
        Point[] tmp = new Point[previousPositions.length];
        for(int i = 0; i < previousPositions.length; i++)
        {
            if(i == previousPositions.length - 1){
                break;
            }
            tmp[i + 1] = previousPositions[i];
        }
        tmp[0] = point;
        previousPositions = tmp;
    }

    public void toggleVisible()
    {
        visible = !visible;
    }
    public void setVisibility(boolean visible)
    {
        this.visible = visible;
    }

    public void setColor(Lang.Color c)
    {
        r = c.nR;
        g = c.nG;
        b = c.nB;
    }

    public Point getPosition()
    {
        return position;
    }
    public Vector getDirection()
    {
        return direction;
    }
}
