package com.android.owarn.a3dpong.gameState;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.object.Border;
import com.android.owarn.a3dpong.object.Cube;
import com.android.owarn.a3dpong.object.GameBoard;
import com.android.owarn.a3dpong.object.GameObject;
import com.android.owarn.a3dpong.object.Paddle;
import com.android.owarn.a3dpong.object.Text;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Ray;

/**
 * Created by Oscar Warne on 4/06/2018 for 3DPong.
 */
public class Title extends GameState{

    private long start;
    private boolean visible;
    private int i = 0;

    public Title(Context c, PongRenderer pr)
    {
        super(Lang.GameState.TITLE, c, pr);
    }

    @Override
    public void update()
    {
        i++;
        if(flagChange)
        {
            pongRenderer.changeGameState(toChange);
        }

        float x = limit(((Cube) gameObjects.get(5)).getPosition().x, -0.5f, 0.5f);

        if(((Cube) gameObjects.get(5)).getDirection().y > 0)
        {
            ((Paddle) gameObjects.get(3)).setTouchX(x);
        }
        else {
            ((Paddle) gameObjects.get(2)).setTouchX(x);
        }

        for (GameObject go : gameObjects)
        {
            go.update();
        }
        if((System.nanoTime() - start) > 2 * 1000000000L && visible)
        {
            if(i > 15)
            {
                i = 0;
                Text t = (Text) gameObjects.get(8);
                StringBuilder sb = new StringBuilder(t.getText());
                if(sb.length() == 0)
                {
                    visible = false;
                    Lang.disconnectReason = " ";
                    return;
                }
                t.setText(sb.deleteCharAt(0).toString());
            }

        }
        if(i > 15)
        {
            i = 0;
        }
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix)
    {
        for (GameObject go : gameObjects)
        {
            go.draw(viewProjectionMatrix, viewMatrix);
        }
    }

    @Override
    public void initialise()
    {
        start = System.nanoTime();
        initialiseGameObject(new Text(context, this, -0.6f, 1.075f));
        initialiseGameObject(new Border(context, this));
        initialiseGameObject(new Paddle(context, -0.8f, this));
        initialiseGameObject(new Paddle(context, 0.8f, this));
        initialiseGameObject(new GameBoard(context, this));
        initialiseGameObject(new Cube(0.0f, 0.0f, context, this));
        initialiseGameObject(new Text(context, this, -0.4f, 0.2f));
        initialiseGameObject(new Text(context, this, -0.4f, -0.2f));
        initialiseGameObject(new Text(context, this, -0.7f, -1.1f));
        ((Cube) gameObjects.get(5)).setPositionAndVector(0.0f, 0.0f, 1.3f, 2.0f);
        ((Cube) gameObjects.get(5)).toggleVisible();
        ((Text) gameObjects.get(0)).setText(">pong");
        ((Text) gameObjects.get(6)).setText(">multiplayer");
        ((Text) gameObjects.get(7)).setText(">exit");
        ((Text) gameObjects.get(8)).setText(Lang.disconnectReason);
        visible = true;
        ((Paddle) gameObjects.get(2)).setDummy();
        ((Paddle) gameObjects.get(3)).setDummy();
    }

    @Override
    public void cleanUp()
    {

    }

    @Override
    public void handleTouchPress(float normalisedX, float normalisedY, Ray ray)
    {
        if(normalisedX < 0.4f && normalisedX > -0.5f && normalisedY < 0.2f && normalisedY > 0.1f)
        {
            ((Text) gameObjects.get(6)).setPressed(true, ">multiplayer");
        }
        if(normalisedX < -0.1f && normalisedX > -0.5f && normalisedY > -0.2f && normalisedY < -0.1f)
        {
            ((Text) gameObjects.get(7)).setPressed(true, ">exit");
        }
        Log.e("Touch", normalisedX + " " + normalisedY);
    }

    @Override
    public void handleTouchDrag(float normalisedX, float normalisedY, Ray ray) {

    }

    @Override
    public void handleTouchRelease(float normalisedX, float normalisedY, Ray ray)
    {
        if(((Text) gameObjects.get(6)).isPressed())
        {
            flagGamestateChange(Lang.GameState.SERVER_BROWSER);
        }
        if(((Text) gameObjects.get(7)).isPressed())
        {
            flagGamestateChange(Lang.GameState.exit);
        }
        ((Text) gameObjects.get(6)).setPressed(false, ">multiplayer");
        ((Text) gameObjects.get(7)).setPressed(false, ">exit");
    }

    public float limit(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
