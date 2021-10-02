package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.model.Buyer

interface AuctionRepository {
    fun getReservePrice(): Double
    fun addBuyer(buyer: Buyer): Boolean
    fun getBuyers(): List<Buyer>
}