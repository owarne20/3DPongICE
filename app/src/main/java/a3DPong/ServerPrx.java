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

public interface ServerPrx extends com.zeroc.Ice.ObjectPrx
{
    default void sendBallPositionAndVector(float x, float y, float vX, float vY)
    {
        sendBallPositionAndVector(x, y, vX, vY, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void sendBallPositionAndVector(float x, float y, float vX, float vY, java.util.Map<String, String> context)
    {
        _iceI_sendBallPositionAndVectorAsync(x, y, vX, vY, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> sendBallPositionAndVectorAsync(float x, float y, float vX, float vY)
    {
        return _iceI_sendBallPositionAndVectorAsync(x, y, vX, vY, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> sendBallPositionAndVectorAsync(float x, float y, float vX, float vY, java.util.Map<String, String> context)
    {
        return _iceI_sendBallPositionAndVectorAsync(x, y, vX, vY, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_sendBallPositionAndVectorAsync(float iceP_x, float iceP_y, float iceP_vX, float iceP_vY, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "sendBallPositionAndVector", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_x);
                     ostr.writeFloat(iceP_y);
                     ostr.writeFloat(iceP_vX);
                     ostr.writeFloat(iceP_vY);
                 }, null);
        return f;
    }

    default void startGame(float vX, float vY)
    {
        startGame(vX, vY, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void startGame(float vX, float vY, java.util.Map<String, String> context)
    {
        _iceI_startGameAsync(vX, vY, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> startGameAsync(float vX, float vY)
    {
        return _iceI_startGameAsync(vX, vY, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> startGameAsync(float vX, float vY, java.util.Map<String, String> context)
    {
        return _iceI_startGameAsync(vX, vY, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_startGameAsync(float iceP_vX, float iceP_vY, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "startGame", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_vX);
                     ostr.writeFloat(iceP_vY);
                 }, null);
        return f;
    }

    default void startDirectionHint(float vX, float vY, int seconds, int padding)
    {
        startDirectionHint(vX, vY, seconds, padding, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void startDirectionHint(float vX, float vY, int seconds, int padding, java.util.Map<String, String> context)
    {
        _iceI_startDirectionHintAsync(vX, vY, seconds, padding, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> startDirectionHintAsync(float vX, float vY, int seconds, int padding)
    {
        return _iceI_startDirectionHintAsync(vX, vY, seconds, padding, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> startDirectionHintAsync(float vX, float vY, int seconds, int padding, java.util.Map<String, String> context)
    {
        return _iceI_startDirectionHintAsync(vX, vY, seconds, padding, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_startDirectionHintAsync(float iceP_vX, float iceP_vY, int iceP_seconds, int iceP_padding, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "startDirectionHint", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_vX);
                     ostr.writeFloat(iceP_vY);
                     ostr.writeInt(iceP_seconds);
                     ostr.writeInt(iceP_padding);
                 }, null);
        return f;
    }

    default void disconnect(String reason)
    {
        disconnect(reason, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void disconnect(String reason, java.util.Map<String, String> context)
    {
        _iceI_disconnectAsync(reason, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> disconnectAsync(String reason)
    {
        return _iceI_disconnectAsync(reason, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> disconnectAsync(String reason, java.util.Map<String, String> context)
    {
        return _iceI_disconnectAsync(reason, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_disconnectAsync(String iceP_reason, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "disconnect", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeString(iceP_reason);
                 }, null);
        return f;
    }

    default void sendPaddlePositionX(float x)
    {
        sendPaddlePositionX(x, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void sendPaddlePositionX(float x, java.util.Map<String, String> context)
    {
        _iceI_sendPaddlePositionXAsync(x, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> sendPaddlePositionXAsync(float x)
    {
        return _iceI_sendPaddlePositionXAsync(x, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> sendPaddlePositionXAsync(float x, java.util.Map<String, String> context)
    {
        return _iceI_sendPaddlePositionXAsync(x, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_sendPaddlePositionXAsync(float iceP_x, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "sendPaddlePositionX", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_x);
                 }, null);
        return f;
    }

    default void sendTime(float timeInSeconds)
    {
        sendTime(timeInSeconds, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void sendTime(float timeInSeconds, java.util.Map<String, String> context)
    {
        _iceI_sendTimeAsync(timeInSeconds, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> sendTimeAsync(float timeInSeconds)
    {
        return _iceI_sendTimeAsync(timeInSeconds, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> sendTimeAsync(float timeInSeconds, java.util.Map<String, String> context)
    {
        return _iceI_sendTimeAsync(timeInSeconds, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_sendTimeAsync(float iceP_timeInSeconds, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "sendTime", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_timeInSeconds);
                 }, null);
        return f;
    }

    default void ready(byte b)
    {
        ready(b, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void ready(byte b, java.util.Map<String, String> context)
    {
        _iceI_readyAsync(b, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> readyAsync(byte b)
    {
        return _iceI_readyAsync(b, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> readyAsync(byte b, java.util.Map<String, String> context)
    {
        return _iceI_readyAsync(b, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_readyAsync(byte iceP_b, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "ready", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeByte(iceP_b);
                 }, null);
        return f;
    }

    default void timeModifier(float t)
    {
        timeModifier(t, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void timeModifier(float t, java.util.Map<String, String> context)
    {
        _iceI_timeModifierAsync(t, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> timeModifierAsync(float t)
    {
        return _iceI_timeModifierAsync(t, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> timeModifierAsync(float t, java.util.Map<String, String> context)
    {
        return _iceI_timeModifierAsync(t, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_timeModifierAsync(float iceP_t, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "timeModifier", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeFloat(iceP_t);
                 }, null);
        return f;
    }

    default void sendScore(int score, byte side)
    {
        sendScore(score, side, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void sendScore(int score, byte side, java.util.Map<String, String> context)
    {
        _iceI_sendScoreAsync(score, side, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> sendScoreAsync(int score, byte side)
    {
        return _iceI_sendScoreAsync(score, side, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> sendScoreAsync(int score, byte side, java.util.Map<String, String> context)
    {
        return _iceI_sendScoreAsync(score, side, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_sendScoreAsync(int iceP_score, byte iceP_side, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "sendScore", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeInt(iceP_score);
                     ostr.writeByte(iceP_side);
                 }, null);
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ServerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static ServerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static ServerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, ServerPrx.class, _ServerPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default ServerPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (ServerPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default ServerPrx ice_adapterId(String newAdapterId)
    {
        return (ServerPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default ServerPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (ServerPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default ServerPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (ServerPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default ServerPrx ice_invocationTimeout(int newTimeout)
    {
        return (ServerPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default ServerPrx ice_connectionCached(boolean newCache)
    {
        return (ServerPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default ServerPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (ServerPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ServerPrx ice_secure(boolean b)
    {
        return (ServerPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default ServerPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (ServerPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ServerPrx ice_preferSecure(boolean b)
    {
        return (ServerPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default ServerPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (ServerPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default ServerPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (ServerPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default ServerPrx ice_collocationOptimized(boolean b)
    {
        return (ServerPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default ServerPrx ice_twoway()
    {
        return (ServerPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default ServerPrx ice_oneway()
    {
        return (ServerPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default ServerPrx ice_batchOneway()
    {
        return (ServerPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default ServerPrx ice_datagram()
    {
        return (ServerPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default ServerPrx ice_batchDatagram()
    {
        return (ServerPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default ServerPrx ice_compress(boolean co)
    {
        return (ServerPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default ServerPrx ice_timeout(int t)
    {
        return (ServerPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default ServerPrx ice_connectionId(String connectionId)
    {
        return (ServerPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default ServerPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (ServerPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::a3DPong::Server";
    }
}
