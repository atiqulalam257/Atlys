package com.atlys.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.atlys.ui.Screens

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MovieListScreen,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Screens.MovieListScreen> {
            MovieListScreen(
                onItemClick = { navController.navigate(it) }
            )
        }

        composable<Screens.MovieDetailScreen> {
            val args = it.toRoute<Screens.MovieDetailScreen>()

            DetailScreen(
                id = args.id,
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}