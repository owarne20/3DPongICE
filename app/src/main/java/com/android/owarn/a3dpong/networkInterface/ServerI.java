package com.android.owarn.a3dpong.networkInterface;

import android.util.Log;

import com.android.owarn.a3dpong.gameState.InGame;
import com.android.owarn.a3dpong.util.Lang;
import com.zeroc.Ice.Current;

import a3DPong.Server;

/**
 * Created by Oscar Warne on 5/06/2018 for 3DPong.
 */
public class ServerI implements Server{

    private InGame inGame;

    public ServerI(InGame game)
    {
        inGame = game;
    }

    @Override
    public void sendBallPositionAndVector(float x, float y, float vX, float vY, Current current)
    {
        inGame.setBallPositionAndVector(x, y, vX, vY);
    }

    @Override
    public void startGame(float vX, float vY, Current current)
    {
        inGame.startGame(vX, vY);
        Log.e("Network", "startGame");
    }

    @Override
    public void startDirectionHint(float vX, float vY, int seconds, int padding, Current current)
    {
        Log.e("Network","vX: " + vX + " vY: " + vY);
        double angleInRadians = Math.toRadians(Math.toDegrees(Math.atan2(vY, vX)));
        Log.e("Network",String.valueOf(Math.toDegrees(angleInRadians)));
        inGame.setDirectionHint((float) Math.toDegrees(angleInRadians), seconds, padding);
        Log.e("Network", "startDirectionHint");
    }

    @Override
    public void disconnect(String reason, Current current)
    {
        Log.e("Network", reason);
        Lang.disconnectReason = reason.toLowerCase();
        inGame.disconnect();
    }

    @Override
    public void sendPaddlePositionX(float x, Current current)
    {
        inGame.setPaddlePositionX(x);
    }

    @Override
    public void sendTime(float timeInSeconds, Current current)
    {
        Log.e("Network", "sendTime");
        inGame.setTimer(timeInSeconds);
    }

    @Override
    public void ready(byte b, Current current)
    {
        inGame.serverReady(b);
        Log.e("Network", "ready");
    }

    @Override
    public void timeModifier(float t, Current current) {
        Log.e("Network", "timeModifier");

    }

    @Override
    public void sendScore(int score, byte side, Current current) {
        inGame.setScore(score, side);
        Log.e("Network", "Score received");
    }
}
