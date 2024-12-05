package com.atlys.ui

import kotlinx.serialization.Serializable

sealed interface Screens {
    @kotlinx.serialization.Serializable
    data object MovieListScreen : Screens

    @Serializable
    data class MovieDetailScreen(val id: Int) : Screens
}