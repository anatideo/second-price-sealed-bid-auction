package com.anatideo.challenge.teads.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anatideo.challenge.teads.data.database.dao.DataBidDao
import com.anatideo.challenge.teads.data.database.dao.ReservePriceDao
import com.anatideo.challenge.teads.data.database.model.DataBid
import com.anatideo.challenge.teads.data.database.model.DataReservePrice
import com.anatideo.challenge.teads.data.database.utils.Converters

@Database(entities = [DataBid::class, DataReservePrice::class], version = 1)
@TypeConverters(Converters::class)
abstract class AuctionDatabase : RoomDatabase() {
    abstract fun dataBidDao(): DataBidDao
    abstract fun reservePriceDao(): ReservePriceDao
}