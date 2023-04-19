package com.fut.features.matches.data.models.response

import android.os.Parcelable
import com.fut.features.search.data.models.response.Paging
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class FixturesResponse(
    val get: String,
    val parameters: Parameters,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<FixtureInfo>
) : Parcelable


@Parcelize
class Parameters(
    val timezone: String,
    val date: String
) : Parcelable

@Parcelize
class FixtureInfo(
    val fixture: Fixture,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
) : Parcelable

@Parcelize
class Fixture(
    val id: Int? = null,
    val referee: String? = null,
    val timezone: String? = null,
    val date: Date? = null,
    val timestamp: Int? = null,
    val periods: Periods? = Periods(),
    val venue: Venue? = Venue(),
    val status: Status? = Status()
) : Parcelable

@Parcelize
class Periods(
    val first: Int? = null,
    val second: Int? = null
) : Parcelable

@Parcelize
class Venue(
    val id: Int? = null,
    val name: String? = null,
    val city: String? = null
) : Parcelable

@Parcelize
class Status(
    val long: String? = null,
    val short: String? = null,
    val elapsed: Int? = null
) : Parcelable

@Parcelize
class League(
    val id: Int? = null,
    val name: String? = null,
    val country: String? = null,
    val logo: String? = null,
    val flag: String? = null,
    val season: Int? = null,
    val round: String? = null
) : Parcelable

@Parcelize
class Teams(
    val home: Team? = Team(),
    val away: Team? = Team()
) : Parcelable

@Parcelize
class Team(
    val id: Int? = null,
    val name: String? = null,
    val logo: String? = null,
    val winner: Boolean? = null
) : Parcelable

@Parcelize
class Goals(
    val home: Int? = null,
    val away: Int? = null
) : Parcelable

@Parcelize
class Score(
    val halftime: Goals? = Goals(),
    val fulltime: Goals? = Goals(),
    val extratime: Goals? = Goals(),
    val penalty: Goals? = Goals()
) : Parcelable
