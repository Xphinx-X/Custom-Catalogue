package com.customcatalogue.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetsDialogsScreen(
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var pickedTime by remember { mutableStateOf("08:30") }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Sheets & dialogs", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Bottom sheets, alert dialogs and the Zenith time-picker pattern.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(onClick = { showSheet = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Open bottom sheet")
        }
        FilledTonalButton(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Open alert dialog")
        }
        OutlinedButton(onClick = { showTimePicker = true }, modifier = Modifier.fillMaxWidth()) {
            Text("Pick a time ($pickedTime)")
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Bottom sheet", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Skip-partially-expanded sheet, like Zenith uses for confirmations and pickers.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    onClick = {
                        showSheet = false
                        scope.launch { snackbarHostState.showSnackbar("Confirmed from sheet") }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Confirm") }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete item?") },
            text = { Text("This is a standard M3 alert dialog with confirm + dismiss actions.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    scope.launch { snackbarHostState.showSnackbar("Deleted") }
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            initialTime = pickedTime,
            onDismiss = { showTimePicker = false },
            onTimeSelected = { time ->
                pickedTime = time
                showTimePicker = false
                scope.launch { snackbarHostState.showSnackbar("Picked $time") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialTime: String,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    val parts = initialTime.split(":")
    val timeState = rememberTimePickerState(
        initialHour = parts.getOrNull(0)?.toIntOrNull() ?: 0,
        initialMinute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            val locale = LocalConfiguration.current.locales[0]
            TextButton(onClick = {
                onTimeSelected(String.format(locale, "%02d:%02d", timeState.hour, timeState.minute))
            }) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        text = {
            MaterialTheme(
                typography = MaterialTheme.typography.copy(
                    displayLarge = MaterialTheme.typography.headlineLarge
                )
            ) {
                TimePicker(state = timeState)
            }
        }
    )
}
