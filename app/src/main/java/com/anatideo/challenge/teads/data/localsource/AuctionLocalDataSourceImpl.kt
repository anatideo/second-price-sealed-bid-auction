package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider
import com.anatideo.challenge.teads.data.database.model.DataReservePrice
import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class AuctionLocalDataSourceImpl(
    private val auctionDatabase: AuctionDatabaseProvider = AuctionDatabaseProvider,
    private val dataBidMapper: DataBidMapper = DataBidMapper(),
    private val bidderMapper: BidderMapper = BidderMapper()
) : AuctionDataSource {
    override suspend fun getReservePrice(): BigDecimal {
        return auctionDatabase.getReservePriceDao()!!.get().value
    }

    override suspend fun getBidders(): List<Bidder> {
        val dataBids = auctionDatabase.getDataBidDao()!!.getAll()
        return bidderMapper.map(dataBids)
    }

    override suspend fun addBid(bid: Bid) {
        with(auctionDatabase.getDataBidDao()!!) {
            val dataBid = getById(bid.bidderId)

            if (dataBid != null) {
                dataBid.bids.toMutableList().add(bid.value)
                update(dataBid)
            } else {
                insert(dataBidMapper.map(bid))
            }
        }
    }

    override suspend fun addReservePrice(value: BigDecimal) {
        auctionDatabase.getReservePriceDao()!!.insert(DataReservePrice(value = value))
    }
}