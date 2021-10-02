package com.anatideo.challenge.teads.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anatideo.challenge.teads.data.database.dao.DataBidDao
import com.anatideo.challenge.teads.data.database.dao.ReservePriceDao

@Database(entities = [ReservePriceDao::class, DataBidDao::class], version = 1)
abstract class AuctionDatabase : RoomDatabase() {
    abstract fun dataBidDao(): DataBidDao
    abstract fun reservePriceDao(): ReservePriceDao
}