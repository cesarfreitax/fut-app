package com.fut.core.utils

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.fut.R
import com.google.android.material.snackbar.Snackbar

enum class SnackBarType {
    Success, Error, Warning
}

private fun getColorWithType(type: SnackBarType): Int {
    return when (type) {
        SnackBarType.Success -> R.color.green_success
        SnackBarType.Error -> R.color.red_error
        SnackBarType.Warning -> R.color.yellow_warning
    }
}

fun Fragment.showSnackWith(message: String, type: SnackBarType) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}

fun Fragment.showSnackWith(message: Int, type: SnackBarType) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}

fun Activity.showSnackWith(message: String, type: SnackBarType) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}

fun Activity.showSnackWith(message: Int, type: SnackBarType) {
    Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}

fun View.showSnackWith(message: String, type: SnackBarType) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}

fun View.showSnackWith(message: Int, type: SnackBarType) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(resources.getColor(getColorWithType(type), null))
        .show()
}