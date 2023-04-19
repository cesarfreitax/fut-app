package com.fut.features.search.data.models.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TeamsResponse(
    val get: String,
    val parameters: Country,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<TeamInfo>
) : Parcelable



@Parcelize
class Country(
    val country: String
) : Parcelable

@Parcelize
class Paging(
    val current: Int,
    val total: Int
) : Parcelable

@Parcelize
class TeamInfo(
    val team: Team,
    val venue: Venue
) : Parcelable


@Parcelize
class Team(
    val id: Int,
    val name: String,
    val code: String,
    val country: String,
    val founded: Int,
    val national: Boolean,
    val logo: String
) : Parcelable

@Parcelize
class Venue(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val capacity: Int,
    val surface: String,
    val image: String
) : Parcelable
