package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import com.anatideo.challenge.teads.domain.model.AuctionResult
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class GetAuctionResultUseCase(
    private val auctionRepository: AuctionRepository = AuctionRepositoryImpl()
) {
    fun getAuctionResult(): AuctionResult? {
        val reservePrice = auctionRepository.getReservePrice()
        val bidders = auctionRepository.getBidders()

        if (bidders.isEmpty()){
            return null
        }

        bidders.map {
            it to it.bids.maxOrZero()
        }.sortedBy {
            it.second
        }.let { sortedList ->
            val winner = sortedList.getWinner()
            val winnerHighestBid = sortedList.getWinnerHighestBid()
            val winningPrice = sortedList
                .getWinningPrice()
                ?.takeIf { it > reservePrice }
                ?: reservePrice

            return if (winnerHighestBid >= reservePrice) {
                AuctionResult(bidder = winner, winningPrice = winningPrice)
            } else {
                null
            }
        }
    }

    private fun List<BigDecimal>.maxOrZero(): BigDecimal = maxOrNull() ?: BigDecimal.ZERO

    private fun List<Pair<Bidder, BigDecimal>>.getWinner(): Bidder = this.last().first

    private fun List<Pair<Bidder, BigDecimal>>.getWinnerHighestBid(): BigDecimal = this.last().second

    private fun List<Pair<Bidder, BigDecimal>>.getWinningPrice(): BigDecimal? {
        val indexOfNonWinnerHighestBid = this.indexOf(this.last()).dec()
        return this.getOrNull(indexOfNonWinnerHighestBid)?.second
    }
}