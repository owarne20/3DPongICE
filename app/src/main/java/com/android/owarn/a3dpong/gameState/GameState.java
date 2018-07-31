package com.android.owarn.a3dpong.gameState;

import android.content.Context;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.object.GameObject;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Ray;

import java.util.ArrayList;

/**
 * Created by Oscar Warne on 4/06/2018 for 3DPong.
 */
public abstract class GameState {

    private final Lang.GameState gameState;

    protected ArrayList<GameObject> gameObjects;

    protected Context context;

    protected PongRenderer pongRenderer;

    protected GameState(Lang.GameState gs, Context c, PongRenderer pr)
    {
        gameState = gs;
        gameObjects = new ArrayList<>();
        context = c;
        pongRenderer = pr;
    }

    public abstract void update();

    public abstract void draw(float[] viewProjectionMatrix, float[] viewMatrix);

    public abstract void initialise();

    public abstract void cleanUp();

    public Lang.GameState getGameState()
    {
        return gameState;
    }

    public void initialiseGameObject(GameObject go)
    {
        GameObject gameObject = go;
        gameObject.Start();
        gameObjects.add(gameObject);
    }

    public abstract void handleTouchPress(float normalisedX, float normalisedY, Ray ray);

    public abstract void handleTouchDrag(float normalisedX, float normalisedY, Ray ray);

    public GameObject getGameObject(int i)
    {
        if(i > gameObjects.size()){
            return null;
        }
        return gameObjects.get(i);
    }
}
