package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.domain.errors.DuplicatedHighestBidError
import com.anatideo.challenge.teads.domain.errors.EmptyBidderListError
import com.anatideo.challenge.teads.domain.errors.InsufficientHighestBidError
import com.anatideo.challenge.teads.domain.model.AuctionResult
import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal
import javax.inject.Inject

class GetAuctionResultUseCase @Inject constructor(
    private val auctionRepository: AuctionRepository
) {
    suspend operator fun invoke(): AuctionResult {
        val reservePrice = auctionRepository.getReservePrice()
        val bidders = auctionRepository.getBidders()

        if (bidders.isEmpty()){
            throw EmptyBidderListError()
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
                if (sortedList.isDuplicatedHighestBid()) {
                    throw DuplicatedHighestBidError()
                } else {
                    AuctionResult(bidder = winner, winningPrice = winningPrice)
                }
            } else {
                throw InsufficientHighestBidError()
            }
        }
    }

    private fun List<BigDecimal>.maxOrZero(): BigDecimal = maxOrNull() ?: BigDecimal.ZERO

    private fun List<Pair<Bidder, BigDecimal>>.getWinner(): Bidder = last().first

    private fun List<Pair<Bidder, BigDecimal>>.getWinnerHighestBid(): BigDecimal = last().second

    private fun List<Pair<Bidder, BigDecimal>>.getWinningPrice(): BigDecimal? {
        val indexOfNonWinnerHighestBid = this.indexOf(this.last()).dec()
        return getOrNull(indexOfNonWinnerHighestBid)?.second
    }

    private fun List<Pair<Bidder, BigDecimal>>.isDuplicatedHighestBid(): Boolean {
        val highestBid = last().second
        val indexOfSecondHighestBid = this.indexOf(this.last()).dec()
        val secondHighestBid = getOrNull(indexOfSecondHighestBid)?.second

        return highestBid == secondHighestBid
    }
}