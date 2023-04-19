package com.fut.features.matches.data.models.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class LeagueSections(
    val leagues: ArrayList<LeagueFixtures>? = arrayListOf()
) : Parcelable

@Parcelize
class LeagueFixtures(
    val name: String,
    val games: ArrayList<FixtureInfo>,
) : Parcelable
