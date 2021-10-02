package com.anatideo.challenge.teads.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anatideo.challenge.teads.domain.GetAuctionResultUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAuctionResultUseCase: GetAuctionResultUseCase = GetAuctionResultUseCase()
) : ViewModel() {

    fun showResult() {
        viewModelScope.launch {
            val result  = getAuctionResultUseCase.getAuctionResult()
            println("null expected: $result")
        }
    }
}