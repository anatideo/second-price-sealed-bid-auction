package com.anatideo.challenge.teads

import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.model.Bidder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import java.math.BigDecimal

class WinnerBidderUseCaseTest {

    private val auctionRepository = mockk<AuctionRepository>()
    private val winnerBidderUseCase = GetAuctionResultUseCase(auctionRepository)

    @Test
    fun `when getAuctionResult it should return the right winning price despite of bids order`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal.valueOf(377.0), BigDecimal.valueOf(1210.0)))
        val winningPrice = BigDecimal.valueOf(210.0)

        val bidders = listOf(
            Bidder(id = 2L, name = "z", bids = listOf(BigDecimal.valueOf(110.0), BigDecimal.valueOf(10.0))),
            Bidder(id = 3L, name = "y", bids = listOf(BigDecimal.valueOf(77.0), winningPrice, BigDecimal.valueOf(177.0))),
            winnerBidder
        )

        coEvery { auctionRepository.getReservePrice() } returns reservePrice
        coEvery { auctionRepository.getBidders() } returns bidders

        // When
        val result = runBlocking {
            winnerBidderUseCase()
        }

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(winningPrice, result?.winningPrice)

        coVerify {
            auctionRepository.getReservePrice()
            auctionRepository.getBidders()
        }
    }

    @Test
    fun `when getAuctionResult its winning price should be same as reserve price`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal.valueOf(377.0), BigDecimal.valueOf(1210.0)))

        val bidders = listOf(
            Bidder(id = 2L, name = "z", bids = listOf(BigDecimal.valueOf(10.0), BigDecimal.valueOf(90.0))),
            Bidder(id = 3L, name = "y", bids = listOf(BigDecimal.valueOf(77.0), BigDecimal.valueOf(88.1))),
            winnerBidder
        )

        coEvery { auctionRepository.getReservePrice() } returns reservePrice
        coEvery { auctionRepository.getBidders() } returns bidders

        // When
        val result = runBlocking {
            winnerBidderUseCase()
        }

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(reservePrice, result?.winningPrice)

        coVerify {
            auctionRepository.getReservePrice()
            auctionRepository.getBidders()
        }
    }

    @Test
    fun `when getAuctionResult there is no winner`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)

        val bidders = listOf(
            Bidder(id = 1L, name = "z", bids = listOf(BigDecimal.valueOf(10.0), BigDecimal.valueOf(90.0))),
            Bidder(id = 2L, name = "y", bids = listOf(BigDecimal.valueOf(77.0), BigDecimal.valueOf(88.1))),
            Bidder(id = 3L, name = "x", bids = listOf(BigDecimal.valueOf(27.0), BigDecimal.valueOf(27.0)))
        )

        coEvery { auctionRepository.getReservePrice() } returns reservePrice
        coEvery { auctionRepository.getBidders() } returns bidders

        // When
        val result = runBlocking {
            winnerBidderUseCase()
        }

        // Then
        assertEquals(null, result)

        coVerify {
            auctionRepository.getReservePrice()
            auctionRepository.getBidders()
        }
    }

    @Test
    fun `when getAuctionResult there is a winner despite of the auction having a single bidder`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal.valueOf(27.0), BigDecimal.valueOf(199.2)))

        val bidders = listOf(
            winnerBidder
        )

        coEvery { auctionRepository.getReservePrice() } returns reservePrice
        coEvery { auctionRepository.getBidders() } returns bidders

        // When
        val result = runBlocking {
            winnerBidderUseCase()
        }

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(reservePrice, result?.winningPrice)

        coVerify {
            auctionRepository.getReservePrice()
            auctionRepository.getBidders()
        }
    }

    @Test
    fun `when getAuctionResult there is no winner because there is no bidders`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)
        val bidders = emptyList<Bidder>()

        coEvery { auctionRepository.getReservePrice() } returns reservePrice
        coEvery { auctionRepository.getBidders() } returns bidders

        // When
        val result = runBlocking {
            winnerBidderUseCase()
        }

        // Then
        assertEquals(null, result)

        coVerify {
            auctionRepository.getReservePrice()
            auctionRepository.getBidders()
        }
    }
}