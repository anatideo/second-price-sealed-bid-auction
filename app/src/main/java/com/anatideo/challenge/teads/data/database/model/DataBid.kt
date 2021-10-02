package com.anatideo.challenge.teads.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class DataBid(
    @PrimaryKey
    val bidderId: Long,
    @ColumnInfo
    val name: String?,
    @ColumnInfo
    val bids: MutableList<BigDecimal>
)