package com.customcatalogue.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsScreen(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var selectedFilters by remember { mutableStateOf(setOf("Compose", "Material 3")) }
    var inputVisible by remember { mutableStateOf(true) }
    val allFilters = listOf("Compose", "Material 3", "Expressive", "Android")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("All chip styles", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Chips for actions, choices, input and suggestions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Assist chips", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { scope.launch { snackbarHostState.showSnackbar("Share clicked") } },
                label = { Text("Share") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) }
            )
            AssistChip(
                onClick = { scope.launch { snackbarHostState.showSnackbar("Calendar opened") } },
                label = { Text("Add to calendar") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = null
                    )
                }
            )
        }

        Text("Filter chips (multi-select)", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allFilters.forEach { label ->
                val selected = label in selectedFilters
                FilterChip(
                    selected = selected,
                    onClick = {
                        selectedFilters = if (selected) selectedFilters - label else selectedFilters + label
                        scope.launch { snackbarHostState.showSnackbar("$label ${if (selected) "removed" else "added"}") }
                    },
                    label = { Text(label) },
                    leadingIcon = if (selected) {
                        { Icon(Icons.Filled.Done, contentDescription = null) }
                    } else null
                )
            }
        }
        Text(
            "Selected: ${if (selectedFilters.isEmpty()) "none" else selectedFilters.joinToString()}",
            style = MaterialTheme.typography.bodySmall
        )

        Text("Input chip (dismissible)", style = MaterialTheme.typography.titleMedium)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (inputVisible) {
                InputChip(
                    selected = false,
                    onClick = {
                        inputVisible = false
                        scope.launch { snackbarHostState.showSnackbar("Input chip dismissed") }
                    },
                    label = { Text("user@example.com") },
                    avatar = {
                        Icon(Icons.Filled.Person, contentDescription = null)
                    },
                    trailingIcon = {
                        Icon(Icons.Filled.Close, contentDescription = "Dismiss")
                    }
                )
            } else {
                Text("Dismissed — rotate or revisit to reset.", style = MaterialTheme.typography.bodySmall)
            }
        }

        Text("Suggestion chips", style = MaterialTheme.typography.titleMedium)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Reply", "Remind me", "Summarize", "Translate").forEach { label ->
                SuggestionChip(
                    onClick = { scope.launch { snackbarHostState.showSnackbar("Suggestion: $label") } },
                    label = { Text(label) }
                )
            }
        }
    }
}
