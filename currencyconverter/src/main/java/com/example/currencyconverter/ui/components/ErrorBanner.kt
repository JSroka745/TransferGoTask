package com.example.currencyconverter.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.currencyconverter.ui.theme.ErrorPink
import kotlinx.coroutines.delay

private const val ANIMATION_DURATION_MS = 500


@Composable
fun ErrorBanner(
    modifier: Modifier = Modifier,
    message: String = "Check your internet connection",
    onDismiss: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        visible = true
        delay(5000)
        visible = false
    }

    LaunchedEffect(visible) {
        if (!visible) {
            delay(ANIMATION_DURATION_MS.toLong())
            onDismiss()
        }
    }

    Popup {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(animationSpec = tween(durationMillis = ANIMATION_DURATION_MS)),
            exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(animationSpec = tween(durationMillis = ANIMATION_DURATION_MS))
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.background(color = ErrorPink, shape = CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text("No network", fontWeight = FontWeight.Bold)
                        Text(message, color = Color.Gray, fontSize = 13.sp)
                    }
                    IconButton(onClick = {
                        visible = false
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }
        }
    }
}