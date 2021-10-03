package com.anatideo.challenge.teads.data

import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.data.localsource.AuctionDataSource
import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

class AuctionRepositoryImplTest {

    private val auctionDataSource = mockk<AuctionDataSource>()
    private val bidderMapper =  mockk<BidderMapper>()

    private val auctionRepositoryImpl = AuctionRepositoryImpl(
        auctionDataSource,
        bidderMapper
    )

    @Test
    fun `when getting reserve price it is returned as expected`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)

        coEvery { auctionDataSource.getReservePrice() } returns reservePrice

        // When
        val result = runBlocking {
            auctionRepositoryImpl.getReservePrice()
        }

        assertEquals(reservePrice, result)

        coVerify {
            auctionDataSource.getReservePrice()
        }
    }

    @Test
    fun `when getting bidders it is returned as expected`() {
        // Given
        val dataBid = listOf(DataBid(bidderId = 1L, name = null, mutableListOf(BigDecimal.valueOf(100.0))))
        val bidder = Bidder(id = 1L, name = null, bids = listOf(BigDecimal.valueOf(100.0)))
        val bidderList = listOf(bidder)

        coEvery { auctionDataSource.getBidders() } returns dataBid
        coEvery { bidderMapper.map(any()) } returns bidder

        // When
        val result = runBlocking {
            auctionRepositoryImpl.getBidders()
        }

        assertEquals(bidderList, result)

        coVerify {
            auctionDataSource.getBidders()
        }
    }

    @Test
    fun `when adding bid it is added as expected`() {
        // Given
        val bid = Bid(bidderId = 1L, name = null, value = BigDecimal.valueOf(12000.0))

        coEvery { auctionDataSource.addBid(any()) } just Runs

        // When
        runBlocking {
            auctionRepositoryImpl.addBid(bid)
        }

        coVerify {
            auctionDataSource.addBid(bid)
        }
    }

    @Test
    fun `when adding reserve price it is added as expected`() {
        // Given
        val reservePrice = BigDecimal.valueOf(12000.0)

        coEvery { auctionDataSource.addReservePrice(any()) } just Runs

        // When
        runBlocking {
            auctionRepositoryImpl.addReservePrice(reservePrice)
        }

        coVerify {
            auctionDataSource.addReservePrice(reservePrice)
        }
    }
}