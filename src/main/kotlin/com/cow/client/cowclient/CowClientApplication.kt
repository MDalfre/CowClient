package com.cow.client.cowclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CowClientApplication

fun main(args: Array<String>) {
	runApplication<CowClientApplication>(*args)
}
