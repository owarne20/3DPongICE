package com.android.owarn.a3dpong.networkInterface;

import com.android.owarn.a3dpong.util.ServerBrowserCommunicator;
import com.zeroc.Ice.Current;

import a3DPongServerBrowser.ServerBrowserServer;
import a3DPongServerBrowser.ServerList;

/**
 * Created by Oscar Warne on 21/08/2018 for 3DPong.
 */
public class ServerBrowserServerI implements ServerBrowserServer{

    private ServerBrowserCommunicator serverBrowserCommunicator;

    public ServerBrowserServerI(ServerBrowserCommunicator serverBrowserCommunicator)
    {
        this.serverBrowserCommunicator = serverBrowserCommunicator;
    }

    @Override
    public void sendServerList(ServerList sl, Current current)
    {
        this.serverBrowserCommunicator.updateServers(sl);
    }

}
