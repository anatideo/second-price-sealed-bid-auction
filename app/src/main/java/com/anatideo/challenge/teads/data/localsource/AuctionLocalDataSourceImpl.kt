package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.domain.model.Buyer

object AuctionLocalDataSourceImpl : AuctionLocalDataSource {

    private val buyers = mutableListOf<Buyer>()

    override fun getReservePrice(): Double = 1050.00

    override fun addBuyer(buyer: Buyer): Boolean = buyers.add(buyer)

    override fun getBuyers(): List<Buyer> = buyers
}