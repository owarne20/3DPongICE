// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.1
//
// <auto-generated>
//
// Generated from file `NetworkInterfaces.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package a3DPong;

public interface Client extends com.zeroc.Ice.Object
{
    void sendServerCallback(ServerPrx proxy, com.zeroc.Ice.Current current);

    void sendPaddlePositionX(float x, com.zeroc.Ice.Current current);

    void disconnect(String reason, com.zeroc.Ice.Current current);

    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::a3DPong::Client"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::a3DPong::Client";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendServerCallback(Client obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        ServerPrx iceP_proxy;
        iceP_proxy = ServerPrx.uncheckedCast(istr.readProxy());
        inS.endReadParams();
        obj.sendServerCallback(iceP_proxy, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendPaddlePositionX(Client obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_x;
        iceP_x = istr.readFloat();
        inS.endReadParams();
        obj.sendPaddlePositionX(iceP_x, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_disconnect(Client obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_reason;
        iceP_reason = istr.readString();
        inS.endReadParams();
        obj.disconnect(iceP_reason, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    final static String[] _iceOps =
    {
        "disconnect",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "sendPaddlePositionX",
        "sendServerCallback"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_disconnect(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_sendPaddlePositionX(this, in, current);
            }
            case 6:
            {
                return _iceD_sendServerCallback(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
