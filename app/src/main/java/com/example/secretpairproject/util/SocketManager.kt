package com.example.secretpairproject.util


import android.util.Log
import com.example.secretpairproject.config.*
import com.github.nkzawa.socketio.client.Manager
import com.github.nkzawa.socketio.client.Socket
import java.net.URI

object SocketManager {

    lateinit var socket: Socket

    init {
        createSocket()
        makeConnection()
    }


    private fun createSocket() {
        val manager = Manager(URI.create(LOCAL_HOST_BASE))
        manager.timeout(HEARTBEAT_TIMEOUT)
        manager.reconnection(true)
        manager.reconnectionAttempts(RECONNECT_TRY_MAX_COUNT)
        manager.reconnectionDelay(RECONNECT_DELAY)
        socket = manager.socket(NS_CHAT)
    }

    private fun makeConnection() {
        socket.connect()
    }

    private fun disconnection() {
        socket.disconnect()
    }


}
