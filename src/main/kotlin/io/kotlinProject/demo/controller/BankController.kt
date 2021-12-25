package io.kotlinProject.demo.controller

import io.kotlinProject.demo.model.Bank
import io.kotlinProject.demo.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @GetMapping
    fun getAllBanks(): Collection<Bank> {
        return service.getBanks()
    }
}