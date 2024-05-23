package com.example.composeexample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeexample.data.AppContainer
import com.example.composeexample.ui.screens.home.HomeScreen
import com.example.composeexample.ui.screens.home.HomeViewModel
import com.example.composeexample.ui.screens.profile.ProfileScreen

const val ARTICLE_ID = "articleId"

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appContainer: AppContainer,
    startDestination: String = AppDestinations.HOME.toString()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.HOME.toString()) { navBackStackEntry ->
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    articlesRepository = appContainer.articlesRepository,
                    preSelectedArticleId = navBackStackEntry.arguments?.getString(ARTICLE_ID)
                )
            )

            HomeScreen(
                homeViewModel = homeViewModel
            )
        }
        composable(AppDestinations.PROFILE.toString()) {
            ProfileScreen()
        }
    }
}
