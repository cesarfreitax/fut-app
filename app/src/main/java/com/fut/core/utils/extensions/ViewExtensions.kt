package com.fut.utils.extensions

import android.view.View
import androidx.core.view.isVisible

fun View.toggleVisibility(enable: Boolean) {
    this.visibility = if (enable) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.toggleVisibility() {
    this.visibility = if (this.isVisible) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}