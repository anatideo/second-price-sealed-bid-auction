package com.anatideo.challenge.teads.domain

import com.anatideo.challenge.teads.data.AuctionRepositoryImpl
import java.math.BigDecimal

class AddReservePriceUseCase(
    private val auctionRepository: AuctionRepository = AuctionRepositoryImpl()
) {
    suspend operator fun invoke(value: BigDecimal) = auctionRepository.addReservePrice(value)
}