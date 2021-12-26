package io.kotlinProject.demo.datasource.mock

import io.kotlinProject.demo.datasource.BankDataSource
import io.kotlinProject.demo.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    val banks = listOf(
        Bank("435353", 54.0, 1),
        Bank("545464", 12.0, 5),
        Bank("435353", 23.0, 9),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(): Bank {
        return banks.random()
    }

    override fun getAccBank(accNum: String): Bank {
        // hashed row will give default message to Spring
//        return banks.first { it.accountNumber == accNum }
        return banks.firstOrNull() { it.accountNumber == accNum }
            ?: throw NoSuchElementException("DB doesn't have such bank")
    }
}


// Repository implicates that it's responsible for retrieving/
// storing data. I.e. functionality to access the entity