package com.anatideo.challenge.teads.data.database

import android.content.Context
import androidx.room.Room
import com.anatideo.challenge.teads.data.database.dao.DataBidDao
import com.anatideo.challenge.teads.data.database.dao.ReservePriceDao

private const val DATABASE_NAME: String = "AUCTION_DATABASE"

object AuctionDatabaseProvider {
    private var auctionDatabase: AuctionDatabase? = null

    fun init(context: Context) {
        auctionDatabase = Room.databaseBuilder(
            context, AuctionDatabase::class.java, DATABASE_NAME
        ).build()
    }

    fun getDataBidDao(): DataBidDao? = auctionDatabase?.dataBidDao()

    fun getReservePriceDao(): ReservePriceDao? = auctionDatabase?.reservePriceDao()
}