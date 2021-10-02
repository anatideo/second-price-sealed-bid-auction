package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import com.anatideo.challenge.teads.domain.model.AuctionResult

class WinnerBuyerUseCase(
    private val auctionRepository: AuctionRepository = AuctionRepositoryImpl()
) {
    fun getAuctionResult(): AuctionResult? {
        val reservePrice = auctionRepository.getReservePrice()
        val buyers = auctionRepository.getBuyers()

        val result = buyers.map {
            it to it.bids.maxOrNull()
        }.sortedBy {
            it.second
        }.let { sortedList ->
            val indexOfLast = sortedList.indexOf(sortedList.last())
            val winner = sortedList[indexOfLast].first
            val winningPrice = sortedList
                .getOrNull(indexOfLast.dec())?.second
                ?.let { if (it > reservePrice) it else reservePrice }
                ?: reservePrice

            Triple(winner, winner.bids.last(), winningPrice)
        }

        return result
            .takeIf { it.second >= reservePrice}
            ?.let { AuctionResult(buyer = it.first, winningPrice = it.third) }
    }
}