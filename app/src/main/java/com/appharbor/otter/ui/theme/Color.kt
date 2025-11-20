package com.appharbor.otter.ui.theme

import androidx.compose.ui.graphics.Color

// Legacy Material 3 colors (kept for compatibility)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Glassmorphism Theme Colors
// Dark theme - deep, rich backgrounds with premium feel
val GlassBackgroundStart = Color(0xFF0A0E27)      // Deep navy blue
val GlassBackgroundMid = Color(0xFF1A1F3A)        // Rich midnight blue
val GlassBackgroundEnd = Color(0xFF2D1B3D)        // Deep purple

// Accent colors for highlights and interactive elements
val GlassAccentPrimary = Color(0xFF6C63FF)        // Vibrant purple
val GlassAccentSecondary = Color(0xFF5B9FFF)      // Bright blue
val GlassAccentTertiary = Color(0xFF00D4FF)       // Cyan blue

// Surface colors with transparency for glass effect
val GlassSurfaceLight = Color.White.copy(alpha = 0.1f)
val GlassSurfaceMedium = Color.White.copy(alpha = 0.15f)
val GlassSurfaceHeavy = Color.White.copy(alpha = 0.25f)

// Border colors for subtle definition
val GlassBorderLight = Color.White.copy(alpha = 0.1f)
val GlassBorderMedium = Color.White.copy(alpha = 0.2f)
val GlassBorderHeavy = Color.White.copy(alpha = 0.3f)

// Text colors
val GlassTextPrimary = Color.White
val GlassTextSecondary = Color.White.copy(alpha = 0.7f)
val GlassTextTertiary = Color.White.copy(alpha = 0.5f)
val GlassTextDisabled = Color.White.copy(alpha = 0.3f)

// Status colors with glass aesthetic
val GlassSuccess = Color(0xFF00E676)              // Bright green
val GlassWarning = Color(0xFFFFAB00)              // Amber
val GlassError = Color(0xFFFF5252)                // Bright red
val GlassInfo = Color(0xFF40C4FF)                 // Light blue

// Light theme variants (for future light mode support)
val LightGlassBackgroundStart = Color(0xFFE8EAF6)
val LightGlassBackgroundMid = Color(0xFFC5CAE9)
val LightGlassBackgroundEnd = Color(0xFFB39DDB)

val LightGlassSurfaceLight = Color.Black.copy(alpha = 0.05f)
val LightGlassSurfaceMedium = Color.Black.copy(alpha = 0.1f)
val LightGlassSurfaceHeavy = Color.Black.copy(alpha = 0.15f)

val LightGlassTextPrimary = Color(0xFF1A1A1A)
val LightGlassTextSecondary = Color(0xFF666666)
val LightGlassTextTertiary = Color(0xFF999999)