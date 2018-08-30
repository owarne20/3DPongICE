package com.android.owarn.a3dpong.gameState;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.networkInterface.ServerI;
import com.android.owarn.a3dpong.object.Border;
import com.android.owarn.a3dpong.object.Cube;
import com.android.owarn.a3dpong.object.DirectionHint;
import com.android.owarn.a3dpong.object.GameBoard;
import com.android.owarn.a3dpong.object.GameObject;
import com.android.owarn.a3dpong.object.Line;
import com.android.owarn.a3dpong.object.Number;
import com.android.owarn.a3dpong.object.Paddle;
import com.android.owarn.a3dpong.util.Lang;
import com.android.owarn.a3dpong.util.Plane;
import com.android.owarn.a3dpong.util.Point;
import com.android.owarn.a3dpong.util.Ray;
import com.android.owarn.a3dpong.util.Sphere;
import com.android.owarn.a3dpong.util.Vector;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ConnectTimeoutException;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.SocketException;

import a3DPong.ClientPrx;
import a3DPong.ServerPrx;

/**
 * Created by Oscar Warne on 4/06/2018 for 3DPong.
 */
public class InGame extends GameState{

    public boolean connected;
    public boolean flagChange;
    private boolean serverReady;
    public boolean paddlePressed;

    public ClientPrx client;
    public Communicator connection;
    private GameClient gameClient;

    private float touchedX = 0;
    private byte side = 1;

    public InGame(Context c, PongRenderer pr)
    {
        super(Lang.GameState.inGame, c, pr);
    }

    @Override
    public void update()
    {
        gameObjects.get(9).update();
         if(serverReady) {
             client.sendPaddlePositionXAsync(((Paddle) gameObjects.get(2 + side)).position.x);
         }

        if(flagChange)
        {
            pongRenderer.changeGameState(Lang.GameState.title);
            flagChange = false;
            return;
        }

        if(serverReady)
        {
            ((Line) gameObjects.get(4)).setLine(((Paddle) gameObjects.get(2 + side)).position.x, ((Paddle) gameObjects.get(2 + side)).position.y, touchedX - ((Paddle) gameObjects.get(2 + side)).position.x);

            for (GameObject go : gameObjects)
            {
                go.update();
            }
        }

    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix)
    {
        if(flagChange)
        {
            pongRenderer.changeGameState(Lang.GameState.title);
            flagChange = false;
            return;
        }
        for (GameObject go : gameObjects)
        {
            go.draw(viewProjectionMatrix, viewMatrix);
        }
    }

    @Override
    public void initialise()
    {
        initialiseGameObject(new GameBoard(context, this));
        initialiseGameObject(new Border(context, this));
        initialiseGameObject(new Paddle(context, -0.8f, this));
        initialiseGameObject(new Paddle(context,  0.8f, this));
        initialiseGameObject(new Line(context, -0.7f, 1.4f, -0.8f, this));
        initialiseGameObject(new Cube(0.0f,0.0f, context, this));
        initialiseGameObject(new Number(context, this,  0.0f,  0.2f));
        initialiseGameObject(new Number(context, this,  0.0f, -0.2f));
        initialiseGameObject(new Number(context, this, 0.0f, 0.0f));
        initialiseGameObject(new DirectionHint(context, 0.0f, 0.0f, 0.0f, 0.0f, this));

        //TODO: Make a better way to get GameObjects from a state
        ((Number) gameObjects.get(6)).setNumber(0);
        ((Number) gameObjects.get(7)).setNumber(0);
        ((Line) gameObjects.get(4)).setLine(((Paddle) gameObjects.get(2)).position.x, ((Paddle) gameObjects.get(2)).position.y, touchedX - ((Paddle) gameObjects.get(2)).position.x);

        gameClient = new GameClient(this);
        gameClient.start();
    }

    @Override
    public void cleanUp()
    {
        disconnect();
    }

    public void disconnect()
    {
        //Server Disconnected
        flagChange = true;

        if(gameClient != null)
        {
            gameClient.stop();
        }

        connected = false;
    }
    public void serverReady(byte b)
    {
        //Triggered when server is ready to receive information this will not occur until both clients have connected

        ((Paddle) gameObjects.get(2 + b)).setPlayer();
        ((Number) gameObjects.get(8)).toggleVisible();
        serverReady = true;
        side = b;
    }

    public void setBallPositionAndVector(float x, float y, float vX, float vY)
    {
        ((Cube) gameObjects.get(5)).setPositionAndVector(x, y, vX, vY);
    }

    public void startGame(float vX, float vY)
    {
        ((Cube) gameObjects.get(5)).setVectorAndStartGame(vX, vY);
        ((Number) gameObjects.get(6)).toggleVisible();
        ((Number) gameObjects.get(7)).toggleVisible();
        ((DirectionHint) gameObjects.get(9)).toggleVisible();
    }

    public void setTimer(float t)
    {
        ((Number) gameObjects.get(8)).setNumber((int) t);
    }

