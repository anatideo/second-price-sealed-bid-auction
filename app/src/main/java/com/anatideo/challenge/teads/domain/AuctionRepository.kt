package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

interface AuctionRepository {
    suspend fun getReservePrice(): BigDecimal
    suspend fun getBidders(): List<Bidder>
    suspend fun addBid(bid: Bid)
    suspend fun addReservePrice(reservePrice: BigDecimal)
}