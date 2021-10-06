package com.anatideo.challenge.teads.fake

import com.anatideo.challenge.teads.domain.AuctionRepository
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class FakeAuctionRepositoryTestImpl(
    private val fakeAuctionResultData: FakeAuctionResultData
): AuctionRepository {

    override suspend fun getReservePrice(): BigDecimal = fakeAuctionResultData.reservePrice

    override suspend fun getBidders(): List<Bidder> = fakeAuctionResultData.bidders

    override suspend fun addBid(bid: Bid) = Unit

    override suspend fun addReservePrice(reservePrice: BigDecimal) = Unit
}