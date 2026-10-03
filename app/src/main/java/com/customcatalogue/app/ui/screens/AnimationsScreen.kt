package com.customcatalogue.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun AnimationsScreen() {
    var expanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    var count by remember { mutableIntStateOf(0) }
    var bouncy by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (bouncy) 1.25f else 1f,
        animationSpec = spring(dampingRatio = 0.35f, stiffness = 300f),
        label = "bouncy-scale"
    )
    val infinite = rememberInfiniteTransition(label = "pulse")
    val pulse by infinite.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse-alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Animations", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Every core Compose animation pattern in one place.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("1 · Expand / collapse", style = MaterialTheme.typography.titleMedium)
        Button(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
            Text(if (expanded) "Collapse card" else "Expand card")
        }
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "This card expands vertically with a fade. Great for FAQs, details, extra settings.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Text("2 · Show / hide with scale + fade", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilledTonalButton(onClick = { visible = !visible }) {
                Text(if (visible) "Hide box" else "Show box")
            }
        }
        AnimatedVisibility(
            visible = visible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Hi", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        Text("3 · Animated counter", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { count-- }) { Text("−") }
            AnimatedContent(targetState = count, label = "counter") { value ->
                Text("$value", style = MaterialTheme.typography.headlineMedium)
            }
            OutlinedButton(onClick = { count++ }) { Text("+") }
        }

        Text("4 · Crossfade", style = MaterialTheme.typography.titleMedium)
        var face by remember { mutableStateOf(false) }
        FilledTonalButton(onClick = { face = !face }) { Text("Crossfade text") }
        Crossfade(targetState = face, label = "crossfade") { second ->
            Text(
                if (second) "Second state — smooth fade between layouts."
                else "First state — tap above to morph.",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text("5 · Springy button", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = { bouncy = !bouncy },
            modifier = Modifier.scale(scale)
        ) { Text("Bounce me") }

        Text("6 · Infinite pulse", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .size(72.dp)
                .alpha(pulse)
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("●", color = MaterialTheme.colorScheme.onTertiary)
        }
    }
}
