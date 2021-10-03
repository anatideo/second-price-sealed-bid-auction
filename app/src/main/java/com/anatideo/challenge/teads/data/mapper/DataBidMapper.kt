package com.anatideo.challenge.teads.data.mapper

import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.domain.model.Bid
import javax.inject.Inject

class DataBidMapper @Inject constructor() {
    fun map(source: Bid): DataBid {
        return DataBid(
           bidderId = source.bidderId,
           name = source.name,
           bids = mutableListOf(source.value)
        )
    }
}