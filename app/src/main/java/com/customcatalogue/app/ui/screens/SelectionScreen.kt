package com.customcatalogue.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectionScreen() {
    var switchOn by remember { mutableStateOf(true) }
    var checked by remember { mutableStateOf(true) }
    var slider by remember { mutableFloatStateOf(0.5f) }
    var stepped by remember { mutableFloatStateOf(2f) }
    var segment by remember { mutableIntStateOf(1) }
    val options = listOf("Day", "Week", "Month")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Selection controls", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Zenith-style expressive switch, checkboxes, sliders and segmented buttons.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Expressive switch", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Mindful pause")
            Switch(
                checked = switchOn,
                onCheckedChange = { switchOn = it },
                thumbContent = {
                    AnimatedContent(
                        targetState = switchOn,
                        transitionSpec = {
                            (fadeIn() + scaleIn(initialScale = 0.5f))
                                .togetherWith(fadeOut() + scaleOut(targetScale = 0.5f))
                        },
                        label = "switch-thumb"
                    ) { on ->
                        Icon(
                            if (on) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }

        Text("Checkbox", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text(if (checked) "Notifications on" else "Notifications off")
        }

        Text("Sliders", style = MaterialTheme.typography.titleMedium)
        Text("Volume: ${(slider * 100).toInt()}%", style = MaterialTheme.typography.bodyMedium)
        Slider(value = slider, onValueChange = { slider = it })
        Text("Steps: ${stepped.toInt()} / 4", style = MaterialTheme.typography.bodyMedium)
        Slider(
            value = stepped,
            onValueChange = { stepped = it },
            valueRange = 0f..4f,
            steps = 3
        )

        Text("Segmented buttons", style = MaterialTheme.typography.titleMedium)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = segment == index,
                    onClick = { segment = index },
                    shape = SegmentedButtonDefaults.itemShape(index, options.size),
                    label = { Text(label) }
                )
            }
        }
        Text(
            "Selected: ${options[segment]}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
