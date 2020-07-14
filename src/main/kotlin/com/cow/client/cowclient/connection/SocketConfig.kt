package com.cow.client.cowclient.connection

import java.net.ServerSocket
import java.net.Socket

class SocketConfig {

    fun openConnectServer(ip: String, cnPort: Int): Socket {
        println("[System] Connecting to remote server ...")
        return Socket(ip, cnPort).also { println("[System] Connected to $ip at port $cnPort") }
    }

    fun openDebugServer():Socket{
        return Socket("192.168.0.102",55903).also { println("Connectado como debugServer") }
    }

    fun openClient2Server(clPort: Int): Socket{
        println("[System] Waiting for connections at port $clPort")
        return  ServerSocket(clPort).accept().also { println("[System] Client connected !") }
    }

    fun openGameServer(ip:String, cnPort: Int): Socket {
        println("Conectando ao Game Server ...")
        return Socket(ip,cnPort).also { println("Connectado a $ip´na porta $cnPort") }
    }



}