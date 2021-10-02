package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

interface AuctionRepository {
    fun getReservePrice(): BigDecimal
    fun getBidders(): List<Bidder>
    fun addBid(bid: Bid): Boolean
}