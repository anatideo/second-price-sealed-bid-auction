package com.anatideo.challenge.teads.data

import com.anatideo.challenge.teads.data.localsource.AuctionDataSource
import com.anatideo.challenge.teads.data.localsource.AuctionMemoryDataSourceImpl
import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class AuctionRepositoryImpl(
    private val auctionLocalDataSource: AuctionDataSource = AuctionMemoryDataSourceImpl()
) : AuctionRepository {
    override suspend fun getReservePrice(): BigDecimal = auctionLocalDataSource.getReservePrice()

    override suspend fun getBidders(): List<Bidder> = auctionLocalDataSource.getBidders()

    override suspend fun addBid(bid: Bid) = auctionLocalDataSource.addBid(bid)

    override suspend fun addReservePrice(reservePrice: BigDecimal) {
        auctionLocalDataSource.addReservePrice(reservePrice)
    }
}