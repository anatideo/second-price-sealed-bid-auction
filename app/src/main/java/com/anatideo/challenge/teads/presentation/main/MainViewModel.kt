package com.anatideo.challenge.teads.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anatideo.challenge.teads.domain.AddBidUseCase
import com.anatideo.challenge.teads.domain.AddReservePriceUseCase
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import com.anatideo.challenge.teads.domain.model.Bid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel(
    private val getAuctionResultUseCase: GetAuctionResultUseCase = GetAuctionResultUseCase(),
    private val addReservePriceUseCase: AddReservePriceUseCase = AddReservePriceUseCase(),
    private val addBidUseCase: AddBidUseCase = AddBidUseCase()
) : ViewModel() {

    init {
        createFakeData()
    }

    private fun createFakeData() {
        viewModelScope.launch(Dispatchers.Default) {
            addReservePriceUseCase(BigDecimal.valueOf(1000.0))
            getFakeBids().forEach { addBidUseCase(it) }

            val result  = getAuctionResultUseCase()
            println("null is not expected: $result")
        }
    }

    private fun getFakeBids(): List<Bid> {
        return listOf(
            Bid(
                1L,
                name = null,
                value = BigDecimal.valueOf(100.0)
            ),
            Bid(
                1L,
                name = null,
                value = BigDecimal.valueOf(300.0)
            ),
            Bid(
                1L,
                name = null,
                value = BigDecimal.valueOf(300.0)
            ),
            Bid(
                2L,
                name = "Gotham",
                value = BigDecimal.valueOf(3000.0)
            ),
            Bid(
                1L,
                name = null,
                value = BigDecimal.valueOf(200.0)
            ),
            Bid(
                3L,
                name = "Cardy G.",
                value = BigDecimal.valueOf(1200.0)
            ),
            Bid(
                4L,
                name = "Saulo B.",
                value = BigDecimal.valueOf(500.50)
            )
        )
    }
}