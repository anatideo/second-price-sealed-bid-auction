package com.anatideo.challenge.teads.data

import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSource
import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSourceImpl
import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class AuctionRepositoryImpl(
    private val auctionLocalDataSource: AuctionLocalDataSource = AuctionLocalDataSourceImpl()
) : AuctionRepository {
    override fun getReservePrice(): BigDecimal {
        return auctionLocalDataSource.getReservePrice()
    }

    override fun getBidders(): List<Bidder> {
        return auctionLocalDataSource.getBidders()
    }

    override fun addBid(bid: Bid): Boolean {
        return auctionLocalDataSource.addBid(bid)
    }
}