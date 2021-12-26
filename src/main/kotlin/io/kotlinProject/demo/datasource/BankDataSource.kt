package io.kotlinProject.demo.datasource

import io.kotlinProject.demo.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(): Bank

    fun getAccBank(accNum: String): Bank
}