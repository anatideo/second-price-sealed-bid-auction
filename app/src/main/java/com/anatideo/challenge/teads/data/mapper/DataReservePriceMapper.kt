package com.anatideo.challenge.teads.data.mapper

import com.anatideo.challenge.teads.data.database.model.DataReservePrice
import java.math.BigDecimal
import javax.inject.Inject

class DataReservePriceMapper @Inject constructor() {
    fun map(source: BigDecimal): DataReservePrice {
        return DataReservePrice(
           value = source
        )
    }
}