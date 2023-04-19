package com.fut.features.userpref.data.models.response

import android.os.Parcelable
import com.fut.features.search.data.models.response.Paging
import kotlinx.parcelize.Parcelize

@Parcelize
class CountriesResponse(
    val get: String,
    val parameters: ArrayList<String>,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<CountryInfo>
) : Parcelable

@Parcelize
class CountryInfo(
    var name: String? = "",
    val code: String? = "",
    val flag: String? = ""
) : Parcelable
