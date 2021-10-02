package com.anatideo.challenge.teads.domain.model

@Deprecated("make use of Bidder class")
data class Buyer(
    val name: String?,
    val bids: List<Double>
)