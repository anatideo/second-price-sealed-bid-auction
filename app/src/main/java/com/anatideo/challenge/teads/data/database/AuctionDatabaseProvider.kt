package com.anatideo.challenge.teads.data.database

import android.content.Context
import androidx.room.Room
import com.anatideo.challenge.teads.data.database.dao.DataBidDao
import com.anatideo.challenge.teads.data.database.dao.ReservePriceDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val DATABASE_NAME: String = "auction.database"

class AuctionDatabaseProvider @Inject constructor(
    @ApplicationContext context: Context
) {
    private val auctionDatabase: AuctionDatabase by lazy {
        Room.databaseBuilder(context, AuctionDatabase::class.java, DATABASE_NAME).build()
    }

    fun getDataBidDao(): DataBidDao = auctionDatabase.dataBidDao()

    fun getReservePriceDao(): ReservePriceDao = auctionDatabase.reservePriceDao()
}