package com.atlys.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlys.data.repositories.MovieRepositoryImpl
import com.atlys.domain.usecase.MovieDetailsUseCase
import com.atlys.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            movieDetailsUseCase.getMovieDetails(movieId).collect { resource ->
                when(resource){
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message
                            )
                        }
                    }
                    Resource.Initial,Resource.Loading -> {uiState}
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                movieDetails = resource.data,
                                error = null
                            )
                        }
                    }
                }
            }
        }
    }
}