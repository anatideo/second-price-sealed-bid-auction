package com.anatideo.challenge.teads.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<T>.observeOn(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
): LiveData<T> {
    observe(owner, { result -> result?.let { observer(result) } })
    return this
}