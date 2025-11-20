package com.appharbor.otter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Glass Background Component
 * 
 * A premium gradient background with glassmorphism aesthetic.
 * Provides the base layer for the app's visual design with smooth color transitions.
 * 
 * @param modifier Modifier for the background
 * @param colors List of colors for the gradient (default: dark blue to purple gradient)
 * @param content Composable content to display on top of the background
 */
@Composable
fun GlassBackground(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    colors: List<Color>? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val gradientColors = colors ?: if (darkTheme) {
        listOf(
            Color(0xFF0A0E27),
            Color(0xFF1A1F3A),
            Color(0xFF2D1B3D)
        )
    } else {
        listOf(
            Color(0xFFF5F7FA),
            Color(0xFFFFFFFF),
            Color(0xFFEEF2F7)
        )
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            )
    ) {
        content()
    }
}

/**
 * Animated Glass Background
 * 
 * A dynamic background with animated gradient for more visual interest.
 * Perfect for splash screens or feature highlights.
 * 
 * @param modifier Modifier for the background
 * @param primaryColor Primary gradient color
 * @param secondaryColor Secondary gradient color
 * @param tertiaryColor Tertiary gradient color
 * @param content Composable content to display on top
 */
@Composable
fun AnimatedGlassBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF0A0E27),
    secondaryColor: Color = Color(0xFF1A1F3A),
    tertiaryColor: Color = Color(0xFF2D1B3D),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        secondaryColor,
                        primaryColor,
                        tertiaryColor
                    )
                )
            )
    ) {
        content()
    }
}

/**
 * Light Glass Background
 * 
 * A lighter variant for light mode or specific screens.
 * 
 * @param modifier Modifier for the background
 * @param content Composable content to display on top
 */
@Composable
fun LightGlassBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    GlassBackground(
        modifier = modifier,
        colors = listOf(
            Color(0xFFE8EAF6),
            Color(0xFFC5CAE9),
            Color(0xFFB39DDB)
        ),
        content = content
    )
}

/**
 * Glass Divider
 * 
 * A subtle glassmorphic divider line for separating content.
 * 
 * @param modifier Modifier for the divider
 * @param color Divider color with transparency
 * @param thickness Thickness of the divider line
 */
@Composable
fun GlassDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.White.copy(alpha = 0.1f),
    thickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        color,
                        Color.Transparent
                    )
                )
            )
    )
}

/**
 * Glass Spacer
 * 
 * A spacer with optional glassmorphic visual separator.
 * 
 * @param height Height of the spacer
 * @param showDivider Whether to show a divider line
 */
@Composable
fun GlassSpacer(
    height: androidx.compose.ui.unit.Dp = 16.dp,
    showDivider: Boolean = false
) {
    if (showDivider) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            GlassDivider(
                modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            )
        }
    } else {
        Spacer(modifier = Modifier.height(height))
    }
}

/**
 * Glass Surface
 * 
 * A general-purpose glassmorphic surface with customizable appearance.
 * Can be used as a container for various UI elements.
 * 
 * @param modifier Modifier for the surface
 * @param backgroundColor Background color with transparency
 * @param borderColor Border color
 * @param content Composable content inside the surface
 */
@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White.copy(alpha = 0.1f),
    borderColor: Color = Color.White.copy(alpha = 0.15f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = backgroundColor.alpha * 1.2f),
                        backgroundColor
                    )
                )
            )
    ) {
        // Border effect
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .background(borderColor)
        )
        
        // Content
        content()
    }
}

/**
 * Glassmorphism Effect Overlay
 * 
 * A reusable overlay effect that can be applied to any composable
 * to give it a glassmorphic appearance.
 * 
 * @param modifier Modifier for the overlay
 * @param alpha Opacity of the glass effect
 */
@Composable
fun GlassEffect(
    modifier: Modifier = Modifier,
    alpha: Float = 0.1f
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = alpha * 1.5f),
                        Color.White.copy(alpha = alpha * 0.5f)
                    )
                )
            )
    )
}
