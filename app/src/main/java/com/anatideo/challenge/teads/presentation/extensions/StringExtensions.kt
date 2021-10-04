package com.anatideo.challenge.teads.presentation.extensions

fun String.isNumeric(): Boolean = this.matches("-?\\d+(\\.\\d+)?".toRegex())