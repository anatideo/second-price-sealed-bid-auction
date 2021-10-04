package com.anatideo.challenge.teads.presentation.model

import com.anatideo.challenge.teads.domain.model.AuctionResult

sealed class AuctionViewState {
    data class HandleLoading(val show: Boolean): AuctionViewState()
    data class ShowWinner(val auctionResult: AuctionResult): AuctionViewState()
    object BidAdded : AuctionViewState()
    object MissingReservePrice : AuctionViewState()
    object MissingId : AuctionViewState()
    object MissingBidValue : AuctionViewState()
    object AuctionStarted : AuctionViewState()
    object ShowThereIsNoWinner : AuctionViewState()
    object ShowInsufficientHighestBid : AuctionViewState()
    object ShowUnknownError : AuctionViewState()
}