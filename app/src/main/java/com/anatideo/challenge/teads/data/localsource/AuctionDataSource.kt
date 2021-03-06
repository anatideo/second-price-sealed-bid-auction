package com.anatideo.challenge.teads.data.localsource

import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bid
import java.math.BigDecimal

interface AuctionDataSource {
    suspend fun getReservePrice(): BigDecimal
    suspend fun getBidders(): List<DataBid>
    suspend fun addBid(bid: Bid)
    suspend fun addReservePrice(value: BigDecimal)
}