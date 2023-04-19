package com.fut.features.userpref.data.models.response

import android.os.Parcelable
import com.fut.features.matches.data.models.response.Fixture
import com.fut.features.search.data.models.response.Country
import com.fut.features.search.data.models.response.Paging
import kotlinx.parcelize.Parcelize

@Parcelize
class LeaguesResponse(
    val get: String,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<LeagueInfo>
) : Parcelable

@Parcelize
class LeagueInfo(
    val league: League,
    val country: com.fut.features.userpref.data.models.response.Country,
    val seasons: ArrayList<Season>
) : Parcelable

@Parcelize
class League(
    val id: Int,
    val name: String,
    val type: String,
    val logo: String
) : Parcelable

@Parcelize
class Country(
    val name: String,
    val code: String,
    val flag: String
) : Parcelable

@Parcelize
class Season(
    val year: Int,
    val start: String,
    val end: String,
    val current: Boolean,
    val coverage: Coverage,
    val standings: Boolean,
    val players: Boolean,
    val top_scorers: Boolean,
    val top_assists: Boolean,
    val top_cards: Boolean,
    val injuries: Boolean,
    val predictions: Boolean,
    val odds: Boolean
) : Parcelable

@Parcelize
class Coverage(
        val fixtures: Fixtures
) : Parcelable

@Parcelize
class Fixtures(
    val events: Boolean,
    val lineups: Boolean,
    val statistics_fixtures: Boolean,
    val statistics_players: Boolean
) : Parcelable
