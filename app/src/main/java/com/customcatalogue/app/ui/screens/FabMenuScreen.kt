package com.customcatalogue.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.material3.ToggleFloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FabMenuScreen(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("FAB menu", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Zenith Focus-style expandable FAB menu. Tap + to reveal actions.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FloatingActionButtonMenu(
            expanded = expanded,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            button = {
                ToggleFloatingActionButton(
                    checked = expanded,
                    onCheckedChange = { expanded = it },
                    containerColor = ToggleFloatingActionButtonDefaults.containerColor(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = if (expanded) "Close menu" else "Open menu"
                    )
                }
            }
        ) {
            FloatingActionButtonMenuItem(
                onClick = {
                    expanded = false
                    scope.launch { snackbarHostState.showSnackbar("Add Shield") }
                },
                icon = { Icon(Icons.Filled.Shield, contentDescription = null) },
                text = { Text("Add Shield") }
            )
            FloatingActionButtonMenuItem(
                onClick = {
                    expanded = false
                    scope.launch { snackbarHostState.showSnackbar("Add Goal") }
                },
                icon = { Icon(Icons.Filled.Flag, contentDescription = null) },
                text = { Text("Add Goal") }
            )
            FloatingActionButtonMenuItem(
                onClick = {
                    expanded = false
                    scope.launch { snackbarHostState.showSnackbar("Add Schedule") }
                },
                icon = { Icon(Icons.Filled.Schedule, contentDescription = null) },
                text = { Text("Add Schedule") }
            )
        }
    }
}
