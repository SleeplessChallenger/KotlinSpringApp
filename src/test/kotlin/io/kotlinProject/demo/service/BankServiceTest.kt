package io.kotlinProject.demo.service

import io.kotlinProject.demo.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val dataSource: BankDataSource = mockk()
    // easier way: if we don't care what `mockk()` returns
    // => `mockk(relaxed = true)`

    private val bankService = BankService(dataSource)

    @Test
    fun `should call it's data source to retrieve banks` () {
        // given
        // we're to specify every method call that we make
        // for `mockk`
        every { dataSource.retrieveBanks() } returns emptyList()

        // when
        val banks = bankService.getBanks()
        // then
        verify(exactly = 1) { dataSource.retrieveBanks() }
        // `verify()` comes from mockk()
    }
}