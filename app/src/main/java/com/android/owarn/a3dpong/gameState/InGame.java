package com.android.owarn.a3dpong.gameState;

import android.content.Context;
import android.util.Log;

import com.android.owarn.a3dpong.PongRenderer;
import com.android.owarn.a3dpong.networkInterface.ServerI;
import com.android.owarn.a3dpong.object.Border;
import com.android.owarn.a3dpong.object.CircularTransition;
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
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.SocketException;
import com.zeroc.Ice.Util;

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

    private long last;
    private long start;
    private boolean colour;

    public InGame(Context c, PongRenderer pr)
    {
        super(Lang.GameState.IN_GAME, c, pr);
    }

    @Override
    public void update()
    {
        gameObjects.get(9).update();
        gameObjects.get(10).update();
         if(serverReady) {
             client.sendPaddlePositionXAsync(((Paddle) gameObjects.get(2 + side)).position.x);
         }

        if(flagChange)
        {
            flagChange = false;
            pongRenderer.changeGameState(Lang.GameState.TITLE);
            return;
        }

        if(serverReady)
        {
            ((Line) gameObjects.get(4)).setLine(((Paddle) gameObjects.get(2 + side)).position.x, ((Paddle) gameObjects.get(2 + side)).position.y, touchedX - ((Paddle) gameObjects.get(2 + side)).position.x);

            for (GameObject go : gameObjects)
            {
                go.update();
            }

            if((System.nanoTime() - last) > 0.2f * 1000000000L && (System.nanoTime() - start) < 4f * 1000000000L)
            {
                last = System.nanoTime();
                if(colour)
                {
                    ((Paddle) gameObjects.get(2 + side)).setColour(Lang.two);
                }
                else {
                    ((Paddle) gameObjects.get(2 + side)).setColour(Lang.six);
                }
                colour = !colour;
            }
            if((System.nanoTime() - start) > 4f * 1000000000L)
            {
                ((Paddle) gameObjects.get(2 + side)).setColour(Lang.six);
            }

        }

    }

    @Override
    public void draw(float[] viewProjectionMatrix, float[] viewMatrix)
    {
        if(flagChange)
        {
            pongRenderer.changeGameState(Lang.GameState.TITLE);
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
        initialiseGameObject(new CircularTransition(context, this));
        //initialiseGameObject(new Text(context, this, 0.3f, 1.1f));
        //((Text) gameObjects.get(11)).setText(">back");

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

            gameClient = null;
        connected = false;
    }
    public void serverReady(byte b)
    {
        //Triggered when server is ready to receive information this will not occur until both clients have connected

        ((Paddle) gameObjects.get(2 + b)).setPlayer();
        ((Number) gameObjects.get(8)).setVisibility(true);
        ((Number) gameObjects.get(7)).setVisibility(false);
        ((Number) gameObjects.get(6)).setVisibility(false);
        ((Cube) gameObjects.get(5)).setVisibility(false);
        ((Cube) gameObjects.get(5)).setPositionAndVectorNoSmooth(0.0f, 0.0f, 0.0f, 0.0f);

        serverReady = true;
        side = b;
    }

    public void setBallPositionAndVector(float x, float y, float vX, float vY)
    {
        ((Cube) gameObjects.get(5)).setPositionAndVector(x, y, vX, vY);
    }

    public void setScore(int score, byte side)
    {
        //Triggered twice every time someone scores
        ((CircularTransition) gameObjects.get(10)).doAnimation(((Cube) gameObjects.get(5)).getPosition());
        ((Number) gameObjects.get(7 - side)).setNumber(score);
        ((Cube) gameObjects.get(5)).setVisibility(false);
    }

    public void startGame(float vX, float vY)
    {
        ((Cube) gameObjects.get(5)).setVectorAndStartGame(vX, vY);
        ((Number) gameObjects.get(6)).setVisibility(true);
        ((Number) gameObjects.get(7)).setVisibility(true);
        ((DirectionHint) gameObjects.get(9)).setVisibility(false);
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
        ((DirectionHint) gameObjects.get(9)).setVisibility(true);
        ((Number) gameObjects.get(8)).setVisibility(false);
        ((Number) gameObjects.get(6)).setVisibility(false);
        ((Number) gameObjects.get(7)).setVisibility(false);
        ((Cube) gameObjects.get(5)).setVisibility(true);
        ((Cube) gameObjects.get(5)).setPositionAndVectorNoSmooth(0.0f, 0.0f, 0.0f, 0.0f);
        start = System.nanoTime();
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

                Properties properties = Util.createProperties();
                properties.setProperty("Ice.ACM.Close", "0");
                properties.setProperty("Ice.ACM.Heartbeat", "3");
                properties.setProperty("Ice.ACM.Timeout", "30");
                InitializationData id = new InitializationData();
                id.properties = properties;

                if(Lang.server == null)
                {
                    flagGamestateChange(Lang.GameState.TITLE);
                    flagChange = true;
                    return;
                }

                String address = Lang.server.address;
                String port = Lang.server.port;

                communicator = com.zeroc.Ice.Util.initialize(id);
                ClientPrx clientPrx = ClientPrx.checkedCast(
                        communicator.stringToProxy("client:default -h " + address + " -p " + port)).ice_twoway().ice_secure(false).ice_collocationOptimized(false).ice_compress(true);

                if(clientPrx == null)
                {
                    //If connection is failed due to server problems
                    Log.e("Network","Invalid proxy");
                    serverReady = false;
                    flagChange = true;
                    stop();
                    return;
                }
                Log.e("Network", "Connected");

                //Send a callback to the server so that its connection is not trying to punch through a firewall
                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("");
                ServerPrx cbPrx = ServerPrx.uncheckedCast(adapter.addWithUUID(new ServerI(game)));
                clientPrx.ice_getConnection().setAdapter(adapter);
                clientPrx.sendServerCallback(cbPrx);

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
                //Timeout or no adapter to initiate connection on
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
            Log.e("Network","Stopping Network Thread");
            if(thread != null)
            {
                if(communicator != null && !communicator.isShutdown())
                {
                    Log.e("Network","Shutting Down Communicator");
                    communicator.shutdown();
                    //Log.e("Network","Closing Communicator");
                    //communicator.close();
                    //Log.e("Network","Destroying Communicator");
                    //communicator.destroy();
                }
                try {
                    Log.e("Network","Joining Thread");
                    thread.join(1);
                    thread = null;
                    Log.e("Network","Thread Joined");
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

        //Plane plane = new Plane(new Point(0, 0, 0.14f), new Vector(0, 0, 1.0f));
        //Point touchedPoint = intersectionPoint(ray, plane);

        //Text t = (Text) gameObjects.get(11);

        //if(contains(t.getTopLeftBound(), t.getBottomRightBound(), touchedPoint))
        //{
        //    t.setPressed(true, t.getText());
        //}

    }

    public boolean contains(Point topLeft, Point bottomRight, Point doesContain)
    {
        return doesContain.x > topLeft.x && doesContain.x < bottomRight.x && doesContain.y < topLeft.y && doesContain.y > bottomRight.y;
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

    @Override
    public void handleTouchRelease(float normalisedX, float normalisedY, Ray ray)
    {

        //Text t = (Text) gameObjects.get(11);

        //if(t.isPressed())
        //{
        //    t.setPressed(false, t.getText());
        //    flagChange = true;
        //}
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
