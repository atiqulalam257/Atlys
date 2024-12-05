package com.atlys.domain.repositories

import com.atlys.data.MovieDetails
import com.atlys.data.MovieResponse
import com.atlys.data.Search
import com.atlys.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies(): Flow<Resource<MovieResponse>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>
    suspend fun searchMovie(searchQuery: String): Flow<Resource<Search>>
}