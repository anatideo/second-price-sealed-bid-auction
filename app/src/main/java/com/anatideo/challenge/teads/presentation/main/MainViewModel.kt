package com.anatideo.challenge.teads.presentation.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anatideo.challenge.teads.domain.AddBidUseCase
import com.anatideo.challenge.teads.domain.AddReservePriceUseCase
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.errors.DuplicatedHighestBidError
import com.anatideo.challenge.teads.domain.errors.InsufficientHighestBidError
import com.anatideo.challenge.teads.domain.model.Bid
import com.anatideo.challenge.teads.presentation.base.extensions.isNumeric
import com.anatideo.challenge.teads.presentation.model.AuctionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuctionResultUseCase: GetAuctionResultUseCase,
    private val addReservePriceUseCase: AddReservePriceUseCase,
    private val addBidUseCase: AddBidUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<AuctionViewState>()
    val viewState: LiveData<AuctionViewState> get() = _viewState

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
        _viewState.value = AuctionViewState.Terminate
    }

    fun onAuctionResult() {
        viewModelScope.launch(Dispatchers.Default) {
            runCatching {
                val result  = getAuctionResultUseCase()
                _viewState.postValue(AuctionViewState.ShowWinner(result))
            }.onFailure {
                val state = when (it) {
                    is InsufficientHighestBidError -> AuctionViewState.ShowInsufficientHighestBid
                    is DuplicatedHighestBidError -> AuctionViewState.ShowNoSingularWinner
                    else -> AuctionViewState.ShowUnknownError
                }

                _viewState.postValue(state)
            }
        }
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