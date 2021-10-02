package com.anatideo.challenge.teads.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.anatideo.challenge.teads.data.database.model.DataBid

@Dao
interface DataBidDao {
    @Insert
    fun insert(dataBid: DataBid)

    @Update
    fun update(dataBid: DataBid)

    @Query("SELECT * FROM DataBid")
    fun getAll(): List<DataBid>

    @Query("SELECT * FROM DataBid WHERE bidderId=:id")
    fun getById(id: Long): DataBid?
}