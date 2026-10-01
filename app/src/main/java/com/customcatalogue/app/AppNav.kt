package com.customcatalogue.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route ?: "home"

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Custom Catalogue") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == "components",
                    onClick = { navController.navigate("components") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Components") },
                    label = { Text("Buttons") }
                )
                NavigationBarItem(
                    selected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") { launchSingleTop = true } },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate("components") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Explore") }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    onShowSnackbar = { msg ->
                        // fire-and-forget from composition scope below
                    },
                    snackbarHostState = snackbarHostState,
                    onOpenComponents = { navController.navigate("components") }
                )
            }
            composable("components") {
                ComponentsScreen(snackbarHostState = snackbarHostState)
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(
    onShowSnackbar: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    onOpenComponents: () -> Unit
) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    var toggled by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Welcome to Custom Catalogue", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "M3 Expressive base: dynamic color, edge-to-edge, splash, navigation.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Counter: $count",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { count++ }) { Text("+ Increment") }
                    OutlinedButton(onClick = { count = 0 }) { Text("Reset") }
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Expressive toggle")
                Switch(checked = toggled, onCheckedChange = { toggled = it })
            }
        }

        Button(onClick = onOpenComponents, modifier = Modifier.fillMaxWidth()) {
            Text("Open button gallery")
        }
        TextButton(onClick = {
            scope.launch { snackbarHostState.showSnackbar("Hello from Custom Catalogue! toggled=$toggled") }
            onShowSnackbar("hi")
        }) {
            Text("Show snackbar")
        }
    }
}

@Composable
fun ComponentsScreen(snackbarHostState: SnackbarHostState) {
    var slider by remember { mutableFloatStateOf(0.5f) }
    var chipSelected by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Button gallery", style = MaterialTheme.typography.headlineSmall)

        Button(onClick = { scope.launch { snackbarHostState.showSnackbar("Filled button clicked") } }, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Filled.Check, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Filled button")
        }
        FilledTonalButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Filled tonal button") }
        ElevatedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Elevated button") }
        OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Outlined button") }
        TextButton(onClick = {}) { Text("Text button") }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = chipSelected, onClick = { chipSelected = !chipSelected }, label = { Text("Filter chip") })
            FilterChip(selected = !chipSelected, onClick = { chipSelected = !chipSelected }, label = { Text("Another") })
        }

        Text("Slider: ${(slider * 100).toInt()}%")
        Slider(value = slider, onValueChange = { slider = it })

        Spacer(Modifier.height(64.dp))
    }
}

@Composable
fun SettingsScreen() {
    var expressive by rememberSaveable { mutableStateOf(true) }
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings (base placeholder)", style = MaterialTheme.typography.headlineSmall)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Expressive surfaces")
            Switch(checked = expressive, onCheckedChange = { expressive = it })
        }
        Text(
            "This is a starter screen. Wire DataStore/Room here later.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
