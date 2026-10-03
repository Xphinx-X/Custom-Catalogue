package com.customcatalogue.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ToastsScreen(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Toasts & snackbars", style = MaterialTheme.typography.headlineSmall)
        Text(
            "System toasts for quick pings, snackbars for actionable feedback.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("System toasts", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = {
                Toast.makeText(context, "Short toast — hello!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Show short toast") }
        FilledTonalButton(
            onClick = {
                Toast.makeText(context, "Long toast — stays a little longer.", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Show long toast") }

        Text("Snackbars", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = {
                scope.launch { snackbarHostState.showSnackbar("Simple snackbar") }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Simple snackbar") }
        OutlinedButton(
            onClick = {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "File deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        snackbarHostState.showSnackbar("Restored!")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Snackbar with Undo action") }
        FilledTonalButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Syncing… this one stays longer",
                        duration = SnackbarDuration.Long
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Long snackbar") }
    }
}
