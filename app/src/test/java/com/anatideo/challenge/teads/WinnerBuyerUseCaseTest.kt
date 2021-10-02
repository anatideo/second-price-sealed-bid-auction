package com.anatideo.challenge.teads

import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.WinnerBuyerUseCase
import com.anatideo.challenge.teads.domain.model.Buyer
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*

class WinnerBuyerUseCaseTest {

    private val auctionRepository = mockk<AuctionRepository>()
    private val winnerBuyerUseCase = WinnerBuyerUseCase(auctionRepository)

    @Test
    fun `when getAuctionResult it should return the expected winner and winning price`() {
        // Given
        val reservePrice = 100.0
        val winnerBuyer = Buyer(name = "x", bids = listOf(377.0, 1210.0))
        val winningPrice = 210.0

        val buyers = listOf(
            Buyer(name = "z", bids = listOf(10.0, 110.0)),
            Buyer(name = "y", bids = listOf(77.0, winningPrice)),
            winnerBuyer
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBuyers() } returns buyers

        // When
        val result = winnerBuyerUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBuyer, result?.buyer)
        assertEquals(winningPrice, result?.winningPrice)
    }

    @Test
    fun `when getAuctionResult its winning price should be same as reserve price`() {
        // Given
        val reservePrice = 100.0
        val winnerBuyer = Buyer(name = "x", bids = listOf(377.0, 1210.0))

        val buyers = listOf(
            Buyer(name = "z", bids = listOf(10.0, 90.0)),
            Buyer(name = "y", bids = listOf(77.0, 88.1)),
            winnerBuyer
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBuyers() } returns buyers

        // When
        val result = winnerBuyerUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBuyer, result?.buyer)
        assertEquals(reservePrice, result?.winningPrice)
    }

    @Test
    fun `when getAuctionResult there is no winner`() {
        // Given
        val reservePrice = 100.0

        val buyers = listOf(
            Buyer(name = "z", bids = listOf(10.0, 90.0)),
            Buyer(name = "y", bids = listOf(77.0, 88.1)),
            Buyer(name = "x", bids = listOf(27.0, 99.2))
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBuyers() } returns buyers

        // When
        val result = winnerBuyerUseCase.getAuctionResult()

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `when getAuctionResult there is a winner despite of we have no competitors`() {
        // Given
        val reservePrice = 100.0
        val winnerBuyer = Buyer(name = "x", bids = listOf(27.0, 199.2))

        val buyers = listOf(
            winnerBuyer
        )

        every { auctionRepository.getReservePrice() } returns reservePrice
        every { auctionRepository.getBuyers() } returns buyers

        // When
        val result = winnerBuyerUseCase.getAuctionResult()

        // Then
        assertEquals(winnerBuyer, result?.buyer)
        assertEquals(reservePrice, result?.winningPrice)
    }
}