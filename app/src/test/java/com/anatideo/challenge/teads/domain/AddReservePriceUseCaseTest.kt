package com.anatideo.challenge.teads.domain

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

class AddReservePriceUseCaseTest {

    private val auctionRepository = mockk<AuctionRepository>()
    private val addReservePriceUseCase = AddReservePriceUseCase(auctionRepository)

    @Test
    fun `when adding a reserve price it is added as expected`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)

        coEvery { auctionRepository.addReservePrice(any()) } just Runs

        // When
        runBlocking {
            addReservePriceUseCase(reservePrice)
        }

        // Then
        coVerify {
            auctionRepository.addReservePrice(reservePrice)
        }
    }
}