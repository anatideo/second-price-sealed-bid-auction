package com.anatideo.challenge.teads.fake

import com.anatideo.challenge.teads.domain.model.Bidder
import java.math.BigDecimal

class FakeAuctionResultData(
    var reservePrice: BigDecimal = BigDecimal.ZERO,
    var bidders: List<Bidder> = mutableListOf()
)