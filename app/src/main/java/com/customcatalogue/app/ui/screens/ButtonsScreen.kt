package com.customcatalogue.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ButtonsScreen(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    fun say(msg: String) {
        scope.launch { snackbarHostState.showSnackbar(msg) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("All button styles", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Tap any button — it fires a snackbar so you can feel the feedback.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SectionTitle("Contained")
        Button(onClick = { say("Filled button clicked") }, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Filled.Check, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Filled button")
        }
        FilledTonalButton(onClick = { say("Filled tonal clicked") }, modifier = Modifier.fillMaxWidth()) {
            Text("Filled tonal button")
        }
        ElevatedButton(onClick = { say("Elevated clicked") }, modifier = Modifier.fillMaxWidth()) {
            Text("Elevated button")
        }

        SectionTitle("Outlined & Text")
        OutlinedButton(onClick = { say("Outlined clicked") }, modifier = Modifier.fillMaxWidth()) {
            Text("Outlined button")
        }
        TextButton(onClick = { say("Text button clicked") }, modifier = Modifier.fillMaxWidth()) {
            Text("Text button")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { say("Enabled") }) { Text("Enabled") }
            Button(onClick = {}, enabled = false) { Text("Disabled") }
        }

        SectionTitle("Icon buttons")
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(onClick = { say("Filled icon clicked") }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }
            FilledTonalIconButton(onClick = { say("Tonal icon clicked") }) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
            OutlinedIconButton(onClick = { say("Outlined icon clicked") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
            IconButton(onClick = { say("Plain icon clicked") }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Like")
            }
        }

        SectionTitle("Floating action buttons")
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SmallFloatingActionButton(onClick = { say("Small FAB") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
            FloatingActionButton(onClick = { say("FAB") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
            LargeFloatingActionButton(onClick = { say("Large FAB") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
        ExtendedFloatingActionButton(
            onClick = { say("Extended FAB") },
            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
            text = { Text("Create") }
        )

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
}
