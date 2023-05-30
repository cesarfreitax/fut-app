package com.fut.features.matches.presentation.extension

import androidx.appcompat.widget.SearchView
import com.fut.features.matches.data.models.response.LeagueFixtures
import com.fut.features.matches.presentation.MatchesFragment
import com.fut.utils.extensions.unaccent
import java.util.ArrayList

fun MatchesFragment.setupSearchView() {
    binding.srcSearchGames.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = true

        override fun onQueryTextChange(query: String?): Boolean {
            if (query?.isEmpty() == true) {
                setupLeaguesFixturesRecyclerView(
                    leagues = leagueSections.leagues ?: arrayListOf()
                )
            } else {
                val queryIgnoreCase = query.toString().lowercase().unaccent()

                val leagues = leagueSections.leagues?.let { ArrayList(it) }

                val leaguesFilteredByLeagueName =
                    leagues?.filter {
                        it.name.lowercase().unaccent()
                            .contains(queryIgnoreCase)
                    }

                val leaguesFilteredByTeamName = arrayListOf<LeagueFixtures>()

                leagues?.forEach { league ->
                    val leagueFixtures = LeagueFixtures(
                        name = league.name,
                        games = arrayListOf()
                    )

                    league.games.forEach { game ->

                        val teamHome = game.teams.home?.name?.lowercase()?.unaccent()
                        val teamAway = game.teams.away?.name?.lowercase()?.unaccent()

                        if (teamHome?.contains(queryIgnoreCase) == true ||
                            teamAway?.contains(queryIgnoreCase) == true
                        ) {
                            leagueFixtures.games.add(game)
                        }
                    }

                    if (leagueFixtures.games.isNotEmpty()) {
                        leaguesFilteredByTeamName.add(leagueFixtures)
                    }
                }

                filteredFixtures =
                    leaguesFilteredByLeagueName?.plus(leaguesFilteredByTeamName)
                        ?.toMutableList()
                        ?: mutableListOf()

                setupLeaguesFixturesRecyclerView(ArrayList(filteredFixtures))
            }
            return true
        }
    })
}