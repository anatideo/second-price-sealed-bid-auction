package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.domain.model.Bidder
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal

interface AuctionLocalDataSource {
    fun getReservePrice(): BigDecimal
    fun getBidders(): List<Bidder>
    fun addBid(bid: Bid): Boolean
}