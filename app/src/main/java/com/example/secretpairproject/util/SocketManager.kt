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
        socket = manager.socket("/chat")
    }

    private fun makeConnection() {
        socket.connect()
        if (socket.connected()) {
            Log.e("연결했냐", "했심다 형님")
        } else {
            Log.e("연결했냐", "실패 했심다 형님")
        }
    }


}
