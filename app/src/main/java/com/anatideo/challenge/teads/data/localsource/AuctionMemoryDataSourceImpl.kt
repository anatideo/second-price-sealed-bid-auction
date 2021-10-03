package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.mapper.DataBidMapper
import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal

class AuctionMemoryDataSourceImpl(
    private val dataBidMapper: DataBidMapper = DataBidMapper()
): AuctionDataSource {

    private val dataBids = mutableListOf<DataBid>()
    private var reservePrice = BigDecimal.ZERO

    override suspend fun getReservePrice(): BigDecimal = reservePrice

    override suspend fun getBidders(): List<DataBid> = dataBids

    override suspend fun addBid(bid: Bid) {
        dataBids
            .find { it.bidderId == bid.bidderId }
            ?.let { it.bids.toMutableList().add(bid.value) }
            ?: dataBidMapper.map(bid).also { dataBids.add(it) }
    }

    override suspend fun addReservePrice(value: BigDecimal) {
        reservePrice = value
    }
}