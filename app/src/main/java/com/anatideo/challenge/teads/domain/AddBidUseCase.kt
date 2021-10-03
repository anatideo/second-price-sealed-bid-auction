package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.model.Bid
import javax.inject.Inject

class AddBidUseCase @Inject constructor(
    private val auctionRepository: AuctionRepository
) {
    suspend operator fun invoke(bid: Bid) = auctionRepository.addBid(bid)
}