package io.kotlinProject.demo.datasource

import io.kotlinProject.demo.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(): Bank

    fun getAccBank(accNum: String): Bank

    fun createNewBank(bank: Bank): Bank

    fun tweakBank(bank: Bank): Bank

    fun deleteBank(accountNumber: String): Unit
}

// when we have multiple implementations of the `interface` we can:
// - annotate one as `@Primary`
// - make accept multiple beans
// - use `@Qualifier`
// To use the latter option: add name of the @Repository in the desired one
// && use @Qualifier in Service