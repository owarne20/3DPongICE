package com.android.owarn.a3dpong.gameState;

import android.content.Context;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.object.Border;
import com.android.owarn.a3dpong.object.Cube;
import com.android.owarn.a3dpong.object.GameBoard;
import com.android.owarn.a3dpong.object.GameObject;
import com.android.owarn.a3dpong.object.Text;
import com.android.owarn.a3dpong.object.Number;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Plane;
import com.android.owarn.a3dpong.util.Point;
import com.android.owarn.a3dpong.util.Ray;
import com.android.owarn.a3dpong.util.ServerBrowserCommunicator;
import com.android.owarn.a3dpong.util.Vector;

import java.util.ArrayList;

import a3DPongServerBrowser.Server;
import a3DPongServerBrowser.ServerList;

/**
 * Created by Oscar Warne on 4/06/2018 for 3DPong.
 */
public class ServerBrowser extends GameState{

    private Server[] serversToAdd;

    private boolean init;

    private int i = 0;

    public ServerBrowser(Context c, PongRenderer pr)
    {
        super(Lang.GameState.SERVER_BROWSER, c, pr);
    }

    @Override
    public void update()
    {
        i++;

        if(i > 60 * 3)
        {
            i = 0;
            new ServerBrowserCommunicator(this).pull();
        }

        for (GameObject go : gameObjects) {
            if(go instanceof Text)
            {
                go.update();
            }
        }
        if(flagChange)
        {
            pongRenderer.changeGameState(toChange);
        }
        if (init)
        {
            gameObjects = new ArrayList<>();
            int i = 0;
            for(Server s : serversToAdd)
            {
                if(s == null)
                {
                    init = false;
                    initialiseGameObject(new Text(context, this, -0.7f, 1.1f));
                    ((Text) gameObjects.get(gameObjects.size() - 1)).setText(">server list");
                    initialiseGameObject(new Border(context, this));
                    initialiseGameObject(new GameBoard(context, this));
                    initialiseGameObject(new Text(context, this, 0.3f, 1.1f));
                    ((Text) gameObjects.get(gameObjects.size() - 1)).setText(">back");
                    return;
                }
                initialiseGameObject(new Text(context, this, -0.5f, 0.85f - (i * 0.25f)));
                ((Text) gameObjects.get(gameObjects.size() - 1)).setText(s.name.toLowerCase() + " ");
                ((Text) gameObjects.get(gameObjects.size() - 1)).setServer(s);
                initialiseGameObject(new Cube(-0.6f, 0.85f - (i * 0.25f), context, this));
                ((Cube) gameObjects.get(gameObjects.size() - 1)).setVisibility(true);
                ((Cube) gameObjects.get(gameObjects.size() - 1)).setColor(Lang.six);
                initialiseGameObject(new Text(context, this, 0.4f, 0.85f - (i * 0.25f)));
                ((Text) gameObjects.get(gameObjects.size() - 1)).setText(">p ");
                initialiseGameObject(new Number(context, this, 0.6f, 0.85f - (i * 0.25f)));
                ((Number) gameObjects.get(gameObjects.size() - 1)).setNumber(s.players);
                ((Number) gameObjects.get(gameObjects.size() - 1)).setVisibility(true);
                i++;
            }
            init = false;
        }
    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix)
    {
        for(GameObject go : gameObjects)
        {
            go.draw(viewProjectionMatrix, viewMatrix);
        }

    }

    @Override
    public void initialise()
    {
        new ServerBrowserCommunicator(this).pull();
    }

    @Override
    public void cleanUp()
    {

    }

    @Override
    public void handleTouchPress(float normalisedX, float normalisedY, Ray ray)
    {
        Plane plane = new Plane(new Point(0, 0, 0.14f), new Vector(0, 0, 1.0f));
        Point touchedPoint = intersectionPoint(ray, plane);
        for(GameObject go : gameObjects)
        {
            if(go instanceof Text)
            {
                Text t = (Text) go;
                if(!t.getText().equals(">server list") && contains(t.getTopLeftBound(), t.getBottomRightBound(), touchedPoint))
                {
                    t.setPressed(true, t.getText());
                }
            }
        }
    }

    public boolean contains(Point topLeft, Point bottomRight, Point doesContain)
    {
        return doesContain.x > topLeft.x && doesContain.x < bottomRight.x && doesContain.y < topLeft.y && doesContain.y > bottomRight.y;
    }

    @Override
    public void handleTouchDrag(float normalisedX, float normalisedY, Ray ray)
    {

    }

    @Override
    public void handleTouchRelease(float normalisedX, float normalisedY, Ray ray)
    {
        for(GameObject go : gameObjects)
        {
            if(go instanceof Text)
            {
                Text t = (Text) go;
                if(t.isPressed())
                {
                    t.setPressed(false, t.getText());
                    if(t.getServer() != null)
                    {
                        //Goto server
                        Lang.server = t.getServer();
                        flagGamestateChange(Lang.GameState.IN_GAME);
                    }
                    if(t.getText() == ">back")
                    {
                        flagGamestateChange(Lang.GameState.TITLE);
                    }
                }
            }
        }
    }

    public void updateServers(ServerList serverList)
    {
        serversToAdd = serverList.servers;
        init = true;
    }
    private Point intersectionPoint(Ray ray, Plane plane)
    {
        Vector rayToPlaneVector = vectorBetween(ray.point, plane.point);
        float scaleFactor = rayToPlaneVector.dotProduct(plane.normal) / ray.vector.dotProduct(plane.normal);
        Point intersectionPoint = ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectionPoint;
    }

    private Vector vectorBetween(Point from, Point to)
    {
        return new Vector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
        );
    }
}
