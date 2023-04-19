package com.fut.features.matches.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fut.R
import com.fut.core.utils.ResponseWrapper
import com.fut.core.utils.ViewState
import com.fut.features.matches.domain.IMatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MatchesFragmentViewModel @Inject constructor(
    private val matchesRepository: IMatchesRepository
) : ViewModel() {

    fun getFixtures(timezone: String, date: String) = flow {
        when (val response = matchesRepository.getFixtures(timezone, date)) {
            is ResponseWrapper.Success -> {
                emit(ViewState.Success(response))
            }
            is ResponseWrapper.Error -> emit(ViewState.Error(R.string.matches_error_searching_games))
        }
    }.onStart {
        emit(ViewState.Loading())
    }.asLiveData()
}