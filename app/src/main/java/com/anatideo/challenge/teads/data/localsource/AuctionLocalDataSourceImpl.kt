package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider
import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.data.mapper.DataReservePriceMapper
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal
import javax.inject.Inject

class AuctionLocalDataSourceImpl @Inject constructor(
    private val auctionDatabase: AuctionDatabaseProvider,
    private val dataBidMapper: DataBidMapper,
    private val dataReservePriceMapper: DataReservePriceMapper
) : AuctionDataSource {
    override suspend fun getReservePrice(): BigDecimal {
        return auctionDatabase.getReservePriceDao().get().value
    }

    override suspend fun getBidders(): List<DataBid> = auctionDatabase.getDataBidDao().getAll()

    override suspend fun addBid(bid: Bid) {
        with(auctionDatabase.getDataBidDao()) {
            val dataBid = getById(bid.bidderId)

            if (dataBid != null) {
                dataBid.bids.add(bid.value)
                update(dataBid)
            } else {
                insert(dataBidMapper.map(bid))
            }
        }
    }

    override suspend fun addReservePrice(value: BigDecimal) {
        with(auctionDatabase) {
            getReservePriceDao().nukeTable()
            getDataBidDao().nukeTable()

            getReservePriceDao().insert(dataReservePriceMapper.map(value))
        }
    }
}