package com.fut.features.userpref.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fut.R
import com.fut.core.utils.ResponseWrapper
import com.fut.core.utils.ViewState
import com.fut.features.search.domain.ISearchRepository
import com.fut.features.userpref.domain.IUserPrefRepository
import com.fut.features.userpref.domain.LeagueInfoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class UserStartPreferencesViewModel @Inject constructor(
    private val searchRepository: ISearchRepository,
    private val userPrefRepository: IUserPrefRepository
): ViewModel() {

    var selectedCountry = ""
    var selectedTeamId = ""
    var selectedTeamName = ""
    val selectedLeagues = arrayListOf<LeagueInfoEntity>()

    fun getTeamsByCountry(country: String) = flow {
        when (val response = searchRepository.getTeamsByCountry(country)) {
            is ResponseWrapper.Success -> {
                emit(ViewState.Success(response.result))
            }
            is ResponseWrapper.Error -> {
                emit(ViewState.Error(R.string.search_error_searching_teams))
            }
        }
    }.onStart {
        emit(ViewState.Loading())
    }.asLiveData()

    fun getLeagues() = flow {
        when (val response = userPrefRepository.getLeagues()) {
            is ResponseWrapper.Success -> {
                emit(ViewState.Success(response.result))
            }
            is ResponseWrapper.Error -> {
                emit(ViewState.Error(R.string.userstartpreferences_error_searching_leagues))
            }
        }
    }.onStart {
        emit(ViewState.Loading())
    }.asLiveData()

    fun getCountries() = flow {
        when (val response = userPrefRepository.getCountries()) {
            is ResponseWrapper.Success -> {
                emit(ViewState.Success(response.result))
            }
            is ResponseWrapper.Error -> {
                emit(ViewState.Error(R.string.userstartpreferences_error_searching_countries))
            }
        }
    }.onStart {
        emit(ViewState.Loading())
    }.asLiveData()
}