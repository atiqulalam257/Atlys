package com.atlys.domain.usecase

import com.atlys.domain.repositories.MovieRepository
import com.atlys.data.MovieResponse
import com.atlys.data.Search
import com.atlys.utils.Resource
import kotlinx.coroutines.flow.Flow

class MovieListUseCase(private val movieRepository: MovieRepository) {
    suspend fun getTrendingMovies(): Flow<Resource<MovieResponse>> {
        return movieRepository.getTrendingMovies()
    }
    suspend fun searchMovie(query: String): Flow<Resource<Search>> {
        return movieRepository.searchMovie(query)
    }
}