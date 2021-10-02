package com.anatideo.challenge.teads.presentation.application

import android.app.Application
import com.anatideo.challenge.teads.data.database.AuctionDatabaseProvider

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AuctionDatabaseProvider.init(this)
    }
}