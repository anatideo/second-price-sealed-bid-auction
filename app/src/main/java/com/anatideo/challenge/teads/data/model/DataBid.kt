package com.anatideo.challenge.teads.data.model

import java.math.BigDecimal

data class DataBid(
    val bidderId: Long,
    val name: String?,
    val bids: MutableList<BigDecimal>
)