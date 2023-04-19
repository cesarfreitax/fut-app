package com.fut.features.userpref.presentation.extension

import androidx.appcompat.widget.SearchView
import com.fut.features.search.domain.TeamInfoEntity
import com.fut.features.userpref.domain.CountryInfoEntity
import com.fut.features.userpref.domain.LeagueInfoEntity
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepBaseFragment
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepEnum
import com.fut.utils.extensions.hideKeyboard
import com.fut.utils.extensions.unaccent

fun UserStartPreferencesStepBaseFragment.setupSearchView() {
    binding.srcTeamsOrLeagues.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return onSubmitHandler(query.toString())
        }

        override fun onQueryTextChange(query: String?): Boolean {
            return onTextChangeHandler(query.toString())
        }
    })
}

private fun UserStartPreferencesStepBaseFragment.onSubmitHandler(query: String): Boolean {
    data = when (currentStep) {
        UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
            (data as MutableList<CountryInfoEntity>).filter {
                it.name.unaccent().contains(query.unaccent(), ignoreCase = true)
            }.toMutableList()
        }
        UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
            (data as MutableList<TeamInfoEntity>).filter {
                it.team.name.unaccent().contains(query.unaccent(), ignoreCase = true)
            }.toMutableList()
        }
        UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
            (data as MutableList<LeagueInfoEntity>).filter {
                it.league.name.unaccent().contains(query.unaccent(), ignoreCase = true)
            }.toMutableList()
        }
    }
    hideKeyboard()
    adapter.notifyDataSetChanged()
    return true
}

private fun UserStartPreferencesStepBaseFragment.onTextChangeHandler(query: String): Boolean {
    if (query.isNullOrBlank()) {
        data = ArrayList(response)
        adapter.notifyDataSetChanged()
    }
    return true
}