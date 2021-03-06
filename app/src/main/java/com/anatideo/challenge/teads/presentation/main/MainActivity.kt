package com.anatideo.challenge.teads.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.presentation.startauction.StartAuctionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StartAuctionFragment.newInstance())
                .commitNow()
        }
    }
}