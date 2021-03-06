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

public interface Server extends com.zeroc.Ice.Object
{
    void sendBallPositionAndVector(float x, float y, float vX, float vY, com.zeroc.Ice.Current current);

    void startGame(float vX, float vY, com.zeroc.Ice.Current current);

    void startDirectionHint(float vX, float vY, int seconds, int padding, com.zeroc.Ice.Current current);

    void disconnect(String reason, com.zeroc.Ice.Current current);

    void sendPaddlePositionX(float x, com.zeroc.Ice.Current current);

    void sendTime(float timeInSeconds, com.zeroc.Ice.Current current);

    void ready(byte b, com.zeroc.Ice.Current current);

    void timeModifier(float t, com.zeroc.Ice.Current current);

    void sendScore(int score, byte side, com.zeroc.Ice.Current current);

    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::a3DPong::Server"
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
        return "::a3DPong::Server";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendBallPositionAndVector(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_x;
        float iceP_y;
        float iceP_vX;
        float iceP_vY;
        iceP_x = istr.readFloat();
        iceP_y = istr.readFloat();
        iceP_vX = istr.readFloat();
        iceP_vY = istr.readFloat();
        inS.endReadParams();
        obj.sendBallPositionAndVector(iceP_x, iceP_y, iceP_vX, iceP_vY, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_startGame(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_vX;
        float iceP_vY;
        iceP_vX = istr.readFloat();
        iceP_vY = istr.readFloat();
        inS.endReadParams();
        obj.startGame(iceP_vX, iceP_vY, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_startDirectionHint(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_vX;
        float iceP_vY;
        int iceP_seconds;
        int iceP_padding;
        iceP_vX = istr.readFloat();
        iceP_vY = istr.readFloat();
        iceP_seconds = istr.readInt();
        iceP_padding = istr.readInt();
        inS.endReadParams();
        obj.startDirectionHint(iceP_vX, iceP_vY, iceP_seconds, iceP_padding, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_disconnect(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_reason;
        iceP_reason = istr.readString();
        inS.endReadParams();
        obj.disconnect(iceP_reason, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendPaddlePositionX(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_x;
        iceP_x = istr.readFloat();
        inS.endReadParams();
        obj.sendPaddlePositionX(iceP_x, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendTime(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_timeInSeconds;
        iceP_timeInSeconds = istr.readFloat();
        inS.endReadParams();
        obj.sendTime(iceP_timeInSeconds, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_ready(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        byte iceP_b;
        iceP_b = istr.readByte();
        inS.endReadParams();
        obj.ready(iceP_b, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_timeModifier(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        float iceP_t;
        iceP_t = istr.readFloat();
        inS.endReadParams();
        obj.timeModifier(iceP_t, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_sendScore(Server obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_score;
        byte iceP_side;
        iceP_score = istr.readInt();
        iceP_side = istr.readByte();
        inS.endReadParams();
        obj.sendScore(iceP_score, iceP_side, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    final static String[] _iceOps =
    {
        "disconnect",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "ready",
        "sendBallPositionAndVector",
        "sendPaddlePositionX",
        "sendScore",
        "sendTime",
        "startDirectionHint",
        "startGame",
        "timeModifier"
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
                return _iceD_ready(this, in, current);
            }
            case 6:
            {
                return _iceD_sendBallPositionAndVector(this, in, current);
            }
            case 7:
            {
                return _iceD_sendPaddlePositionX(this, in, current);
            }
            case 8:
            {
                return _iceD_sendScore(this, in, current);
            }
            case 9:
            {
                return _iceD_sendTime(this, in, current);
            }
            case 10:
            {
                return _iceD_startDirectionHint(this, in, current);
            }
            case 11:
            {
                return _iceD_startGame(this, in, current);
            }
            case 12:
            {
                return _iceD_timeModifier(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
