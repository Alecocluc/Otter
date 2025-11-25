package com.appharbor.otter.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
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
import androidx.compose.foundation.isSystemInDarkTheme
import com.appharbor.otter.ui.theme.*

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
 * Glass Progress Snackbar
 * 
 * A snackbar with a progress bar at the bottom.
 * Perfect for showing download or upload progress.
 * 
 * @param message The message to display
 * @param progress Progress value between 0f and 1f
 */
@Composable
fun GlassProgressSnackbar(
    message: String,
    progress: Float,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val backgroundColor = if (darkTheme) {
        listOf(
            GlassBackgroundMid.copy(alpha = 0.5f),
            GlassBackgroundStart.copy(alpha = 0.6f)
        )
    } else {
        listOf(
            LightGlassBackgroundMid.copy(alpha = 0.9f),
            LightGlassBackgroundStart.copy(alpha = 0.95f)
        )
    }
    
    val borderColor = if (darkTheme) GlassBorderMedium else LightGlassBorderMedium
    val textColor = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val progressBarBackground = if (darkTheme) {
        GlassBorderMedium
    } else {
        LightGlassBorderHeavy
    }
    
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        label = "ProgressAnimation"
    )

    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background with blur
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(colors = backgroundColor)
                )
                .blur(radius = 10.dp)
        )
        
        // Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
        )
        
        // Content
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f),
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "${(progress * 100).toInt()}%",
                    color = textColor.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(progressBarBackground)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress.value.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = glassProgressGradient()
                            )
                        )
                )
            }
        }
    }
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
    onAction: () -> Unit = {},
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val backgroundColor = if (darkTheme) {
        listOf(
            GlassBackgroundMid.copy(alpha = 0.5f),
            GlassBackgroundStart.copy(alpha = 0.6f)
        )
    } else {
        listOf(
            LightGlassBackgroundMid.copy(alpha = 0.9f),
            LightGlassBackgroundStart.copy(alpha = 0.95f)
        )
    }
    
    val borderColor = if (darkTheme) GlassBorderMedium else LightGlassBorderMedium
    val textColor = if (darkTheme) GlassTextPrimary else LightGlassTextPrimary
    val actionBackground = if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceMedium
    }
    
    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background with blur
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(colors = backgroundColor)
                )
                .blur(radius = 10.dp)
        )
        
        // Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
        )
        
        // Content
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                maxLines = 2
            )
            
            actionLabel?.let { label ->
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(actionBackground)
                        .clickable { onAction() }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        color = textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
