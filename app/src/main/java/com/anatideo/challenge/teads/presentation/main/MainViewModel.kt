package com.anatideo.challenge.teads.presentation.main

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anatideo.challenge.teads.domain.AddBidUseCase
import com.anatideo.challenge.teads.domain.AddReservePriceUseCase
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.errors.EmptyBidderListError
import com.anatideo.challenge.teads.domain.errors.InsufficientHighestBidError
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.presentation.extensions.isNumeric
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val getAuctionResultUseCase: GetAuctionResultUseCase,
    private val addReservePriceUseCase: AddReservePriceUseCase,
    private val addBidUseCase: AddBidUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<AuctionViewState>()
    val viewState: LiveData<AuctionViewState> get() = _viewState

    private fun createFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                val result  = getAuctionResultUseCase()
                println(result)
            }.onFailure {
                when (it) {
                    is EmptyBidderListError -> println("There is no bidder!")
                    is InsufficientHighestBidError -> println("Highest bid is minor than the reserve price")
                    else -> println("unknown error has occurred: ${it.message}")
                }
            }
        }
    }

    fun onStartingAuction(reservePrice: String) {
        if (reservePrice.isBlank().not() && reservePrice.isNumeric()) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    addReservePriceUseCase(reservePrice.toBigDecimal())
                    _viewState.postValue(AuctionViewState.AuctionStarted)
                }.onFailure {
                    _viewState.postValue(AuctionViewState.ShowUnknownError)
                }
            }
        } else {
            _viewState.value = AuctionViewState.MissingReservePrice
        }
    }

    fun onAddingBid(id: String, name: String, value: String) {
        var fail = false

        if (id.isBlank() || id.isNumeric().not()) {
            _viewState.value = AuctionViewState.MissingId
            fail = true
        }

        if (value.isBlank() || value.isNumeric().not()) {
            _viewState.value = AuctionViewState.MissingBidValue
            fail = true
        }

        if (fail.not()) {
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    val bid = Triple(id, name, value).toBid()
                    addBidUseCase(bid)
                    _viewState.postValue(AuctionViewState.BidAdded)
                }.onFailure {
                    _viewState.postValue(AuctionViewState.ShowUnknownError)
                }
            }
        }
    }

    fun onAddNewBid() {
        _viewState.value = AuctionViewState.AddNewBid
    }

    fun onTerminate() {

    }

    @VisibleForTesting
    fun Triple<String, String, String>.toBid(): Bid {
        return Bid(
            bidderId = this.first.toLong(),
            name = this.second,
            value = this.third.toBigDecimal()
        )
    }
}