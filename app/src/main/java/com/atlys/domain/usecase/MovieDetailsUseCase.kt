package com.atlys.domain.usecase

import com.atlys.data.MovieDetails
import com.atlys.domain.repositories.MovieRepository
import com.atlys.utils.Resource
import kotlinx.coroutines.flow.Flow

class MovieDetailsUseCase(private val movieRepository: MovieRepository) {
    suspend fun getMovieDetails(movieId:Int): Flow<Resource<MovieDetails>> {
        return movieRepository.getMovieDetails(movieId)
    }
}