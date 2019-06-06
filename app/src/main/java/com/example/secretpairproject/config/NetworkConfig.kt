package com.example.secretpairproject.config


//socket connect const
const val LOCAL_HOST_BASE = "http://10.0.2.2:4000"
const val LOCAL_HOST_SOCKET = "http://10.0.2.2:4000/socket"


const val HEARTBEAT_INTERVAL = 2000L
const val HEARTBEAT_TIMEOUT = 10000L
const val RECONNECT_TRY_MAX_COUNT = 5
const val RECONNECT_DELAY = 1500L

//socket connect message

const val CHAT_NAME_SPACE = "/socket"
const val SEND_MESSAGE = "send message"
const val SOCKET_ECHO = "echo"



