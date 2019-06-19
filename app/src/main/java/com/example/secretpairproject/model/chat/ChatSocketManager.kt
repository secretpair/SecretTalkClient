package com.example.secretpairproject.model.chat


import com.example.secretpairproject.config.*
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Manager
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import java.net.URI

object ChatSocketManager {

    lateinit var socket: Socket

    init {
        createSocket()
    }


    private fun createSocket() {
        val manager = Manager(URI.create(LOCAL_HOST_BASE))
        manager.timeout(HEARTBEAT_TIMEOUT)
        manager.reconnection(true)
        manager.reconnectionAttempts(RECONNECT_TRY_MAX_COUNT)
        manager.reconnectionDelay(RECONNECT_DELAY)
        socket = manager.socket(NS_CHAT)
    }


     fun makeConnection() {
        socket.connect()
    }

     fun registerSocketListener(eventName: String, listener: Emitter.Listener) {
        socket.on(eventName, listener)
    }

     fun unRegisterSocketListener() {
        socket.off()
    }

     fun disconnection() {
        socket.disconnect()
    }


}
