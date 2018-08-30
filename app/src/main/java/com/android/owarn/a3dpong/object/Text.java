package com.android.owarn.a3dpong.object;

import android.content.Context;
import android.util.Log;

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
public class Text extends GameObject{

    private final int aPositionLocation, uMatrixLocation, uColorLocation;

    private static final float charSize = 0.5f;

    private static final float charHeight = charSize / 5.0f, charWidth = charSize / 10.0f, charPadding = charSize / 25.0f;

    private float x, y;

    private final int[] charLengths =
            {
                    //A
                    0,
                    //B
                    0,
                    //C
                    0,
                    //D
                    0,
                    //E
                    7,
                    //F
                    0,
                    //G
                    6,
                    //H
                    0,
                    //I
                    0,
                    //J
                    0,
                    //K
                    0,
                    //L
                    3,
                    //M
                    0,
                    //N
                    4,
                    //O
                    5,
                    //P
                    5,
                    //Q
                    0,
                    //R
                    0,
                    //S
                    0,
                    //T
                    4,
                    //U
                    0,
                    //V
                    0,
                    //W
                    0,
                    //X
                    7,
                    //Y
                    0,
                    //Z
                    0,
                    //>
                    3,
            };

    private static final float[] vertexData =
            {
                    //E
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,

                    //G
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    //L
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //N
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //O
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //P
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                    //R
                    //-charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //T
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f, -charHeight / 2.0f, 0.0f,
                    //X
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //>
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,

            };
    private static final float[] normalData =
            {
                    //No normal data
            };

    private DrawText text;

    public Text(Context c, GameState gs, float x, float y) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uColorLocation = shader.getUniformLocation(U_COLOR);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        //Set number to zero at initialisation to avoid null pointer exception
        setText("a");

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

        text.draw(viewProjectionMatrix);

    }

    @Override
    public void update() {
        super.update();
    }

    public void setText(String s)
    {

        int[] letters = new int[s.length()];

        for(int j = 0; j < letters.length; j++)
        {
            char ch = s.charAt(j);
            letters[j] = ch - 'a';
            if(ch == '>')
            {
                letters[j] = 26;
            }
            Log.e("Text",String.valueOf(letters[j]));
        }
        float center = 0; //(((iStr.length() - 1) * charWidth));

        text = vpMatrix ->
        {
            for(int k = 0; k < s.length(); k++)
            {
                drawLetter(letters[k], x + (k * (charWidth + charPadding)) - center, y, vpMatrix);
            }
        };
    }

    private void drawLetter(int i, float x, float y, float[] vpMatrix)
    {
        assert i < 10 && i >= 0;

        float [] modelViewProjectionMatrix = positionObject(x, y,0.14f, vpMatrix, 1.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        glUniform4f(uColorLocation, Lang.three.nR,Lang.three.nG,Lang.three.nB,1.0f);

        int offset = 0;

        for(int j = 0; j < i; j++)
        {
            offset += charLengths[j];
        }

        int length = charLengths[i];

        glDrawArrays(GL_LINE_STRIP, offset, length);

    }

    interface DrawText
    {
        void draw(float[] vpMatrix);
    }

}

