package com.anatideo.challenge.teads.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.math.BigDecimal

@Dao
interface ReservePriceDao {
    @Insert
    fun insert(reservePrice: BigDecimal)

    @Query("SELECT * FROM DataBid LIMIT 1")
    fun get(): BigDecimal
}