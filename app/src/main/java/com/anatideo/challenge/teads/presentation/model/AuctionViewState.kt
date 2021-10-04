package com.anatideo.challenge.teads.presentation.model

import com.anatideo.challenge.teads.domain.model.AuctionResult

sealed class AuctionViewState {
    data class HandleLoading(val show: Boolean): AuctionViewState()
    data class ShowWinner(val auctionResult: AuctionResult): AuctionViewState()
    data class BidAdded(val name: String?) : AuctionViewState()
    object MissingReservePrice : AuctionViewState()
    object AuctionStarted : AuctionViewState()
    object ShowThereIsNoWinner : AuctionViewState()
    object ShowInsufficientHighestBid : AuctionViewState()
    object ShowUnknownError : AuctionViewState()
}