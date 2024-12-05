package com.atlys.presentation.home

import com.atlys.data.Movie

data class MovieListUiState(
    val isLoading: Boolean = true,
    val trendingMovies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)