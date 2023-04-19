package com.fut.features.matches.data.models.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DateResponse(
    val date: String,
    var isSelected: Boolean? = false
) : Parcelable