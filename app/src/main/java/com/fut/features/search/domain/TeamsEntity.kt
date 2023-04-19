package com.fut.features.search.domain

import com.fut.features.search.data.models.response.*
import java.io.Serializable

class TeamsEntity(
    val get: String,
    val parameters: Country,
    val errors: ArrayList<String>,
    val results: Int,
    val paging: Paging,
    val response: ArrayList<TeamInfoEntity>
) : Serializable {

    companion object {
        fun mapper(
            response: TeamsResponse
        ) : TeamsEntity {
            return TeamsEntity(
                get = response.get,
                parameters = response.parameters,
                errors = response.errors,
                results = response.results,
                paging = response.paging,
                response = mapperResponse(response.response)
            )
        }

        private fun mapperResponse(
            response: ArrayList<TeamInfo>
        ) : ArrayList<TeamInfoEntity> {
            val list = arrayListOf<TeamInfoEntity>()
            response.map {
                list.add(mapperTeamInfo(it))
            }
            return list
        }

        private fun mapperTeamInfo(data: TeamInfo) : TeamInfoEntity {
            return TeamInfoEntity(
                team = data.team,
                venue = data.venue
            )
        }
    }
}

class TeamInfoEntity(
    val team: Team,
    val venue: Venue,
    var isSelected: Boolean? = false
) : Serializable