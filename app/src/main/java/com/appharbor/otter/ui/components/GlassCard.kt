package com.appharbor.otter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import com.appharbor.otter.ui.theme.*

/**
 * Glassmorphism Card Component
 * 
 * A premium card with frosted glass effect, blur background, and subtle borders.
 * Inspired by Apple's design language with liquid glass aesthetics.
 * 
 * @param modifier Modifier for the card
 * @param cornerRadius Corner radius for rounded corners (default: 24.dp)
 * @param backgroundColor Background color with transparency for glass effect
 * @param borderColor Border color for subtle edge definition
 * @param blurRadius Blur radius for frosted glass effect (default: 16.dp)
 * @param elevation Elevation for shadow effect (default: 0.dp)
 * @param content Composable content to display inside the card
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    darkTheme: Boolean = isSystemInDarkTheme(),
    backgroundColor: Color? = null,
    borderColor: Color? = null,
    blurRadius: Dp = 16.dp,
    elevation: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val effectiveBackgroundColor = backgroundColor ?: if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.3f)
    } else {
        LightGlassSurfaceMedium
    }
    
    val effectiveBorderColor = borderColor ?: if (darkTheme) {
        GlassBorderLight
    } else {
        LightGlassBorderMedium
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        color = Color.Transparent,
        tonalElevation = elevation
    ) {
        Box {
            // Background Layer with Blur
            // We use matchParentSize so it takes the size of the content
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                effectiveBackgroundColor.copy(alpha = effectiveBackgroundColor.alpha * 1.2f),
                                effectiveBackgroundColor
                            )
                        ),
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .then(
                        if (blurRadius > 0.dp) {
                            Modifier.blur(radius = blurRadius)
                        } else {
                            Modifier
                        }
                    )
            )

            // Border overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(1.dp)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                effectiveBorderColor,
                                effectiveBorderColor.copy(alpha = effectiveBorderColor.alpha * 0.3f)
                            )
                        )
                    )
            )
            
            // Content
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(RoundedCornerShape(cornerRadius - 2.dp))
                    .background(effectiveBackgroundColor)
            ) {
                content()
            }
        }
    }
}

/**
 * Compact Glass Card
 * 
 * A smaller variant of GlassCard with less corner radius and padding.
 * Ideal for list items and compact UI elements.
 */
@Composable
fun GlassCardCompact(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    GlassCard(
        modifier = modifier,
        cornerRadius = 16.dp,
        blurRadius = 8.dp,
        content = content
    )
}

/**
 * Glass Card with Shimmer
 * 
 * A glass card with animated shimmer effect for loading states.
 * Provides visual feedback during data loading.
 */
@Composable
fun GlassCardShimmer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val shimmerColor = if (darkTheme) {
        GlassBackgroundMid.copy(alpha = 0.2f)
    } else {
        LightGlassSurfaceLight
    }
    
    GlassCard(
        modifier = modifier,
        cornerRadius = cornerRadius,
        darkTheme = darkTheme,
        backgroundColor = shimmerColor
    ) {
        val shimmerHighlight = if (darkTheme) GlassShimmerHighlight else LightGlassShimmerHighlight
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            shimmerHighlight,
                            Color.Transparent
                        )
                    )
                )
        )
    }
}
