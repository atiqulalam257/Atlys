package com.atlys.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atlys.R
import com.atlys.presentation.view.CardViewMovie
import com.atlys.presentation.view.SearchBar
import com.atlys.utils.Constants
import com.atlys.ui.Screens

@Composable
fun MovieListScreen(
    viewModel: MovieListScreenViewModel = hiltViewModel(),
    onItemClick: (Screens.MovieDetailScreen) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                searchQuery = uiState.searchQuery,
                onValueChange = { searchTerm ->
                    viewModel.updateSearchTerm(searchTerm)
                },
                onSearch = { searchQuery ->
                    viewModel.search(searchQuery)
                },
                onClear = {
                    viewModel.onSearchClear()
                }
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ProgressBar(uiState)

            MovieList(uiState = uiState, onItemClick = onItemClick)

            ErrorAndRetryUi(uiState, viewModel)
        }
    }
}

@Composable
private fun ProgressBar(uiState: MovieListUiState) {
    AnimatedVisibility(uiState.isLoading) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorAndRetryUi(
    uiState: MovieListUiState,
    viewModel: MovieListScreenViewModel
) {
    AnimatedVisibility(!uiState.isLoading && !uiState.error.isNullOrEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.error ?: stringResource(R.string.some_error_occurred),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            /* if search query is there and api returns empty results.
            * we are reusing this error UI, code can be handled separately for actual use-case.
            */
            AnimatedVisibility(uiState.searchQuery.isEmpty()) {
                Button(
                    onClick = {
                        viewModel.getTrendingMovies()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(stringResource(R.string.try_again))
                }
            }
        }
    }
}

@Composable
fun MovieList(uiState: MovieListUiState, onItemClick: (Screens.MovieDetailScreen) -> Unit) {
    AnimatedVisibility(uiState.error.isNullOrEmpty()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                uiState.trendingMovies,
                key = {
                    it.id
                }
            ) { movie ->
                CardViewMovie(
                    imageUrl = Constants.IMAGE_BASE_URL + movie.backdropPath,
                    id = movie.id,
                    title = movie.title,
                    onItemClick = onItemClick
                )
            }
        }
    }
}