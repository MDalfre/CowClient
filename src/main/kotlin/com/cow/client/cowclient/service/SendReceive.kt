package com.cow.client.cowclient.service

import org.springframework.context.annotation.Bean
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class SendReceive {


    fun serverReceive(connectServer: Socket): String{

        while (connectServer.isConnected){

            val serverIn: InputStream = connectServer.getInputStream()
            val byteArrayOutputStream = ByteArrayOutputStream()
            val read = ByteArray(8024)
            var length: Int = serverIn.read(read)

            byteArrayOutputStream.write(read,0,length)

            val readBytes = byteArrayOutputStream.toByteArray()

            val stringBuilder = StringBuilder()
            for(b in readBytes){
                stringBuilder.append(String.format("%02X ", b))
            }
            println("[Server] -> $stringBuilder")
            return stringBuilder.toString()
        }
        connectServer.close()
        return "closed"

    }

    fun serverSend(connectServer: Socket, packetToSend: String){

        if (packetToSend != "closed"){
            //Regex para remover espaços; Remover espaços depois no StringBuilder do serverReceive
            var stringPacket = packetToSend.replace("\\s".toRegex(), "")
            val byteArray = ByteArray(stringPacket.length/2)
            for (i in byteArray.indices) {
                val index = i * 2
                val j = stringPacket.substring(index, index + 2).toInt(16)
                byteArray[i] = j.toByte()
            }
            println("[Client] -> $packetToSend")
            val serverOut: OutputStream = connectServer.getOutputStream()
            serverOut.write(byteArray)
        }else{
            println("Terminando conexão ...")
            connectServer.close()
        }

    }

    fun clientReceive(connectServer: Socket): String{

        while (connectServer.isConnected){

            val clientIn: InputStream = connectServer.getInputStream()
            val byteArrayOutputStream = ByteArrayOutputStream()
            val read = ByteArray(1024)
            var length: Int = clientIn.read(read)

            byteArrayOutputStream.write(read,0,length)

            val readBytes = byteArrayOutputStream.toByteArray()

            val stringBuilder = StringBuilder()
            for(b in readBytes){
                stringBuilder.append(String.format("%02X ", b))
            }
            println("[Client] -> $stringBuilder")
            return stringBuilder.toString()
        }
        connectServer.close()
        return "closed"

    }

    fun clientSend(connectServer: Socket, packetToSend: String){

        if (packetToSend != "closed"){
            //Regex para remover espaços; Remover espaços depois no StringBuilder do serverReceive
            var stringPacket = packetToSend.replace("\\s".toRegex(), "")
            val byteArray = ByteArray(stringPacket.length/2)
            for (i in byteArray.indices) {
                val index = i * 2
                val j = stringPacket.substring(index, index + 2).toInt(16)
                byteArray[i] = j.toByte()
            }
            println("[Client] <- $packetToSend")
            val serverOut: OutputStream = connectServer.getOutputStream()
            serverOut.write(byteArray)
        }else{
            println("Terminando conexão ...")
            connectServer.close()
        }
    }
}