package com.cow.client.cowclient.controller


import com.cow.client.cowclient.request.ConfigRequest
import com.cow.client.cowclient.response.ConfigResponse
import com.cow.client.cowclient.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import kotlin.concurrent.thread

@RestController
class AppController(val clientService: ClientService) {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/start")
    fun start(): String {
        thread(start = true){
            clientService.mainThread()
        }

        return "iniciado"
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/packet2Server")
    fun send2Server(@RequestParam packet: String):String{
        clientService.sendPackets2Server(packet)
        return "Packet Send !"
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/packet2Client")
    fun send2Client(@RequestParam packet: String):String{
        clientService.sendPackets2Client(packet)
        return "Packet Send !"
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/test")
    fun debug():String{
        println("Debug mode")
        return "Sending ..."
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/config")
    fun getConfig(): ConfigResponse{
        return ConfigResponse(
                clientService.serverLog,
                clientService.clientLog,
                clientService.connectServer.isConnected
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/config")
    fun config(@RequestBody configRequest: ConfigRequest):ConfigRequest{
        clientService.serverLog = configRequest.serverLog
        clientService.clientLog = configRequest.clientLog
        return configRequest
    }

}