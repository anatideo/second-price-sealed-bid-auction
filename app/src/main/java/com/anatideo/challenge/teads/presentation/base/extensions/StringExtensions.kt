package com.anatideo.challenge.teads.presentation.base.extensions

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())