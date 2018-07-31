package com.android.owarn.a3dpong.gameState;

import android.content.Context;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Ray;

/**
 * Created by Oscar Warne on 4/06/2018 for 3DPong.
 */
public class Credits extends GameState{
    public Credits(Context c, PongRenderer pr)
    {
        super(Lang.GameState.credits, c, pr);
    }
    @Override
    public void update()
    {

    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix)
    {

    }

    @Override
    public void initialise()
    {

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
}
