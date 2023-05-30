package com.fut.features.matches.data.models.response

import java.io.Serializable

class LeagueSections(
    var leagues: ArrayList<LeagueFixtures>? = arrayListOf()
) : Serializable

class LeagueFixtures(
    val name: String,
    val games: ArrayList<FixtureInfo>,
) : Serializable
