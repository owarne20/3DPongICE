package com.android.owarn.a3dpong.object;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.R;
import com.android.owarn.a3dpong.gameState.GameState;
import com.android.owarn.a3dpong.shader.ShaderCode;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Point;

import a3DPongServerBrowser.Server;

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
                    3,
                    //B
                    6,
                    //C
                    4,
                    //D
                    0,
                    //E
                    7,
                    //F
                    6,
                    //G
                    6,
                    //H
                    6,
                    //I
                    6,
                    //J
                    0,
                    //K
                    6,
                    //L
                    3,
                    //M
                    5,
                    //N
                    4,
                    //O
                    5,
                    //P
                    5,
                    //Q
                    0,
                    //R
                    5,
                    //S
                    6,
                    //T
                    4,
                    //U
                    4,
                    //V
                    3,
                    //W
                    0,
                    //X
                    7,
                    //Y
                    5,
                    //Z
                    0,
                    //>
                    3,
                    //_
                    0,
            };

    private static final float[] vertexData =
            {
                    //A
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //B
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //C
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //E
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //F
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    //G
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    //H
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //I
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //K
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //L
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //M
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
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
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                    //S
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,

                    //T
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f, -charHeight / 2.0f, 0.0f,
                    //U
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //V
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f, -charHeight / 2.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //X
                    -charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                    //Y
                                 0.0f, -charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                    -charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
                                 0.0f,               0.0f, 0.0f,
                     charWidth / 2.0f,  charHeight / 2.0f, 0.0f,
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

    private boolean pressed;

    private float length;

    private String currentText;

    private Server server;

    public Text(Context c, GameState gs, float x, float y) {
        super(c, new ShaderCode(c, R.raw.unlit_fragment_shader, R.raw.unlit_vertex_shader), vertexData, normalData, gs);

        // Get the attribute and uniform locations fromm the shader for our object to use for later
        aPositionLocation = shader.getAttribLocation(A_POSITION);

        uColorLocation = shader.getUniformLocation(U_COLOR);

        uMatrixLocation = shader.getUniformLocation(U_MATRIX);

        shader.setAtrribPointer(0, aPositionLocation, 3, 12, true);

        //Set string to 'a' at initialisation to avoid null pointer exception
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
        Log.e("Text", s);
        int[] letters = new int[s.length()];

        length = s.length() * charWidth;

        currentText = s;

        for(int j = 0; j < letters.length; j++)
        {
            char ch = s.charAt(j);
            letters[j] = ch - 'a';
            if(ch == '>')
            {
                letters[j] = 26;
            }
            if(ch == ' ')
            {
                letters[j] = 27;
            }
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

        float [] modelViewProjectionMatrix = positionObject(x, y,0.14f, vpMatrix, 1.0f, 1.0f, 1.0f);
        glUniformMatrix4fv(uMatrixLocation, 1, false, modelViewProjectionMatrix, 0);
        if(pressed)
        {
            glUniform4f(uColorLocation, Lang.six.nR, Lang.six.nG, Lang.six.nB,1.0f);
        } else{
            glUniform4f(uColorLocation, Lang.two.nR, Lang.two.nG, Lang.two.nB,1.0f);
        }

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

    public void setPressed(boolean pressed, String text)
    {
        if(this.pressed == pressed)
        {
            return;
        }
        this.pressed = pressed;

        setText(text);
    }

    public boolean isPressed()
    {
        return pressed;
    }

    public Point getTopLeftBound()
    {

         return new Point(x, y + charHeight / 2.0f, 0.14f);
    }

    public Point getBottomRightBound()
    {

        return new Point(x + length, y - charHeight / 2.0f, 0.14f);
    }

    public String getText()
    {
        return currentText;
    }

    public void setServer(Server server)
    {
        this.server = server;
    }

    public Server getServer()
    {
        return server;
    }

}

