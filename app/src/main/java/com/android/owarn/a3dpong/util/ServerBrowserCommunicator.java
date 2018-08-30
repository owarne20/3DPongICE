package com.android.owarn.a3dpong.util;

import android.util.Log;

import com.android.owarn.a3dpong.gameState.ServerBrowser;
import com.android.owarn.a3dpong.networkInterface.ServerBrowserServerI;
import com.zeroc.Ice.Communicator;

import a3DPongServerBrowser.ServerBrowserClientPrx;
import a3DPongServerBrowser.ServerBrowserServerPrx;
import a3DPongServerBrowser.ServerList;

/**
 * Created by Oscar Warne on 21/08/2018 for 3DPong.
 */
public class ServerBrowserCommunicator implements Runnable{

    private ServerBrowser serverBrowser;

    private Communicator communicator;

    private boolean running;

    private Thread thread;

    public ServerBrowserCommunicator(ServerBrowser serverBrowser)
    {
        this.serverBrowser = serverBrowser;
    }


    @Override
    public void run()
    {
        communicator = com.zeroc.Ice.Util.initialize();
        ServerBrowserClientPrx clientPrx = ServerBrowserClientPrx.checkedCast(
                communicator.stringToProxy("client:default -h 58.167.142.74 -p 10001")).ice_twoway().ice_secure(false).ice_collocationOptimized(false).ice_compress(true);

        if(clientPrx == null)
        {
            //If connection is failed due to server problems
            Log.e("SB Connect","Invalid proxy");
            return;
        }
        Log.e("SB Connect", "Connected");

        //Send a callback to the server so that its connection is not trying to punch through a firewall
        com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("");
        ServerBrowserServerPrx cbPrx = ServerBrowserServerPrx.uncheckedCast(adapter.addWithUUID(new ServerBrowserServerI(this)));
        clientPrx.ice_getConnection().setAdapter(adapter);
        clientPrx.sendServerCallback(cbPrx);
    }

    public synchronized void pull()
    {
        if (!running)
        {
            thread = new Thread(this);
            thread.start();
            running = true;
        }
    }

    public void updateServers(ServerList serverList)
    {
        serverBrowser.updateServers(serverList);
    }
}
