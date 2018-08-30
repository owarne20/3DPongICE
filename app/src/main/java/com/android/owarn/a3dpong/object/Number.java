package com.android.owarn.a3dpong.object;

import android.content.Context;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;

import static android.opengl.GLES20.GL_LINE_STRIP;
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
public class Number extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uColorLocation;

    private static final float charHeight = 0.2f, charWidth = 0.1f, charPadding = 0.04f;

    private float x, y;

    private boolean visible;

    private final int[] charLengths =
            {
                    //Zero
                    5,
                    //One
                    4,
                    //Two
                    6,
                    //Three
                    7,
                    //Four
                    7,
                    //Five
                    6,
                    //Six
                    7,
                    //Seven
                    3,
                    //Eight
                    8,
                    //Nine
                    5
            };

    private static final float[] vertexData =
            {
                    //Zero
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //One
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,

                    //Two
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,

                    //Three
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,

                    //Four
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 6.0f,               0.0f, 0.0f,
                     charWidth / 6.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 6.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 6.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,

                    //Five
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,

                    //Six
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,

                    //Seven
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,

                    //Eight
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,

                    //Nine
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,

            };
    private static final float[] normalData =
            {
                    //No normal data
            };

        private DrawNumber drawNumber;

    public Number(Context c, GameState gs, float x, float y) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uColorLocation = shader.getUniformLocation(U_COLOR);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        //Set number to zero at initialisation to avoid null pointer exception
        setNumber(0);

        this.x = x;
        this.y = y;
    }
    @Override
    public void Start() {
        super.Start();
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix) {

        shader.useProgram();
        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);
        glLineWidth(5);

        if(visible)
        {
            drawNumber.draw(viewProjectionMatrix);
        }

    }

    @Override
    public void update() {
        super.update();
    }

    public void setNumber(int i)
    {
        String iStr = String.valueOf(i);

        int[] digits = new int[iStr.length()];

        for(int j = 0; j < digits.length; j++)
        {
            digits[j] = Integer.valueOf(String.valueOf(iStr.charAt(j)));
        }
        float center = ((((iStr.length() * 2) - 0) * charWidth / 2));

        drawNumber = vpMatrix ->
        {
            for(int k = 0; k < iStr.length(); k++)
            {
                drawNumber(digits[k], x + (k * (charWidth + charPadding)) - center + charWidth, y, vpMatrix);
            }
        };
    }

    private void drawNumber(int i, float x, float y, float[] vpMatrix)
    {
        assert i < 10 && i >= 0;

        float [] modelViewProjectionMatrix = positionObject(x, y,0.1f, vpMatrix, 1.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniform4f(uColorLocation, Lang.two.nR,Lang.two.nG,Lang.two.nB,1.0f);

        int offset = 0;

        for(int j = 0; j < i; j++)
        {
            offset += charLengths[j];
        }

        int length = charLengths[i];

        glDrawArrays(GL_LINE_STRIP, offset, length);

    }

    interface DrawNumber
    {
        void draw(float[] vpMatrix);
    }

    public void toggleVisible()
    {
        visible = !visible;
        //Log.e("Number", "Visible = " + String.valueOf(visible));
    }
    public void setVisibility(boolean visible)
    {
        this.visible = visible;
    }

}

