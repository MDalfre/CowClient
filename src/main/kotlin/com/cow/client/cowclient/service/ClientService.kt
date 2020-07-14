package com.cow.client.cowclient.service

import com.cow.client.cowclient.commons.Indicator
import com.cow.client.cowclient.connection.SocketConfig
import com.cow.client.cowclient.packets.List
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.net.Socket

@Service
class ClientService(
        @Value("\${connect.server.ip}")
        val connectServerIp:String,
        @Value("\${connect.server.port}")
        val connectServerPort:Int,
        @Value("\${game.server.ip}")
        val gameServerIp:String,
        @Value("\${game.server.port}")
        val gameServerPort:Int,
        @Value("\${client.server.port}")
        val clientPort: Int
) {

    val socketConfig = SocketConfig()
    val sendReceive = SendReceive()

    @Bean
    fun mainThread() {

        // --- Muonline Server
        val client2server: Socket = socketConfig.openClient2Server(clientPort)
        val connectServer: Socket = socketConfig.openConnectServer(gameServerIp,gameServerPort)


        while(true){

            // [] -- [X] -- [<]
            // --- Server Server ( recebe pacotes servidor )
            val serverPackets = sendReceive.receive(connectServer,Indicator.Server, true)

            // --- Server Client
            sendReceive.send(client2server, serverPackets, Indicator.Client, false)

            // --- Client Server
            val clientPackets = sendReceive.receive(client2server,Indicator.Client, true)

            // [] -- [>] -- [X]
            // --- Server Server ( envia pacotes servidor )
            sendReceive.send(connectServer, clientPackets, Indicator.Server, false)

        }
    }


}