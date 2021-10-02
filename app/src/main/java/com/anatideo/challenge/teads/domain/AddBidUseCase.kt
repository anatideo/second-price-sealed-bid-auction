package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import com.anatideo.challenge.teads.domain.model.Bid

class AddBidUseCase(
    private val auctionRepository: AuctionRepository = AuctionRepositoryImpl()
) {
    suspend operator fun invoke(bid: Bid) = auctionRepository.addBid(bid)
}