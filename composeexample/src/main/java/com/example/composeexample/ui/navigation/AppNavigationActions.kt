package com.example.composeexample.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.composeexample.R

enum class AppDestinations {
    HOME {
        override fun toString() = "home"
     },
    PROFILE {
        override fun toString() = "profile"
    };

    companion object {
        fun titleResId(destination: AppDestinations) = when (destination) {
            HOME -> R.string.home_title
            PROFILE -> R.string.profile_title
        }

        fun icon(destination: AppDestinations) = when (destination) {
            HOME -> Icons.Filled.Home
            PROFILE -> Icons.Filled.Person
        }
    }
}

class AppNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AppDestinations.HOME.toString()) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToAccount: () -> Unit = {
        navController.navigate(AppDestinations.PROFILE.toString()) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo(destination: AppDestinations) {
        when (destination) {
            AppDestinations.HOME -> navigateToHome()
            AppDestinations.PROFILE -> navigateToAccount()
        }
    }
}
