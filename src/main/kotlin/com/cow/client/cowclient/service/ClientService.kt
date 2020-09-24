package com.cow.client.cowclient.service

import com.cow.client.cowclient.commons.ByteToHex
import com.cow.client.cowclient.commons.Indicator
import com.cow.client.cowclient.connection.SocketConfig
import com.cow.client.cowclient.packets.decoders.Coord
import org.springframework.beans.factory.annotation.Value
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

    // --- Muonline Server
    lateinit var client2server: Socket
    lateinit var connectServer: Socket
    var serverLog = true
    var clientLog = true



    fun mainThread() {

        client2server = socketConfig.openClient2Server(clientPort)
        connectServer = socketConfig.openConnectServer(gameServerIp,gameServerPort)


        do{

            // --- Server Server ( recebe pacotes servidor )
            var serverPackets = sendReceive.receive(connectServer,Indicator.Server, serverLog)

            serverPackets = filter(serverPackets)

            // --- Server Client
            sendReceive.send(client2server, serverPackets, Indicator.Client, false)

            // --- Client Server
            val clientPackets = sendReceive.receive(client2server,Indicator.Client, clientLog)

            // --- Server Server ( envia pacotes servidor )
            sendReceive.send(connectServer, clientPackets, Indicator.Server, false)

        }while(client2server.isConnected || connectServer.isConnected)

        println("[System] Disconnected from the server")
    }

    fun filter(serverPackts: String):String {
        val currentPacket = serverPackts
        if (serverPackts.length == 24) {
            if (serverPackts.substring(0, 5) == "C1 08") {
                val serverPacketsCut = serverPackts.substring(6, 23)
                val hexArray = serverPacketsCut.split("\\s".toRegex()).toMutableList()
                val longArray = mutableListOf<Long>()
                hexArray.forEach {
                    longArray.add(java.lang.Long.parseLong(it, 16))
                }
                val testcord = Coord(longArray[1], longArray[3]).decode()
                println("[Parsed] We found a ${encounterType(longArray[2])} id: ${longArray[4]} direction: ${directionParser(longArray[5])} coords ${testcord.x},${testcord.y}")
                return currentPacket
            }
        }else if (serverPackts.length == 33) {
            if (serverPackts.substring(0, 5) == "C3 0B") {
                val serverPacketsCut = serverPackts.substring(6, 32)
                val hexArray = serverPacketsCut.split("\\s".toRegex()).toMutableList()
                val longArray = mutableListOf<Long>()
                hexArray.forEach {
                    longArray.add(java.lang.Long.parseLong(it, 16))
                }
                var print = "PACKET -> "
                longArray.forEach { print = "$print $it" }
                println(print)

            }

            return currentPacket
        }

        return currentPacket
    }

    fun encounterType(type: Long):String{
        return when(type){
            3.toLong() -> "Guard"
            4.toLong() -> "Bull Fighter"
            20.toLong() -> "Spider"
            45.toLong() -> "Player"
            else -> "Unknown"
        }

    }

    fun directionParser(direction: Long): String{
        return when(direction){
            48.toLong() -> "Direita"
            112.toLong() -> "Esquerda"
            16.toLong() -> "Baixo"
            64.toLong() -> "Cima"
            else -> "Diagonal"
        }

    }

    fun sendPackets2Server(packet: String){
        sendReceive.send(connectServer, packet, Indicator.Server, true)
    }

    fun sendPackets2Client(packet: String){
        sendReceive.send(client2server, packet, Indicator.Client, true)
    }


}