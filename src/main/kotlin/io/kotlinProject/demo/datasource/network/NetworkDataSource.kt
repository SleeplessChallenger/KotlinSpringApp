package io.kotlinProject.demo.datasource.network

import io.kotlinProject.demo.datasource.BankDataSource
import io.kotlinProject.demo.datasource.network.dbo.BankList
import io.kotlinProject.demo.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException


// to make `RestTemplate` work, we're to provide additional things
// in start application (In my case it's `DemoApplication.kt`)
@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
): BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {
        // getForEntity: `get` is request type && `ForEntity` means we want
        // to parse the response into object of some type.
        // `response` will contain all the fields which we even don't need
        // => create datatype that at first extract desired columns
        // && further we'll extract other required columns.
        // I.e. at first we take `results` which is nested (`BankList.kt`)
        // JSON && then we parse `results` in `Bank.kt`
        val response: ResponseEntity<BankList> = restTemplate.getForEntity<BankList>(
            "http://54.193.31.159/banks")

        return response.body?.results
            ?: throw IOException("Couldn't fetch banks from the network")
    }

    override fun retrieveBank(): Bank {
        TODO("Not yet implemented")
    }

    override fun getAccBank(accNum: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createNewBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun tweakBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }


}