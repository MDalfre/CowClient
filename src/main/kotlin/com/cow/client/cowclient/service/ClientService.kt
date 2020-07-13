package com.cow.client.cowclient.service

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
        val gameServerPort:Int
) {

    val socketConfig = SocketConfig()
    val sendReceive = SendReceive()

    @Bean
    fun mainThread() {

        // --- Muonline Server
        //val connectServer: Socket = socketConfig.openConnectServer(connectServerIp,connectServerPort)
        val connectServer:Socket = socketConfig.openDebugServer()
        val client2server: Socket = socketConfig.openClient2Server()


        println("[Iniciando login ConnectServer]")
        var connectServerlist = List().connectServerClient


        for(packets in connectServerlist){

            // [] -- [X] -- [<]
            // --- Server Server ( recebe pacotes servidor )
            val serverPackets = sendReceive.serverReceive(connectServer)

            // --- Server Client
            sendReceive.clientSend(client2server, serverPackets)

            // --- Client Server
            val clientPackets = sendReceive.clientReceive(client2server)

            // [] -- [>] -- [X]
            // --- Server Server ( envia pacotes servidor )
            sendReceive.serverSend(connectServer, clientPackets)

        }

        val gameServer: Socket = socketConfig.openGameServer(gameServerIp,gameServerPort)
        println("[Iniciando login GameServer]")
        val gameServerList = List().gameServerClient

        for (packets in gameServerList){
            sendReceive.serverReceive(gameServer)
            sendReceive.serverSend(gameServer, packets)
        }
    }


}