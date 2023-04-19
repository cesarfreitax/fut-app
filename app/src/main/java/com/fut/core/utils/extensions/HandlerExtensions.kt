package com.fut.core.utils.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fut.core.utils.SnackBarType
import com.fut.core.utils.showSnackWith

fun Fragment.exceptionHandler(callback: () -> Unit) {
    try {
        callback()
    } catch (e: Exception) {
        this.showSnackWith(e.message.orEmpty(), SnackBarType.Error)
    }
}

fun AppCompatActivity.exceptionHandler(callback: () -> Unit) {
    try {
        callback()
    } catch (e: Exception) {
        this.showSnackWith(e.message.orEmpty(), SnackBarType.Error)
    }
}