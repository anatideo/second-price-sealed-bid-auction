package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.model.Bid
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

class AddBidUseCaseTest {

    private val auctionRepository = mockk<AuctionRepository>()
    private val addBidUseCase = AddBidUseCase(auctionRepository)

    @Test
    fun `when adding a bid it is added as expected`() {
        // Given
        val bid = Bid(bidderId = 1L, name = null, value = BigDecimal.valueOf(100.0))

        coEvery { auctionRepository.addBid(any()) } just Runs

        // When
        runBlocking {
            addBidUseCase(bid)
        }

        // Then
        coVerify {
            auctionRepository.addBid(bid)
        }
    }
}