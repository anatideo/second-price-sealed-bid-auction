package com.anatideo.challenge.teads.data.mapper

import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bidder

class BidderMapper {
    fun map(source: List<DataBid>): List<Bidder> {
        return source.map {
            Bidder(
                id = it.bidderId,
                name = it.name,
                bids = it.bids
            )
        }
    }
}