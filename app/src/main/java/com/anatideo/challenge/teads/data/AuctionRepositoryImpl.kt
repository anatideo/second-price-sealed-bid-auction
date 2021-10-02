package com.anatideo.challenge.teads.data

import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSource
import com.anatideo.challenge.teads.data.localsource.AuctionLocalDataSourceImpl
import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Buyer

class AuctionRepositoryImpl(
    private val auctionLocalDataSource: AuctionLocalDataSource = AuctionLocalDataSourceImpl
) : AuctionRepository {
    override fun getReservePrice(): Double {
        return auctionLocalDataSource.getReservePrice()
    }

    override fun addBuyer(buyer: Buyer): Boolean {
        return auctionLocalDataSource.addBuyer(buyer)
    }

    override fun getBuyers(): List<Buyer> {
        return auctionLocalDataSource.getBuyers()
    }
}