package io.kotlinProject.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinApplication

fun main(args: Array<String>) {
	runApplication<KotlinApplication>(*args)
}


// Example of CURL:
// curl --header "Content-Type: application/json" --request POST --data '{"accountNumber":"44343", "trust":54.2, "transactionFee":4}' http://localhost:9000/api/banks