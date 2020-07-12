package com.cow.client.cowclient.connection

import java.net.Socket

class SocketConfig {

    fun openConnectServer(ip: String, cnPort: Int): Socket {
        println("Conectando ao servidor remoto ...")
        return Socket(ip, cnPort).also { println("Connectado a $ip na porta $cnPort") }
    }


}