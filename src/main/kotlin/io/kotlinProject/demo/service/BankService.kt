package io.kotlinProject.demo.service

import io.kotlinProject.demo.datasource.BankDataSource
import io.kotlinProject.demo.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class BankService(@Qualifier("mock") private val dataSource: BankDataSource) {
    // class requires `object` of TYPE BankDataSource

    fun getBanks(): Collection<Bank> {
        return dataSource.retrieveBanks()
    }

    fun getBank(): Bank {
        return dataSource.retrieveBank()
    }

    fun getAccBank(accNum: String): Bank {
        return dataSource.getAccBank(accNum)
    }

    fun addBank(bank: Bank): Bank {
        return dataSource.createNewBank(bank)
    }

    fun updateBank(bank: Bank): Bank {
        return dataSource.tweakBank(bank)
    }

    fun deleteBank(accountNumber: String): Unit {
        dataSource.deleteBank(accountNumber)
    }

}

// @Service makes it (i.e. object of the class) available in the runtime
// + conveys meaning that Service level bean: calling data source, handling
// exceptions, transformations of the data etc