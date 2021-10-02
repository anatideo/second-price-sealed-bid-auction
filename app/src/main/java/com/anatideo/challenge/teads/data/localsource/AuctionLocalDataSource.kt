package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.domain.model.Buyer

interface AuctionLocalDataSource {
    fun getReservePrice(): Double
    fun addBuyer(buyer: Buyer): Boolean
    fun getBuyers(): List<Buyer>
}