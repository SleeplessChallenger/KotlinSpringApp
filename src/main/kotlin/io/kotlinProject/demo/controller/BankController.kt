package io.kotlinProject.demo.controller

import io.kotlinProject.demo.model.Bank
import io.kotlinProject.demo.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
        // i.e. if `getBankAccount` returns error that no
        // such bank was found: SpringBoot will handle it
        fun handleIfNotFound(e: NoSuchElementException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
        fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @GetMapping
    fun getAllBanks(): Collection<Bank> {
        return service.getBanks()
    }

    @GetMapping("/oneBank")
    fun getOneBank(): Bank {
        return service.getBank()
    }

    @GetMapping("/bank/{accNum}")
    fun getBankAccount(@PathVariable accNum: String): Bank? {
        // @PathVariable allows putting params in `url`
        return service.getAccBank(accNum)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBank(@RequestBody bank: Bank): Bank {
        return service.addBank(bank)
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun updateBank(@RequestBody bank: Bank): Bank {
        return service.updateBank(bank)
    }

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String): Unit {
        service.deleteBank(accountNumber)
    }
 }