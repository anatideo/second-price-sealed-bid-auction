package com.anatideo.challenge.teads.domain.model

import java.math.BigDecimal

class AuctionResult(
    val bidder: Bidder,
    val winningPrice: BigDecimal
)