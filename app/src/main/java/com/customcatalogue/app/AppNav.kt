package com.customcatalogue.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.customcatalogue.app.ui.screens.AnimationsScreen
import com.customcatalogue.app.ui.screens.ButtonsScreen
import com.customcatalogue.app.ui.screens.ChipsScreen
import com.customcatalogue.app.ui.screens.HomeScreen
import com.customcatalogue.app.ui.screens.ToastsScreen

private fun titleFor(route: String?): String = when (route) {
    "buttons" -> "Buttons"
    "chips" -> "Chips"
    "toasts" -> "Toasts & Snackbars"
    "animations" -> "Animations"
    else -> "Custom Catalogue"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav(@Suppress("UNUSED_PARAMETER") windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val isHome = currentRoute == null || currentRoute == "home"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titleFor(currentRoute)) },
                navigationIcon = {
                    if (!isHome) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
            composable("buttons") {
                ButtonsScreen(snackbarHostState = snackbarHostState)
            }
            composable("chips") {
                ChipsScreen(snackbarHostState = snackbarHostState)
            }
            composable("toasts") {
                ToastsScreen(snackbarHostState = snackbarHostState)
            }
            composable("animations") {
                AnimationsScreen()
            }
        }
    }
}
