package io.kotlinProject.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class KotlinApplication {

	@Bean
	fun restTemplate(builder: RestTemplateBuilder): RestTemplate =
		builder.build()
	// Spring will find `restTemplate` that it wants to @Autowire
	// => there is a method annotated with `@bean` && this bean
	// provides `restTemplate` that Spring will take

	// at first everything will be parsed into BankList -> results ->
	// it'll be parsed into properties of `Bank`
}

fun main(args: Array<String>) {
	runApplication<KotlinApplication>(*args)
}


// Example of CURL:
// curl --header "Content-Type: application/json" --request POST --data '{"accountNumber":"44343", "trust":54.2, "transactionFee":4}' http://localhost:9000/api/banks

// UI (Controller) -> Service -> DB (in my case either
// NetworkDataSource || MockDataSource)

