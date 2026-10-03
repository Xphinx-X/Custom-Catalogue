package com.customcatalogue.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProgressScreen() {
    var progress by remember { mutableFloatStateOf(0.6f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Progress & loading", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Zenith-style expressive indicators: wavy progress + loading pills.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Indeterminate loading", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadingIndicator()
            ContainedLoadingIndicator(modifier = Modifier.size(56.dp))
            CircularProgressIndicator()
        }

        Text("Wavy — determinate vs indeterminate", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularWavyProgressIndicator(progress = { progress })
            CircularWavyProgressIndicator()
        }
        LinearWavyProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
        LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())

        Text("Classic — determinate vs indeterminate", style = MaterialTheme.typography.titleMedium)
        CircularProgressIndicator(progress = { progress })
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        Text("Drive it: ${(progress * 100).toInt()}%", style = MaterialTheme.typography.titleMedium)
        Slider(value = progress, onValueChange = { progress = it })
    }
}
