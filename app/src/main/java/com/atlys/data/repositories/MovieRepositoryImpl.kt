package com.atlys.data.repositories

import com.atlys.base.BaseRepository
import com.atlys.network.ApiService
import com.atlys.utils.Resource
import com.atlys.data.MovieDetails
import com.atlys.domain.repositories.MovieRepository
import com.atlys.data.MovieResponse
import com.atlys.data.Search
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl (private val apiService: ApiService): MovieRepository,BaseRepository(){

    override suspend fun getTrendingMovies(): Flow<Resource<MovieResponse>> {
        return getRequest { apiService.getTrendingMovies() }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> {
        return getRequest { apiService.getMovieDetails(movieId) }
    }

    override suspend fun searchMovie(searchQuery: String): Flow<Resource<Search>> {
        return getRequest { apiService.searchMovie(query = searchQuery) }
    }
}