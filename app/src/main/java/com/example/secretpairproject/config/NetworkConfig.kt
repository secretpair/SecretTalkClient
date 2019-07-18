package com.example.secretpairproject.config


//socket connect const
const val LOCAL_HOST_BASE = "http://10.0.2.2:4000"
const val NS_CHAT = "/chat"

const val HEARTBEAT_INTERVAL = 2000L
const val HEARTBEAT_TIMEOUT = 10000L
const val RECONNECT_TRY_MAX_COUNT = 5
const val RECONNECT_DELAY = 1500L

//socket connect message

const val CHAT_NAME_SPACE = "/socket"
const val SEND_MESSAGE = "send message"
const val SOCKET_ECHO = "echo"

const val CHAT_ROOM_JOIN = "chat_room_connect"
const val CHAT_ROOM_LEAVE = "chat_room_disconnect"

const val SEND_TEXT_MESSAGE = "client_text_message_handle"
const val RECEIVE_TEXT_MESSAGE = "text_message_from_server"







