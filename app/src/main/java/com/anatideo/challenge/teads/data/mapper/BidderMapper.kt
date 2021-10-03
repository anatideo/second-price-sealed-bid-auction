package com.anatideo.challenge.teads.data.mapper

import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bidder

class BidderMapper {
    fun map(source: DataBid): Bidder {
        return Bidder(
            id = source.bidderId,
            name = source.name,
            bids = source.bids
        )
    }
}