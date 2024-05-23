package com.example.composeexample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composeexample.R
import com.example.composeexample.data.AppContainer
import com.example.composeexample.ui.navigation.AppDestinations
import com.example.composeexample.ui.navigation.AppNavGraph
import com.example.composeexample.ui.navigation.AppNavigationActions
import com.poool.access.compose.AccessCompositionLocal

@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.media_title)) },
                navigationIcon = {
                    Image(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(start = 8.dp),
                        painter = painterResource(R.drawable.poool_logo_icon_vsg),
                        contentDescription = null
                    )
                }
            )
        },
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
        AccessCompositionLocal(
            appId = "GP24fGU7rjdGCZ5bRvh9KahttH5fzxrPGKiSu1cabyTrwA8c3aYgI07oG6dQkTs5"
        ) {
            AppNavGraph(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                appContainer = appContainer
            )
        }
    }
}
