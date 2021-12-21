package io.kotlinProject.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping("getBoot")
    fun helloWorld(): String {
        return "Hello, this is REST endpoint"
    }


}


// `RequestController` is to tell Spring that this method can handle REST requests
// `RequestMapping` is to tell which API will this method will handle
// `GetMapping` is an endpoint that tells it's for GET requests + we
// can add additional endpoint
