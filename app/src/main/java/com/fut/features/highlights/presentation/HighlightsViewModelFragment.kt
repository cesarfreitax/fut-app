package com.fut.features.highlights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fut.R
import com.fut.core.utils.ResponseWrapper
import com.fut.core.utils.ViewState
import com.fut.features.highlights.domain.IHighlightsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HighlightsViewModelFragment @Inject constructor(
    private val apiRepository: IHighlightsRepository
) : ViewModel() {

    fun getEverything(query: String, sortBy: String) = flow {
        when (val response = apiRepository.getEverything(query, sortBy)) {
            is ResponseWrapper.Success -> {
                emit(ViewState.Success(response.result))
            }
            is ResponseWrapper.Error -> {
                emit(ViewState.Error(R.string.highlights_news_error_searching_news))
            }
        }
    }.onStart {
        emit(ViewState.Loading())
    }.asLiveData()

}