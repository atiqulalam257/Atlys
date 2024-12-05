package com.atlys.presentation

import com.atlys.data.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)