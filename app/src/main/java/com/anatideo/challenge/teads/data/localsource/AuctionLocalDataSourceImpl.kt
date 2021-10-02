package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.data.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bidder
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal

class AuctionLocalDataSourceImpl(
    private val dataBidMapper: DataBidMapper = DataBidMapper(),
    private val bidderMapper: BidderMapper = BidderMapper()
): AuctionLocalDataSource {

    private val dataBids = mutableListOf<DataBid>()

    override suspend fun getReservePrice(): BigDecimal = BigDecimal(1050.00)

    override suspend fun getBidders(): List<Bidder> = bidderMapper.map(dataBids)

    override suspend fun addBid(bid: Bid) {
        dataBids
            .find { it.bidderId == bid.bidderId }
            ?.let { it.bids.add(bid.bid) }
            ?: dataBidMapper.map(bid).also { dataBids.add(it) }
    }
}