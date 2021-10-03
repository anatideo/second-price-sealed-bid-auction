package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider
import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.data.database.model.DataReservePrice
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.data.mapper.DataReservePriceMapper
import com.anatideo.challenge.teads.domain.model.Bid
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.math.BigDecimal

class AuctionLocalDataSourceImplTest {

    private val auctionDatabaseProvider = mockk<AuctionDatabaseProvider>()
    private val dataBidMapper = mockk<DataBidMapper>()
    private val dataReservePriceMapper = mockk<DataReservePriceMapper>()

    private val auctionLocalDataSourceImpl = AuctionLocalDataSourceImpl(
        auctionDatabase = auctionDatabaseProvider,
        dataBidMapper = dataBidMapper,
        dataReservePriceMapper = dataReservePriceMapper
    )

    @Test
    fun `when getting reserve price it is returned as expected`() {
        // Given
        val dataReservePrice = DataReservePrice(value = BigDecimal.valueOf(100.0))

        coEvery { auctionDatabaseProvider.getReservePriceDao().get() } returns dataReservePrice

        // When
        val result = runBlocking {
            auctionLocalDataSourceImpl.getReservePrice()
        }

        assertEquals(dataReservePrice.value, result)

        coVerify(ordering = Ordering.SEQUENCE) {
            auctionDatabaseProvider.getReservePriceDao().get()
        }
    }

    @Test
    fun `when getting bidders it is returned as expected`() {
        // Given
        val dataBids = listOf(DataBid(bidderId = 1L, name = null, bids = mutableListOf(BigDecimal.valueOf(10.0))))

        coEvery { auctionDatabaseProvider.getDataBidDao().getAll() } returns dataBids

        // When
        val result = runBlocking {
            auctionLocalDataSourceImpl.getBidders()
        }

        assertEquals(dataBids, result)

        coVerify {
            auctionDatabaseProvider.getDataBidDao().getAll()
        }
    }

    @Test
    fun `when adding a bid it does not exists yet in database so it is inserted`() {
        // Given
        val bid = Bid(bidderId = 1L, name = null, value = BigDecimal.valueOf(11120.70))
        val dataBid = DataBid(bidderId = 1L, name = null, mutableListOf(BigDecimal.valueOf(11120.70)))

        coEvery { dataBidMapper.map(any()) } returns dataBid
        coEvery { auctionDatabaseProvider.getDataBidDao().getById(any()) } returns null
        coEvery { auctionDatabaseProvider.getDataBidDao().insert(any()) } just Runs

        // When
        runBlocking {
            auctionLocalDataSourceImpl.addBid(bid)
        }

        coVerify {
            auctionDatabaseProvider.getDataBidDao().insert(dataBid)
        }
    }

    @Test
    fun `when adding a bid since it exists in database it is updated`() {
        // Given
        val bid = Bid(bidderId = 1L, name = null, value = BigDecimal.valueOf(20000.0))
        val dataBid = DataBid(bidderId = 1L, name = null, mutableListOf(BigDecimal.valueOf(11120.70)))
        val updatedBids = mutableListOf(BigDecimal.valueOf(11120.70), BigDecimal.valueOf(20000.0))
        val expectedDataBid = DataBid(bidderId = 1L, name = null, updatedBids)

        coEvery { auctionDatabaseProvider.getDataBidDao().getById(any()) } returns dataBid
        coEvery { auctionDatabaseProvider.getDataBidDao().update(any()) } just Runs

        // When
        runBlocking {
            auctionLocalDataSourceImpl.addBid(bid)
        }

        coVerify {
            auctionDatabaseProvider.getDataBidDao().update(expectedDataBid)
        }
    }

    @Test
    fun `when adding reserve price it is added as expected`() {
        // Given
        val reservePrice = BigDecimal.valueOf(100.0)
        val dataReservePrice = DataReservePrice(value = reservePrice)

        coEvery { dataReservePriceMapper.map(any()) } returns dataReservePrice
        coEvery { auctionDatabaseProvider.getReservePriceDao().insert(any()) } just Runs
        coEvery { auctionDatabaseProvider.getReservePriceDao().nukeTable() } just Runs
        coEvery { auctionDatabaseProvider.getDataBidDao().nukeTable() } just Runs

        // When
        runBlocking {
            auctionLocalDataSourceImpl.addReservePrice(reservePrice)
        }

        coVerify(ordering = Ordering.SEQUENCE) {
            auctionDatabaseProvider.getReservePriceDao().nukeTable()
            auctionDatabaseProvider.getDataBidDao().nukeTable()
            auctionDatabaseProvider.getReservePriceDao().insert(dataReservePrice)
        }
    }
}