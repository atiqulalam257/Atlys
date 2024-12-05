package com.atlys.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atlys.domain.usecase.MovieListUseCase
import com.atlys.utils.Resource
import com.atlys.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListScreenViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    /* trending movies cached data */
    private var trendingMovies = mutableListOf<Movie>()

    private var searchJob: Job? = null

    init {
        getTrendingMovies()
    }

    fun getTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            // checking if cache is available
            if (trendingMovies.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        trendingMovies = trendingMovies,
                        error = null,
                        searchQuery = ""
                    )
                }
            }

            movieListUseCase.getTrendingMovies().collect { resource ->
                when(resource){
                    is Resource.Error -> _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = resource.message,
                            searchQuery = ""
                        )
                    }
                    Resource.Initial -> { uiState}
                    Resource.Loading -> { uiState}
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                trendingMovies = resource.data?.movies ?: emptyList(),
                                error = null,
                                searchQuery = ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        _uiState.update {
            it.copy(searchQuery = searchTerm)
        }
    }

    fun search(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isLoading = true)
            }

            if (query.isEmpty()) {
                onSearchClear()
                return@launch
            }
            movieListUseCase.searchMovie(query).collect { resource ->
                when(resource){
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = resource.message
                            )
                        }
                    }
                    Resource.Initial,Resource.Loading -> { uiState }
                    is Resource.Success -> {
                        if (resource.data?.searches?.isNotEmpty() == true) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    trendingMovies = resource.data.searches,
                                    error = null
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    trendingMovies = emptyList(),
                                    error = "No result found"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onSearchClear() {
        if (trendingMovies.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    trendingMovies = trendingMovies,
                    error = null,
                    searchQuery = ""
                )
            }
        } else {
            getTrendingMovies()
        }
    }
}