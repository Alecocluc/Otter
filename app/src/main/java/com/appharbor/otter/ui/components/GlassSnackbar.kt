package com.appharbor.otter.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Glass Snackbar Host
 * 
 * A custom SnackbarHost that displays GlassSnackbars.
 * Place this in your Scaffold's snackbarHost parameter.
 * 
 * @param hostState The SnackbarHostState
 * @param modifier Modifier for the host
 */
@Composable
fun GlassSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier,
        snackbar = { data ->
            GlassSnackbar(data)
        }
    )
}

/**
 * Glass Snackbar Component
 * 
 * A premium snackbar with glassmorphism effect.
 * Replaces standard Material snackbars/toasts.
 * 
 * @param data The SnackbarData to display
 */
@Composable
fun GlassSnackbar(
    data: SnackbarData
) {
    GlassSnackbarContent(
        message = data.visuals.message,
        actionLabel = data.visuals.actionLabel,
        onAction = { data.performAction() }
    )
}

/**
 * Glass Snackbar Content
 * 
 * The visual content of the GlassSnackbar.
 * Use this directly if you need to display a snackbar-like element without SnackbarHost.
 * 
 * @param message The message to display
 * @param actionLabel Optional action button label
 * @param onAction Callback when action is clicked
 */
@Composable
fun GlassSnackbarContent(
    message: String,
    actionLabel: String? = null,
    onAction: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background with blur
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1F3A).copy(alpha = 0.85f),
                            Color(0xFF0A0E27).copy(alpha = 0.95f)
                        )
                    )
                )
                .blur(radius = 10.dp)
        )
        
        // Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.2f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
        
        // Content
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            
            actionLabel?.let { label ->
                Spacer(modifier = Modifier.width(12.dp))
                GlassButton(
                    text = label,
                    onClick = onAction,
                    modifier = Modifier.height(36.dp),
                    cornerRadius = 12.dp,
                    backgroundColor = Color.White.copy(alpha = 0.1f)
                )
            }
        }
    }
}
