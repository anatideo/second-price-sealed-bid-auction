package com.anatideo.challenge.teads.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anatideo.challenge.teads.data.database.model.DataReservePrice

@Dao
interface ReservePriceDao {
    @Insert
    fun insert(dataReservePrice: DataReservePrice)

    @Query("SELECT * FROM DataReservePrice LIMIT 1")
    fun get(): DataReservePrice

    @Query("DELETE FROM DataReservePrice")
    fun nukeTable()
}