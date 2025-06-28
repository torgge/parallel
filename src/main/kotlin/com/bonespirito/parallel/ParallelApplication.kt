package com.bonespirito.parallel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParallelApplication

fun main(args: Array<String>) {
	runApplication<ParallelApplication>(*args)
}
