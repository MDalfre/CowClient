package com.cow.client.cowclient.service

import com.cow.client.cowclient.connection.ClientSocket
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Service
class ClientService {

    @Bean
    fun mainThread() {
        val connectServer = ClientSocket().openConnectServer("211.43.146.215",44405)

        while (connectServer.isConnected){

            val serverIn: InputStream = connectServer.getInputStream()
            val byteArrayOutputStream = ByteArrayOutputStream()
            val read = ByteArray(1024)
            var length: Int = serverIn.read(read)

            byteArrayOutputStream.write(read,0,length)

            val readBytes = byteArrayOutputStream.toByteArray()

            val stringBuilder = StringBuilder()
            for(b in readBytes){
                stringBuilder.append(String.format("%02X ", b))
            }
            println("[Server] -> $stringBuilder")
        }
        connectServer.close()



    }

}