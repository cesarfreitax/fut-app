package com.fut.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fut.R
import com.fut.core.utils.ResponseWrapper
import com.fut.core.utils.ViewState
import com.fut.features.search.domain.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchRepository: ISearchRepository
) : ViewModel() {

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
}