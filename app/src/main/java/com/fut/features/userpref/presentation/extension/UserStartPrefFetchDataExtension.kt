package com.fut.features.userpref.presentation.extension

import com.fut.core.utils.SnackBarType
import com.fut.core.utils.ViewState
import com.fut.core.utils.showSnackWith
import com.fut.features.search.domain.TeamsEntity
import com.fut.features.userpref.domain.CountriesEntity
import com.fut.features.userpref.domain.LeaguesEntity
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepBaseFragment
import com.fut.features.userpref.presentation.steps.UserStartPreferencesStepEnum
import com.fut.utils.extensions.toggleVisibility

fun UserStartPreferencesStepBaseFragment.setupFetchData() {
    fetchData()
}

private fun UserStartPreferencesStepBaseFragment.fetchData() {
    when (currentStep) {
        UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
            getCountries()
        }
        UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
            getTeamsByCountry()
        }
        UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
            getLeagues()
        }
    }
}

private fun UserStartPreferencesStepBaseFragment.getLeagues() {
    viewModel.getLeagues().observe(viewLifecycleOwner) { viewState ->
        when (viewState) {
            is ViewState.Success -> onFetchDataSuccess(viewState as ViewState<Any?>)
            is ViewState.Error -> onFetchDataError(viewState as ViewState<Any?>)
            is ViewState.Loading -> {}
        }
    }
}

private fun UserStartPreferencesStepBaseFragment.getTeamsByCountry() {
    viewModel.getTeamsByCountry(viewModel.selectedCountry)
        .observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Success -> onFetchDataSuccess(viewState as ViewState<Any?>)
                is ViewState.Error -> onFetchDataError(viewState as ViewState<Any?>)
                is ViewState.Loading -> {}
            }
        }
}

private fun UserStartPreferencesStepBaseFragment.getCountries() {
    viewModel.getCountries().observe(viewLifecycleOwner) { viewState ->
        when (viewState) {
            is ViewState.Success -> onFetchDataSuccess(viewState as ViewState<Any?>)
            is ViewState.Loading -> {}
            is ViewState.Error -> onFetchDataError(viewState as ViewState<Any?>)
        }
    }
}

private fun UserStartPreferencesStepBaseFragment.onFetchDataError(
    viewState: ViewState<Any?>
) {
    toggleLoading()
    showSnackWith(viewState.messageResId ?: 0, SnackBarType.Error)
}

private fun UserStartPreferencesStepBaseFragment.onFetchDataSuccess(viewState: ViewState<Any?>) {
    response = when (currentStep) {
        UserStartPreferencesStepEnum.CHOOSE_COUNTRY -> {
            ((viewState.data as CountriesEntity).response)
        }
        UserStartPreferencesStepEnum.CHOOSE_TEAM -> {
            ((viewState.data as TeamsEntity).response)
        }
        UserStartPreferencesStepEnum.CHOOSE_LEAGUE -> {
            (viewState.data as LeaguesEntity).response
        }
    }.toMutableList()
    data = ArrayList(response)
    toggleLoading()
    setupRecyclerView()
}

private fun UserStartPreferencesStepBaseFragment.toggleLoading() {
    binding.rcv.toggleVisibility(true)
    binding.pgbLoading.toggleVisibility(false)
}