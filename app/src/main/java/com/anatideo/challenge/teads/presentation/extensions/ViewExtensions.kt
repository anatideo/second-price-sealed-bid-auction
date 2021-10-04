package com.anatideo.challenge.teads.presentation.extensions

import android.view.View
import android.view.animation.AnimationUtils
import com.anatideo.challenge.teads.R

fun View.shake() {
    val shakeAnimation = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    startAnimation(shakeAnimation)
}