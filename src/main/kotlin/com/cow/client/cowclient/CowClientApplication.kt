package com.cow.client.cowclient

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@EnableAutoConfiguration
@ComponentScan
class CowClientApplication

fun main(args: Array<String>) {
	runApplication<CowClientApplication>(*args)
}
