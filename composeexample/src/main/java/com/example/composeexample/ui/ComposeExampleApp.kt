package com.example.composeexample.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composeexample.data.AppContainer
import com.example.composeexample.ui.navigation.AppDestinations
import com.example.composeexample.ui.navigation.AppNavGraph
import com.example.composeexample.ui.navigation.AppNavigationActions

@Composable
fun ComposeExampleApp(
    appContainer: AppContainer
) {
    val navController = rememberNavController()
    val navigationAction = remember {
        AppNavigationActions(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppDestinations.HOME.toString()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                AppDestinations.entries.forEach { destination ->
                    NavigationBarItem(
                        icon = { Icon(AppDestinations.icon(destination), null) },
                        label = { Text(stringResource(id = AppDestinations.titleResId(destination))) },
                        selected = currentRoute == destination.toString(),
                        onClick = {
                            navigationAction.navigateTo(destination)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->  
        AppNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            appContainer = appContainer
        )
    }
}
