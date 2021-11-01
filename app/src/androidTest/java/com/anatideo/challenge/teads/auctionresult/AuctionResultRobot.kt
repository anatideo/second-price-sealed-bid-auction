package com.anatideo.challenge.teads.auctionresult

import com.anatideo.challenge.teads.R
import com.anatideo.challenge.teads.utils.BaseTestRobot

fun auctionResult(func: AuctionResultRobot.() -> Unit) = AuctionResultRobot().apply { func() }

class AuctionResultRobot : BaseTestRobot() {
    fun matchResultText(text: String) = matchText(textView(R.id.result), text)
}