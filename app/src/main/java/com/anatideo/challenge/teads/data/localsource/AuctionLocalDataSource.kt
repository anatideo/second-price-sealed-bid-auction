package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.domain.model.Bidder
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal

interface AuctionLocalDataSource {
    suspend fun getReservePrice(): BigDecimal
    suspend fun getBidders(): List<Bidder>
    suspend fun addBid(bid: Bid)
}