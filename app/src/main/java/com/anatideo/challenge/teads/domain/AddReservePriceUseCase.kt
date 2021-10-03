package com.anatideo.challenge.teads.domain

import java.math.BigDecimal
import javax.inject.Inject

class AddReservePriceUseCase @Inject constructor(
    private val auctionRepository: AuctionRepository
) {
    suspend operator fun invoke(value: BigDecimal) = auctionRepository.addReservePrice(value)
}