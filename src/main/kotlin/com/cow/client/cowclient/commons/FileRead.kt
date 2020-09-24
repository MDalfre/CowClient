package com.cow.client.cowclient.commons

import java.io.File
import java.nio.charset.Charset

class FileRead{

    val encPath: String = "C:\\Users\\marci\\Documents\\GitHub\\cow-client\\cow-client\\src\\main\\resources\\datFiles\\Enc1.dat"
    val decPath: String = "C:\\Users\\marci\\Documents\\GitHub\\cow-client\\cow-client\\src\\main\\resources\\datFiles\\Dec2.dat"


    fun readEnc(): String{
        val readBytes = File(encPath).inputStream().readBytes()
        val stringBuilder = ByteToHex().toHex(readBytes).toString()
        println("Enc2 file size [${readBytes.size}]bytes")
        return stringBuilder
    }

    fun readDec():String{
        val readBytes = File(decPath).inputStream().readBytes()
        val stringBuilder = ByteToHex().toHex(readBytes).toString()
        println("Dec1 file size [${readBytes.size}]bytes")
        return stringBuilder
    }
}