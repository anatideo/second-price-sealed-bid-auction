package com.anatideo.challenge.teads

import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.model.Bidder
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.Assert.*
import java.math.BigDecimal

class WinnerBidderUseCaseTest {

    private val auctionRepository = mockk<AuctionRepository>()
    private val winnerBidderUseCase = GetAuctionResultUseCase(auctionRepository)

    @Test
    fun `when getAuctionResult it should return the right winning price despite of bids order`() {
        // Given
        val reservePrice = BigDecimal(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal(377.0), BigDecimal(1210.0)))
        val winningPrice = BigDecimal(210.0)

        val bidders = listOf(
            Bidder(id = 2L, name = "z", bids = listOf(BigDecimal(110.0), BigDecimal(10.0))),
            Bidder(id = 3L, name = "y", bids = listOf(BigDecimal(77.0), winningPrice, BigDecimal(177.0))),
            winnerBidder
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBidders() } returns bidders

        // When
        val result = winnerBidderUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(winningPrice, result?.winningPrice)
    }

    @Test
    fun `when getAuctionResult its winning price should be same as reserve price`() {
        // Given
        val reservePrice = BigDecimal(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal(377.0), BigDecimal(1210.0)))

        val bidders = listOf(
            Bidder(id = 2L, name = "z", bids = listOf(BigDecimal(10.0), BigDecimal(90.0))),
            Bidder(id = 3L, name = "y", bids = listOf(BigDecimal(77.0), BigDecimal(88.1))),
            winnerBidder
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBidders() } returns bidders

        // When
        val result = winnerBidderUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(reservePrice, result?.winningPrice)
    }

    @Test
    fun `when getAuctionResult there is no winner`() {
        // Given
        val reservePrice = BigDecimal(100.0)

        val bidders = listOf(
            Bidder(id = 1L, name = "z", bids = listOf(BigDecimal(10.0), BigDecimal(90.0))),
            Bidder(id = 2L, name = "y", bids = listOf(BigDecimal(77.0), BigDecimal(88.1))),
            Bidder(id = 3L, name = "x", bids = listOf(BigDecimal(27.0), BigDecimal(27.0)))
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBidders() } returns bidders

        // When
        val result = winnerBidderUseCase.getAuctionResult()

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `when getAuctionResult there is a winner despite of the auction having a single bidder`() {
        // Given
        val reservePrice = BigDecimal(100.0)
        val winnerBidder = Bidder(id = 1L, name = "x", bids = listOf(BigDecimal(27.0), BigDecimal(199.2)))

        val bidders = listOf(
            winnerBidder
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBidders() } returns bidders

        // When
        val result = winnerBidderUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBidder, result?.bidder)
        assertEquals(reservePrice, result?.winningPrice)
    }

    @Test
    fun `when getAuctionResult there is no winner because there is no bidders`() {
        // Given
        val reservePrice = BigDecimal(100.0)
        val bidders = emptyList<Bidder>()

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBidders() } returns bidders

        // When
        val result = winnerBidderUseCase.getAuctionResult()

        // Then
        assertEquals(null, result)
    }
}