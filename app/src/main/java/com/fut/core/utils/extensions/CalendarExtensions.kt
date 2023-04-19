package com.fut.core.utils.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Int.lessThanTenHandler() : String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

fun dateNowInUsFormat(): String {
    val dateNow = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return dateNow.format(formatter)
}

fun dateNowInBrFormat(): String {
    val dateNow = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return dateNow.format(formatter)
}

fun currentDayInFormatDdMm() : String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat("dd/MM")
    return dateFormat.format(currentDate)
}

fun String.convertToDateInUsFormat(): String {
    val dateNow = LocalDate.now()
    val year = dateNow.year
    val date = "$this/$year"
    val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return localDate.format(formatter)
}