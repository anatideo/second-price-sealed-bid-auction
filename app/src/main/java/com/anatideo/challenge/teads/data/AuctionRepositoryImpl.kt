package com.anatideo.challenge.teads.data

import com.anatideo.challenge.teads.data.localsource.AuctionDataSource
import com.anatideo.challenge.teads.data.mapper.BidderMapper
import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal
import javax.inject.Inject

class AuctionRepositoryImpl @Inject constructor(
    private val auctionLocalDataSource: AuctionDataSource,
    private val bidderMapper: BidderMapper
) : AuctionRepository {
    override suspend fun getReservePrice(): BigDecimal = auctionLocalDataSource.getReservePrice()

    override suspend fun getBidders(): List<Bidder> {
        return auctionLocalDataSource.getBidders().map { bidderMapper.map(it) }
    }

    override suspend fun addBid(bid: Bid) = auctionLocalDataSource.addBid(bid)

    override suspend fun addReservePrice(reservePrice: BigDecimal) {
        auctionLocalDataSource.addReservePrice(reservePrice)
    }
}