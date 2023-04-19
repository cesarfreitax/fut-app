package com.fut.features.userpref.domain

import com.fut.features.userpref.data.models.response.Country
import com.fut.features.userpref.data.models.response.League
import com.fut.features.userpref.data.models.response.LeagueInfo
import com.fut.features.userpref.data.models.response.Season
import java.io.Serializable


class LeaguesEntity(
    val response: List<LeagueInfoEntity>
) : Serializable {

    companion object {
        fun mapper(response: ArrayList<LeagueInfo>): LeaguesEntity =
            LeaguesEntity(
                response = response.map { league ->
                    leagueInfoMapper(league)
                }
            )

        private fun leagueInfoMapper(response: LeagueInfo): LeagueInfoEntity =
            LeagueInfoEntity(
                league = response.league,
                country = response.country,
                seasons = response.seasons
            )
    }


}

class LeagueInfoEntity(
    val league: League,
    val country: Country,
    val seasons: ArrayList<Season>,
    var isSelected: Boolean? = false
) : Serializable
