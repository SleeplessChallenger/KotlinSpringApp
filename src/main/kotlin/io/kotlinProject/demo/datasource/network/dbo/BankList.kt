package io.kotlinProject.demo.datasource.network.dbo

import io.kotlinProject.demo.model.Bank

data class BankList (
    val results: Collection<Bank>
    )