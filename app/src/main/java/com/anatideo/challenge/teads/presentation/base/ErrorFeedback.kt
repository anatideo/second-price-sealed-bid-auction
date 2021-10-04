package com.anatideo.challenge.teads.presentation.base

import android.content.Context
import android.widget.Toast

object ErrorFeedback {
    fun show(message: String, context: Context?) {
        context?.let {
            Toast.makeText(
                it,
                message,
                Toast.LENGTH_SHORT
            )
        }
    }
}