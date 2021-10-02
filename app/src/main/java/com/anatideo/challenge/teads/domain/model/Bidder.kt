package com.anatideo.challenge.teads.domain.model

import java.math.BigDecimal

class Bidder(
    val id: Long,
    val name: String?,
    val bids: List<BigDecimal>
)