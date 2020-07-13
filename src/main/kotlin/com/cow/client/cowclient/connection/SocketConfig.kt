package com.cow.client.cowclient.connection

import java.net.ServerSocket
import java.net.Socket

class SocketConfig {

    fun openConnectServer(ip: String, cnPort: Int): Socket {
        println("Conectando ao servidor remoto ...")
        return Socket(ip, cnPort).also { println("Connectado a $ip na porta $cnPort") }
    }

    fun openDebugServer():Socket{
        return Socket("127.0.0.1",44406).also { println("Connectado como debugServer") }
    }

    fun openClient2Server(): Socket{
        return  ServerSocket(44405).accept().also { println("Iniciando servidor na porta 44405 ...") }
    }

    fun openGameServer(ip:String, cnPort: Int): Socket {
        println("Conectando ao Game Server ...")
        return Socket(ip,cnPort).also { println("Connectado a $ip´na porta $cnPort") }
    }



}