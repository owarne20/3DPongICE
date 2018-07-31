package com.android.owarn.a3dpong.gameState;

import android.content.Context;

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

    public Title(Context c, PongRenderer pr)
    {
        super(Lang.GameState.title, c, pr);
    }

    @Override
    public void update()
    {
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
        initialiseGameObject(new Text(context, this, -0.6f, 1.075f));
        //initialiseGameObject(new Top(context, this));
        initialiseGameObject(new Border(context, this));
        initialiseGameObject(new Paddle(context, -0.8f, this));
        initialiseGameObject(new Paddle(context, 0.8f, this));
        initialiseGameObject(new GameBoard(context, this));
        initialiseGameObject(new Cube(0.0f, 0.0f, context, this));
        ((Cube) gameObjects.get(5)).setPositionAndVector(0.0f, 0.0f, 1.0f, 1.0f);
        ((Cube) gameObjects.get(5)).toggleVisible();
        ((Text) gameObjects.get(0)).setText(">pong");
        ((Paddle) gameObjects.get(2)).setDummy();
        ((Paddle) gameObjects.get(3)).setDummy();
    }

    @Override
    public void cleanUp()
    {

    }

    @Override
    public void handleTouchPress(float normalisedX, float normalisedY, Ray ray) {

    }

    @Override
    public void handleTouchDrag(float normalisedX, float normalisedY, Ray ray) {

    }
    public float limit(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