    public void setPaddlePositionX(float x)
    {
        ((Paddle) gameObjects.get(3 - side)).position.x = (((Paddle) gameObjects.get(3 - side)).position.x * 4 + x) / 5;
    }

    public void setDirectionHint(float rotationInDegrees, float seconds, float padding)
    {
        ((DirectionHint) gameObjects.get(9)).setRotationAndTime(rotationInDegrees, seconds, padding);
        ((DirectionHint) gameObjects.get(9)).toggleVisible();
        ((Number) gameObjects.get(8)).toggleVisible();
        ((Cube) gameObjects.get(5)).toggleVisible();
    }

    private class GameClient implements Runnable
    {

        private Thread thread;
        private Communicator communicator;
        private InGame game;

        public GameClient(InGame game)
        {
            this.game = game;
        }

        @Override
        public void run()
        {
            try
            {
                communicator = com.zeroc.Ice.Util.initialize();
                ClientPrx clientPrx = ClientPrx.checkedCast(
                        communicator.stringToProxy("client:default -h 192.168.137.1 -p 10000")).ice_twoway().ice_timeout(-1).ice_secure(false).ice_collocationOptimized(false).ice_compress(true);

                if(clientPrx == null)
                {
                    Log.e("Network","Invalid proxy");
                    serverReady = false;
                    flagChange = true;
                    stop();
                    return;
                }
                Log.e("Network", "Connected");
                ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("callbackClient", "default");
                adapter.add(new ServerI(game), com.zeroc.Ice.Util.stringToIdentity("server"));
                adapter.activate();

                ServerPrx receiver =
                        ServerPrx.uncheckedCast(adapter.createProxy(
                                com.zeroc.Ice.Util.stringToIdentity("server")));
                clientPrx.sendServerCallback(receiver);

                Log.e("Network","Sent callback to server");

                client = clientPrx;
                connection = communicator;

                connected = true;
                communicator.waitForShutdown();
                stop();
                flagChange = true;

            }
                catch(SocketException | ConnectTimeoutException se)
            {
                Log.e("Network","Networking Problems :(   :");
                se.printStackTrace();
                disconnect();
                flagChange = true;
                stop();
            }

        }

        public synchronized void start()
        {

            if(connected == true)
            {
                //If this is triggered something is wrong
                Log.e("Network","We are connected before starting thread!!! Has this thread been revived?");
                connected = false;
            }
            if(thread == null)
            {
                thread =  new Thread(this);
                thread.start();
            }

        }

        public synchronized void stop() {
            if(thread != null)
            {
                if(communicator != null && !communicator.isShutdown())
                {
                    communicator.shutdown();
                    communicator.close();
                    communicator.destroy();
                }
                try {
                    thread.join(1);
                } catch (InterruptedException e) {
                    Log.e("Network","Unable to join network thread");
                }
            }
        }
    }

    @Override
    public void handleTouchPress(float normalisedX, float normalisedY, Ray ray) {

        Point pos = ((Paddle) gameObjects.get(2 + side)).position;

        Sphere paddleBoundingSphere = new Sphere(new Point(pos.x, pos.y, pos.z), 0.2f);

        paddlePressed = intersects(paddleBoundingSphere, ray);
    }

    @Override
    public void handleTouchDrag(float normalisedX, float normalisedY, Ray ray) {
        if(paddlePressed)
        {
            Plane plane = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1));
            Point touchedPoint = intersectionPoint(ray, plane);
            touchedX = limit(touchedPoint.x, -0.5f, 0.5f);
            ((Paddle) gameObjects.get(2 + side)).setTouchX(touchedX);

        }
    }

    private boolean intersects(Sphere sphere, Ray ray)
    {
        return distanceBetween(sphere.centre, ray) < sphere.radius;
    }

    private float distanceBetween(Point point, Ray ray)
    {
        Vector p1ToPoint = vectorBetween(ray.point, point);
        Vector p2ToPoint = vectorBetween(ray.point.translate(ray.vector), point);

        float areaOfTriangleTimesTwo = p1ToPoint.crossProduct(p2ToPoint).length();
        float lengthOfBase = ray.vector.length();

        float distanceFromPointToRay = areaOfTriangleTimesTwo / lengthOfBase;
        return distanceFromPointToRay;
    }

    private Vector vectorBetween(Point from, Point to)
    {
        return new Vector(
                to.x - from.x,
                to.y - from.y,
                to.z - from.z
        );
    }

    private Point intersectionPoint(Ray ray, Plane plane)
    {
        Vector rayToPlaneVector = vectorBetween(ray.point, plane.point);
        float scaleFactor = rayToPlaneVector.dotProduct(plane.normal) / ray.vector.dotProduct(plane.normal);
        Point intersectionPoint = ray.point.translate(ray.vector.scale(scaleFactor));
        return intersectionPoint;
    }

    private float limit(float value, float min, float max)
    {
        return Math.max(min, Math.min(max, value));
    }


}
