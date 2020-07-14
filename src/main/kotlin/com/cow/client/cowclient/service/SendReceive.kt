package com.cow.client.cowclient.service

import com.cow.client.cowclient.commons.Indicator
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class SendReceive {


    fun receive(connectServer: Socket, indicator: Indicator, log: Boolean): String {

        while (connectServer.isConnected) {

            val serverIn: InputStream = connectServer.getInputStream()
            if (serverIn.available() == 0) {
                break
            }
            val byteArrayOutputStream = ByteArrayOutputStream()
            val read = ByteArray(8024)
            var length: Int = serverIn.read(read)

            byteArrayOutputStream.write(read, 0, length)

            val readBytes = byteArrayOutputStream.toByteArray()

            val stringBuilder = StringBuilder()
            for (b in readBytes) {
                stringBuilder.append(String.format("%02X ", b))
            }
            when (log) {
                true -> println("[$indicator] -> $stringBuilder")
            }
            return stringBuilder.toString()
        }

        return "empty"

    }

    fun send(connectServer: Socket, packetToSend: String, indicator: Indicator, log: Boolean) {

        if (packetToSend != "empty") {
            //Regex para remover espaços; Remover espaços depois no StringBuilder do serverReceive
            var stringPacket = packetToSend.replace("\\s".toRegex(), "")

            val byteArray = ByteArray(stringPacket.length / 2)
            for (i in byteArray.indices) {
                val index = i * 2
                val j = stringPacket.substring(index, index + 2).toInt(16)
                byteArray[i] = j.toByte()
            }
            when(log){
                true -> println("[$indicator] <- $packetToSend")
            }
            val serverOut: OutputStream = connectServer.getOutputStream()
            serverOut.write(byteArray)
        }

    }


}